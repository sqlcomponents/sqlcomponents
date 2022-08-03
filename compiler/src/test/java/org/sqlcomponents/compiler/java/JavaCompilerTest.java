package org.sqlcomponents.compiler.java;

import org.junit.jupiter.api.Test;
import org.sqlcomponents.compiler.java.util.CompilerTestUtil;
import org.sqlcomponents.core.model.Application;

import java.util.HashMap;
import java.util.Map;

class JavaCompilerTest {
    @Test
    void writeCode() throws Exception {
        Application application = CompilerTestUtil.getApplication();
        Map<String, String> insertMap = new HashMap<>();
        insertMap.put("created_at", "CURRENT_TIMESTAMP");
        insertMap.put("modified_by", "");
        insertMap.put("modified_at", "");
        application.setInsertMap(insertMap);

        Map<String, String> updateMap = new HashMap<>();
        updateMap.put("modified_at", "CURRENT_TIMESTAMP");
        updateMap.put("created_by", "");
        updateMap.put("created_at", "");
        application.setUpdateMap(updateMap);

        application.compile(new JavaCompiler());
        System.out.println("Code is compiled into " + application.getSrcFolder());
    }

}