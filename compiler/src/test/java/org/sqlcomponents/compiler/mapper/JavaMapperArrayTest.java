package org.sqlcomponents.compiler.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.model.relational.Column;
import org.sqlcomponents.core.model.relational.Procedure;
import org.sqlcomponents.core.model.relational.enums.ColumnType;

/**
 * JDBC {@code ARRAY} with known PostgreSQL element types (e.g. {@code _int4}) maps to Java boxed
 * arrays; other {@code ARRAY} shapes use {@link java.sql.Array}. {@code STRUCT} maps to
 * {@link Object} so composite IN parameters accept driver-specific values (e.g. PostgreSQL
 * {@code PGobject}) because {@link java.sql.Connection#createStruct} is not implemented for PG.
 */
class JavaMapperArrayTest {

    @Test
    void pgIntArrayColumnMapsToIntegerArray() {
        Application app = new Application();
        JavaMapper mapper = new JavaMapper(app);
        Column col = new Column(new Procedure());
        col.setColumnName("p_values");
        col.setColumnType(ColumnType.ARRAY);
        col.setTypeName("_int4");
        Assertions.assertEquals("java.lang.Integer[]", mapper.getDataType(null, col));
    }

    @Test
    void jdbcArrayWithoutUnderscorePrefixFallsBackToSqlArray() {
        Application app = new Application();
        JavaMapper mapper = new JavaMapper(app);
        Column col = new Column(new Procedure());
        col.setColumnName("p_values");
        col.setColumnType(ColumnType.ARRAY);
        col.setTypeName("integer[]");
        Assertions.assertEquals("java.sql.Array", mapper.getDataType(null, col));
    }

    @Test
    void structColumnMapsToJavaLangObject() {
        Application app = new Application();
        JavaMapper mapper = new JavaMapper(app);
        Column col = new Column(new Procedure());
        col.setColumnName("p_row");
        col.setColumnType(ColumnType.STRUCT);
        col.setTypeName("my_composite");
        Assertions.assertEquals("java.lang.Object", mapper.getDataType(null, col));
    }
}
