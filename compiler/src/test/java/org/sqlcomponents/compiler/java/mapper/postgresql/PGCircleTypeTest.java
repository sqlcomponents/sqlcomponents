package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.locationtech.spatial4j.shape.Circle;
import static  org.sqlcomponents.compiler.java.util.CompilerTestUtil.getDataType;
/**
 * Test Mappings of Circle Types in Postgres.
 * Ref: https://www.postgresql.org/docs/current/datatype-geometric.html
 */
public class PGCircleTypeTest {
    public PGCircleTypeTest() throws IOException, SQLException {
        super();
    }

    @Test
    void testDataType() throws Exception {
        assertEquals(Circle.class, Class.forName(getDataType("a_circle")), "Type Mismatch");
    }
}









