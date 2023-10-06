package com.example.duanmau.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duanmau.Adapter.ThuongHieuAdapter;
import com.example.duanmau.DAO.ThuongHieuDAO;
import com.example.duanmau.R;
import com.example.duanmau.model.ThuongHieu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ThuongHieuFragment extends Fragment {

    private ThuongHieuDAO thuongHieuDAO;
    private  RecyclerView recyclerView;
    private  ArrayList<ThuongHieu> listTH;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thuong_hieu, container, false);

        recyclerView = view.findViewById(R.id.recycle_qlth);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAddTH);

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThem();
            }
        });

        //data
         thuongHieuDAO = new ThuongHieuDAO(getContext());



        loadData();

        return view;
    }

    private void showDialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_thuong_hieu,null);
        builder.setView(view);

        EditText tenTH = view.findViewById(R.id.edt_tenTH);
        Button btnLuu = view.findViewById(R.id.btn_SLuuTH);
        Button btnThoat = view.findViewById(R.id.btn_SThoatTH);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TH = tenTH.getText().toString();

                if (TH.equals("")){
                    Toast.makeText(getContext(), "Chưa nhập dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean check = thuongHieuDAO.themTH(TH);

                if (check){
                    Toast.makeText(getContext(), "Thêm thương hiệu thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "Thêm thương hiệu thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });



    }


    public void loadData() {
        //adapter
        listTH = thuongHieuDAO.getALLTH();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ThuongHieuAdapter thuongHieuAdapter = new ThuongHieuAdapter(getContext(),listTH,thuongHieuDAO);
        recyclerView.setAdapter(thuongHieuAdapter);
    }

}