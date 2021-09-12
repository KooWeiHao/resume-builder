package resume.builder.doc.api.service;

import org.springframework.web.multipart.MultipartFile;
import resume.builder.amqp.api.annotation.AmqpService;
import resume.builder.doc.api.entity.DocumentBean;

import java.util.Optional;

@AmqpService
public interface DocumentService {
    DocumentBean upload(MultipartFile file, String uploadedBy);
    Optional<DocumentBean> getDocumentByDocumentId(String documentId);

    /* Amqp service */
    DocumentBean getNullableDocumentByDocumentId(String documentId);
}
