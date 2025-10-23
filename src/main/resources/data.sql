INSERT INTO Location (id, street, city, state, country, timezone) VALUES
                                                                      (1, '123 Main St', 'Paris', 'Ile-de-France', 'France', '+1:00'),
                                                                      (2, '45 Elm St', 'New York', 'NY', 'USA', '-5:00'),
                                                                      (3, '78 Sakura St', 'Tokyo', 'Tokyo', 'Japan', '+9:00');
INSERT INTO User (id, title, firstName, lastName, email, dateOfBirth, phone, picture, location_id) VALUES
                                                                                                       (1, 'mr', 'Alice', 'Smith', 'alice@example.com', '1990-05-12', '+1-202-555-0171', 'https://example.com/pic1.jpg', 1),
                                                                                                       (2, 'miss', 'Bob', 'Brown', 'bob@example.com', '1985-10-20', '+1-202-555-0198', 'https://example.com/pic2.jpg', 2),
                                                                                                       (3, 'dr', 'Charlie', 'Davis', 'charlie@example.com', '1978-03-03', '+81-90-1234-5678', 'https://example.com/pic3.jpg', 3);
INSERT INTO Post (id, text, image, likes, link, owner_id) VALUES
                                                              (1, 'Hello world!', 'https://example.com/image1.jpg', 5, 'https://example.com/post1', 1),
                                                              (2, 'Vacation time', 'https://example.com/image2.jpg', 10, 'https://example.com/post2', 2),
                                                              (3, 'Enjoying sushi', 'https://example.com/image3.jpg', 7, 'https://example.com/post3', 3),
                                                              (4, 'Learning Spring Boot', NULL, 3, 'https://example.com/post4', 1);