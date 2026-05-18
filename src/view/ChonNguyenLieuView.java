package view;

import controller.NhanKhachController;
import model.LichHen;
import model.LichHenPhucVu;
import model.LichHenNguyenLieu;
import model.NguyenLieu;
import util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChonNguyenLieuView extends JFrame {
    private LichHen lichHen;
    private int currentIndex;
    private JTable tblNguyenLieu;
    private DefaultTableModel modelNguyenLieu;
    private JButton btnXacNhan, btnTim;
    private JTextField txtTimKiem;
    private List<NguyenLieu> listNguyenLieu;
    private NhanKhachController controller;

    public ChonNguyenLieuView(LichHen lichHen, int currentIndex) {
        this.lichHen = lichHen;
        this.currentIndex = currentIndex;
        this.controller = new NhanKhachController();
        
        LichHenPhucVu currentDichVu = lichHen.getDsLichHenPhucVu().get(currentIndex);

        setTitle("Chọn Nguyên Liệu - Dịch Vụ: " + currentDichVu.getDichVu().getTenDichVu());
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Search Panel
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTop.add(new JLabel("Tên Nguyên Liệu:"));
        txtTimKiem = new JTextField(15);
        pnlTop.add(txtTimKiem);
        btnTim = new JButton("Tìm");
        pnlTop.add(btnTim);
        add(pnlTop, BorderLayout.NORTH);

        // Bảng kết quả
        String[] cols = {"STT", "Mã NL", "Tên Nguyên Liệu", "Giá", "Tồn kho", "SL Dùng", "Ghi Chú", "Chọn"};
        modelNguyenLieu = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 6 || column == 7; // Cho phép sửa Số lượng, Ghi chú, Chọn
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 7) return Boolean.class;
                return String.class;
            }
        };
        tblNguyenLieu = new JTable(modelNguyenLieu);
        add(new JScrollPane(tblNguyenLieu), BorderLayout.CENTER);

        // Panel dưới
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnXacNhan = new JButton("Xác Nhận (Quay lại)");
        pnlBottom.add(btnXacNhan);
        add(pnlBottom, BorderLayout.SOUTH);

        loadData("");

        btnTim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData(txtTimKiem.getText().trim());
            }
        });

        btnXacNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xacNhanNguyenLieu();
            }
        });
    }

    private void loadData(String keyword) {
        modelNguyenLieu.setRowCount(0);
        if (keyword.isEmpty()) {
            listNguyenLieu = controller.layDanhSachNguyenLieu();
        } else {
            listNguyenLieu = controller.timNguyenLieu(keyword);
        }
        
        int stt = 1;
        for (NguyenLieu nl : listNguyenLieu) {
            modelNguyenLieu.addRow(new Object[]{
                    stt++,
                    nl.getMa(),
                    nl.getTenNguyenLieu(),
                    String.format("%.0fđ", nl.getGiaNL()),
                    String.valueOf(nl.getSoLuong()),
                    "1", // Mặc định SL dùng = 1
                    "",  // Ghi chú trống
                    false // Mặc định không chọn
            });
        }
    }

    private void xacNhanNguyenLieu() {
        LichHenPhucVu currentDichVu = lichHen.getDsLichHenPhucVu().get(currentIndex);
        
        for (int i = 0; i < tblNguyenLieu.getRowCount(); i++) {
            Boolean isSelected = (Boolean) tblNguyenLieu.getValueAt(i, 7);
            if (isSelected != null && isSelected) {
                NguyenLieu nl = listNguyenLieu.get(i);
                
                String slStr = (String) tblNguyenLieu.getValueAt(i, 5);
                int slDung = 0;
                try {
                    slDung = Integer.parseInt(slStr);
                    if (slDung <= 0) throw new NumberFormatException();
                    if (slDung > nl.getSoLuong()) {
                        MessageUtil.showError(this, "Số lượng nguyên liệu " + nl.getTenNguyenLieu() + " không đủ trong kho!");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    MessageUtil.showError(this, "Số lượng sử dụng không hợp lệ tại dòng " + (i + 1));
                    return;
                }

                String ghiChu = (String) tblNguyenLieu.getValueAt(i, 6);
                
                LichHenNguyenLieu lhnl = new LichHenNguyenLieu();
                lhnl.setNguyenLieu(nl);
                lhnl.setSoLuongSuDung(slDung);
                lhnl.setGhiChu(ghiChu != null ? ghiChu : "");
                lhnl.setLichHenPhucVu(currentDichVu);
                
                currentDichVu.getDsLichHenNguyenLieu().add(lhnl);
            }
        }

        // Quay lại màn hình Hub Nguyên Liệu
        new TimNguyenLieuView(lichHen).setVisible(true);
        this.dispose();
    }
}
