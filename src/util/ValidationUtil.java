package util;

public class ValidationUtil {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isTimeFormat(String time) {
        // Regex đơn giản cho HH:mm:ss
        return time.matches("^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$");
    }
}
