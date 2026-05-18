import itertools

attributes = ['KH', 'DV', 'NL', 'NV', 'Slot']

lines = []
lines.append('package dao;')
lines.append('')
lines.append('import model.*;')
lines.append('import org.junit.Assert;')
lines.append('import org.junit.Test;')
lines.append('import java.util.UUID;')
lines.append('')
lines.append('public class NhanKhachDAOTest {')
lines.append('    private NhanKhachDAO dao = new NhanKhachDAO();')
lines.append('')
lines.append('    private LichHen createTestLichHen(boolean validKH, boolean validDV, boolean validNL, boolean validNV, boolean validSlot) {')
lines.append('        KhachHang kh = new KhachHang(validKH ? "KH036" : "KH_FAIL", "Test KH", "012", "Address");')
lines.append('        LichHen lh = new LichHen("LH_" + UUID.randomUUID().toString().substring(0, 8), null, null, null, null, "Ghi chu", kh);')
lines.append('        ')
lines.append('        DichVu dv = new DichVu(validDV ? "DV05" : "DV_FAIL", 0, "Test DV", "Info");')
lines.append('        Slot slot = new Slot(validSlot ? "SL04" : "SL_FAIL", "P1", null, null, 0);')
lines.append('        LichHenPhucVu lhpv = new LichHenPhucVu();')
lines.append('        lhpv.setDichVu(dv);')
lines.append('        lhpv.setSlot(slot);')
lines.append('        lhpv.setDonGiaTamTinh(100);')
lines.append('        ')
lines.append('        NguyenLieu nl = new NguyenLieu(validNL ? "NL01" : "NL_FAIL", 10, "Test NL", "Unit", 10);')
lines.append('        LichHenNguyenLieu lhnl = new LichHenNguyenLieu();')
lines.append('        lhnl.setNguyenLieu(nl);')
lines.append('        lhnl.setSoLuongSuDung(1);')
lines.append('        lhpv.getDsLichHenNguyenLieu().add(lhnl);')
lines.append('        ')
lines.append('        NhanVien nv = new NhanVien(validNV ? "NV03" : "NV_FAIL", "Test NV", "1");')
lines.append('        LichHenNhanVien lhnv = new LichHenNhanVien();')
lines.append('        lhnv.setNhanVien(nv);')
lines.append('        lhpv.getDsLichHenNhanVien().add(lhnv);')
lines.append('        ')
lines.append('        lh.getDsLichHenPhucVu().add(lhpv);')
lines.append('        return lh;')
lines.append('    }')
lines.append('')

tc_num = 1

def add_test(kh, dv, nl, nv, slot, expect_true, desc):
    global tc_num
    lines.append('    @Test')
    lines.append(f'    public void testCase{tc_num:02d}() {{')
    lines.append(f'        // {desc}')
    lines.append(f'        LichHen lh = createTestLichHen({str(kh).lower()}, {str(dv).lower()}, {str(nl).lower()}, {str(nv).lower()}, {str(slot).lower()});')
    if expect_true:
        lines.append('        Assert.assertTrue(dao.luuNhanKhach(lh));')
    else:
        lines.append('        Assert.assertFalse(dao.luuNhanKhach(lh));')
    lines.append('    }')
    tc_num += 1

# TC 1: All True
add_test(True, True, True, True, True, True, 'Thêm 1 LH: KH tồn tại, DV tồn tại, NL tồn tại, NV tồn tại, Slot tồn tại')

# Generate combinations of falses
for num_falses in range(1, 6):
    for false_indices in itertools.combinations(range(5), num_falses):
        vals = [True] * 5
        for idx in false_indices:
            vals[idx] = False
        
        desc_parts = []
        for i in range(5):
            status = "tồn tại" if vals[i] else "k tồn tại"
            desc_parts.append(f"{attributes[i]} {status}")
        
        desc = "Thêm 1 LH: " + ", ".join(desc_parts)
        add_test(vals[0], vals[1], vals[2], vals[3], vals[4], False, desc)

# TC 33: Duplicate
lines.append('    @Test')
lines.append('    public void testCase33() {')
lines.append('        // Thêm 2 lần liên tục 1 LH: KH tồn tại, DV tồn tại, NL tồn tại, NV tồn tại, Slot tồn tại')
lines.append('        LichHen lh = createTestLichHen(true, true, true, true, true);')
lines.append('        dao.luuNhanKhach(lh); // Lần 1 có thể thành công')
lines.append('        boolean result2 = dao.luuNhanKhach(lh); // Lần 2 trùng lặp')
lines.append('        Assert.assertFalse("Lần 2 phải thất bại do trùng mã", result2);')
lines.append('    }')

lines.append('}')

with open('test/NhanKhachDAOTest.java', 'w', encoding='utf-8') as f:
    f.write('\n'.join(lines))
print('Done writing test/NhanKhachDAOTest.java')
