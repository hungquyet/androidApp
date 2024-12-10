package com.example.callapitourdulich.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.callapitourdulich.R;
import com.example.callapitourdulich.api.UserApi;
import com.example.callapitourdulich.dto.UserDTO;
import com.example.callapitourdulich.response.LoginResponse;
import com.example.callapitourdulich.retrofit.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edt_phoneNumber, edt_password;
    ConstraintLayout csl_btnLogin;
    TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initUI();
    }

    private void initUI() {
        edt_phoneNumber = findViewById(R.id.edt_phoneNumber);
        edt_password = findViewById(R.id.edt_password);
        csl_btnLogin = findViewById(R.id.csl_btnLogin);
        tv_register = findViewById(R.id.tv_register);

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        csl_btnLogin.setOnClickListener(view -> {
            String phoneNumber = String.valueOf(edt_phoneNumber.getText());
            String password = String.valueOf(edt_password.getText());

            if (phoneNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ số điện thoại và mật khẩu!", Toast.LENGTH_SHORT).show();
                return; // Dừng thực hiện nếu chưa nhập đầy đủ thông tin
            }

            UserDTO userDTO = new UserDTO();
            userDTO.setPhone(phoneNumber);
            userDTO.setPassword(password);

            userApi.login(userDTO).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        if (loginResponse != null && loginResponse.getToken() != null) {
                            String token = loginResponse.getToken();

                            // Lưu token vào SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token", token);
                            editor.apply();

                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            Log.d("LoginResponse", "Token: " + token); // In token ra log
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("token", token);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại! Token không hợp lệ.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            String errorBody = response.errorBody().string(); // Lấy body lỗi từ response
                            Log.e("LoginError", "Error Body: " + errorBody); // In thông báo lỗi ra log
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại! Mã lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                    Toast.makeText(LoginActivity.this, "Số điện thoại hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                    Log.e("LoginError", "Error: " + throwable.getMessage()); // In chi tiết lỗi
                    throwable.printStackTrace();
                }
            });
        });

        tv_register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
