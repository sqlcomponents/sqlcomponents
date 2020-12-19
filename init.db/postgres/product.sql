DROP TABLE IF EXISTS product;

CREATE TABLE product (
    id serial PRIMARY KEY,
    name VARCHAR(100),
    category VARCHAR(100)
)