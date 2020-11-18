package org.sqlcomponents.compiler.java.mapper;

import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.model.relational.Column;

import java.sql.Types;

public class JavaMapper extends Mapper {

    @Override
    public String getDataType(Column column) {
        switch (column.getType()) {
            case Types.INTEGER:
                return chooseIntegerType(column);
            case Types.NUMERIC:
                return chooseIntegerType(column);
            case Types.VARCHAR:
                return "java.lang.String";
        }
        throw new RuntimeException("Datatype not found for column "+ column);
    }

    private String chooseIntegerType(Column column) {
        return column.getSize() > 2 ? "java.lang.Integer" : "java.lang.Byte";
    }
}
