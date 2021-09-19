package resume.builder.doc.api.service;

import resume.builder.amqp.api.annotation.AmqpService;
import resume.builder.doc.api.entity.DocumentBean;
import resume.builder.util.SerializableOptional;

@AmqpService
public interface DocumentService {
    DocumentBean upload(String originalFilename, String contentType, byte[] bytes, long size, String uploadedBy);
    SerializableOptional<DocumentBean> getDocumentByDocumentId(String documentId);
}
