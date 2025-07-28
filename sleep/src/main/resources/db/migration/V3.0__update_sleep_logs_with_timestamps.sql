ALTER TABLE sleep_logs
    ADD bed_date_time TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE sleep_logs
    ADD wake_date_time TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE sleep_logs
    DROP COLUMN bed_time;

ALTER TABLE sleep_logs
    DROP COLUMN date;

ALTER TABLE sleep_logs
    DROP COLUMN wake_time;
