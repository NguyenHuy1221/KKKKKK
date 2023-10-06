package com.example.duanmau.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context,"QuanLySP",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //cc huy
        //thuơng hieeuj
        String qlThuongHieu = "CREATE TABLE THUONG_HIEU(idthuonghieu INTEGER PRIMARY KEY ,tenthuonghieu TEXT)";
        db.execSQL(qlThuongHieu);
        //data thương hiệu
        db.execSQL("INSERT INTO THUONG_HIEU VALUES(1,'NIKE'),(2,'ADIDAS'),(3,'PUMA'),(4,'REEBOK')");

        //sản phẩm
        String qlSanPham = "CREATE TABLE SAN_PHAM(masp INTEGER PRIMARY KEY AUTOINCREMENT,tensp TEXT, giasp INTEGER,soluong INTEGER ,imagesp TEXT,idthuonghieu INTEGER REFERENCES THUONG_HIEU(idthuonghieu))";
        db.execSQL(qlSanPham);
        // data sp
        db.execSQL("INSERT INTO SAN_PHAM VALUES (1,'Giày NIKE AIR ZOOM',2231000,9,'',1),(2,'Giày HERRO',3000000,10,'',1)");

        // Khách Hàng
        String qlKhachHang = "CREATE TABLE KHACH_HANG(idkhachhang INTEGER PRIMARY KEY AUTOINCREMENT,tenkhachhang TEXT ,sdt TEXT,diachi TEXT)";
        db.execSQL(qlKhachHang);
        //data kh
        db.execSQL("INSERT INTO KHACH_HANG VALUES (1,'Nguyen Quang Huy','123456789','DakLak'),(2,'Ho Duc Hau','5675689','BMT')");

        // nhan vien
        String qlNhanVien = "CREATE TABLE NHAN_VIEN(idnhanvien INTEGER PRIMARY KEY AUTOINCREMENT,tennhanvien TEXT,sdt TEXT,diachi TEXT,ngayvaolam TEXT,trangthai TEXT)";
        db.execSQL(qlNhanVien);
        //data nhan vien
        db.execSQL("INSERT INTO NHAN_VIEN VALUES (1,'LE THI NO','67887655','GIALAI','11/12/2022','hoat dong'),(2,'PHAM VAN D','443212','HN','11/07/2023','hoat dong')");

        // hoa don
        String qlHoaDon = "CREATE TABLE HOA_DON(idhoadon INTEGER PRIMARY KEY AUTOINCREMENT,idkhachhang INTEGER REFERENCES KHACH_HANG(idkhachhang),idnhanvien INTEGER REFERENCES NHAN_VIEN(idnhanvien) ,ngay TEXT,tongtien TEXT)";
        db.execSQL(qlHoaDon);
        // data hoadon
        db.execSQL("INSERT INTO HOA_DON VALUES (1,1,1,'03/10/2023','3000000'),(2,2,2,'10/09/2023','6000000')");

        // chi tiet hoa don
        String ctHoaDon = "CREATE TABLE CTHD(idcthd INTEGER PRIMARY KEY AUTOINCREMENT,masp INTEGER REFERENCES SAN_PHAM(masp),idhoadon INTEGER REFERENCES HOA_DON(idhoadon),soluong INTEGER,dongia REAL)";
        db.execSQL(ctHoaDon);
        //data cthd
        db.execSQL("INSERT INTO CTHD VALUES(1,1,1,2,300),(2,2,2,3,600)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS THUONG_HIEU");
            db.execSQL("DROP TABLE IF EXISTS SAN_PHAM");
            db.execSQL("DROP TABLE IF EXISTS KHACH_HANG");
            db.execSQL("DROP TABLE IF EXISTS NHAN_VIEN");
            db.execSQL("DROP TABLE IF EXISTS HOA_DON");
            db.execSQL("DROP TABLE IF EXISTS CTHD");
            onCreate(db);
        }
    }
}
