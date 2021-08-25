package resume.builder.doc.api.service;

import org.springframework.web.multipart.MultipartFile;
import resume.builder.doc.api.entity.DocumentBean;

import java.util.Optional;

public interface DocumentService {
    DocumentBean upload(MultipartFile file, String uploadedBy);
    Optional<DocumentBean> getDocumentByDocumentId(String documentId);
}
