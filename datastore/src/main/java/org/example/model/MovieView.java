 package org.example.model;

 /**
  * Data Holder for the table - movie_view.
    *@param id Short.
    *@param title String.
    *@param directedBy String.
 */
public record MovieView(
    Short id,

    String title,

    String directedBy
    ) {


    /**
    * gets value of column - theId.
    * @param theId
    * @return theId
    */
    public MovieView withId(final Short theId) {
        return new MovieView(
                theId,
                title,
                directedBy);
    }


    /**
    * gets value of column - theTitle.
    * @param theTitle
    * @return theTitle
    */
    public MovieView withTitle(final String theTitle) {
        return new MovieView(
                id,
                theTitle,
                directedBy);
    }


    /**
    * gets value of column - theDirectedBy.
    * @param theDirectedBy
    * @return theDirectedBy
    */
    public MovieView withDirectedBy(final String theDirectedBy) {
        return new MovieView(
                id,
                title,
                theDirectedBy);
    }

    }




