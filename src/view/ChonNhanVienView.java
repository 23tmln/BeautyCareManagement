package view;

import controller.NhanKhachController;
import model.LichHen;
import model.LichHenPhucVu;
import model.LichHenNhanVien;
import model.NhanVien;
import util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChonNhanVienView extends JFrame {
    private LichHen lichHen;
    private int currentIndex;
    private JTable tblNhanVien;
    private DefaultTableModel modelNhanVien;
    private JButton btnXacNhan;
    private List<NhanVien> listNhanVien;
    private NhanKhachController controller;

    public ChonNhanVienView(LichHen lichHen, int currentIndex) {
        this.lichHen = lichHen;
        this.currentIndex = currentIndex;
        this.controller = new NhanKhachController();

        LichHenPhucVu currentDichVu = lichHen.getDsLichHenPhucVu().get(currentIndex);

        setTitle("Chọn Nhân Viên - Dịch Vụ: " + currentDichVu.getDichVu().getTenDichVu());
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Bảng kết quả
        String[] cols = {"STT", "Mã NV", "Tên Nhân Viên", "Trạng Thái", "Vai Trò", "Ghi Chú", "Chọn"};
        modelNhanVien = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5 || column == 6; // Cho phép sửa Vai trò, Ghi chú, Chọn
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 6) return Boolean.class;
                return String.class;
            }
        };
        tblNhanVien = new JTable(modelNhanVien);
        add(new JScrollPane(tblNhanVien), BorderLayout.CENTER);

        // Panel dưới
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnXacNhan = new JButton("Xác Nhận (Quay Lại)");
        pnlBottom.add(btnXacNhan);
        add(pnlBottom, BorderLayout.SOUTH);

        loadData();

        btnXacNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xacNhanNhanVien();
            }
        });
    }

    private void loadData() {
        listNhanVien = controller.layDanhSachNhanVienRanh();
        
        int stt = 1;
        for (NhanVien nv : listNhanVien) {
            modelNhanVien.addRow(new Object[]{
                    stt++,
                    nv.getMa(),
                    nv.getTenNhanVien(),
                    nv.getTrangThai(),
                    "Thợ chính", // Mặc định Vai trò
                    "",          // Ghi chú trống
                    false        // Mặc định không chọn
            });
        }
    }

    private void xacNhanNhanVien() {
        LichHenPhucVu currentDichVu = lichHen.getDsLichHenPhucVu().get(currentIndex);
        
        for (int i = 0; i < tblNhanVien.getRowCount(); i++) {
            Boolean isSelected = (Boolean) tblNhanVien.getValueAt(i, 6);
            if (isSelected != null && isSelected) {
                NhanVien nv = listNhanVien.get(i);
                String vaiTro = (String) tblNhanVien.getValueAt(i, 4);
                String ghiChu = (String) tblNhanVien.getValueAt(i, 5);
                
                LichHenNhanVien lhnv = new LichHenNhanVien();
                lhnv.setNhanVien(nv);
                lhnv.setVaiTro(vaiTro != null ? vaiTro : "");
                lhnv.setGhiChu(ghiChu != null ? ghiChu : "");
                lhnv.setLichHenPhucVu(currentDichVu);
                
                currentDichVu.getDsLichHenNhanVien().add(lhnv);
            }
        }

        // Quay lại màn hình Hub Phân Công Nhân Viên
        new PhanCongNhanVienView(lichHen).setVisible(true);
        this.dispose();
    }
}
