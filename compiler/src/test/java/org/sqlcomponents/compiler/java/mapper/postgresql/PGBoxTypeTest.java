package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Test;
import org.locationtech.spatial4j.shape.Circle;
import org.sqlcomponents.compiler.java.mapper.BaseMapperTest;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PGBoxTypeTest extends BaseMapperTest {
    public PGBoxTypeTest() throws IOException, SQLException {
        super();
    }

    @Test
    void getDataType() throws Exception {
        assertEquals(Circle.class, Class.forName(getDataType("a_circle")), "Type Mismatch");
    }
}
