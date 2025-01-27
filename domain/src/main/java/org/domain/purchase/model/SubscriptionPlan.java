package org.domain.purchase.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionPlan {
    private String id;
    private Map<Currency, String> iyzicoRefCodes;
    private Integer credit;
    private Map<Currency, BigDecimal> price;
    private SubscriptionPeriod period;
    private SubscriptionStatus status;
}
