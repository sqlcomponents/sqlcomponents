
package org.example.store;

import java.sql.ResultSet;
import javax.sql.DataSource;
import java.sql.SQLException;

import java.util.List;
import java.util.stream.Collectors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.example.DataManager;
import org.example.DataManager.Value;
import org.example.model.Movie;

    /**
    * Datastore for the table - movie.
    */
    public final class MovieStore  {

    /**
    * Retrieves an instance of MovieStore.
    *
    * @param theDataManager  The DataManager instance.
    * @param theObserver     The observer to notify for data changes.
    * @return an instance of MovieStore
    */
    public static MovieStore getMovieStore(
    final DataManager theDataManager
    ,final DataManager.Observer theObserver

) {
    return new MovieStore(
    theDataManager
    ,theObserver

);

    }

    private final DataManager dataManager;

    private final DataManager.Observer observer;



    /**
    * Constructor for MovieStore.
    *
    * @param theDataManager   The DataManager instance.
    * @param theObserver      The observer for data changes.
    */
    private MovieStore(
    final DataManager theDataManager
    ,final DataManager.Observer theObserver


    ) {

    this.dataManager = theDataManager;
    this.observer = theObserver;



    }

 





	public InsertStatement insert() {
        return new InsertStatement(
);
    }

    public InsertStatement insert(
            
            final Column.TitleColumn titleColumn 
            ) {
        return new InsertStatement(
        
        );
    }
    public final class InsertStatement {
    

        private InsertStatement(
                ) {


        }

        private void prepare(final DataManager.SqlBuilder sqlBuilder,final Movie movie) {
            
                title(movie.title()).set(sqlBuilder);
          
            
                directedBy(movie.directedBy()).set(sqlBuilder);
          
        }

        private void prepare(final DataManager.SqlBuilder sqlBuilder,final List<Movie> movies) {
            for ( Movie movie : movies) {
                prepare(sqlBuilder, movie);
            }
        }

        public ValueClause values(final Movie movie) {
            return new ValueClause(movie);
        }

        public ValuesClause values(final Movie... movies) {
            return new ValuesClause(Arrays.asList(movies));
        }

        public ValuesClause values(final List<Movie> movies) {
            return new ValuesClause(movies);
        }

        public final class ValueClause  {

            private final Movie movie;

            ValueClause(final Movie movie) {
                this.movie = movie;
            }
            

            public int execute(final DataSource dataSource) throws SQLException  {
                final String query = "INSERT INTO movie ( title,directed_by ) VALUES ( ? ,? )";

                DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);

                prepare(sqlBuilder,movie);

                return sqlBuilder.execute(dataSource);
            }


            public Movie returning(final DataSource dataSource) throws SQLException 
  {

                Movie insertedMovie = null;
                
                final String query =  "INSERT INTO movie ( title,directed_by ) VALUES ( ? ,? ) returning id ";
                
                DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
                prepare(sqlBuilder,movie);
  
                      insertedMovie = sqlBuilder.queryForOne(resultSet -> rowMapperForReturning(resultSet,movie)).execute(dataSource);
        


                
                return insertedMovie;
            }
        }

        public final class ValuesClause  {

            private final List<Movie> movies;

            ValuesClause (final List<Movie> movies) {
                this.movies = movies;
            }

            public int execute(final DataSource dataSource) throws SQLException  {
                String query = "INSERT INTO movie ( title,directed_by ) VALUES ( ? ,? )";

                if (movies.size() > 1) {
                    for (int i = 1; i < movies.size() ; i++) {
                        query += " , ( ? ,? ) ";
                    }
                }

                DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
                prepare(sqlBuilder,movies);

                return sqlBuilder.execute(dataSource);

            }


            public List<Movie> returning(final DataSource dataSource) throws SQLException 
  {
                List<Movie> insertedMovies = null;
                String query = "INSERT INTO movie ( title,directed_by ) VALUES ( ? ,? )";

                if (movies.size() > 1) {
          for (int i = 1; i < movies.size() ; i++) {
              query += " , ( ? ,? ) ";
          }
      }

      query += " returning id ";

                DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
                prepare(sqlBuilder,movies);




                            java.util.concurrent.atomic.AtomicInteger atomicInteger =  new java.util.concurrent.atomic.AtomicInteger(0);

                            insertedMovies = sqlBuilder
              .queryForList(resultSet -> rowMapperForReturning(resultSet, movies.get(atomicInteger.getAndIncrement()))).execute(dataSource); 

                                


                
                return insertedMovies;
            }
        }


    }


	public UpdateStatement update() {
        return new UpdateStatement(       
        );
    }

    public final class UpdateStatement {
        



        private UpdateStatement(
                ) {
  
            
        }

        private void prepare(final DataManager.SqlBuilder sqlBuilder,final Movie movie) throws SQLException {
                
                    title(movie.title()).set(sqlBuilder);
                
                
                    directedBy(movie.directedBy()).set(sqlBuilder);
                
        }


        public SetByPKClause set(final Movie movie) {
            return new SetByPKClause(movie);
        }

        public final class SetByPKClause  {
    
                private WhereClause whereClause;
                private final Movie movie;

                SetByPKClause(final Movie movie) {
                    this.movie = movie;
                }

                public SetByPKClause where(final WhereClause whereClause) {
                    this.whereClause = whereClause;
                    return this;
                }


                public int execute(final DataSource dataSource) throws SQLException  {
                
  final String query ="UPDATE movie SET title = ? ,directed_by = ?"
+ ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );

                    DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
                    prepare(sqlBuilder,movie);
                    return sqlBuilder.execute(dataSource);
                }


                public final Movie returning(final DataSource dataSource) throws SQLException 
  {
                    Movie updatedMovie = null ;
  final String query ="UPDATE movie SET title = ? ,directed_by = ?"
+ ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
                    DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
                    prepare(sqlBuilder,movie);
         
                        
                        if( sqlBuilder.execute(dataSource) == 1 ) {
                        
  
                        updatedMovie =  select(dataSource,movie.id()).get();
                    }
                    
                    return updatedMovie;
                }
            }

        public SetClause set(final Value<?,?>... values) {
            return new SetClause(values);
        }

        public final class SetClause  {
            
            private final Value<?,?>[] values;
        

            SetClause(final Value<?,?>[] values) {
                this.values = values;
            }

            public SetWhereClause where(WhereClause whereClause) {
                return new SetWhereClause(this, whereClause);
            } 

            public final class SetWhereClause  {
                private final SetClause setClause;
                private final WhereClause whereClause;

                SetWhereClause(final SetClause setClause, WhereClause whereClause) {
                    this.setClause = setClause;
                    this.whereClause = whereClause;
                }

                private String getSetValues() {
                    StringBuilder stringBuilder = new StringBuilder();
                    boolean isFirst = true;
                    for (Value<?,?> value:
                            this.setClause.values) {
                        if(isFirst) {
                            isFirst = false;
                        } else {
                            stringBuilder.append(",");
                        }
                        stringBuilder.append(value.column().name()).append("=?");
                    }
                    return stringBuilder.toString();
                }
                
                public int execute(final DataSource dataSource) throws SQLException  {
                    
  final String query = "UPDATE movie SET "+ getSetValues()+ ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );

                    DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);

                    for (Value<?,?> value:values) {
                        value.set(sqlBuilder);
                    }

                    return sqlBuilder.execute(dataSource);
                }

                public List<Movie> returning(final DataSource dataSource) throws SQLException 
  {
                    return null;
                }
            }
            
        }
        
    public DataManager.Statement<Value<?,?>> sql(final String sql) {
        return new DataManager.Statement<>(sql);
    }



    }	



