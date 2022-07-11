package org.sqlcomponents.compiler.java.mapper;

import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.model.relational.Column;

import java.nio.ByteBuffer;
import java.time.*;
import java.util.UUID;

/**
 * Java Mapper which is responsible for converting Database Types
 * into appropriate Java Data type.
 */
public final class DB2JavaDataTypeMapper extends Mapper
{
    private static final int MAX_DIGITS_FOR_BYTE = String.valueOf(Byte.MAX_VALUE).length() - 1;
    private static final int MAX_DIGITS_FOR_SHORT = String.valueOf(Short.MAX_VALUE).length() - 1;
    private static final int MAX_DIGITS_FOR_INTEGER = String.valueOf(Integer.MAX_VALUE).length() - 1;
    private static final int MAX_DIGITS_FOR_LONG = String.valueOf(Long.MAX_VALUE).length() - 1;
    private static final int MAX_DIGITS_FOR_FLOAT = String.valueOf(Float.MAX_VALUE).indexOf('.');
    private static final int MAX_DIGITS_FOR_DOUBLE = String.valueOf(Double.MAX_VALUE).indexOf('.');

    public DB2JavaDataTypeMapper(final Application bApplication)
    {
	super(bApplication);
    }

    @Override
    public String getDataType(final Column aColumn)
    {
	switch (aColumn.getColumnType())
	{
	    case JSON:
	    case JSONB:
	    {
		return "org.json.JSONObject";
	    }
	    default:
	    {
		return getDataTypeClass(aColumn).getName();
	    }
	}
    }

    private Class getDataTypeClass(final Column aColumn)
    {
	switch (aColumn.getColumnType())
	{
	    case TINYINT:
	    case BIT:
	    case SMALLINT:
	    {
		return aColumn.getSize() == 1 ? Boolean.class : Short.class;
	    }
	    case BIGINT:
	    {
		return Long.class;
	    }
	    case REAL:
	    case FLOAT:
	    {
		return Float.class;
	    }
	    case DOUBLE:
	    case DECIMAL:
	    {
		return chooseDecimalType(aColumn);
	    }
	    case INTEGER:
	    {
		return chooseIntegerType(aColumn);
	    }
	    case NUMERIC:
	    {
		return chooseNumberType(aColumn);
	    }
	    case VARCHAR:
	    case NVARCHAR:
	    case LONGVARCHAR:
	    case CHAR:
	    {
		return aColumn.getSize() == 1 ? Character.class : String.class;
	    }
	    case TEXT:
	    case SQLXML:
	    {
		return String.class;
	    }
	    case BOOLEAN:
	    {
		return Boolean.class;
	    }
	    case TIME:
	    {
		return LocalTime.class;
	    }
	    case DATE:
	    {
		return LocalDate.class;
	    }
	    case TIMESTAMP:
	    {
		return LocalDateTime.class;
	    }
	    case TIMESTAMP_WITH_TIMEZONE:
	    {
		return OffsetDateTime.class;
	    }
	    case UUID:
	    {
		return UUID.class;
	    }
	    case INTERVAL:
	    {
		return Duration.class;
	    }
	    case BLOB:
	    case LONGVARBINARY:
	    case BINARY:
	    {
		return ByteBuffer.class;
	    }
	    case OTHER:
	    {
		return getDataTypeClassForSpecialType(aColumn);
	    }
	    default:
	    {
		throwRuntimeException(aColumn);
	    }
	}
	return null;
    }

    private Class getDataTypeClassForSpecialType(final Column aColumn)
    {
	if (aColumn.getTypeName().equalsIgnoreCase("interval"))
	{
	    return Duration.class;
	}
	else
	{
	    throwRuntimeException(aColumn);
	}
	return null;
    }

    private void throwRuntimeException(final Column aColumn)
    {
	throw new RuntimeException("Datatype not found for column "
				   + aColumn.getColumnName()
				   + " of table " + aColumn.getTable().getTableName()
				   + " of type name " + aColumn.getTypeName()
				   + " of column type " + aColumn.getColumnType());
    }

    private Class<? extends Number> chooseNumberType(final Column column)
    {
	return column.getDecimalDigits() == 0
	       ? chooseIntegerType(column) : chooseDecimalType(column);
    }

    private Class<? extends Number> chooseIntegerType(final Column aColumn)
    {
	Class<? extends Number> integerType;

	if (aColumn.getSize() <= MAX_DIGITS_FOR_BYTE)
	{
	    integerType = Byte.class;
	}
	else if (aColumn.getSize() <= MAX_DIGITS_FOR_SHORT)
	{
	    integerType = Short.class;
	}
	else if (aColumn.getSize() <= MAX_DIGITS_FOR_INTEGER)
	{
	    integerType = Integer.class;
	}
	else
	{
	    integerType = Long.class;
	}
	return integerType;
    }

    private Class<? extends Number> chooseDecimalType(final Column aColumn)
    {
	Class<? extends Number> lDecimalType;
	if (aColumn.getSize() <= MAX_DIGITS_FOR_FLOAT)
	{
	    lDecimalType = Float.class;
	}
	else
	{
	    lDecimalType = Double.class;
	}
	return lDecimalType;
    }
}
