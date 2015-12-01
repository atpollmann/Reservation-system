package cl.usach.ingesoft.agendator.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
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
}
