package org.domain.purchase.ports.out;

import org.domain.purchase.model.request.InitializeSubscriptionHandlerRequest;
import org.domain.purchase.model.response.SubscriptionCheckoutFormData;
import org.domain.purchase.model.response.SubscriptionInitData;
import org.domain.purchase.model.response.SubscriptionData;

public interface IyzicoPort {

    SubscriptionCheckoutFormData retrieveSubscriptionCheckOutForm(String productToken);

    SubscriptionData retrieveSubscription(String subscriptionReferenceCode);

    SubscriptionInitData initializeSubscription(InitializeSubscriptionHandlerRequest request);

    String cancelSubscription(String subscriptionReferenceCode);
}
