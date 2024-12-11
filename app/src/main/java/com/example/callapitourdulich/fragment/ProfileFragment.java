package com.example.callapitourdulich.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.callapitourdulich.R;
import com.example.callapitourdulich.activity.BookedTourActivity;
import com.example.callapitourdulich.activity.LoginActivity;
import com.example.callapitourdulich.api.UserApi;
import com.example.callapitourdulich.response.UserResponse;
import com.example.callapitourdulich.retrofit.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private View mView;
    private ConstraintLayout csl_updateInfo, csl_bookedTour, csl_logOut;
    private String token;
    private TextView tv_nameUser;
    private UserApi userApi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Lấy token từ SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        if (token == null) {
            Toast.makeText(requireContext(), "Chưa đăng nhập hoặc token không hợp lệ!", Toast.LENGTH_SHORT).show();
            return mView;
        }

        // Khởi tạo Retrofit và UserApi
        RetrofitService retrofitService = new RetrofitService();
        userApi = retrofitService.getRetrofit().create(UserApi.class);

        initUI();
        return mView;
    }

    private void initUI() {
        csl_updateInfo = mView.findViewById(R.id.csl_updateInfo);
        csl_bookedTour = mView.findViewById(R.id.csl_bookedTour);
        csl_logOut = mView.findViewById(R.id.csl_logOut);
        tv_nameUser = mView.findViewById(R.id.tv_nameUser);

        // Gọi API lấy thông tin người dùng
        getUserDetails();

        csl_bookedTour.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), BookedTourActivity.class);
            startActivity(intent);
        });

        csl_logOut.setOnClickListener(v -> {
            logout();
        });
    }

    private void getUserDetails() {
        Log.d("Token", "Bearer " + token);
        userApi.getUserDetails("Bearer " + token)
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            UserResponse user = response.body();

                            // Lưu userId vào SharedPreferences
                            saveToSharedPreferences("user_id", user.getId());
                            Log.d("UserProfile", "User ID: " + user.getId());

                            // Hiển thị tên người dùng trên giao diện
                            tv_nameUser.setText("Xin chào, " + user.getName());

                            // Gọi hàm showUpdateInfoDialog và truyền dữ liệu vào
                            csl_updateInfo.setOnClickListener(v -> showUpdateInfoDialog(user));
                        } else {
                            // Thêm log để kiểm tra lỗi từ server
                            Log.e("UserDetails", "Response code: " + response.code());
                            if (response.errorBody() != null) {
                                try {
                                    Log.e("UserDetails", "Error: " + response.errorBody().string());
                                } catch (Exception e) {
                                    Log.e("UserDetails", "Error parsing errorBody", e);
                                }
                            }
                            Toast.makeText(requireContext(), "Không thể lấy thông tin người dùng!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.e("UserDetails", "Lỗi kết nối: " + t.getMessage(), t);
                        Toast.makeText(requireContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void showUpdateInfoDialog(UserResponse user) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_update_info);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.CENTER);
        }

        TextInputEditText edtName = dialog.findViewById(R.id.edt_name);
        TextInputEditText edtPhone = dialog.findViewById(R.id.edt_phone);
        TextInputEditText edtAddress = dialog.findViewById(R.id.edt_address);
        TextInputEditText edtEmail = dialog.findViewById(R.id.edt_note);
        RadioGroup rgGender = dialog.findViewById(R.id.rg_gender);
        RadioButton rdbMale = dialog.findViewById(R.id.rdb_male);
        RadioButton rdbFemale = dialog.findViewById(R.id.rdb_female);

        edtName.setText(user.getName());
        edtPhone.setText(user.getPhone());
        edtEmail.setText(user.getEmail());
        edtAddress.setText(user.getAddress());

        if ("Nam".equalsIgnoreCase(user.getGender())) {
            rdbMale.setChecked(true);
        } else if ("Nữ".equalsIgnoreCase(user.getGender())) {
            rdbFemale.setChecked(true);
        }

        dialog.findViewById(R.id.btn_completeBooking).setOnClickListener(view -> {
            String name = edtName.getText().toString();
            String phone = edtPhone.getText().toString();
            String address = edtAddress.getText().toString();
            String email = edtEmail.getText().toString();
            String gender = rdbMale.isChecked() ? "Nam" : "Nữ";

            if (!phone.matches("0\\d{9}")) {
                Toast.makeText(requireContext(), "Số điện thoại không đúng định dạng!", Toast.LENGTH_SHORT).show();
                return;
            }

            UserResponse updatedUser = new UserResponse();
            updatedUser.setName(name);
            updatedUser.setPhone(phone);
            updatedUser.setAddress(address);
            updatedUser.setEmail(email);
            updatedUser.setGender(gender);

            updateUserDetails(user.getId(), updatedUser, dialog);
        });

        dialog.show();
    }

    private void updateUserDetails(int userId, UserResponse updatedUser, Dialog dialog) {
        userApi.updateUserDetails(userId, "Bearer " + token, updatedUser)
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            UserResponse updatedUserResponse = response.body();
                            tv_nameUser.setText("Xin chào, " + updatedUserResponse.getName());
                            getUserDetails();
                            Toast.makeText(requireContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(requireContext(), "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.e("UserUpdate", "Lỗi kết nối: " + t.getMessage(), t);
                    }
                });
    }

    private void saveToSharedPreferences(String key, int value) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private void logout() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

        Intent intent = new Intent(requireContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
