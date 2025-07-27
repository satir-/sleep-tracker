# Sleep Tracker
Kotlin + Spring + PostgreSQL - implementation of the test API for the sleep record writing/reading functionality:
* create the sleep log for the last night;
* fetch information about the last night's sleep;
* get the last 30-day averages

### Preconditions:

One may have to specify SpringBoot context manually.

E.g. in Intellij IDEA, open **Project Structure** | **Project Settings** | **Modules** | **Add** -> **Import module**.

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
#### sleep-log

* Add new sleep record
```bash
$ POST {BODY} /sleep-log
```
Where __{BODY}__ - `application/JSON` type.
* Get last logged sleep record
```bash
$ GET /sleep-log/last
```
* Get an averaged sleep record value (within 30-days)
```bash
$ GET /sleep-log/average
```