package org.sqlcomponents.core.crawler;

import org.sqlcomponents.core.crawler.util.DataSourceUtil;
import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.model.relational.Column;
import org.sqlcomponents.core.model.relational.Database;
import org.sqlcomponents.core.model.relational.Index;
import org.sqlcomponents.core.model.relational.Key;
import org.sqlcomponents.core.model.relational.Procedure;
import org.sqlcomponents.core.model.relational.Table;
import org.sqlcomponents.core.model.relational.enumeration.ColumnType;
import org.sqlcomponents.core.model.relational.enumeration.DatabaseType;
import org.sqlcomponents.core.model.relational.enumeration.Flag;
import org.sqlcomponents.core.model.relational.enumeration.Order;
import org.sqlcomponents.core.model.relational.enumeration.TableType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

public class Crawler {

    /**
     *
     * @param application
     * @return Database
     * @throws ScubeException
     */
    public Database getDatabase(final Application application)
            throws ScubeException {
        Database database = new Database();
        DataSource dataSource = DataSourceUtil.getDataSource(
                application.getUrl(),
                application.getUserName(),
                application.getPassword());
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databasemMetadata = connection.getMetaData();


            database.setCatalogTerm(databasemMetadata.getCatalogTerm());
            database.setCatalogSeperator(databasemMetadata
                    .getCatalogSeparator());

            switch (databasemMetadata.getDatabaseProductName()
                    .toLowerCase().trim()) {
                case "postgresql":
                    database.setDatabaseType(DatabaseType.POSTGRES);
                    break;
                case "mysql":
                    database.setDatabaseType(DatabaseType.MYSQL);
                    break;
                case "mariadb":
                    database.setDatabaseType(DatabaseType.MARIADB);
            }

            database.setDatabaseMajorVersion(databasemMetadata
                    .getDatabaseMajorVersion());
            database.setDatabaseMinorVersion(databasemMetadata
                    .getDatabaseMinorVersion());
            database.setDatabaseProductVersion(databasemMetadata
                    .getDatabaseProductVersion());
            database.setDefaultTransactionIsolation(databasemMetadata
                    .getDefaultTransactionIsolation());
            database.setDatabaseMajorVersion(databasemMetadata
                    .getDatabaseMajorVersion());
            database.setDatabaseMinorVersion(databasemMetadata
                    .getDatabaseMinorVersion());
            database.setDriverName(databasemMetadata.getDriverName());
            database.setDriverVersion(databasemMetadata.getDriverVersion());
            database.setExtraNameCharacters(databasemMetadata
                    .getExtraNameCharacters());
            database.setIdentifierQuoteString(databasemMetadata
                    .getIdentifierQuoteString());
            database.setJdbcMajorVersion(databasemMetadata
                    .getJDBCMajorVersion());
            database.setJdbcMinorVersion(databasemMetadata
                    .getJDBCMinorVersion());
            database.setMaxBinaryLiteralLength(databasemMetadata
                    .getMaxBinaryLiteralLength());
            database.setMaxCharLiteralLength(databasemMetadata
                    .getMaxCharLiteralLength());
            database.setMaxCatalogNameLength(databasemMetadata
                    .getMaxCatalogNameLength());
            database.setMaxColumnNameLength(databasemMetadata
                    .getMaxColumnNameLength());
            database.setMaxColumnsInGroupBy(databasemMetadata
                    .getMaxColumnsInGroupBy());
            database.setMaxColumnsInIndex(databasemMetadata
                    .getMaxColumnsInIndex());
            database.setMaxColumnsInOrderBy(databasemMetadata
                    .getMaxColumnsInOrderBy());
            database.setMaxColumnsInSelect(databasemMetadata.
                    getMaxColumnsInSelect());
            database.setMaxColumnsInTable(databasemMetadata
                    .getMaxColumnsInTable());
            database.setMaxConnections(databasemMetadata
                    .getMaxConnections());
            database.setMaxCursorNameLength(databasemMetadata
                    .getMaxCursorNameLength());
            database.setMaxIndexLength(databasemMetadata
                    .getMaxIndexLength());
            database.setMaxSchemaNameLength(databasemMetadata
                    .getMaxSchemaNameLength());
            database.setMaxProcedureNameLength(databasemMetadata
                    .getMaxProcedureNameLength());
            database.setMaxRowSize(databasemMetadata.getMaxRowSize());
            database.setDoesMaxRowSizeIncludeBlobs(databasemMetadata
                    .doesMaxRowSizeIncludeBlobs());
            database.setMaxStatementLength(databasemMetadata
                    .getMaxStatementLength());
            database.setMaxStatements(databasemMetadata
                    .getMaxStatements());
            database.setMaxTableNameLength(databasemMetadata
                    .getMaxTableNameLength());
            database.setMaxTablesInSelect(databasemMetadata.getMaxTablesInSelect());
            database.setMaxUserNameLength(databasemMetadata.getMaxUserNameLength());
            database.setNumericFunctions(new HashSet<>(Arrays.asList(databasemMetadata.getNumericFunctions().split(","))));
            database.setProcedureTerm(databasemMetadata.getProcedureTerm());
            database.setResultSetHoldability(databasemMetadata.getResultSetHoldability());
            database.setSchemaTerm(databasemMetadata.getSchemaTerm());
            database.setSearchStringEscape(databasemMetadata.getSearchStringEscape());
            database.setSqlKeywords(new TreeSet<>(Arrays.asList(databasemMetadata.getSQLKeywords().split(","))));
            database.setStringFunctions(new TreeSet<>(Arrays.asList(databasemMetadata.getStringFunctions().split(","))));
            database.setSystemFunctions(new TreeSet<>(Arrays.asList(databasemMetadata.getSystemFunctions().split(","))));
            database.setTimeDateFunctions(new TreeSet<>(Arrays.asList(databasemMetadata.getTimeDateFunctions().split(","))));
            database.setSupportsTransactions(databasemMetadata.supportsTransactions());
            database.setSupportsDataDefinitionAndDataManipulationTransactions(databasemMetadata.supportsDataDefinitionAndDataManipulationTransactions());
            database.setDataDefinitionCausesTransactionCommit(databasemMetadata.dataDefinitionCausesTransactionCommit());
            database.setDataDefinitionIgnoredInTransactions(databasemMetadata.dataDefinitionIgnoredInTransactions());
//			database.setSupportsResultSetType(databasemMetadata.supportsResultSetType());
//			database.setSupportsResultSetConcurrency(databasemMetadata.supportsResultSetConcurrency());
//			database.setOwnUpdatesAreVisible(databasemMetadata.ownUpdatesAreVisible());
//			database.setOwnDeletesAreVisible(databasemMetadata.ownDeletesAreVisible());
//			database.setOwnInsertsAreVisible(databasemMetadata.ownInsertsAreVisible());
//			database.setOthersUpdatesAreVisible(databasemMetadata.othersUpdatesAreVisible());
//			database.setOthersDeletesAreVisible(databasemMetadata.othersDeletesAreVisible());
//			database.setOthersInsertsAreVisible(databasemMetadata.othersInsertsAreVisible());
//			database.setUpdatesAreDetected(databasemMetadata.updatesAreDetected());
//			database.setDeletesAreDetected(databasemMetadata.deletesAreDetected());
//			database.setInsertsAreDetected(databasemMetadata.insertsAreDetected());
// 			database.setSupportsResultSetHoldability(databasemMetadata.supportsResultSetHoldability());
//			database.setSupportsTransactionIsolationLevel(databasemMetadata.supportsTransactionIsolationLevel());
            database.setCatalogAtStart(databasemMetadata.isCatalogAtStart());
            database.setReadOnly(databasemMetadata.isReadOnly());
            database.setLocatorsUpdateCopy(databasemMetadata.locatorsUpdateCopy());
            database.setSupportsBatchUpdates(databasemMetadata.supportsBatchUpdates());
            database.setSupportsSavePoint(databasemMetadata.supportsAlterTableWithAddColumn());
            database.setSupportsNamedParameters(databasemMetadata.supportsNamedParameters());
            database.setSupportsMultipleOpenResults(databasemMetadata.supportsMultipleOpenResults());
            database.setSupportsGetGeneratedKeys(databasemMetadata.supportsGetGeneratedKeys());
            database.setSqlStateType(databasemMetadata.getSQLStateType());
            database.setSupportsStatementPooling(databasemMetadata.supportsStatementPooling());
            database.setAllProceduresAreCallable(databasemMetadata.allProceduresAreCallable());
            database.setAllTablesAreSelectable(databasemMetadata.allTablesAreSelectable());
            database.setUrl(databasemMetadata.getURL());
            database.setUserName(databasemMetadata.getUserName());
            database.setNullPlusNonNullIsNull(databasemMetadata.nullPlusNonNullIsNull());
            database.setNullsAreSortedHigh(databasemMetadata.nullsAreSortedHigh());
            database.setNullsAreSortedLow(databasemMetadata.nullsAreSortedLow());
            database.setNullsAreSortedAtStart(databasemMetadata.nullsAreSortedAtStart());
            database.setNullsAreSortedAtEnd(databasemMetadata.nullsAreSortedAtEnd());
            database.setAutoCommitFailureClosesAllResultSets(databasemMetadata.autoCommitFailureClosesAllResultSets());
            database.setGeneratedKeyAlwaysReturned(databasemMetadata.generatedKeyAlwaysReturned());
            database.setStoresLowerCaseIdentifiers(databasemMetadata.storesLowerCaseIdentifiers());
            database.setStoresLowerCaseQuotedIdentifiers(databasemMetadata.storesLowerCaseQuotedIdentifiers());
            database.setStoresMixedCaseIdentifiers(databasemMetadata.storesMixedCaseIdentifiers());
            database.setStoresMixedCaseQuotedIdentifiers(databasemMetadata.storesMixedCaseQuotedIdentifiers());
            database.setStoresUpperCaseIdentifiers(databasemMetadata.storesUpperCaseIdentifiers());
            database.setStoresUpperCaseQuotedIdentifiers(databasemMetadata.storesUpperCaseQuotedIdentifiers());
            database.setSupportsAlterTableWithAddColumn(databasemMetadata.supportsAlterTableWithAddColumn());
            database.setSupportsAlterTableWithDropColumn(databasemMetadata.supportsAlterTableWithDropColumn());
            database.setSupportsANSI92EntryLevelSQL(databasemMetadata.supportsANSI92EntryLevelSQL());
            database.setSupportsANSI92FullSQL(databasemMetadata.supportsANSI92FullSQL());
            database.setSupportsANSI92IntermediateSQL(databasemMetadata.supportsANSI92IntermediateSQL());
            database.setSupportsCatalogsInDataManipulation(databasemMetadata.supportsCatalogsInDataManipulation());
            database.setSupportsCatalogsInIndexDefinitions(databasemMetadata.supportsCatalogsInIndexDefinitions());
            database.setSupportsCatalogsInPrivilegeDefinitions(databasemMetadata.supportsCatalogsInPrivilegeDefinitions());
            database.setSupportsCatalogsInProcedureCalls(databasemMetadata.supportsCatalogsInProcedureCalls());
            database.setSupportsCatalogsInTableDefinitions(databasemMetadata.supportsCatalogsInTableDefinitions());
            database.setSupportsColumnAliasing(databasemMetadata.supportsColumnAliasing());

            database.setTableTypes(getTableTypes(databasemMetadata));

            database.setSequences(getSequences(databasemMetadata));
            database.setTables(getTables(databasemMetadata, database, tableName -> application.getTablePatterns() == null || application.getTablePatterns().contains(tableName)));
            // database.setFunctions(getProcedures(databasemMetadata));

            repair(database, databasemMetadata);

        } catch (SQLException e) {
            throw new ScubeException(e);
        }
        return database;
    }

    private Set<TableType> getTableTypes(final DatabaseMetaData databasemetadata) throws SQLException {
        Set<TableType> tableTypes = new TreeSet<>();
        ResultSet resultset = databasemetadata.getTableTypes();

        while (resultset.next()) {
            tableTypes.add(TableType.value(resultset.getString("TABLE_TYPE")));
        }

        return tableTypes;
    }


    private List<String> getSequences(final DatabaseMetaData databasemetadata) throws SQLException {
        List<String> sequences = new ArrayList<>();
        ResultSet resultset = databasemetadata.getTables(null, null,
                null, new String[]{"SEQUENCE"});

        while (resultset.next()) {
            sequences.add(resultset.getString("table_name"));
        }

        return sequences;
    }

    private List<Table> getTables(final DatabaseMetaData databasemetadata, final Database database, final Predicate<String> tableFilter) throws SQLException {
        List<Table> tables = new ArrayList<>();

        ResultSet resultset = databasemetadata.getTables(null, null, null, new String[]{"TABLE"});

        while (resultset.next()) {
            final String tableName = resultset.getString("table_name");
            if (tableFilter.test(tableName)) {
                Table table = new Table(database);
                table.setTableName(tableName);
                table.setCategoryName(resultset.getString("table_cat"));
                table.setSchemaName(resultset.getString("table_schem"));
                table.setTableType(TableType.value(resultset.getString("table_type")));
                table.setRemarks(resultset.getString("remarks"));
                // table.setCategoryType(resultset.getString("type_cat"));
                // table.setSchemaType(resultset.getString("type_schem"));
                // table.setNameType(resultset.getString("type_name"));
                // table.setSelfReferencingColumnName(resultset.getString("self_referencing_col_name"));
                // table.setReferenceGeneration(resultset.getString("ref_generation"));

                table.setColumns(getColumns(databasemetadata, table));

                table.setIndices(getIndices(databasemetadata, table));
                // Set Sequence
                database.getSequences()
                        .stream()
                        .filter(sequenceName -> sequenceName.contains(tableName))
                        .findFirst()
                        .ifPresent(table::setSequenceName);

                tables.add(table);
            }


        }

        return tables;
    }

    private List<Index> getIndices(final DatabaseMetaData databasemetadata, final Table table) throws SQLException {
        List<Index> indices = new ArrayList<>();

        ResultSet indexResultset = databasemetadata.getIndexInfo(null, null, table.getTableName(), true, true);

        while (indexResultset.next()) {
            Index index = new Index(table);
            index.setColumnName(indexResultset.getString("COLUMN_NAME"));
            index.setOrdinalPosition(indexResultset.getShort("ORDINAL_POSITION"));

            index.setIndexName(indexResultset.getString("INDEX_NAME"));
            index.setIndexQualifier(indexResultset.getString("INDEX_QUALIFIER"));
            index.setCardinality(indexResultset.getInt("CARDINALITY"));
            String ascDesc = indexResultset.getString("ASC_OR_DESC");
            if (ascDesc != null) {
                index.setOrder(Order.value(ascDesc));
            }
            index.setFilterCondition(indexResultset.getString("FILTER_CONDITION"));
            index.setPages(indexResultset.getInt("PAGES"));
            index.setType(indexResultset.getShort("TYPE"));
            index.setNonUnique(indexResultset.getBoolean("NON_UNIQUE"));


            indices.add(index);
        }
        return indices;
    }

    private List<Column> getColumns(final DatabaseMetaData databasemetadata, final Table table) throws SQLException {
        List<Column> columns = new ArrayList<>();

        ResultSet columnResultset = databasemetadata.getColumns(null, null, table.getTableName(), null);
        ColumnType columnType;
        while (columnResultset.next()) {
            Column column = new Column(table);
            column.setColumnName(columnResultset.getString("COLUMN_NAME"));
            column.setTableName(columnResultset.getString("TABLE_NAME"));
            column.setTypeName(columnResultset.getString("TYPE_NAME"));
            columnType = ColumnType.value(JDBCType.valueOf(columnResultset.getInt("DATA_TYPE")));
            column.setColumnType(columnType == ColumnType.OTHER ? getColumnTypeForOthers(column) : columnType);
            column.setSize(columnResultset.getInt("COLUMN_SIZE"));
            column.setDecimalDigits(columnResultset.getInt("DECIMAL_DIGITS"));
            column.setRemarks(columnResultset.getString("REMARKS"));
            column.setNullable(Flag.value(columnResultset.getString("IS_NULLABLE")));
            column.setAutoIncrement(Flag.value(columnResultset.getString("IS_AUTOINCREMENT")));
            column.setTableCategory(columnResultset.getString("TABLE_CAT"));
            column.setTableSchema(columnResultset.getString("TABLE_SCHEM"));
            column.setBufferLength(columnResultset.getInt("BUFFER_LENGTH"));
            column.setNumberPrecisionRadix(columnResultset.getInt("NUM_PREC_RADIX"));
            column.setColumnDefinition(columnResultset.getString("COLUMN_DEF"));
            column.setOrdinalPosition(columnResultset.getInt("ORDINAL_POSITION"));
            column.setScopeCatalog(columnResultset.getString("SCOPE_CATALOG"));
            column.setScopeSchema(columnResultset.getString("SCOPE_SCHEMA"));
            column.setScopeTable(columnResultset.getString("SCOPE_TABLE"));
            column.setSourceDataType(columnResultset.getString("SOURCE_DATA_TYPE"));
            column.setGeneratedColumn(Flag.value(columnResultset.getString("IS_GENERATEDCOLUMN")));

            column.setExportedKeys(new TreeSet<>());

            columns.add(column);
        }

        // Fill Primary Keys
        ResultSet primaryKeysResultSet = databasemetadata.getPrimaryKeys(null, null, table.getTableName());
        while (primaryKeysResultSet.next()) {
            columns.stream().filter(column -> {
                try {
                    return column.getColumnName().equals(primaryKeysResultSet.getString("COLUMN_NAME"));
                } catch (SQLException throwables) {
                    return false;
                }
            }).findFirst()
                    .ifPresent(column -> {
                        try {
                            column.setPrimaryKeyIndex(primaryKeysResultSet.getInt("KEY_SEQ"));
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    });
        }

        //Extracting Foreign Keys.
        ResultSet foreignKeysResultSet = databasemetadata.getExportedKeys(null, null, table.getTableName());

        while (foreignKeysResultSet.next()) {
            Key key = new Key();
            key.setTableName(foreignKeysResultSet.getString("FKTABLE_NAME"));
            key.setColumnName(foreignKeysResultSet.getString("FKCOLUMN_NAME"));
            if (!columns.isEmpty()) {
                columns.stream().filter(column -> {
                    try {
                        return column.getColumnName().equals(foreignKeysResultSet.getString("PKCOLUMN_NAME"));
                    } catch (SQLException throwables) {
                        return false;
                    }
                }).findFirst().ifPresent(column -> column.getExportedKeys().add(key));
            }
        }
        return columns;
    }

    private ColumnType getColumnTypeForOthers(final Column column) {
        switch (column.getTable().getDatabase().getDatabaseType()) {
            case POSTGRES:
                if (column.getTypeName().equalsIgnoreCase("json")) {
                    return ColumnType.JSON;
                } else if (column.getTypeName().equalsIgnoreCase("jsonb")) {
                    return ColumnType.JSONB;
                } else if (column.getTypeName().equalsIgnoreCase("uuid")) {
                    return ColumnType.UUID;
                } else if (column.getTypeName().equalsIgnoreCase("interval")) {
                    return ColumnType.INTERVAL;
                }
                break;
        }

        return null;
    }

    private List<Procedure> getProcedures(final DatabaseMetaData databasemetadata) throws SQLException {

        List<Procedure> functions = new ArrayList<>();

        ResultSet functionResultset = databasemetadata.getProcedures(null, null, null);
        while (functionResultset.next()) {
            Procedure function = new Procedure();
            function.setFunctionName(functionResultset.getString("PROCEDURE_NAME"));
            function.setFunctionCategory(functionResultset.getString("PROCEDURE_CAT"));
            function.setFunctionSchema(functionResultset.getString("PROCEDURE_SCHEM"));
            function.setFunctionType(functionResultset.getShort("PROCEDURE_TYPE"));
            function.setRemarks(functionResultset.getString("REMARKS"));
            function.setSpecificName(functionResultset.getString("SPECIFIC_NAME"));
            functions.add(function);
        }

        return functions;
    }

    private void repair(final Database database, final DatabaseMetaData databaseMetaData) {
        switch (database.getDatabaseType()) {
            case MARIADB:
            case MYSQL:
                repairMySQL(database, databaseMetaData);
                break;
        }
    }


    private void repairMySQL(final Database database, final DatabaseMetaData databaseMetaData) {
        if (database != null) {
            database.getTables().forEach(table -> {
                try (PreparedStatement preparedStatement = databaseMetaData.getConnection().prepareStatement("SELECT "
                        + "COLUMN_NAME,COLUMN_TYPE  from INFORMATION_SCHEMA.COLUMNS where\n"
                        + " table_name = ?")) {
                    preparedStatement.setString(1, table.getTableName());

                    ResultSet resultSet = preparedStatement.executeQuery();

                    Column column;
                    String columnType;
                    while (resultSet.next()) {
                         column = table.getColumns().stream()
                                .filter(column1 -> {
                                    try {
                                        return column1.getColumnName().equals(resultSet.getString("COLUMN_NAME"));
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                    return false;
                                })
                                .findFirst().get();
                        columnType = resultSet.getString("COLUMN_TYPE");
                        String[] s = columnType.split("\\(");
                        column.setTypeName(s[0].trim());

                        ColumnType columnType1 = ColumnType.value(column.getTypeName().toUpperCase());
                        if (columnType1 != null) {
                            column.setColumnType(columnType1);
                        }
                        if (s.length == 2) {
                            String grp = s[1].trim().replaceAll("\\)", "");

                            s = grp.split(",");
                            column.setSize(Integer.parseInt(s[0]));
                            if (s.length == 2) {
                                column.setDecimalDigits(Integer.parseInt(s[1]));
                            }
                        }

                    }



                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
        }
    }
}
