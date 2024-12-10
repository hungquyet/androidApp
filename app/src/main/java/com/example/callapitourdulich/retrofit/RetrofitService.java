package com.example.callapitourdulich.retrofit;

import com.example.callapitourdulich.deserializer.RoleDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;

    public RetrofitService() {
        initializeRetrofit();
    }

    private void initializeRetrofit() {
        // Tạo một Gson instance với custom deserializer cho roleId và setLenient
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(String.class, new RoleDeserializer()) // Đăng ký custom deserializer
                .setLenient() // Chấp nhận JSON không đúng định dạng
                .create();



        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8088/api/v1/") // Cấu hình base URL
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
