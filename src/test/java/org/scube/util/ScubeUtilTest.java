package org.scube.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class ScubeUtilTest {

    @Test
    void writeCode() throws IOException, ClassNotFoundException {
        ScubeUtil.generateCode("src/test/resources/mysql.dao","target/generated-sources/mydb");
    }
}