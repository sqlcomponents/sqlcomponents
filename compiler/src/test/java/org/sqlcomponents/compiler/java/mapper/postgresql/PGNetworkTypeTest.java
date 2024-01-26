package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static  org.sqlcomponents.compiler.java.util.CompilerTestUtil.getDataType;
/**
 * Test Mappings of Network Types in Postgres.
 * Ref: https://www.postgresql.org/docs/current/datatype-net-types.html
 */
public class PGNetworkTypeTest {
    public PGNetworkTypeTest() throws IOException, SQLException {
        super();
    }

    @Test
    void testDataType() throws Exception {

        assertEquals(InetAddress.class, Class.forName(getDataType("a_inet")), "Type Mismatch");


    }
}









