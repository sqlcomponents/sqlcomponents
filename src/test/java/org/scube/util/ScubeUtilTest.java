package org.scube.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ScubeUtilTest {

    @Test
    void writeCode() throws IOException, ClassNotFoundException {
        ScubeUtil.generateCode("src/test/resources/mysql.dao","target/generated-sources/mydb");
    }
}