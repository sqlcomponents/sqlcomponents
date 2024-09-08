CREATE OR REPLACE PROCEDURE create_cache(
  IN code TEXT,
  IN cache TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
  INSERT INTO cache (code, cache)
  VALUES (code, cache);
END;
$$;