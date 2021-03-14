package com.example.lockerxlogin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.lockerxlogin.fragment.LockersFragment;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CancelBookingHardcode extends AppCompatActivity {

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
//        CancelBooking.RetrieveLockerDetailsThread runnable = new CancelBooking.RetrieveLockerDetailsThread();
//        new Thread(runnable).start();


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


//        bookid = this.getIntent().getStringExtra("bookid");
//        endDate = this.getIntent().getStringExtra("endDate");
//        endTime = this.getIntent().getStringExtra("endTime");
//        lockerid = this.getIntent().getStringExtra("lockerid");
//        startDate = this.getIntent().getStringExtra("startDate");
//        startTime = this.getIntent().getStringExtra("startTime");
//        status = this.getIntent().getStringExtra("status");
//        structureid = this.getIntent().getStringExtra("structureid");
//        mobile = this.getIntent().getStringExtra("mobile");

        //location = this.getIntent().getStringExtra("location");
        //size = this.getIntent().getStringExtra("size");
        //totalPay = this.getIntent().getStringExtra("totalPay");
        textLocation.setText("Jurong point");
        bookedDate.setText("2021-04-21" + "-" + "2021-04-21");
        bookedTime.setText("13:00:00" + " - " + "14:00:00");
        textSize.setText("M");
        textTotalPay.setText("$2");
        ;
        textLockerid.setText("4");
        textLockerStructureid.setText("Structure 1");
        bookedDuration.setText("1 hour");


        //textLocation.setText(location);
//        bookedDate.setText(startDate+" - "+endDate);
//        bookedTime.setText(startTime+" - "+endTime);
//        // textSize.setText(size);
//        //textTotalPay.setText(totalPay);
//        textLockerid.setText(lockerid);
//        textLockerStructureid.setText(structureid);
//
//
//        String strs = startDate +" "+ startTime;//2021-04-21 13:00:00
//        String stre = endDate +" "+ endTime;//2021-04-21 13:00:00
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDateTime sdateTime = LocalDateTime.parse(strs, formatter);
//        LocalDateTime edateTime = LocalDateTime.parse(stre, formatter);
//        Duration d = Duration.between(sdateTime,edateTime);
//        long minutes = d.toMinutes();
//        int hoursd = (int) Math.floor((int)minutes / 60);
//        int min = (int)minutes % 60;
//        duration = hoursd+" hours "+min+" minutes";
        //  bookedDuration.setText(duration);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog();

            }
        });


    }

    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure to cancel this booking?");
        dialog.setTitle("Cancellation");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onClick(DialogInterface dialog,
                                        int which) {

//                        //TODO delete lockers from user!!!!!
//                        dc.setBookingStatus(mobile,Long.parseLong(structureid),Long.parseLong(lockerid),
//                                LocalDate.parse(startDate), LocalTime.parse(startTime),
//                                LocalDate.parse(endDate),LocalTime.parse(endTime),'C');
//                        Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
//                        FragmentManager fm =getSupportFragmentManager();
//                        FragmentTransaction ft = fm.beginTransaction();
//                        LockersFragment lf = new LockersFragment();
//                        ft.replace(R.id.lockersFragment,lf);
//                        ft.commit();
                        Toast.makeText(CancelBookingHardcode.this, "Succesfully cancelled locker.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainFunc.class));
                    }
                });
        dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "cancel is clicked", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

}