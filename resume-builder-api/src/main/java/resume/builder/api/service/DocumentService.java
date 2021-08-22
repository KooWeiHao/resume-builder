package resume.builder.api.service;

import org.springframework.web.multipart.MultipartFile;
import resume.builder.api.entity.DocumentBean;

import java.util.Optional;

public interface DocumentService {
    DocumentBean upload(MultipartFile file, String uploadedBy);
    Optional<DocumentBean> getDocumentByDocumentId(String documentId);
}
