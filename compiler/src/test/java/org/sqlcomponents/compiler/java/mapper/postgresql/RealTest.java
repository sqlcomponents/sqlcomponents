package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;

public class RealTest extends DataTypeTest<Float> {
    @Override
    Set<Float> values() { return Set.of((float) -2304); }

    @Override
    String dataType() {
        return "real";
    }
}
