package resume.builder.doc.core.dao.custom;

import java.util.Date;

public interface SmartSequenceDaoCustom {
    Integer getSequence(String prefix, Date forDate);
}
