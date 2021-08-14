package resume.builder.core.mvc.form.validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import resume.builder.core.mvc.form.ResumeAboutMeForm;
import resume.builder.util.DateUtil;

import java.util.Date;
import java.util.Optional;

@Component
public class ResumeAboutMeFormValidator implements Validator {
    private static final Logger logger = LoggerFactory.getLogger(ResumeAboutMeFormValidator.class);

    @Value("${resume.profile.picture.max.upload.size:1}")
    private long maxUploadSize;

    @Value("${resume.career.objective.max.length:300}")
    private int maxCareerObjectiveLength;

    private boolean isValidImage(String contentType){
        return Optional.ofNullable(contentType).isPresent() && (contentType.equals(MediaType.IMAGE_JPEG_VALUE) || contentType.equals(MediaType.IMAGE_PNG_VALUE));
    }

    @Override
    public void validate(Object o, Errors errors){
        if(o instanceof ResumeAboutMeForm){
            final ResumeAboutMeForm form = (ResumeAboutMeForm) o;
            EmailValidator emailValidator = EmailValidator.getInstance();
            Date now = DateUtil.instant();

            final MultipartFile profilePicture = form.getProfilePicture();
            if(!isValidImage(profilePicture.getContentType())){
                errors.reject("invalid.profile.picture.image.type");
            }
            if(profilePicture.getSize() > maxUploadSize*1024*1024){
                errors.reject("invalid.profile.picture.image.size");
            }

            if(!emailValidator.isValid(form.getEmail())){
                errors.reject("invalid.email.format");
            }

            if(!StringUtils.isNumeric(form.getMobileNumber())){
                errors.reject("invalid.mobile.number");
            }

            if(!DateUtil.isDate1AfterDate2(DateUtil.setZeroTime(now), form.getDateOfBirth())){
                errors.reject("invalid.birth.date");
            }

            if(Optional.ofNullable(form.getCareerObjective()).isPresent() && form.getCareerObjective().length() > maxCareerObjectiveLength){
                errors.reject("invalid.career.objective.exceed.300.characters");
            }
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
