package resume.builder.api.service;

import resume.builder.api.entity.AccountBean;

public interface AccountService {
    AccountBean addAccount(String username, String authUserId);
    Boolean existsAccountByUsername(String username);
}
