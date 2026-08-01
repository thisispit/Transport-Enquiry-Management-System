# Phase 3 - Database Design

## Database Choice
- SQLite file: `tems.db`
- Simple local database suitable for a campus project and easy demo setup

## Design Goals
- Keep the schema minimal and interview-friendly
- Use clear primary keys and foreign keys
- Store only the data needed for route search, booking, feedback, and admin management
- Avoid unnecessary normalization that would make the project harder to explain

## Tables

### users
Stores both normal users and admins using a role column.

| Column | Type | Notes |
| --- | --- | --- |
| id | INTEGER | Primary key |
| full_name | TEXT | Not null |
| email | TEXT | Unique, not null |
| phone_number | TEXT | Unique, optional |
| password | TEXT | Not null |
| role | TEXT | USER or ADMIN |
| active | INTEGER | 1 for active, 0 for inactive |
| created_at | DATETIME | Not null |
| updated_at | DATETIME | Not null |

### routes
Stores the main route details.

| Column | Type | Notes |
| --- | --- | --- |
| id | INTEGER | Primary key |
| route_name | TEXT | Not null |
| source | TEXT | Not null |
| destination | TEXT | Not null |
| distance_km | REAL | Not null |
| travel_duration_minutes | INTEGER | Not null |
| fare | REAL | Not null |
| transport_type | TEXT | Bus, mini bus, etc. |
| available_seats | INTEGER | Not null |
| active | INTEGER | 1 for active, 0 for inactive |
| created_at | DATETIME | Not null |
| updated_at | DATETIME | Not null |

### stops
Stores route stops in order.

| Column | Type | Notes |
| --- | --- | --- |
| id | INTEGER | Primary key |
| route_id | INTEGER | Foreign key to routes.id |
| stop_name | TEXT | Not null |
| stop_order | INTEGER | Not null |
| arrival_time | TEXT | Optional |
| departure_time | TEXT | Optional |

### bookings
Stores seat booking details.

| Column | Type | Notes |
| --- | --- | --- |
| id | INTEGER | Primary key |
| user_id | INTEGER | Foreign key to users.id |
| route_id | INTEGER | Foreign key to routes.id |
| booking_date | DATETIME | Not null |
| travel_date | DATE | Not null |
| seat_count | INTEGER | Not null |
| total_amount | REAL | Not null |
| status | TEXT | CONFIRMED or CANCELLED |
| booking_reference | TEXT | Unique reference number |
| created_at | DATETIME | Not null |
| updated_at | DATETIME | Not null |

### feedbacks
Stores user feedback for routes or the service.

| Column | Type | Notes |
| --- | --- | --- |
| id | INTEGER | Primary key |
| user_id | INTEGER | Foreign key to users.id |
| route_id | INTEGER | Foreign key to routes.id, optional |
| rating | INTEGER | 1 to 5 |
| comments | TEXT | Not null |
| created_at | DATETIME | Not null |

## Favourite Routes
- Use a join table named `user_favourite_routes`
- Columns: `user_id`, `route_id`
- This supports the favourite routes feature without creating another entity class

## Relationship Summary
- One user has many bookings
- One user has many feedbacks
- One route has many stops
- One route has many bookings
- One route can appear in many users' favourites

## Suggested Constraints
- Unique email in users
- Unique booking reference in bookings
- Unique `(route_id, stop_order)` in stops
- Foreign keys on all related tables

## Suggested Indexes
- `routes(source, destination)` for search
- `bookings(user_id)` for booking history
- `stops(route_id)` for loading route stops
- `feedbacks(route_id)` for viewing route feedback

## Interview Explanation
This schema is intentionally simple: it demonstrates foreign keys, relationships, constraints, and search-friendly indexing while staying easy to build and maintain in a student project.