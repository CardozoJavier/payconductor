package io.payconductor.payment.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/**
 * Immutable monetary value. Amount is stored in the minor unit (e.g. cents for USD/EUR)
 * to eliminate floating-point ambiguity in payment processing.
 *
 * Arithmetic follows the currency's default fraction digits (ISO 4217).
 */
public record Money(long amountMinorUnit, Currency currency) {

    public Money {
        if (amountMinorUnit < 0) throw new IllegalArgumentException("Monetary amount cannot be negative");
        if (currency == null) throw new IllegalArgumentException("Currency cannot be null");
    }

    public static Money of(BigDecimal amount, Currency currency) {
        int scale = currency.getDefaultFractionDigits();
        long minor = amount.setScale(scale, RoundingMode.UNNECESSARY).movePointRight(scale).longValueExact();
        return new Money(minor, currency);
    }

    public static Money ofMinorUnit(long amountMinorUnit, String currencyCode) {
        return new Money(amountMinorUnit, Currency.getInstance(currencyCode));
    }

    public BigDecimal toDecimal() {
        return BigDecimal.valueOf(amountMinorUnit, currency.getDefaultFractionDigits());
    }

    public Money add(Money other) {
        assertSameCurrency(other);
        return new Money(this.amountMinorUnit + other.amountMinorUnit, currency);
    }

    public Money subtract(Money other) {
        assertSameCurrency(other);
        long result = this.amountMinorUnit - other.amountMinorUnit;
        if (result < 0) throw new IllegalArgumentException("Subtraction would result in negative amount");
        return new Money(result, currency);
    }

    public boolean isGreaterThan(Money other) {
        assertSameCurrency(other);
        return this.amountMinorUnit > other.amountMinorUnit;
    }

    private void assertSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch: %s vs %s".formatted(this.currency, other.currency));
        }
    }

    @Override
    public String toString() {
        return "%s %s".formatted(toDecimal().toPlainString(), currency.getCurrencyCode());
    }
}
