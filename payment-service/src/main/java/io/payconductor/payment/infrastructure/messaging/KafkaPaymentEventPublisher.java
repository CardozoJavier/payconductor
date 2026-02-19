package io.payconductor.payment.infrastructure.messaging;

import io.payconductor.payment.domain.model.event.DomainEvent;
import io.payconductor.payment.domain.port.out.PaymentEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Phase 1 direct Kafka publisher.
 *
 * WARNING: This is a two-phase commit hazard — if the DB write succeeds and Kafka publish fails
 * (or vice versa), the system is in an inconsistent state. Replace with the transactional outbox
 * pattern in Phase 2 (kafka-pipeline module).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaPaymentEventPublisher implements PaymentEventPublisher {

    private static final String PAYMENT_EVENTS_TOPIC = "payment.events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(List<DomainEvent> events) {
        events.forEach(event -> {
            log.debug("Publishing domain event type={} aggregateId={} version={}",
                    event.getClass().getSimpleName(), event.aggregateId(), event.aggregateVersion());
            kafkaTemplate.send(PAYMENT_EVENTS_TOPIC, event.aggregateId().toString(), event);
        });
    }
}
