package infra.infra.subcription.purchase.jpa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.domain.purchase.model.Currency;
import org.domain.purchase.model.SubscriptionPeriod;
import org.domain.purchase.model.SubscriptionPlan;
import org.domain.purchase.model.SubscriptionStatus;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "subscription_plan")
public class SubscriptionPlanEntity extends BaseEntity {
    @Field
    private Map<Currency, String> iyzicoRefCodes;
    @Field
    private Integer credit;
    @Field
    private Map<Currency, BigDecimal> price;
    @Field
    private SubscriptionPeriod period;
    @Field
    private SubscriptionStatus status;

    public SubscriptionPlan toDomain() {
        return SubscriptionPlan.builder()
                .id(getId())
                .iyzicoRefCodes(iyzicoRefCodes)
                .credit(credit)
                .price(price)
                .period(period)
                .status(status)
                .build();
    }

}
