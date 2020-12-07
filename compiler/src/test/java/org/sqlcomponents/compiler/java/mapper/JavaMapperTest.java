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
        System.out.println("Byte   " + Byte.MAX_VALUE   + "\t" + (String.valueOf(Byte.MAX_VALUE).length() - 1));
        System.out.println("Short   " + Short.MAX_VALUE   + "\t" + (String.valueOf(Short.MAX_VALUE).length() - 1));
        System.out.println("Integer " + Integer.MAX_VALUE + "\t" + (String.valueOf(Integer.MAX_VALUE).length() - 1));
        System.out.println("Long " + Long.MAX_VALUE + "\t" + (String.valueOf(Long.MAX_VALUE).length() - 1));

        Column column = new Column(null);

        column.setJdbcType(JDBCType.INTEGER);
        column.setSize(5);
        Assertions.assertEquals("java.lang.Integer", javaMapper.getDataType(column), "Integer dataType obtained");

    }
}