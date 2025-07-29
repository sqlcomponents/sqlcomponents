package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;

public class BpcharTest extends DataTypeTest<String> {

    @Override
    Set<String> values() {
        return Set.of("ValueForBpchar");
    }

    @Override
    String dataType() {
        return "bpchar";
    }
}
