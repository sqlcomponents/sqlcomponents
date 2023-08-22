
package org.example.store;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.stream.Collectors;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.example.model.Cache;
import org.example.RajaManager;

/**
 * Datastore for the table - cache.
 */
public final class CacheStore  {

    private final javax.sql.DataSource dbDataSource;

    private final RajaManager.Observer observer;




    /**
     * Datastore
     */
    public CacheStore(final javax.sql.DataSource theDataSource
                ,final RajaManager.Observer theObserver

                ) {
        this.dbDataSource = theDataSource;
        this.observer = theObserver;
    }


public SelectStatement select() {
        return new SelectStatement(this);
}
public SelectStatement select(WhereClause whereClause) throws SQLException  {
        return new SelectStatement(this,whereClause);
}

public static final class SelectStatement {

        private final CacheStore cacheStore;
        private final WhereClause whereClause;

        private LimitClause limitClause;
        private LimitClause.OffsetClause offsetClause;

        public LimitClause limit(final int limit) {
                return new LimitClause(this,limit);
        }

        private SelectStatement(final CacheStore cacheStore) {
            this(cacheStore,null);
        }

        private SelectStatement(final CacheStore cacheStore
                ,final WhereClause whereClause) {
            this.cacheStore = cacheStore;
            this.whereClause = whereClause;
        }

        public List<Cache> execute() throws SQLException {
		final String query = " SELECT code,\"cache\" FROM \"cache\"" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) )
                + ( this.limitClause == null ? "" : this.limitClause.asSql() )
                + ( this.offsetClause == null ? "" : this.offsetClause.asSql() );
                try (java.sql.Connection dbConnection = this.cacheStore.dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                
                ResultSet resultSet = preparedStatement.executeQuery();
                                List<Cache> arrays = new ArrayList();
                while (resultSet.next()) {
                                        arrays.add(this.cacheStore.rowMapper(resultSet));
                                }
                                return arrays;
                } 
	}

        public int count() throws SQLException {
                int count = 0;
		final String query = " SELECT COUNT(*) FROM \"cache\"" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
                try (java.sql.Connection dbConnection = this.cacheStore.dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                
                ResultSet resultSet = preparedStatement.executeQuery();
                                List<Cache> arrays = new ArrayList();
                while (resultSet.next()) {
                                        count = resultSet.getInt(1);
                                }
                                return count;
                } 
	}



        public static final class LimitClause  {
                private final SelectStatement selectStatement;
                private final String asSql;

                private LimitClause(final SelectStatement selectStatement,final int limit) {
                        this.selectStatement = selectStatement;
                        asSql = " LIMIT " + limit;

                        this.selectStatement.limitClause = this;
                }

                private String asSql() {
                        return asSql ;
                }

                public OffsetClause offset(final int offset) {
                        return new OffsetClause(this,offset);
                }

                public RajaManager.Page<Cache> execute() throws SQLException {
                    return RajaManager.page(this.selectStatement.execute(), selectStatement.count());
                }

                public static final class OffsetClause  {
                        private final LimitClause limitClause;
                        private final String asSql;

                        private OffsetClause(final LimitClause limitClause,final int offset) {
                                this.limitClause = limitClause;
                                asSql = " OFFSET " + offset;

                                this.limitClause.selectStatement.offsetClause = this;
                        }

                        private String asSql() {
                                return asSql ;
                        }

                        public RajaManager.Page<Cache> execute() throws SQLException {
                                return this.limitClause.execute();
                        }


        }

        }
}



    










	public final InsertStatement insert() {
        return new InsertStatement(this
);
    }

    public static final class InsertStatement {
        private final CacheStore cacheStore;



        private InsertStatement(final CacheStore cacheStore
                ) {
            this.cacheStore = cacheStore;
        }

        private void prepare(final PreparedStatement preparedStatement,final Cache cache) throws SQLException {
                preparedStatement.setString(1,cache.getCode());
                preparedStatement.setString(2,cache.getCache());
        }

        public final ValueClause values(final Cache cache) {
            return new ValueClause(cache,this);
        }

        public final ValuesClause values(final List<Cache> listOfCache) {
            return new ValuesClause(listOfCache,this);
        }

        public static final class ValueClause  {
            
            private final InsertStatement insertStatement;

            private Cache cache;

            ValueClause (final Cache cache,final InsertStatement insertStatement) {
                this.cache = cache;
                this.insertStatement = insertStatement;
            }

            public final ValuesClause values(final Cache theCache) {
                List<Cache> listOfCache = new ArrayList<>();
                listOfCache.add(cache);
                listOfCache.add(theCache);
                return new ValuesClause(listOfCache,insertStatement);
            }
            

            public final int execute() throws SQLException  {
                int insertedRows = 0;
  final String query = "INSERT INTO \"cache\" ( code ,\"cache\" ) VALUES ( ? ,? ) ";

                try (java.sql.Connection dbConnection = insertStatement.cacheStore.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    this.insertStatement.prepare(preparedStatement,cache);
                    insertedRows = preparedStatement.executeUpdate();
                }
                return insertedRows;
            }

        }

        public static final class ValuesClause  {

            
            private final InsertStatement insertStatement;

            private List<Cache> listOfCache;

            ValuesClause(final List<Cache> theListOfCache,final InsertStatement insertStatement) {
                this.listOfCache = theListOfCache;
                this.insertStatement = insertStatement;
            }

            public final ValuesClause values(final Cache theCache) {
                this.listOfCache.add(theCache);
                return this;
            }

            public final int[] execute() throws SQLException  {
                int[] insertedRows = null;
  final String query = "INSERT INTO \"cache\" ( code ,\"cache\" ) VALUES ( ? ,? ) ";
                
                try (java.sql.Connection dbConnection = insertStatement.cacheStore.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    for (Cache cache:listOfCache) {
                        this.insertStatement.prepare(preparedStatement, cache);
                        preparedStatement.addBatch();
                    }
                    insertedRows = preparedStatement.executeBatch();
                }
                return insertedRows;
            }


        }
    }


