package resume.builder.doc.core.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import resume.builder.doc.core.dao.custom.DocumentDaoCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
class DocumentDaoImpl implements DocumentDaoCustom {
    private static final Logger logger = LoggerFactory.getLogger(DocumentDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;
}
