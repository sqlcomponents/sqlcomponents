DROP TABLE IF EXISTS raja;
DROP TABLE IF EXISTS connection;

CREATE TABLE connection (
    code uuid PRIMARY KEY,
    name VARCHAR(80),
    UNIQUE(name)
);
-- Table that consists one per postgres datatype example
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
	a_real real,
	a_double  DOUBLE PRECISION,
	a_smallserial	 smallserial,
	a_serial	 serial,
	a_bigserial bigserial,

   -- Monetary Types
   -- Ref: https://www.postgresql.org/docs/current/datatype-money.html
   a_money money,


   reference_code uuid,

   -- Character Types
   -- Ref: https://www.postgresql.org/docs/current/datatype-character.html
   a_char CHAR(1),
   a_bpchar bpchar,
   a_text text,
   a_encrypted_text text,

   -- Binary Data Types
   -- Ref:https://www.postgresql.org/docs/current/datatype-binary.html
	a_blob bytea,

    -- Date/Time Types
    -- Ref: https://www.postgresql.org/docs/current/datatype-datetime.html
   	-- todo: timestamp
    a_date date,
    a_time time,
    a_interval interval,

    -- Boolean Type
    -- Ref: https://www.postgresql.org/docs/current/datatype-boolean.html
    a_boolean boolean,

    -- Enumerated Types
    -- Ref: https://www.postgresql.org/docs/current/datatype-enum.html
    -- todo: ENUM

    -- Ceometric Types
    -- Ref: https://www.postgresql.org/docs/current/datatype-geometric.html
    a_point point,
    a_inet inet,
    a_lseg lseg,
    a_circle circle,
    a_polygon polygon,
    a_path path,
    a_line line,
    a_box box,

    -- Network Address Types
    -- Ref: https://www.postgresql.org/docs/current/datatype-net-types.html
    a_macaddr8 macaddr8,
    a_inet inet,
    a_cidr cidr,
    a_macaddr macaddr,


    -- Bit String Types
    -- Ref: https://www.postgresql.org/docs/current/datatype-bit.html
    -- todo: BIT

    -- Text Search Types
    -- Ref: https://www.postgresql.org/docs/current/datatype-textsearch.html
    -- todo: tsvector
    -- todo: tsquery

    -- UUID Type
    -- Ref: https://www.postgresql.org/docs/current/datatype-uuid.html
   	a_uuid uuid,

    -- XML Type
    -- Ref: https://www.postgresql.org/docs/current/datatype-xml.html
    -- this todo need to check once.
    -- todo: XMLPARSE
    -- todo: XMLSERIALIZE
    -- todo: xmloption
	a_xml xml,

    -- JSON Types
    -- https://www.postgresql.org/docs/current/datatype-json.html
   	json json,
   	a_jsonb jsonb,
    UNIQUE(a_uuid),
   CONSTRAINT fk_code
      FOREIGN KEY(reference_code) 
	  REFERENCES connection(code)
);

