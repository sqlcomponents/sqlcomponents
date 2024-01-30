package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static  org.sqlcomponents.compiler.java.util.CompilerTestUtil.getDataType;

/**
 * Test Mappings of Character Types in Postgres.
 * Ref: https://www.postgresql.org/docs/current/datatype-character.html
 */
class PGCharacterTypeTest {

    @Test
    void testDataType() throws Exception {
        assertEquals(Character.class, Class.forName(getDataType("a_char")), "Type Mismatch");
        assertEquals(String.class, Class.forName(getDataType("a_text")), "Type Mismatch");
        assertEquals(String.class, Class.forName(getDataType("a_encrypted_text")), "Type Mismatch");
        assertEquals(String.class, Class.forName(getDataType("a_bpchar")), "Type Mismatch");

    }
}









