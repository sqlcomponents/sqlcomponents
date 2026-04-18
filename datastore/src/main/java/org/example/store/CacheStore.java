
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.example.DataManager;
import org.example.DataManager.Value;
import org.example.model.Cache;

    /**
    * Datastore for the table - cache.
    */
    public final class CacheStore  {

    /**
    * Retrieves an instance of CacheStore.
    *
    * @param theDataManager  The DataManager instance.
    * @param theObserver     The observer to notify for data changes.
    * @return an instance of CacheStore
    */
    public static CacheStore getCacheStore(
    final DataManager theDataManager
    ,final DataManager.Observer theObserver

) {
    return new CacheStore(
    theDataManager
    ,theObserver

);

    }

    private final DataManager dataManager;

    private final DataManager.Observer observer;



    /**
    * Constructor for CacheStore.
    *
    * @param theDataManager   The DataManager instance.
    * @param theObserver      The observer for data changes.
    */
    private CacheStore(
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

    public final class InsertStatement {
    

        private InsertStatement(
                ) {


        }

        private void prepare(final DataManager.SqlBuilder sqlBuilder,final Cache cache) {
            
                code(cache.code()).set(sqlBuilder);
          
            
                cache(cache.cache()).set(sqlBuilder);
          
            
                createdBy(cache.createdBy()).set(sqlBuilder);
          
        }

        private void prepare(final DataManager.SqlBuilder sqlBuilder,final List<Cache> caches) {
            for ( Cache cache : caches) {
                prepare(sqlBuilder, cache);
            }
        }

        public ValueClause values(final Cache cache) {
            return new ValueClause(cache);
        }

        public ValuesClause values(final Cache... caches) {
            return new ValuesClause(Arrays.asList(caches));
        }

        public ValuesClause values(final List<Cache> caches) {
            return new ValuesClause(caches);
        }

        public final class ValueClause  {

            private final Cache cache;

            ValueClause(final Cache cache) {
                this.cache = cache;
            }
            

            public int execute(final DataSource dataSource) throws SQLException  {
                final String query = "INSERT INTO \"cache\" ( code,\"cache\",created_by ) VALUES ( ? ,? ,? )";

                DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);

                prepare(sqlBuilder,cache);

                return sqlBuilder.execute(dataSource);
            }

        }

        public final class ValuesClause  {

            private final List<Cache> caches;

            ValuesClause (final List<Cache> caches) {
                this.caches = caches;
            }

            public int execute(final DataSource dataSource) throws SQLException  {
                String query = "INSERT INTO \"cache\" ( code,\"cache\",created_by ) VALUES ( ? ,? ,? )";

                if (caches.size() > 1) {
                    for (int i = 1; i < caches.size() ; i++) {
                        query += " , ( ? ,? ,? ) ";
                    }
                }

                DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
                prepare(sqlBuilder,caches);

                return sqlBuilder.execute(dataSource);

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

        private void prepare(final DataManager.SqlBuilder sqlBuilder,final Cache cache) throws SQLException {
                
                    code(cache.code()).set(sqlBuilder);
                
                
                    cache(cache.cache()).set(sqlBuilder);
                
                
                    modifiedBy(cache.modifiedBy()).set(sqlBuilder);
                
        }


        public SetByPKClause set(final Cache cache) {
            return new SetByPKClause(cache);
        }

        public final class SetByPKClause  {
    
                private WhereClause whereClause;
                private final Cache cache;

                SetByPKClause(final Cache cache) {
                    this.cache = cache;
                }

                public SetByPKClause where(final WhereClause whereClause) {
                    this.whereClause = whereClause;
                    return this;
                }


                public int execute(final DataSource dataSource) throws SQLException  {
                
  final String query ="UPDATE \"cache\" SET code = ? ,\"cache\" = ? ,modified_by = ?"
+ ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );

                    DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
                    prepare(sqlBuilder,cache);
                    return sqlBuilder.execute(dataSource);
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
                    
  final String query = "UPDATE \"cache\" SET "+ getSetValues()+ ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );

                    DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);

                    for (Value<?,?> value:values) {
                        value.set(sqlBuilder);
                    }

                    return sqlBuilder.execute(dataSource);
                }

                public List<Cache> returning(final DataSource dataSource) throws SQLException 
  {
                    return null;
                }
            }
            
        }
        
    public DataManager.Statement<Value<?,?>> sql(final String sql) {
        return new DataManager.Statement<>(sql);
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

public sealed class SelectStatement implements DataManager.Sql<List<Cache>> permits SelectStatementWithWhere {

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
        public final List<Cache> execute(final Connection connection) throws SQLException 
 {
            
		final String query = "SELECT code,\"cache\",created_by,created_at,modified_by,modified_at FROM \"cache\"" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) )
                + ( this.limitClause == null ? "" : this.limitClause.asSql() )
                + ( this.offsetClause == null ? "" : this.offsetClause.asSql() );
                return dataManager.sql(query).queryForList(CacheStore.this::rowMapper).execute(connection);
	}

        public final int count(final DataSource dataSource) throws SQLException {
		final String query = "SELECT COUNT(*) FROM \"cache\"" 
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

                public DataManager.Page<Cache> execute(final DataSource dataSource) throws SQLException 
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

                        public DataManager.Page<Cache> execute(final DataSource dataSource) throws SQLException 
 {
                                return this.limitClause.execute(dataSource);
                        }


        }

        }


