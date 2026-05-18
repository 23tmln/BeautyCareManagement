package controller;

import dao.NhanKhachDAO;
import model.DichVu;
import model.LichHen;
import model.NguyenLieu;
import model.NhanVien;
import model.Slot;
import service.DichVuService;
import service.LichHenService;
import service.NguyenLieuService;
import service.NhanVienService;
import service.SlotService;
import java.util.List;

public class NhanKhachController {
    private LichHenService lichHenService;
    private DichVuService dichVuService;
    private NguyenLieuService nguyenLieuService;
    private NhanVienService nhanVienService;
    private SlotService slotService;
    private NhanKhachDAO nhanKhachDAO;

    public NhanKhachController() {
        lichHenService = new LichHenService();
        dichVuService = new DichVuService();
        nguyenLieuService = new NguyenLieuService();
        nhanVienService = new NhanVienService();
        slotService = new SlotService();
        nhanKhachDAO = new NhanKhachDAO();
    }

    public List<LichHen> timLichHen(String tenKhachHang) {
        return lichHenService.timLichHenTheoTenKhachHang(tenKhachHang);
    }

    public List<DichVu> timDichVu(String tenDichVu) {
        return dichVuService.timDichVuTheoTen(tenDichVu);
    }

    public List<NguyenLieu> layDanhSachNguyenLieu() {
        return nguyenLieuService.layTatCaNguyenLieu();
    }

    public List<NguyenLieu> timNguyenLieu(String tenNguyenLieu) {
        return nguyenLieuService.timNguyenLieuTheoTen(tenNguyenLieu);
    }

    public List<NhanVien> layDanhSachNhanVienRanh() {
        return nhanVienService.layNhanVienRanh();
    }

    public List<Slot> timSlotTrong(String thoiGian) {
        return slotService.timSlotTrong(thoiGian);
    }

    public boolean luuNhanKhach(LichHen lichHen) {
        return nhanKhachDAO.luuNhanKhach(lichHen);
    }
}
