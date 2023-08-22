
package org.example.store;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.stream.Collectors;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.model.FlywaySchemaHistory;
import org.example.RajaManager;

/**
 * Datastore for the table - flyway_schema_history.
 */
public final class FlywaySchemaHistoryStore  {

    private final javax.sql.DataSource dbDataSource;

    private final RajaManager.Observer observer;




    /**
     * Datastore
     */
    public FlywaySchemaHistoryStore(final javax.sql.DataSource theDataSource
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

        private final FlywaySchemaHistoryStore flywaySchemaHistoryStore;
        private final WhereClause whereClause;

        private LimitClause limitClause;
        private LimitClause.OffsetClause offsetClause;

        public LimitClause limit(final int limit) {
                return new LimitClause(this,limit);
        }

        private SelectStatement(final FlywaySchemaHistoryStore flywaySchemaHistoryStore) {
            this(flywaySchemaHistoryStore,null);
        }

        private SelectStatement(final FlywaySchemaHistoryStore flywaySchemaHistoryStore
                ,final WhereClause whereClause) {
            this.flywaySchemaHistoryStore = flywaySchemaHistoryStore;
            this.whereClause = whereClause;
        }

        public List<FlywaySchemaHistory> execute() throws SQLException {
		final String query = " SELECT installed_rank,\"version\",description,type,script,checksum,installed_by,installed_on,execution_time,success FROM flyway_schema_history" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) )
                + ( this.limitClause == null ? "" : this.limitClause.asSql() )
                + ( this.offsetClause == null ? "" : this.offsetClause.asSql() );
                try (java.sql.Connection dbConnection = this.flywaySchemaHistoryStore.dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                
                ResultSet resultSet = preparedStatement.executeQuery();
                                List<FlywaySchemaHistory> arrays = new ArrayList();
                while (resultSet.next()) {
                                        arrays.add(this.flywaySchemaHistoryStore.rowMapper(resultSet));
                                }
                                return arrays;
                } 
	}

        public int count() throws SQLException {
                int count = 0;
		final String query = " SELECT COUNT(*) FROM flyway_schema_history" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
                try (java.sql.Connection dbConnection = this.flywaySchemaHistoryStore.dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                
                ResultSet resultSet = preparedStatement.executeQuery();
                                List<FlywaySchemaHistory> arrays = new ArrayList();
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

                public RajaManager.Page<FlywaySchemaHistory> execute() throws SQLException {
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

                        public RajaManager.Page<FlywaySchemaHistory> execute() throws SQLException {
                                return this.limitClause.execute();
                        }


        }

        }
}



    

    public Optional<FlywaySchemaHistory> select(Long installedRank) throws SQLException  {
        FlywaySchemaHistory flywaySchemaHistory = null;
		final String query = " SELECT installed_rank,\"version\",description,type,script,checksum,installed_by,installed_on,execution_time,success FROM flyway_schema_history WHERE installed_rank = ?";
        try (java.sql.Connection dbConnection = dbDataSource.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
            preparedStatement.setLong(1,installedRank);
	
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) flywaySchemaHistory = rowMapper(resultSet);
        }
        return Optional.ofNullable(flywaySchemaHistory);
	}

    public boolean exists(Long installedRank) throws SQLException {
        final String query = " SELECT 1 FROM flyway_schema_history WHERE installed_rank = ?";
        boolean isExists = false;
        try (java.sql.Connection dbConnection = dbDataSource.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
            preparedStatement.setLong(1,installedRank);
	
            ResultSet resultSet = preparedStatement.executeQuery();

            isExists = resultSet.next();
        }
		return isExists;
	}

    public List<FlywaySchemaHistory> selectBy() throws SQLException  {
        return null;
	}


    public Optional<FlywaySchemaHistory> selectByInstalledRank(Long installedRank) throws SQLException {
        FlywaySchemaHistory flywaySchemaHistory = null;
            final String query = " SELECT installed_rank,\"version\",description,type,script,checksum,installed_by,installed_on,execution_time,success FROM flyway_schema_history WHERE installed_rank= ?";
            try (java.sql.Connection dbConnection = dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                preparedStatement.setLong(1,installedRank);
	
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) flywaySchemaHistory = rowMapper(resultSet);
            }
            return Optional.ofNullable(flywaySchemaHistory);
    }

    public boolean existsByInstalledRank(Long installedRank) throws SQLException {

            final String query = " SELECT 1 FROM flyway_schema_history WHERE installed_rank= ?";
            boolean isExists = false;
            try (java.sql.Connection dbConnection = dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                preparedStatement.setLong(1,installedRank);
	
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
            
            final Column.InstalledRankColumn installedRankColumn 
                        ,
            final Column.DescriptionColumn descriptionColumn 
                        ,
            final Column.TypeColumn typeColumn 
                        ,
            final Column.ScriptColumn scriptColumn 
                        ,
            final Column.InstalledByColumn installedByColumn 
                        ,
            final Column.InstalledOnColumn installedOnColumn 
                        ,
            final Column.ExecutionTimeColumn executionTimeColumn 
                        ,
            final Column.SuccessColumn successColumn 
            ) {
        return new InsertStatement(this
        );
    }
    public static final class InsertStatement {
        private final FlywaySchemaHistoryStore flywaySchemaHistoryStore;



        private InsertStatement(final FlywaySchemaHistoryStore flywaySchemaHistoryStore
                ) {
            this.flywaySchemaHistoryStore = flywaySchemaHistoryStore;
        }

        private void prepare(final PreparedStatement preparedStatement,final FlywaySchemaHistory flywaySchemaHistory) throws SQLException {
                preparedStatement.setLong(1,flywaySchemaHistory.getInstalledRank());
                preparedStatement.setString(2,flywaySchemaHistory.getVersion());
                preparedStatement.setString(3,flywaySchemaHistory.getDescription());
                preparedStatement.setString(4,flywaySchemaHistory.getType());
                preparedStatement.setString(5,flywaySchemaHistory.getScript());
                preparedStatement.setLong(6,flywaySchemaHistory.getChecksum());
                preparedStatement.setString(7,flywaySchemaHistory.getInstalledBy());
                preparedStatement.setTimestamp(8,flywaySchemaHistory.getInstalledOn() == null ? null : java.sql.Timestamp.valueOf(flywaySchemaHistory.getInstalledOn()));
                preparedStatement.setLong(9,flywaySchemaHistory.getExecutionTime());
                preparedStatement.setBoolean(10,flywaySchemaHistory.getSuccess());
        }

        public final ValueClause values(final FlywaySchemaHistory flywaySchemaHistory) {
            return new ValueClause(flywaySchemaHistory,this);
        }

        public final ValuesClause values(final List<FlywaySchemaHistory> listOfFlywaySchemaHistory) {
            return new ValuesClause(listOfFlywaySchemaHistory,this);
        }

        public static final class ValueClause  {
            
            private final InsertStatement insertStatement;

            private FlywaySchemaHistory flywaySchemaHistory;

            ValueClause (final FlywaySchemaHistory flywaySchemaHistory,final InsertStatement insertStatement) {
                this.flywaySchemaHistory = flywaySchemaHistory;
                this.insertStatement = insertStatement;
            }

            public final ValuesClause values(final FlywaySchemaHistory theFlywaySchemaHistory) {
                List<FlywaySchemaHistory> listOfFlywaySchemaHistory = new ArrayList<>();
                listOfFlywaySchemaHistory.add(flywaySchemaHistory);
                listOfFlywaySchemaHistory.add(theFlywaySchemaHistory);
                return new ValuesClause(listOfFlywaySchemaHistory,insertStatement);
            }
            

            public final int execute() throws SQLException  {
                int insertedRows = 0;
  final String query = "INSERT INTO flyway_schema_history ( installed_rank ,\"version\" ,description ,type ,script ,checksum ,installed_by ,installed_on ,execution_time ,success ) VALUES ( ? ,? ,? ,? ,? ,? ,? ,? ,? ,? ) ";

                try (java.sql.Connection dbConnection = insertStatement.flywaySchemaHistoryStore.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    this.insertStatement.prepare(preparedStatement,flywaySchemaHistory);
                    insertedRows = preparedStatement.executeUpdate();
                }
                return insertedRows;
            }


            public final FlywaySchemaHistory returning() throws SQLException  {
                FlywaySchemaHistory insertedFlywaySchemaHistory = null ;
  final String query = "INSERT INTO flyway_schema_history ( installed_rank ,\"version\" ,description ,type ,script ,checksum ,installed_by ,installed_on ,execution_time ,success ) VALUES ( ? ,? ,? ,? ,? ,? ,? ,? ,? ,? ) ";

                try (java.sql.Connection dbConnection = insertStatement.flywaySchemaHistoryStore.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    this.insertStatement.prepare(preparedStatement,flywaySchemaHistory);

                        if( preparedStatement.executeUpdate() == 1 ) {
                             insertedFlywaySchemaHistory = flywaySchemaHistory;
                        }


                }
                return insertedFlywaySchemaHistory;
            }
        }

        public static final class ValuesClause  {

            
            private final InsertStatement insertStatement;

            private List<FlywaySchemaHistory> listOfFlywaySchemaHistory;

            ValuesClause(final List<FlywaySchemaHistory> theListOfFlywaySchemaHistory,final InsertStatement insertStatement) {
                this.listOfFlywaySchemaHistory = theListOfFlywaySchemaHistory;
                this.insertStatement = insertStatement;
            }

            public final ValuesClause values(final FlywaySchemaHistory theFlywaySchemaHistory) {
                this.listOfFlywaySchemaHistory.add(theFlywaySchemaHistory);
                return this;
            }

            public final int[] execute() throws SQLException  {
                int[] insertedRows = null;
  final String query = "INSERT INTO flyway_schema_history ( installed_rank ,\"version\" ,description ,type ,script ,checksum ,installed_by ,installed_on ,execution_time ,success ) VALUES ( ? ,? ,? ,? ,? ,? ,? ,? ,? ,? ) ";
                
                try (java.sql.Connection dbConnection = insertStatement.flywaySchemaHistoryStore.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    for (FlywaySchemaHistory flywaySchemaHistory:listOfFlywaySchemaHistory) {
                        this.insertStatement.prepare(preparedStatement, flywaySchemaHistory);
                        preparedStatement.addBatch();
                    }
                    insertedRows = preparedStatement.executeBatch();
                }
                return insertedRows;
            }
            public final List<FlywaySchemaHistory> returning() throws SQLException  {
                List<FlywaySchemaHistory> insertedList = null ;
  final String query = "INSERT INTO flyway_schema_history ( installed_rank ,\"version\" ,description ,type ,script ,checksum ,installed_by ,installed_on ,execution_time ,success ) VALUES ( ? ,? ,? ,? ,? ,? ,? ,? ,? ,? ) ";

                try (java.sql.Connection dbConnection = insertStatement.flywaySchemaHistoryStore.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    for (FlywaySchemaHistory flywaySchemaHistory:listOfFlywaySchemaHistory) {
                        this.insertStatement.prepare(preparedStatement, flywaySchemaHistory);
                        preparedStatement.addBatch();
                    }

                    int[] insertedCounts = preparedStatement.executeBatch();

                    if ( insertedCounts != null ) {
                        insertedList = new ArrayList<>();


                        for (int i = 0; i < insertedCounts.length; i++) {

                          int insertedRows = insertedCounts[i];
                          if (insertedRows == 1) {
                            insertedList.add(insertStatement.flywaySchemaHistoryStore.select(listOfFlywaySchemaHistory.get(i).getInstalledRank()).get());
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

public int delete(Long installedRank) throws SQLException  {
		final String query = "DELETE FROM flyway_schema_history WHERE installed_rank = ?";
        try (java.sql.Connection dbConnection = dbDataSource.getConnection();
			PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
			preparedStatement.setLong(1,installedRank);
	
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

    private final FlywaySchemaHistoryStore flywaySchemaHistoryStore;
    private final WhereClause whereClause;

    private DeleteStatement(final FlywaySchemaHistoryStore flywaySchemaHistoryStore) {
            this(flywaySchemaHistoryStore,null);
        }

        private DeleteStatement(final FlywaySchemaHistoryStore flywaySchemaHistoryStore
                ,final WhereClause whereClause) {
            this.flywaySchemaHistoryStore = flywaySchemaHistoryStore;
            this.whereClause = whereClause;
        }

    public int execute() throws SQLException  {
    	final String query = "DELETE FROM flyway_schema_history" 
        + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
        try (java.sql.Connection dbConnection = this.flywaySchemaHistoryStore.dbDataSource.getConnection();
			Statement statement = dbConnection.createStatement()) {
            return statement.executeUpdate(query);
        }
	}
}





public int update(FlywaySchemaHistory flywaySchemaHistory) throws SQLException {
        final String query = " UPDATE flyway_schema_history SET \"version\" = ? ,description = ? ,type = ? ,script = ? ,checksum = ? ,installed_by = ? ,installed_on = ? ,execution_time = ? ,success = ? WHERE installed_rank = ?";

		try (java.sql.Connection dbConnection = dbDataSource.getConnection();
             PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
        {


            preparedStatement.setString(1,flywaySchemaHistory.getVersion());

            preparedStatement.setString(2,flywaySchemaHistory.getDescription());

            preparedStatement.setString(3,flywaySchemaHistory.getType());

            preparedStatement.setString(4,flywaySchemaHistory.getScript());

            preparedStatement.setLong(5,flywaySchemaHistory.getChecksum());

            preparedStatement.setString(6,flywaySchemaHistory.getInstalledBy());

            preparedStatement.setTimestamp(7,flywaySchemaHistory.getInstalledOn() == null ? null : java.sql.Timestamp.valueOf(flywaySchemaHistory.getInstalledOn()));

            preparedStatement.setLong(8,flywaySchemaHistory.getExecutionTime());

            preparedStatement.setBoolean(9,flywaySchemaHistory.getSuccess());


            preparedStatement.setLong(10,flywaySchemaHistory.getInstalledRank());

			return preparedStatement.executeUpdate();
        }
	}

	public final UpdateStatement update() {
        return new UpdateStatement(this
                                               
        );
    }

    public static final class UpdateStatement {
        private final FlywaySchemaHistoryStore flywaySchemaHistoryStore;


        private UpdateStatement(final FlywaySchemaHistoryStore flywaySchemaHistoryStore
                ) {
            this.flywaySchemaHistoryStore = flywaySchemaHistoryStore;
        }

        private void prepare(final PreparedStatement preparedStatement,final FlywaySchemaHistory flywaySchemaHistory) throws SQLException {
                    preparedStatement.setString(1,flywaySchemaHistory.getVersion());
                    preparedStatement.setString(2,flywaySchemaHistory.getDescription());
                    preparedStatement.setString(3,flywaySchemaHistory.getType());
                    preparedStatement.setString(4,flywaySchemaHistory.getScript());
                    preparedStatement.setLong(5,flywaySchemaHistory.getChecksum());
                    preparedStatement.setString(6,flywaySchemaHistory.getInstalledBy());
                    preparedStatement.setTimestamp(7,flywaySchemaHistory.getInstalledOn() == null ? null : java.sql.Timestamp.valueOf(flywaySchemaHistory.getInstalledOn()));
                    preparedStatement.setLong(8,flywaySchemaHistory.getExecutionTime());
                    preparedStatement.setBoolean(9,flywaySchemaHistory.getSuccess());

                preparedStatement.setLong(10,flywaySchemaHistory.getInstalledRank());
        }


        public SetByPKClause set(final FlywaySchemaHistory flywaySchemaHistory) {
            return new SetByPKClause(flywaySchemaHistoryStore.dbDataSource,flywaySchemaHistory,this);
        }

        public static final class SetByPKClause  {
                private final javax.sql.DataSource dbDataSource;
                private final UpdateStatement updateStatement;
                private FlywaySchemaHistory flywaySchemaHistory;

                SetByPKClause(final javax.sql.DataSource dbDataSource,final FlywaySchemaHistory flywaySchemaHistory,final UpdateStatement updateStatement) {
                    this.dbDataSource = dbDataSource;
                    this.flywaySchemaHistory = flywaySchemaHistory;
                    this.updateStatement = updateStatement;
                }

                public final int execute() throws SQLException  {
                    int updtedRows = 0;
  final String query = " UPDATE flyway_schema_history SET \"version\" = ? ,description = ? ,type = ? ,script = ? ,checksum = ? ,installed_by = ? ,installed_on = ? ,execution_time = ? ,success = ? WHERE installed_rank = ?";

                    try (java.sql.Connection dbConnection = dbDataSource.getConnection();
                        PreparedStatement preparedStatement = dbConnection.prepareStatement(query)){

                        this.updateStatement.prepare(preparedStatement,flywaySchemaHistory);

                        updtedRows = preparedStatement.executeUpdate();
                    }
                    return updtedRows;
                }


                public final FlywaySchemaHistory returning() throws SQLException  {
                    FlywaySchemaHistory updatedFlywaySchemaHistory = null ;
  final String query = " UPDATE flyway_schema_history SET \"version\" = ? ,description = ? ,type = ? ,script = ? ,checksum = ? ,installed_by = ? ,installed_on = ? ,execution_time = ? ,success = ? WHERE installed_rank = ?";

                    try (java.sql.Connection dbConnection = updateStatement.flywaySchemaHistoryStore.dbDataSource.getConnection();
                        PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                    {
                        this.updateStatement.prepare(preparedStatement,flywaySchemaHistory);
                        if( preparedStatement.executeUpdate() == 1 ) {
                        updatedFlywaySchemaHistory =  updateStatement.flywaySchemaHistoryStore.select(flywaySchemaHistory.getInstalledRank()).get();
                    }
                    }
                    return updatedFlywaySchemaHistory;
                }
            }

        public SetClause set(final Value... values) {
            return new SetClause(values,this);
        }

        public static final class SetClause  {
            
            private final UpdateStatement updateStatement;

            private FlywaySchemaHistory flywaySchemaHistory;

            private Value[] values;

            SetClause(final Value[] values,final UpdateStatement updateStatement) {
                this.values = values;
                this.updateStatement = updateStatement;
            }

            SetClause(final FlywaySchemaHistory flywaySchemaHistory,final UpdateStatement updateStatement) {
                this.flywaySchemaHistory = flywaySchemaHistory;
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

                public final List<FlywaySchemaHistory> returning() throws SQLException  {
                    return null;
                }
            }


            

            
        }




    }	






  private FlywaySchemaHistory rowMapperForReturning(final ResultSet rs,final FlywaySchemaHistory insertingFlywaySchemaHistory) throws SQLException {
    final FlywaySchemaHistory flywaySchemaHistory = new FlywaySchemaHistory();
        flywaySchemaHistory.setInstalledRank(insertingFlywaySchemaHistory.getInstalledRank());
        flywaySchemaHistory.setVersion(insertingFlywaySchemaHistory.getVersion());
        flywaySchemaHistory.setDescription(insertingFlywaySchemaHistory.getDescription());
        flywaySchemaHistory.setType(insertingFlywaySchemaHistory.getType());
        flywaySchemaHistory.setScript(insertingFlywaySchemaHistory.getScript());
        flywaySchemaHistory.setChecksum(insertingFlywaySchemaHistory.getChecksum());
        flywaySchemaHistory.setInstalledBy(insertingFlywaySchemaHistory.getInstalledBy());
        flywaySchemaHistory.setInstalledOn(insertingFlywaySchemaHistory.getInstalledOn());
        flywaySchemaHistory.setExecutionTime(insertingFlywaySchemaHistory.getExecutionTime());
        flywaySchemaHistory.setSuccess(insertingFlywaySchemaHistory.getSuccess());
        return flywaySchemaHistory;
    }

	private FlywaySchemaHistory rowMapper(ResultSet rs) throws SQLException {
        final FlywaySchemaHistory flywaySchemaHistory = new FlywaySchemaHistory();
          flywaySchemaHistory.setInstalledRank(rs.getLong(1));
          flywaySchemaHistory.setVersion(rs.getString(2));
          flywaySchemaHistory.setDescription(rs.getString(3));
          flywaySchemaHistory.setType(rs.getString(4));
          flywaySchemaHistory.setScript(rs.getString(5));
          flywaySchemaHistory.setChecksum(rs.getLong(6));
          flywaySchemaHistory.setInstalledBy(rs.getString(7));
             flywaySchemaHistory.setInstalledOn(rs.getTimestamp(8) == null ? null : rs.getTimestamp(8).toLocalDateTime());
          flywaySchemaHistory.setExecutionTime(rs.getLong(9));
          flywaySchemaHistory.setSuccess(rs.getBoolean(10));
        return flywaySchemaHistory;
    }




    public static Value<Column.InstalledRankColumn,Long> installedRank(final Long value) {
        return new Value<>(installedRank(),value);
    }
    
    public static Column.InstalledRankColumn installedRank() {
        return new WhereClause().installedRank();
    }


    public static Value<Column.VersionColumn,String> version(final String value) {
        return new Value<>(version(),value);
    }
    
    public static Column.VersionColumn version() {
        return new WhereClause().version();
    }


    public static Value<Column.DescriptionColumn,String> description(final String value) {
        return new Value<>(description(),value);
    }
    
    public static Column.DescriptionColumn description() {
        return new WhereClause().description();
    }


    public static Value<Column.TypeColumn,String> type(final String value) {
        return new Value<>(type(),value);
    }
    
    public static Column.TypeColumn type() {
        return new WhereClause().type();
    }


    public static Value<Column.ScriptColumn,String> script(final String value) {
        return new Value<>(script(),value);
    }
    
    public static Column.ScriptColumn script() {
        return new WhereClause().script();
    }


    public static Value<Column.ChecksumColumn,Long> checksum(final Long value) {
        return new Value<>(checksum(),value);
    }
    
    public static Column.ChecksumColumn checksum() {
        return new WhereClause().checksum();
    }


    public static Value<Column.InstalledByColumn,String> installedBy(final String value) {
        return new Value<>(installedBy(),value);
    }
    
    public static Column.InstalledByColumn installedBy() {
        return new WhereClause().installedBy();
    }


    public static Value<Column.InstalledOnColumn,LocalDateTime> installedOn(final LocalDateTime value) {
        return new Value<>(installedOn(),value);
    }
    
    public static Column.InstalledOnColumn installedOn() {
        return new WhereClause().installedOn();
    }


    public static Value<Column.ExecutionTimeColumn,Long> executionTime(final Long value) {
        return new Value<>(executionTime(),value);
    }
    
    public static Column.ExecutionTimeColumn executionTime() {
        return new WhereClause().executionTime();
    }


    public static Value<Column.SuccessColumn,Boolean> success(final Boolean value) {
        return new Value<>(success(),value);
    }
    
    public static Column.SuccessColumn success() {
        return new WhereClause().success();
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

        public Column.InstalledRankColumn installedRank() {
            Column.InstalledRankColumn query = new Column.InstalledRankColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.VersionColumn version() {
            Column.VersionColumn query = new Column.VersionColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.DescriptionColumn description() {
            Column.DescriptionColumn query = new Column.DescriptionColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.TypeColumn type() {
            Column.TypeColumn query = new Column.TypeColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ScriptColumn script() {
            Column.ScriptColumn query = new Column.ScriptColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ChecksumColumn checksum() {
            Column.ChecksumColumn query = new Column.ChecksumColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.InstalledByColumn installedBy() {
            Column.InstalledByColumn query = new Column.InstalledByColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.InstalledOnColumn installedOn() {
            Column.InstalledOnColumn query = new Column.InstalledOnColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ExecutionTimeColumn executionTime() {
            Column.ExecutionTimeColumn query = new Column.ExecutionTimeColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.SuccessColumn success() {
            Column.SuccessColumn query = new Column.SuccessColumn(this);
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

public static class InstalledRankColumn extends Column<Long> {
    private String sql;

    public InstalledRankColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }

    public final WhereClause eq(final Long value) {
        sql = "installed_rank =" + value;
        return getWhereClause();
    }

    public final WhereClause gt(final Long value) {
        sql = "installed_rank >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final Long value) {
        sql = "installed_rank >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final Long value) {
        sql = "installed_rank <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final Long value) {
        sql = "installed_rank <=" + value;
        return getWhereClause();
    }

    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(Long value) {
        return true;
    }
}
public static class VersionColumn extends Column<String> {
    private String sql;

    public VersionColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "\"version\" ='" + value + "'";
        return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
        sql = "\"version\" LIKE '" + value + "'";
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
public static class DescriptionColumn extends Column<String> {
    private String sql;

    public DescriptionColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "description ='" + value + "'";
        return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
        sql = "description LIKE '" + value + "'";
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
public static class TypeColumn extends Column<String> {
    private String sql;

    public TypeColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "type ='" + value + "'";
        return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
        sql = "type LIKE '" + value + "'";
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
public static class ScriptColumn extends Column<String> {
    private String sql;

    public ScriptColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "script ='" + value + "'";
        return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
        sql = "script LIKE '" + value + "'";
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
public static class ChecksumColumn extends Column<Long> {
    private String sql;

    public ChecksumColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }

    public final WhereClause eq(final Long value) {
        sql = "checksum =" + value;
        return getWhereClause();
    }

    public final WhereClause gt(final Long value) {
        sql = "checksum >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final Long value) {
        sql = "checksum >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final Long value) {
        sql = "checksum <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final Long value) {
        sql = "checksum <=" + value;
        return getWhereClause();
    }

    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(Long value) {
        return true;
    }
}
public static class InstalledByColumn extends Column<String> {
    private String sql;

    public InstalledByColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "installed_by ='" + value + "'";
        return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
        sql = "installed_by LIKE '" + value + "'";
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
public static class InstalledOnColumn extends Column<LocalDateTime> {
    private String sql;

    public InstalledOnColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }

    public final WhereClause  eq(final LocalDateTime value) {
        sql = "installed_on =" + value;
        return getWhereClause();
    }

    public final WhereClause  gt(final LocalDateTime value) {
        sql = "installed_on >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final LocalDateTime value) {
        sql = "installed_on >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final LocalDateTime value) {
        sql = "installed_on <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final LocalDateTime value) {
        sql = "installed_on <=" + value;
        return getWhereClause();
    }

    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(LocalDateTime value) {
        return true;
    }
}
public static class ExecutionTimeColumn extends Column<Long> {
    private String sql;

    public ExecutionTimeColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }

    public final WhereClause eq(final Long value) {
        sql = "execution_time =" + value;
        return getWhereClause();
    }

    public final WhereClause gt(final Long value) {
        sql = "execution_time >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final Long value) {
        sql = "execution_time >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final Long value) {
        sql = "execution_time <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final Long value) {
        sql = "execution_time <=" + value;
        return getWhereClause();
    }

    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(Long value) {
        return true;
    }
}
public static class SuccessColumn extends Column<Boolean> {
    private String sql;

    public SuccessColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final Boolean value) {
        sql = "success =" + value ;
        return getWhereClause();
    }
    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(Boolean value) {
        return true;
    }
}

        }
    

}
