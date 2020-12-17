DROP TABLE IF EXISTS movie;

CREATE TABLE movie (
    code VARCHAR(80) PRIMARY KEY,
    name VARCHAR(80),
    UNIQUE(name)
);