package org.domain.purchase.model;

public enum SubscriptionStatus {
    MERCHANT_SUSPENDED(-99),
    EXPIRED(-4),
    UNPAID(-3),
    CANCELED(-1),
    ACTIVE(1),
    PENDING(2),
    UPGRADED(3);


    private final Integer value;

    private SubscriptionStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}
