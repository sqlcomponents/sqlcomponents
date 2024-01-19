package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Polygon;
import org.sqlcomponents.compiler.java.mapper.BaseMapperTest;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test Mappings of Polygon Types in Postgres.
 * Ref: https://www.postgresql.org/docs/current/datatype-geometric.html
 */ 
public class PGPolygonTypeTest extends BaseMapperTest {
    public PGPolygonTypeTest() throws IOException, SQLException {
        super();
    }

    @Test
    void getDataType() throws Exception {
        assertEquals(Polygon.class, Class.forName(getDataType("a_polygon")), "Type Mismatch");

    }
}









