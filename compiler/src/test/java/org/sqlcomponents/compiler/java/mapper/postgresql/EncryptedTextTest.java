package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.util.Set;

public class EncryptedTextTest extends DataTypeTest<String> {

    @Override
    Set<String> values() {
        return Set.of("AUEYO#3dI*75BC@");
    }

    @Override
    String dataType() { return "text"; }
}
