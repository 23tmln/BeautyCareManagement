package model;

public class NhanVien {
    private String ma;
    private String tenNhanVien;
    private String trangThai;

    public NhanVien() {
    }

    public NhanVien(String ma, String tenNhanVien, String trangThai) {
        this.ma = ma;
        this.tenNhanVien = tenNhanVien;
        this.trangThai = trangThai;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
