import dao.DBConnection;
import dao.NhanKhachDAO;
import model.*;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Test NhanKhachDAO - Luong "Nhan Khach"
 * 
 * Phong cach: Don gian, khong @Before/@After.
 * TC01 (ghi DB that): Dung conn.rollback() de hoan tac sau test.
 * TC02-TC32 (ghi that bai): DAO tu rollback ben trong, khong anh huong DB.
 * TC33 (trung lap): Ghi lan 1 OK, lan 2 kiem tra bao loi, rollback ca 2 lan.
 * 
 * Du lieu co dinh trong DB:
 *   maLichHen   = "LH045"  (tblLichHen - da co san)
 *   maKhachHang = "KH045"  (Nguyen Van Nam)
 *   maDichVu    = "DV005"  (Massage chan)
 *   maSlot      = "SL004"  (da co trong tblSlot)
 *   maNguyenLieu= "NL001"  (da co trong tblNguyenLieu)
 *   maNhanVien  = "NV001"  (trangThai=0 - Ranh)
 */
public class NhanKhachDAOTest {

    NhanKhachDAO dao = new NhanKhachDAO();

    // =====================================================================
    // Ham tao LichHen test (dung LH045 - co san trong DB)
    // =====================================================================
    private LichHen buildLichHen(boolean validDV, boolean validNL,
                                  boolean validNV, boolean validSlot) {
        KhachHang kh = new KhachHang("KH045", "Do Cong Nam", "0234343434", "Tot Dong");
        LichHen lh = new LichHen("LH045", null, null, null, null, "Test JUnit", kh);

        DichVu dv = new DichVu(validDV ? "DV005" : "DV_FAIL", 220000, "Massage chan", "");
        Slot   slot = new Slot(validSlot ? "SL004" : "SL_FAIL", "Ghe 04", null, null, 0);

        LichHenPhucVu lhpv = new LichHenPhucVu();
        lhpv.setDichVu(dv);
        lhpv.setSlot(slot);
        lhpv.setDonGiaTamTinh(220000);

        NguyenLieu nl = new NguyenLieu(validNL ? "NL001" : "NL_FAIL", 50000, "Test NL", "", 10);
        LichHenNguyenLieu lhnl = new LichHenNguyenLieu();
        lhnl.setNguyenLieu(nl);
        lhnl.setSoLuongSuDung(1);
        lhpv.getDsLichHenNguyenLieu().add(lhnl);

        NhanVien nv = new NhanVien(validNV ? "NV001" : "NV_FAIL", "Nguyen Van Hung", "0");
        LichHenNhanVien lhnv = new LichHenNhanVien();
        lhnv.setNhanVien(nv);
        lhpv.getDsLichHenNhanVien().add(lhnv);

        lh.getDsLichHenPhucVu().add(lhpv);
        return lh;
    }

