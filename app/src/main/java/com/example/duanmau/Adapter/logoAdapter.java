package com.example.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.model.logo;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class logoAdapter extends RecyclerView.Adapter<logoAdapter.ViewHolder> {

    private Context context;
    private List<logo> mListLogo;

    public logoAdapter(Context context,List<logo> mListLogo) {
        this.context = context;
        this.mListLogo = mListLogo;
    }

    @NonNull
    @Override
    public logoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_logo,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull logoAdapter.ViewHolder holder, int position) {

        holder.imageView.setImageResource(mListLogo.get(position).getLogo());
    }

    @Override
    public int getItemCount() {
        if (mListLogo!=null){
            return mListLogo.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_logo);
        }
    }
}
