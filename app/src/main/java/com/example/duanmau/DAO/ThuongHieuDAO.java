package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.Database.DbHelper;
import com.example.duanmau.model.ThuongHieu;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ThuongHieuDAO {
    private DbHelper dbHelper;

    public ThuongHieuDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    // lấy danh sách thương hiệu
    public ArrayList<ThuongHieu> getALLTH() {
        ArrayList<ThuongHieu> listTH = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUONG_HIEU",null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                listTH.add(new ThuongHieu(cursor.getInt(0),cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return listTH;
    }

    public  boolean themTH(String tenTH){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenthuonghieu",tenTH);
        long check = sqLiteDatabase.insert("THUONG_HIEU",null,contentValues);

        if (check == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean suaTH(ThuongHieu thuongHieu){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenthuonghieu",thuongHieu.getTenthuonghieu());
        int check = sqLiteDatabase.update("THUONG_HIEU",contentValues,"idthuonghieu =?",new String[]{String.valueOf(thuongHieu.getIdthuonghieu())});

        return check != 0 ;
    }

//    public int xoaTH(int maTH){
//        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUONG_HIEU WHERE idthuonghieu =? ",new String[]{String.valueOf(maTH)});
//        if (cursor.getCount()>0){
//            return 0; // không xóa được khóa ngoại
//        }else {
//            int check = sqLiteDatabase.delete("THUONG_HIEU","idthuonghieu=?",new String[]{String.valueOf(maTH)});
//            if (check ==0){
//                return -1; // xóa không được lỗi
//            }else {
//                return 1; // xoaos thanh công
//            }
//        }
//    }

    public int xoaTH(int maTH) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int check = sqLiteDatabase.delete("THUONG_HIEU", "idthuonghieu=?", new String[]{String.valueOf(maTH)});

        if (check == 0) {
            return -1; // xóa không được lỗi
        } else {
            return 1; // xóa thành công
        }
    }


}
