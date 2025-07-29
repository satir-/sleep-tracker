# Sleep Tracker
![Coverage](https://img.shields.io/badge/Coverage-88%25-brightgreen)

Kotlin + Spring + PostgreSQL implementation of a simple Sleep Tracker API:
* create the sleep log for the last night;
* fetch information about the last night's sleep;
* get the last 30-day averages

### Requirements
- JDK 11+ (SpringBoot 2.7)
- Docker & Docker Compose
- Gradle 8.4
- PostgreSQL (via Docker)

### IntelliJ Setup (Optional)
If IntelliJ doesn't detect the Spring Boot context:
1. Go to **File > Project Structure > Modules**.
2. Click **+ Add** → **Import Module**.
3. Select the root directory containing `build.gradle`.

IDE should autodetect **build.gradle** file with all project necessary context and dependencies.

### Usage:

General start up

```bash
$ docker-compose up
```

To build and run

```bash
$ docker-compose up --build
```

### API:

#### `POST /sleep-log`
Create a new sleep log.

**Request Body:**
```json
{
  "bedDateTime": "2025-07-28T23:00:00",
  "wakeDateTime": "2025-07-29T07:00:00",
  "mood": "GOOD"
}
```

#### `GET /sleep-log/last`

Returns the most recent sleep log.

#### `GET /sleep-log/average`

Returns average sleep stats for the last 30 days.

### Test:
#### Unit tests
```bash
$ ./gradlew test
```
To check coverage
```bash
$ ./gradlew check
```

A full report is available at `build/reports/jacoco/test/html/index.html`

#### Postman
Import the Postman collection and environment (from `sleep/src/test/postman`) to test the API routes live.

The project includes SQL migrations with test data that apply automatically on startup.