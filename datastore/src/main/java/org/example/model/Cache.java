 package org.example.model;

import java.time.LocalDateTime;
 /**
  * Data Holder for the table - cache.
    *@param code String.
    *@param cache String.
    *@param createdBy String.
    *@param createdAt LocalDateTime.
    *@param modifiedBy String.
    *@param modifiedAt LocalDateTime.
 */
public record Cache(
    String code,

    String cache,

    String createdBy,

    LocalDateTime createdAt,

    String modifiedBy,

    LocalDateTime modifiedAt
    ) {


    /**
    * gets value of column - theCode.
    * @param theCode
    * @return theCode
    */
    public Cache withCode(final String theCode) {
        return new Cache(
                theCode,
                cache,
                createdBy,
                createdAt,
                modifiedBy,
                modifiedAt);
    }


    /**
    * gets value of column - theCache.
    * @param theCache
    * @return theCache
    */
    public Cache withCache(final String theCache) {
        return new Cache(
                code,
                theCache,
                createdBy,
                createdAt,
                modifiedBy,
                modifiedAt);
    }


    /**
    * gets value of column - theCreatedBy.
    * @param theCreatedBy
    * @return theCreatedBy
    */
    public Cache withCreatedBy(final String theCreatedBy) {
        return new Cache(
                code,
                cache,
                theCreatedBy,
                createdAt,
                modifiedBy,
                modifiedAt);
    }


    /**
    * gets value of column - theCreatedAt.
    * @param theCreatedAt
    * @return theCreatedAt
    */
    public Cache withCreatedAt(final LocalDateTime theCreatedAt) {
        return new Cache(
                code,
                cache,
                createdBy,
                theCreatedAt,
                modifiedBy,
                modifiedAt);
    }


    /**
    * gets value of column - theModifiedBy.
    * @param theModifiedBy
    * @return theModifiedBy
    */
    public Cache withModifiedBy(final String theModifiedBy) {
        return new Cache(
                code,
                cache,
                createdBy,
                createdAt,
                theModifiedBy,
                modifiedAt);
    }


    /**
    * gets value of column - theModifiedAt.
    * @param theModifiedAt
    * @return theModifiedAt
    */
    public Cache withModifiedAt(final LocalDateTime theModifiedAt) {
        return new Cache(
                code,
                cache,
                createdBy,
                createdAt,
                modifiedBy,
                theModifiedAt);
    }

    }




