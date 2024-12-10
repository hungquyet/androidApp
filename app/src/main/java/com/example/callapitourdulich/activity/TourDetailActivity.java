package com.example.callapitourdulich.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.callapitourdulich.R;
import com.example.callapitourdulich.adapter.TourDetailImageAdapter;
import com.example.callapitourdulich.api.BookingApi;
import com.example.callapitourdulich.api.TourApi;
import com.example.callapitourdulich.dto.BookingDTO;
import com.example.callapitourdulich.model.Tour;
import com.example.callapitourdulich.model.TourDetailImage;
import com.example.callapitourdulich.response.BookingResponse;
import com.example.callapitourdulich.response.TourDetailResponse;
import com.example.callapitourdulich.retrofit.RetrofitService;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourDetailActivity extends AppCompatActivity {

    private ImageView img_header, img_back;
    private TextView tv_name, tv_departureLocation, tv_description, tv_destination, tv_startDate, tv_days, tv_idTour, tv_price;
    private Button btn_bookTour, btn_completeBooking;
    private RecyclerView rcv_imageTourDetail;
    private TourDetailImageAdapter tourDetailImageAdapter;
    private TextInputEditText edt_name, edt_phoneNumber, edt_note, edt_amount;
    private String tourName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tour_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initUI();

        // Nhận ID tour từ Intent
        int tourId = getIntent().getIntExtra("tour_id", -1);
        if (tourId != -1) {
            loadTourDetails(tourId);
            fetchTourImages(tourId);
        } else {
            Toast.makeText(this, "ID tour không hợp lệ", Toast.LENGTH_SHORT).show();
        }
        // Nhận tour_name từ Intent

    }

    private void initUI() {
        img_header = findViewById(R.id.img_header);
        img_back = findViewById(R.id.img_back);
        tv_name = findViewById(R.id.tv_nameTourDetail);
        tv_departureLocation = findViewById(R.id.tv_departureLocation);
        tv_description = findViewById(R.id.tv_description);
        tv_destination = findViewById(R.id.tv_destination);
        tv_startDate = findViewById(R.id.tv_startDate);
        tv_days = findViewById(R.id.tv_days);
        tv_idTour = findViewById(R.id.tv_idTour);
        tv_price = findViewById(R.id.tv_price);
        btn_bookTour = findViewById(R.id.btn_bookTour);
        rcv_imageTourDetail = findViewById(R.id.rcv_imageTourDetail);
        btn_bookTour = findViewById(R.id.btn_bookTour);


        btn_bookTour.setOnClickListener(view -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(TourDetailActivity.this, R.style.RoundedBottomSheetDialog);
            bottomSheetDialog.setContentView(R.layout.dialog_booking);
            bottomSheetDialog.show();

            btn_completeBooking = bottomSheetDialog.findViewById(R.id.btn_completeBooking);
            edt_amount = bottomSheetDialog.findViewById(R.id.edt_amount);
            edt_phoneNumber = bottomSheetDialog.findViewById(R.id.edt_phoneNumber);
            edt_note = bottomSheetDialog.findViewById(R.id.edt_note);
            edt_name = bottomSheetDialog.findViewById(R.id.edt_name);

            btn_completeBooking.setOnClickListener(view1 -> {
                String name = edt_name.getText().toString().trim();
                String phoneNumber = edt_phoneNumber.getText().toString().trim();
                String note = edt_note.getText().toString().trim();
                String amountStr = edt_amount.getText().toString().trim();

                // Nhận user_id, tour_id, tour_name từ Intent
                int tourId = getIntent().getIntExtra("tour_id", -1);

                // Kiểm tra các trường không để trống (nếu cần)
                if (name.isEmpty() || phoneNumber.isEmpty() || amountStr.isEmpty()) {
                    Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                int amount = Integer.parseInt(amountStr);

                // Lấy token từ SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", null);
                int userId = sharedPreferences.getInt("user_id", -1); // -1 là giá trị mặc định nếu không tìm thấy

                if (token == null) {
                    Toast.makeText(this, "Token không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lấy ngày hiện tại và định dạng thành chuỗi
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String startDate = dateFormat.format(new Date());

                // Tạo đối tượng BookingDTO
                BookingDTO bookingDTO = new BookingDTO();
                bookingDTO.setFull_name(name);
                bookingDTO.setPhone_number(phoneNumber);
                bookingDTO.setNotes(note);
                bookingDTO.setAmount(amount);
                bookingDTO.setStart_date(startDate);
                bookingDTO.setTour_id(tourId);
                bookingDTO.setTour_name(tourName);
                if (userId != -1) {
                    // Sử dụng userId
                    bookingDTO.setUser_id(userId);
                    Log.d("TourDetailActivity", "User ID: " + userId);
                }


                // Gọi API createBooking
                RetrofitService retrofitService = new RetrofitService();
                BookingApi bookingApi = retrofitService.getRetrofit().create(BookingApi.class);

                bookingApi.createBooking("Bearer " + token, bookingDTO).enqueue(new Callback<BookingResponse>() {
                    @Override
                    public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(TourDetailActivity.this, "Đặt tour thành công!", Toast.LENGTH_SHORT).show();
                            bottomSheetDialog.dismiss(); // Đóng BottomSheetDialog sau khi đặt thành công
                        } else {
                            Log.e("BookingError", "Error Code: " + response.code() + " - " + response.message());
                            Log.d("BookingDTO", new Gson().toJson(bookingDTO));
                            Toast.makeText(TourDetailActivity.this, "Đặt tour thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookingResponse> call, Throwable t) {
                        Log.e("API_ERROR", "Lỗi khi gọi API tạo booking: ", t);
                        Toast.makeText(TourDetailActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });
            });

        });

        img_back.setOnClickListener(view -> {
            finish();
        });

        // Cấu hình RecyclerView
        tourDetailImageAdapter = new TourDetailImageAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcv_imageTourDetail.setLayoutManager(layoutManager);
        rcv_imageTourDetail.setAdapter(tourDetailImageAdapter);
    }

    private void loadTourDetails(int id) {
        RetrofitService retrofitService = new RetrofitService();
        TourApi tourApi = retrofitService.getRetrofit().create(TourApi.class);

        tourApi.getTourDetails(id).enqueue(new Callback<TourDetailResponse>() {
            @Override
            public void onResponse(Call<TourDetailResponse> call, Response<TourDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TourDetailResponse tourDetailResponse = response.body();

                    tourName = tourDetailResponse.getTour_name();

                    populateDetailView(tourDetailResponse);
                } else {
                    Toast.makeText(TourDetailActivity.this, "Không thể tải chi tiết tour", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TourDetailResponse> call, Throwable throwable) {
                Log.e("API_ERROR", "Lỗi khi gọi API chi tiết tour: ", throwable);
            }
        });
    }

    private void populateDetailView(TourDetailResponse tourDetailResponse) {
        tv_name.setText(tourDetailResponse.getTour_name());
        tv_departureLocation.setText(tourDetailResponse.getDeparture_location());
        tv_description.setText(tourDetailResponse.getDescription());
        tv_destination.setText(tourDetailResponse.getDestination());
        tv_startDate.setText(tourDetailResponse.getStart_date());
        tv_days.setText(tourDetailResponse.getDays());
        tv_idTour.setText(String.valueOf(tourDetailResponse.getId()));
        tv_price.setText(String.valueOf(tourDetailResponse.getPrice()));
        // Để set ảnh, bạn có thể dùng Glide
        Glide.with(this)
                .load(tourDetailResponse.getImageHeader() != null ? tourDetailResponse.getImageHeader() : R.drawable.placeholder)
                .into(img_header);
    }

    private void fetchTourImages(int tourId) {
        RetrofitService retrofitService = new RetrofitService();
        TourApi tourApi = retrofitService.getRetrofit().create(TourApi.class);

        tourApi.getTourDetailImages(tourId).enqueue(new Callback<List<TourDetailImage>>() {
            @Override
            public void onResponse(@NotNull Call<List<TourDetailImage>> call, @NotNull Response<List<TourDetailImage>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TourDetailImage> images = response.body();

                    // Tạo danh sách URL đầy đủ
                    List<String> imageUrls = new ArrayList<>();
                    for (TourDetailImage image : images) {
                        String imgUrl = image.getImgUrl();
                        // Gọi API để lấy ảnh từ imgUrl
                        tourApi.getImage(imgUrl).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    try {
                                        byte[] imageBytes = response.body().bytes();
                                        // Chuyển đổi ảnh từ byte[] thành URL base64
                                        String base64Image = "data:image/png;base64," + Base64.encodeToString(imageBytes, Base64.DEFAULT);
                                        imageUrls.add(base64Image);

                                        // Cập nhật dữ liệu cho adapter sau khi tất cả ảnh được tải về
                                        if (imageUrls.size() == images.size()) {
                                            tourDetailImageAdapter.setData(imageUrls);
                                        }
                                    } catch (Exception e) {
                                        Log.e("API_ERROR", "Lỗi khi đọc dữ liệu ảnh: ", e);
                                    }
                                } else {
                                    Log.e("API_ERROR", "Không thể tải ảnh từ API getImage");
                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                                Log.e("API_ERROR", "Lỗi khi gọi API getImage: ", t);
                            }
                        });
                    }
                } else {
                    Toast.makeText(TourDetailActivity.this, "Không có ảnh cho tour này", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<TourDetailImage>> call, @NotNull Throwable t) {
                Log.e("API_ERROR", "Lỗi khi gọi API lấy ảnh: ", t);
            }
        });
    }
}
