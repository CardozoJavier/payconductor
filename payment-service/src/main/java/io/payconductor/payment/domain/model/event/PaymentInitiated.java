package io.payconductor.payment.domain.model.event;

import io.payconductor.payment.domain.model.Money;

import java.time.Instant;
import java.util.UUID;

public record PaymentInitiated(
        UUID eventId,
        UUID aggregateId,
        String merchantId,
        Money amount,
        String idempotencyKey,
        Instant occurredAt,
        long aggregateVersion
) implements DomainEvent {}
