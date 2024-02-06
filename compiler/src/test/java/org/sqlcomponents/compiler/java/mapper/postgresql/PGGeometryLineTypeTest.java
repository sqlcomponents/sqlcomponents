package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.LineString;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static  org.sqlcomponents.compiler.java.util.CompilerTestUtil.getDataType;
class PGGeometryLineTypeTest {

    @Test
//    @Disabled
    void testDataType() throws Exception {
        assertEquals(LineString.class, Class.forName(getDataType("a_line")), "Type Mismatch");
    }
}

