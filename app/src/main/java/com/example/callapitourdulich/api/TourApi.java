package com.example.callapitourdulich.api;

import com.example.callapitourdulich.model.Tour;
import com.example.callapitourdulich.model.TourDetailImage;
import com.example.callapitourdulich.model.TourImage;
import com.example.callapitourdulich.response.TourDetailResponse;
import com.example.callapitourdulich.response.TourResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TourApi {

    @GET("tours")
    Call<TourResponse> getAllTours(@Query("page") int page, @Query("limit") int limit);

    @POST("tour")
    Call<Tour> createTours(@Body Tour tour);

    @GET("tours/{id}")
    Call<TourDetailResponse> getTourDetails(@Path("id") int id);

    @GET("images/user/{id}")
    Call<List<TourDetailImage>> getTourDetailImages(@Path("id") int tourId);

    @GET("images/full/{imgUrl}")
    Call<ResponseBody> getImage(@Path("imgUrl") String imgUrl);
}

