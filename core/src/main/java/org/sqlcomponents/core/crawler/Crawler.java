package org.sqlcomponents.core.crawler;

import org.sqlcomponents.core.crawler.util.DataSourceUtil;
import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.model.relational.*;
import org.sqlcomponents.core.model.relational.enumeration.Flag;
import org.sqlcomponents.core.model.relational.enumeration.Order;
import org.sqlcomponents.core.model.relational.enumeration.TableType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.function.Predicate;

public class Crawler {


    public Database getDatabase(final Application application) throws ScubeException {
        Database database = new Database();
        DataSource dataSource = DataSourceUtil.getDataSource(application.getUrl(), application
                .getUserName(), application.getPassword());
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databasemetadata = connection.getMetaData();

            database.setTableTypes(getTableTypes(databasemetadata));

            database.setSequences(getSequences(databasemetadata));
            database.setTables(getTables(databasemetadata, database, tableName -> application.getTablePatterns() == null || application.getTablePatterns().contains(tableName)));
            // database.setFunctions(getProcedures(databasemetadata));
            database.setCatalogTerm(databasemetadata.getCatalogTerm());
            database.setCatalogSeperator(databasemetadata.getCatalogSeparator());
            database.setDatabaseProductName(databasemetadata.getDatabaseProductName());
            database.setDatabaseMajorVersion(databasemetadata.getDatabaseMajorVersion());
            database.setDatabaseMinorVersion(databasemetadata.getDatabaseMinorVersion());
            database.setDatabaseProductVersion(databasemetadata.getDatabaseProductVersion());
            database.setDefaultTransactionIsolation(databasemetadata.getDefaultTransactionIsolation());
            database.setDatabaseMajorVersion(databasemetadata.getDatabaseMajorVersion());
            database.setDatabaseMinorVersion(databasemetadata.getDatabaseMinorVersion());
            database.setDriverName(databasemetadata.getDriverName());
            database.setDriverVersion(databasemetadata.getDriverVersion());
            database.setExtraNameCharacters(databasemetadata.getExtraNameCharacters());
            database.setIdentifierQuoteString(databasemetadata.getIdentifierQuoteString());
            database.setJdbcMajorVersion(databasemetadata.getJDBCMajorVersion());
            database.setJdbcMinorVersion(databasemetadata.getJDBCMinorVersion());
            database.setMaxBinaryLiteralLength(databasemetadata.getMaxBinaryLiteralLength());
            database.setMaxCharLiteralLength(databasemetadata.getMaxCharLiteralLength());
            database.setMaxCatalogNameLength(databasemetadata.getMaxCatalogNameLength());
            database.setMaxColumnNameLength(databasemetadata.getMaxColumnNameLength());
            database.setMaxColumnsInGroupBy(databasemetadata.getMaxColumnsInGroupBy());
            database.setMaxColumnsInIndex(databasemetadata.getMaxColumnsInIndex());
            database.setMaxColumnsInOrderBy(databasemetadata.getMaxColumnsInOrderBy());
            database.setMaxColumnsInSelect(databasemetadata.getMaxColumnsInSelect());
            database.setMaxColumnsInTable(databasemetadata.getMaxColumnsInTable());
            database.setMaxConnections(databasemetadata.getMaxConnections());
            database.setMaxCursorNameLength(databasemetadata.getMaxCursorNameLength());
            database.setMaxIndexLength(databasemetadata.getMaxIndexLength());
            database.setMaxSchemaNameLength(databasemetadata.getMaxSchemaNameLength());
            database.setMaxProcedureNameLength(databasemetadata.getMaxProcedureNameLength());
            database.setMaxRowSize(databasemetadata.getMaxRowSize());
            database.setDoesMaxRowSizeIncludeBlobs(databasemetadata.doesMaxRowSizeIncludeBlobs());
            database.setMaxStatementLength(databasemetadata.getMaxStatementLength());
            database.setMaxStatements(databasemetadata.getMaxStatements());
            database.setMaxTableNameLength(databasemetadata.getMaxTableNameLength());
            database.setMaxTablesInSelect(databasemetadata.getMaxTablesInSelect());
            database.setMaxUserNameLength(databasemetadata.getMaxUserNameLength());
            database.setNumericFunctions(new HashSet<>(Arrays.asList(databasemetadata.getNumericFunctions().split(","))));
            database.setProcedureTerm(databasemetadata.getProcedureTerm());
            database.setResultSetHoldability(databasemetadata.getResultSetHoldability());
            database.setSchemaTerm(databasemetadata.getSchemaTerm());
            database.setSearchStringEscape(databasemetadata.getSearchStringEscape());
            database.setSqlKeywords(new TreeSet<>(Arrays.asList(databasemetadata.getSQLKeywords().split(","))));
            database.setStringFunctions(new TreeSet<>(Arrays.asList(databasemetadata.getStringFunctions().split(","))));
            database.setSystemFunctions(new TreeSet<>(Arrays.asList(databasemetadata.getSystemFunctions().split(","))));
            database.setTimeDateFunctions(new TreeSet<>(Arrays.asList(databasemetadata.getTimeDateFunctions().split(","))));
            database.setSupportsTransactions(databasemetadata.supportsTransactions());
            database.setSupportsDataDefinitionAndDataManipulationTransactions(databasemetadata.supportsDataDefinitionAndDataManipulationTransactions());
            database.setDataDefinitionCausesTransactionCommit(databasemetadata.dataDefinitionCausesTransactionCommit());
            database.setDataDefinitionIgnoredInTransactions(databasemetadata.dataDefinitionIgnoredInTransactions());
//			database.setSupportsResultSetType(databasemetadata.supportsResultSetType());
//			database.setSupportsResultSetConcurrency(databasemetadata.supportsResultSetConcurrency());
//			database.setOwnUpdatesAreVisible(databasemetadata.ownUpdatesAreVisible());
//			database.setOwnDeletesAreVisible(databasemetadata.ownDeletesAreVisible());
//			database.setOwnInsertsAreVisible(databasemetadata.ownInsertsAreVisible());
//			database.setOthersUpdatesAreVisible(databasemetadata.othersUpdatesAreVisible());
//			database.setOthersDeletesAreVisible(databasemetadata.othersDeletesAreVisible());
//			database.setOthersInsertsAreVisible(databasemetadata.othersInsertsAreVisible());
//			database.setUpdatesAreDetected(databasemetadata.updatesAreDetected());
//			database.setDeletesAreDetected(databasemetadata.deletesAreDetected());
//			database.setInsertsAreDetected(databasemetadata.insertsAreDetected());
// 			database.setSupportsResultSetHoldability(databasemetadata.supportsResultSetHoldability());
//			database.setSupportsTransactionIsolationLevel(databasemetadata.supportsTransactionIsolationLevel());
            database.setCatalogAtStart(databasemetadata.isCatalogAtStart());
            database.setReadOnly(databasemetadata.isReadOnly());
            database.setLocatorsUpdateCopy(databasemetadata.locatorsUpdateCopy());
            database.setSupportsBatchUpdates(databasemetadata.supportsBatchUpdates());
            database.setSupportsSavePoint(databasemetadata.supportsAlterTableWithAddColumn());
            database.setSupportsNamedParameters(databasemetadata.supportsNamedParameters());
            database.setSupportsMultipleOpenResults(databasemetadata.supportsMultipleOpenResults());
            database.setSupportsGetGeneratedKeys(databasemetadata.supportsGetGeneratedKeys());
            database.setSqlStateType(databasemetadata.getSQLStateType());
            database.setSupportsStatementPooling(databasemetadata.supportsStatementPooling());
            database.setAllProceduresAreCallable(databasemetadata.allProceduresAreCallable());
            database.setAllTablesAreSelectable(databasemetadata.allTablesAreSelectable());
            database.setUrl(databasemetadata.getURL());
            database.setUserName(databasemetadata.getUserName());
            database.setNullPlusNonNullIsNull(databasemetadata.nullPlusNonNullIsNull());
            database.setNullsAreSortedHigh(databasemetadata.nullsAreSortedHigh());
            database.setNullsAreSortedLow(databasemetadata.nullsAreSortedLow());
            database.setNullsAreSortedAtStart(databasemetadata.nullsAreSortedAtStart());
            database.setNullsAreSortedAtEnd(databasemetadata.nullsAreSortedAtEnd());
            database.setAutoCommitFailureClosesAllResultSets(databasemetadata.autoCommitFailureClosesAllResultSets());
            database.setGeneratedKeyAlwaysReturned(databasemetadata.generatedKeyAlwaysReturned());
            database.setStoresLowerCaseIdentifiers(databasemetadata.storesLowerCaseIdentifiers());
            database.setStoresLowerCaseQuotedIdentifiers(databasemetadata.storesLowerCaseQuotedIdentifiers());
            database.setStoresMixedCaseIdentifiers(databasemetadata.storesMixedCaseIdentifiers());
            database.setStoresMixedCaseQuotedIdentifiers(databasemetadata.storesMixedCaseQuotedIdentifiers());
            database.setStoresUpperCaseIdentifiers(databasemetadata.storesUpperCaseIdentifiers());
            database.setStoresUpperCaseQuotedIdentifiers(databasemetadata.storesUpperCaseQuotedIdentifiers());
            database.setSupportsAlterTableWithAddColumn(databasemetadata.supportsAlterTableWithAddColumn());
            database.setSupportsAlterTableWithDropColumn(databasemetadata.supportsAlterTableWithDropColumn());
            database.setSupportsANSI92EntryLevelSQL(databasemetadata.supportsANSI92EntryLevelSQL());
            database.setSupportsANSI92FullSQL(databasemetadata.supportsANSI92FullSQL());
            database.setSupportsANSI92IntermediateSQL(databasemetadata.supportsANSI92IntermediateSQL());
            database.setSupportsCatalogsInDataManipulation(databasemetadata.supportsCatalogsInDataManipulation());
            database.setSupportsCatalogsInIndexDefinitions(databasemetadata.supportsCatalogsInIndexDefinitions());
            database.setSupportsCatalogsInPrivilegeDefinitions(databasemetadata.supportsCatalogsInPrivilegeDefinitions());
            database.setSupportsCatalogsInProcedureCalls(databasemetadata.supportsCatalogsInProcedureCalls());
            database.setSupportsCatalogsInTableDefinitions(databasemetadata.supportsCatalogsInTableDefinitions());
            database.setSupportsColumnAliasing(databasemetadata.supportsColumnAliasing());
        } catch (SQLException e) {
            throw new ScubeException(e);
        }
        return database;
    }

    private Set<TableType> getTableTypes(DatabaseMetaData databasemetadata) throws SQLException {
        Set<TableType> tableTypes = new TreeSet<>();
        ResultSet resultset = databasemetadata.getTableTypes();

        while (resultset.next()) {
            tableTypes.add(TableType.value(resultset.getString("TABLE_TYPE")));
        }

        return tableTypes;
    }


    private List<String> getSequences(DatabaseMetaData databasemetadata) throws SQLException {
        List<String> sequences = new ArrayList<>();
        ResultSet resultset = databasemetadata.getTables(null, null,
                null, new String[]{"SEQUENCE"});

        while (resultset.next()) {
            sequences.add(resultset.getString("table_name"));
        }

        return sequences;
    }

    private List<Table> getTables(DatabaseMetaData databasemetadata, Database database, Predicate<String> tableFilter) throws SQLException {
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

    private List<Index> getIndices(DatabaseMetaData databasemetadata, final Table table) throws SQLException {
        List<Index> indices = new ArrayList<>();

        ResultSet indexResultset = databasemetadata.getIndexInfo(null, null, table.getTableName(), true,true);

        while (indexResultset.next()) {
            Index index = new Index(table);
            index.setColumnName(indexResultset.getString("COLUMN_NAME"));
            index.setOrdinalPosition(indexResultset.getShort("ORDINAL_POSITION"));

            index.setIndexName(indexResultset.getString("INDEX_NAME"));
            index.setIndexQualifier(indexResultset.getString("INDEX_QUALIFIER"));
            index.setCardinality(indexResultset.getInt("CARDINALITY"));
            String ascDesc = indexResultset.getString("ASC_OR_DESC");
            if(ascDesc != null) {
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

    private List<Column> getColumns(DatabaseMetaData databasemetadata, final Table table) throws SQLException {
        List<Column> columns = new ArrayList<>();

        ResultSet columnResultset = databasemetadata.getColumns(null, null, table.getTableName(), null);

        while (columnResultset.next()) {
            Column column = new Column(table);
            column.setColumnName(columnResultset.getString("COLUMN_NAME"));
            column.setTableName(columnResultset.getString("TABLE_NAME"));
            column.setTypeName(columnResultset.getString("TYPE_NAME"));
            column.setJdbcType(JDBCType.valueOf(columnResultset.getInt("DATA_TYPE")));
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
            if(!columns.isEmpty()) {
                columns.stream().filter(column -> {
                    try {
                        return column.getColumnName().equals(foreignKeysResultSet.getString("PKCOLUMN_NAME"));
                    } catch (SQLException throwables) {
                        return false;
                    }
                }).findFirst().ifPresent(column->column.getExportedKeys().add(key));
            }

        }
        return columns;
    }

    private List<Procedure> getProcedures(DatabaseMetaData databasemetadata) throws SQLException {

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
}
