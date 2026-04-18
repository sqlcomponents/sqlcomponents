
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
import org.example.model.Accounts;

    /**
    * Datastore for the table - accounts.
    */
    public final class AccountsStore  {

    /**
    * Retrieves an instance of AccountsStore.
    *
    * @param theDataManager  The DataManager instance.
    * @param theObserver     The observer to notify for data changes.
    * @return an instance of AccountsStore
    */
    public static AccountsStore getAccountsStore(
    final DataManager theDataManager
    ,final DataManager.Observer theObserver

) {
    return new AccountsStore(
    theDataManager
    ,theObserver

);

    }

    private final DataManager dataManager;

    private final DataManager.Observer observer;



    /**
    * Constructor for AccountsStore.
    *
    * @param theDataManager   The DataManager instance.
    * @param theObserver      The observer for data changes.
    */
    private AccountsStore(
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
            
            final Column.NameColumn nameColumn 
                        ,
            final Column.BalanceColumn balanceColumn 
            ) {
        return new InsertStatement(
        
        );
    }
    public final class InsertStatement {
    

        private InsertStatement(
                ) {


        }

        private void prepare(final DataManager.SqlBuilder sqlBuilder,final Accounts accounts) {
            
                name(accounts.name()).set(sqlBuilder);
          
            
                balance(accounts.balance()).set(sqlBuilder);
          
        }

        private void prepare(final DataManager.SqlBuilder sqlBuilder,final List<Accounts> accountss) {
            for ( Accounts accounts : accountss) {
                prepare(sqlBuilder, accounts);
            }
        }

        public ValueClause values(final Accounts accounts) {
            return new ValueClause(accounts);
        }

        public ValuesClause values(final Accounts... accountss) {
            return new ValuesClause(Arrays.asList(accountss));
        }

        public ValuesClause values(final List<Accounts> accountss) {
            return new ValuesClause(accountss);
        }

        public final class ValueClause  {

            private final Accounts accounts;

            ValueClause(final Accounts accounts) {
                this.accounts = accounts;
            }
            

            public int execute(final DataSource dataSource) throws SQLException  {
                final String query = "INSERT INTO accounts ( name,balance ) VALUES ( ? ,? )";

                DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);

                prepare(sqlBuilder,accounts);

                return sqlBuilder.execute(dataSource);
            }


            public Accounts returning(final DataSource dataSource) throws SQLException 
  {

                Accounts insertedAccounts = null;
                
                final String query =  "INSERT INTO accounts ( name,balance ) VALUES ( ? ,? ) returning id ";
                
                DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
                prepare(sqlBuilder,accounts);
  
                      insertedAccounts = sqlBuilder.queryForOne(resultSet -> rowMapperForReturning(resultSet,accounts)).execute(dataSource);
        


                
                return insertedAccounts;
            }
        }

        public final class ValuesClause  {

            private final List<Accounts> accountss;

            ValuesClause (final List<Accounts> accountss) {
                this.accountss = accountss;
            }

            public int execute(final DataSource dataSource) throws SQLException  {
                String query = "INSERT INTO accounts ( name,balance ) VALUES ( ? ,? )";

                if (accountss.size() > 1) {
                    for (int i = 1; i < accountss.size() ; i++) {
                        query += " , ( ? ,? ) ";
                    }
                }

                DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
                prepare(sqlBuilder,accountss);

                return sqlBuilder.execute(dataSource);

            }


            public List<Accounts> returning(final DataSource dataSource) throws SQLException 
  {
                List<Accounts> insertedAccountss = null;
                String query = "INSERT INTO accounts ( name,balance ) VALUES ( ? ,? )";

                if (accountss.size() > 1) {
          for (int i = 1; i < accountss.size() ; i++) {
              query += " , ( ? ,? ) ";
          }
      }

      query += " returning id ";

                DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
                prepare(sqlBuilder,accountss);




                            java.util.concurrent.atomic.AtomicInteger atomicInteger =  new java.util.concurrent.atomic.AtomicInteger(0);

                            insertedAccountss = sqlBuilder
              .queryForList(resultSet -> rowMapperForReturning(resultSet, accountss.get(atomicInteger.getAndIncrement()))).execute(dataSource); 

                                


                
                return insertedAccountss;
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

        private void prepare(final DataManager.SqlBuilder sqlBuilder,final Accounts accounts) throws SQLException {
                
                    name(accounts.name()).set(sqlBuilder);
                
                
                    balance(accounts.balance()).set(sqlBuilder);
                
        }


        public SetByPKClause set(final Accounts accounts) {
            return new SetByPKClause(accounts);
        }

        public final class SetByPKClause  {
    
                private WhereClause whereClause;
                private final Accounts accounts;

                SetByPKClause(final Accounts accounts) {
                    this.accounts = accounts;
                }

                public SetByPKClause where(final WhereClause whereClause) {
                    this.whereClause = whereClause;
                    return this;
                }


                public int execute(final DataSource dataSource) throws SQLException  {
                
  final String query ="UPDATE accounts SET name = ? ,balance = ?"
+ ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );

                    DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
                    prepare(sqlBuilder,accounts);
                    return sqlBuilder.execute(dataSource);
                }


                public final Accounts returning(final DataSource dataSource) throws SQLException 
  {
                    Accounts updatedAccounts = null ;
  final String query ="UPDATE accounts SET name = ? ,balance = ?"
+ ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
                    DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
                    prepare(sqlBuilder,accounts);
         
                        
                        if( sqlBuilder.execute(dataSource) == 1 ) {
                        
  
                        updatedAccounts =  select(dataSource,accounts.id()).get();
                    }
                    
                    return updatedAccounts;
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
                    
  final String query = "UPDATE accounts SET "+ getSetValues()+ ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );

                    DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);

                    for (Value<?,?> value:values) {
                        value.set(sqlBuilder);
                    }

                    return sqlBuilder.execute(dataSource);
                }

                public List<Accounts> returning(final DataSource dataSource) throws SQLException 
  {
                    return null;
                }
            }
            
        }
        
    public DataManager.Statement<Value<?,?>> sql(final String sql) {
        return new DataManager.Statement<>(sql);
    }



    }	



