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

        column.setJdbcType(JDBCType.INTEGER);
        column.setSize(5);
        Assertions.assertEquals("java.lang.Integer", javaMapper.getDataType(column), "Integer dataType obtained");

    }
}