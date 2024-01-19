package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Test;
import org.locationtech.spatial4j.shape.Circle;
import org.sqlcomponents.compiler.java.mapper.BaseMapperTest;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PGGeometryPathType extends BaseMapperTest {
    public PGGeometryPathType() throws IOException, SQLException {
        super();
    }

    @Test
    void getDataType() throws Exception {
        assertEquals(String.class, Class.forName(getDataType("a_path")), "Type Mismatch");
    }
}