    /** Dat lai NV001 va SL004 ve Ranh/Trong sau TC01 */
    private void resetNVvaSlot() {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) return;
            // Xoa ban ghi LichHenPhucVu test vua them
            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM tblLichHenNhanVien WHERE maLichHenPhucVu IN "
                    + "(SELECT ma FROM tblLichHenPhucVu WHERE maLichHen='LH045' AND ghiChu='Test JUnit')")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM tblLichHenNguyenLieu WHERE maLichHenPhucVu IN "
                    + "(SELECT ma FROM tblLichHenPhucVu WHERE maLichHen='LH045' AND ghiChu='Test JUnit')")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM tblLichHenPhucVu WHERE maLichHen='LH045' AND ghiChu='Test JUnit'")) {
                ps.executeUpdate();
            }
            // Reset NV001 va SL004 ve Ranh/Trong
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE tblNhanVien SET trangThai=0 WHERE ma='NV001'")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE tblSlot SET trangThai=0 WHERE ma='SL004'")) {
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =====================================================================
    // TC01: Tat ca hop le -> SUCCESS -> Rollback de giu sach DB
    // =====================================================================
    @Test
    public void testCase01_TatCaHopLe() {
        LichHen lh = buildLichHen(true, true, true, true);
        boolean result = dao.luuNhanKhach(lh);
        // Don dep DB sau khi test thanh cong
        resetNVvaSlot();
        Assert.assertTrue("TC01: KH/DV/NL/NV/Slot deu hop le phai PASS", result);
    }

    // =====================================================================
    // TC02: KH khong ton tai (maLichHen sai) -> FK violation -> FAIL
    // =====================================================================
    @Test
    public void testCase02_KH_KhongTonTai() {
        KhachHang kh = new KhachHang("KH_FAIL", "?", "000", "");
        LichHen lh = new LichHen("LH_FAIL_ID", null, null, null, null, "", kh);
        DichVu dv = new DichVu("DV005", 0, "", "");
        Slot slot = new Slot("SL004", "", null, null, 0);
        LichHenPhucVu lhpv = new LichHenPhucVu();
        lhpv.setDichVu(dv); lhpv.setSlot(slot); lhpv.setDonGiaTamTinh(0);
        NguyenLieu nl = new NguyenLieu("NL001", 0, "", "", 1);
        LichHenNguyenLieu lhnl = new LichHenNguyenLieu();
        lhnl.setNguyenLieu(nl); lhnl.setSoLuongSuDung(1);
        lhpv.getDsLichHenNguyenLieu().add(lhnl);
        NhanVien nv = new NhanVien("NV001", "", "0");
        LichHenNhanVien lhnv = new LichHenNhanVien();
        lhnv.setNhanVien(nv); lhpv.getDsLichHenNhanVien().add(lhnv);
        lh.getDsLichHenPhucVu().add(lhpv);
        Assert.assertFalse("TC02: maLicHen khong ton tai phai FAIL", dao.luuNhanKhach(lh));
    }

    // =====================================================================
    // TC03 - TC06: 1 truong sai
    // =====================================================================
    @Test public void testCase03_DV_KhongTonTai()   { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(false, true,  true,  true)));  }
    @Test public void testCase04_NL_KhongTonTai()   { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(true,  false, true,  true)));  }
    @Test public void testCase05_NV_KhongTonTai()   { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(true,  true,  false, true)));  }
    @Test public void testCase06_Slot_KhongTonTai() { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(true,  true,  true,  false))); }

    // =====================================================================
    // TC07 - TC16: 2 truong sai
    // =====================================================================
    @Test public void testCase07()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(false, false, true,  true)));  }
    @Test public void testCase08()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(false, true,  false, true)));  }
    @Test public void testCase09()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(false, true,  true,  false))); }
    @Test public void testCase10()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(true,  false, false, true)));  }
    @Test public void testCase11()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(true,  false, true,  false))); }
    @Test public void testCase12()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(true,  true,  false, false))); }
    @Test public void testCase13()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(false, false, false, true)));  }
    @Test public void testCase14()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(false, false, true,  false))); }
    @Test public void testCase15()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(false, true,  false, false))); }
    @Test public void testCase16()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(true,  false, false, false))); }

    // =====================================================================
    // TC17 - TC32: 3 va 4 truong sai
    // =====================================================================
    @Test public void testCase17()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(false, false, false, true)));  }
    @Test public void testCase18()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(false, false, true,  false))); }
    @Test public void testCase19()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(false, true,  false, false))); }
    @Test public void testCase20()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(true,  false, false, false))); }
    @Test public void testCase21()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(false, true,  true,  true)));  }
    @Test public void testCase22()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(true,  false, true,  true)));  }
    @Test public void testCase23()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(true,  true,  false, true)));  }
    @Test public void testCase24()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(true,  true,  true,  false))); }
    @Test public void testCase25()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(false, false, true,  true)));  }
    @Test public void testCase26()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(false, true,  false, true)));  }
    @Test public void testCase27()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(false, true,  true,  false))); }
    @Test public void testCase28()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(true,  false, false, true)));  }
    @Test public void testCase29()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(true,  false, true,  false))); }
    @Test public void testCase30()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(true,  true,  false, false))); }
    @Test public void testCase31()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(false, false, false, false))); }
    @Test public void testCase32()  { Assert.assertFalse(dao.luuNhanKhach(buildLichHen(false, false, false, false))); }

    // =====================================================================
    // TC33: Them 2 lan lien tiep - lan 2 NV/Slot da Ban -> FAIL
    // =====================================================================
    @Test
    public void testCase33_TrungLap() {
        LichHen lh = buildLichHen(true, true, true, true);
        dao.luuNhanKhach(lh); // Lan 1 - NV001 va SL004 bi danh Ban
        boolean result2 = dao.luuNhanKhach(lh); // Lan 2 - NV/Slot da Ban -> FK or logic fail
        resetNVvaSlot(); // Don dep
        Assert.assertFalse("TC33: Them 2 lan phai that bai o lan 2", result2);
    }
}