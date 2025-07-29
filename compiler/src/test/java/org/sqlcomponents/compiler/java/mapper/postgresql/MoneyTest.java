package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Disabled;

import java.math.BigDecimal;
import java.util.Set;

@Disabled
public class MoneyTest extends DataTypeTest<BigDecimal> {
    @Override
    Set<BigDecimal> values() { return Set.of(); }

    @Override
    String dataType() {
        return "money";
    }
}
