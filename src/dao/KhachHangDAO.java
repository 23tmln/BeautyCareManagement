package dao;

import model.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KhachHangDAO {
    public KhachHang timTheoMa(String ma) {
        KhachHang kh = null;
        String sql = "SELECT * FROM tblKhachHang WHERE ma = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ma);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                kh = new KhachHang(
                        rs.getString("ma"),
                        rs.getString("tenKhachHang"),
                        rs.getString("soDienThoai"),
                        rs.getString("diaChi")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    }
}
