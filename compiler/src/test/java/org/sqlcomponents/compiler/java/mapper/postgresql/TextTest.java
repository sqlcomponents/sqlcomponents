package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;

public class TextTest extends DataTypeTest<String> {

    @Override
    Set<String> values() {
        return Set.of("ValueForText", "");
    }

    @Override
    String dataType() {
        return "text";
    }
}
