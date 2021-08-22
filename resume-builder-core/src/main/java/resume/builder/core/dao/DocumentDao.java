package resume.builder.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import resume.builder.api.entity.DocumentBean;
import resume.builder.core.dao.custom.DocumentDaoCustom;

public interface DocumentDao extends JpaRepository<DocumentBean, Long>, DocumentDaoCustom {
}
