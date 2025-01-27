package org.domain.purchase.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionData {
    private String pricingPlanReferenceCode;
    private String startDate;
    private String referenceCode;
    private String customerEmail;
}
