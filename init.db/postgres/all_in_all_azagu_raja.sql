DROP TABLE IF EXISTS all_in_all_azagu_raja_reference;
CREATE TABLE all_in_all_azagu_raja_reference (
    code VARCHAR(80) PRIMARY KEY,
    name VARCHAR(80),
    UNIQUE(name)
);