package com.example.callapitourdulich.model;

import com.example.callapitourdulich.enums.TourType;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tour implements Serializable {
    private int id;

    private String tour_name;

    private String description;
    private float price;
    private String days;

    @SerializedName(("startDate"))
    private String start_date;

    private String destination;

    @SerializedName("departureLocation")
    private String departure_location;
    private String status;
    private String content;
    private String imageHeader;
    private TourType tour_type;

    public Tour(int id, String tour_name, String description, float price,
                String days, String start_date, String destination,
                String departure_location, String status, String content, String image_header,
                TourType tour_type) {
        this.id = id;
        this.tour_name = tour_name;
        this.description = description;
        this.price = price;
        this.days = days;
        this.start_date = start_date;
        this.destination = destination;
        this.departure_location = departure_location;
        this.status = status;
        this.content = content;
        this.imageHeader = image_header;
        this.tour_type = tour_type;
    }

    public Tour(){};

    public TourType getTour_type() {
        return tour_type;
    }

    public void setTour_type(TourType tour_type) {
        this.tour_type = tour_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture_location() {
        return departure_location;
    }

    public void setDeparture_location(String departure_location) {
        this.departure_location = departure_location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageHeader() {
        return imageHeader;
    }

    public void setImageHeader(String imageHeader) {
        this.imageHeader = imageHeader;
    }

    public String getTour_name() {
        return tour_name;
    }

    public void setTour_name(String tour_name) {
        this.tour_name = tour_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
