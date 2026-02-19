package io.payconductor.pipeline.infrastructure.dlq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Consumes messages that have exceeded the retry limit and landed in the DLQ.
 *
 * Current behaviour: log and store for manual review.
 * Phase 2+: implement retry classification — transient failures re-enqueue with backoff,
 * permanent failures alert on-call and write to a poison message store.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeadLetterQueueConsumer {

    @KafkaListener(
            topics = "${payconductor.kafka.dlq-topic:payment.events.dlq}",
            groupId = "${payconductor.kafka.dlq-consumer-group:kafka-pipeline-dlq}"
    )
    public void onDeadLetter(
            @Payload String payload,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset
    ) {
        log.error("DLQ message received topic={} partition={} offset={} payload={}",
                topic, partition, offset, payload);
        // Phase 2: persist to dead_letter_messages table for ops visibility
    }
}
