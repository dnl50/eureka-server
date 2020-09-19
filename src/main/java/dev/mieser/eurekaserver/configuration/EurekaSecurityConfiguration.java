package dev.mieser.eurekaserver.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Wrapper class for security configurations for eureka server API requests.
 */
@Configuration
public class EurekaSecurityConfiguration {

    /**
     * The ant pattern matching all eureka API endpoints.
     */
    private static final String EUREKA_ENDPOINT_ANT_PATTERN = "/eureka/**";

    /**
     * Configures the eureka endpoints to be secured with HTTP basic authentication. Can be disabled by setting
     * the {@code app.eureka.security.basic.enabled} property to {@code false}.
     *
     * @see AllowAllSecurityConfiguration
     */
    @Order(10)
    @ConditionalOnProperty(
            value = "app.eureka.security.basic.enabled"
    )
    @EnableWebSecurity
    @RequiredArgsConstructor
    @EnableConfigurationProperties(EurekaSecurityProperties.class)
    public static class HttpBasicSecurityConfiguration extends WebSecurityConfigurerAdapter {

        /**
         * The authority authenticated clients will acquire.
         */
        private static final String EUREKA_CLIENT_AUTHORITY = "ROLE_CLIENT";

        /**
         * The configuration properties to read the configured username and password from.
         */
        private final EurekaSecurityProperties eurekaSecurityProperties;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf()
                        .ignoringAntMatchers(EUREKA_ENDPOINT_ANT_PATTERN)
                    .and()
                    .antMatcher(EUREKA_ENDPOINT_ANT_PATTERN)
                        .authorizeRequests()
                            .anyRequest().authenticated()
                        .and()
                            .httpBasic();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            var username = eurekaSecurityProperties.getUsername();
            var password = eurekaSecurityProperties.getPassword();

            auth.inMemoryAuthentication()
                    .withUser(username).password(passwordEncoder().encode(password))
                    .authorities(EUREKA_CLIENT_AUTHORITY);
        }

        @Bean
        PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

    }

    /**
     * Configures the eureka endpoints to be unsecured. Can be enabled by setting the
     * {@code app.eureka.security.basic.enabled} property to {@code true}. Enabled by default when
     * the property is not set.
     *
     * @see HttpBasicSecurityConfiguration
     */
    @Order(10)
    @ConditionalOnProperty(
            value = "app.eureka.security.basic.enabled",
            havingValue = "false",
            matchIfMissing = true
    )
    @EnableWebSecurity
    public static class AllowAllSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http
                        .csrf()
                            .ignoringAntMatchers(EUREKA_ENDPOINT_ANT_PATTERN)
                        .and()
                        .antMatcher(EUREKA_ENDPOINT_ANT_PATTERN)
                            .authorizeRequests()
                                .anyRequest().permitAll();
        }

    }

}
