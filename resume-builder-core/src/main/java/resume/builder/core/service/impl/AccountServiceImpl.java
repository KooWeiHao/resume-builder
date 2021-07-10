package resume.builder.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import resume.builder.api.entity.AccountBean;
import resume.builder.api.service.AccountService;
import resume.builder.core.dao.AccountDao;
import resume.builder.util.DateUtil;

import java.util.Date;

@Service
class AccountServiceImpl implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountDao accountDao;

    @Autowired
    AccountServiceImpl(AccountDao accountDao){
        this.accountDao = accountDao;
    }

    @Transactional
    @Override
    public AccountBean addAccount(String username, String authUserId) {
        Date now = DateUtil.instant();

        AccountBean account = new AccountBean();
        account.setUsername(username);
        account.setAuthUserId(authUserId);
        account.setCreatedBy(username);
        account.setUpdatedBy(username);
        account.setCreatedDate(now);
        account.setUpdatedDate(now);
        accountDao.save(account);

        return account;
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean existsAccountByUsername(String username) {
        return accountDao.existsByUsername(username);
    }
}
