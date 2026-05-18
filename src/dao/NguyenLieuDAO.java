package dao;

import model.NguyenLieu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NguyenLieuDAO {

    public List<NguyenLieu> layTatCa() {
        List<NguyenLieu> list = new ArrayList<>();
        String sql = "SELECT * FROM tblNguyenLieu";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NguyenLieu nl = new NguyenLieu(
                        rs.getString("ma"),
                        rs.getDouble("giaNL"),
                        rs.getString("tenNguyenLieu"),
                        rs.getString("moTaNguyenLieu"),
                        rs.getInt("soLuong")
                );
                list.add(nl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<NguyenLieu> timNguyenLieuTheoTen(String ten) {
        List<NguyenLieu> list = new ArrayList<>();
        String sql = "SELECT * FROM tblNguyenLieu WHERE tenNguyenLieu LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + ten + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NguyenLieu nl = new NguyenLieu(
                        rs.getString("ma"),
                        rs.getDouble("giaNL"),
                        rs.getString("tenNguyenLieu"),
                        rs.getString("moTaNguyenLieu"),
                        rs.getInt("soLuong")
                );
                list.add(nl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public NguyenLieu timTheoMa(String ma) {
        NguyenLieu nl = null;
        String sql = "SELECT * FROM tblNguyenLieu WHERE ma = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ma);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nl = new NguyenLieu(
                        rs.getString("ma"),
                        rs.getDouble("giaNL"),
                        rs.getString("tenNguyenLieu"),
                        rs.getString("moTaNguyenLieu"),
                        rs.getInt("soLuong")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nl;
    }

    public boolean capNhatSoLuongNguyenLieu(Connection conn, List<model.LichHenNguyenLieu> dsLichHenNguyenLieu) {
        String sql = "UPDATE tblNguyenLieu SET soLuong = soLuong - ? WHERE ma = ? AND soLuong >= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (model.LichHenNguyenLieu lhnl : dsLichHenNguyenLieu) {
                ps.setInt(1, lhnl.getSoLuongSuDung());
                ps.setString(2, lhnl.getNguyenLieu().getMa());
                ps.setInt(3, lhnl.getSoLuongSuDung());
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
