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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReturnLockerHardcode extends AppCompatActivity {

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
    private String size;
    private String sizeTemp;
    private String totalPay;
    private String duration;
    private Handler mainHandler = new Handler();
    private volatile boolean stopThread = false;
    DatabaseController dc = new DatabaseController();
    BookingController bc = new BookingController();
    UserController uc = new UserController();

    public TextView rLocation, rbookedDate, rbookedTime ,rbookedDuration, rtextSize, rtextLockerStructureid,
            rtextLockerid, rtextTotalPay;
    Button returnBtn;
    ProgressBar RprogressBar;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_locker);

        rLocation = findViewById(R.id.rLocation);
        rbookedDate = findViewById(R.id.rbookedDate);
        rbookedTime = findViewById(R.id.rbookedTime);
        rtextSize = findViewById(R.id.rtextSize);
        rtextTotalPay = findViewById(R.id.rtextTotalPay);
        rtextLockerid = findViewById(R.id.rtextLockerid);
        rtextLockerStructureid = findViewById(R.id.rtextLockerStructureid);
        returnBtn = findViewById(R.id.returnBtn);
        rbookedDuration = findViewById(R.id.rbookedDuration);
        RprogressBar = findViewById(R.id.RprogressBar);



        bookid = this.getIntent().getStringExtra("bookid");
        endDate = this.getIntent().getStringExtra("endDate");
        endTime = this.getIntent().getStringExtra("endTime");
        lockerid = this.getIntent().getStringExtra("lockerid");
        startDate = this.getIntent().getStringExtra("startDate");
        startTime = this.getIntent().getStringExtra("startTime");
        status = this.getIntent().getStringExtra("status");
        structureid = this.getIntent().getStringExtra("structureid");
        mobile = this.getIntent().getStringExtra("mobile");
        location = this.getIntent().getStringExtra("location");
        //sizeTemp = this.getIntent().getStringExtra("size");
        size = this.getIntent().getStringExtra("size");

        totalPay = this.getIntent().getStringExtra("totalPay");


        //rLocation.setText(location);
        rbookedDate.setText("2021-04-21"+ "-" +"2021-04-21");
        rbookedTime.setText("13:00:00" +" - "+"14:00:00");
        rbookedDuration.setText("1 hour");
        rtextSize.setText("M");
        rtextLockerStructureid.setText("Structure 1");
        rtextLockerid.setText("4");
        rtextTotalPay.setText("$2");
        // rtextSize.setText(size);
        // rtextTotalPay.setText(totalPay);
        // rtextLockerid.setText(lockerid);
        // rtextLockerStructureid.setText(structureid);

        String strs = startDate +" "+ startTime;//2021-04-21 13:00:00
        String stre = endDate +" "+ endTime;//2021-04-21 13:00:00
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDateTime sdateTime = LocalDateTime.parse(strs, formatter);
        //  LocalDateTime edateTime = LocalDateTime.parse(stre, formatter);
        // Duration d = Duration.between(sdateTime,edateTime);
        // long minutes = d.toMinutes();
//        int hoursd = (int) Math.floor((int)minutes / 60);
//        int min = (int)minutes % 60;
//        duration = hoursd+" hours "+min+" minutes";
        //    rbookedDuration.setText(duration);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });
    }

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure to return this booking?");
        dialog.setTitle("Return Locker");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        // ReturnLocker.CalculateLateFeesThread runnable2 = new ReturnLocker.CalculateLateFeesThread();
                        //new Thread(runnable2).start();
                      /*  FragmentManager fm =getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        WalletFragment lf = new WalletFragment();`
                        ft.replace(R.id.walletFragment,lf);
                        ft.commit();*/
                        Toast.makeText(ReturnLockerHardcode.this, "Succesfully returned locker.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainFunc.class));




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







}