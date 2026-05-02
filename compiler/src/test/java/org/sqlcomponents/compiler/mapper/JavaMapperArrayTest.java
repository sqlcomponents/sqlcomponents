package org.sqlcomponents.compiler.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.model.relational.Column;
import org.sqlcomponents.core.model.relational.Procedure;
import org.sqlcomponents.core.model.relational.enums.ColumnType;

/**
 * JDBC {@code ARRAY} / {@code STRUCT} on routine parameters map to {@link java.sql.Array}
 * and {@link java.sql.Struct} for generated {@code CallableStatement} code.
 */
class JavaMapperArrayTest {

    @Test
    void jdbcArrayColumnMapsToJavaSqlArray() {
        Application app = new Application();
        JavaMapper mapper = new JavaMapper(app);
        Column col = new Column(new Procedure());
        col.setColumnName("p_values");
        col.setColumnType(ColumnType.ARRAY);
        col.setTypeName("_int4");
        Assertions.assertEquals("java.sql.Array", mapper.getDataType(null, col));
    }

    @Test
    void structColumnMapsToJavaSqlStruct() {
        Application app = new Application();
        JavaMapper mapper = new JavaMapper(app);
        Column col = new Column(new Procedure());
        col.setColumnName("p_row");
        col.setColumnType(ColumnType.STRUCT);
        col.setTypeName("my_composite");
        Assertions.assertEquals("java.sql.Struct", mapper.getDataType(null, col));
    }
}
