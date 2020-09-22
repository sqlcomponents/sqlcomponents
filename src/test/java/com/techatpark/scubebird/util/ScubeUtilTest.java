package com.techatpark.scubebird.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class ScubeUtilTest {

    @Test
    void writeCode() throws IOException, ClassNotFoundException {
        ScubeUtil.generateCode("src/test/resources/sakila.dao","target/generated-sources/sakila");
    }
}