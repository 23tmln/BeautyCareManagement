package view;

import controller.NhanKhachController;
import model.DichVu;
import model.LichHen;
import model.LichHenPhucVu;
import util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ThemDichVuView extends JFrame {
    private LichHen lichHen;
    private JTable tblDichVu;
    private DefaultTableModel modelDichVu;
    private JButton btnXacNhan;
    private List<DichVu> listDichVu;
    private String searchKeyword;

    public ThemDichVuView(LichHen lichHen, String searchKeyword) {
        this.lichHen = lichHen;
        this.searchKeyword = searchKeyword;
        setTitle("Thêm Dịch Vụ - Lịch Hẹn: " + lichHen.getMa());
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Bảng kết quả
        String[] cols = {"STT", "Mã Dịch Vụ", "Tên Dịch Vụ", "Giá Dịch Vụ", "GhiChu", "Chọn"};
        modelDichVu = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5; // Chỉ cho phép sửa cột GhiChu và Chọn
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 5) return Boolean.class; // Hiển thị Checkbox
                return String.class;
            }
        };
        tblDichVu = new JTable(modelDichVu);
        add(new JScrollPane(tblDichVu), BorderLayout.CENTER);

        // Panel dưới
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnXacNhan = new JButton("Xác Nhận");
        pnlBottom.add(btnXacNhan);
        add(pnlBottom, BorderLayout.SOUTH);

        loadData();

        btnXacNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xacNhanThemDichVu();
            }
        });
    }

    private void loadData() {
        NhanKhachController controller = new NhanKhachController();
        listDichVu = controller.timDichVu(searchKeyword);
        int stt = 1;
        for (DichVu dv : listDichVu) {
            modelDichVu.addRow(new Object[]{
                    stt++,
                    dv.getMa(),
                    dv.getTenDichVu(),
                    String.format("%.0fđ", dv.getGiaDichVu()),
                    "", // Ghi chú trống
                    false // Mặc định không chọn
            });
        }
    }

    private void xacNhanThemDichVu() {
        boolean hasSelected = false;
        // Duyệt qua các dòng trong bảng để lấy dịch vụ được chọn
        for (int i = 0; i < tblDichVu.getRowCount(); i++) {
            Boolean isSelected = (Boolean) tblDichVu.getValueAt(i, 5);
            if (isSelected != null && isSelected) {
                hasSelected = true;
                DichVu dv = listDichVu.get(i);
                String ghiChu = (String) tblDichVu.getValueAt(i, 4);
                
                LichHenPhucVu lhpv = new LichHenPhucVu();
                lhpv.setDichVu(dv);
                lhpv.setGhiChu(ghiChu != null ? ghiChu : "");
                lhpv.setDonGiaTamTinh(dv.getGiaDichVu());
                lhpv.setLichHen(lichHen);
                
                lichHen.getDsLichHenPhucVu().add(lhpv);
            }
        }

        if (!hasSelected) {
            MessageUtil.showWarning(this, "Vui lòng chọn ít nhất một dịch vụ!");
            return;
        }

        // Quay lại màn hình Hub Dịch Vụ
        new TimDichVuView(lichHen).setVisible(true);
        this.dispose();
    }
}
