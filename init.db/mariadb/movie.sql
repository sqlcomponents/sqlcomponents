DROP TABLE IF EXISTS movie;

CREATE TABLE movie (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(80),
    directed_by VARCHAR(80),
    PRIMARY KEY ( id )
);