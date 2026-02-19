package io.payconductor.payment.infrastructure.web.dto;

import io.payconductor.payment.domain.model.Payment;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        String merchantId,
        BigDecimal amount,
        String currency,
        String status
) {
    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getId().value(),
                payment.getMerchantId(),
                payment.getAmount().toDecimal(),
                payment.getAmount().currency().getCurrencyCode(),
                payment.getStatus().name()
        );
    }
}
