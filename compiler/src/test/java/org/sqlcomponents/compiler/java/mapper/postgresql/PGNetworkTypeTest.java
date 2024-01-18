package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.LineSegment;
import org.sqlcomponents.compiler.java.mapper.BaseMapperTest;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test Mappings of Network Types in Postgres.
 * Ref: https://www.postgresql.org/docs/current/datatype-net-types.html
 */
public class PGNetworkTypeTest extends BaseMapperTest {
    public PGNetworkTypeTest() throws IOException, SQLException {
        super();
    }

    @Test
    void getDataType() throws Exception {

        assertEquals(InetAddress.class, Class.forName(getDataType("a_inet")), "Type Mismatch");


    }
}









