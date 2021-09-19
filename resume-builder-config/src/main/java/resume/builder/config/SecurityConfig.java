package resume.builder.config;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.*;

@Configuration
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Value("${resume.builder.react.origin}")
    private List<String> allowedOrigin;

    @Value("${resume.builder.principal.name.key.default:sub}")
    private String defaultPrincipalNameKey;

    @Value("${resume.builder.principal.name.key.new:preferred_username}")
    private String newPrincipalNameKey;

    private class ClaimSetConverter implements Converter<Map<String, Object>, Map<String, Object>> {
        private final MappedJwtClaimSetConverter delegate = MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());

        @Override
        public Map<String, Object> convert(Map<String, Object> claims) {
            Map<String, Object> convertedClaims = this.delegate.convert(claims);

            convertedClaims.put("auth_user_id", convertedClaims.get(defaultPrincipalNameKey));
            Optional.ofNullable(convertedClaims.get(newPrincipalNameKey))
                    .ifPresent(newName -> convertedClaims.put(defaultPrincipalNameKey, newName));

            return convertedClaims;
        }
    }

    @SneakyThrows
    @Override
    protected void configure(HttpSecurity http) {
        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/rest/auth/**", "/rest/public/**",
                        "/v2/**", "/swagger-resources/**", "/swagger-ui/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
    }

    @Bean
    JwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(properties.getJwt().getJwkSetUri()).build();
        jwtDecoder.setClaimSetConverter(new ClaimSetConverter());
        return jwtDecoder;
    }

    //Enabling Cross Origin Requests for a RESTful Web Service
    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        // *** URL below needs to match the client URL and port ***
        logger.info("Whitelisted origins - {}", allowedOrigin);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(allowedOrigin);
        config.setAllowedMethods(Arrays.asList("POST","PUT","GET","OPTIONS","DELETE"));
        config.setAllowedHeaders(Arrays.asList("X-Requested-With", "accept", "authorization","content-type"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
