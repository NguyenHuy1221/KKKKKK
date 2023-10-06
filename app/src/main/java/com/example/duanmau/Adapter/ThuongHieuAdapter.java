package com.example.duanmau.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.ThuongHieuDAO;
import com.example.duanmau.R;
import com.example.duanmau.model.ThuongHieu;

import java.util.ArrayList;

public class ThuongHieuAdapter extends RecyclerView.Adapter<ThuongHieuAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ThuongHieu> listTH;
    private ThuongHieuDAO thuongHieuDAO;

    public ThuongHieuAdapter(Context context, ArrayList<ThuongHieu> listTH,ThuongHieuDAO thuongHieuDAO) {
        this.context = context;
        this.listTH = listTH;
        this.thuongHieuDAO = thuongHieuDAO;
    }


    @NonNull
    @Override
    public ThuongHieuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thuong_hieu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThuongHieuAdapter.ViewHolder holder, int position) {
        holder.txtID.setText("ID: "+listTH.get(position).getIdthuonghieu());
        holder.txtTenLoai.setText("Tên: "+listTH.get(position).getTenthuonghieu());

        holder.txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(listTH.get(holder.getAbsoluteAdapterPosition()));
            }
        });

        holder.txtDlete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn chắc chắn muốn xóa thương hiệu : "+listTH.get(holder.getAbsoluteAdapterPosition()).getIdthuonghieu()+" không");
                int check = thuongHieuDAO.xoaTH(listTH.get(holder.getAbsoluteAdapterPosition()).getIdthuonghieu());
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (check){
                            case -1:
                                Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Ràng buộc khóa ngoại", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                cleaData();
                                break;
                        }
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listTH.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtID ;
        TextView txtTenLoai;
        TextView txtUpdate;
        TextView txtDlete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.ID_TH);
            txtTenLoai = itemView.findViewById(R.id.TheLoai);
            txtUpdate = itemView.findViewById(R.id.sua_qlth);
            txtDlete = itemView.findViewById(R.id.xoa_qlth);
        }
    }

    public void update(ThuongHieu thuongHieu) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_thuong_hieu,null);
        builder.setView(view);
        // ánh xạ
        TextView ten = view.findViewById(R.id.txtTH);
        EditText edtTH = view.findViewById(R.id.edt_tenTH);
        Button btnSua = view.findViewById(R.id.btn_SLuuTH);
        Button btnThoat = view.findViewById(R.id.btn_SThoatTH);

        AlertDialog dialog = builder.create();
        dialog.show();

        ten.setText("Cập Nhập");
        btnSua.setText("Sửa");

        edtTH.setText(thuongHieu.getTenthuonghieu());

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten =edtTH.getText().toString();

                if (ten.equals("")){
                    Toast.makeText(context, "Chưa nhập dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }

                ThuongHieu update = new ThuongHieu(thuongHieu.getIdthuonghieu(),ten);
                boolean check = thuongHieuDAO.suaTH(update);

                if (check){
                    Toast.makeText(context, "Thêm thương hiệu thành công", Toast.LENGTH_SHORT).show();
                    cleaData();
                    dialog.dismiss();
                }else {
                    Toast.makeText(context, "Thêm thương hiệu thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    public void cleaData() {
        listTH.clear();
        listTH = thuongHieuDAO.getALLTH();
        notifyDataSetChanged();
    }
}
