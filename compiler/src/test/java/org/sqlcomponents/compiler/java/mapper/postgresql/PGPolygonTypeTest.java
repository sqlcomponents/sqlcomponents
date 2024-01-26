package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Polygon;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static  org.sqlcomponents.compiler.java.util.CompilerTestUtil.getDataType;
/**
 * Test Mappings of Polygon Types in Postgres.
 * Ref: https://www.postgresql.org/docs/current/datatype-geometric.html
 */
public class PGPolygonTypeTest {
    public PGPolygonTypeTest() throws IOException, SQLException {
        super();
    }

    @Test
    @Disabled
    void testDataType() throws Exception {
        assertEquals(Polygon.class, Class.forName(getDataType("a_polygon")), "Type Mismatch");

    }
}









