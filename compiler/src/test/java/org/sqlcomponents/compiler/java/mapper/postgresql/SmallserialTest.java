package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Disabled;

import java.util.Set;

@Disabled
public class SmallserialTest extends DataTypeTest<Short> {
    @Override
    Set<Short> values() { return Set.of((short) 23); }

    @Override
    String dataType() {
        return "smallserial";
    }
}