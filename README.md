# PayConductor

An event-driven payment orchestration platform built with a microservices architecture.

## Services

| Service | Framework | Role |
|---|---|---|
| `payment-service` | Spring Boot | Command side (CQRS) — handles payment lifecycle (init, auth, capture, refund) |
| `kafka-pipeline` | Spring Boot | Outbox pattern, idempotent consumers, dead letter queue |
| `projection-service` | Quarkus | Query side (CQRS) — maintains read-optimized payment views |
| `iso-adapter` | Spring Boot | Translates between ISO 8583 and ISO 20022 formats |
| `reconciliation-job` | Spring Batch | Reconciles processed payments against settlement files |

## Tech Stack

- **Java 21**, Maven multi-module
- **Spring Boot 3** / **Quarkus 3**
- **Kafka** with Schema Registry
- **PostgreSQL** (per-service databases) + **Flyway**
- **Redis** for distributed locks/caching
- **OpenTelemetry** + Jaeger, Prometheus, Grafana

## Prerequisites

- Docker & Docker Compose
- Java 21
- Maven 3.9+

## Getting Started

```bash
# 1. Start infrastructure (Kafka, PostgreSQL, Redis, observability stack)
docker-compose up -d

# 2. Build all modules
mvn clean install

# 3. Run services
mvn -pl payment-service spring-boot:run
mvn -pl kafka-pipeline spring-boot:run
mvn -pl projection-service quarkus:dev
mvn -pl iso-adapter spring-boot:run
mvn -pl reconciliation-job spring-boot:run
```

## Monitoring

| Tool | URL |
|---|---|
| Jaeger (tracing) | http://localhost:16686 |
| Prometheus | http://localhost:9090 |
| Grafana | http://localhost:3000 (admin/admin) |
| Schema Registry | http://localhost:8081 |
