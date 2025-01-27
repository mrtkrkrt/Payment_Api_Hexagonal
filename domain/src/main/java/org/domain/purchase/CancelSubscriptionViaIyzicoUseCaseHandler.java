package org.domain.purchase;

import lombok.extern.slf4j.Slf4j;
import org.domain.common.DomainComponent;
import org.domain.common.usecase.ObservableUseCasePublisher;
import org.domain.common.usecase.UseCaseHandler;
import org.domain.purchase.model.Product;
import org.domain.purchase.model.response.CancelIyzicoSubscriptionResponse;
import org.domain.purchase.ports.out.IyzicoPort;
import org.domain.purchase.ports.out.ProductPort;
import org.domain.purchase.usecase.CancelSubscriptionIyzicoUseCase;

@Slf4j
@DomainComponent
public class CancelSubscriptionViaIyzicoUseCaseHandler extends ObservableUseCasePublisher implements UseCaseHandler<CancelIyzicoSubscriptionResponse, CancelSubscriptionIyzicoUseCase> {

    private final ProductPort productPort;
    private final IyzicoPort iyzicoPort;

    public CancelSubscriptionViaIyzicoUseCaseHandler(ProductPort productPort, IyzicoPort iyzicoPort) {
        this.productPort = productPort;
        this.iyzicoPort = iyzicoPort;
        register(CancelSubscriptionIyzicoUseCase.class, this);
    }

    @Override
    public CancelIyzicoSubscriptionResponse handle(CancelSubscriptionIyzicoUseCase useCase) {
        Product product = productPort.findByUserId(useCase.getSubscriptionPlanId(), useCase.getUserId());
        log.info("Canceling subscription: {}", product);
        String cancelSubscriptionDate = iyzicoPort.cancelSubscription(product.getSubscriptionReferenceCode());
        log.info("Subscription canceled successfully: {}", cancelSubscriptionDate);
        productPort.cancelProduct(product);
        return CancelIyzicoSubscriptionResponse.builder().build();
    }
}
