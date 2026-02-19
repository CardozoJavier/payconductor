package io.payconductor.payment.infrastructure.web;

import io.payconductor.payment.domain.model.Money;
import io.payconductor.payment.domain.model.Payment;
import io.payconductor.payment.domain.port.in.InitiatePaymentUseCase;
import io.payconductor.payment.infrastructure.web.dto.InitiatePaymentRequest;
import io.payconductor.payment.infrastructure.web.dto.PaymentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Currency;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final InitiatePaymentUseCase initiatePaymentUseCase;

    @PostMapping
    public ResponseEntity<PaymentResponse> initiate(@Valid @RequestBody InitiatePaymentRequest request) {
        Money amount = Money.of(request.amount(), Currency.getInstance(request.currency()));

        Payment payment = initiatePaymentUseCase.initiate(new InitiatePaymentUseCase.Command(
                request.merchantId(),
                amount,
                request.idempotencyKey()
        ));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(payment.getId().value())
                .toUri();

        return ResponseEntity.created(location).body(PaymentResponse.from(payment));
    }
}
