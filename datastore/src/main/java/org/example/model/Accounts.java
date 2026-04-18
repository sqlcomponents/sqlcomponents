 package org.example.model;

 /**
  * Data Holder for the table - accounts.
    *@param id Long.
    *@param name String.
    *@param balance Double.
 */
public record Accounts(
    Long id,

    String name,

    Double balance
    ) {


    /**
    * gets value of column - theId.
    * @param theId
    * @return theId
    */
    public Accounts withId(final Long theId) {
        return new Accounts(
                theId,
                name,
                balance);
    }


    /**
    * gets value of column - theName.
    * @param theName
    * @return theName
    */
    public Accounts withName(final String theName) {
        return new Accounts(
                id,
                theName,
                balance);
    }


    /**
    * gets value of column - theBalance.
    * @param theBalance
    * @return theBalance
    */
    public Accounts withBalance(final Double theBalance) {
        return new Accounts(
                id,
                name,
                theBalance);
    }

    }




