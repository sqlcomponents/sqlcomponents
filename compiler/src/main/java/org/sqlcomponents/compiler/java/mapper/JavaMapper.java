package org.sqlcomponents.compiler.java.mapper;

import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.model.relational.Column;


public class JavaMapper extends Mapper {

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
                return Boolean.class;
        }
        throw new RuntimeException("Datatype not found for column "+ column);
    }

    private Class<? extends Number> chooseIntegerType(Column column) {
        return column.getSize() > 2 ? Integer.class : Byte.class;
    }


}
