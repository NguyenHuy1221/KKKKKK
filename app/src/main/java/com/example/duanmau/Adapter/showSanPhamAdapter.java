package com.example.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duanmau.R;
import com.example.duanmau.model.sanPham;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class showSanPhamAdapter extends RecyclerView.Adapter<showSanPhamAdapter.ViewHolder> {

    private Context mContext;
    private List<sanPham> mListSP;
    private FirebaseFirestore database;

    public showSanPhamAdapter(Context mContext , List<sanPham> mListSP,FirebaseFirestore database) {
        this.mContext = mContext;
        this.mListSP = mListSP;
        this.database = database;
    }

    public void setData (List<sanPham> list){
        this.mListSP =list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public showSanPhamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull showSanPhamAdapter.ViewHolder holder, int position) {

        sanPham sanPhamList =mListSP.get(position);
        String imageUrl = sanPhamList.getImagesp();
        Glide.with(mContext).load(imageUrl).into(holder.imgSP);
        holder.tenSP.setText(sanPhamList.getTensp());
        holder.giaSP.setText(sanPhamList.getGiasp());

    }

    @Override
    public int getItemCount() {
        if (mListSP != null){
            return mListSP.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        sanPham mSanPham;
        private ImageView imgSP;
        private TextView tenSP;
        private TextView giaSP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSP = itemView.findViewById(R.id.img_SanPhamMoi);
            tenSP = itemView.findViewById(R.id.tv_TenSanPhamMoi);
            giaSP = itemView.findViewById(R.id.tv_GiaSanPhamMoi);
            database = FirebaseFirestore.getInstance(); // khởi tạo database
        }
    }
}
