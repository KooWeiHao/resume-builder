package resume.builder.doc.api.service;

import org.springframework.web.multipart.MultipartFile;
import resume.builder.amqp.api.annotation.AmqpService;
import resume.builder.doc.api.entity.DocumentBean;
import resume.builder.util.SerializableOptional;

@AmqpService
public interface DocumentService {
    DocumentBean upload(MultipartFile file, String uploadedBy);
    SerializableOptional<DocumentBean> getDocumentByDocumentId(String documentId);
}
