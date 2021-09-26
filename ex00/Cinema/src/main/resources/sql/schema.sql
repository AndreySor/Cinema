CREATE TABLE IF NOT EXISTS cinema_halls
(
    hall_id serial PRIMARY KEY,
    serial_number INTEGER UNIQUE NOT NULL,
    seats_number INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS cinema_films
(
    film_id serial PRIMARY KEY,
    title VARCHAR(64) NOT NULL,
    release_year INTEGER NOT NULL,
    age_restrictions INTEGER NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS cinema_sessions
(
    id serial PRIMARY KEY,
    ticket_cost INTEGER NOT NULL,
    hall_id INTEGER REFERENCES cinema_halls (hall_id),
    film_id INTEGER REFERENCES cinema_films (film_id)
);