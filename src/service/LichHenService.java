package service;

import dao.LichHenDAO;
import model.LichHen;
import java.util.ArrayList;
import java.util.List;

public class LichHenService {
    private LichHenDAO lichHenDAO = new LichHenDAO();

    public List<LichHen> timLichHenTheoTenKhachHang(String ten) {
        if (ten == null || ten.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return lichHenDAO.timLichHenTheoTenKhachHang(ten.trim());
    }
}
