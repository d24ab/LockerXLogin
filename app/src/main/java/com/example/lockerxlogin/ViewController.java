package com.example.lockerxlogin;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ViewController {

    public ViewController(){} //constructor

    @RequiresApi(api = Build.VERSION_CODES.O)
    //retrieve a booking list containing all bookings which are booked, status = 'B'
    public ArrayList<Long> viewAvailLocker (DatabaseController dc, int lockerStructureID,
                                               LocalDate startDate, LocalDate endDate, LocalTime startTime,
                                               LocalTime endTime, char lockerSize){

        //ArrayList<Booking> bookings = dc.retrieveBookedBookingList();

        //List to store all LockerIDs that are unavailable
        ArrayList<Long> unavailLockers = new ArrayList<Long>();

        //variable declaration for use in for loop later to enhance readability
        LocalDate bookingStartDate;
        LocalTime bookingStartTime;
        LocalDate bookingEndDate;
        LocalTime bookingEndTime;
        /*
        for(int i=0; i<bookings.size();i++){
            //variable initialisation to enhance readability
            bookingStartDate = bookings.get(i).getStartDate();
            bookingStartTime = bookings.get(i).getStartTime();
            bookingEndDate = bookings.get(i).getEndDate();
            bookingEndTime = bookings.get(i).getEndTime();

            //checking for existing bookings (ongoing and future) which clash with the current user's selection
            if ((bookingStartDate.compareTo(startDate)>=0 && bookingStartDate.compareTo(endDate)<=0) ||
                    (bookingEndDate.compareTo(startDate)>=0 && bookingEndDate.compareTo(endDate)<=0) ||
                    (bookingStartDate.compareTo(startDate)<=0 && bookingEndDate.compareTo(endDate)>=0)) {
                if ((bookingStartTime.compareTo(startTime)>=0 && bookingStartTime.compareTo(endTime)<=0) ||
                        (bookingEndTime.compareTo(startTime)>=0 && bookingEndTime.compareTo(endTime)<=0) ||
                        (bookingStartTime.compareTo(startTime)<=0 && bookingEndTime.compareTo(endTime)>=0)) {
                    unavailLockers.add(bookings.get(i).getLockerID());
                }
            }
        }
        //A list of lockerID, filtered by lockerSize
//        ArrayList<Long> availLockers = dc.retrieveLockerIDFilterByLockerSize(lockerStructureID,lockerSize);
//        for(int j=0;j<availLockers.size();j++){
//            for(int a=0;a<unavailLockers.size();a++){
//                /*comparing list of available lockers to unavailable ones, if match, remove lockerID
//                from the available list*/
//                if(availLockers.get(j)==unavailLockers.get(a)){
//                    availLockers.remove(j);
//                    break;
//                }
//            }
//        }
        return new ArrayList<Long>();
//        return availLockers;
    }
}