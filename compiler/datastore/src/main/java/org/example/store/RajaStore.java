
package org.example.store;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.stream.Collectors;

import java.nio.ByteBuffer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.example.model.Raja;
import org.example.RajaManager;
import org.json.JSONObject;

/**
 * Datastore for the table - raja.
 */
public final class RajaStore  {

    private final javax.sql.DataSource dbDataSource;

    private final RajaManager.Observer observer;


    private final RajaManager.GetFunction<ResultSet, Integer, Duration> getInterval;
    private final RajaManager.ConvertFunction<Duration,Object> convertInterval;
    private final RajaManager.GetFunction<ResultSet, Integer, JSONObject> getJson;
    private final RajaManager.ConvertFunction<JSONObject,Object> convertJson;
    private final RajaManager.GetFunction<ResultSet, Integer, JSONObject> getJsonb;
    private final RajaManager.ConvertFunction<JSONObject,Object> convertJsonb;
    private final RajaManager.GetFunction<ResultSet, Integer, UUID> getUuid;
    private final RajaManager.ConvertFunction<UUID,Object> convertUuid;

        private final Function<String,String> encryptionFunction;
        private final Function<String,String> decryptionFunction;

    /**
     * Datastore
     */
    public RajaStore(final javax.sql.DataSource theDataSource
                ,final RajaManager.Observer theObserver
                    ,final RajaManager.GetFunction<ResultSet, Integer, Duration> theGetInterval
                    ,final RajaManager.ConvertFunction<Duration,Object> theConvertInterval
                    ,final RajaManager.GetFunction<ResultSet, Integer, JSONObject> theGetJson
                    ,final RajaManager.ConvertFunction<JSONObject,Object> theConvertJson
                    ,final RajaManager.GetFunction<ResultSet, Integer, JSONObject> theGetJsonb
                    ,final RajaManager.ConvertFunction<JSONObject,Object> theConvertJsonb
                    ,final RajaManager.GetFunction<ResultSet, Integer, UUID> theGetUuid
                    ,final RajaManager.ConvertFunction<UUID,Object> theConvertUuid

                        ,final Function<String,String> encryptionFunction
                        ,final Function<String,String> decryptionFunction
                ) {
        this.dbDataSource = theDataSource;
        this.observer = theObserver;
        this.getInterval =  theGetInterval;
        this.convertInterval =  theConvertInterval;
        this.getJson =  theGetJson;
        this.convertJson =  theConvertJson;
        this.getJsonb =  theGetJsonb;
        this.convertJsonb =  theConvertJsonb;
        this.getUuid =  theGetUuid;
        this.convertUuid =  theConvertUuid;
            this.encryptionFunction = encryptionFunction;
            this.decryptionFunction = decryptionFunction;
    }


public SelectStatement select() {
        return new SelectStatement(this);
}
public SelectStatement select(WhereClause whereClause) throws SQLException  {
        return new SelectStatement(this,whereClause);
}

public static final class SelectStatement {

        private final RajaStore rajaStore;
        private final WhereClause whereClause;

        private LimitClause limitClause;
        private LimitClause.OffsetClause offsetClause;

        public LimitClause limit(final int limit) {
                return new LimitClause(this,limit);
        }

        private SelectStatement(final RajaStore rajaStore) {
            this(rajaStore,null);
        }

        private SelectStatement(final RajaStore rajaStore
                ,final WhereClause whereClause) {
            this.rajaStore = rajaStore;
            this.whereClause = whereClause;
        }

