package io.payconductor.payment.domain.port.out;

import io.payconductor.payment.domain.model.Payment;
import io.payconductor.payment.domain.model.PaymentId;

import java.util.Optional;

/**
 * Secondary port — persistence abstraction for payment aggregates.
 * Implemented by {@link io.payconductor.payment.infrastructure.persistence.PostgresPaymentRepository}.
 *
 * The save contract: persist uncommitted domain events and update aggregate state.
 * Optimistic locking is enforced via the aggregate version.
 */
public interface PaymentRepository {

    void save(Payment payment);

    Optional<Payment> findById(PaymentId id);

    Optional<Payment> findByIdempotencyKey(String idempotencyKey);
}
