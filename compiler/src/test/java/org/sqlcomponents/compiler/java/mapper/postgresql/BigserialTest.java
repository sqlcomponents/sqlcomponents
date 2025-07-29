package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Disabled;

import java.util.Set;

@Disabled
public class BigserialTest extends DataTypeTest<Long> {
    @Override
    Set<Long> values() { return Set.of((long) 23); }

    @Override
    String dataType() {
        return "bigserial";
    }
}