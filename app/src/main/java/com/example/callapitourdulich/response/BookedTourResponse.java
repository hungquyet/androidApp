package com.example.callapitourdulich.response;

import java.time.LocalDateTime;
import java.util.Date;

public class BookedTourResponse {
    private int id;
    private int userId;
    private String tour_name;
    private String full_name;
    private String phone_number;
    private float total_price;
    private String payment_method;
    private String booking_time;
    private Date start_date;
    private int amount;
    private String notes;
    private String status;

    public BookedTourResponse(int id, int userId, String tour_name, String full_name, String phone_number,
                              float total_price, String payment_method, String booking_time, Date start_date, int amount, String notes, String status) {
        this.id = id;
        this.userId = userId;
        this.tour_name = tour_name;
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.total_price = total_price;
        this.payment_method = payment_method;
        this.booking_time = booking_time;
        this.start_date = start_date;
        this.amount = amount;
        this.notes = notes;
        this.status = status;
    }

    public BookedTourResponse(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTour_name() {
        return tour_name;
    }

    public void setTour_name(String tour_name) {
        this.tour_name = tour_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(String booking_time) {
        this.booking_time = booking_time;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
