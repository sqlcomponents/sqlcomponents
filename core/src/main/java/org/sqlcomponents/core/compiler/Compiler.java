package org.sqlcomponents.core.compiler;

import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.core.model.Application;

public interface Compiler {

    /**
     *
     * @param application
     * @throws ScubeException
     */
    void compile(Application application) throws ScubeException;
}
