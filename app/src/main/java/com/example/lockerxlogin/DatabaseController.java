package com.example.lockerxlogin;

import android.os.Build;
import android.renderscript.Sampler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DatabaseController {
    private static final String TAG = "DatabaseController"; //for Log

    TempDataStorage ds = new TempDataStorage();

    public DatabaseController(){}

    //TODO remove event listeners for all the methods and see if the app still crashes randomly, if it does then just dont remove?

    //method to store new user to db
    public void storeNewUser(String name, String email, String mobile) {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("User"); //reference to the "User" table of the db
        //store new user to db
        reff.child(mobile).child("name").setValue(name);
        reff.child(mobile).child("email").setValue(email);
        reff.child(mobile).child("mobile").setValue(mobile);
        reff.child(mobile).child("walletBalance").setValue(0);
        reff.child(mobile).child("lateFees").setValue(0);
    }


//    //retrieve all users from the db
//    public ArrayList retrieveAllUsers() {
////        ArrayList<User> userList = new ArrayList<User>();
////        User dummyUser = new User();
////        dummyUser.setName("error");
////        userList.add(dummyUser);
////        ds.setUserList(userList);
//        reff = FirebaseDatabase.getInstance().getReference().child("User");
//        reff.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    ds.getUserList().clear();
////                    ds.setUserCount(snapshot.getChildrenCount());
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        User user = dataSnapshot.getValue(User.class);
//                        ds.getUserList().add(user);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        return ds.getUserList();
//        //use this to check if data has been retrieved: ArrayList<User> userList = ds.retrieveAllUsers(); while (userList.get(0).getName()==="error") {userList=ds.retrieveAllUsers();}
//
//    }


    //retrieve user using their email from db
    public User retrieveUserByEmail(String email) {
//        User resetUser = new User();
//        resetUser.setName("error");
//        ds.setUser(resetUser);
        Query query = FirebaseDatabase.getInstance().getReference().child("User").orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        User user = new User(snapshot.child("name").getValue().toString(), snapshot.child("email").getValue().toString(), snapshot.child("mobile").getValue().toString(), Float.parseFloat(snapshot.child("walletBalance").getValue().toString()), Float.parseFloat(snapshot.child("lateFees").getValue().toString()));
                        User user = dataSnapshot.getValue(User.class);
                        ds.setUser(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return ds.getUser();
    }


//    //retrieve user using their mobile from db
//    public User retrieveUserByMobile(String mobile) {
////        User resetUser = new User();
////        resetUser.setName("error");
////        ds.setUser(resetUser);
//        Query query = FirebaseDatabase.getInstance().getReference().child("User").orderByChild("mobile").equalTo(mobile);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        User user = new User(snapshot.child("name").getValue().toString(), snapshot.child("email").getValue().toString(), snapshot.child("mobile").getValue().toString(), Float.parseFloat(snapshot.child("walletBalance").getValue().toString()), Float.parseFloat(snapshot.child("lateFees").getValue().toString()));
//                        ds.setUser(user);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        return ds.getUser();
//        //use this to check if data has been retrieved: User user = ds.retriveUserByMobile("target mobile"); while (user.getName()==="error") {user=ds.retrieveUserByMobile("target mobile");}
//    }


    //retrieve user's mobile using their email from db
    //NOTE!!! when calling this method, make sure to do what's stated beside the return "error"; statement!
    //need to do this bc retrieving the data is asynchronous so other code may run before the data is even retrieved
    //so must check that the data is retrieved before you use it!
//    public String retrieveMobileByEmail(String email) {
//        ds.setStr("error");
//        Query query = FirebaseDatabase.getInstance().getReference().child("User").orderByChild("email").equalTo(email); //return the child node of "User" whose "email"==email
//        //the .orderByChild() in the prev line doesnt affect where the snapshot will be pointed to!
//        //snapshot will be the path ~\User in the db, NOT ~\User\[key of the user with "email"==email]\email
//        //.orderByChild("email").equalTo(email) will give the snapshot of ~\User and only its children whose "email"==email will be included
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d(TAG, "Key of this snapshot is: "+snapshot.getKey()); //key (last part of the path of the location of the snapshot)=User
//                if (snapshot.exists()) {
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        String mobile = dataSnapshot.child("mobile").getValue().toString();
//                        ds.setStr(mobile);
////                        Log.d(TAG, "mobile: " + ds.getStr());
//                    }
//                }
////                Log.d(TAG, "mobile: "+ds.getStr());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//
//        return ds.getStr();
//        //use this to check if data has been retrieved: email=ds.retrieveMobileByEmail("target email"); while (email==="error") {email=ds.retrieveMobileByEmail("target email");}
//    }


    //retrieve total number of bookings from db
    ////NOTE!!! when calling this method, make sure to do what's stated beside the return "error"; statement!
//    public long retrieveBookingCount() {
//        ds.setLongNum(-1);  //set as -1 to check if it has changed to the correct bookingCount
//        reff = FirebaseDatabase.getInstance().getReference().child("Booking");
//        reff.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    ds.setLongNum(snapshot.getChildrenCount());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//
//        return ds.getLongNum();
//        //use this to check if data has been retrieved: count=ds.retrieveBookingCount(); while (count==="-1") {count=ds.retrieveBookingCount();}
//    }





    //retrieve all lockers of a structure using structureID
//    public ArrayList<Locker> retrieveAvailLockers(int structureID) {
//        ArrayList<Locker> lockerList = new ArrayList<Locker>();
//        Locker dummyLocker = new Locker();
//        dummyLocker.setSize('E');
//        lockerList.add(dummyLocker);
//        ds.setLockerList(lockerList);
//        Query query = FirebaseDatabase.getInstance().getReference().child("LockerStructure").orderByChild("postalCode").equalTo(postalCode);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    ds.getStructureList().clear();
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        LockerStructure lockerStructure = dataSnapshot.getValue(LockerStructure.class);
//                        ds.getStructureList().add(lockerStructure);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        return ds.getStructureList();
//        //use this to check if data has been retrieved: ArrayList<LockerStructure> structureList = ds.retrieveStructureByPostalCode("123456"); while (structureList.get(0).getAddress()==="error") {structureList=ds.retrieveStructureByPostalCode("123456");}
//    }







    /*@RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate retrieveBookingStartDate(String email, int lockerID, int lockerStructureID){
        //database query to retrieve the date of booking
        LocalDate date = LocalDate.now(); //dummy value
        return date;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalTime retrieveBookingStartTime(String email, int lockerID, int lockerStructureID){
        //database query to retrieve the time of booking
        LocalTime time = LocalTime.now(); //dummy value
        return time;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate retrieveBookingEndDate(String email, int lockerID, int lockerStructureID){
        //database query to retrieve the date of booking
        LocalDate date = LocalDate.now(); //dummy value
        return date;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalTime retrieveBookingEndTime(String email, int lockerID, int lockerStructureID){
        //database query to retrieve the time of booking
        LocalTime time = LocalTime.now(); //dummy value
        return time;
    }*/


    // TODO all these methods below

    public void setBookingStatus(String mobile, long structureID, long lockerID, LocalDate startDate,
                                 LocalTime startTime, LocalDate endDate, LocalTime endTime, char newStatus){
        Booking booking = new Booking(startDate, startTime, endDate, endTime, mobile, structureID, lockerID, newStatus);
        ds.setBooking(booking);

        Query query = FirebaseDatabase.getInstance().getReference().child("Booking").orderByChild("mobile").equalTo(mobile);
        Log.d(TAG, "setBookingStatus: after setting query");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: inside on datachange");
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (LocalDate.parse(dataSnapshot.child("startDate").getValue().toString()).compareTo(ds.getBooking().getStartDate())==0) {
                        Log.d(TAG, "onDataChange: same startDate");
                        if (LocalDate.parse(dataSnapshot.child("endDate").getValue().toString()).compareTo(ds.getBooking().getEndDate())==0) {
                            Log.d(TAG, "onDataChange: same endDate");
                            if (LocalTime.parse(dataSnapshot.child("startTime").getValue().toString()).compareTo(ds.getBooking().getStartTime()) == 0) {
                                Log.d(TAG, "onDataChange: same startTime");
                                if (LocalTime.parse(dataSnapshot.child("endTime").getValue().toString()).compareTo(ds.getBooking().getEndTime()) == 0) {
                                    Log.d(TAG, "onDataChange: same endTime");
                                    if (Long.parseLong(dataSnapshot.child("structureID").getValue().toString())==ds.getBooking().getStructureID()) {
                                        Log.d(TAG, "onDataChange: same structureID");
                                        if (Long.parseLong(dataSnapshot.child("lockerID").getValue().toString())==ds.getBooking().getLockerID()) {
                                            Log.d(TAG, "onDataChange: same lockerID");
                                            String key = dataSnapshot.getKey();
                                            Log.d(TAG, "onDataChange: key of the matching booking=" +key);
                                            Log.d(TAG, "onDataChange: old status=" + dataSnapshot.child("status").getValue().toString());
                                            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Booking");
                                            Map<String, Object> statusUpdate = new HashMap<>();
                                            statusUpdate.put("status", Character.toString(ds.getBooking().getStatus()));
                                            reff.child(key).updateChildren(statusUpdate);
                                            query.removeEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {}
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {}
                                            });
//                                            Log.d(TAG, "onDataChange: new status=" + dataSnapshot.child("status").getValue().toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: oncancelled");
            }
        });
    }


    public void setLockerStatus(long structureID, long lockerID){
        ds.setStructureID(structureID);
        ds.setLockerID(lockerID);

        Query query = FirebaseDatabase.getInstance().getReference().child("LockerStructure").child(structureID+"").child("Locker");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
//                    Log.d(TAG, "onDataChange: snapshot exists");
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (Long.parseLong(dataSnapshot.child("lockerID").getValue().toString())==ds.getLockerID()) {
                            Log.d(TAG, "onDataChange: old value=" +dataSnapshot.child("isLocked").getValue());
                            ds.setIsLocked((Boolean) dataSnapshot.child("isLocked").getValue());
                            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("LockerStructure").child(ds.getStructureID()+"").child("Locker");
                            Map<String, Object> lockerStatus = new HashMap<>();
                            if (ds.getIsLocked()) {
                                lockerStatus.put("isLocked", false);
                            }
                            else if (!ds.getIsLocked()) {
                                lockerStatus.put("isLocked", true);
                            }
                            reff.child(ds.getLockerID()+"").updateChildren(lockerStatus);
                            Log.d(TAG, "onDataChange: new value=" +dataSnapshot.child("isLocked").getValue());
                            query.removeEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {}
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createBooking(String mobile, long structureID, long lockerID,
                              LocalDate startDate, LocalTime startTime,
                              LocalDate endDate, LocalTime endTime, char status) {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Booking").push();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        reff.child("mobile").setValue(mobile);
        reff.child("structureID").setValue(structureID);
        reff.child("lockerID").setValue(lockerID);
        reff.child("startDate").setValue(startDate.toString());
        reff.child("startTime").setValue(startTime.format(timeFormatter));
        reff.child("endDate").setValue(endDate.toString());
        reff.child("endTime").setValue(endTime.format(timeFormatter));
        reff.child("status").setValue(Character.toString(status));
        Log.d(TAG, "createBooking: booking created");
    }


    //method to update wallet balance
    public void updateWalletBalance(String mobile, float newBalance) {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("User");
        Map<String, Object> walletUpdate = new HashMap<>();
        walletUpdate.put("walletBalance", newBalance);
        reff.child(mobile).updateChildren(walletUpdate);   //update walletBalance of user with mobile
    }

    //method to update lateFees
    public void updateLateFees(String mobile, float newLateFees) {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("User");
        Map<String, Object> lateFeesUpdate = new HashMap<>();
        lateFeesUpdate.put("lateFees", newLateFees);
        reff.child(mobile).updateChildren(lateFeesUpdate);
    }

    // TODO edit profile methods
    public void setNewEmail(String mobile, String newEmail) {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("User");
        Map<String, Object> emailUpdate = new HashMap<>();
        emailUpdate.put("email", newEmail);
        reff.child(mobile).updateChildren(emailUpdate);
    }

    public void setNewName(String mobile, String name) {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("User");
        Map<String, Object> nameUpdate = new HashMap<>();
        nameUpdate.put("name", name);
        reff.child(mobile).updateChildren(nameUpdate);
    }

    public void setNewMobile(String oldMobile, String name, String email, String newMobile, float walletBalance, float lateFees) {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("User");
        reff.child(oldMobile).removeValue();
        Map<String, Object> recreateUser = new HashMap<>();
        recreateUser.put("name", name);
        recreateUser.put("email", email);
        recreateUser.put("mobile", newMobile);
        recreateUser.put("walletBalance", walletBalance);
        recreateUser.put("lateFees", lateFees);
        reff.child(newMobile).updateChildren(recreateUser);
    }

    // get size (in character) using structureID+lockerID
    public char retrieveLockerSize(long structureID, long lockerID) {
        ds.setLockerID(lockerID);
        ds.setLockerSize('a');
        Query query = FirebaseDatabase.getInstance().getReference().child("LockerStructure").orderByChild("structureID").equalTo(structureID);
        query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ArrayList<HashMap<String, Object>> lockerList = (ArrayList<HashMap<String, Object>>) dataSnapshot.child("Locker").getValue();
                        if (lockerList.get(0) == null) {
                            lockerList.remove(0);
                        }
//                        Log.d(TAG, "onDataChange: hashmap: " +lockerList.get(1).get("lockerID"));
                        for (HashMap<String, Object> locker : lockerList) {
                            if (Long.parseLong(locker.get("lockerID").toString())==ds.getLockerID()) {
                                ds.setLockerSize(locker.get("size").toString().charAt(0));
                            }
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return ds.getLockerSize();
    }

    // get locationName using structureID
    public String retrieveLocationName(long structureID) {
        Query query = FirebaseDatabase.getInstance().getReference().child("LockerStructure").orderByChild("structureID").equalTo(structureID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            ds.setLocationName(dataSnapshot.child("locationName").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return ds.getLocationName();
    }



//    //pls return me a list of lockerID filtered by locker size thanks
//    public ArrayList<Integer> retrieveLockerIDFilterByLockerSize(int lockerStructureID, int lockerSize){
//        ArrayList<Integer> dummy  = new ArrayList<Integer>();
//        return dummy;
//    }


    //output: list of locker ids of lockers which match the desired size which are avail for the booking selection
    //input: startDate, endDate, startTime, endTime, postalCode, lockerSize

    //two db calls to retrieve:
    //1) bookings with b
    //retrieve all bookings with status=='B'
    public ArrayList<Booking> retrieveBBookings() {
        ds.setBBookingList(new ArrayList<Booking>());
        Query query = FirebaseDatabase.getInstance().getReference().child("Booking").orderByChild("status").equalTo("B");
        query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Booking booking = new Booking();
                        booking.setStartDate(LocalDate.parse(dataSnapshot.child("startDate").getValue().toString()));
                        booking.setEndDate(LocalDate.parse(dataSnapshot.child("endDate").getValue().toString()));
                        booking.setStartTime(LocalTime.parse(dataSnapshot.child("startTime").getValue().toString()));
                        booking.setEndTime(LocalTime.parse(dataSnapshot.child("endTime").getValue().toString()));
                        booking.setMobile(dataSnapshot.child("mobile").getValue().toString());
                        booking.setStructureID((Long) dataSnapshot.child("structureID").getValue());
                        booking.setLockerID((Long) dataSnapshot.child("lockerID").getValue());
                        booking.setStatus(dataSnapshot.child("status").getValue().toString().charAt(0));
                        ds.getBBookingList().add(booking);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return ds.getBBookingList();
    }

    //2) lockerIDs of lockers from lockerStructure with postal code == postalCode and of locker size == lockerSize
    //retrieve all LockerStructures from db that has a certain postalCode --> need to change to get the lockers
    public ArrayList<Locker> retrieveMatchingLockers(String postalCode, char lockerSize) {
        ds.setLockerSize(lockerSize);
        Query query = FirebaseDatabase.getInstance().getReference().child("LockerStructure").orderByChild("postalCode").equalTo(postalCode);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d(TAG, "onDataChange: in onDataChange of first query");
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        long structureID = (long) dataSnapshot.child("structureID").getValue();
//                        ds.setStructureID(structureID);
//                        Log.d(TAG, "onDataChange: End of first query");
//                        Log.d(TAG, "onDataChange: structureID = " +structureID+"");
//                        Log.d(TAG, "onDataChange: Locker structure: "+dataSnapshot.child("Locker").getValue());

                        ArrayList<HashMap<String, Object>> lockerList = new ArrayList<HashMap<String, Object>>();
                        lockerList = (ArrayList<HashMap<String, Object>>) dataSnapshot.child("Locker").getValue();
                        if (lockerList.get(0) == null) {
                            lockerList.remove(0);
                        }
//                        Log.d(TAG, "onDataChange: hashmap: " +lockerList.get(1).get("lockerID"));
                        for (HashMap<String, Object> locker : lockerList) {
                            if (locker.get("size").equals(Character.toString(ds.getLockerSize()))) {
                                Locker matchingLocker = new Locker();
                                matchingLocker.setStructureID(structureID);
                                matchingLocker.setLockerID((Long) locker.get("lockerID"));
                                ds.getLockerList().add(matchingLocker);
//                                Log.d(TAG, "structureID: " +matchingLocker.getStructureID()+"");
//                                Log.d(TAG, "lockerID: " +matchingLocker.getLockerID()+"");
                            }
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return ds.getLockerList();
    }

    public long retrieveUserBookingCount(String mobile) {
        ds.setUserBookingCount(-1);
        Query query = FirebaseDatabase.getInstance().getReference().child("Booking").orderByChild("mobile").equalTo(mobile);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ds.setUserBookingCount(snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return ds.getUserBookingCount();
    }


    public ArrayList<Booking> retrieveRBookingsForUser(String mobile) {
        ds.setMobile(mobile);
        ds.setUserRBookingList(new ArrayList<Booking>());
        Query query = FirebaseDatabase.getInstance().getReference().child("Booking").orderByChild("status").equalTo("R");
        query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Booking booking = new Booking();
                        booking.setStartDate(LocalDate.parse(dataSnapshot.child("startDate").getValue().toString()));
                        booking.setEndDate(LocalDate.parse(dataSnapshot.child("endDate").getValue().toString()));
                        booking.setStartTime(LocalTime.parse(dataSnapshot.child("startTime").getValue().toString()));
                        booking.setEndTime(LocalTime.parse(dataSnapshot.child("endTime").getValue().toString()));
                        booking.setMobile(dataSnapshot.child("mobile").getValue().toString());
                        booking.setStructureID((Long) dataSnapshot.child("structureID").getValue());
                        booking.setLockerID((Long) dataSnapshot.child("lockerID").getValue());
                        booking.setStatus(dataSnapshot.child("status").getValue().toString().charAt(0));
                        Log.d(TAG, "onDataChange: " +booking);
                        if (booking.getMobile().equals(ds.getMobile())) {
                            ds.getUserRBookingList().add(booking);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return ds.getUserRBookingList();
    }


    public ArrayList<Booking> retrieveCBookingsForUser(String mobile) {
        ds.setMobile(mobile);
        ds.setUserCBookingList(new ArrayList<Booking>());
        Query query = FirebaseDatabase.getInstance().getReference().child("Booking").orderByChild("status").equalTo("C");
        query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Booking booking = new Booking();
                        booking.setStartDate(LocalDate.parse(dataSnapshot.child("startDate").getValue().toString()));
                        booking.setEndDate(LocalDate.parse(dataSnapshot.child("endDate").getValue().toString()));
                        booking.setStartTime(LocalTime.parse(dataSnapshot.child("startTime").getValue().toString()));
                        booking.setEndTime(LocalTime.parse(dataSnapshot.child("endTime").getValue().toString()));
                        booking.setMobile(dataSnapshot.child("mobile").getValue().toString());
                        booking.setStructureID((Long) dataSnapshot.child("structureID").getValue());
                        booking.setLockerID((Long) dataSnapshot.child("lockerID").getValue());
                        booking.setStatus(dataSnapshot.child("status").getValue().toString().charAt(0));
                        if (booking.getMobile().equals(ds.getMobile())) {
                            ds.getUserCBookingList().add(booking);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return ds.getUserCBookingList();
    }


//    public ArrayList<Booking> retrieveOBookingsForUser(String mobile) {
//        ds.setMobile(mobile);
//        ds.setUserOBookingList(new ArrayList<Booking>());
//        Query query = FirebaseDatabase.getInstance().getReference().child("Booking").orderByChild("status").equalTo("B");
//        query.addValueEventListener(new ValueEventListener() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    LocalDate currDate = java.time.LocalDate.now();
//                    LocalTime currTime = java.time.LocalTime.now();
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        Booking booking = new Booking();
//                        booking.setStartDate(LocalDate.parse(dataSnapshot.child("startDate").getValue().toString()));
//                        booking.setEndDate(LocalDate.parse(dataSnapshot.child("endDate").getValue().toString()));
//                        booking.setStartTime(LocalTime.parse(dataSnapshot.child("startTime").getValue().toString()));
//                        booking.setEndTime(LocalTime.parse(dataSnapshot.child("endTime").getValue().toString()));
//                        booking.setMobile(dataSnapshot.child("mobile").getValue().toString());
//                        booking.setStructureID((Long) dataSnapshot.child("structureID").getValue());
//                        booking.setLockerID((Long) dataSnapshot.child("lockerID").getValue());
//                        booking.setStatus(dataSnapshot.child("status").getValue().toString().charAt(0));
//                        if (booking.getMobile().equals(ds.getMobile())) {
//                            if ((currDate.compareTo(booking.getStartDate())>=0) && (currDate.compareTo(booking.getEndDate())<=0)) {
//                                if ((currTime.compareTo(booking.getStartTime())>=0) && (currTime.compareTo(booking.getEndTime())<=0)) {
//                                    ds.getUserOBookingList().add(booking);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        return ds.getUserOBookingList();
//    }

    public ArrayList<Booking> retrieveBBookingsForUser(String mobile) {
        ds.setMobile(mobile);
        ds.setUserBBookingList(new ArrayList<Booking>());
        Query query = FirebaseDatabase.getInstance().getReference().child("Booking").orderByChild("status").equalTo("B");
        query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Booking booking = new Booking();
                        booking.setStartDate(LocalDate.parse(dataSnapshot.child("startDate").getValue().toString()));
                        booking.setEndDate(LocalDate.parse(dataSnapshot.child("endDate").getValue().toString()));
                        booking.setStartTime(LocalTime.parse(dataSnapshot.child("startTime").getValue().toString()));
                        booking.setEndTime(LocalTime.parse(dataSnapshot.child("endTime").getValue().toString()));
                        booking.setMobile(dataSnapshot.child("mobile").getValue().toString());
                        booking.setStructureID((Long) dataSnapshot.child("structureID").getValue());
                        booking.setLockerID((Long) dataSnapshot.child("lockerID").getValue());
                        booking.setStatus(dataSnapshot.child("status").getValue().toString().charAt(0));
                        if (booking.getMobile().equals(ds.getMobile())) {
                            ds.getUserBBookingList().add(booking);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return ds.getUserBBookingList();
    }

}