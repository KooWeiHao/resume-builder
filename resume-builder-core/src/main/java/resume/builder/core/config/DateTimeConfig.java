package resume.builder.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.time.format.DateTimeFormatter;

@Configuration
class DateTimeConfig extends WebMvcConfigurationSupport {

    @Bean
    @Override
    public FormattingConversionService mvcConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);

        DateTimeFormatterRegistrar dateTimeFormatterRegistrar = new DateTimeFormatterRegistrar();
        dateTimeFormatterRegistrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dateTimeFormatterRegistrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        dateTimeFormatterRegistrar.registerFormatters(conversionService);

        DateFormatterRegistrar dateFormatterRegistrar = new DateFormatterRegistrar();
        dateFormatterRegistrar.setFormatter(new DateFormatter("yyyy-MM-dd"));
        dateFormatterRegistrar.registerFormatters(conversionService);

        return conversionService;
    }
}
