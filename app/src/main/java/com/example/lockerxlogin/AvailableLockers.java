package com.example.lockerxlogin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;

import com.example.lockerxlogin.fragment.BookingHistoryArrAdapter;
import com.example.lockerxlogin.fragment.BookingSearchArrAdapter;
import com.example.lockerxlogin.ui.BookingSearchArr;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AvailableLockers extends AppCompatActivity {

    public static RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static LocalDate startDate, endDate;
    public static LocalTime startTime, endTime;
    public static int sDateYear, sDateMonth, sDateDay, eDateYear, eDateMonth, eDateDay,
            sTimeHour, sTimeMin, eTimeHour, eTimeMin;
    public static String postal, location;
    public static String size;
    public boolean stopThread;

    DatabaseController dc = new DatabaseController();



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postal = this.getIntent().getStringExtra("postal");
        size = this.getIntent().getStringExtra("size");
        location = this.getIntent().getStringExtra("location");
        sDateDay = this.getIntent().getIntExtra("sDateDay",0);
        sDateMonth = this.getIntent().getIntExtra("sDateMonth",0);
        sDateYear = this.getIntent().getIntExtra("sDateYear",0);
        eDateDay = this.getIntent().getIntExtra("eDateDay",0);
        eDateMonth = this.getIntent().getIntExtra("eDateMonth",0);
        eDateYear = this.getIntent().getIntExtra("eDateYear",0);
        sTimeHour = this.getIntent().getIntExtra("sTimeHour",0);
        sTimeMin = this.getIntent().getIntExtra("sTimeMin",0);
        eTimeHour = this.getIntent().getIntExtra("eTimeHour",0);
        eTimeMin = this.getIntent().getIntExtra("eTimeMinute",0);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, sDateYear);
        c.set(Calendar.MONTH, sDateMonth);
        c.set(Calendar.DAY_OF_MONTH, sDateDay);
        startDate = LocalDateTime.ofInstant(c.toInstant(), c.getTimeZone().toZoneId()).toLocalDate();

        c.set(Calendar.YEAR, eDateYear);
        c.set(Calendar.MONTH, eDateMonth);
        c.set(Calendar.DAY_OF_MONTH, eDateDay);
        endDate = LocalDateTime.ofInstant(c.toInstant(), c.getTimeZone().toZoneId()).toLocalDate();

        startTime = LocalTime.of(sTimeHour, sTimeMin);
        endTime = LocalTime.of(eTimeHour, eTimeMin);

        System.out.println(endTime);

        //returnToMain=false;

        AvailableLockers.BookingMultiThread dbThread = new AvailableLockers.BookingMultiThread();
        Thread t = new Thread(dbThread);
        t.start();

        setContentView(R.layout.activity_available_lockers);

    }

    class BookingMultiThread implements Runnable{
        BookingMultiThread(){
            //
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            //TODO place code here to run for this thread



            for (int i = 0; i < 10000; i++) {
                Log.d("TAG", "HIHIHI" + i);



                //mAdapter = mRecyclerView.getAdapter();
                //              mAdapter.notifyItemInserted(exampleList.size()-1);

                ArrayList<Booking> bBookings = dc.retrieveBBookings();
                ArrayList<Locker> availLockers = dc.retrieveMatchingLockers(postal, size.charAt(0));
                //bookingSearchArr = new ArrayList<BookingSearchArr>();
                //System.out.println("out");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /*stopThread = true;
                if(stopThread){
                    return;
                }*/


                if (!availLockers.isEmpty() && !bBookings.isEmpty()) {

                    stopThread = true;
                    if (stopThread) {

                        //dbProgressBar.setVisibility(View.GONE);

                        ArrayList<Long> unavailLockers = new ArrayList<Long>();

                        for (Booking booking : bBookings) {

                            if ((booking.getStartDate().compareTo(startDate) >= 0 && booking.getStartDate().compareTo(endDate) <= 0) ||
                                    (booking.getEndDate().compareTo(startDate) >= 0 && booking.getEndDate().compareTo(endDate) <= 0) ||
                                    (booking.getStartDate().compareTo(startDate) <= 0 && booking.getEndDate().compareTo(endDate) >= 0)) {
                                if ((booking.getStartTime().compareTo(startTime) >= 0 && booking.getStartTime().compareTo(endTime) <= 0) ||
                                        (booking.getEndTime().compareTo(startTime) >= 0 && booking.getEndTime().compareTo(endTime) <= 0) ||
                                        (booking.getStartTime().compareTo(startTime) <= 0 && booking.getEndTime().compareTo(endTime) >= 0)) {
                                    unavailLockers.add(booking.getLockerID());
                                }
                            }
                        }
                        for (Locker locker : availLockers) {
                            if (unavailLockers.contains(locker.getLockerID())) {
                                availLockers.remove(locker);
                            }
                        }
                        /*for (i=0; i<availLockers.size();i++){
                            bookingSearchArr.add(new BookingSearchArr(availLockers.get(i).getLockerID(), availLockers.get(i).getStructureID()));
                            System.out.println(bookingSearchArr.get(i).getLockerID());
                            System.out.println(availLockers.get(i).getLockerID());
                            //bookingSearchArr.get(i).setStructureID(availLockers.get(i).getStructureID());
                            System.out.println(bookingSearchArr.get(i).getStructureID());
                            System.out.println(availLockers.get(i).getStructureID());
                        }*/
                        //System.out.println(availLockers.size());
                        //System.out.println(availLockers.get(0).getLockerID());
                        //System.out.println(availLockers.get(0).getStructureID());

                        runOnUiThread(new Runnable (){
                            @Override
                            public void run() {

                                mRecyclerView = findViewById(R.id.recyclerView);
                                mRecyclerView.setHasFixedSize(true);
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                mRecyclerView.setAdapter(new BookingSearchArrAdapter(availLockers));
                                //mAdapter = mRecyclerView.getAdapter();
                            }




                        });


                       /* runOnUiThread(new Runnable (){
                            @Override
                            public void run() {
                                ArrayList <String> lockerArray = new ArrayList<String>();
                                mRecyclerView = findViewById(R.id.recyclerView);
                                mRecyclerView.setHasFixedSize(true);
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                //mRecyclerView.setAdapter(new BookingSearchArrAdapter(bookingSearchArr));
                            }

                        });*/

                        return;
                    }
                }

            }

        }

    }


    public void startThread(View view){
        stopThread = false;
        AvailableLockers.BookingMultiThread runnable = new AvailableLockers.BookingMultiThread();
        new Thread(runnable).start();

    }
    public void stopThread(View view){
        stopThread = true;
    }
}