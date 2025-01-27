package infra.infra.subcription.purchase.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "payconstants")
@Component
public class IyzicoPaymentProperties {
    private String iyzicoSecretKey;
    private String iyzicoApiKey;
    private String iyzicoBaseUrl;
    private String iyzicoCallbackUrl;
}
