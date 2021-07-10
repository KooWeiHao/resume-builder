package resume.builder.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import resume.builder.core.ResumeBuilderApplication;

import java.util.Arrays;

public class ResumeBuilderRest {
    private static final Logger logger = LoggerFactory.getLogger(ResumeBuilderRest.class);

    public static void main(String[] args){
        ConfigurableApplicationContext cac = SpringApplication.run(ResumeBuilderApplication.class, args);
        if(logger.isDebugEnabled()){
            String[] beanNames = cac.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for(String beanName: beanNames){
                logger.debug(beanName);
            }
        }
    }
}
