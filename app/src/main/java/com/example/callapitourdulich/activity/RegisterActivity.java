package com.example.callapitourdulich.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.callapitourdulich.retrofit.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText edt_phoneNumber, edt_passwordRegister, edt_retypePassword, edt_name;
    ConstraintLayout csl_btnRegister;
    TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
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
        edt_passwordRegister = findViewById(R.id.edt_passwordRegister);
        edt_retypePassword = findViewById(R.id.edt_retypePassword);
        edt_name = findViewById(R.id.edt_name);
        csl_btnRegister = findViewById(R.id.csl_btnRegister);
        tv_login = findViewById(R.id.tv_login);

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        csl_btnRegister.setOnClickListener(view -> {
            String phoneNumber = String.valueOf(edt_phoneNumber.getText());
            String password = String.valueOf(edt_passwordRegister.getText());
            String retypePassword = String.valueOf(edt_retypePassword.getText());
            String name = String.valueOf(edt_name.getText());
            int roleID = Integer.valueOf(1);

            UserDTO userDTO = new UserDTO();
            userDTO.setPhone(phoneNumber);
            userDTO.setPassword(password);
            userDTO.setRetypePassword(retypePassword);
            userDTO.setName(name);
            userDTO.setRole_id(roleID);

            userApi.register(userDTO)
                    .enqueue(new Callback<UserDTO>() {
                        @Override
                        public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                            Toast.makeText(RegisterActivity.this,"Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            edt_phoneNumber.setText("");
                            edt_passwordRegister.setText("");
                            edt_retypePassword.setText("");
                            edt_name.setText("");
                        }

                        @Override
                        public void onFailure(Call<UserDTO> call, Throwable throwable) {
                            Toast.makeText(RegisterActivity.this,"Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(RegisterActivity.class.getName()).log(Level.SEVERE, "Lỗi!", throwable);

                        }
                    });
        });

        tv_login.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}