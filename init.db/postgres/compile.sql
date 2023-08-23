-- No Primary Keys, Table and Column Name has key words
DROP TABLE IF EXISTS cache;

CREATE TABLE cache (
    code VARCHAR(80),
    cache VARCHAR(80)
);

