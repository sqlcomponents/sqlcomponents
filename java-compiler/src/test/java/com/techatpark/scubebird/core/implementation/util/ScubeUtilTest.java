package com.techatpark.scubebird.core.implementation.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class ScubeUtilTest {

    @Test
    void writeCode() throws IOException, ClassNotFoundException {
        ScubeUtil.generateCode("src/test/resources/oracle-hr.dao"
                ,"../examples/java-jdbc/target/generated-sources/hr-oracle");
    }
}