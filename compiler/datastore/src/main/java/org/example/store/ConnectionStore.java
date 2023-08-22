
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
import java.util.Optional;
import org.example.model.Connection;
import org.example.RajaManager;

/**
 * Datastore for the table - connection.
 */
public final class ConnectionStore  {

    private final javax.sql.DataSource dbDataSource;

    private final RajaManager.Observer observer;




    /**
     * Datastore
     */
    public ConnectionStore(final javax.sql.DataSource theDataSource
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

        private final ConnectionStore connectionStore;
        private final WhereClause whereClause;

        private LimitClause limitClause;
        private LimitClause.OffsetClause offsetClause;

        public LimitClause limit(final int limit) {
                return new LimitClause(this,limit);
        }

        private SelectStatement(final ConnectionStore connectionStore) {
            this(connectionStore,null);
        }

        private SelectStatement(final ConnectionStore connectionStore
                ,final WhereClause whereClause) {
            this.connectionStore = connectionStore;
            this.whereClause = whereClause;
        }

        public List<Connection> execute() throws SQLException {
		final String query = " SELECT code,name FROM \"connection\"" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) )
                + ( this.limitClause == null ? "" : this.limitClause.asSql() )
                + ( this.offsetClause == null ? "" : this.offsetClause.asSql() );
                try (java.sql.Connection dbConnection = this.connectionStore.dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                
                ResultSet resultSet = preparedStatement.executeQuery();
                                List<Connection> arrays = new ArrayList();
                while (resultSet.next()) {
                                        arrays.add(this.connectionStore.rowMapper(resultSet));
                                }
                                return arrays;
                } 
	}

        public int count() throws SQLException {
                int count = 0;
		final String query = " SELECT COUNT(*) FROM \"connection\"" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
                try (java.sql.Connection dbConnection = this.connectionStore.dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                
                ResultSet resultSet = preparedStatement.executeQuery();
                                List<Connection> arrays = new ArrayList();
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

                public RajaManager.Page<Connection> execute() throws SQLException {
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

                        public RajaManager.Page<Connection> execute() throws SQLException {
                                return this.limitClause.execute();
                        }


        }

        }
}



    

    public Optional<Connection> select(String code) throws SQLException  {
        Connection connection = null;
		final String query = " SELECT code,name FROM \"connection\" WHERE code = ?";
        try (java.sql.Connection dbConnection = dbDataSource.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
            preparedStatement.setString(1,code);
	
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) connection = rowMapper(resultSet);
        }
        return Optional.ofNullable(connection);
	}

    public boolean exists(String code) throws SQLException {
        final String query = " SELECT 1 FROM \"connection\" WHERE code = ?";
        boolean isExists = false;
        try (java.sql.Connection dbConnection = dbDataSource.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
            preparedStatement.setString(1,code);
	
            ResultSet resultSet = preparedStatement.executeQuery();

            isExists = resultSet.next();
        }
		return isExists;
	}

    public List<Connection> selectBy() throws SQLException  {
        return null;
	}


    public Optional<Connection> selectByName(String name) throws SQLException {
        Connection connection = null;
            final String query = " SELECT code,name FROM \"connection\" WHERE name= ?";
            try (java.sql.Connection dbConnection = dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                preparedStatement.setString(1,name);
	
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) connection = rowMapper(resultSet);
            }
            return Optional.ofNullable(connection);
    }

    public boolean existsByName(String name) throws SQLException {

            final String query = " SELECT 1 FROM \"connection\" WHERE name= ?";
            boolean isExists = false;
            try (java.sql.Connection dbConnection = dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                preparedStatement.setString(1,name);
	
                ResultSet resultSet = preparedStatement.executeQuery();

                isExists = resultSet.next();
            }
            return isExists;
    }

    public Optional<Connection> selectByCode(String code) throws SQLException {
        Connection connection = null;
            final String query = " SELECT code,name FROM \"connection\" WHERE code= ?";
            try (java.sql.Connection dbConnection = dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                preparedStatement.setString(1,code);
	
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) connection = rowMapper(resultSet);
            }
            return Optional.ofNullable(connection);
    }

    public boolean existsByCode(String code) throws SQLException {

            final String query = " SELECT 1 FROM \"connection\" WHERE code= ?";
            boolean isExists = false;
            try (java.sql.Connection dbConnection = dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                preparedStatement.setString(1,code);
	
                ResultSet resultSet = preparedStatement.executeQuery();

                isExists = resultSet.next();
            }
            return isExists;
    }








	public final InsertStatement insert() {
        return new InsertStatement(this
);
    }

    public final InsertStatement insert(
            
            final Column.CodeColumn codeColumn 
            ) {
        return new InsertStatement(this
        );
    }
    public static final class InsertStatement {
        private final ConnectionStore connectionStore;



        private InsertStatement(final ConnectionStore connectionStore
                ) {
            this.connectionStore = connectionStore;
        }

        private void prepare(final PreparedStatement preparedStatement,final Connection connection) throws SQLException {
                preparedStatement.setString(1,connection.getCode());
                preparedStatement.setString(2,connection.getName());
        }

        public final ValueClause values(final Connection connection) {
            return new ValueClause(connection,this);
        }

        public final ValuesClause values(final List<Connection> listOfConnection) {
            return new ValuesClause(listOfConnection,this);
        }

        public static final class ValueClause  {
            
            private final InsertStatement insertStatement;

            private Connection connection;

            ValueClause (final Connection connection,final InsertStatement insertStatement) {
                this.connection = connection;
                this.insertStatement = insertStatement;
            }

            public final ValuesClause values(final Connection theConnection) {
                List<Connection> listOfConnection = new ArrayList<>();
                listOfConnection.add(connection);
                listOfConnection.add(theConnection);
                return new ValuesClause(listOfConnection,insertStatement);
            }
            

            public final int execute() throws SQLException  {
                int insertedRows = 0;
  final String query = "INSERT INTO \"connection\" ( code ,name ) VALUES ( ? ,? ) ";

                try (java.sql.Connection dbConnection = insertStatement.connectionStore.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    this.insertStatement.prepare(preparedStatement,connection);
                    insertedRows = preparedStatement.executeUpdate();
                }
                return insertedRows;
            }


            public final Connection returning() throws SQLException  {
                Connection insertedConnection = null ;
  final String query = "INSERT INTO \"connection\" ( code ,name ) VALUES ( ? ,? ) ";

                try (java.sql.Connection dbConnection = insertStatement.connectionStore.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    this.insertStatement.prepare(preparedStatement,connection);

                        if( preparedStatement.executeUpdate() == 1 ) {
                             insertedConnection = connection;
                        }


                }
                return insertedConnection;
            }
        }

        public static final class ValuesClause  {

            
            private final InsertStatement insertStatement;

            private List<Connection> listOfConnection;

            ValuesClause(final List<Connection> theListOfConnection,final InsertStatement insertStatement) {
                this.listOfConnection = theListOfConnection;
                this.insertStatement = insertStatement;
            }

            public final ValuesClause values(final Connection theConnection) {
                this.listOfConnection.add(theConnection);
                return this;
            }

            public final int[] execute() throws SQLException  {
                int[] insertedRows = null;
  final String query = "INSERT INTO \"connection\" ( code ,name ) VALUES ( ? ,? ) ";
                
                try (java.sql.Connection dbConnection = insertStatement.connectionStore.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    for (Connection connection:listOfConnection) {
                        this.insertStatement.prepare(preparedStatement, connection);
                        preparedStatement.addBatch();
                    }
                    insertedRows = preparedStatement.executeBatch();
                }
                return insertedRows;
            }
            public final List<Connection> returning() throws SQLException  {
                List<Connection> insertedList = null ;
  final String query = "INSERT INTO \"connection\" ( code ,name ) VALUES ( ? ,? ) ";

                try (java.sql.Connection dbConnection = insertStatement.connectionStore.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    for (Connection connection:listOfConnection) {
                        this.insertStatement.prepare(preparedStatement, connection);
                        preparedStatement.addBatch();
                    }

                    int[] insertedCounts = preparedStatement.executeBatch();

                    if ( insertedCounts != null ) {
                        insertedList = new ArrayList<>();


                        for (int i = 0; i < insertedCounts.length; i++) {

                          int insertedRows = insertedCounts[i];
                          if (insertedRows == 1) {
                            insertedList.add(insertStatement.connectionStore.select(listOfConnection.get(i).getCode()).get());
                          }
                          else {
                            insertedList.add(null);
                          }
                        }

                    }




                }
                return insertedList;
            }


        }
    }

