CREATE TYPE branch_type AS ENUM ('CSE', 'ECE', 'MECH');

CREATE TABLE students (
   id SERIAL PRIMARY KEY,
   name VARCHAR(30) NOT NULL,
   branch branch_type,
   fees INT NOT NULL
);