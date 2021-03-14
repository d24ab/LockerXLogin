package com.example.lockerxlogin.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lockerxlogin.R;


public class BookingSearchRecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView lockerID, structureID;

    public BookingSearchRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        structureID = itemView.findViewById(R.id.search_locker_structure_id_tv);
        lockerID = itemView.findViewById(R.id.search_locker_id_item_tv);
    }


    public TextView getStructureID() {
        return structureID;
    }

    public TextView getLockerID() { return lockerID; }
}