public final DataManager.SelectQuery<Value<?,?>,Cache> sql(final String sql) {
            return new DataManager.SelectQuery<>(sql, CacheStore.this::rowMapper);
    }




}



    






    public DataManager.DeleteStatement delete() {
        return new DataManager.DeleteStatement("DELETE FROM \"cache\"");
    }

 

    /**
    * Maps a row from ResultSet for returning properties.
    *
    * @param rs The ResultSet.
    * @param insertingCache The inserting Cache instance.
    * @return A new Cache object.
    * @throws SQLException if any SQL error occurs.
    */
    private Cache rowMapperForReturning(final ResultSet rs,final Cache insertingCache) throws SQLException 
{
    return new Cache(


        insertingCache.code()
        
    ,

        insertingCache.cache()
        
    ,

        insertingCache.createdBy()
        
    ,

        createdAt().get(rs,1)
        
    ,

        insertingCache.modifiedBy()
        
    ,

        insertingCache.modifiedAt()
        
    );
    
    }

    /**
    * Maps a row from ResultSet.
    *
    * @param rs The ResultSet.
    * @return A new Cache object.
    * @throws SQLException if any SQL error occurs.
    */
    private Cache rowMapper(ResultSet rs) throws SQLException 
 {
    return new Cache(
        code().get(rs,1)
    ,
        cache().get(rs,2)
    ,
        createdBy().get(rs,3)
    ,
        createdAt().get(rs,4)
    ,
        modifiedBy().get(rs,5)
    ,
        modifiedAt().get(rs,6)
    );
    
    }



        /**
        * Creates a Value for code.
        *
        * @param value The value of type String.
        * @return A Value object.
        */
        public static Value<Column.CodeColumn,String> code(final String value) {
        return new Value<>(code(),value);
        }

        /**
        * Retrieves the column for code.
        *
        * @return The column for code.
        */
        public static Column.CodeColumn code() {
            return new WhereClause().code();
        }

        /**
        * Creates a Value for cache.
        *
        * @param value The value of type String.
        * @return A Value object.
        */
        public static Value<Column.CacheColumn,String> cache(final String value) {
        return new Value<>(cache(),value);
        }

        /**
        * Retrieves the column for cache.
        *
        * @return The column for cache.
        */
        public static Column.CacheColumn cache() {
            return new WhereClause().cache();
        }

        /**
        * Creates a Value for createdBy.
        *
        * @param value The value of type String.
        * @return A Value object.
        */
        public static Value<Column.CreatedByColumn,String> createdBy(final String value) {
        return new Value<>(createdBy(),value);
        }

        /**
        * Retrieves the column for createdBy.
        *
        * @return The column for createdBy.
        */
        public static Column.CreatedByColumn createdBy() {
            return new WhereClause().createdBy();
        }

        /**
        * Creates a Value for createdAt.
        *
        * @param value The value of type LocalDateTime.
        * @return A Value object.
        */
        public static Value<Column.CreatedAtColumn,LocalDateTime> createdAt(final LocalDateTime value) {
        return new Value<>(createdAt(),value);
        }

        /**
        * Retrieves the column for createdAt.
        *
        * @return The column for createdAt.
        */
        public static Column.CreatedAtColumn createdAt() {
            return new WhereClause().createdAt();
        }

        /**
        * Creates a Value for modifiedBy.
        *
        * @param value The value of type String.
        * @return A Value object.
        */
        public static Value<Column.ModifiedByColumn,String> modifiedBy(final String value) {
        return new Value<>(modifiedBy(),value);
        }

        /**
        * Retrieves the column for modifiedBy.
        *
        * @return The column for modifiedBy.
        */
        public static Column.ModifiedByColumn modifiedBy() {
            return new WhereClause().modifiedBy();
        }

        /**
        * Creates a Value for modifiedAt.
        *
        * @param value The value of type LocalDateTime.
        * @return A Value object.
        */
        public static Value<Column.ModifiedAtColumn,LocalDateTime> modifiedAt(final LocalDateTime value) {
        return new Value<>(modifiedAt(),value);
        }

        /**
        * Retrieves the column for modifiedAt.
        *
        * @return The column for modifiedAt.
        */
        public static Column.ModifiedAtColumn modifiedAt() {
            return new WhereClause().modifiedAt();
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
        * Adds code to the SQL clause.
        *
        * @return The column for code.
        */
        public Column.CodeColumn code() {
        Column.CodeColumn query = new Column.CodeColumn(this);
        this.nodes.add(query);
        return query;
        }

        
        /**
        * Adds cache to the SQL clause.
        *
        * @return The column for cache.
        */
        public Column.CacheColumn cache() {
        Column.CacheColumn query = new Column.CacheColumn(this);
        this.nodes.add(query);
        return query;
        }

        
        /**
        * Adds createdBy to the SQL clause.
        *
        * @return The column for createdBy.
        */
        public Column.CreatedByColumn createdBy() {
        Column.CreatedByColumn query = new Column.CreatedByColumn(this);
        this.nodes.add(query);
        return query;
        }

        
        /**
        * Adds createdAt to the SQL clause.
        *
        * @return The column for createdAt.
        */
        public Column.CreatedAtColumn createdAt() {
        Column.CreatedAtColumn query = new Column.CreatedAtColumn(this);
        this.nodes.add(query);
        return query;
        }

        
        /**
        * Adds modifiedBy to the SQL clause.
        *
        * @return The column for modifiedBy.
        */
        public Column.ModifiedByColumn modifiedBy() {
        Column.ModifiedByColumn query = new Column.ModifiedByColumn(this);
        this.nodes.add(query);
        return query;
        }

        
        /**
        * Adds modifiedAt to the SQL clause.
        *
        * @return The column for modifiedAt.
        */
        public Column.ModifiedAtColumn modifiedAt() {
        Column.ModifiedAtColumn query = new Column.ModifiedAtColumn(this);
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


    public static class CodeColumn extends Column<String> {
    private String sql;

    public CodeColumn(final PartialWhereClause  whereClause) {
    super(whereClause);
    }

    public String name() {
    return "code";
    }

    public final WhereClause isNull() {
    sql = "code IS NULL";
    return getWhereClause();
    }

    public final WhereClause isNotNull() {
    sql = "code IS NOT NULL";
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
    sql = "code ='" + value + "'";
    return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
    sql = "code LIKE '" + value + "'";
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
    public static class CacheColumn extends Column<String> {
    private String sql;

    public CacheColumn(final PartialWhereClause  whereClause) {
    super(whereClause);
    }

    public String name() {
    return "\"cache\"";
    }

    public final WhereClause isNull() {
    sql = "\"cache\" IS NULL";
    return getWhereClause();
    }

    public final WhereClause isNotNull() {
    sql = "\"cache\" IS NOT NULL";
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
    sql = "\"cache\" ='" + value + "'";
    return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
    sql = "\"cache\" LIKE '" + value + "'";
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
    public static class CreatedByColumn extends Column<String> {
    private String sql;

    public CreatedByColumn(final PartialWhereClause  whereClause) {
    super(whereClause);
    }

    public String name() {
    return "created_by";
    }

    public final WhereClause isNull() {
    sql = "created_by IS NULL";
    return getWhereClause();
    }

    public final WhereClause isNotNull() {
    sql = "created_by IS NOT NULL";
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
    sql = "created_by ='" + value + "'";
    return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
    sql = "created_by LIKE '" + value + "'";
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
    public static class CreatedAtColumn extends Column<LocalDateTime> {
    private String sql;

    public CreatedAtColumn(final PartialWhereClause  whereClause) {
    super(whereClause);
    }

    public String name() {
    return "created_at";
    }

    public final WhereClause isNull() {
    sql = "created_at IS NULL";
    return getWhereClause();
    }

    public final WhereClause isNotNull() {
    sql = "created_at IS NOT NULL";
    return getWhereClause();
    }


    public void set(final DataManager.SqlBuilder preparedStatement, final LocalDateTime value) {
    preparedStatement.param(value == null ? null : java.sql.Timestamp.valueOf(value));
    }

    @Override
    public LocalDateTime get(final ResultSet resultSet, final int i) throws SQLException {
        java.sql.Timestamp timestamp = resultSet.getTimestamp(i);
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

    public final WhereClause  eq(final LocalDateTime value) {
    sql = "created_at =" + value;
    return getWhereClause();
    }

    public final WhereClause  gt(final LocalDateTime value) {
    sql = "created_at >" + value;
    return getWhereClause();
    }

    public final WhereClause  gte(final LocalDateTime value) {
    sql = "created_at >=" + value;
    return getWhereClause();
    }

    public final WhereClause  lt(final LocalDateTime value) {
    sql = "created_at <" + value;
    return getWhereClause();
    }

    public final WhereClause  lte(final LocalDateTime value) {
    sql = "created_at <=" + value;
    return getWhereClause();
    }

    @Override
    public String asSql() {
    return sql;
    }

    public boolean validate(LocalDateTime value) {
    return true;
    }

    }
    public static class ModifiedByColumn extends Column<String> {
    private String sql;

    public ModifiedByColumn(final PartialWhereClause  whereClause) {
    super(whereClause);
    }

    public String name() {
    return "modified_by";
    }

    public final WhereClause isNull() {
    sql = "modified_by IS NULL";
    return getWhereClause();
    }

    public final WhereClause isNotNull() {
    sql = "modified_by IS NOT NULL";
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
    sql = "modified_by ='" + value + "'";
    return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
    sql = "modified_by LIKE '" + value + "'";
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
    public static class ModifiedAtColumn extends Column<LocalDateTime> {
    private String sql;

    public ModifiedAtColumn(final PartialWhereClause  whereClause) {
    super(whereClause);
    }

    public String name() {
    return "modified_at";
    }

    public final WhereClause isNull() {
    sql = "modified_at IS NULL";
    return getWhereClause();
    }

    public final WhereClause isNotNull() {
    sql = "modified_at IS NOT NULL";
    return getWhereClause();
    }


    public void set(final DataManager.SqlBuilder preparedStatement, final LocalDateTime value) {
    preparedStatement.param(value == null ? null : java.sql.Timestamp.valueOf(value));
    }

    @Override
    public LocalDateTime get(final ResultSet resultSet, final int i) throws SQLException {
        java.sql.Timestamp timestamp = resultSet.getTimestamp(i);
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

    public final WhereClause  eq(final LocalDateTime value) {
    sql = "modified_at =" + value;
    return getWhereClause();
    }

    public final WhereClause  gt(final LocalDateTime value) {
    sql = "modified_at >" + value;
    return getWhereClause();
    }

    public final WhereClause  gte(final LocalDateTime value) {
    sql = "modified_at >=" + value;
    return getWhereClause();
    }

    public final WhereClause  lt(final LocalDateTime value) {
    sql = "modified_at <" + value;
    return getWhereClause();
    }

    public final WhereClause  lte(final LocalDateTime value) {
    sql = "modified_at <=" + value;
    return getWhereClause();
    }

    @Override
    public String asSql() {
    return sql;
    }

    public boolean validate(LocalDateTime value) {
    return true;
    }

    }

    }








    }
