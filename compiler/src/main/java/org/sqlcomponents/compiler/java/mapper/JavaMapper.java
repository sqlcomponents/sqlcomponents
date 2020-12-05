package org.sqlcomponents.compiler.java.mapper;

import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.model.relational.Column;

import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Map;


public final class JavaMapper extends Mapper {

    private final Map<Class<? extends Number>,Integer> integerTypes;

    private final Map<Class<? extends Number>,Integer> decimalTypes;

    public JavaMapper() {
        integerTypes = new IdentityHashMap<>();
        // Holds Map of Integer Types and Max digits they can hold
        integerTypes.put(Byte.class,String.valueOf(Byte.MAX_VALUE).length()-1);
        integerTypes.put(Short.class,String.valueOf(Short.MAX_VALUE).length()-1);
        integerTypes.put(Integer.class,String.valueOf(Integer.MAX_VALUE).length()-1);
        integerTypes.put(Long.class,String.valueOf(Long.MAX_VALUE).length()-1);

        decimalTypes = new IdentityHashMap<>();
        // Holds Map of Decimal Types and Max digits they can hold
        decimalTypes.put(Float.class,String.valueOf(Float.MAX_VALUE).indexOf('.')-1);
        decimalTypes.put(Double.class,String.valueOf(Double.MAX_VALUE).indexOf('.')-1);
    }

    @Override
    public String getDataType(Column column) {
        return getDataTypeClass(column).getName();
    }

    private Class getDataTypeClass(Column column) {
        switch (column.getJdbcType()) {
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
        }
        throw new RuntimeException("Datatype not found for column "+ column.getColumnName() + " of jdbc type " + column.getJdbcType());
    }

    private Class<? extends Number> chooseNumberType(Column column) {
        return column.getDecimalDigits() == 0 ? chooseIntegerType(column) : chooseDecimalType(column);
    }

    private Class<? extends Number> chooseIntegerType(Column column) {
        return integerTypes.entrySet().stream()
                .filter( entry -> entry.getValue() <= column.getSize())
                .max(Comparator.comparing(Map.Entry::getValue))
                .get().getKey();
    }

    private Class<? extends Number> chooseDecimalType(Column column) {
        return decimalTypes.entrySet().stream()
                .filter( entry -> entry.getValue() <= column.getSize())
                .max(Comparator.comparing(Map.Entry::getValue))
                .get().getKey();
    }

}
