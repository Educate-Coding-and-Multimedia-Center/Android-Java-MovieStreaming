package com.educate.moviestreaming.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.educate.moviestreaming.R;
import com.educate.moviestreaming.model.Slide;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {

    private static final String BASE_POSTER_IMAGE_URL = "https://image.tmdb.org/t/p/w500";

    private Context mContext ;
    private List<Slide> mList ;

    public SliderPagerAdapter(Context mContext, List<Slide> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slide_item,null);

        ImageView slideImg = slideLayout.findViewById(R.id.slide_img);
        TextView slideText = slideLayout.findViewById(R.id.slide_title);
//        slideImg.setImageResource(mList.get(position).getImage());
        slideText.setText(mList.get(position).getTitle());

        Glide.with(mContext).load(BASE_POSTER_IMAGE_URL + mList.get(position).getBackdropPath()).into(slideImg);

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public int getCount() {
        if (mList.size() != 0)
            return 5;

        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public void SetData(List<Slide> data){
        this.mList = data;
        notifyDataSetChanged();
    }
}
