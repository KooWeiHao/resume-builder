package resume.builder.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import resume.builder.ResumeBuilder;
import resume.builder.api.entity.AccountBean;
import resume.builder.core.dao.AccountDao;

@SpringBootApplication(scanBasePackageClasses = {ResumeBuilder.class})
@EntityScan(basePackageClasses = {AccountBean.class})
@EnableJpaRepositories(basePackageClasses = {AccountDao.class})
public class ResumeBuilderApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(ResumeBuilderApplication.class);
	}
}
