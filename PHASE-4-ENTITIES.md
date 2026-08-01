# Phase 4 - Entities

## Added Domain Model
- BaseEntity
- User
- Route
- Stop
- Booking
- Feedback
- UserRole
- BookingStatus

## Design Notes
- BaseEntity stores `id`, `createdAt`, and `updatedAt` to avoid duplication.
- User supports both normal users and admins through `UserRole`.
- Route owns the core search and management data.
- Stop models ordered route stops with a many-to-one relationship to Route.
- Booking stores seat booking details with a status enum.
- Feedback stores user ratings and comments, optionally linked to a route.

## Relationship Summary
- One Route has many Stops
- One User has many Bookings
- One User has many Feedbacks
- One User can favourite many Routes
- One Route can be favourited by many Users

## Validation
- Entity package compiled cleanly in workspace validation.

## Outcome
The domain layer is now small, realistic, and ready for repository design in Phase 5.