public SingleSelectStatementWithWhere select(Short id) {
        return new SingleSelectStatementWithWhere(id);
}

public final class SingleSelectStatementWithWhere extends SingleSelectStatement{

        private final Short id;


        private SingleSelectStatementWithWhere(Short id) {
            super(id);
            this.id = id;

        }

        public SingleSelectStatement where(WhereClause whereClause) {
            return new SingleSelectStatement(whereClause,id);
        }
}

public sealed class SingleSelectStatement implements DataManager.Sql<Movie> permits SingleSelectStatementWithWhere {


        private final Short id;


        private final WhereClause whereClause;

        private SingleSelectStatement(Short id) {
            this(null,id);
        }

        private SingleSelectStatement(final WhereClause whereClause, Short id) {
            this.whereClause = whereClause;
            this.id = id;

        }

        @Override
        public final Movie execute(final Connection connection) throws SQLException 
 {
            
		final String query = "SELECT id,title,directed_by FROM movie WHERE id = ?"

                + ( whereClause == null ? "" : (" AND " + whereClause.asSql()) );
                DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
    
        id(id).set(sqlBuilder);
	

        return sqlBuilder.queryForOne(MovieStore.this::rowMapper).execute(connection);
	}

        


        
}


public SelectStatementWithWhere select() {
        return new SelectStatementWithWhere();
}

