package resume.builder.core.mvc.form.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import resume.builder.api.service.AccountService;
import resume.builder.core.mvc.form.SignUpForm;

@Component
public class SignUpFormValidator implements Validator {
    private static final Logger logger = LoggerFactory.getLogger(SignUpFormValidator.class);

    private final AccountService accountService;

    @Autowired
    SignUpFormValidator(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(o instanceof SignUpForm){
            final SignUpForm form = (SignUpForm) o;

            Boolean isAccountExists = accountService.existsAccountByUsername(form.getUsername());
            if(isAccountExists){
                final String error = "duplicate.username";
                errors.reject(error);

                logger.debug("Failed: {}-{}", form.getUsername(), error);
            }
        }
    }
}
