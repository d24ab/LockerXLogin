package com.example.lockerxlogin.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lockerxlogin.AvailableLockers;
import com.example.lockerxlogin.Booking;
import com.example.lockerxlogin.BookingHistoryArr;
import com.example.lockerxlogin.BookingInfo;
import com.example.lockerxlogin.Locker;
import com.example.lockerxlogin.R;
import com.example.lockerxlogin.Register;
import com.example.lockerxlogin.ui.BookingSearchArr;

import java.util.ArrayList;



public class BookingSearchArrAdapter extends RecyclerView.Adapter<BookingSearchRecyclerViewHolder> {
    private ArrayList<Locker> availablelockers;
    //public static Long StructureID, LockerID;

    public BookingSearchArrAdapter(ArrayList<Locker> availableLocker) {
        this.availablelockers =  availableLocker;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.booking_search_item;
    }

    @NonNull
    @Override
    public BookingSearchRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new BookingSearchRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingSearchRecyclerViewHolder holder, int position) {
        Locker bsa = availablelockers.get(position);
        holder.getStructureID().setText("Structure id: " + bsa.getStructureID());
        holder.getLockerID().setText("Locker id: " + bsa.getLockerID());
        holder.itemView.setOnClickListener(v -> {
            Context mContext = v.getContext();
            final Intent intent;
            intent =  new Intent(mContext, BookingInfo.class);
            Log.d("Tag",bsa.getLockerID() + " THE LOCKER ID");
            Log.d("TAG", bsa.getStructureID() + " THE STRUCTURE ID");


            intent.putExtra("LockerID", bsa.getLockerID());
            intent.putExtra("StructureID", bsa.getStructureID());
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return availablelockers.size();
    }

}
