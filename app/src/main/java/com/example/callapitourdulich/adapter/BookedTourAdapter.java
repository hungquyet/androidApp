package com.example.callapitourdulich.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callapitourdulich.R;
import com.example.callapitourdulich.response.BookedTourResponse;

import java.util.List;

public class BookedTourAdapter extends RecyclerView.Adapter<BookedTourAdapter.ViewHolder> {

    private final List<BookedTourResponse> bookedTourList;
    private final Context context;

    public BookedTourAdapter(Context context, List<BookedTourResponse> bookedTourList) {
        this.context = context;
        this.bookedTourList = bookedTourList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_booked_tour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookedTourResponse tour = bookedTourList.get(position);

        holder.tvName.setText(tour.getTour_name());
        holder.tvStatus.setText(tour.getStatus());
        holder.tvTotalPrice.setText(String.format("Giá: %.0f VND", tour.getTotal_price()));

        // Nếu bạn có URL ảnh, sử dụng thư viện như Glide hoặc Picasso để tải ảnh:
        // Glide.with(context).load(tour.getImageUrl()).into(holder.imgTour);
    }

    @Override
    public int getItemCount() {
        return bookedTourList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStatus, tvTotalPrice;
        ImageView imgTour;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_nameBookedTour);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvTotalPrice = itemView.findViewById(R.id.tv_totalPrice);
            imgTour = itemView.findViewById(R.id.img_tour);
        }
    }
}
