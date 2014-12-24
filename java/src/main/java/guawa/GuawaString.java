package guawa;

public class GuawaString {
    public static String capitalize(String s) {
        String trim = s.trim();
        return trim.toUpperCase().charAt(0) + trim.substring(1);
    }
}
