package org.sqlcomponents.core.model.relational;


import org.sqlcomponents.core.model.relational.enums.DBType;
import org.sqlcomponents.core.model.relational.enums.TableType;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The type representing a database.
 */

public class Database {
    /**
     * The Tables.
     */
    private List<Table> tables;
    /**
     * The Functions.
     */
    private List<Procedure> functions;
    /**
     * The Packages.
     */
    private List<Package> packages;
    /**
     * The Sequences.
     */
    private List<String> sequences;

    /**
     * The Table types.
     */
    private Set<TableType> tableTypes;

    /**
     * The Catalog term.
     */
    private String catalogTerm;
    /**
     * The Catalog seperator.
     */
    private String catalogSeperator;
    /**
     * The Db type.
     */
// private String databaseProductName;
    private DBType dbType;
    /**
     * The Database major version.
     */
    private int databaseMajorVersion;
    /**
     * The Database minor version.
     */
    private int databaseMinorVersion;
    /**
     * The Database product version.
     */
    private String databaseProductVersion;
    /**
     * The Default transaction isolation.
     */
    private int defaultTransactionIsolation;
    /**
     * The Driver major version.
     */
    private int driverMajorVersion;
    /**
     * The Driver minor version.
     */
    private int driverMinorVersion;
    /**
     * The Driver name.
     */
    private String driverName;
    /**
     * The Driver version.
     */
    private String driverVersion;
    /**
     * The Extra name characters.
     */
    private String extraNameCharacters;
    /**
     * The Identifier quote string.
     */
    private String identifierQuoteString;
    /**
     * The Jdbc major version.
     */
    private int jdbcMajorVersion;
    /**
     * The Jdbc minor version.
     */
    private int jdbcMinorVersion;
    /**
     * The Max binary literal length.
     */
    private int maxBinaryLiteralLength;
    /**
     * The Max catalog name length.
     */
    private int maxCatalogNameLength;
    /**
     * The Max char literal length.
     */
    private int maxCharLiteralLength;
    /**
     * The Max column name length.
     */
    private int maxColumnNameLength;
    /**
     * The Max columns in group by.
     */
    private int maxColumnsInGroupBy;
    /**
     * The Max columns in index.
     */
    private int maxColumnsInIndex;
    /**
     * The Max columns in order by.
     */
    private int maxColumnsInOrderBy;
    /**
     * The Max columns in select.
     */
    private int maxColumnsInSelect;
    /**
     * The Max columns in table.
     */
    private int maxColumnsInTable;
    /**
     * The Max connections.
     */
    private int maxConnections;
    /**
     * The Max cursor name length.
     */
    private int maxCursorNameLength;
    /**
     * The Max index length.
     */
    private int maxIndexLength;
    /**
     * The Max schema name length.
     */
// private long maxLogicalLobSize;
    private int maxSchemaNameLength;
    /**
     * The Max procedure name length.
     */
    private int maxProcedureNameLength;
    /**
     * The Max row size.
     */
    private int maxRowSize;
    /**
     * The Does max row size include blobs.
     */
    private boolean doesMaxRowSizeIncludeBlobs;
    /**
     * The Max statement length.
     */
    private int maxStatementLength;
    /**
     * The Max statements.
     */
    private int maxStatements;
    /**
     * The Max table name length.
     */
    private int maxTableNameLength;
    /**
     * The Max tables in select.
     */
    private int maxTablesInSelect;
    /**
     * The Max username length.
     */
    private int maxUserNameLength;
    /**
     * The Numeric functions.
     */
    private Set<String> numericFunctions;
    /**
     * The Procedure term.
     */
    private String procedureTerm;
    /**
     * The Result set holdability.
     */
    private int resultSetHoldability;
    /**
     * The Schema term.
     */
// private rowidlifetime rowIdLifeTime;
    private String schemaTerm;
    /**
     * The Search string escape.
     */
    private String searchStringEscape;
    /**
     * The Sql keywords.
     */
    private SortedSet<String> sqlKeywords;
    /**
     * The String functions.
     */
    private SortedSet<String> stringFunctions;
    /**
     * The System functions.
     */
    private SortedSet<String> systemFunctions;
    /**
     * The Time date functions.
     */
    private SortedSet<String> timeDateFunctions;
    /**
     * The Types from the DB.
     */
    private List<Type> types;
    /**
     * The Supports transactions.
     */
    private boolean supportsTransactions;
    /**
     * The Supports transaction isolation level.
     */
    private boolean supportsTransactionIsolationLevel;
    /**
     * The Supports data definition and data manipulation transactions.
     */
    private boolean supportsDataDefinitionAndDataManipulationTransactions;
    /**
     * The Data definition causes transaction commit.
     */
    private boolean dataDefinitionCausesTransactionCommit;
    /**
     * The Data definition ignored in transactions.
     */
    private boolean dataDefinitionIgnoredInTransactions;
    /**
     * The Supports result set type.
     */
    private boolean supportsResultSetType;
    /**
     * The Supports result set concurrency.
     */
    private boolean supportsResultSetConcurrency;
    /**
     * The Own updates are visible.
     */
    private boolean ownUpdatesAreVisible;
    /**
     * The Own deletes are visible.
     */
    private boolean ownDeletesAreVisible;
    /**
     * The Own inserts are visible.
     */
    private boolean ownInsertsAreVisible;
    /**
     * The Others updates are visible.
     */
    private boolean othersUpdatesAreVisible;
    /**
     * The Others deletes are visible.
     */
    private boolean othersDeletesAreVisible;
    /**
     * The Others inserts are visible.
     */
    private boolean othersInsertsAreVisible;
    /**
     * The Updates are detected.
     */
    private boolean updatesAreDetected;
    /**
     * The Deletes are detected.
     */
    private boolean deletesAreDetected;
    /**
     * The Inserts are detected.
     */
    private boolean insertsAreDetected;
    /**
     * The Is catalog at start.
     */
    private boolean isCatalogAtStart;
    /**
     * The Is read only.
     */
    private boolean isReadOnly;
    /**
     * The Locators update copy.
     */
    private boolean locatorsUpdateCopy;
    /**
     * The Supports batch updates.
     */
    private boolean supportsBatchUpdates;
    /**
     * The Supports savepoints.
     */
// private Connection getConnection;
    private boolean supportsSavepoints;
    /**
     * The Supports named parameters.
     */
    private boolean supportsNamedParameters;
    /**
     * The Supports multiple open results.
     */
    private boolean supportsMultipleOpenResults;
    /**
     * The Supports get generated keys.
     */
    private boolean supportsGetGeneratedKeys;
    /**
     * The Supports result set holdability.
     */
    private boolean supportsResultSetHoldability;
    /**
     * The Sql state type.
     */
    private int sqlStateType;
    /**
     * The Supports statement pooling.
     */
    private boolean supportsStatementPooling;
    /**
     * The All procedures are callable.
     */
    private boolean allProceduresAreCallable;
    /**
     * The All tables are selectable.
     */
    private boolean allTablesAreSelectable;
    /**
     * The Url.
     */
    private String url;
    /**
     * The User name.
     */
    private String userName;
    /**
     * The Null plus non null is null.
     */
    private boolean nullPlusNonNullIsNull;
    /**
     * The Nulls are sorted high.
     */
    private boolean nullsAreSortedHigh;
    /**
     * The Nulls are sorted low.
     */
    private boolean nullsAreSortedLow;
    /**
     * The Nulls are sorted at start.
     */
    private boolean nullsAreSortedAtStart;
    /**
     * The Nulls are sorted at end.
     */
    private boolean nullsAreSortedAtEnd;
    /**
     * The Auto commit failure closes all result sets.
     */
    private boolean autoCommitFailureClosesAllResultSets;
    /**
     * The Generated key always returned.
     */
    private boolean generatedKeyAlwaysReturned;
    /**
     * The Stores lower case identifiers.
     */
    private boolean storesLowerCaseIdentifiers;
    /**
     * The Stores lower case quoted identifiers.
     */
    private boolean storesLowerCaseQuotedIdentifiers;
    /**
     * The Stores mixed case identifiers.
     */
    private boolean storesMixedCaseIdentifiers;
    /**
     * The Stores mixed case quoted identifiers.
     */
    private boolean storesMixedCaseQuotedIdentifiers;
    /**
     * The Stores upper case identifiers.
     */
    private boolean storesUpperCaseIdentifiers;
    /**
     * The Stores upper case quoted identifiers.
     */
    private boolean storesUpperCaseQuotedIdentifiers;
    /**
     * The Supports alter table with add column.
     */
    private boolean supportsAlterTableWithAddColumn;
    /**
     * The Supports alter table with drop column.
     */
    private boolean supportsAlterTableWithDropColumn;
    /**
     * The Supports ansi 92 entry level sql.
     */
    private boolean supportsANSI92EntryLevelSQL;
    /**
     * The Supports ansi 92 full sql.
     */
    private boolean supportsANSI92FullSQL;
    /**
     * The Supports ansi 92 intermediate sql.
     */
    private boolean supportsANSI92IntermediateSQL;
    /**
     * The Supports catalogs in data manipulation.
     */
    private boolean supportsCatalogsInDataManipulation;
    /**
     * The Supports catalogs in index definitions.
     */
    private boolean supportsCatalogsInIndexDefinitions;
    /**
     * The Supports catalogs in privilege definitions.
     */
    private boolean supportsCatalogsInPrivilegeDefinitions;
    /**
     * The Supports catalogs in procedure calls.
     */
    private boolean supportsCatalogsInProcedureCalls;
    /**
     * The Supports catalogs in table definitions.
     */
    private boolean supportsCatalogsInTableDefinitions;
    /**
     * The Supports column aliasing.
     */
    private boolean supportsColumnAliasing;
    /**
     * The Supports convert.
     */
    private boolean supportsConvert;
    /**
     * The Supports core sql grammar.
     */
    private boolean supportsCoreSQLGrammar;
    /**
     * The Supports correlated subqueries.
     */
    private boolean supportsCorrelatedSubqueries;
    /**
     * The Supports data manipulation transactions only.
     */
    private boolean supportsDataManipulationTransactionsOnly;
    /**
     * The Supports different table correlation names.
     */
    private boolean supportsDifferentTableCorrelationNames;
    /**
     * The Supports expressions in order by.
     */
    private boolean supportsExpressionsInOrderBy;
    /**
     * The Supports extended sql grammar.
     */
    private boolean supportsExtendedSQLGrammar;
    /**
     * The Supports full outer joins.
     */
    private boolean supportsFullOuterJoins;
    /**
     * The Supports group by.
     */
    private boolean supportsGroupBy;
    /**
     * The Supports group by beyond select.
     */
    private boolean supportsGroupByBeyondSelect;
    /**
     * The Supports group by unrelated.
     */
    private boolean supportsGroupByUnrelated;
    /**
     * The Supports integrity enhancement facility.
     */
    private boolean supportsIntegrityEnhancementFacility;
    /**
     * The Supports like escape clause.
     */
    private boolean supportsLikeEscapeClause;
    /**
     * The Supports limited outer joins.
     */
    private boolean supportsLimitedOuterJoins;
    /**
     * The Supports minimum sql grammar.
     */
    private boolean supportsMinimumSQLGrammar;
    /**
     * The Supports mixed case identifiers.
     */
    private boolean supportsMixedCaseIdentifiers;
    /**
     * The Supports mixed case quote identifiers.
     */
    private boolean supportsMixedCaseQuoteIdentifiers;
    /**
     * The Supports multiple result sets.
     */
    private boolean supportsMultipleResultSets;
    /**
     * The Supports multiple transactions.
     */
    private boolean supportsMultipleTransactions;
    /**
     * The Supports non nullable columns.
     */
    private boolean supportsNonNullableColumns;
    /**
     * The Supports open cursors across commit.
     */
    private boolean supportsOpenCursorsAcrossCommit;
    /**
     * The Supports open cursors across rollback.
     */
    private boolean supportsOpenCursorsAcrossRollback;
    /**
     * The Supports order by unrelated.
     */
    private boolean supportsOrderByUnrelated;
    /**
     * The Supports outer joins.
     */
    private boolean supportsOuterJoins;
    /**
     * The Supports positioned delete.
     */
    private boolean supportsPositionedDelete;
    /**
     * The Supports positioned update.
     */
    private boolean supportsPositionedUpdate;
    /**
     * The Supports ref cursors.
     */
    private boolean supportsRefCursors;
    /**
     * The Supports save point.
     */
    private boolean supportsSavePoint;
    /**
     * The Supports schema in data manipulation.
     */
    private boolean supportsSchemaInDataManipulation;
    /**
     * The Supports schema in index definition.
     */
    private boolean supportsSchemaInIndexDefinition;
    /**
     * The Supports schemas in privilege definitions.
     */
    private boolean supportsSchemasInPrivilegeDefinitions;
    /**
     * The Supports schemas in procedure calls.
     */
    private boolean supportsSchemasInProcedureCalls;
    /**
     * The Supports schemas in table definitions.
     */
    private boolean supportsSchemasInTableDefinitions;
    /**
     * The Supports select for update.
     */
    private boolean supportsSelectForUpdate;
    /**
     * The Supports sharding.
     */
    private boolean supportsSharding;
    /**
     * The Supports stored functions using call syntax.
     */
    private boolean supportsStoredFunctionsUsingCallSyntax;
    /**
     * The Supports stored procedures.
     */
    private boolean supportsStoredProcedures;
    /**
     * The Supports subqueries in comparisons.
     */
    private boolean supportsSubqueriesInComparisons;
    /**
     * The Supports subqueries in exists.
     */
    private boolean supportsSubqueriesInExists;
    /**
     * The Supports subqueries in ins.
     */
    private boolean supportsSubqueriesInIns;
    /**
     * The Supports subqueries in quantifieds.
     */
    private boolean supportsSubqueriesInQuantifieds;
    /**
     * The Supports table correlation names.
     */
    private boolean supportsTableCorrelationNames;
    /**
     * The Supports union.
     */
    private boolean supportsUnion;
    /**
     * The Supports union all.
     */
    private boolean supportsUnionAll;
    /**
     * The Users local file per table.
     */
    private boolean usersLocalFilePerTable;
    /**
     * The Users local files.
     */
    private boolean usersLocalFiles;

