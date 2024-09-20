package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;

public class BigintTest extends DataTypeTest<Long> {
    @Override
    Set<Long> values() { return Set.of((long) Long.MAX_VALUE); }

    @Override
    String dataType() {
        return "bigint";
    }
}
