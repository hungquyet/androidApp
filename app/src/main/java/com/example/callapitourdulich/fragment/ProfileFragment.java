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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.callapitourdulich.R;
import com.example.callapitourdulich.activity.BookedTourActivity;
import com.example.callapitourdulich.activity.TourDetailActivity;
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
    private TextInputEditText edt_name, edt_phone, edt_address, edt_email;
    private RadioGroup rg_gender;
    private RadioButton rdb_male, rdb_female;
    private String token;
    private int userId;

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

        initUI();

        return mView;
    }

    private void initUI() {
        csl_updateInfo = mView.findViewById(R.id.csl_updateInfo);
        csl_bookedTour = mView.findViewById(R.id.csl_bookedTour);
        csl_logOut = mView.findViewById(R.id.csl_logOut);

        csl_updateInfo.setOnClickListener(v -> showUpdateInfoDialog());
        csl_bookedTour.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), BookedTourActivity.class);
            startActivity(intent);
        });
    }

    private void showUpdateInfoDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_update_info);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.CENTER);
        }

        // Khai báo các EditText và RadioButton
        edt_name = dialog.findViewById(R.id.edt_name);
        edt_phone = dialog.findViewById(R.id.edt_phone);
        edt_address = dialog.findViewById(R.id.edt_address);
        edt_email = dialog.findViewById(R.id.edt_note);
        rg_gender = dialog.findViewById(R.id.rg_gender);
        rdb_male = dialog.findViewById(R.id.rdb_male);
        rdb_female = dialog.findViewById(R.id.rdb_female);

        // Gửi request lấy thông tin người dùng
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.getUserDetails("Bearer " + token)
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            UserResponse user = response.body();

                            // Lưu userId vào SharedPreferences
                            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("user_id", userId); // userId là giá trị bạn muốn lưu
                            editor.apply(); // Lưu thay đổi vào SharedPreferences
                            Log.d("UserProfile", "User ID: " + userId);

                            // Điền dữ liệu vào các trường trong dialog
                            edt_name.setText(user.getName());
                            edt_phone.setText(user.getPhone());
                            edt_email.setText(user.getEmail());
                            edt_address.setText(user.getAddress());

                            if ("Nam".equalsIgnoreCase(user.getGender())) {
                                rdb_male.setChecked(true);
                            } else if ("Nữ".equalsIgnoreCase(user.getGender())) {
                                rdb_female.setChecked(true);
                            }
                        } else {
                            Toast.makeText(requireContext(), "Không thể lấy thông tin người dùng!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Toast.makeText(requireContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Xử lý sự kiện nút "Hoàn tất"
        dialog.findViewById(R.id.btn_completeBooking).setOnClickListener(view -> {
            // Lấy dữ liệu từ các trường
            String name = edt_name.getText().toString();
            String phone = edt_phone.getText().toString();
            String address = edt_address.getText().toString();
            String email = edt_email.getText().toString();
            String gender = rdb_male.isChecked() ? "Nam" : "Nữ";

            // Tạo đối tượng UserResponse để gửi
            UserResponse updatedUser = new UserResponse();
            updatedUser.setName(name);
            updatedUser.setPhone(phone);
            updatedUser.setAddress(address);
            updatedUser.setEmail(email);
            updatedUser.setGender(gender);

            userApi.updateUserDetails(userId, "Bearer " + token, updatedUser)
                    .enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
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
        });
        dialog.show();
    }
}
