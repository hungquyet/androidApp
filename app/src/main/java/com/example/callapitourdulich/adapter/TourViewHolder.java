package com.example.callapitourdulich.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callapitourdulich.R;

public class TourViewHolder extends RecyclerView.ViewHolder{
    TextView tv_name, tv_descriptionTour, tv_price;
    ImageView img_tour;

    public TourViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_name = itemView.findViewById(R.id.tv_name);
        tv_descriptionTour = itemView.findViewById(R.id.tv_descriptionTour);
        tv_price = itemView.findViewById(R.id.tv_price);
        img_tour = itemView.findViewById(R.id.img_tour);
    }
}
