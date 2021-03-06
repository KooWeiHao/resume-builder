package resume.builder.doc.core.service.impl;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import resume.builder.doc.api.entity.DocumentBean;
import resume.builder.doc.api.service.DocumentService;
import resume.builder.doc.api.service.SmartSequenceService;
import resume.builder.doc.core.dao.DocumentDao;
import resume.builder.util.DateUtil;
import resume.builder.util.SerializableOptional;

import java.util.Date;

@Service
class DocumentServiceImpl implements DocumentService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    private final DocumentDao documentDao;
    private final SmartSequenceService smartSequenceService;

    @Autowired
    DocumentServiceImpl(DocumentDao documentDao, SmartSequenceService smartSequenceService) {
        this.documentDao = documentDao;
        this.smartSequenceService = smartSequenceService;
    }

    @SneakyThrows
    @Transactional
    @Override
    public DocumentBean upload(String originalFilename, String contentType, byte[] bytes, long size, String uploadedBy) {
        Date now = DateUtil.instant();

        DocumentBean document = new DocumentBean();
        document.setCode(smartSequenceService.createCode(DocumentBean.CodePrefix.DOC.toString(), now));
        document.setName(StringUtils.cleanPath(originalFilename));
        document.setType(contentType);
        document.setData(bytes);
        document.setSize(size);
        document.setCreatedBy(uploadedBy);
        document.setUpdatedBy(uploadedBy);
        document.setCreatedDate(now);
        document.setUpdatedDate(now);
        documentDao.save(document);

        return document;
    }

    @Transactional(readOnly = true)
    @Override
    public SerializableOptional<DocumentBean> getDocumentByDocumentId(String documentId) {
        return SerializableOptional.fromOptional(documentDao.findById(documentId));
    }
}
