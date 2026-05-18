package view;

import controller.NhanKhachController;
import model.LichHen;
import model.LichHenPhucVu;
import model.Slot;
import util.MessageUtil;
import util.ValidationUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChonSlotView extends JFrame {
    private LichHen lichHen;
    private JTextField txtThoiGian;
    private JButton btnTim, btnTiepTuc;
    private JTable tblSlot;
    private DefaultTableModel modelSlot;
    private NhanKhachController controller;
    private List<Slot> listSlot;

    public ChonSlotView(LichHen lichHen) {
        this.lichHen = lichHen;
        this.controller = new NhanKhachController();

        setTitle("GDChonSlot - Chọn Slot Chung");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Panel
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTop.add(new JLabel("Thời gian (HH:mm:ss): "));
        txtThoiGian = new JTextField("08:00:00", 15);
        pnlTop.add(txtThoiGian);
        btnTim = new JButton("Tìm");
        pnlTop.add(btnTim);
        add(pnlTop, BorderLayout.NORTH);

        // Center Table
        String[] cols = {"STT", "Mã Slot", "Phòng/Vị trí", "Giờ BĐ", "Giờ KT", "Trạng thái", "Chọn"};
        modelSlot = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Cho phép Checkbox cột 6
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 6) return Boolean.class;
                return String.class;
            }
        };
        tblSlot = new JTable(modelSlot);
        // Chỉ chọn 1 Slot duy nhất
        tblSlot.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tblSlot), BorderLayout.CENTER);

        // Bottom Panel
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnTiepTuc = new JButton("Tiếp Tục");
        pnlBottom.add(btnTiepTuc);
        add(pnlBottom, BorderLayout.SOUTH);

        // Actions
        btnTim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timSlot();
            }
        });

        btnTiepTuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xacNhanSlot();
            }
        });
    }

    private void timSlot() {
        String thoiGian = txtThoiGian.getText().trim();
        if (!ValidationUtil.isTimeFormat(thoiGian)) {
            MessageUtil.showError(this, "Sai định dạng thời gian. Vui lòng nhập HH:mm:ss");
            return;
        }

        listSlot = controller.timSlotTrong(thoiGian);
        modelSlot.setRowCount(0);

        if (listSlot == null || listSlot.isEmpty()) {
            MessageUtil.showInfo(this, "Không có slot trống vào thời gian này!");
            return;
        }

        int stt = 1;
        for (Slot slot : listSlot) {
            modelSlot.addRow(new Object[]{
                    stt++,
                    slot.getMa(),
                    slot.getViTri(),
                    slot.getGioBatDau(),
                    slot.getGioKetThuc(),
                    slot.getTrangThai() == 0 ? "Trống" : "Đã dùng",
                    false
            });
        }
    }

    private void xacNhanSlot() {
        Slot slotChon = null;
        for (int i = 0; i < tblSlot.getRowCount(); i++) {
            Boolean isSelected = (Boolean) tblSlot.getValueAt(i, 6);
            if (isSelected != null && isSelected) {
                slotChon = listSlot.get(i);
                break; // Vì yêu cầu chỉ chọn 1 slot
            }
        }

        if (slotChon == null) {
            MessageUtil.showWarning(this, "Vui lòng chọn một slot!");
            return;
        }

        // Gán slot này cho tất cả dịch vụ
        if (lichHen.getDsLichHenPhucVu() != null) {
            for (LichHenPhucVu lhpv : lichHen.getDsLichHenPhucVu()) {
                lhpv.setSlot(slotChon);
            }
        }

        new XacNhanView(lichHen).setVisible(true);
        this.dispose();
    }
}
