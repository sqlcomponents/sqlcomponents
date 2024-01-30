package org.sqlcomponents.compiler.java.mapper.postgresql;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static  org.sqlcomponents.compiler.java.util.CompilerTestUtil.getDataType;
class PGNetworkMacaddr8Type {

    @Test
    void testDataType() throws Exception {

        assertEquals(InetAddress.class, Class.forName(getDataType("a_macaddr8")), "Type Mismatch");


    }
}