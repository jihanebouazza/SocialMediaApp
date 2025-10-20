INSERT INTO location (id, street, city, state, country, timezone)
VALUES ('11111111-1111-1111-1111-111111111111', '221B Baker Street', 'London', 'Greater London', 'United Kingdom', '+02:00');

INSERT INTO location (id, street, city, state, country, timezone)
VALUES ('22222222-2222-2222-2222-222222222222', '742 Evergreen Terrace', 'Springfield', 'Illinois', 'USA', '-05:00');

INSERT INTO users (id, title, first_name, last_name, email, date_of_birth, register_date, phone, picture, location_id)
VALUES (
           'aaaaaaa1-1111-1111-1111-aaaaaaaaaaaa', 'Mr', 'Sherlock', 'Holmes', 'sherlock@detective.com', '1985-01-06', '2025-10-20', '+44 1234 567890', 'https://example.com/sherlock.jpg', '11111111-1111-1111-1111-111111111111'
       );

INSERT INTO users (id, title, first_name, last_name, email, date_of_birth, register_date, phone, picture, location_id)
VALUES (
           'bbbbbbb2-2222-2222-2222-bbbbbbbbbbbb', 'Mrs', 'Marge', 'Simpson', 'marge@simpsonmail.com', '1979-10-01', '2025-10-19', '+1 555 678 999', 'https://example.com/marge.jpg', '22222222-2222-2222-2222-222222222222'
       );
