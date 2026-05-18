package service;

import dao.SlotDAO;
import model.Slot;
import java.util.ArrayList;
import java.util.List;

public class SlotService {
    private SlotDAO slotDAO = new SlotDAO();

    public List<Slot> timSlotTrong(String thoiGian) {
        if (thoiGian == null || thoiGian.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return slotDAO.timSlotTrongTheoThoiGian(thoiGian.trim());
    }
}
