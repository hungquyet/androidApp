package com.example.callapitourdulich.response;

import com.example.callapitourdulich.model.Tour;

import java.util.List;

public class TourResponse {
    private List<Tour> tourResponses;
    private int totalPages;

    public List<Tour> getTourResponses() {
        return tourResponses;
    }

    public void setTourResponses(List<Tour> tourResponses) {
        this.tourResponses = tourResponses;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
