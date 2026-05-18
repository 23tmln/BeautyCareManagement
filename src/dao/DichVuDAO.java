package dao;

import model.DichVu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DichVuDAO {
    public List<DichVu> timDichVuTheoTen(String ten) {
        List<DichVu> list = new ArrayList<>();
        String sql = "SELECT * FROM tblDichVu WHERE tenDichVu LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + ten + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DichVu dv = new DichVu(
                        rs.getString("ma"),
                        rs.getDouble("giaDichVu"),
                        rs.getString("tenDichVu"),
                        rs.getString("moTaDichVu")
                );
                list.add(dv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public DichVu timTheoMa(String ma) {
        DichVu dv = null;
        String sql = "SELECT * FROM tblDichVu WHERE ma = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ma);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dv = new DichVu(
                        rs.getString("ma"),
                        rs.getDouble("giaDichVu"),
                        rs.getString("tenDichVu"),
                        rs.getString("moTaDichVu")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dv;
    }
}
