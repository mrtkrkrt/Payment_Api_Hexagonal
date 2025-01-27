package infra.infra.subcription.purchase.adapter;

import infra.infra.subcription.purchase.jpa.ProductRepository;
import infra.infra.subcription.purchase.jpa.entity.ProductEntity;
import lombok.extern.slf4j.Slf4j;
import org.domain.purchase.model.Product;
import org.domain.purchase.model.SubscriptionStatus;
import org.domain.purchase.ports.out.ProductPort;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductJpaAdapter implements ProductPort {

    private final ProductRepository productRepository;

    public ProductJpaAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void checkUserAlreadyHasProduct(String userId, String productId) {
        log.info("Checking if user already has product: userId={}, productId={}", userId, productId);
        if (productRepository.getProductsByUserIdAndSubscriptionPlanId(userId, productId).isEmpty()){
            throw new RuntimeException("User already has product");
        }
    }

    @Override
    public void saveProduct(Product product) {
        log.info("Saving product: product={}", product);
        productRepository.save(ProductEntity.from(product));
    }

    @Override
    public Product findByUserId(String subscriptionPlanId, String userId) {
        log.info("Finding product by user id: subscriptionPlanId={}, userId={}", subscriptionPlanId, userId);
        ProductEntity product = productRepository.getProductsByUserIdAndSubscriptionPlanId(userId, subscriptionPlanId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return product.from();
    }

    @Override
    public void cancelProduct(Product product) {
        log.info("Canceling product: product={}", product);
        ProductEntity productEntity = ProductEntity.from(product);
        productEntity.setStatus(SubscriptionStatus.CANCELED);
        productEntity.setAutoRenewal(false);
        productRepository.save(productEntity);
    }

}
