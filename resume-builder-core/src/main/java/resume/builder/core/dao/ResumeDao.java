package resume.builder.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import resume.builder.api.entity.ResumeBean;
import resume.builder.core.dao.custom.ResumeDaoCustom;

public interface ResumeDao extends JpaRepository<ResumeBean, Long>, ResumeDaoCustom {
}
