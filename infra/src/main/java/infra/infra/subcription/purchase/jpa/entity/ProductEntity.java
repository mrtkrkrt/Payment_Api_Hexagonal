package infra.infra.subcription.purchase.jpa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.domain.purchase.model.Currency;
import org.domain.purchase.model.Product;
import org.domain.purchase.model.SubscriptionPeriod;
import org.domain.purchase.model.SubscriptionStatus;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "product")
@SuperBuilder
public class ProductEntity extends BaseEntity {
    @Field
    private String userId;
    @Field
    private int credit;
    @Field
    private Currency currency;
    @Field
    private SubscriptionStatus status;
    @Field
    private boolean autoRenewal;
    @Field
    private SubscriptionPeriod subscriptionPeriod;
    @Field
    private String subscriptionPlanId;
    @Field
    private String subscriptionReferenceCode;

    public static ProductEntity from(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .userId(product.getUserId())
                .credit(product.getCredit())
                .currency(product.getCurrency())
                .status(product.getStatus())
                .autoRenewal(product.isAutoRenewal())
                .subscriptionPeriod(product.getSubscriptionPeriod())
                .subscriptionPlanId(product.getSubscriptionPlanId())
                .subscriptionReferenceCode(product.getSubscriptionReferenceCode())
                .build();
    }

    public Product from() {
        return Product.builder()
                .id(getId())
                .userId(userId)
                .credit(credit)
                .currency(currency)
                .status(status)
                .autoRenewal(autoRenewal)
                .subscriptionPeriod(subscriptionPeriod)
                .subscriptionPlanId(subscriptionPlanId)
                .subscriptionReferenceCode(subscriptionReferenceCode)
                .build();
    }

}