public SingleSelectStatementWithWhere select(Long id) {
        return new SingleSelectStatementWithWhere(id);
}

public final class SingleSelectStatementWithWhere extends SingleSelectStatement{

        private final Long id;


        private SingleSelectStatementWithWhere(Long id) {
            super(id);
            this.id = id;

        }

        public SingleSelectStatement where(WhereClause whereClause) {
            return new SingleSelectStatement(whereClause,id);
        }
}

public sealed class SingleSelectStatement implements DataManager.Sql<Accounts> permits SingleSelectStatementWithWhere {


        private final Long id;


        private final WhereClause whereClause;

        private SingleSelectStatement(Long id) {
            this(null,id);
        }

        private SingleSelectStatement(final WhereClause whereClause, Long id) {
            this.whereClause = whereClause;
            this.id = id;

        }

        @Override
        public final Accounts execute(final Connection connection) throws SQLException 
 {
            
		final String query = "SELECT id,name,balance FROM accounts WHERE id = ?"

                + ( whereClause == null ? "" : (" AND " + whereClause.asSql()) );
                DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
    
        id(id).set(sqlBuilder);
	

        return sqlBuilder.queryForOne(AccountsStore.this::rowMapper).execute(connection);
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

public sealed class SelectStatement implements DataManager.Sql<List<Accounts>> permits SelectStatementWithWhere {

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
        public final List<Accounts> execute(final Connection connection) throws SQLException 
 {
            
		final String query = "SELECT id,name,balance FROM accounts" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) )
                + ( this.limitClause == null ? "" : this.limitClause.asSql() )
                + ( this.offsetClause == null ? "" : this.offsetClause.asSql() );
                return dataManager.sql(query).queryForList(AccountsStore.this::rowMapper).execute(connection);
	}

