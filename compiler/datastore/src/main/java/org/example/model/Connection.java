package org.example.model;

 /**
  * Data Holder for the table - connection.
  */
public final class Connection {

    /**
     * holds value of the column code.
     */
    private String code;
    /**
     * holds value of the column name.
     */
    private String name;
    /**
     * gets value of column - code.
	 *
     * @return code
     */
    public String getCode() {
        return code;
	}

	/**
	 * sets value of column - code.
	 *
	 * @param theCode
	 */
	public void setCode(final String theCode) {
		this.code = theCode;
	}
    /**
     * gets value of column - name.
	 *
     * @return name
     */
    public String getName() {
        return name;
	}

	/**
	 * sets value of column - name.
	 *
	 * @param theName
	 */
	public void setName(final String theName) {
		this.name = theName;
	}

}

