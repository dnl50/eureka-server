package dev.mieser.eurekaserver.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Configuration properties for HTTP basic authentication on eureka endpoints.
 */
@Data
@Validated
@ConfigurationProperties(prefix = "app.eureka.security.basic")
public class EurekaSecurityProperties {

    /**
     * A boolean flag indicating whether HTTP basic authentication should be enabled.
     * <p/>
     * Default is set to {@code false}.
     */
    private boolean enabled = false;

    /**
     * The username used to authenticate eureka API requests with. Cannot be blank and must be at
     * least {@code 3} characters long.
     * <p/>
     * Default is set to {@code "username"}
     */
    @NotBlank
    @Size(min = 3)
    private String username = "username";

    /**
     * The password used to authenticate eureka requests with. Cannot be blank and must be at
     * least {@code 5} characters long.
     * <p/>
     * Default is set to {@code "password"}
     */
    @NotBlank
    @Size(min = 5)
    private String password = "password";

}