public final class SelectStatementWithWhere extends SelectStatement{

        private SelectStatementWithWhere() {
            super(null);
        }

        public SelectStatement where(WhereClause whereClause) {
            return new SelectStatement(whereClause);
        }
}

public sealed class SelectStatement implements DataManager.Sql<List<Movie>> permits SelectStatementWithWhere {

        private final WhereClause whereClause;

        private LimitClause limitClause;
        private LimitClause.OffsetClause offsetClause;


        public LimitClause limit(final int limit) {
                return new LimitClause(limit);
        }

        private SelectStatement() {
            this(null);
        }

        private SelectStatement(final WhereClause whereClause) {
            this.whereClause = whereClause;
        }

        @Override
        public final List<Movie> execute(final Connection connection) throws SQLException 
 {
            
		final String query = "SELECT id,title,directed_by FROM movie" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) )
                + ( this.limitClause == null ? "" : this.limitClause.asSql() )
                + ( this.offsetClause == null ? "" : this.offsetClause.asSql() );
                return dataManager.sql(query).queryForList(MovieStore.this::rowMapper).execute(connection);
	}

        public final int count(final DataSource dataSource) throws SQLException {
		final String query = "SELECT COUNT(id) FROM movie" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
                return dataManager.sql(query).queryForInt().execute(dataSource);
	}


    


        public final class LimitClause  {

                private final String asSql;

                private LimitClause(final int limit) {
                        asSql = " LIMIT " + limit;

                        limitClause = this;
                }

                private String asSql() {
                        return asSql ;
                }

                public OffsetClause offset(final int offset) {
                        return new OffsetClause(this,offset);
                }

                public DataManager.Page<Movie> execute(final DataSource dataSource) throws SQLException 
 {
                    return DataManager.page(SelectStatement.this.execute(dataSource), count(dataSource));
                }

                public final class OffsetClause  {
                        private final LimitClause limitClause;
                        private final String asSql;

                        private OffsetClause(final LimitClause limitClause,final int offset) {
                                this.limitClause = limitClause;
                                asSql = " OFFSET " + offset;

                                offsetClause = this;
                        }

                        private String asSql() {
                                return asSql ;
                        }

                        public DataManager.Page<Movie> execute(final DataSource dataSource) throws SQLException 
 {
                                return this.limitClause.execute(dataSource);
                        }


        }

        }