public int delete(String code) throws SQLException  {
		final String query = "DELETE FROM \"connection\" WHERE code = ?";
        try (java.sql.Connection dbConnection = dbDataSource.getConnection();
			PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
			preparedStatement.setString(1,code);
	
            return preparedStatement.executeUpdate();
        }
	}

public DeleteStatement delete(WhereClause whereClause) {
    return new DeleteStatement(this,whereClause);
}

public DeleteStatement delete() {
    return new DeleteStatement(this,null);
}

public static final class DeleteStatement {

    private final ConnectionStore connectionStore;
    private final WhereClause whereClause;

    private DeleteStatement(final ConnectionStore connectionStore) {
            this(connectionStore,null);
        }

        private DeleteStatement(final ConnectionStore connectionStore
                ,final WhereClause whereClause) {
            this.connectionStore = connectionStore;
            this.whereClause = whereClause;
        }

    public int execute() throws SQLException  {
    	final String query = "DELETE FROM \"connection\"" 
        + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
        try (java.sql.Connection dbConnection = this.connectionStore.dbDataSource.getConnection();
			Statement statement = dbConnection.createStatement()) {
            return statement.executeUpdate(query);
        }
	}
}





public int update(Connection connection) throws SQLException {
        final String query = " UPDATE \"connection\" SET name = ? WHERE code = ?";

		try (java.sql.Connection dbConnection = dbDataSource.getConnection();
             PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
        {


            preparedStatement.setString(1,connection.getName());


            preparedStatement.setString(2,connection.getCode());

			return preparedStatement.executeUpdate();
        }
	}

	public final UpdateStatement update() {
        return new UpdateStatement(this
                                               
        );
    }

    public static final class UpdateStatement {
        private final ConnectionStore connectionStore;


        private UpdateStatement(final ConnectionStore connectionStore
                ) {
            this.connectionStore = connectionStore;
        }

        private void prepare(final PreparedStatement preparedStatement,final Connection connection) throws SQLException {
                    preparedStatement.setString(1,connection.getName());

                preparedStatement.setString(2,connection.getCode());
        }


        public SetByPKClause set(final Connection connection) {
            return new SetByPKClause(connectionStore.dbDataSource,connection,this);
        }

        public static final class SetByPKClause  {
                private final javax.sql.DataSource dbDataSource;
                private final UpdateStatement updateStatement;
                private Connection connection;

                SetByPKClause(final javax.sql.DataSource dbDataSource,final Connection connection,final UpdateStatement updateStatement) {
                    this.dbDataSource = dbDataSource;
                    this.connection = connection;
                    this.updateStatement = updateStatement;
                }

                public final int execute() throws SQLException  {
                    int updtedRows = 0;
  final String query = " UPDATE \"connection\" SET name = ? WHERE code = ?";

                    try (java.sql.Connection dbConnection = dbDataSource.getConnection();
                        PreparedStatement preparedStatement = dbConnection.prepareStatement(query)){

                        this.updateStatement.prepare(preparedStatement,connection);

                        updtedRows = preparedStatement.executeUpdate();
                    }
                    return updtedRows;
                }


                public final Connection returning() throws SQLException  {
                    Connection updatedConnection = null ;
  final String query = " UPDATE \"connection\" SET name = ? WHERE code = ?";

                    try (java.sql.Connection dbConnection = updateStatement.connectionStore.dbDataSource.getConnection();
                        PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                    {
                        this.updateStatement.prepare(preparedStatement,connection);
                        if( preparedStatement.executeUpdate() == 1 ) {
                        updatedConnection =  updateStatement.connectionStore.select(connection.getCode()).get();
                    }
                    }
                    return updatedConnection;
                }
            }

        public SetClause set(final Value... values) {
            return new SetClause(values,this);
        }

        public static final class SetClause  {
            
            private final UpdateStatement updateStatement;

            private Connection connection;

            private Value[] values;

            SetClause(final Value[] values,final UpdateStatement updateStatement) {
                this.values = values;
                this.updateStatement = updateStatement;
            }

            SetClause(final Connection connection,final UpdateStatement updateStatement) {
                this.connection = connection;
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

                public final List<Connection> returning() throws SQLException  {
                    return null;
                }
            }


            

            
        }




    }	






  private Connection rowMapperForReturning(final ResultSet rs,final Connection insertingConnection) throws SQLException {
    final Connection connection = new Connection();
        connection.setCode(insertingConnection.getCode());
        connection.setName(insertingConnection.getName());
        return connection;
    }

	private Connection rowMapper(ResultSet rs) throws SQLException {
        final Connection connection = new Connection();
          connection.setCode(rs.getString(1));
          connection.setName(rs.getString(2));
        return connection;
    }




    public static Value<Column.CodeColumn,String> code(final String value) {
        return new Value<>(code(),value);
    }
    
    public static Column.CodeColumn code() {
        return new WhereClause().code();
    }


    public static Value<Column.NameColumn,String> name(final String value) {
        return new Value<>(name(),value);
    }
    
    public static Column.NameColumn name() {
        return new WhereClause().name();
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


        public Column.NameColumn name() {
            Column.NameColumn query = new Column.NameColumn(this);
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
public static class NameColumn extends Column<String> {
    private String sql;

    public NameColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
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
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(String value) {
        return true;
    }
}

        }
    

}
