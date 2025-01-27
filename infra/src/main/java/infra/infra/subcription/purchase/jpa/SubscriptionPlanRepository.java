package infra.infra.subcription.purchase.jpa;

import infra.infra.subcription.purchase.jpa.entity.SubscriptionPlanEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubscriptionPlanRepository extends MongoRepository<SubscriptionPlanEntity, String> {
}
