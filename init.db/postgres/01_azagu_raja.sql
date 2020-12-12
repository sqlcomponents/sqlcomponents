DROP TABLE IF EXISTS azagu_raja;
DROP TABLE IF EXISTS azagu_raja_reference;

CREATE TABLE azagu_raja_reference (
    code VARCHAR(80) PRIMARY KEY,
    name VARCHAR(80),
    UNIQUE(name)
);

CREATE TABLE azagu_raja(
   id SERIAL PRIMARY KEY,
   a_boolean boolean,
   reference_code VARCHAR(80),
   a_char CHAR(1),
   a_text text,
	a_smallint smallint,
	a_integer	 integer,	
	a_bigint	 bigint	,
	a_decimal	 decimal,	
	a_numeric	 numeric,	
	a_real real,
	a_double  DOUBLE PRECISION, 
	a_smallserial	 smallserial,	
	a_serial	 serial,	
	a_bigserial bigserial,
   a_date date,
      a_time time,
   CONSTRAINT fk_code
      FOREIGN KEY(reference_code) 
	  REFERENCES azagu_raja_reference(code)
);
