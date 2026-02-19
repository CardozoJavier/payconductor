package io.payconductor.payment.domain.port.in;

import io.payconductor.payment.domain.model.Money;
import io.payconductor.payment.domain.model.Payment;
import io.payconductor.payment.domain.model.PaymentId;

public interface CapturePaymentUseCase {

    record Command(PaymentId paymentId, Money captureAmount, String idempotencyKey) {}

    Payment capture(Command command);
}
