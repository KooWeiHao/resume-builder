package resume.builder.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import resume.builder.api.entity.SmartSequenceBean;
import resume.builder.api.service.SmartSequenceService;
import resume.builder.core.dao.SmartSequenceDao;

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
    Long createSmartSequence(String prefix, Date forDate){
        // TRIGGER `smart_sequence_BEFORE_INSERT` will update the sequence

        SmartSequenceBean smartSequence = new SmartSequenceBean();
        smartSequence.setPrefix(prefix);
        smartSequence.setForDate(forDate);
        smartSequenceDao.save(smartSequence);

        return smartSequence.getSmartSequenceId();
    }

    @Transactional(readOnly = true)
    Integer getSequenceBySmartSequenceId(Long smartSequenceId){
        return smartSequenceDao.findById(smartSequenceId).get().getSequence();
    }

    @Override
    public String createCode(String prefix, Date forDate) {
        final Long smartSequenceId = createSmartSequence(prefix, forDate);
        final Integer sequence = getSequenceBySmartSequenceId(smartSequenceId);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);

        return String.format("%s%s%s", prefix, sdf.format(forDate), StringUtils.leftPad(sequence.toString(), 8, '0'));
    }
}
