package org.sqlcomponents.core.compiler;

import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.core.model.Application;

public interface Compiler {

    void compile(Application application) throws ScubeException;
}
