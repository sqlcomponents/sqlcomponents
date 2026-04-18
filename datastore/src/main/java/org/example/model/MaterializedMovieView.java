 package org.example.model;

 /**
  * Data Holder for the table - materialized_movie_view.
    *@param id Short.
    *@param title String.
    *@param directedBy String.
 */
public record MaterializedMovieView(
    Short id,

    String title,

    String directedBy
    ) {


    /**
    * gets value of column - theId.
    * @param theId
    * @return theId
    */
    public MaterializedMovieView withId(final Short theId) {
        return new MaterializedMovieView(
                theId,
                title,
                directedBy);
    }


    /**
    * gets value of column - theTitle.
    * @param theTitle
    * @return theTitle
    */
    public MaterializedMovieView withTitle(final String theTitle) {
        return new MaterializedMovieView(
                id,
                theTitle,
                directedBy);
    }


    /**
    * gets value of column - theDirectedBy.
    * @param theDirectedBy
    * @return theDirectedBy
    */
    public MaterializedMovieView withDirectedBy(final String theDirectedBy) {
        return new MaterializedMovieView(
                id,
                title,
                theDirectedBy);
    }

    }




