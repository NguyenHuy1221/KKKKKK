package com.example.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.duanmau.R;
import com.example.duanmau.model.slider;

import java.util.List;

public class sliderAdapter extends PagerAdapter {

    private Context mContext;
    private List<slider> mSliders;

    public sliderAdapter(Context mContext, List<slider> mSliders) {
        this.mContext = mContext;
        this.mSliders = mSliders;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_slider,container,false);
        ImageView img =view.findViewById(R.id.item_slider);

        slider slider = mSliders.get(position);
        if (slider != null) {
            Glide.with(mContext).load(slider.getResourchID()).into(img);
        }

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if (mSliders != null){
            return mSliders.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
