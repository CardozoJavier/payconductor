package io.payconductor.payment.domain.model;

public enum PaymentStatus {
    INITIATED,
    AUTHORIZED,
    CAPTURED,
    PARTIALLY_CAPTURED,
    FAILED,
    REFUNDED,
    PARTIALLY_REFUNDED,
    VOIDED
}
