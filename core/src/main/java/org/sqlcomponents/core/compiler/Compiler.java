package org.sqlcomponents.core.compiler;

import org.sqlcomponents.core.model.Application;

import java.sql.SQLException;

/**
 * The interface Compiler.
 */
//todo: Is it compiler or generator?
public interface Compiler {
    /**
     * Compile.
     *
     * @param aApplication the a application
     * @throws SQLException the sql exception
     */
    public void compile(final Application aApplication) throws SQLException;
}
