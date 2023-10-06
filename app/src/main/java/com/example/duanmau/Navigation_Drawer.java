package com.example.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.duanmau.Fragment.FragmentQuanLySanPham;
import com.example.duanmau.Fragment.FragmentQuanLyTaiKhoan;
import com.example.duanmau.Fragment.Fragment_Trang_Chu;
import com.example.duanmau.Fragment.ThuongHieuFragment;
import com.google.android.material.navigation.NavigationView;

public class Navigation_Drawer extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private Toolbar mToolbar;

    private final int PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        // ánh xạ
        mDrawerLayout = findViewById(R.id.drawerlayout);
        mNavigationView = findViewById(R.id.navigation);
        mToolbar = findViewById(R.id.toolbar);

        requestPermission();

        // toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        getSupportActionBar().setTitle("");

        // sét mặc định fragment khi chạy lên
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_navigation,new Fragment_Trang_Chu())
                .commit();


        //sự kien khi ấn vào item tương ứng
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                int items = item.getItemId();

                if(items == R.id.menu_home){
                    fragment = new Fragment_Trang_Chu();
                } else if (items == R.id.menu_ql_SP) {
                    fragment = new FragmentQuanLySanPham();
                } else if (items == R.id.menu_ql_tk) {
                    fragment = new FragmentQuanLyTaiKhoan();
                } else if (items == R.id.menu_ql_TH) {
                    fragment = new ThuongHieuFragment();
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_navigation,fragment)
                        .commit();

                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

    }


    // sự kiện khi ấn vàoi_menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


    // xin quyền
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Cấp quyền thành công ", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Cấp quyền thành công ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


}