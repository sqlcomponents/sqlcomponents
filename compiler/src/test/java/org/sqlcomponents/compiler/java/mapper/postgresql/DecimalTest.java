package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;

public class DecimalTest extends DataTypeTest<Byte> {
    @Override
    Set<Byte> values() { return Set.of((byte) -128); }

    @Override
    String dataType() {
        return "decimal";
    }
}
