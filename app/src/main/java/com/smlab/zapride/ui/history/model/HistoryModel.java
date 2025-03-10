package com.smlab.zapride.ui.history.model;

public class HistoryModel {
    private String dateTime, bookingId, pickupLocation, dropoffLocation, status;
    int price;

    public HistoryModel(String dateTime, int price, String bookingId, String pickupLocation, String dropoffLocation, String status) {
        this.dateTime = dateTime;
        this.price = price;
        this.bookingId = bookingId;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.status = String.valueOf(status);
    }

    public String getDateTime() { return dateTime; }
    public int getPrice() { return price; }
    public String getBookingId() { return bookingId; }
    public String getPickupLocation() { return pickupLocation; }
    public String getDropoffLocation() { return dropoffLocation; }
    public String getStatus() { return status; }
}
