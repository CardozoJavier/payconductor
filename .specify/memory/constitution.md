<!--
Sync Impact Report
- Version: none → 1.0.0 (initial ratification)
- Principles added: I. Correctness Before Performance; II. Idempotency Is Mandatory;
  III. Transactional Outbox by Default; IV. Append-Only Ledger; V. No Sensitive Data in Logs;
  VI. Hexagonal Boundaries; VII. Production Standard
- Sections added: Engineering Standards; Development Workflow; Governance
- Sources: .claude/CLAUDE.md (repo); ~/.claude/CLAUDE.md (user global); git history (commit style)
- Templates: none require updates
- Deferred: none
-->

# PayConductor Constitution

## Core Principles

### I. Correctness Before Performance
A change MUST NOT weaken a correctness guarantee for performance; any trade-off between the two
MUST be flagged explicitly.
*Rationale*: a double charge costs more than added latency. (See `.claude/CLAUDE.md`, Technical
Principles.)

### II. Idempotency Is Mandatory
Every operation that can be retried MUST be idempotent. A payment flow without an idempotency
mechanism is a defect and MUST be flagged. (See `.claude/CLAUDE.md`, Technical Principles.)

### III. Transactional Outbox by Default
Code MUST NOT write to the database and publish to Kafka as two separate steps; it MUST use a
transactional outbox or an equivalent atomic mechanism. (See `.claude/CLAUDE.md`, Technical
Principles.)

### IV. Append-Only Ledger
Ledger entries MUST NOT be updated or deleted. Balances MUST be computed from entries, never
stored as a column. (See `.claude/CLAUDE.md`, Technical Principles.)

### V. No Sensitive Data in Logs
PAN, CVV, raw card numbers and processor credentials MUST NOT appear in logs; logging code MUST
apply masking. Credentials MUST NOT be hardcoded anywhere. PCI DSS questions MUST be checked
against the current PCI DSS v4.0 spec, not guessed. (See `.claude/CLAUDE.md`, Technical
Principles, How to Handle Code Requests, and What to Do When You Are Unsure.)

### VI. Hexagonal Boundaries
Domain classes MUST NOT depend on Spring, Kafka or Postgres-specific types. Infrastructure
adapters implement domain ports. A design that crosses this boundary MUST say so. (See
`.claude/CLAUDE.md`, The Project and Technical Principles.)

### VII. Production Standard
All code and documentation MUST meet production fintech standards: meaningful exception types
from the PayConductor hierarchy (never raw `RuntimeException`), structured logging, correct
transaction boundaries. Code, comments and docs MUST NOT describe the system as a demo or
learning exercise, or defer quality with "simplified" or "TODO: in production" notes. (See
`.claude/CLAUDE.md`, The Project and Framing for External Audiences.)

## Engineering Standards

- **Stack**: use the technology decisions fixed in `.claude/CLAUDE.md` (Core technical
  decisions); proposing an alternative MUST name its costs as well as its benefits.
- **Code style**: follow `.claude/CLAUDE.md`, How to Handle Code Requests (Java 21 idioms,
  Lombok, MapStruct, Spring profiles, `application.yml` configuration).
- **Dependency injection**: constructor injection only; `@Autowired` on fields MUST NOT be used;
  configuration classes live in `infrastructure` or `config` packages. (See `.claude/CLAUDE.md`,
  When writing Spring configuration.)
- **Braces**: `catch`, `else`, `else if`, `finally` and the `while` of a do-while MUST sit on the
  same line as the closing brace. Fix it in touched files; a repo-wide sweep needs approval first.
  (See `~/.claude/CLAUDE.md`, Code Style — Braces.)
- **Tests**:
  - Unit tests MUST cover domain logic only, with no Spring context and no mocks of
    infrastructure.
  - Integration tests touching Kafka, Postgres or Redis MUST use Testcontainers.
  - Tests MUST test behaviour, not framework wiring.
  - Test names SHOULD follow `should_[expected behaviour]_when_[condition]`.
  - (See `.claude/CLAUDE.md`, When writing tests.)

## Development Workflow

- **Build gate**: the Maven multi-module build (`mvn clean install`, per `README.md`) MUST pass,
  with its output shown, before a change is claimed done.
- **Phase scope**: work SHOULD stay within the current roadmap phase; patterns from later phases
  are introduced only on request, and a known later refactor is noted without blocking. (See
  `.claude/CLAUDE.md`, The Roadmap Structure.)
- **Plan review**: non-trivial plans (multi-file, persisted state, external contracts, hard to
  reverse) MUST get an independent review before implementation. (See `~/.claude/CLAUDE.md`, Plan
  Review Protocol.)
- **Honesty**: unknowns MUST be stated as unknown; processor-specific API behaviour MUST NOT be
  fabricated. (See `.claude/CLAUDE.md`, What to Do When You Are Unsure.)
- **Commits**: messages SHOULD follow the existing Conventional Commits style with module scope
  (`feat(payment-service): …`, per git history). Commits MUST NOT carry a `Co-Authored-By`
  trailer or any other AI co-author trailer. (See `~/.claude/CLAUDE.md`, Git Commits.)

## Governance

- Host instructions win over keel and Spec Kit defaults. Between host files, the repository's
  `.claude/CLAUDE.md` wins over `~/.claude/CLAUDE.md` on project-specific matters.
- This constitution refers to those files rather than copying them; when they change, re-run
  `/keel:constitution` to sync.
- Amendments use semantic versioning: MAJOR for removing or redefining a principle, MINOR for
  adding or expanding one, PATCH for wording. Each amendment prepends a Sync Impact Report.
- Plans and reviews MUST check compliance with the Core Principles; a justified deviation MUST be
  recorded in the plan.

**Version**: 1.0.0 | **Ratified**: 2026-11-27 | **Last Amended**: 2026-11-27
