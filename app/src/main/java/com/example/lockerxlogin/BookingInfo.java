package com.example.lockerxlogin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lockerxlogin.fragment.BookingSearchArrAdapter;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class BookingInfo extends AppCompatActivity {

    UserController uc = new UserController();
    BookingController bc = new BookingController();
    private Button payBtn;
    private TextView textLocation,textDateDuration,textTimeDuration,textBookingHour,textRate,textSize,textStructureid,textTotalPay;
    public double total;
    public long lockerID, structureID;
    public boolean bookingResult, stopThread;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_info);

        payBtn = findViewById(R.id.bookBtn);
        textLocation = findViewById(R.id.bLocation);
        textDateDuration = findViewById(R.id.bbookedDate);
        textTimeDuration = findViewById(R.id.bbookedTime);
        textBookingHour = findViewById(R.id.bbookedDuration);
        textRate = findViewById(R.id.textRate);
        textSize = findViewById(R.id.btextSize);
        textStructureid = findViewById(R.id.btextLockerStructureid);
        textTotalPay = findViewById(R.id.btextTotalPay);

        lockerID = this.getIntent().getLongExtra("LockerID", 0);
        structureID = this.getIntent().getLongExtra("StructureID", 0);

        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);

        textLocation.setText(AvailableLockers.location);
        textDateDuration.setText(AvailableLockers.startDate.toString() + " to " +AvailableLockers.endDate.toString());
        textTimeDuration.setText(AvailableLockers.startTime.toString() + " to " + AvailableLockers.endTime.toString());
        textSize.setText(AvailableLockers.size);
        textStructureid.setText(Long.toString(structureID));


        //String strs = AvailableLockers.startDate +" "+ AvailableLockers.startTime;//2021-04-21 13:00:00
        //String stre = AvailableLockers.endDate +" "+ AvailableLockers.endTime;//2021-04-21 13:00:00
        LocalDateTime startDateTime = LocalDateTime.of(AvailableLockers.startDate,AvailableLockers.startTime);
        LocalDateTime endDateTime = LocalDateTime.of(AvailableLockers.endDate,AvailableLockers.endTime);
        long differenceInMinutes = ChronoUnit.MINUTES.between(startDateTime,endDateTime);
        int hoursd = (int) Math.floor((int)differenceInMinutes / 60);
        //textBookingHour.setText(Long.toString(differenceInMinutes) + " Minutes");
        textBookingHour.setText(Integer.toString(hoursd) + " Hours");

        if (AvailableLockers.size.charAt(0) == 'S'){
            total = (double) differenceInMinutes / 60;
            textRate.setText("$1/hour");
        }
        else if (AvailableLockers.size.charAt(0) == 'M') {
            total = (double) differenceInMinutes / 30;
            textRate.setText("$2/hour");
        }
        else {
            total = (double) differenceInMinutes / 20;
            textRate.setText("$3/hour");
        }

        textTotalPay.setText("$ " + df.format(total));


        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO update datebase: new booking + update wallet
                //BookingInfo.BookingMultiThread dbThread = new BookingInfo.BookingMultiThread();
                //Thread t = new Thread(dbThread);
                //t.start();

                //TODO jumo to locker fragment page

            }
        });

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

                bookingResult = uc.makeBooking(structureID, lockerID, AvailableLockers.startDate, AvailableLockers.startTime,
                        AvailableLockers.endDate, AvailableLockers.endTime,AvailableLockers.size.charAt(0));

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /*stopThread = true;
                if(stopThread){
                    return;
                }*/


                if (bookingResult == true) {

                    stopThread = true;
                    if (stopThread) {

                        System.out.println("HELLO");
                        System.out.println("Booking Successful!");

                        //bc.makeBooking(Login.currUser.getMobile(), structureID, lockerID, AvailableLockers.startDate,
                           //     AvailableLockers.startTime, AvailableLockers.endDate, AvailableLockers.endTime);

                    }
                }

                if (bookingResult == false) {
                    stopThread = true;
                    if (stopThread) {

                        System.out.println("HI");
                        System.out.println("Booking Failed!");
                    }

                        runOnUiThread(new Runnable (){
                            @Override
                            public void run() {
                            }

                        });

                        return;
                }

            }

        }

    }


    public void startThread(View view){
        stopThread = false;
        BookingInfo.BookingMultiThread runnable = new BookingInfo.BookingMultiThread();
        new Thread(runnable).start();

    }
    public void stopThread(View view){
        stopThread = true;
    }
}