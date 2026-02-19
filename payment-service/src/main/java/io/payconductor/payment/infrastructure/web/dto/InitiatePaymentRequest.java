package io.payconductor.payment.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record InitiatePaymentRequest(
        @NotBlank String merchantId,
        @NotNull @Positive BigDecimal amount,
        @NotBlank String currency,
        @NotBlank String idempotencyKey
) {}
