package model;

public class LichHenPhucVu {
    private int ma;
    private String ghiChu;
    private LichHen lichHen;
    private DichVu dichVu;
    private Slot slot;
    private double donGiaTamTinh;
    private java.util.List<LichHenNhanVien> dsLichHenNhanVien = new java.util.ArrayList<>();
    private java.util.List<LichHenNguyenLieu> dsLichHenNguyenLieu = new java.util.ArrayList<>();

    public LichHenPhucVu() {
    }

    public LichHenPhucVu(int ma, String ghiChu, LichHen lichHen, DichVu dichVu, Slot slot, double donGiaTamTinh) {
        this.ma = ma;
        this.ghiChu = ghiChu;
        this.lichHen = lichHen;
        this.dichVu = dichVu;
        this.slot = slot;
        this.donGiaTamTinh = donGiaTamTinh;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public LichHen getLichHen() {
        return lichHen;
    }

    public void setLichHen(LichHen lichHen) {
        this.lichHen = lichHen;
    }

    public DichVu getDichVu() {
        return dichVu;
    }

    public void setDichVu(DichVu dichVu) {
        this.dichVu = dichVu;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public double getDonGiaTamTinh() {
        return donGiaTamTinh;
    }

    public void setDonGiaTamTinh(double donGiaTamTinh) {
        this.donGiaTamTinh = donGiaTamTinh;
    }

    public java.util.List<LichHenNhanVien> getDsLichHenNhanVien() {
        return dsLichHenNhanVien;
    }

    public void setDsLichHenNhanVien(java.util.List<LichHenNhanVien> dsLichHenNhanVien) {
        this.dsLichHenNhanVien = dsLichHenNhanVien;
    }

    public java.util.List<LichHenNguyenLieu> getDsLichHenNguyenLieu() {
        return dsLichHenNguyenLieu;
    }

    public void setDsLichHenNguyenLieu(java.util.List<LichHenNguyenLieu> dsLichHenNguyenLieu) {
        this.dsLichHenNguyenLieu = dsLichHenNguyenLieu;
    }
}
