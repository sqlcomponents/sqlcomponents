package org.sqlcomponents.compiler.java.mapper;

import org.jetbrains.annotations.NotNull;
import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.model.relational.Column;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Java Mapper which is responsible for converting Database Types into
 * appropriate Java Data type.
 */
public final class JavaMapper extends Mapper {
    /**
     * The constant MAX_DIGITS_FOR_BYTE.
     */
    private static final int MAX_DIGITS_FOR_BYTE =
            String.valueOf(Byte.MAX_VALUE).length() - 1;
    /**
     * The constant MAX_DIGITS_FOR_SHORT.
     */
    private static final int MAX_DIGITS_FOR_SHORT =
            String.valueOf(Short.MAX_VALUE).length() - 1;
    /**
     * The constant MAX_DIGITS_FOR_INTEGER.
     */
    private static final int MAX_DIGITS_FOR_INTEGER =
            String.valueOf(Integer.MAX_VALUE).length() - 1;
    /**
     * The constant MAX_DIGITS_FOR_LONG.
     */
    private static final int MAX_DIGITS_FOR_LONG =
            String.valueOf(Long.MAX_VALUE).length() - 1;
    /**
     * The constant MAX_DIGITS_FOR_FLOAT.
     */
    private static final int MAX_DIGITS_FOR_FLOAT =
            String.valueOf(Float.MAX_VALUE).indexOf('.');
    /**
     * The constant MAX_DIGITS_FOR_DOUBLE.
     */
    private static final int MAX_DIGITS_FOR_DOUBLE =
            String.valueOf(Double.MAX_VALUE).indexOf('.');
    /**
     * The constant INTERVAL_STR.
     */
    public static final String INTERVAL_STR = "interval";

    /**
     * Instantiates a new Java mapper.
     *
     * @param bApplication the b application
     */
    public JavaMapper(final Application bApplication) {
        super(bApplication);
    }

    @Override
    public String getDataType(final Column aColumn) {
        switch (aColumn.getColumnType()) {
            case POLYGON:
                return "org.locationtech.jts.geom.Polygon";
            case PATH:
                return "java.lang.String";
            case MACADDR8:
            case INET:
                return "java.net.InetAddress";
            case LSEG:
                return "org.locationtech.jts.geom.LineSegment";
            case CIRCLE:
                return "org.locationtech.spatial4j.shape.Circle";
            case JSON:
            case JSONB:
                return "com.fasterxml.jackson.databind.JsonNode";
            default:
                return Objects.requireNonNull(getDataTypeClass(aColumn))
                        .getName();
        }
    }

    /**
     * Gets data type class.
     *
     * @param aColumn the a column
     * @return the data type class
     */
    private Class getDataTypeClass(final Column aColumn) {
        switch (aColumn.getColumnType()) {
            case TINYINT:
            case BIT:
            case SMALLINT:
                return aColumn.getSize() == 1 ? Boolean.class : Short.class;
            case BIGINT:
                return Long.class;
            case REAL:
            case FLOAT:
                return Float.class;
            case DOUBLE:
            case DECIMAL:
                return chooseDecimalType(aColumn);
            case INTEGER:
                return chooseIntegerType(aColumn);
            case NUMERIC:
                return chooseNumberType(aColumn);
            case VARCHAR:
            case NVARCHAR:
            case LONGVARCHAR:
            case CHAR:
                return aColumn.getSize() == 1 ? Character.class : String.class;
            case TEXT:
            case SQLXML:
                return String.class;
            case BOOLEAN:
                return Boolean.class;
            case TIME:
                return LocalTime.class;
            case DATE:
                return LocalDate.class;
            case TIMESTAMP:
                return LocalDateTime.class;
            case TIMESTAMP_WITH_TIMEZONE:
                return OffsetDateTime.class;
            case UUID:
                return UUID.class;
            case INTERVAL:
                return Duration.class;
            case BLOB:
            case LONGVARBINARY:
            case BINARY:
                return ByteBuffer.class;
            case OTHER:
                return getDataTypeClassForSpecialType(aColumn);
            default:
                throw new RuntimeException(createMessage(aColumn));
        }
    }

    /**
     * Gets data type class for special type.
     *
     * @param aColumn the a column
     * @return the data type class for special type
     */
    private Class getDataTypeClassForSpecialType(final Column aColumn) {
        if (aColumn.getTypeName().equalsIgnoreCase(INTERVAL_STR)) {
            return Duration.class;
        }
        throw new RuntimeException(createMessage(aColumn));
    }

    /**
     * Create message string.
     *
     * @param aColumn the a column
     * @return the string
     */
    @NotNull
    private String createMessage(final Column aColumn) {
        return "Datatype not found for column " + aColumn.getColumnName()
                + " of table "
                + aColumn.getTable().getTableName() + " of type name "
                + aColumn.getTypeName() + " of column type "
                + aColumn.getColumnType();
    }

    /**
     * Choose number type class.
     *
     * @param aColumn the a column
     * @return the class
     */
    private Class<? extends Number> chooseNumberType(final Column aColumn) {
        return (aColumn.getDecimalDigits() == 0) ? chooseIntegerType(aColumn)
                : chooseDecimalType(aColumn);
    }

    /**
     * Choose integer type class.
     *
     * @param aColumn the a column
     * @return the class
     */
    private Class<? extends Number> chooseIntegerType(final Column aColumn) {
        final Class<? extends Number> lIntegerType;

        if (aColumn.getSize() <= MAX_DIGITS_FOR_BYTE) {
            lIntegerType = Byte.class;
        } else if (aColumn.getSize() <= MAX_DIGITS_FOR_SHORT) {
            lIntegerType = Short.class;
        } else if (aColumn.getSize() <= MAX_DIGITS_FOR_INTEGER) {
            lIntegerType = Integer.class;
        } else {
            lIntegerType = Long.class;
        }

        return lIntegerType;
    }

    /**
     * Choose decimal type class.
     *
     * @param aColumn the a column
     * @return the class
     */
    private Class<? extends Number> chooseDecimalType(final Column aColumn) {
        final Class<? extends Number> lDecimalType;
        if (aColumn.getSize() <= MAX_DIGITS_FOR_FLOAT) {
            lDecimalType = Float.class;
        } else {
            if (aColumn.getTypeName().equalsIgnoreCase("money")) {
                lDecimalType = BigDecimal.class;
            } else {
                lDecimalType = Double.class;
            }
        }

        return lDecimalType;
    }
}
