package org.sqlcomponents.core.compiler;

import org.sqlcomponents.core.exception.SQLComponentsException;
import org.sqlcomponents.core.model.Application;

//todo: Is it compiler or generator?
public interface Compiler
{
    void compile(final Application aApplication) throws Exception;
}
