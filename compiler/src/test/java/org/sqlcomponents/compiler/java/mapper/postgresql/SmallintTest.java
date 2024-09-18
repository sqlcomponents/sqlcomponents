package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;

public class SmallintTest extends DataTypeTest<Short> {
    @Override
    Set<Short> values() { return Set.of((short)32798); }

    @Override
    String dataType() {
        return "smallint";
    }
}
