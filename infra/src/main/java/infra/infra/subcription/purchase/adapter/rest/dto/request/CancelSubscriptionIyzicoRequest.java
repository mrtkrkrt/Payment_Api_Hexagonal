package infra.infra.subcription.purchase.adapter.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.domain.purchase.usecase.CancelSubscriptionIyzicoUseCase;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelSubscriptionIyzicoRequest {
    private String subscriptionPlanId;

    public CancelSubscriptionIyzicoUseCase toUseCase(String userId) {
        return CancelSubscriptionIyzicoUseCase.builder()
                .subscriptionPlanId(subscriptionPlanId)
                .userId(userId)
                .build();
    }
}
