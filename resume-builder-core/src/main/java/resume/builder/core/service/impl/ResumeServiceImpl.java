package resume.builder.core.service.impl;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import resume.builder.amqp.api.AmqpSystemCode;
import resume.builder.amqp.consumer.AmqpConsumerCreator;
import resume.builder.api.entity.ResumeBean;
import resume.builder.api.model.ResumeModifyModel;
import resume.builder.api.service.ResumeService;
import resume.builder.api.service.SmartSequenceService;
import resume.builder.core.dao.ResumeDao;
import resume.builder.doc.api.service.DocumentService;
import resume.builder.util.DateUtil;

import java.util.Date;

@Service
class ResumeServiceImpl implements ResumeService {
    private static final Logger logger = LoggerFactory.getLogger(ResumeServiceImpl.class);

    private final SmartSequenceService smartSequenceService;
    private final AmqpConsumerCreator amqpConsumerCreator;
    private final ResumeDao resumeDao;

    @Autowired
    ResumeServiceImpl(SmartSequenceService smartSequenceService, AmqpConsumerCreator amqpConsumerCreator, ResumeDao resumeDao) {
        this.smartSequenceService = smartSequenceService;
        this.amqpConsumerCreator = amqpConsumerCreator;
        this.resumeDao = resumeDao;
    }

    @SneakyThrows
    @Transactional
    @Override
    public ResumeBean addOrUpdateResume(ResumeModifyModel resumeModifyModel) {
        Date now = DateUtil.instant();
        final MultipartFile profilePicture = resumeModifyModel.getProfilePicture();

        ResumeBean resume = new ResumeBean();
        BeanUtils.copyProperties(resumeModifyModel, resume);
        resume.setCode(smartSequenceService.createCode(ResumeBean.CodePrefix.RES.toString(), now));
        resume.setStatus(ResumeBean.Status.ACTIVE.toString());
        resume.setProfilePicture(
                amqpConsumerCreator.create(AmqpSystemCode.RESUME_BUILDER_DOC, DocumentService.class)
                        .upload(profilePicture.getOriginalFilename(), profilePicture.getContentType(), profilePicture.getBytes(), profilePicture.getSize(), resume.getCreatedBy())
                        .getCode()
        );
        resume.setCreatedDate(now);
        resume.setUpdatedDate(now);
        resume.setUpdatedBy(resume.getCreatedBy());
        resumeDao.save(resume);

        return resume;
    }
}
