DROP TABLE IF EXISTS azagu_raja;
DROP TABLE IF EXISTS azagu_raja_reference;

CREATE TABLE azagu_raja_reference (
    code VARCHAR(80) PRIMARY KEY,
    name VARCHAR(80),
    UNIQUE(name)
);
CREATE TABLE azagu_raja (
    code VARCHAR(80) PRIMARY KEY,
    name VARCHAR(80),
    UNIQUE(name)
);