package view;

import model.LichHen;
import model.LichHenPhucVu;
import util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhanCongNhanVienView extends JFrame {
    private LichHen lichHen;
    private JTable tblDichVu;
    private DefaultTableModel modelDichVu;
    private JButton btnChonNhanVien, btnTiepTuc;

    public PhanCongNhanVienView(LichHen lichHen) {
        this.lichHen = lichHen;
        setTitle("Phân Công Nhân Viên - Trạm Trung Chuyển");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Bảng danh sách dịch vụ
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createTitledBorder("Chọn Dịch Vụ Cần Phân Công Nhân Viên"));
        
        String[] cols = {"STT", "Tên Dịch Vụ", "Số Nhân Viên Đã Chọn"};
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
        btnChonNhanVien = new JButton("Chọn Nhân Viên Cho Dịch Vụ Này");
        btnTiepTuc = new JButton("Tiếp Tục (Sang Chọn Slot)");
        pnlBottom.add(btnChonNhanVien);
        pnlBottom.add(btnTiepTuc);
        add(pnlBottom, BorderLayout.SOUTH);

        loadData();

        btnChonNhanVien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblDichVu.getSelectedRow();
                if (selectedRow == -1) {
                    MessageUtil.showWarning(PhanCongNhanVienView.this, "Vui lòng chọn 1 dịch vụ trên bảng!");
                    return;
                }
                new ChonNhanVienView(lichHen, selectedRow).setVisible(true);
                dispose();
            }
        });

        btnTiepTuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChonSlotView(lichHen).setVisible(true);
                dispose();
            }
        });
    }

    private void loadData() {
        modelDichVu.setRowCount(0);
        if (lichHen.getDsLichHenPhucVu() != null) {
            int stt = 1;
            for (LichHenPhucVu lhpv : lichHen.getDsLichHenPhucVu()) {
                int countNV = (lhpv.getDsLichHenNhanVien() != null) ? lhpv.getDsLichHenNhanVien().size() : 0;
                modelDichVu.addRow(new Object[]{
                        stt++,
                        lhpv.getDichVu().getTenDichVu(),
                        countNV
                });
            }
        }
    }
}
