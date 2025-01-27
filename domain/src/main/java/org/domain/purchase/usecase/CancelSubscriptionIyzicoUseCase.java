package org.domain.purchase.usecase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.domain.common.model.UseCase;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelSubscriptionIyzicoUseCase implements UseCase {
    private String subscriptionPlanId;
    private String userId;
}
