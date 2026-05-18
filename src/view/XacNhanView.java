package view;

import controller.NhanKhachController;
import model.LichHen;
import model.LichHenPhucVu;
import model.LichHenNguyenLieu;
import model.LichHenNhanVien;
import util.MessageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class XacNhanView extends JFrame {
    private LichHen lichHen;
    private NhanKhachController controller;

    public XacNhanView(LichHen lichHen) {
        this.lichHen = lichHen;
        this.controller = new NhanKhachController();

        setTitle("Xác Nhận Nhận Khách");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel pnlContent = new JPanel();
        pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
        pnlContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        double tongTien = 0;

        // THÔNG TIN KHÁCH HÀNG
        addTitle(pnlContent, "THÔNG TIN KHÁCH HÀNG");
        addInfo(pnlContent, "Khách hàng:", lichHen.getKhachHang().getTenKhachHang());
        addInfo(pnlContent, "Số điện thoại:", lichHen.getKhachHang().getSoDienThoai());
        addInfo(pnlContent, "Lịch hẹn:", lichHen.getMa());
        
        // Slot
        String slotInfo = "Chưa chọn";
        if (lichHen.getDsLichHenPhucVu() != null && !lichHen.getDsLichHenPhucVu().isEmpty()) {
            if (lichHen.getDsLichHenPhucVu().get(0).getSlot() != null) {
                slotInfo = lichHen.getDsLichHenPhucVu().get(0).getSlot().getMa() + 
                           " (Phòng/Vị trí: " + lichHen.getDsLichHenPhucVu().get(0).getSlot().getViTri() + ")";
            }
        }
        addInfo(pnlContent, "Nơi phục vụ:", slotInfo);
        
        pnlContent.add(rigidArea(15));
        addTitle(pnlContent, "CHI TIẾT DỊCH VỤ");

        // CHI TIẾT TỪNG DỊCH VỤ
        if (lichHen.getDsLichHenPhucVu() != null) {
            for (LichHenPhucVu lhpv : lichHen.getDsLichHenPhucVu()) {
                double tienDV = lhpv.getDonGiaTamTinh();
                
                JPanel pnlDV = new JPanel(new BorderLayout());
                pnlDV.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                
                JPanel pnlInner = new JPanel();
                pnlInner.setLayout(new BoxLayout(pnlInner, BoxLayout.Y_AXIS));
                pnlInner.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                
                addInfo(pnlInner, "Dịch vụ:", lhpv.getDichVu().getTenDichVu() + " - " + String.format("%.0f VNĐ", lhpv.getDonGiaTamTinh()));
                
                // Nguyên liệu
                if (lhpv.getDsLichHenNguyenLieu() != null) {
                    for (LichHenNguyenLieu lhnl : lhpv.getDsLichHenNguyenLieu()) {
                        double tienNL = lhnl.getNguyenLieu().getGiaNL() * lhnl.getSoLuongSuDung();
                        tienDV += tienNL;
                        addInfo(pnlInner, " + Nguyên liệu:", lhnl.getNguyenLieu().getTenNguyenLieu() + " (SL: " + lhnl.getSoLuongSuDung() + ") - " + String.format("%.0f VNĐ", tienNL));
                    }
                }
                
                // Nhân viên
                if (lhpv.getDsLichHenNhanVien() != null) {
                    for (LichHenNhanVien lhnv : lhpv.getDsLichHenNhanVien()) {
                        addInfo(pnlInner, " + Nhân viên:", lhnv.getNhanVien().getTenNhanVien() + " (" + lhnv.getVaiTro() + ")");
                    }
                }
                
                tongTien += tienDV;
                pnlDV.add(pnlInner, BorderLayout.CENTER);
                pnlDV.setAlignmentX(Component.LEFT_ALIGNMENT);
                pnlContent.add(pnlDV);
                pnlContent.add(rigidArea(10));
            }
        }

        pnlContent.add(rigidArea(10));
        addTitle(pnlContent, "TỔNG TIỀN TẠM TÍNH: " + String.format("%.0f VNĐ", tongTien));

        // Bọc pnlContent vào BorderLayout.NORTH để không bị giãn theo chiều dọc trong JScrollPane
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(pnlContent, BorderLayout.NORTH);
        add(new JScrollPane(wrapper), BorderLayout.CENTER);

        // Nút bấm
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnHuy = new JButton("Hủy");
        JButton btnXacNhan = new JButton("Xác nhận lưu DB");
        pnlBottom.add(btnHuy);
        pnlBottom.add(btnXacNhan);
        add(pnlBottom, BorderLayout.SOUTH);

        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TrangChuNhanVienView().setVisible(true);
                dispose();
            }
        });

        btnXacNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xacNhanThem();
            }
        });
    }

    private void addTitle(JPanel panel, String title) {
        JLabel lblTitle = new JLabel("<html><b>" + title + "</b></html>");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblTitle);
        panel.add(rigidArea(5));
    }

    private Component rigidArea(int height) {
        JComponent comp = (JComponent) Box.createRigidArea(new Dimension(0, height));
        comp.setAlignmentX(Component.LEFT_ALIGNMENT);
        return comp;
    }

    private void addInfo(JPanel panel, String label, String value) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lblLabel = new JLabel(label);
        lblLabel.setPreferredSize(new Dimension(150, 20));
        JLabel lblValue = new JLabel(value);
        p.add(lblLabel);
        p.add(lblValue);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, p.getPreferredSize().height));
        panel.add(p);
    }

    private void xacNhanThem() {
        boolean success = controller.luuNhanKhach(lichHen);
        if (success) {
            MessageUtil.showInfo(this, "Tiếp nhận khách thành công! Đã lưu vào Cơ sở dữ liệu.");
            new TrangChuNhanVienView().setVisible(true);
            this.dispose();
        } else {
            MessageUtil.showError(this, "Lỗi tiếp nhận khách! Vui lòng kiểm tra lại trạng thái DB.");
        }
    }
}