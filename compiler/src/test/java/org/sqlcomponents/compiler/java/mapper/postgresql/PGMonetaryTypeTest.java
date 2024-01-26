package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import static  org.sqlcomponents.compiler.java.util.CompilerTestUtil.getDataType;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test Mappings of Monetary Types in Postgres.
 * Ref: https://www.postgresql.org/docs/current/datatype-money.html
 */
public class PGMonetaryTypeTest {
    public PGMonetaryTypeTest() throws IOException, SQLException {
        super();
    }

    @Test
    void testDataType() throws Exception {
        assertEquals(BigDecimal.class, Class.forName(getDataType("a_money")), "Type Mismatch");


    }
}









