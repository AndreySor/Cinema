CREATE TABLE IF NOT EXISTS cinema_halls
(
    hall_id bigserial PRIMARY KEY,
    serial_number INTEGER UNIQUE NOT NULL,
    seats_number INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS cinema_films
(
    film_id bigserial PRIMARY KEY,
    title VARCHAR(64) UNIQUE NOT NULL,
    release_year INTEGER NOT NULL,
    age_restrictions INTEGER NOT NULL,
    description TEXT,
    poster TEXT
);

CREATE TABLE IF NOT EXISTS cinema_sessions
(
    id bigserial PRIMARY KEY,
    ticket_cost INTEGER NOT NULL,
    session_date TIMESTAMP WITHOUT TIME ZONE,
    hall_id INTEGER REFERENCES cinema_halls (hall_id),
    film_id INTEGER REFERENCES cinema_films (film_id)
);

CREATE TABLE IF NOT EXISTS users
(
    id    BIGSERIAL PRIMARY KEY,
    login VARCHAR(64) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS cinema_authentications
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGSERIAL REFERENCES users (id),
    authentication_date TIMESTAMP WITHOUT TIME ZONE,
    ip_address VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS cinema_messages
(
    id BIGSERIAL PRIMARY KEY,
    message TEXT NOT NULL,
    author_id BIGSERIAL REFERENCES users (id),
    message_date TIMESTAMP WITHOUT TIME ZONE,
    film_id BIGSERIAL REFERENCES cinema_films (film_id)
);
