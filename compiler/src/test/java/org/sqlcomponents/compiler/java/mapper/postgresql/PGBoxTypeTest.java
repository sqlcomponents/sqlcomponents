package org.sqlcomponents.compiler.java.mapper.postgresql;


import org.junit.jupiter.api.Test;

import org.locationtech.jts.geom.Envelope;
import static  org.sqlcomponents.compiler.java.util.CompilerTestUtil.getDataType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PGBoxTypeTest {

//    @Test
//    @Disabled
//    void testDataType() throws Exception {
//        assertEquals(Circle.class, Class.forName(getDataType("a_circle")), "Type Mismatch");
//    }

    @Test
//    @Disabled
    void testDataType() throws Exception {
        assertEquals(Envelope.class, Class.forName(getDataType("a_box")), "Type Mismatch");
    }
}
