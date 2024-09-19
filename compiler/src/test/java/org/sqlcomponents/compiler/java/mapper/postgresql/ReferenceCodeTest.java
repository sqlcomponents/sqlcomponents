package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;
import java.util.UUID;

public class ReferenceCodeTest extends DataTypeTest<UUID> {
    @Override
    Set<UUID> values() { return Set.of(UUID.randomUUID(), UUID.randomUUID());  }

    @Override
    String dataType() {
        return "uuid";
    }
}
