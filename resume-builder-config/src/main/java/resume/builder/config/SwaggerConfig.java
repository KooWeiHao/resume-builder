package resume.builder.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Configuration
class SwaggerConfig {
    @Value("${controller.base.package:}")
    private String controllerBasePackage;

    @Value("${swagger.ui.docket.group.name:}")
    private String swaggerUiDocketGroupName;

    @Value("${swagger.ui.api.info.title:}")
    private String swaggerUiApiInfoTitle;

    @Value("${swagger.ui.api.info.description:}")
    private String swaggerUiApiInfoDescription;

    @Value("${swagger.ui.api.info.version:}")
    private String swaggerUiApiInfoVersion;

    @Value("${swagger.ui.api.info.license:}")
    private String swaggerUiApiInfoLicense;

    @Value("${swagger.ui.api.info.contact.name:}")
    private String swaggerUiApiInfoContactName;

    @Value("${swagger.ui.api.info.contact.url:}")
    private String swaggerUiApiInfoContactUrl;

    @Value("${swagger.ui.api.info.email:}")
    private String swaggerUiApiInfoContactEmail;

    @Value("${swagger.ui.api.key.name:}")
    private String swaggerUiApiKeyName;

    @Bean
    public Docket api() {
        final Docket docket = new Docket(DocumentationType.SWAGGER_2);

        if(StringUtils.isNotBlank(swaggerUiDocketGroupName)){
            docket.groupName(swaggerUiDocketGroupName);
        }

        return docket.apiInfo(apiInfo())
                .ignoredParameterTypes(InputStream.class, Resource.class)
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .select()
                .apis(StringUtils.isNotBlank(controllerBasePackage) ? RequestHandlerSelectors.basePackage(controllerBasePackage) : RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerUiApiInfoTitle)
                .description(swaggerUiApiInfoDescription)
                .version(swaggerUiApiInfoVersion)
                .license(swaggerUiApiInfoLicense)
                .contact(new Contact(swaggerUiApiInfoContactName, swaggerUiApiInfoContactUrl, swaggerUiApiInfoContactEmail))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey(swaggerUiApiKeyName, "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference(swaggerUiApiKeyName, authorizationScopes));
    }
}
