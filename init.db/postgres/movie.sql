DROP TABLE IF EXISTS movie;

CREATE TABLE movie (
    id smallserial PRIMARY KEY,
    title VARCHAR(80) NOT NULL,
    directed_by VARCHAR(80)
);

CREATE VIEW movie_view AS
    SELECT id, title, directed_by
        FROM movie;

CREATE MATERIALIZED VIEW materialized_movie_view AS
    SELECT id, title, directed_by
        FROM movie;