package org.sqlcomponents.compiler.java.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sqlcomponents.core.model.relational.Column;
import org.sqlcomponents.core.model.relational.enums.ColumnType;

class DB2JavaDataTypeMapperTest {
    private final DB2JavaDataTypeMapper db2JavaDataTypeMapper;

    DB2JavaDataTypeMapperTest() {
        db2JavaDataTypeMapper = new DB2JavaDataTypeMapper(null);
    }

    @Test
    void test_GetIntegerDataTypes() {
        Column column = new Column(null);
        column.setColumnType(ColumnType.INTEGER);
        column.setSize(5);
        Assertions.assertEquals("java.lang.Integer", db2JavaDataTypeMapper.getDataType(column),
                "Integer dataType obtained");
    }
}