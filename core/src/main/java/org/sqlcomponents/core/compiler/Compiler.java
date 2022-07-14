package org.sqlcomponents.core.compiler;

import org.sqlcomponents.core.exception.SQLComponentsException;
import org.sqlcomponents.core.model.Application;

import java.sql.SQLException;

//todo: Is it compiler or generator?
public interface Compiler {
    public void compile(final Application aApplication) throws SQLException;
}
