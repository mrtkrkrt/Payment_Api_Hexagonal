package infra.infra.subcription.purchase.jpa;

import infra.infra.subcription.purchase.jpa.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<ProductEntity, String> {
    Optional<ProductEntity> getProductsByUserIdAndSubscriptionPlanId(String userId, String id);

}
