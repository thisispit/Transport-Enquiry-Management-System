# Phase 2 - Folder Structure

## Main Source Tree
```text
src/main/java/com/thisispit/tems
├── TemsApplication.java
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── service
├── service/impl
└── util
```

## Test Source Tree
```text
src/test/java/com/thisispit/tems
├── controller
├── service
└── repository
```

## Resource Tree
```text
src/main/resources
```

## Structure Rules
- Keep controllers thin.
- Put business logic in service implementation classes.
- Keep repositories focused on data access only.
- Use DTOs for request and response objects.
- Keep exception handling centralized.

## Outcome
This structure is small, familiar, and easy to explain in an interview while still showing proper layered architecture.