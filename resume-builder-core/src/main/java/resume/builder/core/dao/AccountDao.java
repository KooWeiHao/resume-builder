package resume.builder.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import resume.builder.api.entity.AccountBean;
import resume.builder.core.dao.custom.AccountDaoCustom;

public interface AccountDao extends JpaRepository<AccountBean, Long>, AccountDaoCustom {
}
