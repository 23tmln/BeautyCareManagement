package dao;

import model.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    public List<NhanVien> layNhanVienRanh() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM tblNhanVien WHERE trangThai = 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getString("ma"),
                        rs.getString("tenNhanVien"),
                        rs.getInt("trangThai") == 0 ? "Rảnh" : "Bận"
                );
                list.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public NhanVien timTheoMa(String ma) {
        NhanVien nv = null;
        String sql = "SELECT * FROM tblNhanVien WHERE ma = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ma);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nv = new NhanVien(
                        rs.getString("ma"),
                        rs.getString("tenNhanVien"),
                        rs.getInt("trangThai") == 0 ? "Rảnh" : "Bận"
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nv;
    }

    public boolean capNhatNhanVienTrongLichHen(Connection conn, List<model.LichHenNhanVien> dsLichHenNhanVien) {
        String sql = "UPDATE tblNhanVien SET trangThai = ? WHERE ma = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (model.LichHenNhanVien lhnv : dsLichHenNhanVien) {
                ps.setInt(1, 1); // 1 = Bận
                ps.setString(2, lhnv.getNhanVien().getMa());
                ps.addBatch();
            }
            int[] results = ps.executeBatch();
            for (int res : results) {
                if (res <= 0 && res != PreparedStatement.SUCCESS_NO_INFO) return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
