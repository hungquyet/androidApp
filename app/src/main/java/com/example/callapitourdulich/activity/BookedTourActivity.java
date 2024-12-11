package com.example.callapitourdulich.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callapitourdulich.R;
import com.example.callapitourdulich.adapter.BookedTourAdapter;
import com.example.callapitourdulich.api.BookingApi;
import com.example.callapitourdulich.response.BookedTourResponse;
import com.example.callapitourdulich.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookedTourActivity extends AppCompatActivity {
    private RecyclerView rcv_bookedTour;
    private ImageView img_exit;
    private BookedTourAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booked_tour);

        // Áp dụng padding cho các view để tránh đè lên system bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        initUI();
        fetchBookedTours();
    }

    private void initUI() {
        rcv_bookedTour = findViewById(R.id.rcv_bookedTour);
        img_exit = findViewById(R.id.img_exit);

        // Sự kiện click vào nút thoát
        img_exit.setOnClickListener(view -> finish());

        // Cấu hình RecyclerView với layout dọc
        rcv_bookedTour.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fetchBookedTours() {
        // Lấy RetrofitService và BookingApi
        RetrofitService retrofitService = new RetrofitService();
        BookingApi bookingApi = retrofitService.getRetrofit().create(BookingApi.class);

        // Lấy token và userId từ intent hoặc shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        int userId = sharedPreferences.getInt("user_id", -1);

        if (token == null || userId == -1) {
            Toast.makeText(this, "Thiếu thông tin token hoặc userId", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gửi request lấy danh sách các tour đã đặt
        // Gửi request lấy danh sách các tour đã đặt
        bookingApi.getBookingByUserId("Bearer " + token, userId).enqueue(new Callback<List<BookedTourResponse>>() {
            @Override
            public void onResponse(Call<List<BookedTourResponse>> call, Response<List<BookedTourResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<BookedTourResponse> bookedTourList = response.body();
                    // Set adapter cho RecyclerView
                    adapter = new BookedTourAdapter(BookedTourActivity.this, bookedTourList);
                    rcv_bookedTour.setAdapter(adapter);
                } else {
                    // Log lỗi khi response không thành công
                    Log.e("BookedTourActivity", "API Error: " + response.code() + " - " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("BookedTourActivity", "Error Body: " + errorResponse);
                        } catch (Exception e) {
                            Log.e("BookedTourActivity", "Error parsing error body", e);
                        }
                    }
                    Toast.makeText(BookedTourActivity.this, "Lỗi: Không có dữ liệu hoặc API không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BookedTourResponse>> call, Throwable t) {
                Log.e("BookedTourActivity", "API Call Failed", t);
                Toast.makeText(BookedTourActivity.this, "Lỗi khi gọi API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
