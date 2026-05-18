package view;

import util.MessageUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThemLichHenView extends JFrame {
    private JTextField txtTenKhachHang, txtSoDienThoai, txtDiaChi, txtNgayHen, txtGioBatDau, txtGioKetThuc;
    private JButton btnThem, btnHuy;
    private TimLichHenView parentView;

    public ThemLichHenView(TimLichHenView parent) {
        this.parentView = parent;
        setTitle("Thêm Lịch Hẹn");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel pnlForm = new JPanel(new GridLayout(7, 2, 10, 10));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pnlForm.add(new JLabel("Tên KH:"));
        txtTenKhachHang = new JTextField();
        pnlForm.add(txtTenKhachHang);

        pnlForm.add(new JLabel("SĐT:"));
        txtSoDienThoai = new JTextField();
        pnlForm.add(txtSoDienThoai);

        pnlForm.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        pnlForm.add(txtDiaChi);

        pnlForm.add(new JLabel("Ngày hẹn (yyyy-MM-dd):"));
        txtNgayHen = new JTextField();
        pnlForm.add(txtNgayHen);

        pnlForm.add(new JLabel("Giờ bắt đầu (HH:mm:ss):"));
        txtGioBatDau = new JTextField();
        pnlForm.add(txtGioBatDau);

        pnlForm.add(new JLabel("Giờ kết thúc (HH:mm:ss):"));
        txtGioKetThuc = new JTextField();
        pnlForm.add(txtGioKetThuc);

        btnThem = new JButton("Thêm");
        btnHuy = new JButton("Hủy");
        pnlForm.add(btnThem);
        pnlForm.add(btnHuy);

        add(pnlForm);

        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Giả lập lưu thành công (Do yêu cầu bài tập ko tập trung phần lưu này, ta hiển thị thành công luôn)
                MessageUtil.showInfo(ThemLichHenView.this, "Đã thêm lịch hẹn thành công (Giả lập)!");
                dispose();
            }
        });
    }
}
