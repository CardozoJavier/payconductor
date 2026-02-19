package io.payconductor.pipeline.infrastructure.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Polls the outbox table and publishes unpublished events to Kafka.
 *
 * The outbox table is written atomically with the domain state change (same DB transaction).
 * This component is the only path from the database to Kafka, which eliminates the dual-write hazard.
 *
 * Polling interval is deliberately kept short (< 1s) to minimise end-to-end latency.
 * In Phase 2 this will be replaced with Debezium CDC for sub-millisecond propagation.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    // Inject OutboxRepository here in Phase 2

    @Scheduled(fixedDelayString = "${payconductor.outbox.poll-interval-ms:200}")
    @Transactional
    public void publishPendingEvents() {
        // Phase 2 implementation:
        // 1. SELECT * FROM outbox WHERE published = false ORDER BY created_at LIMIT 100 FOR UPDATE SKIP LOCKED
        // 2. For each row: kafkaTemplate.send(row.topic, row.aggregateId, row.payload)
        // 3. UPDATE outbox SET published = true WHERE id IN (...)
        log.trace("Outbox poll — not yet wired");
    }
}
