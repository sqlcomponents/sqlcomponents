-- No Primary Keys, Table and Column Name has key words
DROP TABLE IF EXISTS cache;

CREATE TABLE cache (
    code VARCHAR(80),
    cache VARCHAR(80),
    created_by VARCHAR(80),
    created_at TIMESTAMP,
    modified_by VARCHAR(80),
    modified_at TIMESTAMP
);

