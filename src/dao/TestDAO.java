import dao.DBConnection;
import dao.LichHenDAO;
import java.sql.Connection;

public class TestDAO {
    public static void main(String[] args) {
        System.out.println("=== TEST DBConnection ===");

        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Ket noi that bai, dung test");
            return;
        }

        System.out.println("Ket noi thanh cong");

        System.out.println("=== TEST LichHenDAO ===");

        LichHenDAO dao = new LichHenDAO();
        dao.timLichHenTheoTenKhachHang("Nam");

        System.out.println("=== DONE ===");
    }
}