package view;

import model.LichHen;
import model.LichHenPhucVu;
import util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimDichVuView extends JFrame {
    private LichHen lichHen;
    private JTextField txtTenDichVu;
    private JButton btnTim, btnTiepTuc;
    private JTable tblDichVuDaChon;
    private DefaultTableModel modelDichVu;

    public TimDichVuView(LichHen lichHen) {
        this.lichHen = lichHen;
        setTitle("Quản Lý Dịch Vụ - Lịch Hẹn: " + lichHen.getMa());
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Tìm kiếm
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTop.add(new JLabel("Tên Dịch Vụ:"));
        txtTenDichVu = new JTextField(20);
        pnlTop.add(txtTenDichVu);
        btnTim = new JButton("Tìm");
        pnlTop.add(btnTim);
        add(pnlTop, BorderLayout.NORTH);

        // Bảng các dịch vụ đã chọn (Hub)
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createTitledBorder("Các Dịch Vụ Đã Chọn"));
        
        String[] cols = {"STT", "Mã Dịch Vụ", "Tên Dịch Vụ", "Ghi Chú", "Giá"};
        modelDichVu = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDichVuDaChon = new JTable(modelDichVu);
        pnlCenter.add(new JScrollPane(tblDichVuDaChon), BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        // Panel dưới
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnTiepTuc = new JButton("Tiếp Tục (Sang Nguyên Liệu)");
        pnlBottom.add(btnTiepTuc);
        add(pnlBottom, BorderLayout.SOUTH);

        loadData();

        btnTim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = txtTenDichVu.getText().trim();
                new ThemDichVuView(lichHen, keyword).setVisible(true);
                dispose();
            }
        });

        btnTiepTuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lichHen.getDsLichHenPhucVu() == null || lichHen.getDsLichHenPhucVu().isEmpty()) {
                    MessageUtil.showWarning(TimDichVuView.this, "Vui lòng chọn ít nhất 1 Dịch vụ trước khi tiếp tục!");
                    return;
                }
                new TimNguyenLieuView(lichHen).setVisible(true);
                dispose();
            }
        });
    }

    private void loadData() {
        modelDichVu.setRowCount(0);
        if (lichHen.getDsLichHenPhucVu() != null) {
            int stt = 1;
            for (LichHenPhucVu lhpv : lichHen.getDsLichHenPhucVu()) {
                modelDichVu.addRow(new Object[]{
                        stt++,
                        lhpv.getDichVu().getMa(),
                        lhpv.getDichVu().getTenDichVu(),
                        lhpv.getGhiChu(),
                        String.format("%.0fđ", lhpv.getDonGiaTamTinh())
                });
            }
        }
    }
}
