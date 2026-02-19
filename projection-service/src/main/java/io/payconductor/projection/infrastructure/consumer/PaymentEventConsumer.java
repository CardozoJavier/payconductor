package io.payconductor.projection.infrastructure.consumer;

import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

/**
 * Consumes domain events from the payment.events topic and updates read-optimized projections.
 *
 * @Blocking ensures the handler runs on a worker thread, keeping the reactive event loop free
 * for I/O. Required here because projection updates involve database writes.
 */
@ApplicationScoped
public class PaymentEventConsumer {

    private static final Logger log = Logger.getLogger(PaymentEventConsumer.class);

    @Incoming("payment-events")
    @Blocking
    public void onPaymentEvent(String eventJson) {
        // Phase 1: deserialise and route to the appropriate projection handler
        // Phase 2: schema-aware deserialisation via Confluent Schema Registry / Avro
        log.debugf("Received payment event: %s", eventJson);
    }
}
