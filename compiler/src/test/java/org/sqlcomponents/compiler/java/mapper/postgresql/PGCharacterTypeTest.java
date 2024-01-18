package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Test;
import org.sqlcomponents.compiler.java.mapper.BaseMapperTest;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test Mappings of Character Types in Postgres.
 * Ref: https://www.postgresql.org/docs/current/datatype-character.html
 */
public class PGCharacterTypeTest extends BaseMapperTest {
    public PGCharacterTypeTest() throws IOException, SQLException {
        super();
    }

    @Test
    void getDataType() throws Exception {
        assertEquals(Character.class, Class.forName(getDataType("a_char")), "Type Mismatch");
        assertEquals(String.class, Class.forName(getDataType("a_text")), "Type Mismatch");
        assertEquals(String.class, Class.forName(getDataType("a_encrypted_text")), "Type Mismatch");
        assertEquals(String.class, Class.forName(getDataType("a_bpchar")), "Type Mismatch");

    }
}









