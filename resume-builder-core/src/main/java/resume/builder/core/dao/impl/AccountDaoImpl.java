package resume.builder.core.dao.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import resume.builder.core.dao.custom.AccountDaoCustom;
import resume.builder.api.entity.QAccountBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
class AccountDaoImpl implements AccountDaoCustom {
    private static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Boolean existsByUsername(String username) {
        final JPAQueryFactory q = new JPAQueryFactory(entityManager);
        final QAccountBean account = QAccountBean.accountBean;

        return q.selectFrom(account).where(account.username.eq(username)).fetchCount() > 0;
    }
}
