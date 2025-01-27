package infra.infra.subcription.purchase.adapter;

import com.iyzipay.Options;
import com.iyzipay.model.Status;
import com.iyzipay.model.SubscriptionAddress;
import com.iyzipay.model.subscription.Subscription;
import com.iyzipay.model.subscription.SubscriptionCheckoutForm;
import com.iyzipay.model.subscription.SubscriptionInitialize;
import com.iyzipay.model.subscription.SubscriptionOperation;
import com.iyzipay.model.subscription.enumtype.SubscriptionInitialStatus;
import com.iyzipay.model.subscription.resource.SubscriptionCardData;
import com.iyzipay.model.subscription.resource.SubscriptionCustomer;
import com.iyzipay.request.subscription.InitializeSubscriptionRequest;
import infra.infra.subcription.purchase.common.config.IyzicoPaymentProperties;
import lombok.extern.slf4j.Slf4j;
import org.domain.purchase.model.request.InitializeSubscriptionHandlerRequest;
import org.domain.purchase.model.response.SubscriptionCheckoutFormData;
import org.domain.purchase.model.response.SubscriptionInitData;
import org.domain.purchase.model.response.SubscriptionData;
import org.domain.purchase.ports.out.IyzicoPort;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IyzicoAdapter implements IyzicoPort {

    public static final String IYZICO_CHECKOUT_FORM_SUCCESS_STATUS_CODE = "success";
    private final IyzicoPaymentProperties payProperties;

    public IyzicoAdapter(IyzicoPaymentProperties payProperties) {
        this.payProperties = payProperties;
    }

    @Override
    public SubscriptionCheckoutFormData retrieveSubscriptionCheckOutForm(String productToken) {
        log.info("Retrieving subscription check out form for product token: {}", productToken);
        SubscriptionCheckoutForm subscriptionCheckOutForm = SubscriptionCheckoutForm.retrieve(productToken, getOptions());
        checkSubscriptionFormOperationCompletedSuccessfully(subscriptionCheckOutForm);
        return buildSubscriptionCheckOutFormData(subscriptionCheckOutForm);
    }

    @Override
    public SubscriptionData retrieveSubscription(String subscriptionReferenceCode) {
        log.info("Retrieving subscription for reference code: {}", subscriptionReferenceCode);
        Subscription subscription = Subscription.retrieve(subscriptionReferenceCode, getOptions());
        return buildSubscriptionData(subscription);
    }

    @Override
    public SubscriptionInitData initializeSubscription(InitializeSubscriptionHandlerRequest subscriptionHandlerRequest) {
        log.info("Initializing subscription for request: {}", subscriptionHandlerRequest);
        SubscriptionInitialize subscriptionInitialize = initializeSub(subscriptionHandlerRequest);
        log.info("Subscription initialized successfully: {}", subscriptionInitialize);
        return buildSubscriptionInitData(subscriptionInitialize);
    }

    @Override
    public String cancelSubscription(String subscriptionReferenceCode) {
        SubscriptionOperation subscriptionOperation = SubscriptionOperation.cancel(subscriptionReferenceCode, getOptions());
        checkCancelOperationIsCompletedSuccessfully(subscriptionReferenceCode, subscriptionOperation);
        Subscription subscription = Subscription.retrieve(subscriptionReferenceCode, getOptions());
        return subscription.getSubscriptionData().getCanceledAt();
    }

    private static void checkCancelOperationIsCompletedSuccessfully(String subscriptionReferenceCode, SubscriptionOperation subscriptionOperation) {
        if (Status.SUCCESS.name().equals(subscriptionOperation.getStatus())) {
            log.info("Subs cancel fail, subscriptionRefCode: {}, failMessage: {} ", subscriptionReferenceCode,
                    subscriptionOperation.getErrorMessage());
            throw new RuntimeException("Subscription cancel operation failed: " + subscriptionOperation.getErrorMessage());
        }
    }

    private SubscriptionInitData buildSubscriptionInitData(SubscriptionInitialize subscriptionInitialize) {
        return SubscriptionInitData.builder()
                .status(subscriptionInitialize.getStatus())
                .subscriptionReferenceCode(subscriptionInitialize.getCreatedSubscriptionData().getReferenceCode())
                .parentReferenceCode(subscriptionInitialize.getCreatedSubscriptionData().getParentReferenceCode())
                .customerReferenceCode(subscriptionInitialize.getCreatedSubscriptionData().getCustomerReferenceCode())
                .pricingPlanReferenceCode(subscriptionInitialize.getCreatedSubscriptionData().getPricingPlanReferenceCode())
                .build();
    }

    private SubscriptionInitialize initializeSub(InitializeSubscriptionHandlerRequest subscriptionHandlerRequest) {
        SubscriptionCardData subscriptionCardData = buildSubscriptionCardData(subscriptionHandlerRequest);
        SubscriptionAddress billingAddress = buildSubscriptionBillingAddressData();
        SubscriptionAddress shippingAddress = buildSubscriptionBillingAddressData();
        SubscriptionCustomer subscriptionCustomer = buildSubscriptionCustomer(billingAddress, shippingAddress, subscriptionHandlerRequest);
        InitializeSubscriptionRequest request = buildSubscriptionRequest(subscriptionHandlerRequest, subscriptionCardData, subscriptionCustomer);
        return SubscriptionInitialize.create(request, getOptions());
    }

    private InitializeSubscriptionRequest buildSubscriptionRequest(InitializeSubscriptionHandlerRequest subscriptionHandlerRequest, SubscriptionCardData subscriptionCardData, SubscriptionCustomer subscriptionCustomer) {
        InitializeSubscriptionRequest initializeSubscriptionRequest =
                new InitializeSubscriptionRequest();
        initializeSubscriptionRequest.setLocale(subscriptionHandlerRequest.getLocale());
        initializeSubscriptionRequest.setPricingPlanReferenceCode(subscriptionHandlerRequest.getPricingPlanReferenceCode());

        initializeSubscriptionRequest.setConversationId(subscriptionHandlerRequest.getUserId());
        initializeSubscriptionRequest.setSubscriptionInitialStatus(SubscriptionInitialStatus.ACTIVE.name());
        initializeSubscriptionRequest.setCustomer(subscriptionCustomer);
        initializeSubscriptionRequest.setPaymentCard(subscriptionCardData);
        return initializeSubscriptionRequest;
    }

    private SubscriptionCustomer buildSubscriptionCustomer(SubscriptionAddress billingAddress, SubscriptionAddress shippingAddress, InitializeSubscriptionHandlerRequest subscriptionHandlerRequest) {
        SubscriptionCustomer subscriptionCustomer = new SubscriptionCustomer();
        subscriptionCustomer.setBillingAddress(billingAddress);
        subscriptionCustomer.setEmail(subscriptionHandlerRequest.getEmail());
        subscriptionCustomer.setSurname(subscriptionHandlerRequest.getLastName());
        subscriptionCustomer.setName(subscriptionHandlerRequest.getFirstName());
        subscriptionCustomer.setGsmNumber("+90 5322367279");
        subscriptionCustomer.setShippingAddress(shippingAddress);
        subscriptionCustomer.setIdentityNumber("000000000000");
        return subscriptionCustomer;
    }

    private SubscriptionAddress buildSubscriptionBillingAddressData() {
        SubscriptionAddress billingAddress = new SubscriptionAddress();
        billingAddress.setContactName("Alicibul Bilgi Teknolojileri Pazarlama Ve Ticaret Anonim Şirketi");
        billingAddress.setCity("Istanbul");
        billingAddress.setCountry("Turkey");
        billingAddress.setAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        billingAddress.setZipCode("34000");
        return billingAddress;
    }

    private SubscriptionCardData buildSubscriptionCardData(InitializeSubscriptionHandlerRequest subscriptionHandlerRequest) {
        SubscriptionCardData subscriptionCardData = new SubscriptionCardData();
        subscriptionCardData.setCardHolderName(subscriptionHandlerRequest.getCardHolderName());
        subscriptionCardData.setCardNumber(subscriptionHandlerRequest.getCardNumber());
        subscriptionCardData.setCvc(subscriptionHandlerRequest.getCvc());
        subscriptionCardData.setExpireMonth(subscriptionHandlerRequest.getExpireMonth());
        subscriptionCardData.setExpireYear(subscriptionHandlerRequest.getExpireYear());
        return subscriptionCardData;
    }

    private SubscriptionData buildSubscriptionData(Subscription subscription) {
        return SubscriptionData.builder()
                .referenceCode(subscription.getSubscriptionData().getReferenceCode())
                .pricingPlanReferenceCode(subscription.getSubscriptionData().getPricingPlanReferenceCode())
                .startDate(subscription.getSubscriptionData().getStartDate())
                .customerEmail(subscription.getSubscriptionData().getCustomerEmail())
                .build();
    }

    private SubscriptionCheckoutFormData buildSubscriptionCheckOutFormData(SubscriptionCheckoutForm subscriptionCheckOutForm) {
        return SubscriptionCheckoutFormData.builder()
                .subscriptionReferenceCode(subscriptionCheckOutForm.getCreatedSubscriptionData().getReferenceCode())
                .parentReferenceCode(subscriptionCheckOutForm.getCreatedSubscriptionData().getParentReferenceCode())
                .customerReferenceCode(subscriptionCheckOutForm.getCreatedSubscriptionData().getCustomerReferenceCode())
                .pricingPlanReferenceCode(subscriptionCheckOutForm.getCreatedSubscriptionData().getPricingPlanReferenceCode())
                .build();
    }

    private void checkSubscriptionFormOperationCompletedSuccessfully(SubscriptionCheckoutForm subscriptionCheckOutForm) {
        if (!subscriptionCheckOutForm.getStatus().equals(IYZICO_CHECKOUT_FORM_SUCCESS_STATUS_CODE)) {
            log.error("Subscription check out form operation failed: {}", subscriptionCheckOutForm.getErrorMessage());
            throw new RuntimeException("Subscription check out form operation failed: " + subscriptionCheckOutForm.getErrorMessage());
        }
    }

    private Options getOptions() {
        Options options = new Options();
        options.setApiKey(payProperties.getIyzicoApiKey());
        options.setBaseUrl(payProperties.getIyzicoBaseUrl());
        options.setSecretKey(payProperties.getIyzicoSecretKey());
        return options;
    }
}
