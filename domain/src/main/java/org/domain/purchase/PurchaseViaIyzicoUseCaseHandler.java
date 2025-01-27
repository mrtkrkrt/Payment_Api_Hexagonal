package org.domain.purchase;

import lombok.extern.slf4j.Slf4j;
import org.domain.common.DomainComponent;
import org.domain.common.exception.PaymentApiBusinessException;
import org.domain.common.usecase.ObservableUseCasePublisher;
import org.domain.common.usecase.UseCaseHandler;
import org.domain.purchase.model.PaymentHistory;
import org.domain.purchase.model.Product;
import org.domain.purchase.model.SubscriptionPlan;
import org.domain.purchase.model.SubscriptionStatus;
import org.domain.purchase.model.request.InitializeSubscriptionHandlerRequest;
import org.domain.purchase.model.response.PurchaseViaIyzicoResponse;
import org.domain.purchase.model.response.SubscriptionInitData;
import org.domain.purchase.ports.out.IyzicoPort;
import org.domain.purchase.ports.out.PaymentHistoryPort;
import org.domain.purchase.ports.out.ProductPort;
import org.domain.purchase.ports.out.SubscriptionPlanPort;
import org.domain.purchase.usecase.PurchaseIyzicoUseCase;

@Slf4j
@DomainComponent
public class PurchaseViaIyzicoUseCaseHandler extends ObservableUseCasePublisher implements UseCaseHandler<PurchaseViaIyzicoResponse, PurchaseIyzicoUseCase> {

    private final ProductPort productPort;
    private final IyzicoPort iyzicoPort;
    private final SubscriptionPlanPort subscriptionPlanPort;
    private final PaymentHistoryPort paymentHistoryPort;

    public PurchaseViaIyzicoUseCaseHandler(ProductPort productPort, IyzicoPort iyzicoPort, SubscriptionPlanPort subscriptionPlanPort, PaymentHistoryPort paymentHistoryPort) {
        this.productPort = productPort;
        this.iyzicoPort = iyzicoPort;
        this.subscriptionPlanPort = subscriptionPlanPort;
        this.paymentHistoryPort = paymentHistoryPort;
        register(PurchaseIyzicoUseCase.class, this);
    }

    @Override
    public PurchaseViaIyzicoResponse handle(PurchaseIyzicoUseCase useCase) {
        log.info("Handling use case: {}", useCase);
        SubscriptionPlan subscriptionPlan = subscriptionPlanPort.checkSubscriptionPlanIsExist(useCase.getSubscriptionPlanId());
        productPort.checkUserAlreadyHasProduct(useCase.getUserId(), useCase.getSubscriptionPlanId());
        // TODO retrieve user data and pass to the initializeSubscription method CRITICALLLLL!!!!
        SubscriptionInitData subscriptionInitData = iyzicoPort.initializeSubscription(InitializeSubscriptionHandlerRequest.of(useCase, subscriptionPlan));
        log.info("Subscription initialized successfully: {}", subscriptionInitData);
        PaymentHistory paymentHistory = useCase.toPaymentHistory();
        checkPaymentSuccess(subscriptionInitData, paymentHistory);
        productPort.saveProduct(Product.from(useCase, subscriptionPlan, subscriptionInitData));
        return PurchaseViaIyzicoResponse.builder().success(true).build();
    }

    private void checkPaymentSuccess(SubscriptionInitData subscriptionInitData, PaymentHistory paymentHistory) {
        if (!subscriptionInitData.getStatus().equals("success")) {
            log.info("Subscription initialization failed: {}", subscriptionInitData);
            paymentHistory.setStatus(SubscriptionStatus.CANCELED);
            paymentHistoryPort.savePayment(paymentHistory);
            throw new PaymentApiBusinessException("Subscription initialization failed");
        }
        log.info("Subscription initialization success: {}", subscriptionInitData);
        paymentHistory.setStatus(SubscriptionStatus.ACTIVE);
        paymentHistoryPort.savePayment(paymentHistory);
    }

}
