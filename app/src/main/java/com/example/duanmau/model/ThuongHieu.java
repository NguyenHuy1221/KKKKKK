package com.example.duanmau.model;

public class ThuongHieu {

    private int idthuonghieu;
    private String tenthuonghieu;

    public ThuongHieu(int idthuonghieu, String tenthuonghieu) {
        this.idthuonghieu = idthuonghieu;
        this.tenthuonghieu = tenthuonghieu;
    }

    public int getIdthuonghieu() {
        return idthuonghieu;
    }

    public void setIdthuonghieu(int idthuonghieu) {
        this.idthuonghieu = idthuonghieu;
    }

    public String getTenthuonghieu() {
        return tenthuonghieu;
    }

    public void setTenthuonghieu(String tenthuonghieu) {
        this.tenthuonghieu = tenthuonghieu;
    }
}
