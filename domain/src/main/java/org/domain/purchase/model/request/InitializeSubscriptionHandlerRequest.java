package org.domain.purchase.model.request;

import lombok.*;
import org.domain.purchase.model.SubscriptionPlan;
import org.domain.purchase.usecase.PurchaseIyzicoUseCase;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializeSubscriptionHandlerRequest {
    private String cardHolderName;
    private String cardNumber;
    private String expireYear;
    private String expireMonth;
    private String cvc;
    private String email;
    private String firstName;
    private String lastName;
    private String userId;
    private String locale;
    private String pricingPlanReferenceCode;

    public static InitializeSubscriptionHandlerRequest of(PurchaseIyzicoUseCase useCase, SubscriptionPlan subscriptionPlan) {
        return InitializeSubscriptionHandlerRequest.builder()
                .cardHolderName(useCase.getCardHolderName())
                .cardNumber(useCase.getCardNumber())
                .expireYear(useCase.getExpireYear())
                .expireMonth(useCase.getExpireMonth())
                .cvc(useCase.getCvv())
                .email(useCase.getUserId())
                .firstName(useCase.getUserId())
                .lastName(useCase.getUserId())
                .userId(useCase.getUserId())
                .locale(useCase.getLocale())
                .pricingPlanReferenceCode(subscriptionPlan.getIyzicoRefCodes().get(useCase.getCurrency()))
                .build();
    }
}
