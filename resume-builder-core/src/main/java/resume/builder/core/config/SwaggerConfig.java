package resume.builder.core.config;

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
class SwaggerConfig{
    @Value("${controller.base.package:resume.builder.core.mvc}")
    private String controllerBasePackage;

    @Value("${swagger.ui.docket.group.name:Resume Builder}")
    private String swaggerUiDocketGroupName;

    @Value("${swagger.ui.api.info.title:Resume Builder APIs}")
    private String swaggerUiApiInfoTitle;

    @Value("${swagger.ui.api.info.description:Testing out Swagger UI}")
    private String swaggerUiApiInfoDescription;

    @Value("${swagger.ui.api.info.version:resume.v1.0}")
    private String swaggerUiApiInfoVersion;

    @Value("${swagger.ui.api.info.license:v1.0}")
    private String swaggerUiApiInfoLicense;

    @Value("${swagger.ui.api.info.contact.name:Eric Koo}")
    private String swaggerUiApiInfoContactName;

    @Value("${swagger.ui.api.info.contact.url:https://github.com/KooWeiHao}")
    private String swaggerUiApiInfoContactUrl;

    @Value("${swagger.ui.api.info.email:ericcool.1129@gmail.com}")
    private String swaggerUiApiInfoContactEmail;

    @Value("${swagger.ui.api.key.name:Bearer [access_token]}")
    private String swaggerUiApiKeyName;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerUiDocketGroupName)
                .apiInfo(apiInfo())
                .ignoredParameterTypes(InputStream.class, Resource.class)
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage(controllerBasePackage))
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
