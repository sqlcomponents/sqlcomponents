package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Test;
import org.locationtech.spatial4j.shape.Circle;
import org.sqlcomponents.compiler.java.mapper.BaseMapperTest;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test Mappings of Point Types in Postgres.
 * Ref: https://www.postgresql.org/docs/current/datatype-geometric.html
 */
public class PGPointTypeTest extends BaseMapperTest {

    public PGPointTypeTest() throws IOException, SQLException {
        super();
    }

    @Test
    void getDataType() throws Exception {
        assertEquals(Circle.class, Class.forName(getDataType("a_point")), "Type Mismatch");
    }
}
