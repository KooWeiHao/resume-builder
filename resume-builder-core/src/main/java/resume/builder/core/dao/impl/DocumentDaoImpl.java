package resume.builder.core.dao.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import resume.builder.api.entity.DocumentBean;
import resume.builder.api.entity.QDocumentBean;
import resume.builder.core.dao.custom.DocumentDaoCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
class DocumentDaoImpl implements DocumentDaoCustom {
    private static final Logger logger = LoggerFactory.getLogger(DocumentDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<DocumentBean> getByDocumentId(String documentId) {
        final JPAQueryFactory q = new JPAQueryFactory(entityManager);
        final QDocumentBean document = QDocumentBean.documentBean;

        return Optional.ofNullable(
                q.selectFrom(document)
                        .where(document.documentId.eq(documentId))
                        .fetchOne());
    }
}
