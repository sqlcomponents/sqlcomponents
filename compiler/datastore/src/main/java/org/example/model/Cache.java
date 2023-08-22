package org.example.model;

 /**
  * Data Holder for the table - cache.
  */
public final class Cache {

    /**
     * holds value of the column code.
     */
    private String code;
    /**
     * holds value of the column cache.
     */
    private String cache;
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
     * gets value of column - cache.
	 *
     * @return cache
     */
    public String getCache() {
        return cache;
	}

	/**
	 * sets value of column - cache.
	 *
	 * @param theCache
	 */
	public void setCache(final String theCache) {
		this.cache = theCache;
	}

}

