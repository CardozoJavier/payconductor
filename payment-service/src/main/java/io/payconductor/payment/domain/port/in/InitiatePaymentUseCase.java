package io.payconductor.payment.domain.port.in;

import io.payconductor.payment.domain.model.Money;
import io.payconductor.payment.domain.model.Payment;

/**
 * Primary port — drives the payment aggregate from outside the domain.
 * Implemented by {@link io.payconductor.payment.application.PaymentCommandHandler}.
 */
public interface InitiatePaymentUseCase {

    record Command(String merchantId, Money amount, String idempotencyKey) {}

    Payment initiate(Command command);
}
