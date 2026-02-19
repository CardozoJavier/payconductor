package io.payconductor.payment.domain.port.out;

import io.payconductor.payment.domain.model.event.DomainEvent;

import java.util.List;

/**
 * Secondary port — event publishing abstraction.
 * Implemented by the outbox pattern in kafka-pipeline or directly in
 * {@link io.payconductor.payment.infrastructure.messaging.KafkaPaymentEventPublisher}
 * depending on the phase.
 *
 * Phase 1: direct Kafka publish (acceptable for bootstrapping).
 * Phase 2+: replace with transactional outbox to guarantee at-least-once delivery.
 */
public interface PaymentEventPublisher {

    void publish(List<DomainEvent> events);
}
