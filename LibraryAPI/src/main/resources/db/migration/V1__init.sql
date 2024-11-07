create table  users(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    username VARCHAR(50),
    password VARCHAR(255)

);

create table user_role(
    user_id INTEGER,
    role VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE
);

create table book(
    id SERIAL PRIMARY KEY,
    isbn VARCHAR(30),
    name VARCHAR(50),
    genre VARCHAR(50),
    description TEXT,
    author VARCHAR(50)
);

create table book_status(
    id SERIAL PRIMARY KEY,
    book_id INTEGER,
    borrowed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP + '7 days',
    FOREIGN KEY (book_id) REFERENCES book(id) ON UPDATE CASCADE ON DELETE CASCADE
);
INSERT INTO users (username, password, name) VALUES
('Admin','$2a$10$Rjjt2alpVfFARVQ1kQKQkeAZfttiUkvwYns7GA71GnZNfAS3ofZF6','Владислав'),
('User','$2a$10$Rjjt2alpVfFARVQ1kQKQkeAZfttiUkvwYns7GA71GnZNfAS3ofZF6','Елизавета');

INSERT INTO user_role VALUES
            (1,'ROLE_ADMIN'),
            (1,'ROLE_USER'),
            (2,'ROLE_USER');

INSERT INTO book (isbn, name, genre, description, author)
VALUES
    ('978-3-16-148410-0', 'The Great Gatsby', 'Classic', 'A novel set in the Roaring Twenties, narrating the story of Jay Gatsby and his unrequited love for Daisy Buchanan.', 'F. Scott Fitzgerald'),
    ('978-0-14-017739-8', 'Of Mice and Men', 'Classic', 'The story of two displaced migrant ranch workers during the Great Depression in California.', 'John Steinbeck'),
    ('978-0-7432-7356-5', 'Angels & Demons', 'Thriller', 'A symbologist uncovers a deadly conspiracy between a secret society and the Catholic Church.', 'Dan Brown'),
    ('978-1-250-11684-6', 'Educated', 'Memoir', 'A memoir recounting a woman''s journey from a survivalist family to Cambridge University.', 'Tara Westover');

INSERT INTO book_status (book_id, borrowed_at, due_date)
VALUES
    (1, '2023-11-01 10:00:00', '2023-11-02 10:00:00'),
    (2, '2023-10-30 09:30:00', '2023-11-01 09:30:00'),
    (3, '2023-10-29 14:15:00', '2023-10-31 14:15:00'),
    (4, '2024-10-28 08:45:00', '2024-12-30 08:45:00');
