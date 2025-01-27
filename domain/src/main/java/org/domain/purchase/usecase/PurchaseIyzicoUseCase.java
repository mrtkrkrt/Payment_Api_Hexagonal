package org.domain.purchase.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.domain.common.model.UseCase;
import org.domain.purchase.model.CardData;
import org.domain.purchase.model.Currency;
import org.domain.purchase.model.PaymentHistory;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseIyzicoUseCase implements UseCase {
    private String subscriptionPlanId;
    private String userId;
    private Currency currency;
    private String cardAlias;
    private String cardHolderName;
    private String cardNumber;
    private String cvv;
    private String expireMonth;
    private String expireYear;
    private String locale;

    public CardData toCardData() {
        return CardData.builder()
            .cardHolderName(cardHolderName)
            .cardNumber(cardNumber)
            .expireYear(expireYear)
            .expireMonth(expireMonth)
            .cvc(cvv)
            .build();
    }

    public PaymentHistory toPaymentHistory() {
        return PaymentHistory.builder()
            .subscriptionPlanId(subscriptionPlanId)
            .userId(userId)
            .currency(currency)
            .build();
    }
}
