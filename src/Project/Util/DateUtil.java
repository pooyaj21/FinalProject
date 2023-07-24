package Project.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static long timeOfNow(){
        return System.currentTimeMillis();
    }
    public static String formatDate(Long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}
