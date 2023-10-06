package com.example.duanmau.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.duanmau.Adapter.logoAdapter;
import com.example.duanmau.Adapter.showSanPhamAdapter;
import com.example.duanmau.Adapter.sliderAdapter;
import com.example.duanmau.DAO.sanPhamDAO;
import com.example.duanmau.R;
import com.example.duanmau.model.logo;
import com.example.duanmau.model.sanPham;
import com.example.duanmau.model.slider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;


public class Fragment_Trang_Chu extends Fragment {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private sliderAdapter sliderAdapter;
    private SearchView searchView;
    private RecyclerView recyclerViewLogo;
    private logoAdapter mLogoAdapter;
    private sanPhamDAO mSanPhamDAO;

    private showSanPhamAdapter showSanPhamAdapter;
    private RecyclerView recyclerViewSanPham;
    private FirebaseFirestore database;

    public Fragment_Trang_Chu() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__trang__chu, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator = view.findViewById(R.id.circle_indicator);
//        searchView = view.findViewById(R.id.timKiem);
        recyclerViewLogo = view.findViewById(R.id.rv_logo);
        recyclerViewSanPham = view.findViewById(R.id.rc_sanPham);

        // khởi tạo database
        database = FirebaseFirestore.getInstance();

        // hien danh sach slider
        sliderAdapter = new sliderAdapter(getContext(),getListSlider());
        viewPager.setAdapter(sliderAdapter);
        circleIndicator.setViewPager(viewPager);
        sliderAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

//        searchView.setQueryHint("What are you looking for");

        // hiện danh sách logo
        mLogoAdapter = new logoAdapter(getContext(),getLogo());
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(),4);
        recyclerViewLogo.setLayoutManager(gridLayoutManager1);
        recyclerViewLogo.setAdapter(mLogoAdapter);


        // hiện sản phẩm
        mSanPhamDAO = new sanPhamDAO(getContext());
        ArrayList<sanPham> listsp = mSanPhamDAO.getDS();

        showSanPhamAdapter = new showSanPhamAdapter(getContext(),listsp,database);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerViewSanPham.setLayoutManager(gridLayoutManager);

        recyclerViewSanPham.setAdapter(showSanPhamAdapter);


        //search
//        searchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }

    // danh sách slider
    public List<slider> getListSlider() {
        List<slider> list = new ArrayList<>();
        list.add(new slider(R.drawable.sanpham3));
        list.add(new slider(R.drawable.sanpham3));
        list.add(new slider(R.drawable.sanpham3));
        return list;
    }

    // danh sách logo
    public List<logo> getLogo(){
        List<logo> list = new ArrayList<>();
        list.add(new logo(R.drawable.nike));
        list.add(new logo(R.drawable.adidas));
        list.add(new logo(R.drawable.puma));
        list.add(new logo(R.drawable.reebok));
        return list;
    }

}