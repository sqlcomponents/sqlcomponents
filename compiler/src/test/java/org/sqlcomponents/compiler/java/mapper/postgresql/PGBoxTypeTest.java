package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.locationtech.spatial4j.shape.Circle;
import static  org.sqlcomponents.compiler.java.util.CompilerTestUtil.getDataType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PGBoxTypeTest {

    @Test
    @Disabled
    void testDataType() throws Exception {
        assertEquals(Circle.class, Class.forName(getDataType("a_circle")), "Type Mismatch");
    }
}
