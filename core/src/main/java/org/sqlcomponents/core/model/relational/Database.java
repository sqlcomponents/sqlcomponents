package org.sqlcomponents.core.model.relational;

import lombok.Getter;
import lombok.Setter;
import org.sqlcomponents.core.model.relational.enums.DBType;
import org.sqlcomponents.core.model.relational.enums.TableType;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The type representing a database.
 */
@Getter
@Setter
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
}
