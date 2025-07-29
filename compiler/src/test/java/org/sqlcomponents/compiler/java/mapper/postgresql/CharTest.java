package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;

public class CharTest extends DataTypeTest<Character> {
    @Override
    Set<Character> values() { return Set.of('4', 'v'); }

    @Override
    String dataType() {
        return "char(1)";
    }
}

