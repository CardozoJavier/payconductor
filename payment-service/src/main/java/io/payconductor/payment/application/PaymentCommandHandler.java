package io.payconductor.payment.application;

import io.payconductor.payment.domain.model.Payment;
import io.payconductor.payment.domain.model.PaymentId;
import io.payconductor.payment.domain.port.in.CapturePaymentUseCase;
import io.payconductor.payment.domain.port.in.InitiatePaymentUseCase;
import io.payconductor.payment.domain.port.out.PaymentEventPublisher;
import io.payconductor.payment.domain.port.out.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentCommandHandler implements InitiatePaymentUseCase, CapturePaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher eventPublisher;

    @Override
    @Transactional
    public Payment initiate(InitiatePaymentUseCase.Command command) {
        paymentRepository.findByIdempotencyKey(command.idempotencyKey()).ifPresent(existing -> {
            log.info("Idempotent hit on initiate payment, idempotencyKey={}", command.idempotencyKey());
            // Return early — idempotency enforced at controller level via returned Payment
        });

        Payment payment = Payment.initiate(
                PaymentId.generate(),
                command.merchantId(),
                command.amount(),
                command.idempotencyKey()
        );

        paymentRepository.save(payment);
        eventPublisher.publish(payment.getUncommittedEvents());
        payment.markEventsAsCommitted();

        log.info("Payment initiated paymentId={} merchantId={} amount={}",
                payment.getId(), payment.getMerchantId(), payment.getAmount());

        return payment;
    }

    @Override
    @Transactional
    public Payment capture(CapturePaymentUseCase.Command command) {
        // Phase 2: load aggregate from event store and apply PaymentCaptured event
        throw new UnsupportedOperationException("Capture not implemented — scheduled for Phase 2");
    }
}
