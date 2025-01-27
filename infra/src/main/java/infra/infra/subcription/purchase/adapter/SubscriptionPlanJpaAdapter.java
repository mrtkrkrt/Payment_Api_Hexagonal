package infra.infra.subcription.purchase.adapter;

import infra.infra.subcription.purchase.jpa.SubscriptionPlanRepository;
import infra.infra.subcription.purchase.jpa.entity.SubscriptionPlanEntity;
import lombok.extern.slf4j.Slf4j;
import org.domain.purchase.model.SubscriptionPlan;
import org.domain.purchase.ports.out.SubscriptionPlanPort;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SubscriptionPlanJpaAdapter implements SubscriptionPlanPort {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionPlanJpaAdapter(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    @Override
    public SubscriptionPlan checkSubscriptionPlanIsExist(String subscriptionPlanId) {
        log.info("Checking subscription plan is exist: {}", subscriptionPlanId);
        SubscriptionPlanEntity subscriptionPlanEntity = subscriptionPlanRepository.findById(subscriptionPlanId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription plan is not exist: " + subscriptionPlanId));
        return subscriptionPlanEntity.toDomain();
    }
}
