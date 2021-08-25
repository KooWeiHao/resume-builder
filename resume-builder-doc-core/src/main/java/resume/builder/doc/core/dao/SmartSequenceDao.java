package resume.builder.doc.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import resume.builder.doc.api.entity.SmartSequenceBean;
import resume.builder.doc.core.dao.custom.SmartSequenceDaoCustom;

public interface SmartSequenceDao extends JpaRepository<SmartSequenceBean, Long>, SmartSequenceDaoCustom {
}
