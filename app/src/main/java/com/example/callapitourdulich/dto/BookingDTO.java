package com.example.callapitourdulich.dto;


import java.util.Date;

public class BookingDTO {
    private int user_id;
    private int tour_id;
    private String tour_name;
    private String full_name;
    private String phone_number;
    private int amount;
    private String notes;
    private String start_date;
    private String status;

    public BookingDTO(int user_id, int tour_id, String tour_name, String full_name,
                      String phone_number, int amount, String notes, String start_date, String status) {
        this.user_id = user_id;
        this.tour_id = tour_id;
        this.tour_name = tour_name;
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.amount = amount;
        this.notes = notes;
        this.start_date = start_date;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTour_id() {
        return tour_id;
    }

    public void setTour_id(int tour_id) {
        this.tour_id = tour_id;
    }

    public String getTour_name() {
        return tour_name;
    }

    public void setTour_name(String tour_name) {
        this.tour_name = tour_name;
    }

    public BookingDTO(){}

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
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

    public float getAmount() {
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
}
