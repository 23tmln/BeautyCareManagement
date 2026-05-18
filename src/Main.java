import view.TrangChuNhanVienView;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Khởi chạy ứng dụng Java Swing trong luồng EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Cài đặt Look and Feel hệ thống để giao diện đẹp hơn
                    javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                TrangChuNhanVienView app = new TrangChuNhanVienView();
                app.setVisible(true);
            }
        });
    }
}