        public List<Raja> execute() throws SQLException {
		final String query = " SELECT id,a_boolean,reference_code,a_char,a_text,a_encrypted_text,a_smallint,a_integer,a_bigint,a_decimal,a_numeric,a_real,a_double,a_smallserial,a_serial,a_bigserial,a_date,a_blob,json,a_jsonb,a_uuid,a_xml,a_time,a_interval FROM raja" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) )
                + ( this.limitClause == null ? "" : this.limitClause.asSql() )
                + ( this.offsetClause == null ? "" : this.offsetClause.asSql() );
                try (java.sql.Connection dbConnection = this.rajaStore.dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                
                ResultSet resultSet = preparedStatement.executeQuery();
                                List<Raja> arrays = new ArrayList();
                while (resultSet.next()) {
                                        arrays.add(this.rajaStore.rowMapper(resultSet));
                                }
                                return arrays;
                } 
	}

        public int count() throws SQLException {
                int count = 0;
		final String query = " SELECT COUNT(*) FROM raja" 
                + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
                try (java.sql.Connection dbConnection = this.rajaStore.dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                
                ResultSet resultSet = preparedStatement.executeQuery();
                                List<Raja> arrays = new ArrayList();
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

                public RajaManager.Page<Raja> execute() throws SQLException {
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

                        public RajaManager.Page<Raja> execute() throws SQLException {
                                return this.limitClause.execute();
                        }


        }

        }
}



    

    public Optional<Raja> select(Long id) throws SQLException  {
        Raja raja = null;
		final String query = " SELECT id,a_boolean,reference_code,a_char,a_text,a_encrypted_text,a_smallint,a_integer,a_bigint,a_decimal,a_numeric,a_real,a_double,a_smallserial,a_serial,a_bigserial,a_date,a_blob,json,a_jsonb,a_uuid,a_xml,a_time,a_interval FROM raja WHERE id = ?";
        try (java.sql.Connection dbConnection = dbDataSource.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
            preparedStatement.setLong(1,id);
	
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) raja = rowMapper(resultSet);
        }
        return Optional.ofNullable(raja);
	}

    public boolean exists(Long id) throws SQLException {
        final String query = " SELECT 1 FROM raja WHERE id = ?";
        boolean isExists = false;
        try (java.sql.Connection dbConnection = dbDataSource.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
            preparedStatement.setLong(1,id);
	
            ResultSet resultSet = preparedStatement.executeQuery();

            isExists = resultSet.next();
        }
		return isExists;
	}

    public List<Raja> selectBy() throws SQLException  {
        return null;
	}


    public Optional<Raja> selectById(Long id) throws SQLException {
        Raja raja = null;
            final String query = " SELECT id,a_boolean,reference_code,a_char,a_text,a_encrypted_text,a_smallint,a_integer,a_bigint,a_decimal,a_numeric,a_real,a_double,a_smallserial,a_serial,a_bigserial,a_date,a_blob,json,a_jsonb,a_uuid,a_xml,a_time,a_interval FROM raja WHERE id= ?";
            try (java.sql.Connection dbConnection = dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                preparedStatement.setLong(1,id);
	
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) raja = rowMapper(resultSet);
            }
            return Optional.ofNullable(raja);
    }

    public boolean existsById(Long id) throws SQLException {

            final String query = " SELECT 1 FROM raja WHERE id= ?";
            boolean isExists = false;
            try (java.sql.Connection dbConnection = dbDataSource.getConnection();
                PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
                preparedStatement.setLong(1,id);
	
                ResultSet resultSet = preparedStatement.executeQuery();

                isExists = resultSet.next();
            }
            return isExists;
    }








	public final InsertStatement insert() {
        return new InsertStatement(this
                                                      ,this.convertInterval
                                                      ,this.convertJson
                                                      ,this.convertJsonb
                                                      ,this.convertUuid
            ,this.encryptionFunction
            ,this.decryptionFunction
        );
    }

    public static final class InsertStatement {
        private final RajaStore rajaStore;

        private final RajaManager.ConvertFunction<Duration,Object> convertInterval;
        private final RajaManager.ConvertFunction<JSONObject,Object> convertJson;
        private final RajaManager.ConvertFunction<JSONObject,Object> convertJsonb;
        private final RajaManager.ConvertFunction<UUID,Object> convertUuid;

            private final Function<String,String> encryptionFunction;
            private final Function<String,String> decryptionFunction;

        private InsertStatement(final RajaStore rajaStore
         ,final RajaManager.ConvertFunction<Duration,Object> theConvertInterval
         ,final RajaManager.ConvertFunction<JSONObject,Object> theConvertJson
         ,final RajaManager.ConvertFunction<JSONObject,Object> theConvertJsonb
         ,final RajaManager.ConvertFunction<UUID,Object> theConvertUuid
            ,final Function<String,String> encryptionFunction
            ,final Function<String,String> decryptionFunction
                ) {
            this.rajaStore = rajaStore;
            this.convertInterval =  theConvertInterval;
            this.convertJson =  theConvertJson;
            this.convertJsonb =  theConvertJsonb;
            this.convertUuid =  theConvertUuid;
            this.encryptionFunction = encryptionFunction;
            this.decryptionFunction = decryptionFunction;
        }

        private void prepare(final PreparedStatement preparedStatement,final Raja raja) throws SQLException {
                preparedStatement.setBoolean(1,raja.getABoolean());
                preparedStatement.setString(2,raja.getReferenceCode());
                preparedStatement.setString(3,raja.getAChar() == null ? null : String.valueOf(raja.getAChar()));
                preparedStatement.setString(4,raja.getAText());
                preparedStatement.setString(5,this.encryptionFunction.apply(raja.getAEncryptedText()));
                preparedStatement.setShort(6,raja.getASmallint());
                preparedStatement.setLong(7,raja.getABigint());
                preparedStatement.setByte(8,raja.getADecimal());
                preparedStatement.setByte(9,raja.getANumeric());
                preparedStatement.setFloat(10,raja.getAReal());
                preparedStatement.setDouble(11,raja.getADouble());
                preparedStatement.setDate(12,raja.getADate() == null ? null : java.sql.Date.valueOf(raja.getADate()));
                preparedStatement.setBytes(13,raja.getABlob() == null ? null : raja.getABlob().array());
                preparedStatement.setObject(14,this.convertJson.apply(raja.getJson()));
                preparedStatement.setObject(15,this.convertJsonb.apply(raja.getAJsonb()));
                preparedStatement.setObject(16,this.convertUuid.apply(raja.getAUuid()));
                preparedStatement.setString(17,raja.getAXml());
                preparedStatement.setTime(18,raja.getATime() == null ? null : java.sql.Time.valueOf(raja.getATime()));
                preparedStatement.setObject(19,this.convertInterval.apply(raja.getAInterval()));
        }

        public final ValueClause values(final Raja raja) {
            return new ValueClause(raja,this);
        }

        public final ValuesClause values(final List<Raja> listOfRaja) {
            return new ValuesClause(listOfRaja,this);
        }

        public static final class ValueClause  {
            
            private final InsertStatement insertStatement;

            private Raja raja;

            ValueClause (final Raja raja,final InsertStatement insertStatement) {
                this.raja = raja;
                this.insertStatement = insertStatement;
            }

            public final ValuesClause values(final Raja theRaja) {
                List<Raja> listOfRaja = new ArrayList<>();
                listOfRaja.add(raja);
                listOfRaja.add(theRaja);
                return new ValuesClause(listOfRaja,insertStatement);
            }
            

            public final int execute() throws SQLException  {
                int insertedRows = 0;
  final String query = "INSERT INTO raja ( a_boolean ,reference_code ,a_char ,a_text ,a_encrypted_text ,a_smallint ,a_integer ,a_bigint ,a_decimal ,a_numeric ,a_real ,a_double ,a_date ,a_blob ,json ,a_jsonb ,a_uuid ,a_xml ,a_time ,a_interval ) VALUES ( ? ,? ,? ,? ,? ,? ,4 ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,XMLPARSE(document ?) ,? ,? ) ";

                try (java.sql.Connection dbConnection = insertStatement.rajaStore.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    this.insertStatement.prepare(preparedStatement,raja);
                    insertedRows = preparedStatement.executeUpdate();
                }
                return insertedRows;
            }


            public final Raja returning() throws SQLException  {
                Raja insertedRaja = null ;
  final String query = "INSERT INTO raja ( a_boolean ,reference_code ,a_char ,a_text ,a_encrypted_text ,a_smallint ,a_integer ,a_bigint ,a_decimal ,a_numeric ,a_real ,a_double ,a_date ,a_blob ,json ,a_jsonb ,a_uuid ,a_xml ,a_time ,a_interval ) VALUES ( ? ,? ,? ,? ,? ,? ,4 ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,XMLPARSE(document ?) ,? ,? ) returning id,a_integer,a_smallserial,a_serial,a_bigserial ";

                try (java.sql.Connection dbConnection = insertStatement.rajaStore.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    this.insertStatement.prepare(preparedStatement,raja);

                                ResultSet resultSet = preparedStatement.executeQuery();

                                if (resultSet.next()) insertedRaja = this.insertStatement.rajaStore.rowMapperForReturning(resultSet, raja);


                }
                return insertedRaja;
            }
        }

        public static final class ValuesClause  {

            
            private final InsertStatement insertStatement;

            private List<Raja> listOfRaja;

            ValuesClause(final List<Raja> theListOfRaja,final InsertStatement insertStatement) {
                this.listOfRaja = theListOfRaja;
                this.insertStatement = insertStatement;
            }

            public final ValuesClause values(final Raja theRaja) {
                this.listOfRaja.add(theRaja);
                return this;
            }

            public final int[] execute() throws SQLException  {
                int[] insertedRows = null;
  final String query = "INSERT INTO raja ( a_boolean ,reference_code ,a_char ,a_text ,a_encrypted_text ,a_smallint ,a_integer ,a_bigint ,a_decimal ,a_numeric ,a_real ,a_double ,a_date ,a_blob ,json ,a_jsonb ,a_uuid ,a_xml ,a_time ,a_interval ) VALUES ( ? ,? ,? ,? ,? ,? ,4 ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,XMLPARSE(document ?) ,? ,? ) ";
                
                try (java.sql.Connection dbConnection = insertStatement.rajaStore.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
                {
                    for (Raja raja:listOfRaja) {
                        this.insertStatement.prepare(preparedStatement, raja);
                        preparedStatement.addBatch();
                    }
                    insertedRows = preparedStatement.executeBatch();
                }
                return insertedRows;
            }
            public final List<Raja> returning() throws SQLException  {
                List<Raja> insertedList = null ;
  final String query = "INSERT INTO raja ( a_boolean ,reference_code ,a_char ,a_text ,a_encrypted_text ,a_smallint ,a_integer ,a_bigint ,a_decimal ,a_numeric ,a_real ,a_double ,a_date ,a_blob ,json ,a_jsonb ,a_uuid ,a_xml ,a_time ,a_interval ) VALUES ( ? ,? ,? ,? ,? ,? ,4 ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,XMLPARSE(document ?) ,? ,? ) returning id,a_integer,a_smallserial,a_serial,a_bigserial ";

                try (java.sql.Connection dbConnection = insertStatement.rajaStore.dbDataSource.getConnection();
                     PreparedStatement preparedStatement = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
                {
                    for (Raja raja:listOfRaja) {
                        this.insertStatement.prepare(preparedStatement, raja);
                        preparedStatement.addBatch();
                    }

                    int[] insertedCounts = preparedStatement.executeBatch();

                    if ( insertedCounts != null ) {
                        insertedList = new ArrayList<>();

                             ResultSet res = preparedStatement.getGeneratedKeys();

                        for (int i = 0; i < insertedCounts.length; i++) {

                          int insertedRows = insertedCounts[i];
                          if (insertedRows == 1 && res.next()) {
                            insertedList.add(insertStatement.rajaStore.select(res.getLong(1)).get()) ;
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

public int delete(Long id) throws SQLException  {
		final String query = "DELETE FROM raja WHERE id = ?";
        try (java.sql.Connection dbConnection = dbDataSource.getConnection();
			PreparedStatement preparedStatement = dbConnection.prepareStatement(query)) {
			preparedStatement.setLong(1,id);
	
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

    private final RajaStore rajaStore;
    private final WhereClause whereClause;

    private DeleteStatement(final RajaStore rajaStore) {
            this(rajaStore,null);
        }

        private DeleteStatement(final RajaStore rajaStore
                ,final WhereClause whereClause) {
            this.rajaStore = rajaStore;
            this.whereClause = whereClause;
        }

    public int execute() throws SQLException  {
    	final String query = "DELETE FROM raja" 
        + ( this.whereClause == null ? "" : (" WHERE " + this.whereClause.asSql()) );
        try (java.sql.Connection dbConnection = this.rajaStore.dbDataSource.getConnection();
			Statement statement = dbConnection.createStatement()) {
            return statement.executeUpdate(query);
        }
	}
}





public int update(Raja raja) throws SQLException {
        final String query = " UPDATE raja SET a_boolean = ? ,reference_code = ? ,a_char = ? ,a_text = ? ,a_encrypted_text = ? ,a_smallint = ? ,a_integer = 5 ,a_bigint = ? ,a_decimal = ? ,a_numeric = ? ,a_real = ? ,a_double = ? ,a_date = ? ,a_blob = ? ,json = ? ,a_jsonb = ? ,a_uuid = ? ,a_xml = XMLPARSE(document ?) ,a_time = ? ,a_interval = ? WHERE id = ?";

		try (java.sql.Connection dbConnection = dbDataSource.getConnection();
             PreparedStatement preparedStatement = dbConnection.prepareStatement(query))
        {

            preparedStatement.setBoolean(1,raja.getABoolean());

            preparedStatement.setString(2,raja.getReferenceCode());

            preparedStatement.setString(3,raja.getAChar() == null ? null : String.valueOf(raja.getAChar()));

            preparedStatement.setString(4,raja.getAText());

            preparedStatement.setString(5,this.encryptionFunction.apply(raja.getAEncryptedText()));

            preparedStatement.setShort(6,raja.getASmallint());

            preparedStatement.setLong(7,raja.getAInteger());

            preparedStatement.setLong(8,raja.getABigint());

            preparedStatement.setByte(9,raja.getADecimal());

            preparedStatement.setByte(10,raja.getANumeric());

            preparedStatement.setFloat(11,raja.getAReal());

            preparedStatement.setDouble(12,raja.getADouble());

            preparedStatement.setDate(13,raja.getADate() == null ? null : java.sql.Date.valueOf(raja.getADate()));

            preparedStatement.setBytes(14,raja.getABlob() == null ? null : raja.getABlob().array());

            preparedStatement.setObject(15,this.convertJson.apply(raja.getJson()));

            preparedStatement.setObject(16,this.convertJsonb.apply(raja.getAJsonb()));

            preparedStatement.setObject(17,this.convertUuid.apply(raja.getAUuid()));

            preparedStatement.setString(18,raja.getAXml());

            preparedStatement.setTime(19,raja.getATime() == null ? null : java.sql.Time.valueOf(raja.getATime()));

            preparedStatement.setObject(20,this.convertInterval.apply(raja.getAInterval()));


            preparedStatement.setLong(21,raja.getId());

			return preparedStatement.executeUpdate();
        }
	}

	public final UpdateStatement update() {
        return new UpdateStatement(this
                                                      ,this.convertInterval
                                                      ,this.convertJson
                                                      ,this.convertJsonb
                                                      ,this.convertUuid
                                               
            ,this.encryptionFunction
            ,this.decryptionFunction
        );
    }

    public static final class UpdateStatement {
        private final RajaStore rajaStore;

        private final RajaManager.ConvertFunction<Duration,Object> convertInterval;
        private final RajaManager.ConvertFunction<JSONObject,Object> convertJson;
        private final RajaManager.ConvertFunction<JSONObject,Object> convertJsonb;
        private final RajaManager.ConvertFunction<UUID,Object> convertUuid;
            private final Function<String,String> encryptionFunction;
            private final Function<String,String> decryptionFunction;

        private UpdateStatement(final RajaStore rajaStore
         ,final RajaManager.ConvertFunction<Duration,Object> theConvertInterval
         ,final RajaManager.ConvertFunction<JSONObject,Object> theConvertJson
         ,final RajaManager.ConvertFunction<JSONObject,Object> theConvertJsonb
         ,final RajaManager.ConvertFunction<UUID,Object> theConvertUuid
            ,final Function<String,String> encryptionFunction
            ,final Function<String,String> decryptionFunction
                ) {
            this.rajaStore = rajaStore;
            this.convertInterval =  theConvertInterval;
            this.convertJson =  theConvertJson;
            this.convertJsonb =  theConvertJsonb;
            this.convertUuid =  theConvertUuid;
            this.encryptionFunction = encryptionFunction;
            this.decryptionFunction = decryptionFunction;
        }

        private void prepare(final PreparedStatement preparedStatement,final Raja raja) throws SQLException {
                    preparedStatement.setBoolean(1,raja.getABoolean());
                    preparedStatement.setString(2,raja.getReferenceCode());
                    preparedStatement.setString(3,raja.getAChar() == null ? null : String.valueOf(raja.getAChar()));
                    preparedStatement.setString(4,raja.getAText());
                    preparedStatement.setString(5,this.encryptionFunction.apply(raja.getAEncryptedText()));
                    preparedStatement.setShort(6,raja.getASmallint());
                    preparedStatement.setLong(7,raja.getABigint());
                    preparedStatement.setByte(8,raja.getADecimal());
                    preparedStatement.setByte(9,raja.getANumeric());
                    preparedStatement.setFloat(10,raja.getAReal());
                    preparedStatement.setDouble(11,raja.getADouble());
                    preparedStatement.setDate(12,raja.getADate() == null ? null : java.sql.Date.valueOf(raja.getADate()));
                    preparedStatement.setBytes(13,raja.getABlob() == null ? null : raja.getABlob().array());
                    preparedStatement.setObject(14,this.convertJson.apply(raja.getJson()));
                    preparedStatement.setObject(15,this.convertJsonb.apply(raja.getAJsonb()));
                    preparedStatement.setObject(16,this.convertUuid.apply(raja.getAUuid()));
                    preparedStatement.setString(17,raja.getAXml());
                    preparedStatement.setTime(18,raja.getATime() == null ? null : java.sql.Time.valueOf(raja.getATime()));
                    preparedStatement.setObject(19,this.convertInterval.apply(raja.getAInterval()));

                preparedStatement.setLong(20,raja.getId());
        }


        public SetByPKClause set(final Raja raja) {
            return new SetByPKClause(rajaStore.dbDataSource,raja,this);
        }

        public static final class SetByPKClause  {
                private final javax.sql.DataSource dbDataSource;
                private final UpdateStatement updateStatement;
                private Raja raja;

                SetByPKClause(final javax.sql.DataSource dbDataSource,final Raja raja,final UpdateStatement updateStatement) {
                    this.dbDataSource = dbDataSource;
                    this.raja = raja;
                    this.updateStatement = updateStatement;
                }

                public final int execute() throws SQLException  {
                    int updtedRows = 0;
  final String query = " UPDATE raja SET a_boolean = ? ,reference_code = ? ,a_char = ? ,a_text = ? ,a_encrypted_text = ? ,a_smallint = ? ,a_integer = 5 ,a_bigint = ? ,a_decimal = ? ,a_numeric = ? ,a_real = ? ,a_double = ? ,a_date = ? ,a_blob = ? ,json = ? ,a_jsonb = ? ,a_uuid = ? ,a_xml = XMLPARSE(document ?) ,a_time = ? ,a_interval = ? WHERE id = ?";

                    try (java.sql.Connection dbConnection = dbDataSource.getConnection();
                        PreparedStatement preparedStatement = dbConnection.prepareStatement(query)){

                        this.updateStatement.prepare(preparedStatement,raja);

                        updtedRows = preparedStatement.executeUpdate();
                    }
                    return updtedRows;
                }


                public final Raja returning() throws SQLException  {
                    Raja updatedRaja = null ;
  final String query = " UPDATE raja SET a_boolean = ? ,reference_code = ? ,a_char = ? ,a_text = ? ,a_encrypted_text = ? ,a_smallint = ? ,a_integer = 5 ,a_bigint = ? ,a_decimal = ? ,a_numeric = ? ,a_real = ? ,a_double = ? ,a_date = ? ,a_blob = ? ,json = ? ,a_jsonb = ? ,a_uuid = ? ,a_xml = XMLPARSE(document ?) ,a_time = ? ,a_interval = ? WHERE id = ?";

                    try (java.sql.Connection dbConnection = updateStatement.rajaStore.dbDataSource.getConnection();
                        PreparedStatement preparedStatement = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
                    {
                        this.updateStatement.prepare(preparedStatement,raja);
                        if( preparedStatement.executeUpdate() == 1 ) {
                        ResultSet res = preparedStatement.getGeneratedKeys();
                        if (res.next()) {
                            updatedRaja =  updateStatement.rajaStore.select(res.getLong(1)).get();
                        }
                    }
                    }
                    return updatedRaja;
                }
            }

        public SetClause set(final Value... values) {
            return new SetClause(values,this);
        }

        public static final class SetClause  {
            
            private final UpdateStatement updateStatement;

            private Raja raja;

            private Value[] values;

            SetClause(final Value[] values,final UpdateStatement updateStatement) {
                this.values = values;
                this.updateStatement = updateStatement;
            }

            SetClause(final Raja raja,final UpdateStatement updateStatement) {
                this.raja = raja;
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

                public final List<Raja> returning() throws SQLException  {
                    return null;
                }
            }


            

            
        }




    }	






  private Raja rowMapperForReturning(final ResultSet rs,final Raja insertingRaja) throws SQLException {
    final Raja raja = new Raja();
          raja.setId(rs.getLong(1));
          raja.setAInteger(rs.getLong(2));
          raja.setASmallserial(rs.getShort(3));
          raja.setASerial(rs.getLong(4));
          raja.setABigserial(rs.getLong(5));
        raja.setABoolean(insertingRaja.getABoolean());
        raja.setReferenceCode(insertingRaja.getReferenceCode());
        raja.setAChar(insertingRaja.getAChar());
        raja.setAText(insertingRaja.getAText());
        raja.setAEncryptedText(insertingRaja.getAEncryptedText());
        raja.setASmallint(insertingRaja.getASmallint());
        raja.setABigint(insertingRaja.getABigint());
        raja.setADecimal(insertingRaja.getADecimal());
        raja.setANumeric(insertingRaja.getANumeric());
        raja.setAReal(insertingRaja.getAReal());
        raja.setADouble(insertingRaja.getADouble());
        raja.setADate(insertingRaja.getADate());
        raja.setABlob(insertingRaja.getABlob());
        raja.setJson(insertingRaja.getJson());
        raja.setAJsonb(insertingRaja.getAJsonb());
        raja.setAUuid(insertingRaja.getAUuid());
        raja.setAXml(insertingRaja.getAXml());
        raja.setATime(insertingRaja.getATime());
        raja.setAInterval(insertingRaja.getAInterval());
        return raja;
    }

	private Raja rowMapper(ResultSet rs) throws SQLException {
        final Raja raja = new Raja();
          raja.setId(rs.getLong(1));
          raja.setABoolean(rs.getBoolean(2));
          raja.setReferenceCode(rs.getString(3));
          	 raja.setAChar(rs.getString(4) == null ? null : rs.getString(4).charAt(0));
          raja.setAText(rs.getString(5));
            raja.setAEncryptedText(this.decryptionFunction.apply(rs.getString(6)));
          raja.setASmallint(rs.getShort(7));
          raja.setAInteger(rs.getLong(8));
          raja.setABigint(rs.getLong(9));
          raja.setADecimal(rs.getByte(10));
          raja.setANumeric(rs.getByte(11));
          raja.setAReal(rs.getFloat(12));
          raja.setADouble(rs.getDouble(13));
          raja.setASmallserial(rs.getShort(14));
          raja.setASerial(rs.getLong(15));
          raja.setABigserial(rs.getLong(16));
            raja.setADate(rs.getDate(17) == null ? null : rs.getDate(17).toLocalDate());
             raja.setABlob(rs.getBytes(18) == null ? null : ByteBuffer.wrap(rs.getBytes(18)));
        	    raja.setJson(this.getJson.apply(rs,19));
        	    raja.setAJsonb(this.getJsonb.apply(rs,20));
        	    raja.setAUuid(this.getUuid.apply(rs,21));
          raja.setAXml(rs.getString(22));
        	 raja.setATime(rs.getTime(23) == null ? null : rs.getTime(23).toLocalTime());
        	    raja.setAInterval(this.getInterval.apply(rs,24));
        return raja;
    }




    public static Value<Column.IdColumn,Long> id(final Long value) {
        return new Value<>(id(),value);
    }
    
    public static Column.IdColumn id() {
        return new WhereClause().id();
    }


    public static Value<Column.ABooleanColumn,Boolean> aBoolean(final Boolean value) {
        return new Value<>(aBoolean(),value);
    }
    
    public static Column.ABooleanColumn aBoolean() {
        return new WhereClause().aBoolean();
    }


    public static Value<Column.ReferenceCodeColumn,String> referenceCode(final String value) {
        return new Value<>(referenceCode(),value);
    }
    
    public static Column.ReferenceCodeColumn referenceCode() {
        return new WhereClause().referenceCode();
    }


    public static Value<Column.ACharColumn,Character> aChar(final Character value) {
        return new Value<>(aChar(),value);
    }
    
    public static Column.ACharColumn aChar() {
        return new WhereClause().aChar();
    }


    public static Value<Column.ATextColumn,String> aText(final String value) {
        return new Value<>(aText(),value);
    }
    
    public static Column.ATextColumn aText() {
        return new WhereClause().aText();
    }


    public static Value<Column.AEncryptedTextColumn,String> aEncryptedText(final String value) {
        return new Value<>(aEncryptedText(),value);
    }
    
    public static Column.AEncryptedTextColumn aEncryptedText() {
        return new WhereClause().aEncryptedText();
    }


    public static Value<Column.ASmallintColumn,Short> aSmallint(final Short value) {
        return new Value<>(aSmallint(),value);
    }
    
    public static Column.ASmallintColumn aSmallint() {
        return new WhereClause().aSmallint();
    }


    public static Value<Column.AIntegerColumn,Long> aInteger(final Long value) {
        return new Value<>(aInteger(),value);
    }
    
    public static Column.AIntegerColumn aInteger() {
        return new WhereClause().aInteger();
    }


    public static Value<Column.ABigintColumn,Long> aBigint(final Long value) {
        return new Value<>(aBigint(),value);
    }
    
    public static Column.ABigintColumn aBigint() {
        return new WhereClause().aBigint();
    }


    public static Value<Column.ADecimalColumn,Byte> aDecimal(final Byte value) {
        return new Value<>(aDecimal(),value);
    }
    
    public static Column.ADecimalColumn aDecimal() {
        return new WhereClause().aDecimal();
    }


    public static Value<Column.ANumericColumn,Byte> aNumeric(final Byte value) {
        return new Value<>(aNumeric(),value);
    }
    
    public static Column.ANumericColumn aNumeric() {
        return new WhereClause().aNumeric();
    }


    public static Value<Column.ARealColumn,Float> aReal(final Float value) {
        return new Value<>(aReal(),value);
    }
    
    public static Column.ARealColumn aReal() {
        return new WhereClause().aReal();
    }


    public static Value<Column.ADoubleColumn,Double> aDouble(final Double value) {
        return new Value<>(aDouble(),value);
    }
    
    public static Column.ADoubleColumn aDouble() {
        return new WhereClause().aDouble();
    }


    public static Value<Column.ASmallserialColumn,Short> aSmallserial(final Short value) {
        return new Value<>(aSmallserial(),value);
    }
    
    public static Column.ASmallserialColumn aSmallserial() {
        return new WhereClause().aSmallserial();
    }


    public static Value<Column.ASerialColumn,Long> aSerial(final Long value) {
        return new Value<>(aSerial(),value);
    }
    
    public static Column.ASerialColumn aSerial() {
        return new WhereClause().aSerial();
    }


    public static Value<Column.ABigserialColumn,Long> aBigserial(final Long value) {
        return new Value<>(aBigserial(),value);
    }
    
    public static Column.ABigserialColumn aBigserial() {
        return new WhereClause().aBigserial();
    }


    public static Value<Column.ADateColumn,LocalDate> aDate(final LocalDate value) {
        return new Value<>(aDate(),value);
    }
    
    public static Column.ADateColumn aDate() {
        return new WhereClause().aDate();
    }


    public static Value<Column.ABlobColumn,ByteBuffer> aBlob(final ByteBuffer value) {
        return new Value<>(aBlob(),value);
    }
    
    public static Column.ABlobColumn aBlob() {
        return new WhereClause().aBlob();
    }


    public static Value<Column.JsonColumn,JSONObject> json(final JSONObject value) {
        return new Value<>(json(),value);
    }
    
    public static Column.JsonColumn json() {
        return new WhereClause().json();
    }


    public static Value<Column.AJsonbColumn,JSONObject> aJsonb(final JSONObject value) {
        return new Value<>(aJsonb(),value);
    }
    
    public static Column.AJsonbColumn aJsonb() {
        return new WhereClause().aJsonb();
    }


    public static Value<Column.AUuidColumn,UUID> aUuid(final UUID value) {
        return new Value<>(aUuid(),value);
    }
    
    public static Column.AUuidColumn aUuid() {
        return new WhereClause().aUuid();
    }


    public static Value<Column.AXmlColumn,String> aXml(final String value) {
        return new Value<>(aXml(),value);
    }
    
    public static Column.AXmlColumn aXml() {
        return new WhereClause().aXml();
    }


    public static Value<Column.ATimeColumn,LocalTime> aTime(final LocalTime value) {
        return new Value<>(aTime(),value);
    }
    
    public static Column.ATimeColumn aTime() {
        return new WhereClause().aTime();
    }


    public static Value<Column.AIntervalColumn,Duration> aInterval(final Duration value) {
        return new Value<>(aInterval(),value);
    }
    
    public static Column.AIntervalColumn aInterval() {
        return new WhereClause().aInterval();
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

        public Column.IdColumn id() {
            Column.IdColumn query = new Column.IdColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ABooleanColumn aBoolean() {
            Column.ABooleanColumn query = new Column.ABooleanColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ReferenceCodeColumn referenceCode() {
            Column.ReferenceCodeColumn query = new Column.ReferenceCodeColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ACharColumn aChar() {
            Column.ACharColumn query = new Column.ACharColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ATextColumn aText() {
            Column.ATextColumn query = new Column.ATextColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.AEncryptedTextColumn aEncryptedText() {
            Column.AEncryptedTextColumn query = new Column.AEncryptedTextColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ASmallintColumn aSmallint() {
            Column.ASmallintColumn query = new Column.ASmallintColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.AIntegerColumn aInteger() {
            Column.AIntegerColumn query = new Column.AIntegerColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ABigintColumn aBigint() {
            Column.ABigintColumn query = new Column.ABigintColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ADecimalColumn aDecimal() {
            Column.ADecimalColumn query = new Column.ADecimalColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ANumericColumn aNumeric() {
            Column.ANumericColumn query = new Column.ANumericColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ARealColumn aReal() {
            Column.ARealColumn query = new Column.ARealColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ADoubleColumn aDouble() {
            Column.ADoubleColumn query = new Column.ADoubleColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ASmallserialColumn aSmallserial() {
            Column.ASmallserialColumn query = new Column.ASmallserialColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ASerialColumn aSerial() {
            Column.ASerialColumn query = new Column.ASerialColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ABigserialColumn aBigserial() {
            Column.ABigserialColumn query = new Column.ABigserialColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ADateColumn aDate() {
            Column.ADateColumn query = new Column.ADateColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ABlobColumn aBlob() {
            Column.ABlobColumn query = new Column.ABlobColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.JsonColumn json() {
            Column.JsonColumn query = new Column.JsonColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.AJsonbColumn aJsonb() {
            Column.AJsonbColumn query = new Column.AJsonbColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.AUuidColumn aUuid() {
            Column.AUuidColumn query = new Column.AUuidColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.AXmlColumn aXml() {
            Column.AXmlColumn query = new Column.AXmlColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.ATimeColumn aTime() {
            Column.ATimeColumn query = new Column.ATimeColumn(this);
            this.nodes.add(query);
            return query;
        }


        public Column.AIntervalColumn aInterval() {
            Column.AIntervalColumn query = new Column.AIntervalColumn(this);
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

public static class IdColumn extends Column<Long> {
    private String sql;

    public IdColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
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
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(Long value) {
        return true;
    }
}
public static class ABooleanColumn extends Column<Boolean> {
    private String sql;

    public ABooleanColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final Boolean value) {
        sql = "a_boolean =" + value ;
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
public static class ReferenceCodeColumn extends Column<String> {
    private String sql;

    public ReferenceCodeColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "reference_code ='" + value + "'";
        return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
        sql = "reference_code LIKE '" + value + "'";
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
public static class ACharColumn extends Column<Character> {
    private String sql;

    public ACharColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "a_char ='" + value + "'";
        return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
        sql = "a_char LIKE '" + value + "'";
        return getWhereClause();
    }
    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(Character value) {
        return true;
    }
}
public static class ATextColumn extends Column<String> {
    private String sql;

    public ATextColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "a_text ='" + value + "'";
        return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
        sql = "a_text LIKE '" + value + "'";
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
public static class AEncryptedTextColumn extends Column<String> {
    private String sql;

    public AEncryptedTextColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "a_encrypted_text ='" + value + "'";
        return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
        sql = "a_encrypted_text LIKE '" + value + "'";
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
public static class ASmallintColumn extends Column<Short> {
    private String sql;

    public ASmallintColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }

    public final WhereClause eq(final Short value) {
        sql = "a_smallint =" + value;
        return getWhereClause();
    }

    public final WhereClause gt(final Short value) {
        sql = "a_smallint >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final Short value) {
        sql = "a_smallint >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final Short value) {
        sql = "a_smallint <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final Short value) {
        sql = "a_smallint <=" + value;
        return getWhereClause();
    }

    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(Short value) {
        return true;
    }
}
public static class AIntegerColumn extends Column<Long> {
    private String sql;

    public AIntegerColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }

    public final WhereClause eq(final Long value) {
        sql = "a_integer =" + value;
        return getWhereClause();
    }

    public final WhereClause gt(final Long value) {
        sql = "a_integer >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final Long value) {
        sql = "a_integer >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final Long value) {
        sql = "a_integer <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final Long value) {
        sql = "a_integer <=" + value;
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
public static class ABigintColumn extends Column<Long> {
    private String sql;

    public ABigintColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }

    public final WhereClause eq(final Long value) {
        sql = "a_bigint =" + value;
        return getWhereClause();
    }

    public final WhereClause gt(final Long value) {
        sql = "a_bigint >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final Long value) {
        sql = "a_bigint >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final Long value) {
        sql = "a_bigint <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final Long value) {
        sql = "a_bigint <=" + value;
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
public static class ADecimalColumn extends Column<Byte> {
    private String sql;

    public ADecimalColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }

    public final WhereClause eq(final Byte value) {
        sql = "a_decimal =" + value;
        return getWhereClause();
    }

    public final WhereClause gt(final Byte value) {
        sql = "a_decimal >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final Byte value) {
        sql = "a_decimal >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final Byte value) {
        sql = "a_decimal <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final Byte value) {
        sql = "a_decimal <=" + value;
        return getWhereClause();
    }

    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(Byte value) {
        return true;
    }
}
public static class ANumericColumn extends Column<Byte> {
    private String sql;

    public ANumericColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }

    public final WhereClause eq(final Byte value) {
        sql = "a_numeric =" + value;
        return getWhereClause();
    }

    public final WhereClause gt(final Byte value) {
        sql = "a_numeric >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final Byte value) {
        sql = "a_numeric >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final Byte value) {
        sql = "a_numeric <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final Byte value) {
        sql = "a_numeric <=" + value;
        return getWhereClause();
    }

    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(Byte value) {
        return true;
    }
}
public static class ARealColumn extends Column<Float> {
    private String sql;

    public ARealColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause eq(final Float value) {
        sql = "a_real =" + value;
        return getWhereClause();
    }

    public final WhereClause gt(final Float value) {
        sql = "a_real >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final Float value) {
        sql = "a_real >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final Float value) {
        sql = "a_real <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final Float value) {
        sql = "a_real <=" + value;
        return getWhereClause();
    }
    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(Float value) {
        return true;
    }
}
public static class ADoubleColumn extends Column<Double> {
    private String sql;

    public ADoubleColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }

    public final WhereClause eq(final Double value) {
        sql = "a_double =" + value;
        return getWhereClause();
    }

    public final WhereClause gt(final Double value) {
        sql = "a_double >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final Double value) {
        sql = "a_double >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final Double value) {
        sql = "a_double <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final Double value) {
        sql = "a_double <=" + value;
        return getWhereClause();
    }

    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(Double value) {
        return true;
    }
}
public static class ASmallserialColumn extends Column<Short> {
    private String sql;

    public ASmallserialColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }

    public final WhereClause eq(final Short value) {
        sql = "a_smallserial =" + value;
        return getWhereClause();
    }

    public final WhereClause gt(final Short value) {
        sql = "a_smallserial >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final Short value) {
        sql = "a_smallserial >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final Short value) {
        sql = "a_smallserial <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final Short value) {
        sql = "a_smallserial <=" + value;
        return getWhereClause();
    }

    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(Short value) {
        return true;
    }
}
public static class ASerialColumn extends Column<Long> {
    private String sql;

    public ASerialColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }

    public final WhereClause eq(final Long value) {
        sql = "a_serial =" + value;
        return getWhereClause();
    }

    public final WhereClause gt(final Long value) {
        sql = "a_serial >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final Long value) {
        sql = "a_serial >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final Long value) {
        sql = "a_serial <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final Long value) {
        sql = "a_serial <=" + value;
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
public static class ABigserialColumn extends Column<Long> {
    private String sql;

    public ABigserialColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }

    public final WhereClause eq(final Long value) {
        sql = "a_bigserial =" + value;
        return getWhereClause();
    }

    public final WhereClause gt(final Long value) {
        sql = "a_bigserial >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final Long value) {
        sql = "a_bigserial >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final Long value) {
        sql = "a_bigserial <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final Long value) {
        sql = "a_bigserial <=" + value;
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
public static class ADateColumn extends Column<LocalDate> {
    private String sql;

    public ADateColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final LocalDate value) {
        sql = "a_date =" + value;
        return getWhereClause();
    }

    public final WhereClause  gt(final LocalDate value) {
        sql = "a_date >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final LocalDate value) {
        sql = "a_date >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final LocalDate value) {
        sql = "a_date <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final LocalDate value) {
        sql = "a_date <=" + value;
        return getWhereClause();
    }

    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(LocalDate value) {
        return true;
    }
}
public static class ABlobColumn extends Column<ByteBuffer> {
    private String sql;

    public ABlobColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "a_blob ='" + value + "'";
        return getWhereClause();
    }
    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(ByteBuffer value) {
        return true;
    }
}
public static class JsonColumn extends Column<JSONObject> {
    private String sql;

    public JsonColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "json ='" + value + "'";
        return getWhereClause();
    }
    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(JSONObject value) {
        return true;
    }
}
public static class AJsonbColumn extends Column<JSONObject> {
    private String sql;

    public AJsonbColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "a_jsonb ='" + value + "'";
        return getWhereClause();
    }
    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(JSONObject value) {
        return true;
    }
}
public static class AUuidColumn extends Column<UUID> {
    private String sql;

    public AUuidColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "a_uuid ='" + value + "'";
        return getWhereClause();
    }
    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(UUID value) {
        return true;
    }
}
public static class AXmlColumn extends Column<String> {
    private String sql;

    public AXmlColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "a_xml ='" + value + "'";
        return getWhereClause();
    }

    public final WhereClause LIKE(final String value) {
        sql = "a_xml LIKE '" + value + "'";
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
public static class ATimeColumn extends Column<LocalTime> {
    private String sql;

    public ATimeColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }


    public final WhereClause  eq(final LocalTime value) {
        sql = "a_time =" + value;
        return getWhereClause();
    }

    public final WhereClause  gt(final LocalTime value) {
        sql = "a_time >" + value;
        return getWhereClause();
    }

    public final WhereClause  gte(final LocalTime value) {
        sql = "a_time >=" + value;
        return getWhereClause();
    }

    public final WhereClause  lt(final LocalTime value) {
        sql = "a_time <" + value;
        return getWhereClause();
    }

    public final WhereClause  lte(final LocalTime value) {
        sql = "a_time <=" + value;
        return getWhereClause();
    }


    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(LocalTime value) {
        return true;
    }
}
public static class AIntervalColumn extends Column<Duration> {
    private String sql;

    public AIntervalColumn(final PartialWhereClause  whereClause) {
        super(whereClause);
    }
    public final WhereClause  eq(final String value) {
        sql = "a_interval ='" + value + "'";
        return getWhereClause();
    }
    @Override
    protected String asSql() {
        return sql;
    }

    protected Boolean validate(Duration value) {
        return true;
    }
}

        }
    

}
