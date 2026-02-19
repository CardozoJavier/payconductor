package io.payconductor.payment.infrastructure.persistence;

import io.payconductor.payment.domain.model.Payment;
import io.payconductor.payment.domain.model.PaymentId;
import io.payconductor.payment.domain.port.out.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Persistence adapter for the Payment aggregate.
 *
 * Phase 1: simple JPA-based row-per-aggregate snapshot store.
 * Phase 2: replace with append-only event store — the PaymentRepository port stays the same,
 *          only this adapter changes.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class PostgresPaymentRepository implements PaymentRepository {

    // TODO Phase 1: inject JPA entity manager or Spring Data repository here

    @Override
    public void save(Payment payment) {
        // Phase 1: map Payment aggregate → JPA entity and persist
        throw new UnsupportedOperationException("Persistence adapter not yet wired — implement in Phase 1 week 2");
    }

    @Override
    public Optional<Payment> findById(PaymentId id) {
        throw new UnsupportedOperationException("Persistence adapter not yet wired — implement in Phase 1 week 2");
    }

    @Override
    public Optional<Payment> findByIdempotencyKey(String idempotencyKey) {
        throw new UnsupportedOperationException("Persistence adapter not yet wired — implement in Phase 1 week 2");
    }
}
