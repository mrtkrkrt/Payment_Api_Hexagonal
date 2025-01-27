package org.domain.purchase.ports.out;

import org.domain.purchase.model.PaymentHistory;

public interface PaymentHistoryPort {

    void savePayment(PaymentHistory paymentHistory);
}
