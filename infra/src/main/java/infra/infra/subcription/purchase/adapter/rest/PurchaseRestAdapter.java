package infra.infra.subcription.purchase.adapter.rest;

import infra.infra.subcription.purchase.adapter.rest.dto.request.CancelSubscriptionIyzicoRequest;
import infra.infra.subcription.purchase.adapter.rest.dto.request.PurchaseViaIyzicoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.domain.common.usecase.BeanAwareUseCasePublisher;
import org.domain.purchase.model.response.CancelIyzicoSubscriptionResponse;
import org.domain.purchase.model.response.PurchaseViaIyzicoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Service
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/purchase")
public class PurchaseRestAdapter extends BeanAwareUseCasePublisher {

    @PostMapping("/iyzico")
    public ResponseEntity<PurchaseViaIyzicoResponse> purchaseViaIyzico(
            @RequestHeader("x-user-id") String userId,
            @RequestBody PurchaseViaIyzicoRequest request) {
        return ResponseEntity.ok(publish(PurchaseViaIyzicoResponse.class, request.toUseCase(userId)));
    }

    @PostMapping("/iyzico/cancel")
    public ResponseEntity<CancelIyzicoSubscriptionResponse> cancelIyzicoSubscription(
            @RequestHeader("x-user-id") String userId,
            @RequestBody CancelSubscriptionIyzicoRequest request) {
        return ResponseEntity.ok(publish(CancelIyzicoSubscriptionResponse.class, request.toUseCase(userId)));
    }
}
