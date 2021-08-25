package resume.builder.doc.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import resume.builder.doc.api.entity.DocumentBean;
import resume.builder.doc.core.dao.custom.DocumentDaoCustom;

public interface DocumentDao extends JpaRepository<DocumentBean, Long>, DocumentDaoCustom {
}
