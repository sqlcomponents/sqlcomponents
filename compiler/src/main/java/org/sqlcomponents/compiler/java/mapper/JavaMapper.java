package org.sqlcomponents.compiler.java.mapper;

import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.model.relational.Column;

import java.util.IdentityHashMap;
import java.util.Map;


public final class JavaMapper extends Mapper {

    private final Map<Class<? extends Number>,Integer> integerTypes;

    public JavaMapper() {
        integerTypes = new IdentityHashMap<>();
        // Holds Map of Unteger Types and Max digits they can hold
        integerTypes.put(Byte.class,String.valueOf(Byte.MAX_VALUE).length()-1);
        integerTypes.put(Short.class,String.valueOf(Short.MAX_VALUE).length()-1);
        integerTypes.put(Integer.class,String.valueOf(Integer.MAX_VALUE).length()-1);
        integerTypes.put(Long.class,String.valueOf(Long.MAX_VALUE).length()-1);

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
                return chooseIntegerType(column);
            case VARCHAR:
                return String.class;
            case BIT:
            case BOOLEAN:
                return Boolean.class;
        }
        throw new RuntimeException("Datatype not found for column "+ column);
    }

    private Class<? extends Number> chooseIntegerType(Column column) {
        return integerTypes.entrySet().stream()
                .filter( entry -> entry.getValue() <= column.getSize())
                .max((entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()))
                .get().getKey();
    }


}
