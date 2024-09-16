package org.sqlcomponents.compiler.java;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.sqlcomponents.compiler.java.util.CompilerTestUtil;
import org.sqlcomponents.core.model.Application;

@Disabled
class JavaCompilerTest {
    @Test
    @SuppressWarnings("squid:S2699")
    void writeCode() throws Exception {
        Application application = CompilerTestUtil.getApplication();
        application.compile(new JavaCompiler());
        System.out.println(
                "Code is compiled into " + application.getSrcFolder());
    }

}