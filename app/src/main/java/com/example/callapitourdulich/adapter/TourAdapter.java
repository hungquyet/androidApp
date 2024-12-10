package com.example.callapitourdulich.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.callapitourdulich.R;
import com.example.callapitourdulich.activity.TourDetailActivity;
import com.example.callapitourdulich.model.Tour;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourViewHolder> {

    private List<Tour> tourList;

    public TourAdapter(List<Tour> tourList) {

        this.tourList = tourList;
        Log.d("TOUR_ADAPTER", "Number of tours passed to adapter: " + tourList.size());
    }

    @NonNull
    @Override
    public TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_tour, parent, false);
        return new TourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourViewHolder holder, int position) {
        Tour tour = tourList.get(position);
        Log.d("TOUR_BIND", "Binding tour at position " + position + ": " + tour.getTour_name());
        holder.tv_name.setText(tour.getTour_name());
        holder.tv_descriptionTour.setText(tour.getDescription());
        holder.tv_price.setText(String.valueOf((float) tour.getPrice()));


        String imageUrl = tour.getImageHeader();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .into(holder.img_tour);
        } else {
            // Nếu URL ảnh không hợp lệ, có thể để ảnh mặc định
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.placeholder) // Hình ảnh mặc định
                    .into(holder.img_tour);
        }

        // Thêm sự kiện click vào item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), TourDetailActivity.class);
            intent.putExtra("tour_id", tour.getId());  // Gửi ID tour
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }
}
