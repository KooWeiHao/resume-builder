package resume.builder.util;

import org.apache.commons.lang3.time.DateUtils;

import java.time.Instant;
import java.util.Date;

public final class DateUtil extends DateUtils {
    public static Date instant() {
        return Date.from(Instant.now());
    }
}
