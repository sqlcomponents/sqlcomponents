package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;

public class IntegerTest extends DataTypeTest<Long> {
    @Override
    Set<Long> values() { return Set.of((long)-2147483648); }

    @Override
    String dataType() {
        return "integer";
    }
}
