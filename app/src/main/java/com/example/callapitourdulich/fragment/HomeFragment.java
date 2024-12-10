package com.example.callapitourdulich.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callapitourdulich.R;
import com.example.callapitourdulich.adapter.TourAdapter;
import com.example.callapitourdulich.adapter.TourImageAdapter;
import com.example.callapitourdulich.api.TourApi;
import com.example.callapitourdulich.model.Tour;
import com.example.callapitourdulich.model.TourImage;
import com.example.callapitourdulich.response.TourResponse;
import com.example.callapitourdulich.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private View mView;
    private RecyclerView rcv_tour, rcv_imageSlide;
    private EditText edt_searchTour;
    private List<Tour> tourList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        // Bắt sự kiện cho trang Home
        initUI();
        imageSlide();
        loadTours();
        return mView;
    }

    private List<TourImage> listImage() {
        List<TourImage> listImage = new ArrayList<>();
        listImage.add(new TourImage(R.drawable.intro));
        listImage.add(new TourImage(R.drawable.nghinhphong));
        listImage.add(new TourImage(R.drawable.nghinhphong2));
        listImage.add(new TourImage(R.drawable.nghinhphong3));
        return listImage;
    }

    private void imageSlide() {
        LinearLayoutManager layoutImageSlide = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        rcv_imageSlide.setLayoutManager(layoutImageSlide);
        rcv_imageSlide.setFocusable(false);

        TourImageAdapter tourImageAdapter = new TourImageAdapter();
        tourImageAdapter.setData(listImage());
        rcv_imageSlide.setAdapter(tourImageAdapter);

        Handler handler = new Handler(Looper.getMainLooper());
        int delay = 2000; // Chuyển sang item tiếp theo mỗi 2 giây
        Runnable runnable = new Runnable() {
            int currentPosition = 0;

            @Override
            public void run() {
                if (currentPosition >= tourImageAdapter.getItemCount()) {
                    currentPosition = 0;
                }
                rcv_imageSlide.smoothScrollToPosition(currentPosition++);
                handler.postDelayed(this, delay);
            }
        };
        handler.postDelayed(runnable, delay);
    }

    private void initUI() {
        rcv_tour = mView.findViewById(R.id.rcv_tour);
        rcv_imageSlide = mView.findViewById(R.id.rcv_imageSlide);
        edt_searchTour = mView.findViewById(R.id.edt_searchTour);

        rcv_tour.setLayoutManager(new LinearLayoutManager(requireContext()));
        rcv_tour.setNestedScrollingEnabled(false);
        rcv_tour.setHasFixedSize(false);

        // Thêm TextWatcher để xử lý tìm kiếm khi người dùng nhập vào
        edt_searchTour.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTours(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    private void loadTours() {
        RetrofitService retrofitService = new RetrofitService();
        TourApi tourApi = retrofitService.getRetrofit().create(TourApi.class);

        tourApi.getAllTours(0, 12)
                .enqueue(new Callback<TourResponse>() {
                    @Override
                    public void onResponse(Call<TourResponse> call, Response<TourResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            tourList = response.body().getTourResponses();
                            if (tourList != null && !tourList.isEmpty()) {
                                // Lọc danh sách chỉ lấy những tour có status là "ACTIVE"
                                List<Tour> activeTours = filterActiveTours(tourList);

                                if (!activeTours.isEmpty()) {
                                    populateListView(activeTours);
                                } else {
                                    Toast.makeText(requireContext(), "Không có tour nào ở trạng thái ACTIVE", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(requireContext(), "Không có dữ liệu tour", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(requireContext(), "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TourResponse> call, Throwable throwable) {
                        Log.e("API_ERROR", "Lỗi xảy ra khi gọi API: ", throwable);
                    }
                });
    }
    private List<Tour> filterActiveTours(List<Tour> tourList) {
        return tourList.stream()
                .filter(tour -> "ACTIVE".equalsIgnoreCase(tour.getStatus()))
                .collect(Collectors.toList());
    }

    private void filterTours(String keyword) {
        if (keyword.isEmpty()) {
            // Nếu không nhập từ khóa, hiển thị lại toàn bộ danh sách tour
            populateListView(filterActiveTours(tourList));
        } else {
            // Lọc tour dựa trên từ khóa (tìm trong tên hoặc mô tả)
            List<Tour> filteredTours = tourList.stream()
                    .filter(tour -> tour.getTour_name().toLowerCase().contains(keyword.toLowerCase()) ||
                            tour.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());

            if (!filteredTours.isEmpty()) {
                populateListView(filteredTours);
            } else {
                populateListView(new ArrayList<>());
                Toast.makeText(requireContext(), "Không tìm thấy tour nào", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void populateListView(List<Tour> tourList) {
        TourAdapter tourAdapter = new TourAdapter(tourList);
        rcv_tour.setAdapter(tourAdapter);
    }
}