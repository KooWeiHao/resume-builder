package resume.builder.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
class StandardControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(StandardControllerAdvice.class);

    @InitBinder
    private void initBinder(WebDataBinder binder){
        binder.registerCustomEditor( String.class, new StringTrimmerEditor( true ));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    ResponseEntity<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception){
        logger.error(exception.getMessage());
        return ResponseEntity.badRequest().body("max.upload.size.exceeded");
    }
}
