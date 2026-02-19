package io.payconductor.payment.domain.model;

import io.payconductor.payment.domain.model.event.DomainEvent;
import io.payconductor.payment.domain.model.event.PaymentInitiated;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Payment aggregate root. Encapsulates all state transitions for the payment lifecycle.
 *
 * Design constraints:
 * - No framework dependencies (no Spring, no JPA). Pure domain logic.
 * - State changes are applied via domain events (event-sourcing ready for Phase 2).
 * - Uncommitted events accumulate in {@link #uncommittedEvents} until persisted by the repository.
 */
public class Payment {

    private PaymentId id;
    private String merchantId;
    private Money amount;
    private PaymentStatus status;
    private String processorReference;
    private String idempotencyKey;
    private long version;

    private final List<DomainEvent> uncommittedEvents = new ArrayList<>();

    private Payment() {
        // Reconstitution via apply() only
    }

    // -------------------------------------------------------------------------
    // Factory
    // -------------------------------------------------------------------------

    public static Payment initiate(
            PaymentId id,
            String merchantId,
            Money amount,
            String idempotencyKey
    ) {
        Payment payment = new Payment();
        payment.apply(new PaymentInitiated(
                UUID.randomUUID(),
                id.value(),
                merchantId,
                amount,
                idempotencyKey,
                Instant.now(),
                0L
        ));
        return payment;
    }

    // -------------------------------------------------------------------------
    // Event application
    // -------------------------------------------------------------------------

    private void apply(DomainEvent event) {
        switch (event) {
            case PaymentInitiated e -> {
                this.id = PaymentId.of(e.aggregateId());
                this.merchantId = e.merchantId();
                this.amount = e.amount();
                this.idempotencyKey = e.idempotencyKey();
                this.status = PaymentStatus.INITIATED;
                this.version = e.aggregateVersion();
            }
            // Phase 2: add cases for PaymentAuthorized, PaymentCaptured, etc.
            default -> throw new IllegalStateException("Unhandled event type: " + event.getClass().getSimpleName());
        }
        uncommittedEvents.add(event);
    }

    // -------------------------------------------------------------------------
    // Queries
    // -------------------------------------------------------------------------

    public PaymentId getId() { return id; }
    public String getMerchantId() { return merchantId; }
    public Money getAmount() { return amount; }
    public PaymentStatus getStatus() { return status; }
    public String getProcessorReference() { return processorReference; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public long getVersion() { return version; }

    public List<DomainEvent> getUncommittedEvents() {
        return Collections.unmodifiableList(uncommittedEvents);
    }

    public void markEventsAsCommitted() {
        uncommittedEvents.clear();
    }
}
