package io.payconductor.payment.domain.model;

import java.util.UUID;

/**
 * Strongly-typed wrapper for payment aggregate identifiers.
 * Using a value object prevents accidental passing of arbitrary UUIDs where a PaymentId is expected.
 */
public record PaymentId(UUID value) {

    public PaymentId {
        if (value == null) throw new IllegalArgumentException("PaymentId cannot be null");
    }

    public static PaymentId generate() {
        return new PaymentId(UUID.randomUUID());
    }

    public static PaymentId of(String value) {
        return new PaymentId(UUID.fromString(value));
    }

    public static PaymentId of(UUID value) {
        return new PaymentId(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
