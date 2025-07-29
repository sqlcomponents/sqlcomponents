package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;

public class VarCharTest extends DataTypeTest<String> {

    VarCharTest() throws Exception {
    }

    @Override
    Set<String> values() {
        return Set.of("ValueForVarchar");
    }

    @Override
    String dataType() {
        return "VARCHAR";
    }
}
