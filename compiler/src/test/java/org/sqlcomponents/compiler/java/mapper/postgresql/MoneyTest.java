package org.sqlcomponents.compiler.java.mapper.postgresql;

import java.math.BigDecimal;
import java.util.Set;

public class MoneyTest extends DataTypeTest<BigDecimal> {
    @Override
    Set<BigDecimal> values() {
        return Set.of(new BigDecimal("1000.00"));
    }

    @Override
    String dataType() {
        return "money";
    }
}

