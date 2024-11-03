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
)

