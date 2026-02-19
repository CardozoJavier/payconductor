package io.payconductor.reconciliation.domain.model;

public enum ReconciliationStatus {
    MATCHED,
    UNMATCHED_IN_PROCESSOR,   // Processor settled it, we have no matching ledger entry
    UNMATCHED_IN_LEDGER,      // We have a ledger entry, processor has no matching settlement
    AMOUNT_MISMATCH,
    PENDING
}
