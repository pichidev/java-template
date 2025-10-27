# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Test Commands

**Standard build and test:**
```bash
mvn clean verify
```

**Run tests with coverage report:**
```bash
mvn clean test
# Coverage reports are generated in target/reports/jacoco/
```

**Run a single test class:**
```bash
mvn test -Dtest=CreateUserUseCaseTest
```

**Run a single test method:**
```bash
mvn test -Dtest=CreateUserUseCaseTest#executeCreatesUserWhenEmailDoesNotExist
```

**Run the application locally:**
```bash
# Start PostgreSQL with Docker Compose first
docker compose up -d

# Run the Spring Boot application
mvn spring-boot:run
```

**Database migration:**
Flyway migrations run automatically on application startup. Migrations are in `src/main/resources/db/migration/`.

## Architecture Overview

This is a Spring Boot 3.5.6 application using Java 21, following **Hexagonal Architecture** (Ports & Adapters) with **Spring Modulith** for module boundaries.

### Module Structure

The application is organized into independent modules under `ar.com.pichidev.template`:

- **auth** - OAuth authentication module (Google OAuth integration via piauth library)
- **user** - User management module
- **common** - Shared utilities and configurations (marked as OPEN module)

### Hexagonal Architecture Layers

Each module (except `common`) follows this structure:

```
{module}/
├── core/                      # Business logic (hexagon core)
│   ├── entity/               # Domain entities
│   ├── usecase/              # Use case implementations
│   ├── service/              # Domain services and orchestrators
│   ├── interfaces/           # Port definitions
│   │   ├── repository/       # Outbound ports for data persistence
│   │   ├── integration/      # Outbound ports for external services
│   │   └── usecase/          # Inbound ports (strategies)
│   └── exception/            # Domain exceptions
├── entrypoint/               # Inbound adapters
│   └── api/                  # REST API controllers
│       └── dto/              # API DTOs (input/output)
└── infrastructure/           # Outbound adapters
    ├── postgresql/           # Database adapters
    │   ├── adapter/          # JPA adapters
    │   ├── orm/              # JPA entity models
    │   ├── repository/       # Repository implementations
    │   └── mapper/           # Entity ↔ Domain mappers
    ├── integration/          # External service adapters
    │   └── external/         # External API clients
    └── auth/                 # Auth-specific infrastructure (OAuth stores)
```

**Key patterns:**
- **Repositories** implement port interfaces from `core/interfaces/repository/` and handle data persistence
- **Use Cases** contain business logic and orchestrate domain operations
- **Services** coordinate complex workflows across multiple use cases
- **Strategies** implement the Strategy pattern (e.g., OAuth provider strategies)

### OAuth Authentication Flow

The auth module uses the custom `piauth` library (v0.0.13) for OAuth integration:

1. OAuth providers are configured via `OAuthConfig`
2. Each provider has a strategy implementing `OAuthLoginStrategy` (e.g., `GoogleOAuthLoginStrategy`)
3. `OAuthLoginStrategyResolver` selects the appropriate strategy based on provider
4. `OAuthLoginOrchestrator` coordinates the flow:
   - Validates provider user info via strategy
   - Checks for existing `AuthIdentity` (provider → user mapping)
   - Creates new user if needed via `CreateUserPort` (calls user module)
   - Returns JWT token information
5. `AppOAuthUserResolver` integrates with piauth library to generate tokens

**Cross-module communication:** Auth module calls user module through port interfaces (`CreateUserPort`, `GetUserInformationPort`), maintaining loose coupling.

### Spring Modulith Boundaries

- Package-info files define module interfaces with `@NamedInterface("name")`
- Only classes in packages marked with `@NamedInterface` are accessible to other modules
- The `common` module is marked `type = OPEN` for shared utilities
- Verify module boundaries: `mvn spring-modulith:verify`

## Testing Strategy

- **Unit tests**: Mock dependencies, test business logic in isolation (e.g., `CreateUserUseCaseTest`)
- **Integration tests**: Use `@SpringBootTest`, test full flow with real database (e.g., `CreateUserDbRepositoryIntegrationTest`)
- **Coverage requirement**: 80% minimum (enforced in CI via JaCoCo)
- **Test naming**: `{MethodName}{Scenario}` pattern (e.g., `executeCreatesUserWhenEmailDoesNotExist`)

**Coverage exclusions** (see pom.xml):
- DTOs (`**/dto/**`)
- Entities (`**/entity/**`)
- Configuration classes (`**/configuration/**`)
- Exception classes (`**/exception/**`)
- Interceptors (`**/interceptor/**`)
- Response classes (`**/common/rest/response/**`)

## Database

- **PostgreSQL** in production (configured via Docker Compose on port 5434)
- **H2** for testing (in-memory)
- **Flyway** manages schema migrations (runs automatically on startup)
- Schema versioning: `V{number}__{description}.sql` in `src/main/resources/db/migration/`

## Key Technologies

- **Spring Boot 3.5.6** with Spring Data JPA, Spring Security
- **Spring Modulith 1.4.3** for modular architecture
- **Lombok** for reducing boilerplate
- **JaCoCo** for code coverage
- **piauth** (custom library from GitHub Packages) for OAuth
- **Flyway** for database migrations
- **Hypersistence Utils** for advanced Hibernate features

## GitHub Packages Authentication

The project uses a private dependency (`piauth`) from GitHub Packages. CI requires secrets:
- `READ_PACKAGES_USER`
- `READ_PACKAGES_TOKEN`

For local development, configure Maven settings.xml with GitHub credentials.