package resume.builder.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import resume.builder.api.entity.SmartSequenceBean;
import resume.builder.core.dao.custom.SmartSequenceDaoCustom;

public interface SmartSequenceDao extends JpaRepository<SmartSequenceBean, Long>, SmartSequenceDaoCustom {
}
