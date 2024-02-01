package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Polygon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static  org.sqlcomponents.compiler.java.util.CompilerTestUtil.getDataType;
/**
 * Test Mappings of Polygon Types in Postgres.
 * Ref: https://www.postgresql.org/docs/current/datatype-geometric.html
 */
class PGPolygonTypeTest {

    @Test
    @Disabled
    void testDataType() throws Exception {
        assertEquals(Polygon.class, Class.forName(getDataType("a_polygon")), "Type Mismatch");

    }
}









