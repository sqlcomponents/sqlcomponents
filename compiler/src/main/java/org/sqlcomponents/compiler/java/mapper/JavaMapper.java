package org.sqlcomponents.compiler.java.mapper;

import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.model.relational.Column;

import java.sql.Types;

public class JavaMapper extends Mapper {

    @Override
    public String getDataType(Column column) {

        switch (column.getType()) {
            case Types.BIGINT:
                return "java.lang.Long";
                case Types.VARCHAR:
                    return "java.lang.String";

        }
        return "java.lang.String";
    }
}
