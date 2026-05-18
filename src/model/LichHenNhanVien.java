package model;

public class LichHenNhanVien {
    private String ma;
    private String vaiTro;
    private String ghiChu;
    private NhanVien nhanVien;
    private LichHenPhucVu lichHenPhucVu;

    public LichHenNhanVien() {
    }

    public LichHenNhanVien(String ma, String vaiTro, String ghiChu, NhanVien nhanVien, LichHenPhucVu lichHenPhucVu) {
        this.ma = ma;
        this.vaiTro = vaiTro;
        this.ghiChu = ghiChu;
        this.nhanVien = nhanVien;
        this.lichHenPhucVu = lichHenPhucVu;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public LichHenPhucVu getLichHenPhucVu() {
        return lichHenPhucVu;
    }

    public void setLichHenPhucVu(LichHenPhucVu lichHenPhucVu) {
        this.lichHenPhucVu = lichHenPhucVu;
    }
}
