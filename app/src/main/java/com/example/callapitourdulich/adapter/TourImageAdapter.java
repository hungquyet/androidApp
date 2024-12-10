package com.example.callapitourdulich.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callapitourdulich.R;
import com.example.callapitourdulich.model.TourImage;

import java.util.List;

public class TourImageAdapter extends RecyclerView.Adapter<TourImageAdapter.TourImageViewHolder>{

    private List<TourImage> mListTourImage;

    public void setData(List<TourImage> list){
        this.mListTourImage = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TourImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_slide, parent, false);
        return new TourImageAdapter.TourImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourImageViewHolder holder, int position) {
        TourImage tourImage = mListTourImage.get(position);
        if(tourImage == null){
            return;
        }

        holder.img_tour_slide.setImageResource(tourImage.getTour_image());
    }

    @Override
    public int getItemCount() {
        if(mListTourImage != null){
            return mListTourImage.size();
        }
        return 0;
    }

    public class TourImageViewHolder extends RecyclerView.ViewHolder{
        // Khai báo các thành phần có trong layout item
        private ImageView img_tour_slide;

        public TourImageViewHolder(@NonNull View itemView) {
            super(itemView);

            img_tour_slide = itemView.findViewById(R.id.img_tour_slide);

        }
    }

}