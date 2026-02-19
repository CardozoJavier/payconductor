package io.payconductor.reconciliation.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A single line from a processor settlement file, normalised across all processor formats.
 */
public record ReconciliationEntry(
        UUID paymentId,
        String processorReference,
        BigDecimal settledAmount,
        String currency,
        String processorName,
        LocalDate settlementDate,
        ReconciliationStatus status
) {}
