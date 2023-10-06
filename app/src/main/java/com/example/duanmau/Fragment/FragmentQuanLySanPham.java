package com.example.duanmau.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.duanmau.Adapter.sanPhamAdapter;
import com.example.duanmau.DAO.sanPhamDAO;
import com.example.duanmau.R;
import com.example.duanmau.model.sanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragmentQuanLySanPham extends Fragment {

    private sanPhamDAO sanPhamDAO;
    RecyclerView recyclerView;
    private sanPhamAdapter sanPhamAdapter;
    private FloatingActionButton floatAdd;

    private ImageView ivHinhSP;
    private String filePath = "";
    private TextView tvTrangThai;
    private String linkHinh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quan_ly_san_pham, container, false);

        //ánh xạ
        recyclerView = view.findViewById(R.id.recycle_qlsp);
        floatAdd = view.findViewById(R.id.floatAdd);

        configCloudinary();

        // lâấy danh sách sản phẩm database
        sanPhamDAO = new sanPhamDAO(getContext());
        ArrayList<sanPham> listsp = sanPhamDAO.getDS();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        sanPhamAdapter = new sanPhamAdapter(getContext(),listsp,sanPhamDAO);
        recyclerView.setAdapter(sanPhamAdapter);

        // thêm san phẩm
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themSP();
            }
        });


        return view;
    }

    public void themSP() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_san_pham,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        // ánh xạ
        EditText edtSoLuong = view.findViewById(R.id.txt_soLuong);
        EditText edtTen = view.findViewById(R.id.txt_tenSP);
        EditText edtGia = view.findViewById(R.id.txt_GiaSP);
        Button btnThoat = view.findViewById(R.id.btn_Thoat);
        Button btnLuu = view.findViewById(R.id.btn_Luu);
        ivHinhSP = view.findViewById(R.id.iv_hinhsp);
        tvTrangThai = view.findViewById(R.id.tv_trang_thai);

        //
        sanPhamDAO = new sanPhamDAO(getContext());

        //bắt sự kiện cho nút lưu
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tenSP = edtTen.getText().toString().trim();
                String giaSP = edtGia.getText().toString().trim();
                String soLuong = edtSoLuong.getText().toString().trim();


                if(tenSP.isEmpty() || giaSP.isEmpty() || soLuong.isEmpty()) {
                    Toast.makeText(getContext(), "Vui Lòng Nhập Đủ Dữ Liệu", Toast.LENGTH_SHORT).show();
                }else {
                    sanPham mSanPham = new sanPham(tenSP,giaSP,soLuong,linkHinh);
                    boolean check = sanPhamDAO.addSP(mSanPham);
                    if (check){
                        Toast.makeText(getContext(), "Thêm Sản Phẩm Thành Công", Toast.LENGTH_SHORT).show();
                        ArrayList<sanPham> capnhat = sanPhamDAO.getDS();
                        sanPhamAdapter.updatelist(capnhat);
                        dialog.dismiss();
                    }else {
                        Toast.makeText(getContext(), "Thêm Sản Phẩm Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // bắt sự kiện cho nút thoát
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ivHinhSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessTheGallery();
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }


    public void accessTheGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        myLauncher.launch(i);
    }

    private ActivityResultLauncher<Intent> myLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            //get the image's file location
            filePath = getRealPathFromUri(result.getData().getData(), getActivity());

            if (result.getResultCode() == RESULT_OK) {
                try {
                    //set picked image to the mProfile
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result.getData().getData());
                    ivHinhSP.setImageBitmap(bitmap);
                    uploadToCloudinary(filePath);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    private String getRealPathFromUri(Uri imageUri, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(imageUri, null, null, null, null);

        if (cursor == null) {
            return imageUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }


    HashMap<String, String> config = new HashMap<>();

    private void configCloudinary() {
        config.put("cloud_name", "db6fm3nna");
        config.put("api_key", "275634911119596");
        config.put("api_secret", "hInLl-uSG0iim9jDRjGjT-huhv0");
        MediaManager.init(getActivity(), config);
    }

    private void uploadToCloudinary(String filePath) {
        Log.d("A", "sign up uploadToCloudinary- ");
        MediaManager.get().upload(filePath).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                tvTrangThai.setText("Bắt đầu upload");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                tvTrangThai.setText("Đang upload... ");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                tvTrangThai.setText("Thành công: " + resultData.get("url").toString());
                linkHinh = resultData.get("url").toString();
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                tvTrangThai.setText("Lỗi " + error.getDescription());
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                tvTrangThai.setText("Reshedule " + error.getDescription());
            }
        }).dispatch();
    }




}