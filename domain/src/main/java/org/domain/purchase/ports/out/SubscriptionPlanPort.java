package org.domain.purchase.ports.out;

import org.domain.purchase.model.SubscriptionPlan;

public interface SubscriptionPlanPort {

    SubscriptionPlan checkSubscriptionPlanIsExist(String subscriptionPlanId);
}
