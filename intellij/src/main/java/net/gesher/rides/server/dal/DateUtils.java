package net.gesher.rides.server.dal;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {

    public static Date getEndOfPreviousDay(Date d){
        Calendar c = GregorianCalendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, -1);
//        c.set(Calendar.HOUR, 23);
//        c.set(Calendar.MINUTE, 59);
//        c.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
        return c.getTime();
    }

    public static Date getStartOfNextDay(Date d){
        Calendar c = GregorianCalendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, 1);
//        c.set(Calendar.HOUR, 0);
//        c.set(Calendar.MINUTE, 1);
//        c.setTimeZone(TimeZone.getTimeZone("UTC"));
        return c.getTime();
    }

    public static Date getXDaysAhead(Date d, int days){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }
}
