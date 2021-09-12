package resume.builder.doc.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import resume.builder.ResumeBuilder;
import resume.builder.doc.api.entity.DocumentBean;
import resume.builder.doc.core.dao.DocumentDao;

@SpringBootApplication(scanBasePackageClasses = {ResumeBuilder.class})
@EntityScan(basePackageClasses = {DocumentBean.class})
@EnableJpaRepositories(basePackageClasses = {DocumentDao.class})
public class ResumeBuilderDocumentApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(ResumeBuilderDocumentApplication.class);
    }
}
