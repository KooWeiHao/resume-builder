package resume.builder.core.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import resume.builder.core.mvc.form.ResumeAboutMeForm;
import resume.builder.core.mvc.form.validator.ResumeAboutMeFormValidator;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/rest/resume", method = RequestMethod.POST)
class ResumeController {
    private static final Logger logger = LoggerFactory.getLogger(ResumeController.class);

    private final ResumeAboutMeFormValidator resumeAboutMeFormValidator;

    @Autowired
    ResumeController(ResumeAboutMeFormValidator resumeAboutMeFormValidator) {
        this.resumeAboutMeFormValidator = resumeAboutMeFormValidator;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder){
        binder.addValidators(resumeAboutMeFormValidator);
    }

    @RequestMapping(value ="add-or-update-resume-about-me", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    void addOrUpdateResumeAboutMe(@Valid @ModelAttribute ResumeAboutMeForm resumeAboutMeForm, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            final String error = bindingResult.getAllErrors().get(0).getCode();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
        }
    }
}
