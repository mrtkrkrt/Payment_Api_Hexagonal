package org.domain.purchase.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionCheckoutFormData {
    private String subscriptionReferenceCode;
    private String parentReferenceCode;
    private String customerReferenceCode;
    private String pricingPlanReferenceCode;
}
