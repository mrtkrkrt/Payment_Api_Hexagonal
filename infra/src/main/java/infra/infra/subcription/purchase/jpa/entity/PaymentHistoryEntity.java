package infra.infra.subcription.purchase.jpa.entity;

import lombok.Builder;
import lombok.Data;
import org.domain.purchase.model.Currency;
import org.domain.purchase.model.PaymentHistory;
import org.domain.purchase.model.SubscriptionStatus;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "payment_history")
@Builder
public class PaymentHistoryEntity {

    @Field
    private String userId;

    @Field
    private Currency currency;

    @Field
    private String subscriptionPlanId;

    @Field
    private SubscriptionStatus status;

    public static PaymentHistoryEntity from(PaymentHistory entity) {
        return PaymentHistoryEntity.builder()
                .userId(entity.getUserId())
                .currency(entity.getCurrency())
                .subscriptionPlanId(entity.getSubscriptionPlanId())
                .status(entity.getStatus())
                .build();
    }
}
