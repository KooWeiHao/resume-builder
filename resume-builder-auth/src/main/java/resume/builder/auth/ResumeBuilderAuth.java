package resume.builder.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import resume.builder.auth.config.KeycloakServerProperties;

import java.util.Arrays;

@SpringBootApplication(exclude = {LiquibaseAutoConfiguration.class})
@EnableConfigurationProperties({ KeycloakServerProperties.class })
public class ResumeBuilderAuth extends SpringBootServletInitializer {
    private static final Logger logger = LoggerFactory.getLogger(ResumeBuilderAuth.class);

    public static void main(String[] args){
        ConfigurableApplicationContext cac = SpringApplication.run(ResumeBuilderAuth.class, args);
        if(logger.isDebugEnabled()){
            String[] beanNames = cac.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for(String beanName: beanNames){
                logger.debug(beanName);
            }
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(ResumeBuilderAuth.class);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> onApplicationReadyEventListener(ServerProperties serverProperties, KeycloakServerProperties keycloakServerProperties) {
        return (evt) -> {
            Integer port = serverProperties.getPort();
            String keycloakContextPath = keycloakServerProperties.getContextPath();
            logger.info("Embedded Keycloak started: http://localhost:{}{} to use keycloak", port, keycloakContextPath);
        };
    }
}
