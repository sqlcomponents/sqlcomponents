package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.LineSegment;
import org.sqlcomponents.compiler.java.mapper.BaseMapperTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test Mappings of Geometry Types in Postgres.
 * Ref: https://www.postgresql.org/docs/current/datatype-geometric.html
 */
public class PGGeometryTypeTest extends BaseMapperTest {
    public PGGeometryTypeTest() throws IOException, SQLException {
        super();
    }

    @Test
    void getDataType() throws Exception {
        assertEquals(LineSegment.class, Class.forName(getDataType("a_closed")), "Type Mismatch");



    }
}









