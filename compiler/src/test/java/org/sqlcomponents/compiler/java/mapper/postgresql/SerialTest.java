package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;

public class SerialTest extends DataTypeTest<Long> {
    @Override
    Set<Long> values() { return Set.of((long) 23); }

    @Override
    String dataType() {
        return "serial";
    }
}
