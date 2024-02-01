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
   a_bpchar bpchar,
   a_text text,
   a_encrypted_text text,
	a_smallint smallint CHECK (a_smallint>=0),
	a_integer	 integer CHECK (a_integer >= 0),	
	a_bigint	 bigint	CHECK (a_bigint >= 0),
	a_decimal	 decimal,	
	a_numeric	 numeric,
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
--    a_macaddr8 macaddr8,
--    a_closed lseg,
--    a_inet inet,
    a_point point,
--    a_circle circle,
--    a_cidr cidr,
--    a_macaddr macaddr,
--    a_polygon polygon,
--    a_path varchar,
--    a_open line,
--    a_box box,
    UNIQUE(a_uuid),
   CONSTRAINT fk_code
      FOREIGN KEY(reference_code) 
	  REFERENCES connection(code)
);

