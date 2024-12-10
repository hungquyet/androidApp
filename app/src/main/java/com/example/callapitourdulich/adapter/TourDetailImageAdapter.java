package com.example.callapitourdulich.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.callapitourdulich.R;

import java.util.List;

public class TourDetailImageAdapter extends RecyclerView.Adapter<TourDetailImageAdapter.TourDetailImageViewHolder> {

    private List<String> imageUrls;

    public void setData(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TourDetailImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cv_img, parent, false);
        return new TourDetailImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourDetailImageViewHolder holder, int position) {
        if (imageUrls != null && !imageUrls.isEmpty()) {
            String imageUrl = imageUrls.get(position);

            // Tải ảnh từ chuỗi base64 hoặc URL
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder) // Ảnh tạm khi tải
                    .error(R.drawable.error) // Ảnh khi lỗi
                    .transition(DrawableTransitionOptions.withCrossFade()) // Hiệu ứng chuyển tiếp
                    .into(holder.imgTourDetail);
        }
    }

    @Override
    public int getItemCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    static class TourDetailImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTourDetail;

        public TourDetailImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTourDetail = itemView.findViewById(R.id.img_tourDetail);
        }
    }
}
