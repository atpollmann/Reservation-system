package cl.usach.ingesoft.agendator.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static SimpleDateFormat ISO_SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat ISO_SDF_TIME = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat ISO_SDF_DATETIME = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


    private static SimpleDateFormat SDF_DATE = new SimpleDateFormat("dd-MM-yyyy");
    private static SimpleDateFormat SDF_TIME = new SimpleDateFormat("HH:mm:ss");

    public static Date makeDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month-1, day);
        return c.getTime();
    }
    public static Date makeDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.set(year, month-1, day, hour, minute, second);
        return c.getTime();
    }

    // --------------- format methods ---------------

    public static String formatDate(Date date) {
        return SDF_DATE.format(date);
    }
    public static String formatTime(Date date) {
        return SDF_TIME.format(date);
    }
    public static String formatDateTime(Date date) {
        return formatTime(date) + ' ' + formatDate(date);
    }

    public static String formatIsoDate(Date date) {
        return ISO_SDF_DATE.format(date);
    }
    public static String formatIsoTime(Date date) {
        return ISO_SDF_TIME.format(date);
    }
    public static String formatIsoDateTime(Date date) {
        return ISO_SDF_DATETIME.format(date);
    }

    // --------------- parsing methods ---------------
    public static Date tryParse(String isoDateTime) throws ParseException {
        return ISO_SDF_DATETIME.parse(isoDateTime);
    }
}
