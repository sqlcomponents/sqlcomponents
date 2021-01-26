package org.sqlcomponents.core.exception;

public class ScubeException extends Exception {

    /**
     * Contructor with no arguements.
     */
    public ScubeException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * constructor with arguements.
     * @param arg0
     * @param arg1
     */
    public ScubeException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    /**
     * constructor with arguements.
     * @param arg0
     */
    public ScubeException(final String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * constructor with arguements.
     * @param arg0
     */
    public ScubeException(final Throwable arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

}
