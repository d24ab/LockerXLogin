package com.example.lockerxlogin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lockerxlogin.fragment.AccountsFragment;
import com.example.lockerxlogin.fragment.LockersFragment;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;

public class CancelBooking extends AppCompatActivity {

    private String bookid;
    private String endDate;
    private String endTime;
    private String lockerid;
    private String startDate;
    private String startTime;
    private String status;
    private String structureid;
    private String mobile;
    private String location;
    private char size;
    private String totalPay;
    private String duration;
    private Handler mainHandler = new Handler();
    private volatile boolean stopThread = false;
    DatabaseController dc = new DatabaseController();
    BookingController bc = new BookingController();
    ProgressBar CprogressBar;

    TextView textLocation, bookedDate, bookedTime, textSize, textTotalPay, textLockerid, textLockerStructureid, bookedDuration;
    Button cancelBtn;




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_booking);
        CancelBooking.RetrieveLockerDetailsThread runnable = new CancelBooking.RetrieveLockerDetailsThread();
        new Thread(runnable).start();


        textLocation = findViewById(R.id.cLocation);
        bookedDate = findViewById(R.id.cbookedDate);
        bookedTime = findViewById(R.id.cbookedTime);
        textSize = findViewById(R.id.ctextSize);
        textTotalPay = findViewById(R.id.ctextTotalPay);
        textLockerid = findViewById(R.id.ctextLockerid);
        textLockerStructureid = findViewById(R.id.ctextLockerStructureid);
        bookedDuration = findViewById(R.id.cbookedDuration);
        CprogressBar = findViewById(R.id.CprogressBar);
        cancelBtn = findViewById(R.id.cancelBtn);


        bookid = this.getIntent().getStringExtra("bookid");
        endDate = this.getIntent().getStringExtra("endDate");
        endTime = this.getIntent().getStringExtra("endTime");
        lockerid = this.getIntent().getStringExtra("lockerid");
        startDate = this.getIntent().getStringExtra("startDate");
        startTime = this.getIntent().getStringExtra("startTime");
        status = this.getIntent().getStringExtra("status");
        structureid = this.getIntent().getStringExtra("structureid");
        mobile = this.getIntent().getStringExtra("mobile");

        //location = this.getIntent().getStringExtra("location");
        //size = this.getIntent().getStringExtra("size");
        //totalPay = this.getIntent().getStringExtra("totalPay");


        //textLocation.setText(location);
        bookedDate.setText(startDate+" - "+endDate);
        bookedTime.setText(startTime+" - "+endTime);
        // textSize.setText(size);
        //textTotalPay.setText(totalPay);
        textLockerid.setText(lockerid);
        textLockerStructureid.setText(structureid);


        String strs = startDate +" "+ startTime;//2021-04-21 13:00:00
        String stre = endDate +" "+ endTime;//2021-04-21 13:00:00
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime sdateTime = LocalDateTime.parse(strs, formatter);
        LocalDateTime edateTime = LocalDateTime.parse(stre, formatter);
        Duration d = Duration.between(sdateTime,edateTime);
        long minutes = d.toMinutes();
        int hoursd = (int) Math.floor((int)minutes / 60);
        int min = (int)minutes % 60;
        duration = hoursd+" hours "+min+" minutes";
        bookedDuration.setText(duration);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog();

            }
        });



    }
    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure to cancel this booking?");
        dialog.setTitle("Cancellation");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        //TODO delete lockers from user!!!!!
                        dc.setBookingStatus(mobile,Long.parseLong(structureid),Long.parseLong(lockerid),
                                LocalDate.parse(startDate),LocalTime.parse(startTime),
                                LocalDate.parse(endDate),LocalTime.parse(endTime),'C');
                        Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                        FragmentManager fm =getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        LockersFragment lf = new LockersFragment();
                        ft.replace(R.id.lockersFragment,lf);
                        ft.commit();
                    }
                });
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    class RetrieveLockerDetailsThread implements Runnable{
        RetrieveLockerDetailsThread(){
            //
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            //TODO place code here to run for this thread


            for (int i = 0; i < 100000; i++) {
                Log.d("TAG", "HIHIHI" + i);
                size = dc.retrieveLockerSize(Long.parseLong(structureid),Long.parseLong(lockerid));
                location = dc.retrieveLocationName(Long.parseLong(structureid));



                Log.d("TAG", totalPay + " Here is the total pay");
                Log.d("TAG", location + " Here is the location");


//                rLocation.setText(location);


                // rtextTotalPay.setText(totalPay);




                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }






                if (size!='a') {
                    Log.d("HERE" , "Locker size is " + size);
                    Log.d("HERE" , "Location is is " + location);

                    float d = bc.calculateRentalFees(Long.parseLong(structureid),Long.parseLong(lockerid), LocalDate.parse(startDate),
                            LocalTime.parse(startTime), LocalDate.parse(endDate), LocalTime.parse(endTime), size);

                    totalPay = String.format("%.2f", d);

                    if(totalPay!=null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CprogressBar.setVisibility(View.GONE);
                                textSize.setText(Character.toString(size));
                                textLocation.setText(location);
                                textTotalPay.setText(totalPay);
                            }
                        });

                        stopThread = true;
                    }
                    if (stopThread) {
                        //dbProgressBar.setVisibility(View.GONE);



                        return;
                    }
                }

            }

        }

    }


    public void startThread(View view){
        stopThread = false;
        CancelBooking.RetrieveLockerDetailsThread runnable = new CancelBooking.RetrieveLockerDetailsThread();
        new Thread(runnable).start();

    }
    public void stopThread(View view){
        stopThread = true;
    }
}