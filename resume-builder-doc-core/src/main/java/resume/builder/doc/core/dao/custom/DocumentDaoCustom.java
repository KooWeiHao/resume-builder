package resume.builder.doc.core.dao.custom;

import resume.builder.doc.api.entity.DocumentBean;

import java.util.Optional;

public interface DocumentDaoCustom {
    Optional<DocumentBean> getByDocumentId(String documentId);
}
