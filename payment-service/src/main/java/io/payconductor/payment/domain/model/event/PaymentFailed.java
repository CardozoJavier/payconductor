package io.payconductor.payment.domain.model.event;

import java.time.Instant;
import java.util.UUID;

public record PaymentFailed(
        UUID eventId,
        UUID aggregateId,
        String failureReason,
        String processorResponseCode,
        Instant occurredAt,
        long aggregateVersion
) implements DomainEvent {}
