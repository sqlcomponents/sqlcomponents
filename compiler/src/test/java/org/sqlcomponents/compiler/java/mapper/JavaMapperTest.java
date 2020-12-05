package org.sqlcomponents.compiler.java.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sqlcomponents.core.model.relational.Column;

import java.sql.JDBCType;

class JavaMapperTest {
    private final JavaMapper javaMapper;

    JavaMapperTest() {

        this.javaMapper = new JavaMapper();
    }

    @Test
    void test_GetIntegerDataTypes() {
        Column column = new Column(null);
        // checking integer types
        column.setJdbcType(JDBCType.INTEGER);
        column.setSize(String.valueOf(Integer.MAX_VALUE).length());
        Assertions.assertEquals("java.lang.Long", javaMapper.getDataType(column), "Long dataType obtained");

        column.setSize(String.valueOf(Integer.MAX_VALUE).length() - 1);
        Assertions.assertEquals("java.lang.Integer", javaMapper.getDataType(column), "Integer dataType obtained");
        // checking long types
//        column.setJdbcType(JDBCType.INTEGER);
//        column.setSize(100);
//        Assertions.assertEquals("java.lang.Long", javaMapper.getDataType(column), "Long dataType obtained");
    }
}