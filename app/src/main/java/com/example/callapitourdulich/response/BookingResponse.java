package com.example.callapitourdulich.response;

import com.example.callapitourdulich.model.Tour;
import com.example.callapitourdulich.model.User;

public class BookingResponse {
    private int id;
    private User userId;
    private String fullName;
    private String phoneNumber;
    private Tour tourId;
    private String tourName;
    private int amount;
    private String startDate;
    private double totalPrice;
    private String status;
    private String notes;
    private String bookingTime;

    public BookingResponse(int id, User userId, String fullName, String phoneNumber, Tour tourId,
                           String tourName, int amount, String startDate, double totalPrice, String status, String notes, String bookingTime) {
        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.tourId = tourId;
        this.tourName = tourName;
        this.amount = amount;
        this.startDate = startDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.notes = notes;
        this.bookingTime = bookingTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Tour getTourId() {
        return tourId;
    }

    public void setTourId(Tour tourId) {
        this.tourId = tourId;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }
}
