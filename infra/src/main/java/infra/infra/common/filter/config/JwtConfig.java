package infra.infra.common.filter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "jwt")
@Configuration
public class JwtConfig {
    private String secretKey;
    private String tokenPrefix;
    private int tokenExpirationForVerification;
    private int tokenExpirationForLogin;
}
