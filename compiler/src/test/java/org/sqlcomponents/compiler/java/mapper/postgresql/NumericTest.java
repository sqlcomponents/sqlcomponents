package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;

public class NumericTest extends DataTypeTest<Byte> {
    @Override
    Set<Byte> values() { return Set.of((byte) -129); }

    @Override
    String dataType() {
        return "numeric";
    }
}
