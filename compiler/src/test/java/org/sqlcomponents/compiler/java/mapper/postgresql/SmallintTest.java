package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;

public class SmallintTest extends DataTypeTest<Short> {
    @Override
    Set<Short> values() { return Set.of(Short.MIN_VALUE,Short.MAX_VALUE); }

    @Override
    String dataType() {
        return "smallint";
    }
}
