package model;

public class LichHenNguyenLieu {
    private String ma;
    private int soLuongSuDung;
    private String ghiChu;
    private NguyenLieu nguyenLieu;
    private LichHenPhucVu lichHenPhucVu;

    public LichHenNguyenLieu() {
    }

    public LichHenNguyenLieu(String ma, int soLuongSuDung, String ghiChu, NguyenLieu nguyenLieu, LichHenPhucVu lichHenPhucVu) {
        this.ma = ma;
        this.soLuongSuDung = soLuongSuDung;
        this.ghiChu = ghiChu;
        this.nguyenLieu = nguyenLieu;
        this.lichHenPhucVu = lichHenPhucVu;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public int getSoLuongSuDung() {
        return soLuongSuDung;
    }

    public void setSoLuongSuDung(int soLuongSuDung) {
        this.soLuongSuDung = soLuongSuDung;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public NguyenLieu getNguyenLieu() {
        return nguyenLieu;
    }

    public void setNguyenLieu(NguyenLieu nguyenLieu) {
        this.nguyenLieu = nguyenLieu;
    }

    public LichHenPhucVu getLichHenPhucVu() {
        return lichHenPhucVu;
    }

    public void setLichHenPhucVu(LichHenPhucVu lichHenPhucVu) {
        this.lichHenPhucVu = lichHenPhucVu;
    }
}
