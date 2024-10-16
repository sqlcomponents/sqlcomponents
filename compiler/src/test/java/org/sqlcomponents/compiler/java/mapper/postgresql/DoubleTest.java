package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;

public class DoubleTest extends DataTypeTest<Double> {
    @Override
    Set<Double> values() { return Set.of(Double.MIN_VALUE,Double.MAX_VALUE); }

    @Override
    String dataType() {
        return "double precision";
    }
}