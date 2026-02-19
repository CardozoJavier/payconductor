package io.payconductor.payment.domain.model.event;

import java.time.Instant;
import java.util.UUID;

public record PaymentAuthorized(
        UUID eventId,
        UUID aggregateId,
        String processorReference,
        String processorResponseCode,
        Instant occurredAt,
        long aggregateVersion
) implements DomainEvent {}
