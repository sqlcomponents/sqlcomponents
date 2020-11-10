package org.sqlcomponents.compiler.java.mapper;

import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.model.relational.Column;

public class JavaMapper extends Mapper {

    // Oracle Data Types
    private static final String VARCHAR2 = "VARCHAR2";
    private static final String NVARCHAR2 = "NVARCHAR2";
    private static final String VARCHAR = "VARCHAR";
    private static final String CHAR = "CHAR";
    private static final String NCHAR = "NCHAR";
    private static final String NUMBER = "NUMBER";
    private static final String FLOAT = "FLOAT";
    private static final String DATE = "DATE";
    private static final String TIMESTAMP = "TIMESTAMP";
    private static final String BLOB = "BLOB";

    // MySQL Data Types
    private static final String INT = "int";
    private static final String TINYINT = "tinyint";
    private static final String SMALLINT = "smallint";


    // Postgres Data Types
    private static final String SERIAL = "serial";
    private static final String INT2 = "int2";
    private static final String INT4 = "int4";
    private static final String NUMERIC = "numeric";

    // Java Data Type
    private static final String JAVA_BYTE_ARRAY = "byte[]" ;

    private static final String JAVA_OBJECT = "java.lang.Object";

    private static final String JAVA_STRING = "java.lang.String";

    private static final String JAVA_LONG = "java.lang.Long";

    private static final String JAVA_LONG_PRIMITIVE = "long";

    private static final String JAVA_SHORT = "java.lang.Short";

    private static final String JAVA_SHORT_PRIMITIVE = "short";

    private static final String JAVA_INTEGER = "java.lang.Integer";

    private static final String JAVA_INTEGER_PRIMITIVE = "int";

    private static final String JAVA_DOUBLE = "java.lang.Double";

    private static final String JAVA_DOUBLE_PRIMITIVE = "double";

    private static final String JAVA_FLOAT = "java.lang.Float";

    private static final String JAVA_FLOAT_PRIMITIVE = "float";

    private static final String JAVA_DATE = "java.util.Date";

    private static final String JAVA_DATE_SQL = "java.sql.Date";

    private static final String JAVA_BYTE = "java.lang.Byte";

    private static final String JAVA_BYTE_PRIMITIVE = "byte";

    // Java Data Type Group
    private static final String[] JAVA_GROUP_STRING = new String[] { JAVA_STRING };
    // private static final String[] JAVA_GROUP_CHAR = new String[] { JAVA_CHAR,
    // JAVA_CHAR_PRIMITIVE, JAVA_STRING };
    private static final String[] JAVA_GROUP_LONG = new String[] { JAVA_LONG,
            JAVA_LONG_PRIMITIVE, JAVA_STRING };
    private static final String[] JAVA_GROUP_INTEGER = new String[] {
            JAVA_INTEGER, JAVA_INTEGER_PRIMITIVE, JAVA_LONG,
            JAVA_LONG_PRIMITIVE, JAVA_STRING };
    private static final String[] JAVA_GROUP_SHORT = new String[] { JAVA_SHORT,
            JAVA_SHORT_PRIMITIVE, JAVA_INTEGER, JAVA_INTEGER_PRIMITIVE,
            JAVA_LONG, JAVA_LONG_PRIMITIVE, JAVA_STRING };
    private static final String[] JAVA_GROUP_BYTE = new String[] { JAVA_BYTE,
            JAVA_BYTE_PRIMITIVE, JAVA_SHORT, JAVA_SHORT_PRIMITIVE,
            JAVA_INTEGER, JAVA_INTEGER_PRIMITIVE, JAVA_LONG,
            JAVA_LONG_PRIMITIVE, JAVA_STRING };

    private static final String[] JAVA_GROUP_DATE = new String[] { JAVA_DATE,
            JAVA_DATE_SQL, JAVA_STRING };
    private static final String[] JAVA_GROUP_DOUBLE = new String[] {
            JAVA_DOUBLE, JAVA_DOUBLE_PRIMITIVE, JAVA_STRING };
    private static final String[] JAVA_GROUP_FLOAT = new String[] { JAVA_FLOAT,
            JAVA_FLOAT_PRIMITIVE, JAVA_STRING };

    private static final String[] JAVA_GROUP_OBJECT = new String[] { JAVA_OBJECT };

    private static final String[] JAVA_GROUP_BYTE_ARRAY = new String[] { JAVA_BYTE_ARRAY };



    @Override
    public String getDataType(Column column) {
        String[] dataType = getValidDataTypes(column.getTypeName(), column.getSize(), column.getDecimalDigits());
        return (dataType == null) ? null : dataType[0];

    }

    private String[] getValidDataTypes(String sqlType, int size, int precision) {
        if (VARCHAR2.equals(sqlType) || VARCHAR.equals(sqlType)
                || NVARCHAR2.equals(sqlType) || CHAR.equals(sqlType)
                || NCHAR.equals(sqlType)) {
            if (size == 1) {

                // Workaround: JAVA_GROUP_CHAR is not supported in iBatis
                return JAVA_GROUP_STRING;
            } else {
                return JAVA_GROUP_STRING;
            }
        } else if (NUMBER.equals(sqlType)
                || INT2.equals(sqlType)
                || INT4.equals(sqlType)
                || NUMERIC.equals(sqlType)
                || FLOAT.equals(sqlType)
                || SMALLINT.equals(sqlType)
                || TINYINT.equals(sqlType)
                || INT.equals(sqlType)
                || SERIAL.equals(sqlType)) {
            if (precision > 0) {
                if (size >= 37 || size == 0) {
                    return JAVA_GROUP_DOUBLE;
                } else {
                    return JAVA_GROUP_FLOAT;
                }
            } else {
                if (size >= 10 || size == 0) {
                    return JAVA_GROUP_LONG;
                } else if (size <= 9 && size >= 5) {
                    return JAVA_GROUP_INTEGER;
                } else if (size <= 4 && size >= 2) {
                    return JAVA_GROUP_SHORT;
                } else if (size <= 2) {
                    return JAVA_GROUP_BYTE;
                }
            }
        } else if (DATE.equals(sqlType) || ( TIMESTAMP.equalsIgnoreCase(sqlType) ) ) {
            return JAVA_GROUP_DATE;
        }
        else if (BLOB.equals(sqlType)) {
            return JAVA_GROUP_BYTE_ARRAY;
        }
        return JAVA_GROUP_STRING;
    }
}
