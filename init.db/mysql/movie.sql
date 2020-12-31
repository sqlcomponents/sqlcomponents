DROP TABLE IF EXISTS movie;

CREATE TABLE movie (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(80),
    directed_by VARCHAR(80),
    year_of_release NUMERIC(4),
    rating NUMERIC(2,1),
    genre VARCHAR(150),
    imdb_id VARCHAR(15),
    UNIQUE(imdb_id),
    PRIMARY KEY ( id )
);