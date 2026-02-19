package io.payconductor.payment.domain.model.event;

import io.payconductor.payment.domain.model.Money;

import java.time.Instant;
import java.util.UUID;

public record PaymentRefunded(
        UUID eventId,
        UUID aggregateId,
        Money refundedAmount,
        String processorRefundReference,
        Instant occurredAt,
        long aggregateVersion
) implements DomainEvent {}
