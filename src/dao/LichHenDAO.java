package dao;

import model.KhachHang;
import model.LichHen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LichHenDAO {
    private KhachHangDAO khachHangDAO = new KhachHangDAO();

    public List<LichHen> timLichHenTheoTenKhachHang(String ten) {
        List<LichHen> list = new ArrayList<>();
        String sql = "SELECT lh.* FROM tblLichHen lh " +
                     "JOIN tblKhachHang kh ON lh.maKhachHang = kh.ma " +
                     "WHERE kh.tenKhachHang LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + ten + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = khachHangDAO.timTheoMa(rs.getString("maKhachHang"));
                LichHen lh = new LichHen(
                        rs.getString("ma"),
                        rs.getTime("gioBatDau"),
                        rs.getTime("gioKetThuc"),
                        rs.getDate("ngayHen"),
                        rs.getDate("ngayDat"),
                        rs.getString("ghiChu"),
                        kh
                );
                list.add(lh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean themLichHen(LichHen lichHen) {
        // Chưa dùng tới trong luồng chuẩn nhưng đề bài có yêu cầu
        return false;
    }

    public LichHen timTheoMa(String ma) {
        LichHen lh = null;
        String sql = "SELECT * FROM tblLichHen WHERE ma = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ma);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                KhachHang kh = khachHangDAO.timTheoMa(rs.getString("maKhachHang"));
                lh = new LichHen(
                        rs.getString("ma"),
                        rs.getTime("gioBatDau"),
                        rs.getTime("gioKetThuc"),
                        rs.getDate("ngayHen"),
                        rs.getDate("ngayDat"),
                        rs.getString("ghiChu"),
                        kh
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lh;
    }
}
