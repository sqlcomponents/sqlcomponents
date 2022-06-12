package org.sqlcomponents.core.exception;

public class SQLComponentsException extends Exception {

    /**
     * Constructor with no arguments.
     */
    public SQLComponentsException() {
        super();
    }

    /**
     * constructor with arguments.
     * @param arg0
     * @param arg1
     */
    public SQLComponentsException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * constructor with arguments.
     * @param arg0
     */
    public SQLComponentsException(final String arg0) {
        super(arg0);
    }

    /**
     * constructor with arguments.
     * @param arg0
     */
    public SQLComponentsException(final Throwable arg0) {
        super(arg0);
    }
}
