package resume.builder.api.service;

import java.util.Date;

public interface SmartSequenceService {
    String createCode(String prefix, Date forDate);
}
