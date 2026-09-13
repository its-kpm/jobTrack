# jobTrack

A Spring Boot REST API for tracking job applications, recruiter details, follow-ups, notes, and application status.

## Stack

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- PostgreSQL
- Bean Validation
- Lombok
- Spring Boot Actuator
- Docker Compose
- Maven

## Features

- Create, read, update, and delete job applications
- Track application lifecycle: `SAVED`, `APPLIED`, `ONLINE_ASSESSMENT`, `INTERVIEW`, `OFFER`, `REJECTED`, `WITHDRAWN`
- Store recruiter and follow-up information
- Search by free text, company, and status
- Pagination and sorting through Spring Data
- Partial status updates
- Validation and centralized API error responses
- PostgreSQL persistence
- Health endpoint through Actuator

## API

### Create an application

```http
POST /api/applications
Content-Type: application/json
```

```json
{
  "company": "Example Corp",
  "position": "Backend Engineer",
  "location": "Chicago, IL",
  "jobUrl": "https://example.com/jobs/123",
  "recruiterName": "Alex Smith",
  "recruiterEmail": "alex@example.com",
  "status": "APPLIED",
  "appliedDate": "2026-09-12",
  "nextFollowUpDate": "2026-09-19",
  "notes": "Applied through company website"
}
```

### List/search

```http
GET /api/applications?page=0&size=20&sort=createdAt,desc
GET /api/applications?company=example
GET /api/applications?status=INTERVIEW
GET /api/applications?q=backend
```

### Get one

```http
GET /api/applications/{id}
```

### Replace/update

```http
PUT /api/applications/{id}
```

### Change status

```http
PATCH /api/applications/{id}/status?status=INTERVIEW
```

### Delete

```http
DELETE /api/applications/{id}
```

## Run PostgreSQL

```bash
docker compose up -d
```

## Run the application

With Maven installed:

```bash
mvn spring-boot:run
```

The API starts at `http://localhost:8080`.

Health check:

```http
GET /actuator/health
```

## Environment variables

| Variable | Default |
| --- | --- |
| `DB_URL` | `jdbc:postgresql://localhost:5432/jobtrack` |
| `DB_USERNAME` | `jobtrack` |
| `DB_PASSWORD` | `jobtrack` |
| `PORT` | `8080` |

## Suggested next iterations

The project intentionally starts as a clean modular monolith. Strong next features are JWT authentication, per-user applications, interview rounds, scheduled email reminders, Redis caching, Kafka events, Flyway migrations, Testcontainers integration tests, and a React frontend.
