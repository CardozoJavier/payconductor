package io.payconductor.payment.domain.model.event;

import io.payconductor.payment.domain.model.Money;

import java.time.Instant;
import java.util.UUID;

public record PaymentCaptured(
        UUID eventId,
        UUID aggregateId,
        Money capturedAmount,
        Instant occurredAt,
        long aggregateVersion
) implements DomainEvent {}
