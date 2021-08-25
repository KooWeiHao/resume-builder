package resume.builder.doc.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import resume.builder.doc.api.service.SmartSequenceService;
import resume.builder.doc.core.dao.SmartSequenceDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class SmartSequenceServiceImpl implements SmartSequenceService {
    private static final Logger logger = LoggerFactory.getLogger(SmartSequenceServiceImpl.class);

    private final SmartSequenceDao smartSequenceDao;

    @Autowired
    public SmartSequenceServiceImpl(SmartSequenceDao smartSequenceDao) {
        this.smartSequenceDao = smartSequenceDao;
    }

    @Transactional
    @Override
    public String createCode(String prefix, Date forDate) {
        final Integer sequence = smartSequenceDao.getSequence(prefix, forDate);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);

        return String.format("%s%s%s", prefix, sdf.format(forDate), StringUtils.leftPad(sequence.toString(), 8, '0'));
    }
}
