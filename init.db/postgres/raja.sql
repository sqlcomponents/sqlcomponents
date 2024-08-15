DROP TABLE IF EXISTS raja;
DROP TABLE IF EXISTS connection;

CREATE TABLE connection (
    code uuid PRIMARY KEY,
    name VARCHAR(80),
    UNIQUE(name)
);
-- Table that consistas one per postgres datatype example
-- Ref: https://www.postgresql.org/docs/current/datatype.html
CREATE TABLE raja(
   id SERIAL PRIMARY KEY,
   -- Numeric Types
   -- Ref: https://www.postgresql.org/docs/current/datatype-numeric.html
    a_smallint smallint CHECK (a_smallint>=0),
    a_integer	 integer CHECK (a_integer >= 0),
    a_bigint	 bigint	CHECK (a_bigint >= 0),
    a_decimal	 decimal,
    a_numeric	 numeric,

   a_boolean boolean,
   reference_code uuid,
   a_char CHAR(1),
   a_bpchar bpchar,
   a_text text,
   a_encrypted_text text,

	a_money money,
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
     a_macaddr8 macaddr8,
    a_lseg lseg,
    a_inet inet,
    a_cidr cidr,
    a_macaddr macaddr,
    -- Ceometric Types
    -- Ref: https://www.postgresql.org/docs/current/datatype-geometric.html
    a_point point,
    a_inet inet,
    --TODO a_point point,
 a_circle circle,

    a_polygon polygon,
    a_path path,
    a_line line,
    a_box box,
    UNIQUE(a_uuid),
   CONSTRAINT fk_code
      FOREIGN KEY(reference_code) 
	  REFERENCES connection(code)
);

