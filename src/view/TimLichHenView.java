package view;

import controller.NhanKhachController;
import model.LichHen;
import util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TimLichHenView extends JFrame {
    private JTextField txtTenKhachHang;
    private JButton btnTim, btnThemLichHen, btnTiepTuc;
    private JTable tblLichHen;
    private DefaultTableModel modelLichHen;
    private NhanKhachController controller;
    private List<LichHen> listLichHen;

    public TimLichHenView() {
        controller = new NhanKhachController();
        setTitle("Tìm Kiếm Lịch Hẹn");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTop.add(new JLabel("Tên khách hàng: "));
        txtTenKhachHang = new JTextField(20);
        pnlTop.add(txtTenKhachHang);
        btnTim = new JButton("Tìm");
        pnlTop.add(btnTim);

        // Table
        String[] columns = {"STT", "Mã lịch hẹn", "Tên KH", "SĐT", "Địa chỉ", "Giờ BĐ", "Giờ KT", "Ngày hẹn"};
        modelLichHen = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép sửa trực tiếp trên bảng
            }
        };
        tblLichHen = new JTable(modelLichHen);
        JScrollPane scrollPane = new JScrollPane(tblLichHen);

        // Bottom Panel
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnThemLichHen = new JButton("Thêm lịch hẹn");
        btnTiepTuc = new JButton("Tiếp tục");
        pnlBottom.add(btnThemLichHen);
        pnlBottom.add(btnTiepTuc);

        add(pnlTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);

        // Events
        btnTim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timLichHen();
            }
        });

        btnTiepTuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiepTuc();
            }
        });

        btnThemLichHen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chuyển sang form Thêm Lịch Hẹn
                new ThemLichHenView(TimLichHenView.this).setVisible(true);
            }
        });
    }

    private void timLichHen() {
        String ten = txtTenKhachHang.getText().trim();
        if (ten.isEmpty()) {
            MessageUtil.showWarning(this, "Vui lòng nhập tên khách hàng!");
            return;
        }

        listLichHen = controller.timLichHen(ten);
        modelLichHen.setRowCount(0); // Xóa dữ liệu cũ

        if (listLichHen.isEmpty()) {
            MessageUtil.showInfo(this, "Không tìm thấy lịch hẹn nào!");
            return;
        }

        int stt = 1;
        for (LichHen lh : listLichHen) {
            modelLichHen.addRow(new Object[]{
                    stt++,
                    lh.getMa(),
                    lh.getKhachHang().getTenKhachHang(),
                    lh.getKhachHang().getSoDienThoai(),
                    lh.getKhachHang().getDiaChi(),
                    lh.getGioBatDau(),
                    lh.getGioKetThuc(),
                    lh.getNgayHen()
            });
        }
    }

    private void tiepTuc() {
        int selectedRow = tblLichHen.getSelectedRow();
        if (selectedRow == -1) {
            MessageUtil.showWarning(this, "Vui lòng chọn một lịch hẹn để tiếp tục!");
            return;
        }

        LichHen lhChon = listLichHen.get(selectedRow);
        new TimDichVuView(lhChon).setVisible(true);
        this.dispose();
    }
}
