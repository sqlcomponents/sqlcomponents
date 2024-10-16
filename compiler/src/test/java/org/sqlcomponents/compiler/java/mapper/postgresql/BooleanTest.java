package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;

public class BooleanTest extends DataTypeTest<Boolean> {

    @Override
    Set<Boolean> values() {
        return Set.of(true,false);
    }

    @Override
    String dataType() {
        return "boolean";
    }
}
