
package org.example.store;

import java.sql.ResultSet;
import javax.sql.DataSource;
import java.sql.SQLException;

import java.util.List;
import java.util.stream.Collectors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.DataManager;
import org.example.DataManager.Value;
import org.example.model.MaterializedMovieView;

    /**
    * Datastore for the table - materialized_movie_view.
    */
    public final class MaterializedMovieViewStore  {

    /**
    * Retrieves an instance of MaterializedMovieViewStore.
    *
    * @param theDataManager  The DataManager instance.
    * @param theObserver     The observer to notify for data changes.
    * @return an instance of MaterializedMovieViewStore
    */
    public static MaterializedMovieViewStore getMaterializedMovieViewStore(
    final DataManager theDataManager
    ,final DataManager.Observer theObserver

) {
    return new MaterializedMovieViewStore(
    theDataManager
    ,theObserver

);

    }

    private final DataManager dataManager;

    private final DataManager.Observer observer;



    /**
    * Constructor for MaterializedMovieViewStore.
    *
    * @param theDataManager   The DataManager instance.
    * @param theObserver      The observer for data changes.
    */
    private MaterializedMovieViewStore(
    final DataManager theDataManager
    ,final DataManager.Observer theObserver


    ) {

    this.dataManager = theDataManager;
    this.observer = theObserver;



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

public sealed class SelectStatement implements DataManager.Sql<List<MaterializedMovieView>> permits SelectStatementWithWhere {

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
        public final List<MaterializedMovieView> execute(final Connection connection) throws SQLException 
 {
            
		final String query = "SELECT id,title,directed_by FROM materialized_movie_view" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) )
                + ( this.limitClause == null ? "" : this.limitClause.asSql() )
                + ( this.offsetClause == null ? "" : this.offsetClause.asSql() );
                return dataManager.sql(query).queryForList(MaterializedMovieViewStore.this::rowMapper).execute(connection);
	}

        public final int count(final DataSource dataSource) throws SQLException {
		final String query = "SELECT COUNT(*) FROM materialized_movie_view" 
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

                public DataManager.Page<MaterializedMovieView> execute(final DataSource dataSource) throws SQLException 
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

                        public DataManager.Page<MaterializedMovieView> execute(final DataSource dataSource) throws SQLException 
 {
                                return this.limitClause.execute(dataSource);
                        }


        }

        }


public final DataManager.SelectQuery<Value<?,?>,MaterializedMovieView> sql(final String sql) {
            return new DataManager.SelectQuery<>(sql, MaterializedMovieViewStore.this::rowMapper);
    }




}



    




    public void refresh(final DataSource dataSource) throws SQLException {
      final String query = "REFRESH MATERIALIZED VIEW materialized_movie_view";
      dataManager.sql(query).execute(dataSource);
	}

 

    /**
    * Maps a row from ResultSet.
    *
    * @param rs The ResultSet.
    * @return A new MaterializedMovieView object.
    * @throws SQLException if any SQL error occurs.
    */
    private MaterializedMovieView rowMapper(ResultSet rs) throws SQLException 
 {
    return new MaterializedMovieView(
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
        preparedStatement.paramNull(5,"int2" );
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








    }
