package resume.builder.doc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import resume.builder.doc.core.ResumeBuilderDocumentApplication;

import java.util.Arrays;

public class ResumeBuilderDoc {
    private static final Logger logger = LoggerFactory.getLogger(ResumeBuilderDoc.class);

    public static void main(String[] args){
        ConfigurableApplicationContext cac = SpringApplication.run(ResumeBuilderDocumentApplication.class, args);
        if(logger.isDebugEnabled()){
            String[] beanNames = cac.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for(String beanName: beanNames){
                logger.debug(beanName);
            }
        }
    }
}
