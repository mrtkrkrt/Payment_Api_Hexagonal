package org.domain.purchase.model;

import lombok.*;
import org.domain.common.usecase.AggregateRoot;
import org.domain.purchase.model.response.SubscriptionInitData;
import org.domain.purchase.usecase.PurchaseIyzicoUseCase;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends AggregateRoot {
    private String id;
    private String userId;
    private int credit;
    private Currency currency;
    private SubscriptionStatus status;
    private boolean autoRenewal;
    private SubscriptionPeriod subscriptionPeriod;
    private String subscriptionPlanId;
    private String subscriptionReferenceCode;

    public static Product from(PurchaseIyzicoUseCase useCase, SubscriptionPlan subscriptionPlan, SubscriptionInitData subscriptionInitData) {
        return Product.builder()
                .userId(useCase.getUserId())
                .credit(subscriptionPlan.getCredit())
                .currency(useCase.getCurrency())
                .status(SubscriptionStatus.ACTIVE)
                .autoRenewal(true)
                .subscriptionPeriod(subscriptionPlan.getPeriod())
                .subscriptionPlanId(subscriptionPlan.getId())
                .subscriptionReferenceCode(subscriptionInitData.getSubscriptionReferenceCode())
                .build();
    }
}
