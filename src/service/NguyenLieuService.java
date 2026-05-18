package service;

import dao.NguyenLieuDAO;
import model.NguyenLieu;
import java.util.ArrayList;
import java.util.List;

public class NguyenLieuService {
    private NguyenLieuDAO nguyenLieuDAO = new NguyenLieuDAO();

    public List<NguyenLieu> layTatCaNguyenLieu() {
        return nguyenLieuDAO.layTatCa();
    }
    
    public List<NguyenLieu> timNguyenLieuTheoTen(String ten) {
        if (ten == null || ten.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return nguyenLieuDAO.timNguyenLieuTheoTen(ten.trim());
    }
}
