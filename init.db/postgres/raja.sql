DROP TABLE IF EXISTS raja;
DROP TABLE IF EXISTS connection;

CREATE TABLE connection (
    code uuid PRIMARY KEY,
    name VARCHAR(80),
    UNIQUE(name)
);

CREATE TABLE raja(
   id SERIAL PRIMARY KEY,
   a_boolean boolean,
   reference_code uuid,
   a_char CHAR(1),
   a_text text,
   a_encrypted_text text,
	a_smallint smallint CHECK (a_smallint>=0),
	a_integer	 integer CHECK (a_integer >= 0),	
	a_bigint	 bigint	CHECK (a_bigint >= 0),
	a_decimal	 decimal,	
	a_numeric	 numeric,	
	a_real real,
	a_double  DOUBLE PRECISION, 
	a_smallserial	 smallserial,	
	a_serial	 serial,	
	a_bigserial bigserial,
   	a_date date,
   	a_blob bytea,
   	json json,
   	a_jsonb jsonb,
   	a_uuid uuid,
   	a_xml xml,
    a_time time,
    a_interval interval,
    UNIQUE(a_uuid),
   CONSTRAINT fk_code
      FOREIGN KEY(reference_code) 
	  REFERENCES connection(code)
);
