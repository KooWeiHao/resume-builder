package resume.builder.core.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import resume.builder.amqp.api.AmqpSystemCode;
import resume.builder.amqp.consumer.AmqpConsumerCreator;
import resume.builder.api.entity.AccountBean;
import resume.builder.api.service.AccountService;
import resume.builder.core.mvc.form.SignUpForm;
import resume.builder.core.mvc.form.validator.SignUpFormValidator;
import resume.builder.core.mvc.helper.AuthHelper;
import resume.builder.doc.api.entity.DocumentBean;
import resume.builder.doc.api.service.DocumentService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(value = "/rest/auth", method = RequestMethod.POST)
class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AccountService accountService;
    private final SignUpFormValidator signUpFormValidator;
    private final AuthHelper authHelper;
    private final AmqpConsumerCreator amqpConsumerCreator;

    @Autowired
    AuthController(AccountService accountService, SignUpFormValidator signUpFormValidator, AuthHelper authHelper, AmqpConsumerCreator amqpConsumerCreator){
        this.accountService = accountService;
        this.signUpFormValidator = signUpFormValidator;
        this.authHelper = authHelper;
        this.amqpConsumerCreator = amqpConsumerCreator;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder){
        binder.addValidators(signUpFormValidator);
    }

    @RequestMapping("sign-up")
    Map<String, String> signUp(@Valid @RequestBody SignUpForm signUpForm, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            final String error = bindingResult.getAllErrors().get(0).getCode();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
        }

        final Optional<String> accessToken = authHelper.getAdminAccessToken();
        if(accessToken.isPresent()){
            final Boolean status = authHelper.createNewUserByAccessTokenAndUsernameAndPassword(accessToken.get(), signUpForm.username, signUpForm.password);
            if(status){
                final Optional<String> authUserId = authHelper.getAuthUserIdByAccessTokenAndUsername(accessToken.get(), signUpForm.username);
                if(authUserId.isPresent()){
                    final AccountBean account = accountService.addAccount(signUpForm.username, authUserId.get());
                    return Collections.singletonMap("username", account.getUsername());
                }
            }
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping("login")
    Map<String, String> login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password)
    {
        final Map<String, String> token = authHelper.getTokenByUsernameAndPassword(username, password);
        if(token.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid.credentials");
        }

        return token;
    }

    @RequestMapping("refresh-access-token")
    Map<String, String> refreshAccessToken(@RequestParam(value = "refreshToken") String refreshToken)
    {
        final Map<String, String> token = authHelper.getRefreshedToken(refreshToken);
        if(token.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return token;
    }

    @RequestMapping("logout")
    void logout(@RequestParam(value = "refreshToken") String refreshToken)
    {
        authHelper.logoutByRefreshToken(refreshToken);
    }

    @GetMapping("test-amqp")
    void testAmqp()
    {
        Optional<DocumentBean> doc = amqpConsumerCreator.create(AmqpSystemCode.RESUME_BUILDER_DOC, DocumentService.class).getDocumentByDocumentId("6b93c665-d968-4d38-8015-c7bfa693ac9e");
        System.out.println(doc.get().getName());
    }
}
