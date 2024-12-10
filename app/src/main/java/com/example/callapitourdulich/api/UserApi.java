package com.example.callapitourdulich.api;

import com.example.callapitourdulich.dto.UserDTO;
import com.example.callapitourdulich.response.LoginResponse;
import com.example.callapitourdulich.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {

    //Call<kiểu dữ liệu trả về>
    @POST("users/register")
    Call<UserDTO> register(@Body UserDTO userDTO);

    @POST("users/login")
    Call<LoginResponse> login(@Body UserDTO userDTO);

    @POST("users/details")
    Call<UserResponse> getUserDetails(@Header("Authorization") String token);

    @PUT("users/{id}")
    Call<UserResponse> updateUserDetails(
            @Path("id") int id,
            @Header("Authorization") String token,
            @Body UserResponse userResponse
    );
}
