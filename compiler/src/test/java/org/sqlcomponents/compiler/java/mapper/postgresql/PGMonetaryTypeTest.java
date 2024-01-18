package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Test;
import org.sqlcomponents.compiler.java.mapper.BaseMapperTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test Mappings of Monetary Types in Postgres.
 * Ref: https://www.postgresql.org/docs/current/datatype-money.html
 */
public class PGMonetaryTypeTest extends BaseMapperTest {
    public PGMonetaryTypeTest() throws IOException, SQLException {
        super();
    }

    @Test
    void getDataType() throws Exception {
        assertEquals(BigDecimal.class, Class.forName(getDataType("a_money")), "Type Mismatch");


    }
}









