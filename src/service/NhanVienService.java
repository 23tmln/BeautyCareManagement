package service;

import dao.NhanVienDAO;
import model.NhanVien;
import java.util.List;

public class NhanVienService {
    private NhanVienDAO nhanVienDAO = new NhanVienDAO();

    public List<NhanVien> layNhanVienRanh() {
        return nhanVienDAO.layNhanVienRanh();
    }
}
