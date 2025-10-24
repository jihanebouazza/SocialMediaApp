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


-- 🕵️ Sherlock Holmes' posts
INSERT INTO post (id, text, image, likes, link, publish_date, user_id)
VALUES (
           'p1111111-1111-1111-1111-pppppppppppp',
           'Just solved another impossible case!',
           'https://example.com/sherlock_post1.jpg',
           152,
           'https://bakerstreetblog.com/case1',
           '2025-10-20T10:30:00',
           'aaaaaaa1-1111-1111-1111-aaaaaaaaaaaa'
       );

INSERT INTO post (id, text, image, likes, link, publish_date, user_id)
VALUES (
           'p2222222-2222-2222-2222-pppppppppppp',
           'Taking a break at 221B Baker Street — tea time!',
           'https://example.com/sherlock_post2.jpg',
           87,
           'https://bakerstreetblog.com/teatime',
           '2025-10-21T14:00:00',
           'aaaaaaa1-1111-1111-1111-aaaaaaaaaaaa'
       );

-- 👩‍🦱 Marge Simpson’s posts
INSERT INTO post (id, text, image, likes, link, publish_date, user_id)
VALUES (
           'p3333333-3333-3333-3333-pppppppppppp',
           'Family dinner time with the Simpsons!',
           'https://example.com/marge_post1.jpg',
           203,
           'https://simpsonsdaily.com/family-dinner',
           '2025-10-19T19:00:00',
           'bbbbbbb2-2222-2222-2222-bbbbbbbbbbbb'
       );

INSERT INTO post (id, text, image, likes, link, publish_date, user_id)
VALUES (
           'p4444444-4444-4444-4444-pppppppppppp',
           'Springfield park is beautiful this evening 🌇',
           'https://example.com/marge_post2.jpg',
           95,
           'https://simpsonsdaily.com/park-evening',
           '2025-10-22T17:30:00',
           'bbbbbbb2-2222-2222-2222-bbbbbbbbbbbb'
       );


INSERT INTO post_tags (post_id, tags) VALUES
                                          ('p1111111-1111-1111-1111-pppppppppppp', 'mystery'),
                                          ('p1111111-1111-1111-1111-pppppppppppp', 'detective'),
                                          ('p3333333-3333-3333-3333-pppppppppppp', 'family'),
                                          ('p3333333-3333-3333-3333-pppppppppppp', 'springfield');


-- 💬 Comments on Sherlock Holmes' first post
INSERT INTO comment (id, message, publish_date, user_id, post_id)
VALUES
    ('c1111111-1111-1111-1111-cccccccccccc', 'Brilliant work as always, Sherlock!', '2025-10-20T11:00:00', 'bbbbbbb2-2222-2222-2222-bbbbbbbbbbbb', 'p1111111-1111-1111-1111-pppppppppppp'),
    ('c2222222-2222-2222-2222-cccccccccccc', 'You never fail to impress!', '2025-10-20T11:15:00', 'aaaaaaa1-1111-1111-1111-aaaaaaaaaaaa', 'p1111111-1111-1111-1111-pppppppppppp');

-- 💬 Comments on Sherlock Holmes' tea time post
INSERT INTO comment (id, message, publish_date, user_id, post_id)
VALUES
    ('c3333333-3333-3333-3333-cccccccccccc', 'I’d love to join for tea next time!', '2025-10-21T14:30:00', 'bbbbbbb2-2222-2222-2222-bbbbbbbbbbbb', 'p2222222-2222-2222-2222-pppppppppppp');

-- 💬 Comments on Marge Simpson’s family dinner post
INSERT INTO comment (id, message, publish_date, user_id, post_id)
VALUES
    ('c4444444-4444-4444-4444-cccccccccccc', 'Looks delicious! What’s for dinner?', '2025-10-19T19:30:00', 'aaaaaaa1-1111-1111-1111-aaaaaaaaaaaa', 'p3333333-3333-3333-3333-pppppppppppp'),
    ('c5555555-5555-5555-5555-cccccccccccc', 'Such a lovely family moment ❤️', '2025-10-19T19:45:00', 'bbbbbbb2-2222-2222-2222-bbbbbbbbbbbb', 'p3333333-3333-3333-3333-pppppppppppp');

-- 💬 Comments on Marge Simpson’s park post
INSERT INTO comment (id, message, publish_date, user_id, post_id)
VALUES
    ('c6666666-6666-6666-6666-cccccccccccc', 'The sunset is beautiful! 🌇', '2025-10-22T18:00:00', 'aaaaaaa1-1111-1111-1111-aaaaaaaaaaaa', 'p4444444-4444-4444-4444-pppppppppppp'),
    ('c7777777-7777-7777-7777-cccccccccccc', 'Springfield looks peaceful tonight.', '2025-10-22T18:15:00', 'bbbbbbb2-2222-2222-2222-bbbbbbbbbbbb', 'p4444444-4444-4444-4444-pppppppppppp');
