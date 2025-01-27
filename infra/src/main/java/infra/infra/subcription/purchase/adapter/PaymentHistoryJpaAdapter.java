package infra.infra.subcription.purchase.adapter;

import infra.infra.subcription.purchase.jpa.PaymentHistoryRepository;
import infra.infra.subcription.purchase.jpa.entity.PaymentHistoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.domain.purchase.model.PaymentHistory;
import org.domain.purchase.ports.out.PaymentHistoryPort;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentHistoryJpaAdapter implements PaymentHistoryPort {

    private final PaymentHistoryRepository paymentHistoryRepository;

    public PaymentHistoryJpaAdapter(PaymentHistoryRepository paymentHistoryRepository) {
        this.paymentHistoryRepository = paymentHistoryRepository;
    }

    @Override
    public void savePayment(PaymentHistory paymentHistory) {
        log.info("Saving payment history: {}", paymentHistory);
        paymentHistoryRepository.save(PaymentHistoryEntity.from(paymentHistory));
    }
}
