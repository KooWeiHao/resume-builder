package resume.builder.doc.core.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriUtils;
import resume.builder.doc.api.service.DocumentService;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/rest/public/document", method = RequestMethod.GET)
class DocumentController {
    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    private final DocumentService documentService;

    @Autowired
    DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @RequestMapping(value ="download/{documentId}")
    ResponseEntity<byte[]> download(@PathVariable String documentId){
        return getDocumentResponse(documentId, false);
    }

    @RequestMapping(value ="preview/{documentId}")
    ResponseEntity<byte[]> preview(@PathVariable String documentId){
        return getDocumentResponse(documentId, true);
    }

    private ResponseEntity<byte[]> getDocumentResponse(String documentId, Boolean isPreview){
        return documentService.getDocumentByDocumentId(documentId).asOptional()
                .map(document -> {
                    final String returnType = isPreview ? "inline" : "attachment";
                    final String filename = UriUtils.encode(document.getName(), StandardCharsets.UTF_8);

                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(document.getType()))
                            .header(HttpHeaders.CONTENT_DISPOSITION, String.format("%s; filename=\"%s\"; filename*=UTF-8''%s", returnType, filename, filename))
                            .body(document.getData());
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
