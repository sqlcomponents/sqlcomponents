package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.LineString;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static  org.sqlcomponents.compiler.java.util.CompilerTestUtil.getDataType;
public class PGGeometryLineTypeTest {
    public PGGeometryLineTypeTest() throws IOException, SQLException {
        super();
    }

    @Test
    @Disabled
    void testDataType() throws Exception {
        assertEquals(LineString.class, Class.forName(getDataType("a_open")), "Type Mismatch");
    }
}

