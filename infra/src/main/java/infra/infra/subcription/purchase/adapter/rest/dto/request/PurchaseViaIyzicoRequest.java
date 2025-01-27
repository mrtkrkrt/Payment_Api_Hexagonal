package infra.infra.subcription.purchase.adapter.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.domain.purchase.model.Currency;
import org.domain.purchase.usecase.PurchaseIyzicoUseCase;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseViaIyzicoRequest {
    private String subscriptionPlanId;
    private Currency currency;
    private String cardAlias;
    private String cardHolderName;
    private String cardNumber;
    private String cvv;
    private String expireMonth;
    private String expireYear;
    private String locale;

    public PurchaseIyzicoUseCase toUseCase(String userId) {
        return PurchaseIyzicoUseCase.builder()
                .subscriptionPlanId(subscriptionPlanId)
                .userId(userId)
                .currency(currency)
                .cardAlias(cardAlias)
                .cardHolderName(cardHolderName)
                .cardNumber(cardNumber)
                .cvv(cvv)
                .expireMonth(expireMonth)
                .expireYear(expireYear)
                .locale(locale)
                .build();
    }
}
