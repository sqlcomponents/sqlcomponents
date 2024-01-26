package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static  org.sqlcomponents.compiler.java.util.CompilerTestUtil.getDataType;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Test Mappings of Network Types in Postgres.
 * Ref: https://www.postgresql.org/docs/current/datatype-net-types.html
 */
public class PGNetworkCidrTypeTest {
    public PGNetworkCidrTypeTest() throws IOException, SQLException {
        super();
    }

    @Test
    @Disabled
    void testDataType() throws Exception {

        assertEquals(InetAddress.class, Class.forName(getDataType("a_cidr")), "Type Mismatch");


    }
}
