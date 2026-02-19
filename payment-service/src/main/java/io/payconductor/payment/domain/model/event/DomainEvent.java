package io.payconductor.payment.domain.model.event;

import java.time.Instant;
import java.util.UUID;

/**
 * Marker interface for all domain events emitted by payment aggregates.
 * Implementations are sealed to enforce exhaustive pattern matching at call sites.
 */
public sealed interface DomainEvent
        permits PaymentInitiated, PaymentAuthorized, PaymentCaptured, PaymentFailed, PaymentRefunded {

    UUID eventId();

    UUID aggregateId();

    Instant occurredAt();

    /**
     * Monotonically increasing version of the aggregate at the time this event was applied.
     * Used for optimistic locking in the event store.
     */
    long aggregateVersion();
}
