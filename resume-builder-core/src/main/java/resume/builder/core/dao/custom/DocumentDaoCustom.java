package resume.builder.core.dao.custom;

import resume.builder.api.entity.DocumentBean;

import java.util.Optional;

public interface DocumentDaoCustom {
    Optional<DocumentBean> getByDocumentId(String documentId);
}
