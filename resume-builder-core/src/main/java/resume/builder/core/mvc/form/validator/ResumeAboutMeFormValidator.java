package resume.builder.core.mvc.form.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import resume.builder.core.mvc.form.ResumeAboutMeForm;

@Component
public class ResumeAboutMeFormValidator implements Validator {
    private static final Logger logger = LoggerFactory.getLogger(ResumeAboutMeFormValidator.class);

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public void validate(Object o, Errors errors){
        if(o instanceof ResumeAboutMeForm){
            final ResumeAboutMeForm form = (ResumeAboutMeForm) o;
            System.out.println(form.getProfilePicture().getSize());
        }
    }
}
