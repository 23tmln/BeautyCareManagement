package view;

import model.LichHen;
import model.LichHenPhucVu;
import util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimNguyenLieuView extends JFrame {
    private LichHen lichHen;
    private JTable tblDichVu;
    private DefaultTableModel modelDichVu;
    private JButton btnChonNguyenLieu, btnTiepTuc;

    public TimNguyenLieuView(LichHen lichHen) {
        this.lichHen = lichHen;
        setTitle("Quản Lý Nguyên Liệu - Trạm Trung Chuyển");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Bảng danh sách dịch vụ
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createTitledBorder("Chọn Dịch Vụ Cần Thêm Nguyên Liệu"));
        
        String[] cols = {"STT", "Tên Dịch Vụ", "Số Loại Nguyên Liệu Đã Chọn"};
        modelDichVu = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDichVu = new JTable(modelDichVu);
        tblDichVu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pnlCenter.add(new JScrollPane(tblDichVu), BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        // Panel dưới
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnChonNguyenLieu = new JButton("Chọn Nguyên Liệu Cho Dịch Vụ Này");
        btnTiepTuc = new JButton("Tiếp Tục (Sang Nhân Viên)");
        pnlBottom.add(btnChonNguyenLieu);
        pnlBottom.add(btnTiepTuc);
        add(pnlBottom, BorderLayout.SOUTH);

        loadData();

        btnChonNguyenLieu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblDichVu.getSelectedRow();
                if (selectedRow == -1) {
                    MessageUtil.showWarning(TimNguyenLieuView.this, "Vui lòng chọn 1 dịch vụ trên bảng!");
                    return;
                }
                new ChonNguyenLieuView(lichHen, selectedRow).setVisible(true);
                dispose();
            }
        });

        btnTiepTuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PhanCongNhanVienView(lichHen).setVisible(true);
                dispose();
            }
        });
    }

    private void loadData() {
        modelDichVu.setRowCount(0);
        if (lichHen.getDsLichHenPhucVu() != null) {
            int stt = 1;
            for (LichHenPhucVu lhpv : lichHen.getDsLichHenPhucVu()) {
                int countNL = (lhpv.getDsLichHenNguyenLieu() != null) ? lhpv.getDsLichHenNguyenLieu().size() : 0;
                modelDichVu.addRow(new Object[]{
                        stt++,
                        lhpv.getDichVu().getTenDichVu(),
                        countNL
                });
            }
        }
    }
}
