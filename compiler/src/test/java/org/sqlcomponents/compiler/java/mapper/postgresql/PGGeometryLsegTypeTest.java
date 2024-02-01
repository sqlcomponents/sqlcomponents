package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.LineSegment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static  org.sqlcomponents.compiler.java.util.CompilerTestUtil.getDataType;

/**
 * Test Mappings of Geometry Types in Postgres.
 * Ref: https://www.postgresql.org/docs/current/datatype-geometric.html
 */
class PGGeometryLsegTypeTest {

    @Test
//    @Disabled
    void testDataType() throws Exception {
        assertEquals(LineSegment.class, Class.forName(getDataType("a_lseg")), "Type Mismatch");
    }
}









