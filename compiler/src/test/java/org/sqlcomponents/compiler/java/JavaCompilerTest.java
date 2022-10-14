package org.sqlcomponents.compiler.java;

import org.junit.jupiter.api.Test;
import org.sqlcomponents.compiler.java.util.CompilerTestUtil;
import org.sqlcomponents.core.model.Application;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class JavaCompilerTest {
    @Test
    void writeCode() throws Exception {
        Application application = CompilerTestUtil.getApplication();

        if (application.getInsertMap() == null) {
            Map<String, String> insertMap = new HashMap<>();
            insertMap.put("created_at", "CURRENT_TIMESTAMP");
            insertMap.put("modified_by", null);
            insertMap.put("modified_at", null);
            insertMap.put("azagu_raja#a_integer", "4");
            application.setInsertMap(insertMap);
        }

        if (application.getUpdateMap() == null) {
            Map<String, String> updateMap = new HashMap<>();
            updateMap.put("modified_at", "CURRENT_TIMESTAMP");
            updateMap.put("created_by", null);
            updateMap.put("created_at", null);
            updateMap.put("azagu_raja#a_integer", "5");
            application.setUpdateMap(updateMap);
        }

        application.setEncryption(Arrays.asList("a_encrypted_text"));

        application.compile(new JavaCompiler());
        System.out.println("Code is compiled into " + application.getSrcFolder());
    }

}