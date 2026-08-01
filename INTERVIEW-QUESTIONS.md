# TEMS Interview Questions

## 1. Why did you choose SQLite for this project?
Why interviewer asks it: To check whether you made a practical technology choice.
Expected answer: SQLite keeps the project simple, portable, and easy to run on any laptop without database setup.
Possible follow-up: Why not MySQL or PostgreSQL?

## 2. How is the project structured?
Why interviewer asks it: To see if you understand layered architecture.
Expected answer: The project is divided into controller, service, repository, entity, DTO, exception, config, and util packages.
Possible follow-up: Why are controllers kept thin?

## 3. What is the role of DTOs here?
Why interviewer asks it: To verify API design knowledge.
Expected answer: DTOs separate request and response data from JPA entities and keep the API clean.
Possible follow-up: What problem do DTOs solve in real projects?

## 4. Why did you use constructor injection?
Why interviewer asks it: To check Spring best practices.
Expected answer: Constructor injection makes dependencies explicit and improves testability.
Possible follow-up: How is it better than field injection?

## 5. How do you handle exceptions?
Why interviewer asks it: To test API robustness.
Expected answer: Custom exceptions are handled centrally using a global exception handler.
Possible follow-up: What response do clients get for validation failures?

## 6. How does route search work?
Why interviewer asks it: To assess querying and paging knowledge.
Expected answer: The repository searches active routes by source and destination with pagination support.
Possible follow-up: How would you add more filters?

## 7. How do you manage seat availability?
Why interviewer asks it: To test business logic understanding.
Expected answer: Booking reduces available seats and cancellation restores them.
Possible follow-up: What happens if two users book the last seats at the same time?

## 8. Is the booking delete permanent?
Why interviewer asks it: To check your design choice.
Expected answer: No, cancellation is handled as a status change instead of physical deletion.
Possible follow-up: Why is soft delete useful?

## 9. How are stops stored?
Why interviewer asks it: To verify entity relationships.
Expected answer: Stops have a many-to-one relationship with routes and are ordered using stopOrder.
Possible follow-up: How do you prevent duplicate stop order values?

## 10. Why did you include favourite routes?
Why interviewer asks it: To see feature completeness.
Expected answer: It adds a practical user-centric feature and demonstrates a many-to-many relationship.
Possible follow-up: How would you implement this in the UI?

## 11. How did you implement login without Spring Security?
Why interviewer asks it: To test scope awareness.
Expected answer: I kept login simple for a campus project and validated credentials in the service layer.
Possible follow-up: What would you change for production?

## 12. How is feedback handled?
Why interviewer asks it: To see if the project covers user interaction.
Expected answer: Users can submit feedback with ratings and comments, optionally linked to a route.
Possible follow-up: Why make route optional?

## 13. What is the purpose of the dashboard stats?
Why interviewer asks it: To check admin functionality.
Expected answer: It gives a quick summary of counts like users, routes, bookings, feedbacks, and booking status totals.
Possible follow-up: Which metrics would you add next?

## 14. Why use pagination?
Why interviewer asks it: To assess scalability basics.
Expected answer: Pagination prevents large data responses and improves API performance.
Possible follow-up: How does Spring handle Pageable?

## 15. Why use sorting?
Why interviewer asks it: To check search UX thinking.
Expected answer: Sorting helps users and admins view data in a predictable order.
Possible follow-up: Can sorting be combined with search?

## 16. What does the Route entity represent?
Why interviewer asks it: To verify domain modeling.
Expected answer: It stores transport route details such as source, destination, fare, duration, and available seats.
Possible follow-up: Why not store everything in a single table?

## 17. What does BaseEntity do?
Why interviewer asks it: To check reuse and clean code.
Expected answer: It holds common fields like id, createdAt, and updatedAt.
Possible follow-up: How is auditing useful?

## 18. Why use enums?
Why interviewer asks it: To assess domain clarity.
Expected answer: Enums are used for stable values like user role and booking status.
Possible follow-up: What are the benefits over strings?

## 19. How did you make the code testable?
Why interviewer asks it: To check engineering quality.
Expected answer: I separated business logic into services and used Mockito-based tests.
Possible follow-up: Why are service tests more stable than controller tests?

## 20. What is the role of the repository layer?
Why interviewer asks it: To confirm Spring Data JPA understanding.
Expected answer: Repositories handle database operations and keep persistence logic out of services.
Possible follow-up: What is a derived query method?

## 21. How do you prevent duplicate users?
Why interviewer asks it: To test validation and constraints.
Expected answer: The service checks email and phone uniqueness before saving a user.
Possible follow-up: Why also keep unique constraints in the database?

## 22. How do you handle invalid booking requests?
Why interviewer asks it: To verify business rule handling.
Expected answer: The service checks seat availability, route status, and input validation before booking.
Possible follow-up: What exception would you throw for no seats?

## 23. Why did you not add JWT?
Why interviewer asks it: To check scope control.
Expected answer: The project is intentionally kept simple for placement interviews and not over-engineered.
Possible follow-up: How would you add authentication later?

## 24. How do you document the API?
Why interviewer asks it: To see project professionalism.
Expected answer: Swagger/OpenAPI provides a browsable API contract for testing and demo purposes.
Possible follow-up: What is the benefit for frontend teams?

## 25. How would you scale this project later?
Why interviewer asks it: To assess design thinking.
Expected answer: I would add auth, better reporting, a stronger database, and separate modules only if the project grows.
Possible follow-up: What would you change first for production?

## 26. Why use soft delete for routes?
Why interviewer asks it: To test data integrity thinking.
Expected answer: Soft delete preserves booking history and avoids breaking foreign key references.
Possible follow-up: Could you still show inactive routes in admin views?

## 27. What is the purpose of validation annotations?
Why interviewer asks it: To check input safety.
Expected answer: They stop invalid data before it reaches the service layer.
Possible follow-up: What happens on validation failure?

## 28. Why are tests mock-based?
Why interviewer asks it: To confirm testing strategy.
Expected answer: Mock-based tests are faster and isolate service/controller behavior.
Possible follow-up: What would an integration test cover here?

## 29. How did you keep the project interview-friendly?
Why interviewer asks it: To check scope discipline.
Expected answer: I used a small layered design, clear naming, and only the features needed for a placement project.
Possible follow-up: What did you intentionally avoid?

## 30. What is the strongest part of this project?
Why interviewer asks it: To see your self-assessment.
Expected answer: It shows clean Java, Spring Boot fundamentals, REST API design, and practical business logic without unnecessary complexity.
Possible follow-up: If given more time, what would you improve first?