public DeleteStatement delete(WhereClause whereClause) {
    return new DeleteStatement(this,whereClause);
}

public DeleteStatement delete() {
    return new DeleteStatement(this,null);
}

public static final class DeleteStatement {

    private final CacheStore cacheStore;
    private final WhereClause whereClause;

    private DeleteStatement(final CacheStore cacheStore) {
            this(cacheStore,null);
        }

        private DeleteStatement(final CacheStore cacheStore
                ,final WhereClause whereClause) {
            this.cacheStore = cacheStore;
            this.whereClause = whereClause;
        }

    public int execute() throws SQLException  {
    	final String query = "DELETE FROM \"cache\"" 
        + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
        try (java.sql.Connection dbConnection = this.cacheStore.dbDataSource.getConnection();
			Statement statement = dbConnection.createStatement()) {
            return statement.executeUpdate(query);
        }
	}
}





public int update(Cache cache) throws SQLException {
        final String query = " UPDATE \"cache\" SET code = ? ,\"cache\" = ? WHERE";

		try (java.sql.Connection dbConnection = dbDataSource.getConnection();
             PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
        {

            preparedStatement.setString(1,cache.getCode());

            preparedStatement.setString(2,cache.getCache());



			return preparedStatement.executeUpdate();
        }
	}

	public final UpdateStatement update() {
        return new UpdateStatement(this
                                               
        );
    }

    public static final class UpdateStatement {
        private final CacheStore cacheStore;


        private UpdateStatement(final CacheStore cacheStore
                ) {
            this.cacheStore = cacheStore;
        }

        private void prepare(final PreparedStatement preparedStatement,final Cache cache) throws SQLException {
                    preparedStatement.setString(1,cache.getCode());
                    preparedStatement.setString(2,cache.getCache());

        }


        public SetByPKClause set(final Cache cache) {
            return new SetByPKClause(cacheStore.dbDataSource,cache,this);
        }

        public static final class SetByPKClause  {
                private final javax.sql.DataSource dbDataSource;
                private final UpdateStatement updateStatement;
                private Cache cache;

                SetByPKClause(final javax.sql.DataSource dbDataSource,final Cache cache,final UpdateStatement updateStatement) {
                    this.dbDataSource = dbDataSource;
                    this.cache = cache;
                    this.updateStatement = updateStatement;
                }

                public final int execute() throws SQLException  {
                    int updtedRows = 0;
  final String query = " UPDATE \"cache\" SET code = ? ,\"cache\" = ? WHERE";

                    try (java.sql.Connection dbConnection = dbDataSource.getConnection();
                        PreparedStatement preparedStatement = dbConnection.prepareStatement(query)){

                        this.updateStatement.prepare(preparedStatement,cache);

                        updtedRows = preparedStatement.executeUpdate();
                    }
                    return updtedRows;
                }

            }

        public SetClause set(final Value... values) {
            return new SetClause(values,this);
        }

        public static final class SetClause  {
            
            private final UpdateStatement updateStatement;

            private Cache cache;

            private Value[] values;

            SetClause(final Value[] values,final UpdateStatement updateStatement) {
                this.values = values;
                this.updateStatement = updateStatement;
            }

            SetClause(final Cache cache,final UpdateStatement updateStatement) {
                this.cache = cache;
                this.updateStatement = updateStatement;
            }

            public SetWhereClause where(WhereClause whereClause) {
                return new SetWhereClause(this);
            }

            

            public static final class SetWhereClause  {
                private final SetClause setClause;

                SetWhereClause(final SetClause setClause) {
                    this.setClause = setClause;
                }

                public final int execute() throws SQLException  {
                    return 0;
                }

                public final List<Cache> returning() throws SQLException  {
                    return null;
                }
            }


            

            
        }




    }	






  private Cache rowMapperForReturning(final ResultSet rs,final Cache insertingCache) throws SQLException {
    final Cache cache = new Cache();
        cache.setCode(insertingCache.getCode());
        cache.setCache(insertingCache.getCache());
        return cache;
    }

	private Cache rowMapper(ResultSet rs) throws SQLException {
        final Cache cache = new Cache();
          cache.setCode(rs.getString(1));
          cache.setCache(rs.getString(2));
        return cache;
    }




    public static Value<Column.CodeColumn,String> code(final String value) {
        return new Value<>(code(),value);
    }
    
    public static Column.CodeColumn code() {
        return new WhereClause().code();
    }


    public static Value<Column.CacheColumn,String> cache(final String value) {
        return new Value<>(cache(),value);
    }
    
    public static Column.CacheColumn cache() {
        return new WhereClause().cache();
    }


    public static class WhereClause  extends PartialWhereClause  {
        private WhereClause(){
        }
        private String asSql() {
            return nodes.isEmpty() ? null : nodes.stream().map(node -> {
                String asSql;
                if (node instanceof Column) {
                    asSql = ((Column) node).asSql();
                } else if (node instanceof WhereClause) {
                    asSql = "(" + ((WhereClause) node).asSql() + ")";
                } else {
                    asSql = (String) node;
                }
                return asSql;
            }).collect(Collectors.joining(" "));
        }

        public PartialWhereClause and() {
            this.nodes.add("AND");
            return this;
        }

        public PartialWhereClause  or() {
            this.nodes.add("OR");
            return this;
        }

        public WhereClause  and(final WhereClause  whereClause) {
            this.nodes.add("AND");
            this.nodes.add(whereClause);
            return (WhereClause) this;
        }

        public WhereClause  or(final WhereClause  whereClause) {
            this.nodes.add("OR");
            this.nodes.add(whereClause);
            return (WhereClause) this;
        }
    }

    public static class PartialWhereClause  {

        protected final List<Object> nodes;

        private PartialWhereClause() {
            this.nodes = new ArrayList<>();
        }

        public Column.CodeColumn code() {
            Column.CodeColumn query = new Column.CodeColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.CacheColumn cache() {
            Column.CacheColumn query = new Column.CacheColumn(this);
            this.nodes.add(query);
            return query;
        }


       

        

    }

    public static class Value<T extends Column<R>,R> {
        private final T column;
        private final R value;

        private Value(final T column,final R value) {
            this.column =column;
            this.value = value;
        }
    }


    public static abstract class Column<T> {

  
            private final PartialWhereClause  whereClause ;

            protected Column(final PartialWhereClause  whereClause) {
                this.whereClause  = whereClause ;
            }

            protected WhereClause  getWhereClause() {
                return (WhereClause) whereClause ;
            }

            protected abstract String asSql();

            protected abstract Boolean validate(T value);

public static class CodeColumn extends Column<String> {
    private String sql;

    public CodeColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "code ='" + value + "'";
        return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
        sql = "code LIKE '" + value + "'";
        return getWhereClause();
    }
    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(String value) {
        return true;
    }
}
public static class CacheColumn extends Column<String> {
    private String sql;

    public CacheColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "\"cache\" ='" + value + "'";
        return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
        sql = "\"cache\" LIKE '" + value + "'";
        return getWhereClause();
    }
    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(String value) {
        return true;
    }
}

        }
    

}
