package model;

public class DichVu {
    private String ma;
    private double giaDichVu;
    private String tenDichVu;
    private String moTaDichVu;

    public DichVu() {
    }

    public DichVu(String ma, double giaDichVu, String tenDichVu, String moTaDichVu) {
        this.ma = ma;
        this.giaDichVu = giaDichVu;
        this.tenDichVu = tenDichVu;
        this.moTaDichVu = moTaDichVu;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public double getGiaDichVu() {
        return giaDichVu;
    }

    public void setGiaDichVu(double giaDichVu) {
        this.giaDichVu = giaDichVu;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public String getMoTaDichVu() {
        return moTaDichVu;
    }

    public void setMoTaDichVu(String moTaDichVu) {
        this.moTaDichVu = moTaDichVu;
    }
}
