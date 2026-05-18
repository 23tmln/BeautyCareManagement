package service;

import dao.DichVuDAO;
import model.DichVu;
import java.util.ArrayList;
import java.util.List;

public class DichVuService {
    private DichVuDAO dichVuDAO = new DichVuDAO();

    public List<DichVu> timDichVuTheoTen(String ten) {
        if (ten == null || ten.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return dichVuDAO.timDichVuTheoTen(ten.trim());
    }
}
