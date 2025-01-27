package infra.infra.subcription.purchase.jpa;

import infra.infra.subcription.purchase.jpa.entity.PaymentHistoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentHistoryRepository extends MongoRepository<PaymentHistoryEntity, String> {
}
