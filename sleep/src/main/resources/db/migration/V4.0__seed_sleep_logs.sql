INSERT INTO sleep_logs (user_id, bed_date_time, wake_date_time, total_time_minutes, mood)
VALUES
-- Past 5 days (valid for average)
(1, '2025-07-23T23:00:00', '2025-07-24T07:00:00', 480, 'GOOD'),
(1, '2025-07-22T22:30:00', '2025-07-23T06:45:00', 495, 'OK'),
(1, '2025-07-21T23:15:00', '2025-07-22T07:30:00', 495, 'GOOD'),
(1, '2025-07-20T00:00:00', '2025-07-20T06:00:00', 360, 'BAD'),
(1, '2025-07-19T23:45:00', '2025-07-20T06:45:00', 420, 'OK'),

-- Outside 30-day window (should be ignored)
(1, '2025-06-15T23:00:00', '2025-06-16T07:00:00', 480, 'BAD'),

-- Another user (should be ignored unless explicitly queried)
(2, '2025-07-22T22:00:00', '2025-07-23T06:30:00', 510, 'GOOD');