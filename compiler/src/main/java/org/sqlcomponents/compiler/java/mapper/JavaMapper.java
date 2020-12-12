package org.sqlcomponents.compiler.java.mapper;

import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.model.relational.Column;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
        return getDataTypeClass(column).getName();
    }

    private Class getDataTypeClass(final Column column) {
        switch (column.getJdbcType()) {
            case SMALLINT:
                return  Short.class;
            case BIGINT:
                return Long.class;
            case REAL:
                return Float.class;
            case DOUBLE:
                return Double.class;
            case INTEGER:
                return chooseIntegerType(column);
            case NUMERIC:
                return chooseNumberType(column);
            case DECIMAL:
                return chooseDecimalType(column);
            case VARCHAR:
            case CHAR:
                return column.getSize() == 1 ? Character.class : String.class;
            case BIT:
            case BOOLEAN:
                return Boolean.class;
            case TIME:
                return LocalTime.class;
            case DATE:
                return LocalDate.class;
            case TIMESTAMP:
                return LocalDateTime.class;
            default:
                throw new RuntimeException("Datatype not found for column "
                        + column.getColumnName() + " of jdbc type "
                        + column.getJdbcType());
        }

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
