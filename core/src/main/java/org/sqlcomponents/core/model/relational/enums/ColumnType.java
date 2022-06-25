package org.sqlcomponents.core.model.relational.enums;

import java.sql.JDBCType;

public enum ColumnType
{
    BIT("BIT"),
    /**
     * Identifies the generic SQL type {@code TINYINT}.
     */
    TINYINT("TINYINT"),
    /**
     * Identifies the generic SQL type {@code SMALLINT}.
     */
    SMALLINT("SMALLINT"),
    /**
     * Identifies the generic SQL type {@code INTEGER}.
     */
    INTEGER("INTEGER"),
    /**
     * Identifies the generic SQL type {@code BIGINT}.
     */
    BIGINT("BIGINT"),
    /**
     * Identifies the generic SQL type {@code FLOAT}.
     */
    FLOAT("FLOAT"),
    /**
     * Identifies the generic SQL type {@code REAL}.
     */
    REAL("REAL"),
    /**
     * Identifies the generic SQL type {@code DOUBLE}.
     */
    DOUBLE("DOUBLE"),
    /**
     * Identifies the generic SQL type {@code NUMERIC}.
     */
    NUMERIC("NUMERIC"),
    /**
     * Identifies the generic SQL type {@code DECIMAL}.
     */
    DECIMAL("DECIMAL"),
    /**
     * Identifies the generic SQL type {@code CHAR}.
     */
    CHAR("CHAR"),
    /**
     * Identifies the generic SQL type {@code VARCHAR}.
     */
    VARCHAR("VARCHAR"),
    /**
     * Identifies the generic SQL type {@code LONGVARCHAR}.
     */
    LONGVARCHAR("LONGVARCHAR"),
    /**
     * Identifies the generic SQL type {@code DATE}.
     */
    DATE("DATE"),
    /**
     * Identifies the generic SQL type {@code TIME}.
     */
    TIME("TIME"),
    /**
     * Identifies the generic SQL type {@code TIMESTAMP}.
     */
    TIMESTAMP("TIMESTAMP"),
    /**
     * Identifies the generic SQL type {@code BINARY}.
     */
    BINARY("BINARY"),
    /**
     * Identifies the generic SQL type {@code VARBINARY}.
     */
    VARBINARY("VARBINARY"),
    /**
     * Identifies the generic SQL type {@code LONGVARBINARY}.
     */
    LONGVARBINARY("LONGVARBINARY"),
    /**
     * Identifies the generic SQL value {@code NULL}.
     */
    NULL("NULL"),
    /**
     * Indicates that the SQL type
     * is database-specific and gets mapped to a Java object that can be
     * accessed via the methods getObject and setObject.
     */
    OTHER("OTHER"),
    /**
     * Indicates that the SQL type
     * is database-specific and gets mapped to a Java object that can be
     * accessed via the methods getObject and setObject.
     */
    JAVA_OBJECT("JAVA_OBJECT"),
    /**
     * Identifies the generic SQL type {@code DISTINCT}.
     */
    DISTINCT("DISTINCT"),
    /**
     * Identifies the generic SQL type {@code STRUCT}.
     */
    STRUCT("STRUCT"),
    /**
     * Identifies the generic SQL type {@code ARRAY}.
     */
    ARRAY("ARRAY"),
    /**
     * Identifies the generic SQL type {@code BLOB}.
     */
    BLOB("BLOB"),
    /**
     * Identifies the generic SQL type {@code CLOB}.
     */
    CLOB("CLOB"),
    /**
     * Identifies the generic SQL type {@code REF}.
     */
    REF("REF"),
    /**
     * Identifies the generic SQL type {@code DATALINK}.
     */
    DATALINK("DATALINK"),
    /**
     * Identifies the generic SQL type {@code BOOLEAN}.
     */
    BOOLEAN("BOOLEAN"),

    /* JDBC 4.0 Types */

    /**
     * Identifies the SQL type {@code ROWID}.
     */
    ROWID("ROWID"),
    /**
     * Identifies the generic SQL type {@code NCHAR}.
     */
    NCHAR("NCHAR"),
    /**
     * Identifies the generic SQL type {@code NVARCHAR}.
     */
    NVARCHAR("NVARCHAR"),
    /**
     * Identifies the generic SQL type {@code LONGNVARCHAR}.
     */
    LONGNVARCHAR("LONGNVARCHAR"),
    /**
     * Identifies the generic SQL type {@code NCLOB}.
     */
    NCLOB("NCLOB"),
    /**
     * Identifies the generic SQL type {@code SQLXML}.
     */
    SQLXML("SQLXML"),

    /* JDBC 4.2 Types */

    /**
     * Identifies the generic SQL type {@code REF_CURSOR}.
     */
    REF_CURSOR("REF_CURSOR"),

    /**
     * Identifies the generic SQL type {@code TIME_WITH_TIMEZONE}.
     */
    TIME_WITH_TIMEZONE("TIME_WITH_TIMEZONE"),

    /**
     * Identifies the generic SQL type {@code TIMESTAMP_WITH_TIMEZONE}.
     */
    TIMESTAMP_WITH_TIMEZONE("TIMESTAMP_WITH_TIMEZONE"),

    //Special Types
    /**
     * Identifies the generic JSON type {@code TIMESTAMP_WITH_TIMEZONE}.
     */
    JSON("JSON"),
    /**
     * Identifies the generic JSON type {@code TIMESTAMP_WITH_TIMEZONE}.
     */
    JSONB("JSONB"),
    /**
     * Identifies the generic UUID type {@code TIMESTAMP_WITH_TIMEZONE}.
     */
    UUID("UUID"),
    /**
     * Identifies the generic INTERVAL type {@code TIMESTAMP_WITH_TIMEZONE}.
     */
    INTERVAL("INTERVAL"),
    TEXT("TEXT");

    private final String value;

    ColumnType(final String aValue)
    {
	this.value = aValue;
    }

    public static ColumnType value(final String aValue)
    {
	for (ColumnType columnType : ColumnType.values())
	{
	    if (columnType.value.equals(aValue))
	    {
		return columnType;
	    }
	}
	return null;
    }

    public static ColumnType value(final JDBCType aJDBCType)
    {
	for (ColumnType columnType : ColumnType.values())
	{
	    if (columnType.value.equals(aJDBCType.getName()))
	    {
		return columnType;
	    }
	}
	return null;
    }
}
