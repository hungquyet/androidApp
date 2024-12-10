package com.example.callapitourdulich.api;

import com.example.callapitourdulich.dto.BookingDTO;
import com.example.callapitourdulich.response.BookedTourResponse;
import com.example.callapitourdulich.response.BookingResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookingApi {
    @POST("bookings")
    Call<BookingResponse> createBooking(@Header("Authorization") String token,
                                        @Body BookingDTO bookingDTO);

    @GET("bookings/user/{userId}")
    Call<List<BookedTourResponse>> getBookingByUserId(
            @Header("Authorization") String token,
            @Path("userId") int userId
    );
}
