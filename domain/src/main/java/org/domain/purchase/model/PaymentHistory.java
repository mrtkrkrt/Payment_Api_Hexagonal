package org.domain.purchase.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentHistory {
    private String userId;
    private Currency currency;
    private String subscriptionPlanId;
    private SubscriptionStatus status;
}
