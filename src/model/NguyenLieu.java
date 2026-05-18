package model;

public class NguyenLieu {
    private String ma;
    private double giaNL;
    private String tenNguyenLieu;
    private String moTaNguyenLieu;
    private int soLuong;

    public NguyenLieu() {
    }

    public NguyenLieu(String ma, double giaNL, String tenNguyenLieu, String moTaNguyenLieu, int soLuong) {
        this.ma = ma;
        this.giaNL = giaNL;
        this.tenNguyenLieu = tenNguyenLieu;
        this.moTaNguyenLieu = moTaNguyenLieu;
        this.soLuong = soLuong;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public double getGiaNL() {
        return giaNL;
    }

    public void setGiaNL(double giaNL) {
        this.giaNL = giaNL;
    }

    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }

    public void setTenNguyenLieu(String tenNguyenLieu) {
        this.tenNguyenLieu = tenNguyenLieu;
    }

    public String getMoTaNguyenLieu() {
        return moTaNguyenLieu;
    }

    public void setMoTaNguyenLieu(String moTaNguyenLieu) {
        this.moTaNguyenLieu = moTaNguyenLieu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
