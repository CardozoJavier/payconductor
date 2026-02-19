package io.payconductor.reconciliation.infrastructure.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Spring Batch job definition for nightly reconciliation.
 *
 * Steps (Phase 5 implementation):
 *   1. fetch-settlement-files  — pull settlement CSV/XML from processor SFTP/S3
 *   2. parse-and-normalise     — parse into ReconciliationEntry, handling per-processor formats
 *   3. match-ledger-entries    — compare against our ledger, write ReconciliationResult
 *   4. generate-report         — write discrepancy report to S3 / alert on-call if breach threshold
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ReconciliationJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job reconciliationJob(Step matchLedgerStep) {
        return new JobBuilder("reconciliationJob", jobRepository)
                .start(matchLedgerStep)
                .build();
    }

    @Bean
    public Step matchLedgerStep() {
        return new StepBuilder("matchLedgerStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("Reconciliation step — not yet implemented (Phase 5)");
                    return org.springframework.batch.repeat.RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}
