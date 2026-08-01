# Phase 1 - Project Planning

## Project Goal
Build a clean, interview-friendly Java 21 + Spring Boot 3 application called Transport Enquiry Management System (TEMS) for route search, booking, and admin transport management.

## Scope Lock
- Keep the project small enough to explain in a 20-minute interview.
- Target about 25-35 Java classes, with an upper limit of 40.
- Prefer simple, real-world backend design over advanced enterprise patterns.
- Use SQLite for local persistence with Spring Data JPA.

## Tech Stack
- Java 21
- Spring Boot 3
- Spring Data JPA
- SQLite
- Maven
- Swagger OpenAPI
- JUnit 5
- Lombok

## Core Modules
- User management: register, login, profile-friendly booking flow
- Route management: search, view, add, update, delete
- Booking management: book seat, cancel booking, booking history
- Feedback management: submit and view feedback
- Admin dashboard: bookings count, route count, feedback count

## Planned Database Entities
- User
- Route
- Stop
- Booking
- Feedback

## Relationship Summary
- One Route has many Stops
- One User has many Bookings
- One User has many Feedbacks

## API Style
- RESTful endpoints with clean nouns and standard HTTP verbs
- Use DTOs for request and response payloads
- Use pagination, sorting, and simple search where useful

## Java Concepts to Demonstrate
- OOP, inheritance, polymorphism, abstraction, encapsulation
- Collections, streams, lambdas, Optional
- Comparable and Comparator where relevant
- Exception handling and custom exceptions
- Enums for fixed states like booking status or user role

## Excluded Items
- JWT, OAuth, Spring Security
- Microservices, Redis, Kafka, RabbitMQ
- Docker, Kubernetes, WebSockets
- Cloud deployment, email services, payment gateways

## Phase Plan
1. Project planning
2. Folder structure
3. Database design
4. Entities
5. Repositories
6. Services
7. Controllers
8. Configuration
9. Testing
10. README and resume/interview content

## Success Criteria
- Easy to explain and extend
- Looks polished on GitHub
- Demonstrates solid Java and Spring Boot fundamentals
- Stays realistic for a campus placement project