package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Disabled;

import java.nio.ByteBuffer;
import java.util.Set;

@Disabled
public class BlobTest extends DataTypeTest<ByteBuffer> {

    @Override
    Set<ByteBuffer> values() { return Set.of(ByteBuffer.wrap(new byte[]{1, 2, 3, 4, 5})); }

    @Override
    String dataType() {
        return "bytea";
    }
}
