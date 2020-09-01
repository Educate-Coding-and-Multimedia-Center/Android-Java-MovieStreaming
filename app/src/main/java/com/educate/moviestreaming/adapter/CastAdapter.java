package com.educate.moviestreaming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.educate.moviestreaming.R;
import com.educate.moviestreaming.model.Cast;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private static final String BASE_POSTER_IMAGE_URL = "https://image.tmdb.org/t/p/w500";

    Context mContext;
    List<Cast> mData;

    public CastAdapter(Context mContext, List<Cast> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cast, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        Glide.with(mContext).load(BASE_POSTER_IMAGE_URL + mData.get(position).getImg_link()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public CastViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_cast);
        }
    }

    public void SetData(List<Cast> data) {
        this.mData = data;
        notifyDataSetChanged();
    }
}
