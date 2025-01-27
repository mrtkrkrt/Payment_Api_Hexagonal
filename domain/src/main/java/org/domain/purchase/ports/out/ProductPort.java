package org.domain.purchase.ports.out;

import org.domain.purchase.model.Product;

public interface ProductPort {

    void checkUserAlreadyHasProduct(String userId, String productId);

    void saveProduct(Product product);

    Product findByUserId(String subscriptionPlanId, String userId);

    void cancelProduct(Product product);
}
