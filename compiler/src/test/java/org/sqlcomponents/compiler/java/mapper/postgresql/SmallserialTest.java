package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;

public class SmallserialTest extends DataTypeTest<Short> {
    @Override
    Set<Short> values() { return Set.of((short) 23); }

    @Override
    String dataType() {
        return "smallserial";
    }
}