package dao;

import model.LichHen;
import model.LichHenPhucVu;
import model.LichHenNhanVien;
import model.LichHenNguyenLieu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class NhanKhachDAO {
    
    private NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private SlotDAO slotDAO = new SlotDAO();
    private NguyenLieuDAO nguyenLieuDAO = new NguyenLieuDAO();

    public boolean luuNhanKhach(LichHen lichHen) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            if (conn == null) return false;
            
            conn.setAutoCommit(false); // Bắt đầu Transaction

            // Duyệt qua danh sách Dịch Vụ
            for (LichHenPhucVu lhpv : lichHen.getDsLichHenPhucVu()) {
                String sqlLHPV = "INSERT INTO tblLichHenPhucVu (ghiChu, maLichHen, maDichVu, maSlot, donGiaTamTinh) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement psLHPV = conn.prepareStatement(sqlLHPV, Statement.RETURN_GENERATED_KEYS)) {
                    psLHPV.setString(1, lhpv.getGhiChu());
                    psLHPV.setString(2, lichHen.getMa());
                    psLHPV.setString(3, lhpv.getDichVu().getMa());
                    psLHPV.setString(4, lhpv.getSlot().getMa()); // Gán chung 1 Slot
                    psLHPV.setDouble(5, lhpv.getDonGiaTamTinh());
                    
                    int affectedRows = psLHPV.executeUpdate();
                    if (affectedRows > 0) {
                        try (ResultSet rs = psLHPV.getGeneratedKeys()) {
                            if (rs.next()) {
                                lhpv.setMa(rs.getInt(1)); // Cập nhật ID tự sinh
                            } else {
                                throw new Exception("Không lấy được ID của LichHenPhucVu");
                            }
                        }
                    } else {
                        throw new Exception("Thêm LichHenPhucVu thất bại");
                    }
                }

                // Lưu Nhân Viên của Dịch Vụ này
                for (LichHenNhanVien lhnv : lhpv.getDsLichHenNhanVien()) {
                    String sqlLHNV = "INSERT INTO tblLichHenNhanVien (ma, vaiTro, ghiChu, maNhanVien, maLichHenPhucVu) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement psLHNV = conn.prepareStatement(sqlLHNV)) {
                        String maLHNV = lhnv.getMa() != null ? lhnv.getMa() : java.util.UUID.randomUUID().toString();
                        psLHNV.setString(1, maLHNV);
                        psLHNV.setString(2, lhnv.getVaiTro());
                        psLHNV.setString(3, lhnv.getGhiChu());
                        psLHNV.setString(4, lhnv.getNhanVien().getMa());
                        psLHNV.setInt(5, lhpv.getMa());
                        if (psLHNV.executeUpdate() <= 0) {
                            throw new Exception("Thêm LichHenNhanVien thất bại");
                        }
                    }
                }

                // Lưu Nguyên Liệu của Dịch Vụ này
                for (LichHenNguyenLieu lhnl : lhpv.getDsLichHenNguyenLieu()) {
                    String sqlLHNL = "INSERT INTO tblLichHenNguyenLieu (ma, soLuongSuDung, ghiChu, maNguyenLieu, maLichHenPhucVu) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement psLHNL = conn.prepareStatement(sqlLHNL)) {
                        String maLHNL = lhnl.getMa() != null ? lhnl.getMa() : java.util.UUID.randomUUID().toString();
                        psLHNL.setString(1, maLHNL);
                        psLHNL.setInt(2, lhnl.getSoLuongSuDung());
                        psLHNL.setString(3, lhnl.getGhiChu());
                        psLHNL.setString(4, lhnl.getNguyenLieu().getMa());
                        psLHNL.setInt(5, lhpv.getMa());
                        if (psLHNL.executeUpdate() <= 0) {
                            throw new Exception("Thêm LichHenNguyenLieu thất bại");
                        }
                    }
                }
            }

            // Cập nhật trạng thái thông qua các DAO khác
            for (LichHenPhucVu lhpv : lichHen.getDsLichHenPhucVu()) {
                if (!nhanVienDAO.capNhatNhanVienTrongLichHen(conn, lhpv.getDsLichHenNhanVien())) {
                    throw new Exception("Cập nhật trạng thái Nhân Viên thất bại");
                }
                if (!nguyenLieuDAO.capNhatSoLuongNguyenLieu(conn, lhpv.getDsLichHenNguyenLieu())) {
                    throw new Exception("Cập nhật số lượng Nguyên Liệu thất bại");
                }
            }
            // Slot được chọn 1 lần và gán cho tất cả dịch vụ nên chỉ cập nhật list LichHenPhucVu là đủ
            if (!slotDAO.capNhatSlotTrongLichHen(conn, lichHen.getDsLichHenPhucVu())) {
                throw new Exception("Cập nhật trạng thái Slot thất bại");
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
