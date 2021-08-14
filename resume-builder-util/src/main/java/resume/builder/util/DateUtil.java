package resume.builder.util;

import org.apache.commons.lang3.time.DateUtils;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.IntStream;

public final class DateUtil extends DateUtils {
    public static Date instant() {
        return Date.from(Instant.now());
    }

    public static boolean isDate1AfterDate2(Date date1, Date date2) {
        return date1.compareTo(date2) > 0;
    }

    public static Date setMaxTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        IntStream.of(Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND).forEach(value -> calendar.set(value, calendar.getMaximum(value)));
        return calendar.getTime();
    }

    public static Date setZeroTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        IntStream.of(Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND).forEach(value -> calendar.set(value, calendar.getMinimum(value)));
        return calendar.getTime();
    }
}
