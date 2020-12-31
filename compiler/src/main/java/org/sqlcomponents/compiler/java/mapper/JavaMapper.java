package org.sqlcomponents.compiler.java.mapper;

import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.model.relational.Column;

import java.time.*;
import java.util.UUID;

/**
 * Java Mapper which is responsible for converting Database Types
 * into appropriaye Java Data type.
 */
public final class JavaMapper extends Mapper {
    /**
     * varaible declaration.
     */
    private static final int MAX_DIGITS_FOR_FLOAT
            = String.valueOf(Float.MAX_VALUE).indexOf('.');
    /**
     * varaible declaration.
     */
    private static final int MAX_DIGITS_FOR_DOUBLE
            = String.valueOf(Double.MAX_VALUE).indexOf('.');
    /**
     * varaible declaration.
     */
    private static final int MAX_DIGITS_FOR_BYTE
            = String.valueOf(Byte.MAX_VALUE).length() - 1;
    /**
     * varaible declaration.
     */
    private static final int MAX_DIGITS_FOR_SHORT
            = String.valueOf(Short.MAX_VALUE).length() - 1;
    /**
     * varaible declaration.
     */
    private static final int MAX_DIGITS_FOR_INTEGER
            = String.valueOf(Integer.MAX_VALUE).length() - 1;
    /**
     * varaible declaration.
     */
    private static final int MAX_DIGITS_FOR_LONG
            = String.valueOf(Long.MAX_VALUE).length() - 1;
    @Override
    public String getDataType(final Column column) {
        switch (column.getColumnType()) {
            case JSON:
            case JSONB:
                return "org.json.JSONObject";
            default:
                return getDataTypeClass(column).getName();
        }
    }

    private Class getDataTypeClass(final Column column) {
        switch (column.getColumnType()) {
            case TINYINT:
                return column.getSize() == 1 ? Boolean.class : Short.class;
            case BIT:
            case SMALLINT:
                return  Short.class;
            case BIGINT:
                return Long.class;
            case REAL:
                return Float.class;
            case DOUBLE:
            case DECIMAL:
                return chooseDecimalType(column);
            case INTEGER:
                return chooseIntegerType(column);
            case NUMERIC:
                return chooseNumberType(column);
            case VARCHAR:
            case NVARCHAR:
            case LONGVARCHAR:
            case CHAR:
                return column.getSize() == 1 ? Character.class : String.class;
            case TEXT:
            case SQLXML:
                return String.class;
            case BOOLEAN:
                return Boolean.class;
            case TIME:
                return LocalTime.class;
            case DATE:
                return LocalDate.class;
            case FLOAT:
                return Float.class;
            case TIMESTAMP:
                return LocalDateTime.class;
            case TIMESTAMP_WITH_TIMEZONE:
                return OffsetDateTime.class;
            case UUID:
                return UUID.class;
            case INTERVAL:
                return Duration.class;
            case OTHER:
                return getDataTypeClassForSpecialType(column);
            default:
                throwRuntimeException(column);
        }
        return null;
    }

    private Class getDataTypeClassForSpecialType(final Column column) {
        if(column.getTypeName().equalsIgnoreCase("interval")) {
            return Duration.class;
        }else {
            throwRuntimeException(column);
        }
        return null;
    }

    private void throwRuntimeException(final Column column) {
        throw new RuntimeException("Datatype not found for column "
                + column.getColumnName()
                + " of table " + column.getTable().getTableName()
                + " of type name " + column.getTypeName()
                + " of column type " + column.getColumnType());
    }

    private Class<? extends Number> chooseNumberType(final Column column) {
        return column.getDecimalDigits() == 0
                ? chooseIntegerType(column) : chooseDecimalType(column);
    }

    private Class<? extends Number> chooseIntegerType(final Column column) {
        Class<? extends Number> integerType;
        if (column.getSize() <= MAX_DIGITS_FOR_BYTE) {
            integerType = Byte.class;
        } else if (column.getSize() <= MAX_DIGITS_FOR_SHORT) {
            integerType = Short.class;
        } else if (column.getSize() <= MAX_DIGITS_FOR_INTEGER) {
            integerType = Integer.class;
        } else {
            integerType = Long.class;
        }
        return integerType;
    }

    private Class<? extends Number> chooseDecimalType(final Column column) {
        Class<? extends Number> decimalType;
        if (column.getSize() <= MAX_DIGITS_FOR_FLOAT) {
            decimalType = Float.class;
        } else {
            decimalType = Double.class;
        }
        return decimalType;
    }

}