        public final int count(final DataSource dataSource) throws SQLException {
		final String query = "SELECT COUNT(id) FROM accounts" 
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

                public DataManager.Page<Accounts> execute(final DataSource dataSource) throws SQLException 
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

                        public DataManager.Page<Accounts> execute(final DataSource dataSource) throws SQLException 
 {
                                return this.limitClause.execute(dataSource);
                        }


        }

        }


public final DataManager.SelectQuery<Value<?,?>,Accounts> sql(final String sql) {
            return new DataManager.SelectQuery<>(sql, AccountsStore.this::rowMapper);
    }




}



    






    public DataManager.DeleteStatement delete() {
        return new DataManager.DeleteStatement("DELETE FROM accounts");
    }

 

    /**
    * Maps a row from ResultSet for returning properties.
    *
    * @param rs The ResultSet.
    * @param insertingAccounts The inserting Accounts instance.
    * @return A new Accounts object.
    * @throws SQLException if any SQL error occurs.
    */
    private Accounts rowMapperForReturning(final ResultSet rs,final Accounts insertingAccounts) throws SQLException 
{
    return new Accounts(


        id().get(rs,1)
        
    ,

        insertingAccounts.name()
        
    ,

        insertingAccounts.balance()
        
    );
    
    }

    /**
    * Maps a row from ResultSet.
    *
    * @param rs The ResultSet.
    * @return A new Accounts object.
    * @throws SQLException if any SQL error occurs.
    */
    private Accounts rowMapper(ResultSet rs) throws SQLException 
 {
    return new Accounts(
        id().get(rs,1)
    ,
        name().get(rs,2)
    ,
        balance().get(rs,3)
    );
    
    }



        /**
        * Creates a Value for id.
        *
        * @param value The value of type Long.
        * @return A Value object.
        */
        public static Value<Column.IdColumn,Long> id(final Long value) {
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
        * Creates a Value for name.
        *
        * @param value The value of type String.
        * @return A Value object.
        */
        public static Value<Column.NameColumn,String> name(final String value) {
        return new Value<>(name(),value);
        }

        /**
        * Retrieves the column for name.
        *
        * @return The column for name.
        */
        public static Column.NameColumn name() {
            return new WhereClause().name();
        }

        /**
        * Creates a Value for balance.
        *
        * @param value The value of type Double.
        * @return A Value object.
        */
        public static Value<Column.BalanceColumn,Double> balance(final Double value) {
        return new Value<>(balance(),value);
        }

        /**
        * Retrieves the column for balance.
        *
        * @return The column for balance.
        */
        public static Column.BalanceColumn balance() {
            return new WhereClause().balance();
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
        * Adds name to the SQL clause.
        *
        * @return The column for name.
        */
        public Column.NameColumn name() {
        Column.NameColumn query = new Column.NameColumn(this);
        this.nodes.add(query);
        return query;
        }

        
        /**
        * Adds balance to the SQL clause.
        *
        * @return The column for balance.
        */
        public Column.BalanceColumn balance() {
        Column.BalanceColumn query = new Column.BalanceColumn(this);
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


    public static class IdColumn extends Column<Long> {
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



    public void set(final DataManager.SqlBuilder preparedStatement, final Long value) {
    if(value == null) {
        preparedStatement.paramNull(4,"int4" );
    } else {
        preparedStatement.param(value);
    }
        

    
    }

    @Override
    public Long get(final ResultSet resultSet, final int i) throws SQLException {
        return resultSet.getObject(i) == null ? null : resultSet.getLong(i);
    }

    public final WhereClause eq(final Long value) {
    sql = "id =" + value;
    return getWhereClause();
    }

    public final WhereClause gt(final Long value) {
    sql = "id >" + value;
    return getWhereClause();
    }

    public final WhereClause  gte(final Long value) {
    sql = "id >=" + value;
    return getWhereClause();
    }

    public final WhereClause  lt(final Long value) {
    sql = "id <" + value;
    return getWhereClause();
    }

    public final WhereClause  lte(final Long value) {
    sql = "id <=" + value;
    return getWhereClause();
    }




    @Override
    public String asSql() {
    return sql;
    }

    public boolean validate(Long value) {
    return true;
    }

    }
    public static class NameColumn extends Column<String> {
    private String sql;

    public NameColumn(final PartialWhereClause  whereClause) {
    super(whereClause);
    }

    public String name() {
    return "name";
    }

    public final WhereClause isNull() {
    sql = "name IS NULL";
    return getWhereClause();
    }

    public final WhereClause isNotNull() {
    sql = "name IS NOT NULL";
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
    sql = "name ='" + value + "'";
    return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
    sql = "name LIKE '" + value + "'";
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
    public static class BalanceColumn extends Column<Double> {
    private String sql;

    public BalanceColumn(final PartialWhereClause  whereClause) {
    super(whereClause);
    }

    public String name() {
    return "balance";
    }

    public final WhereClause isNull() {
    sql = "balance IS NULL";
    return getWhereClause();
    }

    public final WhereClause isNotNull() {
    sql = "balance IS NOT NULL";
    return getWhereClause();
    }



    public void set(final DataManager.SqlBuilder preparedStatement, final Double value) {
    if(value == null) {
        preparedStatement.paramNull(2,"numeric" );
    } else {
        preparedStatement.param(value);
    }
        

    
    }

    @Override
    public Double get(final ResultSet resultSet, final int i) throws SQLException {
        return resultSet.getObject(i) == null ? null : resultSet.getDouble(i);
    }

    public final WhereClause eq(final Double value) {
    sql = "balance =" + value;
    return getWhereClause();
    }

    public final WhereClause gt(final Double value) {
    sql = "balance >" + value;
    return getWhereClause();
    }

    public final WhereClause  gte(final Double value) {
    sql = "balance >=" + value;
    return getWhereClause();
    }

    public final WhereClause  lt(final Double value) {
    sql = "balance <" + value;
    return getWhereClause();
    }

    public final WhereClause  lte(final Double value) {
    sql = "balance <=" + value;
    return getWhereClause();
    }




    @Override
    public String asSql() {
    return sql;
    }

    public boolean validate(Double value) {
    return true;
    }

    }

    }



    public Optional<Accounts> select(final DataSource dataSource,Long id) throws SQLException 
  {
            return select(dataSource,id, null);
    }
    public Optional<Accounts> select(final DataSource dataSource,Long id, WhereClause whereClause) throws SQLException 
  {
        
		final String query = "SELECT id,name,balance FROM accounts WHERE id = ?"

                + ( whereClause == null ? "" : (" AND " + whereClause.asSql()) );

        DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
    
        id(id).set(sqlBuilder);
	

        return Optional.ofNullable(sqlBuilder.queryForOne(this::rowMapper).execute(dataSource));
            
    }
        
    public boolean exists(final DataSource dataSource,Long id) throws SQLException {
        final String query = "SELECT 1 FROM accounts WHERE id = ?";
        DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);

        id(id).set(sqlBuilder);
	

		return sqlBuilder.queryForExists().execute(dataSource);
	}


public int delete(final DataSource dataSource,Long id) throws SQLException  {
		final String query = "DELETE FROM accounts WHERE id = ?";
        DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
        id(id).set(sqlBuilder);
	
        return sqlBuilder.execute(dataSource);
}


    public Optional<Accounts> selectById(final DataSource dataSource,Long id) throws SQLException 
 {
        
            final String query = "SELECT id,name,balance FROM accounts WHERE id= ?";

        DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
    
        id(id).set(sqlBuilder);
	

        return Optional.ofNullable(sqlBuilder.queryForOne(this::rowMapper).execute(dataSource));

            
    }

    public boolean existsById(final DataSource dataSource,Long id) throws SQLException 
 {

            final String query = "SELECT 1 FROM accounts WHERE id= ?";
            DataManager.SqlBuilder sqlBuilder = dataManager.sql(query);
    
        id(id).set(sqlBuilder);
	

        return sqlBuilder.queryForExists().execute(dataSource);
    }





    }
