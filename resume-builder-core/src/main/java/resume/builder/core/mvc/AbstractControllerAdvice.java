package resume.builder.core.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
class AbstractControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(AbstractControllerAdvice.class);

    @InitBinder
    private void initBinder(WebDataBinder binder){
        binder.registerCustomEditor( String.class, new StringTrimmerEditor( true ));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    ResponseEntity<Map<String, String>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception){
        logger.error(exception.getMessage());
        return ResponseEntity.badRequest().body(Collections.singletonMap("error", "max.upload.size.exceeded"));
    }
}
