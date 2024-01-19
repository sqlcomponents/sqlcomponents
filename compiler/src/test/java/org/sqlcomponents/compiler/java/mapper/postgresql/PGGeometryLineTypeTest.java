package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.LineString;
import org.sqlcomponents.compiler.java.mapper.BaseMapperTest;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PGGeometryLineTypeTest extends BaseMapperTest {
    public PGGeometryLineTypeTest() throws IOException, SQLException {
        super();
    }

    @Test
    void getDataType() throws Exception {
        assertEquals(LineString.class, Class.forName(getDataType("a_open")), "Type Mismatch");
    }
}