public final DataManager.SelectQuery<Value<?,?>,Movie> sql(final String sql) {
            return new DataManager.SelectQuery<>(sql, MovieStore.this::rowMapper);
    }




}



    






    public DataManager.DeleteStatement delete() {
        return new DataManager.DeleteStatement("DELETE FROM movie");
    }

 

    /**
    * Maps a row from ResultSet for returning properties.
    *
    * @param rs The ResultSet.
    * @param insertingMovie The inserting Movie instance.
    * @return A new Movie object.
    * @throws SQLException if any SQL error occurs.
    */
    private Movie rowMapperForReturning(final ResultSet rs,final Movie insertingMovie) throws SQLException 
{
    return new Movie(


        id().get(rs,1)
        
    ,

        insertingMovie.title()
        
    ,

        insertingMovie.directedBy()
        
    );
    
    }

    /**
    * Maps a row from ResultSet.
    *
    * @param rs The ResultSet.
    * @return A new Movie object.
    * @throws SQLException if any SQL error occurs.
    */
    private Movie rowMapper(ResultSet rs) throws SQLException 
 {
    return new Movie(
        id().get(rs,1)
    ,
        title().get(rs,2)
    ,
        directedBy().get(rs,3)
    );
    
    }



        /**
        * Creates a Value for id.
        *
        * @param value The value of type Short.
        * @return A Value object.
        */
        public static Value<Column.IdColumn,Short> id(final Short value) {
        return new Value<>(id(),value);
        }

        /**
        * Retrieves the column for id.
        *
        * @return The column for id.
        */
        public static Column.IdColumn id() {
            return new WhereClause().id();
        }

        /**
        * Creates a Value for title.
        *
        * @param value The value of type String.
        * @return A Value object.
        */
        public static Value<Column.TitleColumn,String> title(final String value) {
        return new Value<>(title(),value);
        }

        /**
        * Retrieves the column for title.
        *
        * @return The column for title.
        */
        public static Column.TitleColumn title() {
            return new WhereClause().title();
        }

        /**
        * Creates a Value for directedBy.
        *
        * @param value The value of type String.
        * @return A Value object.
        */
        public static Value<Column.DirectedByColumn,String> directedBy(final String value) {
        return new Value<>(directedBy(),value);
        }

        /**
        * Retrieves the column for directedBy.
        *
        * @return The column for directedBy.
        */
        public static Column.DirectedByColumn directedBy() {
            return new WhereClause().directedBy();
        }


    /**
     * Class for building the SQL WhereClause.
     */
    public static class WhereClause extends PartialWhereClause implements DataManager.WhereClause {
        private WhereClause() {
            super();
        }

        @Override
        public String asSql() {
            return nodes.isEmpty() ? null : nodes.stream().map(node -> {
                if (node instanceof Column) {
                    return ((Column) node).asSql();
                } else if (node instanceof WhereClause) {
                    return "(" + ((WhereClause) node).asSql() + ")";
                } else {
                    return (String) node;
                }
            }).collect(Collectors.joining(" "));
        }

        /**
        * Adds "AND" to the clause.
        *
        * @return This PartialWhereClause instance.
        */
        public PartialWhereClause and() {
            this.nodes.add("AND");
            return this;
        }

        /**
        * Adds "OR" to the clause.
        *
        * @return This PartialWhereClause instance.
        */
        public PartialWhereClause or() {
            this.nodes.add("OR");
            return this;
        }

        /**
        * Adds "AND" followed by the given WhereClause.
        *
        * @param whereClause The WhereClause to add.
        * @return This WhereClause instance.
        */
        public WhereClause and(final WhereClause whereClause) {
            this.nodes.add("AND");
            this.nodes.add(whereClause);
            return this;
        }

        /**
        * Adds "OR" followed by the given WhereClause.
        *
        * @param whereClause The WhereClause to add.
        * @return This WhereClause instance.
        */
        public WhereClause or(final WhereClause whereClause) {
            this.nodes.add("OR");
            this.nodes.add(whereClause);
            return this;
        }

    }

    /**
    * Partial SQL WhereClause.
    */
    public static class PartialWhereClause  {

        protected final List<Object> nodes;

        private PartialWhereClause() {
            this.nodes = new ArrayList<>();
        }


        
        /**
        * Adds id to the SQL clause.
        *
        * @return The column for id.
        */
        public Column.IdColumn id() {
        Column.IdColumn query = new Column.IdColumn(this);
        this.nodes.add(query);
        return query;
        }

        
        /**
        * Adds title to the SQL clause.
        *
        * @return The column for title.
        */
        public Column.TitleColumn title() {
        Column.TitleColumn query = new Column.TitleColumn(this);
        this.nodes.add(query);
        return query;
        }

        
        /**
        * Adds directedBy to the SQL clause.
        *
        * @return The column for directedBy.
        */
        public Column.DirectedByColumn directedBy() {
        Column.DirectedByColumn query = new Column.DirectedByColumn(this);
        this.nodes.add(query);
        return query;
        }


    }

    /**
    * Abstract class for defining columns in the DataManager.
    */
    public static abstract class Column<T> implements DataManager.Column<T> {

    private final PartialWhereClause  whereClause ;

    protected Column(final PartialWhereClause  whereClause) {
    this.whereClause  = whereClause ;
    }

    protected WhereClause  getWhereClause() {
    return (WhereClause) whereClause ;
    }


    public static class IdColumn extends Column<Short> {
    private String sql;

    public IdColumn(final PartialWhereClause  whereClause) {
    super(whereClause);
    }

    public String name() {
    return "id";
    }

    public final WhereClause isNull() {
    sql = "id IS NULL";
    return getWhereClause();
    }

    public final WhereClause isNotNull() {
    sql = "id IS NOT NULL";
    return getWhereClause();
    }



    public void set(final DataManager.SqlBuilder preparedStatement, final Short value) {
    if(value == null) {
        preparedStatement.paramNull(5,"smallserial" );
    } else {
        preparedStatement.param(value);
    }
        

    
    }

    @Override
    public Short get(final ResultSet resultSet, final int i) throws SQLException {
        return resultSet.getObject(i) == null ? null : resultSet.getShort(i);
    }

    public final WhereClause eq(final Short value) {
    sql = "id =" + value;
    return getWhereClause();
    }

    public final WhereClause gt(final Short value) {
    sql = "id >" + value;
    return getWhereClause();
    }

    public final WhereClause  gte(final Short value) {
    sql = "id >=" + value;
    return getWhereClause();
    }

    public final WhereClause  lt(final Short value) {
    sql = "id <" + value;
    return getWhereClause();
    }

    public final WhereClause  lte(final Short value) {
    sql = "id <=" + value;
    return getWhereClause();
    }




    @Override
    public String asSql() {
    return sql;
    }

    public boolean validate(Short value) {
    return true;
    }

    }
    public static class TitleColumn extends Column<String> {
    private String sql;

    public TitleColumn(final PartialWhereClause  whereClause) {
    super(whereClause);
    }

    public String name() {
    return "title";
    }

    public final WhereClause isNull() {
    sql = "title IS NULL";
    return getWhereClause();
    }

    public final WhereClause isNotNull() {
    sql = "title IS NOT NULL";
    return getWhereClause();
    }


    public void set(final DataManager.SqlBuilder preparedStatement, final String value) {
    preparedStatement.param(value);
    }

    @Override
    public String get(final ResultSet resultSet, final int i) throws SQLException {
        return resultSet.getString(i);
    }

    public final WhereClause  eq(final String value) {
    sql = "title ='" + value + "'";
    return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
    sql = "title LIKE '" + value + "'";
    return getWhereClause();
    }

    @Override
    public String asSql() {
    return sql;
    }

    public boolean validate(String value) {
    return true;
    }

    }
    public static class DirectedByColumn extends Column<String> {
    private String sql;

    public DirectedByColumn(final PartialWhereClause  whereClause) {
    super(whereClause);
    }

    public String name() {
    return "directed_by";
    }

    public final WhereClause isNull() {
    sql = "directed_by IS NULL";
    return getWhereClause();
    }

    public final WhereClause isNotNull() {
    sql = "directed_by IS NOT NULL";
    return getWhereClause();
    }


    public void set(final DataManager.SqlBuilder preparedStatement, final String value) {
    preparedStatement.param(value);
    }

    @Override
    public String get(final ResultSet resultSet, final int i) throws SQLException {
        return resultSet.getString(i);
    }

    public final WhereClause  eq(final String value) {
    sql = "directed_by ='" + value + "'";
    return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
    sql = "directed_by LIKE '" + value + "'";
    return getWhereClause();
    }

    @Override
    public String asSql() {
    return sql;
    }

    public boolean validate(String value) {
    return true;
    }

    }

    }



    public Optional<Movie> select(final DataSource dataSource,Short id) throws SQLException 
  {
            return select(dataSource,id, null);
    }
    public Optional<Movie> select(final DataSource dataSource,Short id, WhereClause whereClause) throws SQLException 
  {
        
		final String query = "SELECT id,title,directed_by FROM movie WHERE id = ?"

                + ( whereClause == null ? "" : (" AND " + whereClause.asSql()) );

        DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
    
        id(id).set(sqlBuilder);
	

        return Optional.ofNullable(sqlBuilder.queryForOne(this::rowMapper).execute(dataSource));
            
    }
        
    public boolean exists(final DataSource dataSource,Short id) throws SQLException {
        final String query = "SELECT 1 FROM movie WHERE id = ?";
        DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);

        id(id).set(sqlBuilder);
	

		return sqlBuilder.queryForExists().execute(dataSource);
	}


public int delete(final DataSource dataSource,Short id) throws SQLException  {
		final String query = "DELETE FROM movie WHERE id = ?";
        DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
        id(id).set(sqlBuilder);
	
        return sqlBuilder.execute(dataSource);
}


    public Optional<Movie> selectById(final DataSource dataSource,Short id) throws SQLException 
 {
        
            final String query = "SELECT id,title,directed_by FROM movie WHERE id= ?";

        DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
    
        id(id).set(sqlBuilder);
	

        return Optional.ofNullable(sqlBuilder.queryForOne(this::rowMapper).execute(dataSource));

            
    }

    public boolean existsById(final DataSource dataSource,Short id) throws SQLException 
 {

            final String query = "SELECT 1 FROM movie WHERE id= ?";
            DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
    
        id(id).set(sqlBuilder);
	

        return sqlBuilder.queryForExists().execute(dataSource);
    }





    }
