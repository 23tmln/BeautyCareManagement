package model;

import java.sql.Date;
import java.sql.Time;

public class LichHen {
    private String ma;
    private Time gioBatDau;
    private Time gioKetThuc;
    private Date ngayHen;
    private Date ngayDat;
    private String ghiChu;
    private KhachHang khachHang;
    private java.util.List<LichHenPhucVu> dsLichHenPhucVu = new java.util.ArrayList<>();

    public LichHen() {
    }

    public LichHen(String ma, Time gioBatDau, Time gioKetThuc, Date ngayHen, Date ngayDat, String ghiChu, KhachHang khachHang) {
        this.ma = ma;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
        this.ngayHen = ngayHen;
        this.ngayDat = ngayDat;
        this.ghiChu = ghiChu;
        this.khachHang = khachHang;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public Time getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(Time gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public Time getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(Time gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }

    public Date getNgayHen() {
        return ngayHen;
    }

    public void setNgayHen(Date ngayHen) {
        this.ngayHen = ngayHen;
    }

    public Date getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(Date ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public java.util.List<LichHenPhucVu> getDsLichHenPhucVu() {
        return dsLichHenPhucVu;
    }

    public void setDsLichHenPhucVu(java.util.List<LichHenPhucVu> dsLichHenPhucVu) {
        this.dsLichHenPhucVu = dsLichHenPhucVu;
    }
}
