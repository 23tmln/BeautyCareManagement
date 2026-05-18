package dao;

import model.Slot;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SlotDAO {

    public List<Slot> timSlotTrongTheoThoiGian(String thoiGian) {
        List<Slot> list = new ArrayList<>();
        // Sử dụng CONVERT sang định dạng 108 (HH:mm:ss) để so sánh chuỗi chính xác 100% với input của người dùng
        String sql = "SELECT * FROM tblSlot WHERE trangThai = 0 AND CONVERT(VARCHAR, gioBatDau, 108) = ?";
        System.out.println("[DEBUG SlotDAO] thoiGian input: '" + thoiGian + "'");
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, thoiGian);
            System.out.println("[DEBUG SlotDAO] Executing query: " + sql);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
                Slot slot = new Slot(
                        rs.getString("ma"),
                        rs.getString("viTri"),
                        rs.getTime("gioBatDau"),
                        rs.getTime("gioKetThuc"),
                        rs.getInt("trangThai")
                );
                list.add(slot);
            }
            System.out.println("[DEBUG SlotDAO] Found " + count + " slots.");
        } catch (Exception e) {
            System.out.println("[DEBUG SlotDAO] ERROR:");
            e.printStackTrace();
        }
        return list;
    }

    public Slot timTheoMa(String ma) {
        Slot slot = null;
        String sql = "SELECT * FROM tblSlot WHERE ma = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ma);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                slot = new Slot(
                        rs.getString("ma"),
                        rs.getString("viTri"),
                        rs.getTime("gioBatDau"),
                        rs.getTime("gioKetThuc"),
                        rs.getInt("trangThai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slot;
    }

    public boolean capNhatSlotTrongLichHen(Connection conn, List<model.LichHenPhucVu> dsLichHenPhucVu) {
        if (dsLichHenPhucVu == null || dsLichHenPhucVu.isEmpty()) return true;
        
        // Tất cả dịch vụ trong 1 Lịch Hẹn dùng chung 1 Slot, nên chỉ cần lấy Slot của dịch vụ đầu tiên
        Slot slot = dsLichHenPhucVu.get(0).getSlot();
        if (slot == null) return true;

        String sql = "UPDATE tblSlot SET trangThai = ? WHERE ma = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 1); // 1 = Bận
            ps.setString(2, slot.getMa());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