    /**
     * Used to get Escaped Name for tableName or columnName.
     *
     * @param name the name
     * @return escapedName string
     */
    public String escapedName(final String name) {
        boolean shouldEscape = this.getSqlKeywords().stream()
                .anyMatch(keyword -> keyword.equalsIgnoreCase(name));
        return shouldEscape
                ?
                this.identifierQuoteString + name + this.identifierQuoteString
                : name;
    }

    /**
     * Gets distinct column type names.
     *
     * @return the distinct column type names
     */
    public SortedSet<String> getDistinctColumnTypeNames() {
        SortedSet<String> distinctColumnTypeNames = new TreeSet<>();
        this.tables.stream().forEach(table -> {
            distinctColumnTypeNames.addAll(table.getDistinctColumnTypeNames());
        });

        return distinctColumnTypeNames;
    }

    /**
     * Gets distinct custom column type names.
     *
     * @return the distinct custom column type names
     */
    public SortedSet<String> getDistinctCustomColumnTypeNames() {
        SortedSet<String> distinctColumnTypeNames = new TreeSet<>();
        this.tables.stream().forEach(table -> {
            distinctColumnTypeNames.addAll(
                    table.getDistinctCustomColumnTypeNames());
        });

        return distinctColumnTypeNames;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(final List<Table> theTables) {
        this.tables = theTables;
    }

    public List<Procedure> getFunctions() {
        return functions;
    }

    public void setFunctions(final List<Procedure> theFunctions) {
        this.functions = theFunctions;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(final List<Package> thePackages) {
        this.packages = thePackages;
    }

    public List<String> getSequences() {
        return sequences;
    }

    public void setSequences(final List<String> theSequences) {
        this.sequences = theSequences;
    }

    public Set<TableType> getTableTypes() {
        return tableTypes;
    }

    public void setTableTypes(final Set<TableType> theTableTypes) {
        this.tableTypes = theTableTypes;
    }

    public String getCatalogTerm() {
        return catalogTerm;
    }

    public void setCatalogTerm(final String theCatalogTerm) {
        this.catalogTerm = theCatalogTerm;
    }

    public String getCatalogSeperator() {
        return catalogSeperator;
    }

    public void setCatalogSeperator(final String theCatalogSeperator) {
        this.catalogSeperator = theCatalogSeperator;
    }

    public DBType getDbType() {
        return dbType;
    }

    public void setDbType(final DBType theDbType) {
        this.dbType = theDbType;
    }

    public int getDatabaseMajorVersion() {
        return databaseMajorVersion;
    }

    public void setDatabaseMajorVersion(final int theDatabaseMajorVersion) {
        this.databaseMajorVersion = theDatabaseMajorVersion;
    }

    public int getDatabaseMinorVersion() {
        return databaseMinorVersion;
    }

    public void setDatabaseMinorVersion(final int theDatabaseMinorVersion) {
        this.databaseMinorVersion = theDatabaseMinorVersion;
    }

    public String getDatabaseProductVersion() {
        return databaseProductVersion;
    }

    public void setDatabaseProductVersion(
            final String theDatabaseProductVersion) {
        this.databaseProductVersion = theDatabaseProductVersion;
    }

    public int getDefaultTransactionIsolation() {
        return defaultTransactionIsolation;
    }

    public void setDefaultTransactionIsolation(
            final int theDefaultTransactionIsolation) {
        this.defaultTransactionIsolation = theDefaultTransactionIsolation;
    }

    public int getDriverMajorVersion() {
        return driverMajorVersion;
    }

    public void setDriverMajorVersion(
            final int theDriverMajorVersion) {
        this.driverMajorVersion = theDriverMajorVersion;
    }

    public int getDriverMinorVersion() {
        return driverMinorVersion;
    }

    public void setDriverMinorVersion(
            final int theDriverMinorVersion) {
        this.driverMinorVersion = theDriverMinorVersion;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(
            final String theDriverName) {
        this.driverName = theDriverName;
    }

    public String getDriverVersion() {
        return driverVersion;
    }

    public void setDriverVersion(
            final String theDriverVersion) {
        this.driverVersion = theDriverVersion;
    }

    public String getExtraNameCharacters() {
        return extraNameCharacters;
    }

    public void setExtraNameCharacters(
            final String theExtraNameCharacters) {
        this.extraNameCharacters = theExtraNameCharacters;
    }

    public String getIdentifierQuoteString() {
        return identifierQuoteString;
    }

    public void setIdentifierQuoteString(
            final String theIdentifierQuoteString) {
        this.identifierQuoteString = theIdentifierQuoteString;
    }

    public int getJdbcMajorVersion() {
        return jdbcMajorVersion;
    }

    public void setJdbcMajorVersion(final int theJdbcMajorVersion) {
        this.jdbcMajorVersion = theJdbcMajorVersion;
    }

    public int getJdbcMinorVersion() {
        return jdbcMinorVersion;
    }

    public void setJdbcMinorVersion(final int theJdbcMinorVersion) {
        this.jdbcMinorVersion = theJdbcMinorVersion;
    }

    public int getMaxBinaryLiteralLength() {
        return maxBinaryLiteralLength;
    }

    public void setMaxBinaryLiteralLength(final int theMaxBinaryLiteralLength) {
        this.maxBinaryLiteralLength = theMaxBinaryLiteralLength;
    }

    public int getMaxCatalogNameLength() {
        return maxCatalogNameLength;
    }

    public void setMaxCatalogNameLength(final int theMaxCatalogNameLength) {
        this.maxCatalogNameLength = theMaxCatalogNameLength;
    }

    public int getMaxCharLiteralLength() {
        return maxCharLiteralLength;
    }

    public void setMaxCharLiteralLength(final int theMaxCharLiteralLength) {
        this.maxCharLiteralLength = theMaxCharLiteralLength;
    }

    public int getMaxColumnNameLength() {
        return maxColumnNameLength;
    }

    public void setMaxColumnNameLength(final int theMaxColumnNameLength) {
        this.maxColumnNameLength = theMaxColumnNameLength;
    }

    public int getMaxColumnsInGroupBy() {
        return maxColumnsInGroupBy;
    }

    public void setMaxColumnsInGroupBy(final int theMaxColumnsInGroupBy) {
        this.maxColumnsInGroupBy = theMaxColumnsInGroupBy;
    }

    public int getMaxColumnsInIndex() {
        return maxColumnsInIndex;
    }

    public void setMaxColumnsInIndex(final int theMaxColumnsInIndex) {
        this.maxColumnsInIndex = theMaxColumnsInIndex;
    }

    public int getMaxColumnsInOrderBy() {
        return maxColumnsInOrderBy;
    }

    public void setMaxColumnsInOrderBy(final int theMaxColumnsInOrderBy) {
        this.maxColumnsInOrderBy = theMaxColumnsInOrderBy;
    }

    public int getMaxColumnsInSelect() {
        return maxColumnsInSelect;
    }

    public void setMaxColumnsInSelect(final int theMaxColumnsInSelect) {
        this.maxColumnsInSelect = theMaxColumnsInSelect;
    }

    public int getMaxColumnsInTable() {
        return maxColumnsInTable;
    }

    public void setMaxColumnsInTable(final int theMaxColumnsInTable) {
        this.maxColumnsInTable = theMaxColumnsInTable;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(final int theMaxConnections) {
        this.maxConnections = theMaxConnections;
    }

    public int getMaxCursorNameLength() {
        return maxCursorNameLength;
    }

    public void setMaxCursorNameLength(final int theMaxCursorNameLength) {
        this.maxCursorNameLength = theMaxCursorNameLength;
    }

    public int getMaxIndexLength() {
        return maxIndexLength;
    }

    public void setMaxIndexLength(final int theMaxIndexLength) {
        this.maxIndexLength = theMaxIndexLength;
    }

    public int getMaxSchemaNameLength() {
        return maxSchemaNameLength;
    }

    public void setMaxSchemaNameLength(final int theMaxSchemaNameLength) {
        this.maxSchemaNameLength = theMaxSchemaNameLength;
    }

    public int getMaxProcedureNameLength() {
        return maxProcedureNameLength;
    }

    public void setMaxProcedureNameLength(final int theMaxProcedureNameLength) {
        this.maxProcedureNameLength = theMaxProcedureNameLength;
    }

    public int getMaxRowSize() {
        return maxRowSize;
    }

    public void setMaxRowSize(final int theMaxRowSize) {
        this.maxRowSize = theMaxRowSize;
    }

    public boolean isDoesMaxRowSizeIncludeBlobs() {
        return doesMaxRowSizeIncludeBlobs;
    }

    public void setDoesMaxRowSizeIncludeBlobs(
            final boolean theDoesMaxRowSizeIncludeBlobs) {
        this.doesMaxRowSizeIncludeBlobs = theDoesMaxRowSizeIncludeBlobs;
    }

    public int getMaxStatementLength() {
        return maxStatementLength;
    }

    public void setMaxStatementLength(final int theMaxStatementLength) {
        this.maxStatementLength = theMaxStatementLength;
    }

    public int getMaxStatements() {
        return maxStatements;
    }

    public void setMaxStatements(final int theMaxStatements) {
        this.maxStatements = theMaxStatements;
    }

    public int getMaxTableNameLength() {
        return maxTableNameLength;
    }

    public void setMaxTableNameLength(final int theMaxTableNameLength) {
        this.maxTableNameLength = theMaxTableNameLength;
    }

    public int getMaxTablesInSelect() {
        return maxTablesInSelect;
    }

    public void setMaxTablesInSelect(final int theMaxTablesInSelect) {
        this.maxTablesInSelect = theMaxTablesInSelect;
    }

    public int getMaxUserNameLength() {
        return maxUserNameLength;
    }

    public void setMaxUserNameLength(final int theMaxUserNameLength) {
        this.maxUserNameLength = theMaxUserNameLength;
    }

    public Set<String> getNumericFunctions() {
        return numericFunctions;
    }

    public void setNumericFunctions(final Set<String> theNumericFunctions) {
        this.numericFunctions = theNumericFunctions;
    }

    public String getProcedureTerm() {
        return procedureTerm;
    }

    public void setProcedureTerm(final String theProcedureTerm) {
        this.procedureTerm = theProcedureTerm;
    }

    public int getResultSetHoldability() {
        return resultSetHoldability;
    }

    public void setResultSetHoldability(final int theResultSetHoldability) {
        this.resultSetHoldability = theResultSetHoldability;
    }

    public String getSchemaTerm() {
        return schemaTerm;
    }

    public void setSchemaTerm(final String theSchemaTerm) {
        this.schemaTerm = theSchemaTerm;
    }

    public String getSearchStringEscape() {
        return searchStringEscape;
    }

    public void setSearchStringEscape(final String theSearchStringEscape) {
        this.searchStringEscape = theSearchStringEscape;
    }

    public SortedSet<String> getSqlKeywords() {
        return sqlKeywords;
    }

    public void setSqlKeywords(final SortedSet<String> theSqlKeywords) {
        this.sqlKeywords = theSqlKeywords;
    }

    public SortedSet<String> getStringFunctions() {
        return stringFunctions;
    }

    public void setStringFunctions(final SortedSet<String> theStringFunctions) {
        this.stringFunctions = theStringFunctions;
    }

    public SortedSet<String> getSystemFunctions() {
        return systemFunctions;
    }

    public void setSystemFunctions(final SortedSet<String> theSystemFunctions) {
        this.systemFunctions = theSystemFunctions;
    }

    public SortedSet<String> getTimeDateFunctions() {
        return timeDateFunctions;
    }

    public void setTimeDateFunctions(
            final SortedSet<String> theTimeDateFunctions) {
        this.timeDateFunctions = theTimeDateFunctions;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(final List<Type> theTypes) {
        this.types = theTypes;
    }
    public boolean isSupportsTransactions() {
        return supportsTransactions;
    }

    public void setSupportsTransactions(final boolean theSupportsTransactions) {
        this.supportsTransactions = theSupportsTransactions;
    }

    public boolean isSupportsTransactionIsolationLevel() {
        return supportsTransactionIsolationLevel;
    }

    public void setSupportsTransactionIsolationLevel(
            final boolean theSupportsTransactionIsolationLevel) {
        this.supportsTransactionIsolationLevel =
                theSupportsTransactionIsolationLevel;
    }

    public boolean isSupportsDataDefinitionAndDataManipulationTransactions() {
        return supportsDataDefinitionAndDataManipulationTransactions;
    }

    public void setSupportsDataDefinitionAndDataManipulationTransactions(
            final boolean
                    theSupportsDataDefinitionAndDataManipulationTransactions) {
        this.supportsDataDefinitionAndDataManipulationTransactions =
                theSupportsDataDefinitionAndDataManipulationTransactions;
    }

    public boolean isDataDefinitionCausesTransactionCommit() {
        return dataDefinitionCausesTransactionCommit;
    }

    public void setDataDefinitionCausesTransactionCommit(
            final boolean theDataDefinitionCausesTransactionCommit) {
        this.dataDefinitionCausesTransactionCommit =
                theDataDefinitionCausesTransactionCommit;
    }

    public boolean isDataDefinitionIgnoredInTransactions() {
        return dataDefinitionIgnoredInTransactions;
    }

    public void setDataDefinitionIgnoredInTransactions(
            final boolean theDataDefinitionIgnoredInTransactions) {
        this.dataDefinitionIgnoredInTransactions =
                theDataDefinitionIgnoredInTransactions;
    }

    public boolean isSupportsResultSetType() {
        return supportsResultSetType;
    }

    public void setSupportsResultSetType(
            final boolean theSupportsResultSetType) {
        this.supportsResultSetType = theSupportsResultSetType;
    }

    public boolean isSupportsResultSetConcurrency() {
        return supportsResultSetConcurrency;
    }

    public void setSupportsResultSetConcurrency(
            final boolean theSupportsResultSetConcurrency) {
        this.supportsResultSetConcurrency = theSupportsResultSetConcurrency;
    }

    public boolean isOwnUpdatesAreVisible() {
        return ownUpdatesAreVisible;
    }

    public void setOwnUpdatesAreVisible(
            final boolean theOwnUpdatesAreVisible) {
        this.ownUpdatesAreVisible = theOwnUpdatesAreVisible;
    }

    public boolean isOwnDeletesAreVisible() {
        return ownDeletesAreVisible;
    }

    public void setOwnDeletesAreVisible(final boolean theOwnDeletesAreVisible) {
        this.ownDeletesAreVisible = theOwnDeletesAreVisible;
    }

    public boolean isOwnInsertsAreVisible() {
        return ownInsertsAreVisible;
    }

    public void setOwnInsertsAreVisible(final boolean theOwnInsertsAreVisible) {
        this.ownInsertsAreVisible = theOwnInsertsAreVisible;
    }

    public boolean isOthersUpdatesAreVisible() {
        return othersUpdatesAreVisible;
    }

    public void setOthersUpdatesAreVisible(
            final boolean theOthersUpdatesAreVisible) {
        this.othersUpdatesAreVisible = theOthersUpdatesAreVisible;
    }

    public boolean isOthersDeletesAreVisible() {
        return othersDeletesAreVisible;
    }

    public void setOthersDeletesAreVisible(
            final boolean theOthersDeletesAreVisible) {
        this.othersDeletesAreVisible = theOthersDeletesAreVisible;
    }

    public boolean isOthersInsertsAreVisible() {
        return othersInsertsAreVisible;
    }

    public void setOthersInsertsAreVisible(
            final boolean theOthersInsertsAreVisible) {
        this.othersInsertsAreVisible = theOthersInsertsAreVisible;
    }

    public boolean isUpdatesAreDetected() {
        return updatesAreDetected;
    }

    public void setUpdatesAreDetected(final boolean theUpdatesAreDetected) {
        this.updatesAreDetected = theUpdatesAreDetected;
    }

    public boolean isDeletesAreDetected() {
        return deletesAreDetected;
    }

    public void setDeletesAreDetected(final boolean theDeletesAreDetected) {
        this.deletesAreDetected = theDeletesAreDetected;
    }

    public boolean isInsertsAreDetected() {
        return insertsAreDetected;
    }

    public void setInsertsAreDetected(final boolean theInsertsAreDetected) {
        this.insertsAreDetected = theInsertsAreDetected;
    }

    public boolean isCatalogAtStart() {
        return isCatalogAtStart;
    }

    public void setCatalogAtStart(final boolean theCatalogAtStart) {
        isCatalogAtStart = theCatalogAtStart;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(final boolean readOnly) {
        isReadOnly = readOnly;
    }

    public boolean isLocatorsUpdateCopy() {
        return locatorsUpdateCopy;
    }

    public void setLocatorsUpdateCopy(final boolean finalLocatorsUpdateCopy) {
        this.locatorsUpdateCopy = finalLocatorsUpdateCopy;
    }

    public boolean isSupportsBatchUpdates() {
        return supportsBatchUpdates;
    }

    public void setSupportsBatchUpdates(
            final boolean theSupportsBatchUpdates) {
        this.supportsBatchUpdates = theSupportsBatchUpdates;
    }

    public boolean isSupportsSavepoints() {
        return supportsSavepoints;
    }

    public void setSupportsSavepoints(
            final boolean theSupportsSavepoints) {
        this.supportsSavepoints = theSupportsSavepoints;
    }

    public boolean isSupportsNamedParameters() {
        return supportsNamedParameters;
    }

    public void setSupportsNamedParameters(
            final boolean theSupportsNamedParameters) {
        this.supportsNamedParameters = theSupportsNamedParameters;
    }

    public boolean isSupportsMultipleOpenResults() {
        return supportsMultipleOpenResults;
    }

    public void setSupportsMultipleOpenResults(
            final boolean theSupportsMultipleOpenResults) {
        this.supportsMultipleOpenResults =
                theSupportsMultipleOpenResults;
    }

    public boolean isSupportsGetGeneratedKeys() {
        return supportsGetGeneratedKeys;
    }

    public void setSupportsGetGeneratedKeys(
            final boolean theSupportsGetGeneratedKeys) {
        this.supportsGetGeneratedKeys =
                theSupportsGetGeneratedKeys;
    }

    public boolean isSupportsResultSetHoldability() {
        return supportsResultSetHoldability;
    }

    public void setSupportsResultSetHoldability(
            final boolean theSupportsResultSetHoldability) {
        this.supportsResultSetHoldability =
                theSupportsResultSetHoldability;
    }

    public int getSqlStateType() {
        return sqlStateType;
    }

    public void setSqlStateType(final int theSqlStateType) {
        this.sqlStateType = theSqlStateType;
    }

    public boolean isSupportsStatementPooling() {
        return supportsStatementPooling;
    }

    public void setSupportsStatementPooling(
            final boolean theSupportsStatementPooling) {
        this.supportsStatementPooling = theSupportsStatementPooling;
    }

    public boolean isAllProceduresAreCallable() {
        return allProceduresAreCallable;
    }

    public void setAllProceduresAreCallable(
            final boolean theAllProceduresAreCallable) {
        this.allProceduresAreCallable = theAllProceduresAreCallable;
    }

    public boolean isAllTablesAreSelectable() {
        return allTablesAreSelectable;
    }

    public void setAllTablesAreSelectable(
            final boolean theAllTablesAreSelectable) {
        this.allTablesAreSelectable = theAllTablesAreSelectable;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String theUrl) {
        this.url = theUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String theUserName) {
        this.userName = theUserName;
    }

    public boolean isNullPlusNonNullIsNull() {
        return nullPlusNonNullIsNull;
    }

    public void setNullPlusNonNullIsNull(
            final boolean theNullPlusNonNullIsNull) {
        this.nullPlusNonNullIsNull = theNullPlusNonNullIsNull;
    }

    public boolean isNullsAreSortedHigh() {
        return nullsAreSortedHigh;
    }

    public void setNullsAreSortedHigh(final boolean theNullsAreSortedHigh) {
        this.nullsAreSortedHigh = theNullsAreSortedHigh;
    }

    public boolean isNullsAreSortedLow() {
        return nullsAreSortedLow;
    }

    public void setNullsAreSortedLow(
            final boolean theNullsAreSortedLow) {
        this.nullsAreSortedLow = theNullsAreSortedLow;
    }

    public boolean isNullsAreSortedAtStart() {
        return nullsAreSortedAtStart;
    }

    public void setNullsAreSortedAtStart(
            final boolean theNullsAreSortedAtStart) {
        this.nullsAreSortedAtStart = theNullsAreSortedAtStart;
    }

    public boolean isNullsAreSortedAtEnd() {
        return nullsAreSortedAtEnd;
    }

    public void setNullsAreSortedAtEnd(
            final boolean theNullsAreSortedAtEnd) {
        this.nullsAreSortedAtEnd = theNullsAreSortedAtEnd;
    }

    public boolean isAutoCommitFailureClosesAllResultSets() {
        return autoCommitFailureClosesAllResultSets;
    }

    public void setAutoCommitFailureClosesAllResultSets(
            final boolean
                    theAutoCommitFailureClosesAllResultSets) {
        this.autoCommitFailureClosesAllResultSets =
                theAutoCommitFailureClosesAllResultSets;
    }

    public boolean isGeneratedKeyAlwaysReturned() {
        return generatedKeyAlwaysReturned;
    }

    public void setGeneratedKeyAlwaysReturned(
            final boolean theGeneratedKeyAlwaysReturned) {
        this.generatedKeyAlwaysReturned =
                theGeneratedKeyAlwaysReturned;
    }

    public boolean isStoresLowerCaseIdentifiers() {
        return storesLowerCaseIdentifiers;
    }

    public void setStoresLowerCaseIdentifiers(
            final boolean theStoresLowerCaseIdentifiers) {
        this.storesLowerCaseIdentifiers =
                theStoresLowerCaseIdentifiers;
    }

    public boolean isStoresLowerCaseQuotedIdentifiers() {
        return storesLowerCaseQuotedIdentifiers;
    }

    public void setStoresLowerCaseQuotedIdentifiers(
            final boolean theStoresLowerCaseQuotedIdentifiers) {
        this.storesLowerCaseQuotedIdentifiers =
                theStoresLowerCaseQuotedIdentifiers;
    }

    public boolean isStoresMixedCaseIdentifiers() {
        return storesMixedCaseIdentifiers;
    }

    public void setStoresMixedCaseIdentifiers(
            final boolean theStoresMixedCaseIdentifiers) {
        this.storesMixedCaseIdentifiers = theStoresMixedCaseIdentifiers;
    }

    public boolean isStoresMixedCaseQuotedIdentifiers() {
        return storesMixedCaseQuotedIdentifiers;
    }

    public void setStoresMixedCaseQuotedIdentifiers(
            final boolean theStoresMixedCaseQuotedIdentifiers) {
        this.storesMixedCaseQuotedIdentifiers =
                theStoresMixedCaseQuotedIdentifiers;
    }

    public boolean isStoresUpperCaseIdentifiers() {
        return storesUpperCaseIdentifiers;
    }

    public void setStoresUpperCaseIdentifiers(
            final boolean theStoresUpperCaseIdentifiers) {
        this.storesUpperCaseIdentifiers = theStoresUpperCaseIdentifiers;
    }

    public boolean isStoresUpperCaseQuotedIdentifiers() {
        return storesUpperCaseQuotedIdentifiers;
    }

    public void setStoresUpperCaseQuotedIdentifiers(
            final boolean theStoresUpperCaseQuotedIdentifiers) {
        this.storesUpperCaseQuotedIdentifiers =
                theStoresUpperCaseQuotedIdentifiers;
    }

    public boolean isSupportsAlterTableWithAddColumn() {
        return supportsAlterTableWithAddColumn;
    }

    public void setSupportsAlterTableWithAddColumn(
            final boolean theSupportsAlterTableWithAddColumn) {
        this.supportsAlterTableWithAddColumn =
                theSupportsAlterTableWithAddColumn;
    }

    public boolean isSupportsAlterTableWithDropColumn() {
        return supportsAlterTableWithDropColumn;
    }

    public void setSupportsAlterTableWithDropColumn(
            final boolean theSupportsAlterTableWithDropColumn) {
        this.supportsAlterTableWithDropColumn =
                theSupportsAlterTableWithDropColumn;
    }

    public boolean isSupportsANSI92EntryLevelSQL() {
        return supportsANSI92EntryLevelSQL;
    }

    public void setSupportsANSI92EntryLevelSQL(
            final boolean theSupportsANSI92EntryLevelSQL) {
        this.supportsANSI92EntryLevelSQL =
                theSupportsANSI92EntryLevelSQL;
    }

    public boolean isSupportsANSI92FullSQL() {
        return supportsANSI92FullSQL;
    }

    public void setSupportsANSI92FullSQL(
            final boolean theSupportsANSI92FullSQL) {
        this.supportsANSI92FullSQL = theSupportsANSI92FullSQL;
    }

    public boolean isSupportsANSI92IntermediateSQL() {
        return supportsANSI92IntermediateSQL;
    }

    public void setSupportsANSI92IntermediateSQL(
            final boolean theSupportsANSI92IntermediateSQL) {
        this.supportsANSI92IntermediateSQL = theSupportsANSI92IntermediateSQL;
    }

    public boolean isSupportsCatalogsInDataManipulation() {
        return supportsCatalogsInDataManipulation;
    }

    public void setSupportsCatalogsInDataManipulation(
            final boolean theSupportsCatalogsInDataManipulation) {
        this.supportsCatalogsInDataManipulation =
                theSupportsCatalogsInDataManipulation;
    }

    public boolean isSupportsCatalogsInIndexDefinitions() {
        return supportsCatalogsInIndexDefinitions;
    }

    public void setSupportsCatalogsInIndexDefinitions(
            final boolean theSupportsCatalogsInIndexDefinitions) {
        this.supportsCatalogsInIndexDefinitions =
                theSupportsCatalogsInIndexDefinitions;
    }

    public boolean isSupportsCatalogsInPrivilegeDefinitions() {
        return supportsCatalogsInPrivilegeDefinitions;
    }

    public void setSupportsCatalogsInPrivilegeDefinitions(
            final boolean theSupportsCatalogsInPrivilegeDefinitions) {
        this.supportsCatalogsInPrivilegeDefinitions =
                theSupportsCatalogsInPrivilegeDefinitions;
    }

    public boolean isSupportsCatalogsInProcedureCalls() {
        return supportsCatalogsInProcedureCalls;
    }

    public void setSupportsCatalogsInProcedureCalls(
            final boolean theSupportsCatalogsInProcedureCalls) {
        this.supportsCatalogsInProcedureCalls =
                theSupportsCatalogsInProcedureCalls;
    }

    public boolean isSupportsCatalogsInTableDefinitions() {
        return supportsCatalogsInTableDefinitions;
    }

    public void setSupportsCatalogsInTableDefinitions(
            final boolean theSupportsCatalogsInTableDefinitions) {
        this.supportsCatalogsInTableDefinitions =
                theSupportsCatalogsInTableDefinitions;
    }

    public boolean isSupportsColumnAliasing() {
        return supportsColumnAliasing;
    }

    public void setSupportsColumnAliasing(
            final boolean theSupportsColumnAliasing) {
        this.supportsColumnAliasing = theSupportsColumnAliasing;
    }

    public boolean isSupportsConvert() {
        return supportsConvert;
    }

    public void setSupportsConvert(final boolean theSupportsConvert) {
        this.supportsConvert = theSupportsConvert;
    }

    public boolean isSupportsCoreSQLGrammar() {
        return supportsCoreSQLGrammar;
    }

    public void setSupportsCoreSQLGrammar(
            final boolean theSupportsCoreSQLGrammar) {
        this.supportsCoreSQLGrammar = theSupportsCoreSQLGrammar;
    }

    public boolean isSupportsCorrelatedSubqueries() {
        return supportsCorrelatedSubqueries;
    }

    public void setSupportsCorrelatedSubqueries(
            final boolean theSupportsCorrelatedSubqueries) {
        this.supportsCorrelatedSubqueries = theSupportsCorrelatedSubqueries;
    }

    public boolean isSupportsDataManipulationTransactionsOnly() {
        return supportsDataManipulationTransactionsOnly;
    }

    public void setSupportsDataManipulationTransactionsOnly(
            final boolean theSupportsDataManipulationTransactionsOnly) {
        this.supportsDataManipulationTransactionsOnly =
                theSupportsDataManipulationTransactionsOnly;
    }

    public boolean isSupportsDifferentTableCorrelationNames() {
        return supportsDifferentTableCorrelationNames;
    }

    public void setSupportsDifferentTableCorrelationNames(
            final boolean theSupportsDifferentTableCorrelationNames) {
        this.supportsDifferentTableCorrelationNames =
                theSupportsDifferentTableCorrelationNames;
    }

    public boolean isSupportsExpressionsInOrderBy() {
        return supportsExpressionsInOrderBy;
    }

    public void setSupportsExpressionsInOrderBy(
            final boolean theSupportsExpressionsInOrderBy) {
        this.supportsExpressionsInOrderBy =
                theSupportsExpressionsInOrderBy;
    }

    public boolean isSupportsExtendedSQLGrammar() {
        return supportsExtendedSQLGrammar;
    }

    public void setSupportsExtendedSQLGrammar(
            final boolean theSupportsExtendedSQLGrammar) {
        this.supportsExtendedSQLGrammar = theSupportsExtendedSQLGrammar;
    }

    public boolean isSupportsFullOuterJoins() {
        return supportsFullOuterJoins;
    }

    public void setSupportsFullOuterJoins(
            final boolean theSupportsFullOuterJoins) {
        this.supportsFullOuterJoins = theSupportsFullOuterJoins;
    }

    public boolean isSupportsGroupBy() {
        return supportsGroupBy;
    }

    public void setSupportsGroupBy(
            final boolean theSupportsGroupBy) {
        this.supportsGroupBy = theSupportsGroupBy;
    }

    public boolean isSupportsGroupByBeyondSelect() {
        return supportsGroupByBeyondSelect;
    }

    public void setSupportsGroupByBeyondSelect(
            final boolean theSupportsGroupByBeyondSelect) {
        this.supportsGroupByBeyondSelect = theSupportsGroupByBeyondSelect;
    }

    public boolean isSupportsGroupByUnrelated() {
        return supportsGroupByUnrelated;
    }

    public void setSupportsGroupByUnrelated(
            final boolean theSupportsGroupByUnrelated) {
        this.supportsGroupByUnrelated = theSupportsGroupByUnrelated;
    }

    public boolean isSupportsIntegrityEnhancementFacility() {
        return supportsIntegrityEnhancementFacility;
    }

    public void setSupportsIntegrityEnhancementFacility(
            final boolean theSupportsIntegrityEnhancementFacility) {
        this.supportsIntegrityEnhancementFacility =
                theSupportsIntegrityEnhancementFacility;
    }

    public boolean isSupportsLikeEscapeClause() {
        return supportsLikeEscapeClause;
    }

    public void setSupportsLikeEscapeClause(
            final boolean theSupportsLikeEscapeClause) {
        this.supportsLikeEscapeClause = theSupportsLikeEscapeClause;
    }

    public boolean isSupportsLimitedOuterJoins() {
        return supportsLimitedOuterJoins;
    }

    public void setSupportsLimitedOuterJoins(
            final boolean theSupportsLimitedOuterJoins) {
        this.supportsLimitedOuterJoins = theSupportsLimitedOuterJoins;
    }

    public boolean isSupportsMinimumSQLGrammar() {
        return supportsMinimumSQLGrammar;
    }

    public void setSupportsMinimumSQLGrammar(
            final boolean theSupportsMinimumSQLGrammar) {
        this.supportsMinimumSQLGrammar = theSupportsMinimumSQLGrammar;
    }

    public boolean isSupportsMixedCaseIdentifiers() {
        return supportsMixedCaseIdentifiers;
    }

    public void setSupportsMixedCaseIdentifiers(
            final boolean theSupportsMixedCaseIdentifiers) {
        this.supportsMixedCaseIdentifiers =
                theSupportsMixedCaseIdentifiers;
    }

    public boolean isSupportsMixedCaseQuoteIdentifiers() {
        return supportsMixedCaseQuoteIdentifiers;
    }

    public void setSupportsMixedCaseQuoteIdentifiers(
            final boolean theSupportsMixedCaseQuoteIdentifiers) {
        this.supportsMixedCaseQuoteIdentifiers =
                theSupportsMixedCaseQuoteIdentifiers;
    }

    public boolean isSupportsMultipleResultSets() {
        return supportsMultipleResultSets;
    }

    public void setSupportsMultipleResultSets(
            final boolean theSupportsMultipleResultSets) {
        this.supportsMultipleResultSets = theSupportsMultipleResultSets;
    }

    public boolean isSupportsMultipleTransactions() {
        return supportsMultipleTransactions;
    }

    public void setSupportsMultipleTransactions(
            final boolean theSupportsMultipleTransactions) {
        this.supportsMultipleTransactions = theSupportsMultipleTransactions;
    }

    public boolean isSupportsNonNullableColumns() {
        return supportsNonNullableColumns;
    }

    public void setSupportsNonNullableColumns(
            final boolean theSupportsNonNullableColumns) {
        this.supportsNonNullableColumns =
                theSupportsNonNullableColumns;
    }

    public boolean isSupportsOpenCursorsAcrossCommit() {
        return supportsOpenCursorsAcrossCommit;
    }

    public void setSupportsOpenCursorsAcrossCommit(
            final boolean theSupportsOpenCursorsAcrossCommit) {
        this.supportsOpenCursorsAcrossCommit =
                theSupportsOpenCursorsAcrossCommit;
    }

    public boolean isSupportsOpenCursorsAcrossRollback() {
        return supportsOpenCursorsAcrossRollback;
    }

    public void setSupportsOpenCursorsAcrossRollback(
            final boolean theSupportsOpenCursorsAcrossRollback) {
        this.supportsOpenCursorsAcrossRollback =
                theSupportsOpenCursorsAcrossRollback;
    }

    public boolean isSupportsOrderByUnrelated() {
        return supportsOrderByUnrelated;
    }

    public void setSupportsOrderByUnrelated(
            final boolean theSupportsOrderByUnrelated) {
        this.supportsOrderByUnrelated = theSupportsOrderByUnrelated;
    }

    public boolean isSupportsOuterJoins() {
        return supportsOuterJoins;
    }

    public void setSupportsOuterJoins(
            final boolean theSupportsOuterJoins) {
        this.supportsOuterJoins = theSupportsOuterJoins;
    }

    public boolean isSupportsPositionedDelete() {
        return supportsPositionedDelete;
    }

    public void setSupportsPositionedDelete(
            final boolean theSupportsPositionedDelete) {
        this.supportsPositionedDelete = theSupportsPositionedDelete;
    }

    public boolean isSupportsPositionedUpdate() {
        return supportsPositionedUpdate;
    }

    public void setSupportsPositionedUpdate(
            final boolean theSupportsPositionedUpdate) {
        this.supportsPositionedUpdate = theSupportsPositionedUpdate;
    }

    public boolean isSupportsRefCursors() {
        return supportsRefCursors;
    }

    public void setSupportsRefCursors(
            final boolean theSupportsRefCursors) {
        this.supportsRefCursors = theSupportsRefCursors;
    }

    public boolean isSupportsSavePoint() {
        return supportsSavePoint;
    }

    public void setSupportsSavePoint(
            final boolean theSupportsSavePoint) {
        this.supportsSavePoint = theSupportsSavePoint;
    }

    public boolean isSupportsSchemaInDataManipulation() {
        return supportsSchemaInDataManipulation;
    }

    public void setSupportsSchemaInDataManipulation(
            final boolean theSupportsSchemaInDataManipulation) {
        this.supportsSchemaInDataManipulation =
                theSupportsSchemaInDataManipulation;
    }

    public boolean isSupportsSchemaInIndexDefinition() {
        return supportsSchemaInIndexDefinition;
    }

    public void setSupportsSchemaInIndexDefinition(
            final boolean theSupportsSchemaInIndexDefinition) {
        this.supportsSchemaInIndexDefinition =
                theSupportsSchemaInIndexDefinition;
    }

    public boolean isSupportsSchemasInPrivilegeDefinitions() {
        return supportsSchemasInPrivilegeDefinitions;
    }

    public void setSupportsSchemasInPrivilegeDefinitions(
            final boolean theSupportsSchemasInPrivilegeDefinitions) {
        this.supportsSchemasInPrivilegeDefinitions =
                theSupportsSchemasInPrivilegeDefinitions;
    }

    public boolean isSupportsSchemasInProcedureCalls() {
        return supportsSchemasInProcedureCalls;
    }

    public void setSupportsSchemasInProcedureCalls(
            final boolean theSupportsSchemasInProcedureCalls) {
        this.supportsSchemasInProcedureCalls =
                theSupportsSchemasInProcedureCalls;
    }

    public boolean isSupportsSchemasInTableDefinitions() {
        return supportsSchemasInTableDefinitions;
    }

    public void setSupportsSchemasInTableDefinitions(
            final boolean theSupportsSchemasInTableDefinitions) {
        this.supportsSchemasInTableDefinitions =
                theSupportsSchemasInTableDefinitions;
    }

    public boolean isSupportsSelectForUpdate() {
        return supportsSelectForUpdate;
    }

    public void setSupportsSelectForUpdate(
            final boolean theSupportsSelectForUpdate) {
        this.supportsSelectForUpdate = theSupportsSelectForUpdate;
    }

    public boolean isSupportsSharding() {
        return supportsSharding;
    }

    public void setSupportsSharding(
            final boolean theSupportsSharding) {
        this.supportsSharding = theSupportsSharding;
    }

    public boolean isSupportsStoredFunctionsUsingCallSyntax() {
        return supportsStoredFunctionsUsingCallSyntax;
    }

    public void setSupportsStoredFunctionsUsingCallSyntax(
            final boolean theSupportsStoredFunctionsUsingCallSyntax) {
        this.supportsStoredFunctionsUsingCallSyntax =
                theSupportsStoredFunctionsUsingCallSyntax;
    }

    public boolean isSupportsStoredProcedures() {
        return supportsStoredProcedures;
    }

    public void setSupportsStoredProcedures(
            final boolean theSupportsStoredProcedures) {
        this.supportsStoredProcedures =
                theSupportsStoredProcedures;
    }

    public boolean isSupportsSubqueriesInComparisons() {
        return supportsSubqueriesInComparisons;
    }

    public void setSupportsSubqueriesInComparisons(
            final boolean theSupportsSubqueriesInComparisons) {
        this.supportsSubqueriesInComparisons =
                theSupportsSubqueriesInComparisons;
    }

    public boolean isSupportsSubqueriesInExists() {
        return supportsSubqueriesInExists;
    }

    public void setSupportsSubqueriesInExists(
            final boolean theSupportsSubqueriesInExists) {
        this.supportsSubqueriesInExists = theSupportsSubqueriesInExists;
    }

    public boolean isSupportsSubqueriesInIns() {
        return supportsSubqueriesInIns;
    }

    public void setSupportsSubqueriesInIns(
            final boolean theSupportsSubqueriesInIns) {
        this.supportsSubqueriesInIns = theSupportsSubqueriesInIns;
    }

    public boolean isSupportsSubqueriesInQuantifieds() {
        return supportsSubqueriesInQuantifieds;
    }

    public void setSupportsSubqueriesInQuantifieds(
            final boolean theSupportsSubqueriesInQuantifieds) {
        this.supportsSubqueriesInQuantifieds =
                theSupportsSubqueriesInQuantifieds;
    }

    public boolean isSupportsTableCorrelationNames() {
        return supportsTableCorrelationNames;
    }

    public void setSupportsTableCorrelationNames(
            final boolean theSupportsTableCorrelationNames) {
        this.supportsTableCorrelationNames =
                theSupportsTableCorrelationNames;
    }

    public boolean isSupportsUnion() {
        return supportsUnion;
    }

    public void setSupportsUnion(final boolean theSupportsUnion) {
        this.supportsUnion = theSupportsUnion;
    }

    public boolean isSupportsUnionAll() {
        return supportsUnionAll;
    }

    public void setSupportsUnionAll(
            final boolean theSupportsUnionAll) {
        this.supportsUnionAll = theSupportsUnionAll;
    }

    public boolean isUsersLocalFilePerTable() {
        return usersLocalFilePerTable;
    }

    public void setUsersLocalFilePerTable(
            final boolean theUsersLocalFilePerTable) {
        this.usersLocalFilePerTable = theUsersLocalFilePerTable;
    }

    public boolean isUsersLocalFiles() {
        return usersLocalFiles;
    }

    public void setUsersLocalFiles(
            final boolean theUsersLocalFiles) {
        this.usersLocalFiles = theUsersLocalFiles;
    }
}
