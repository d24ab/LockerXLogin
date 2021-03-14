package com.example.lockerxlogin.ui;

public class BookingSearchArr {
    private Long lockerID, structureID;

    public BookingSearchArr(Long lockerID, Long structureID) {

        this.lockerID = lockerID;
        this.structureID = structureID;
    }

    public void setLockerID(Long lockerID) {
        this.lockerID = lockerID;
    }

    public Long getLockerID() { return lockerID; }

    public Long getStructureID() {
        return structureID;
    }

    public void setStructureID(Long structureID) {
        this.structureID = structureID;
    }


}
