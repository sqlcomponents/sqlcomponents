DROP TABLE IF EXISTS movie;

CREATE TABLE movie (
    id smallserial PRIMARY KEY,
    name VARCHAR(80),
    directed_by VARCHAR(80),
    year_of_release NUMERIC(4),
    rating NUMERIC(2,1),
    genre VARCHAR(150)
);