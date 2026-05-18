package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrangChuNhanVienView extends JFrame {
    private JButton btnNhanKhach;

    public TrangChuNhanVienView() {
        setTitle("Trang Chủ Nhân Viên");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel pnlTitle = new JPanel();
        JLabel lblTitle = new JLabel("Trang Chủ Nhân Viên");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        pnlTitle.add(lblTitle);

        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        JLabel lblTenNV = new JLabel("Xin chào: Lễ Tân 1");
        lblTenNV.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnNhanKhach = new JButton("Nhận Khách");
        btnNhanKhach.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Padding
        pnlCenter.add(Box.createRigidArea(new Dimension(0, 50)));
        pnlCenter.add(lblTenNV);
        pnlCenter.add(Box.createRigidArea(new Dimension(0, 20)));
        pnlCenter.add(btnNhanKhach);

        add(pnlTitle, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);

        btnNhanKhach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TimLichHenView().setVisible(true);
                dispose(); // Đóng trang chủ
            }
        });
    }
}
