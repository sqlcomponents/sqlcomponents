package org.sqlcomponents.core.crawler;

import org.sqlcomponents.core.crawler.util.CrawlerConsts;
import org.sqlcomponents.core.crawler.util.DataSourceUtil;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.model.relational.Column;
import org.sqlcomponents.core.model.relational.Database;
import org.sqlcomponents.core.model.relational.Index;
import org.sqlcomponents.core.model.relational.Key;
import org.sqlcomponents.core.model.relational.Procedure;
import org.sqlcomponents.core.model.relational.Table;
import org.sqlcomponents.core.model.relational.UniqueConstraint;
import org.sqlcomponents.core.model.relational.enums.ColumnType;
import org.sqlcomponents.core.model.relational.enums.DBType;
import org.sqlcomponents.core.model.relational.enums.Flag;
import org.sqlcomponents.core.model.relational.enums.Order;
import org.sqlcomponents.core.model.relational.enums.TableType;

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
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public final class Crawler {
    private final Database database = new Database();

    private final Application application;
    private final DatabaseMetaData databaseMetaData;

    public Crawler(final Application aApplication) throws SQLException {
        application = aApplication;
        DataSource lDataSource = DataSourceUtil.getDataSource(application.getUrl(), application.getUserName(),
                application.getPassword(), application.getSchemaName());
        Connection lConnection = lDataSource.getConnection();
        databaseMetaData = lConnection.getMetaData();
    }

    public Database getDatabase() throws SQLException {
        database.setCatalogTerm(databaseMetaData.getCatalogTerm());
        database.setCatalogSeperator(databaseMetaData.getCatalogSeparator());

        switch (databaseMetaData.getDatabaseProductName().toLowerCase().trim()) {
        case CrawlerConsts.POSTGRES_DB: {
            database.setDbType(DBType.POSTGRES);
            break;
        }
        case CrawlerConsts.MYSQL_DB: {
            database.setDbType(DBType.MYSQL);
            break;
        }
        case CrawlerConsts.MARIA_DB: {
            database.setDbType(DBType.MARIADB);
        }
        }

        database.setDatabaseMajorVersion(databaseMetaData.getDatabaseMajorVersion());
        database.setDatabaseMinorVersion(databaseMetaData.getDatabaseMinorVersion());
        database.setDatabaseProductVersion(databaseMetaData.getDatabaseProductVersion());
        database.setDefaultTransactionIsolation(databaseMetaData.getDefaultTransactionIsolation());
        database.setDatabaseMajorVersion(databaseMetaData.getDatabaseMajorVersion());
        database.setDatabaseMinorVersion(databaseMetaData.getDatabaseMinorVersion());
        database.setDriverName(databaseMetaData.getDriverName());
        database.setDriverVersion(databaseMetaData.getDriverVersion());
        database.setExtraNameCharacters(databaseMetaData.getExtraNameCharacters());
        database.setIdentifierQuoteString(databaseMetaData.getIdentifierQuoteString());
        database.setJdbcMajorVersion(databaseMetaData.getJDBCMajorVersion());
        database.setJdbcMinorVersion(databaseMetaData.getJDBCMinorVersion());
        database.setMaxBinaryLiteralLength(databaseMetaData.getMaxBinaryLiteralLength());
        database.setMaxCharLiteralLength(databaseMetaData.getMaxCharLiteralLength());
        database.setMaxCatalogNameLength(databaseMetaData.getMaxCatalogNameLength());
        database.setMaxColumnNameLength(databaseMetaData.getMaxColumnNameLength());
        database.setMaxColumnsInGroupBy(databaseMetaData.getMaxColumnsInGroupBy());
        database.setMaxColumnsInIndex(databaseMetaData.getMaxColumnsInIndex());
        database.setMaxColumnsInOrderBy(databaseMetaData.getMaxColumnsInOrderBy());
        database.setMaxColumnsInSelect(databaseMetaData.getMaxColumnsInSelect());
        database.setMaxColumnsInTable(databaseMetaData.getMaxColumnsInTable());
        database.setMaxConnections(databaseMetaData.getMaxConnections());
        database.setMaxCursorNameLength(databaseMetaData.getMaxCursorNameLength());
        database.setMaxIndexLength(databaseMetaData.getMaxIndexLength());
        database.setMaxSchemaNameLength(databaseMetaData.getMaxSchemaNameLength());
        database.setMaxProcedureNameLength(databaseMetaData.getMaxProcedureNameLength());
        database.setMaxRowSize(databaseMetaData.getMaxRowSize());
        database.setDoesMaxRowSizeIncludeBlobs(databaseMetaData.doesMaxRowSizeIncludeBlobs());
        database.setMaxStatementLength(databaseMetaData.getMaxStatementLength());
        database.setMaxStatements(databaseMetaData.getMaxStatements());
        database.setMaxTableNameLength(databaseMetaData.getMaxTableNameLength());
        database.setMaxTablesInSelect(databaseMetaData.getMaxTablesInSelect());
        database.setMaxUserNameLength(databaseMetaData.getMaxUserNameLength());
        database.setNumericFunctions(
                new HashSet<>(Arrays.asList(databaseMetaData.getNumericFunctions().split(CrawlerConsts.COMMA_STR))));
        database.setProcedureTerm(databaseMetaData.getProcedureTerm());
        database.setResultSetHoldability(databaseMetaData.getResultSetHoldability());
        database.setSchemaTerm(databaseMetaData.getSchemaTerm());
        database.setSearchStringEscape(databaseMetaData.getSearchStringEscape());
        database.setSqlKeywords(
                new TreeSet<>(Arrays.asList(databaseMetaData.getSQLKeywords().split(CrawlerConsts.COMMA_STR))));
        database.setStringFunctions(
                new TreeSet<>(Arrays.asList(databaseMetaData.getStringFunctions().split(CrawlerConsts.COMMA_STR))));
        database.setSystemFunctions(
                new TreeSet<>(Arrays.asList(databaseMetaData.getSystemFunctions().split(CrawlerConsts.COMMA_STR))));
        database.setTimeDateFunctions(
                new TreeSet<>(Arrays.asList(databaseMetaData.getTimeDateFunctions().split(CrawlerConsts.COMMA_STR))));
        database.setSupportsTransactions(databaseMetaData.supportsTransactions());
        database.setSupportsDataDefinitionAndDataManipulationTransactions(
                databaseMetaData.supportsDataDefinitionAndDataManipulationTransactions());
        database.setDataDefinitionCausesTransactionCommit(databaseMetaData.dataDefinitionCausesTransactionCommit());
        database.setDataDefinitionIgnoredInTransactions(databaseMetaData.dataDefinitionIgnoredInTransactions());
        // lDatabase.setSupportsResultSetType(lDatabaseMetaData.supportsResultSetType());
        // lDatabase.setSupportsResultSetConcurrency(lDatabaseMetaData.supportsResultSetConcurrency());
        // lDatabase.setOwnUpdatesAreVisible(lDatabaseMetaData.ownUpdatesAreVisible());
        // lDatabase.setOwnDeletesAreVisible(lDatabaseMetaData.ownDeletesAreVisible());
        // lDatabase.setOwnInsertsAreVisible(lDatabaseMetaData.ownInsertsAreVisible());
        // lDatabase.setOthersUpdatesAreVisible(lDatabaseMetaData.othersUpdatesAreVisible());
        // lDatabase.setOthersDeletesAreVisible(lDatabaseMetaData.othersDeletesAreVisible());
        // lDatabase.setOthersInsertsAreVisible(lDatabaseMetaData.othersInsertsAreVisible());
        // lDatabase.setUpdatesAreDetected(lDatabaseMetaData.updatesAreDetected());
        // lDatabase.setDeletesAreDetected(lDatabaseMetaData.deletesAreDetected());
        // lDatabase.setInsertsAreDetected(lDatabaseMetaData.insertsAreDetected());
        // lDatabase.setSupportsResultSetHoldability(lDatabaseMetaData.supportsResultSetHoldability());
        // lDatabase.setSupportsTransactionIsolationLevel(lDatabaseMetaData.supportsTransactionIsolationLevel());
        database.setCatalogAtStart(databaseMetaData.isCatalogAtStart());
        database.setReadOnly(databaseMetaData.isReadOnly());
        database.setLocatorsUpdateCopy(databaseMetaData.locatorsUpdateCopy());
        database.setSupportsBatchUpdates(databaseMetaData.supportsBatchUpdates());
        database.setSupportsSavePoint(databaseMetaData.supportsAlterTableWithAddColumn());
        database.setSupportsNamedParameters(databaseMetaData.supportsNamedParameters());
        database.setSupportsMultipleOpenResults(databaseMetaData.supportsMultipleOpenResults());
        database.setSupportsGetGeneratedKeys(databaseMetaData.supportsGetGeneratedKeys());
        database.setSqlStateType(databaseMetaData.getSQLStateType());
        database.setSupportsStatementPooling(databaseMetaData.supportsStatementPooling());
        database.setAllProceduresAreCallable(databaseMetaData.allProceduresAreCallable());
        database.setAllTablesAreSelectable(databaseMetaData.allTablesAreSelectable());
        database.setUrl(databaseMetaData.getURL());
        database.setUserName(databaseMetaData.getUserName());
        database.setNullPlusNonNullIsNull(databaseMetaData.nullPlusNonNullIsNull());
        database.setNullsAreSortedHigh(databaseMetaData.nullsAreSortedHigh());
        database.setNullsAreSortedLow(databaseMetaData.nullsAreSortedLow());
        database.setNullsAreSortedAtStart(databaseMetaData.nullsAreSortedAtStart());
        database.setNullsAreSortedAtEnd(databaseMetaData.nullsAreSortedAtEnd());
        database.setAutoCommitFailureClosesAllResultSets(databaseMetaData.autoCommitFailureClosesAllResultSets());
        database.setGeneratedKeyAlwaysReturned(databaseMetaData.generatedKeyAlwaysReturned());
        database.setStoresLowerCaseIdentifiers(databaseMetaData.storesLowerCaseIdentifiers());
        database.setStoresLowerCaseQuotedIdentifiers(databaseMetaData.storesLowerCaseQuotedIdentifiers());
        database.setStoresMixedCaseIdentifiers(databaseMetaData.storesMixedCaseIdentifiers());
        database.setStoresMixedCaseQuotedIdentifiers(databaseMetaData.storesMixedCaseQuotedIdentifiers());
        database.setStoresUpperCaseIdentifiers(databaseMetaData.storesUpperCaseIdentifiers());
        database.setStoresUpperCaseQuotedIdentifiers(databaseMetaData.storesUpperCaseQuotedIdentifiers());
        database.setSupportsAlterTableWithAddColumn(databaseMetaData.supportsAlterTableWithAddColumn());
        database.setSupportsAlterTableWithDropColumn(databaseMetaData.supportsAlterTableWithDropColumn());
        database.setSupportsANSI92EntryLevelSQL(databaseMetaData.supportsANSI92EntryLevelSQL());
        database.setSupportsANSI92FullSQL(databaseMetaData.supportsANSI92FullSQL());
        database.setSupportsANSI92IntermediateSQL(databaseMetaData.supportsANSI92IntermediateSQL());
        database.setSupportsCatalogsInDataManipulation(databaseMetaData.supportsCatalogsInDataManipulation());
        database.setSupportsCatalogsInIndexDefinitions(databaseMetaData.supportsCatalogsInIndexDefinitions());
        database.setSupportsCatalogsInPrivilegeDefinitions(databaseMetaData.supportsCatalogsInPrivilegeDefinitions());
        database.setSupportsCatalogsInProcedureCalls(databaseMetaData.supportsCatalogsInProcedureCalls());
        database.setSupportsCatalogsInTableDefinitions(databaseMetaData.supportsCatalogsInTableDefinitions());
        database.setSupportsColumnAliasing(databaseMetaData.supportsColumnAliasing());
        database.setTableTypes(getTableTypes());
        database.setSequences(getSequences());
        database.setTables(getTables(application.getSchemaName(),
                tableName -> matches(application.getTablePatterns(), tableName)));
        // lDatabase.setFunctions(getProcedures(lDatabaseMetaData));
        repair();
        return database;
    }

    private boolean matches(final List<String> aPatterns, final String aValue) {
        boolean matches = aPatterns == null || aPatterns.isEmpty();
        if (!matches) {
            for (String pattern : aPatterns) {
                matches = Pattern.matches(pattern, aValue);
                if (matches) {
                    break;
                }
            }
        }
        return matches;
    }

    private Set<TableType> getTableTypes() throws SQLException {
        Set<TableType> tableTypes = new TreeSet<>();
        ResultSet resultset = databaseMetaData.getTableTypes();

        while (resultset.next()) {
            String s = resultset.getString("TABLE_TYPE");
            TableType lTableType = TableType.value(s);
            assert (lTableType != null) : "TableType can't be null for '" + s
                    + "', Check if all tables are created in Database";
            tableTypes.add(lTableType);
        }

        return tableTypes;
    }

    private List<String> getSequences() throws SQLException {
        List<String> sequences = new ArrayList<>();
        ResultSet resultset = databaseMetaData.getTables(null, null, null, new String[] { "SEQUENCE" });

        while (resultset.next()) {
            sequences.add(resultset.getString("table_name"));
        }

        return sequences;
    }

    private List<Table> getTables(final String aSchemeName, final Predicate<String> aTableFilter) throws SQLException {
        List<Table> lTables = new ArrayList<>();

        String lSchemaNamePattern = (database.getDbType() == DBType.MYSQL) ? aSchemeName : null;
        String lCatalog = database.getDbType() == DBType.MYSQL ? aSchemeName : null;

        ResultSet lResultSet = databaseMetaData.getTables(lCatalog, lSchemaNamePattern, null, new String[] { "TABLE" });
        while (lResultSet.next()) {
            final String tableName = lResultSet.getString("table_name");
            if (aTableFilter.test(tableName)) {
                Table bTable = new Table(database);
                bTable.setTableName(tableName);
                bTable.setCategoryName(lResultSet.getString("table_cat"));
                bTable.setSchemaName(lResultSet.getString("table_schem"));
                bTable.setTableType(TableType.value(lResultSet.getString("table_type")));
                bTable.setRemarks(lResultSet.getString("remarks"));
                // bTable.setCategoryType(lResultSet.getString("type_cat"));
                // bTable.setSchemaType(lResultSet.getString("type_schem"));
                // bTable.setNameType(lResultSet.getString("type_name"));
                // bTable.setSelfReferencingColumnName(lResultSet.getString("self_referencing_col_name"));
                // bTable.setReferenceGeneration(lResultSet.getString("ref_generation"));

                bTable.setColumns(getColumns(bTable));
                bTable.setIndices(getIndices(bTable));
                bTable.setUniqueColumns(getUniqueConstraints(bTable));
                // Set Sequence
                database.getSequences().stream().filter(sequenceName -> sequenceName.contains(tableName)).findFirst()
                        .ifPresent(bTable::setSequenceName);
                lTables.add(bTable);
            }
        }

        return lTables;
    }

    private List<Index> getIndices(final Table aTable) throws SQLException {
        List<Index> indices = new ArrayList<>();

        ResultSet indexResultset = databaseMetaData.getIndexInfo(null, null, aTable.getTableName(), true, true);

        while (indexResultset.next()) {
            Index bIndex = new Index(aTable);
            bIndex.setColumnName(indexResultset.getString("COLUMN_NAME"));
            bIndex.setOrdinalPosition(indexResultset.getShort("ORDINAL_POSITION"));

            bIndex.setIndexName(indexResultset.getString("INDEX_NAME"));
            bIndex.setIndexQualifier(indexResultset.getString("INDEX_QUALIFIER"));
            bIndex.setCardinality(indexResultset.getInt("CARDINALITY"));
            String ascDesc = indexResultset.getString("ASC_OR_DESC");
            if (ascDesc != null) {
                bIndex.setOrder(Order.value(ascDesc));
            }
            bIndex.setFilterCondition(indexResultset.getString("FILTER_CONDITION"));
            bIndex.setPages(indexResultset.getInt("PAGES"));
            bIndex.setType(indexResultset.getShort("TYPE"));
            bIndex.setNonUnique(indexResultset.getBoolean("NON_UNIQUE"));
            indices.add(bIndex);
        }
        return indices;
    }

    private List<Column> getColumns(final Table aTable) throws SQLException {
        List<Column> lColumns = new ArrayList<>();
        ResultSet lColumnResultSet = databaseMetaData.getColumns(null, null, aTable.getTableName(), null);
        ColumnType lColumnType;
        while (lColumnResultSet.next()) {
            Column bColumn = new Column(aTable);
            bColumn.setColumnName(lColumnResultSet.getString("COLUMN_NAME"));
            bColumn.setTableName(lColumnResultSet.getString("TABLE_NAME"));
            bColumn.setTypeName(lColumnResultSet.getString("TYPE_NAME"));
            lColumnType = ColumnType.value(JDBCType.valueOf(lColumnResultSet.getInt("DATA_TYPE")));
            bColumn.setColumnType(lColumnType == ColumnType.OTHER ? getColumnTypeForOthers(bColumn) : lColumnType);
            bColumn.setSize(lColumnResultSet.getInt("COLUMN_SIZE"));
            bColumn.setDecimalDigits(lColumnResultSet.getInt("DECIMAL_DIGITS"));
            bColumn.setRemarks(lColumnResultSet.getString("REMARKS"));
            bColumn.setNullable(Flag.value(lColumnResultSet.getString("IS_NULLABLE")));
            bColumn.setAutoIncrement(Flag.value(lColumnResultSet.getString("IS_AUTOINCREMENT")));
            bColumn.setTableCategory(lColumnResultSet.getString("TABLE_CAT"));
            bColumn.setTableSchema(lColumnResultSet.getString("TABLE_SCHEM"));
            bColumn.setBufferLength(lColumnResultSet.getInt("BUFFER_LENGTH"));
            bColumn.setNumberPrecisionRadix(lColumnResultSet.getInt("NUM_PREC_RADIX"));
            bColumn.setColumnDefinition(lColumnResultSet.getString("COLUMN_DEF"));
            bColumn.setOrdinalPosition(lColumnResultSet.getInt("ORDINAL_POSITION"));
            bColumn.setScopeCatalog(lColumnResultSet.getString("SCOPE_CATALOG"));
            bColumn.setScopeSchema(lColumnResultSet.getString("SCOPE_SCHEMA"));
            bColumn.setScopeTable(lColumnResultSet.getString("SCOPE_TABLE"));
            bColumn.setSourceDataType(lColumnResultSet.getString("SOURCE_DATA_TYPE"));
            bColumn.setGeneratedColumn(Flag.value(lColumnResultSet.getString("IS_GENERATEDCOLUMN")));
            bColumn.setExportedKeys(new TreeSet<>());
            lColumns.add(bColumn);
        }

        // Fill Primary Keys
        ResultSet primaryKeysResultSet = databaseMetaData.getPrimaryKeys(null, null, aTable.getTableName());
        while (primaryKeysResultSet.next()) {
            lColumns.stream().filter(column -> {
                try {
                    return column.getColumnName().equals(primaryKeysResultSet.getString("COLUMN_NAME"));
                } catch (SQLException throwables) {
                    return false;
                }
            }).findFirst().ifPresent(column -> {
                try {
                    column.setPrimaryKeyIndex(primaryKeysResultSet.getInt("KEY_SEQ"));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
        }

        // Extracting Foreign Keys.
        ResultSet foreignKeysResultSet = databaseMetaData.getExportedKeys(null, null, aTable.getTableName());
        while (foreignKeysResultSet.next()) {
            Key bKey = new Key();
            bKey.setTableName(foreignKeysResultSet.getString("FKTABLE_NAME"));
            bKey.setColumnName(foreignKeysResultSet.getString("FKCOLUMN_NAME"));
            if (!lColumns.isEmpty()) {
                lColumns.stream().filter(column -> {
                    try {
                        return column.getColumnName().equals(foreignKeysResultSet.getString("PKCOLUMN_NAME"));
                    } catch (SQLException throwables) {
                        return false;
                    }
                }).findFirst().ifPresent(column -> column.getExportedKeys().add(bKey));
            }
        }
        return lColumns;
    }

    private ColumnType getColumnTypeForOthers(final Column aColumn) {
        if (aColumn.getTable().getDatabase().getDbType() == DBType.POSTGRES) {
            return ColumnType.findEnum(aColumn.getTypeName());
        }

        return null;
    }

    private List<Procedure> getProcedures() throws SQLException {
        List<Procedure> lProcedures = new ArrayList<>();
        ResultSet lResultSet = databaseMetaData.getProcedures(null, null, null);
        while (lResultSet.next()) {
            Procedure function = new Procedure();
            function.setFunctionName(lResultSet.getString("PROCEDURE_NAME"));
            function.setFunctionCategory(lResultSet.getString("PROCEDURE_CAT"));
            function.setFunctionSchema(lResultSet.getString("PROCEDURE_SCHEM"));
            function.setFunctionType(lResultSet.getShort("PROCEDURE_TYPE"));
            function.setRemarks(lResultSet.getString("REMARKS"));
            function.setSpecificName(lResultSet.getString("SPECIFIC_NAME"));
            lProcedures.add(function);
        }
        return lProcedures;
    }

    private void repair() {
        switch (database.getDbType()) {
        case MARIADB:
        case MYSQL: {
            repairMySQL();
            break;
        }
        }
    }

    public List<UniqueConstraint> getUniqueConstraints(final Table aTable) throws SQLException {
        List<UniqueConstraint> lUniqueConstraints = new ArrayList<>();
        ResultSet rs = databaseMetaData.getIndexInfo(null, aTable.getSchemaName(), aTable.getTableName(), true, true);
        while (rs.next()) {
            String indexName = rs.getString("index_name");
            String columnName = rs.getString("column_name");
            Optional<UniqueConstraint> lUniqueConstraint = lUniqueConstraints.stream()
                    .filter(uniqueConstraint -> uniqueConstraint.getName().equals(indexName)).findFirst();
            if (lUniqueConstraint.isPresent()) {
                lUniqueConstraint.get().getColumns().add(aTable.getColumns().stream()
                        .filter(column -> column.getColumnName().equals(columnName)).findFirst().get());

            } else {
                UniqueConstraint bUniqueConstraint = new UniqueConstraint();
                bUniqueConstraint.setName(indexName);
                List<Column> bColumns = new ArrayList<>();
                bColumns.add(aTable.getColumns().stream().filter(column -> column.getColumnName().equals(columnName))
                        .findFirst().get());
                bUniqueConstraint.setColumns(bColumns);
                lUniqueConstraints.add(bUniqueConstraint);
            }
        }

        return lUniqueConstraints;
    }

    private void repairMySQL() {
        database.getTables().forEach(table -> {
            try (PreparedStatement preparedStatement = databaseMetaData.getConnection().prepareStatement("SELECT "
                    + "COLUMN_NAME,COLUMN_TYPE  from INFORMATION_SCHEMA.COLUMNS where\n" + " table_name = ?")) {
                preparedStatement.setString(1, table.getTableName());
                ResultSet lResultSet = preparedStatement.executeQuery();

                Column bColumn;
                String bColumnType;
                while (lResultSet.next()) {
                    bColumn = table.getColumns().stream().filter(column1 -> {
                        try {
                            return column1.getColumnName().equals(lResultSet.getString("COLUMN_NAME"));
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        return false;
                    }).findFirst().get();
                    bColumnType = lResultSet.getString("COLUMN_TYPE");
                    String[] s = bColumnType.split(CrawlerConsts.START_BR_REGX);
                    bColumn.setTypeName(s[0].trim());

                    ColumnType columnType1 = ColumnType.value(bColumn.getTypeName().toUpperCase());
                    if (columnType1 != null) {
                        bColumn.setColumnType(columnType1);
                    }
                    if (s.length == 2) {
                        String grp = s[1].trim().replaceAll(CrawlerConsts.END_BR_REGX, "");

                        s = grp.split(CrawlerConsts.COMMA_STR);
                        bColumn.setSize(Integer.parseInt(s[0]));
                        if (s.length == 2) {
                            bColumn.setDecimalDigits(Integer.parseInt(s[1]));
                        }
                    }
                }
            } catch (final SQLException aSQLException) {
                aSQLException.printStackTrace();
            }
        });
    }
}
