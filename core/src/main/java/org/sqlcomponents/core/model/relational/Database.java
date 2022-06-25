package org.sqlcomponents.core.model.relational;


import lombok.Getter;
import lombok.Setter;
import org.sqlcomponents.core.model.relational.enums.DBType;
import org.sqlcomponents.core.model.relational.enums.TableType;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@Getter
@Setter
public class Database
{
    private List<Table> tables;
    private List<Procedure> functions;
    private List<Package> packages;
    private List<String> sequences;


    private Set<TableType> tableTypes;

    private String catalogTerm;
    private String catalogSeperator;
    // private String databaseProductName;
    private DBType dbType;
    private int databaseMajorVersion;
    private int databaseMinorVersion;
    private String databaseProductVersion;
    private int defaultTransactionIsolation;
    private int driverMajorVersion;
    private int driverMinorVersion;
    private String driverName;
    private String driverVersion;
    private String extraNameCharacters;
    private String identifierQuoteString;
    private int jdbcMajorVersion;
    private int jdbcMinorVersion;
    private int maxBinaryLiteralLength;
    private int maxCatalogNameLength;
    private int maxCharLiteralLength;
    private int maxColumnNameLength;
    private int maxColumnsInGroupBy;
    private int maxColumnsInIndex;
    private int maxColumnsInOrderBy;
    private int maxColumnsInSelect;
    private int maxColumnsInTable;
    private int maxConnections;
    private int maxCursorNameLength;
    private int maxIndexLength;
    //private long maxLogicalLobSize;
    private int maxSchemaNameLength;
    private int maxProcedureNameLength;
    private int maxRowSize;
    private boolean doesMaxRowSizeIncludeBlobs;
    private int maxStatementLength;
    private int maxStatements;
    private int maxTableNameLength;
    private int maxTablesInSelect;
    private int maxUserNameLength;
    private Set<String> numericFunctions;
    private String procedureTerm;
    private int resultSetHoldability;
    //private rowidlifetime rowIdLifeTime;
    private String schemaTerm;
    private String searchStringEscape;
    private SortedSet<String> sqlKeywords;
    private SortedSet<String> stringFunctions;
    private SortedSet<String> systemFunctions;
    private SortedSet<String> timeDateFunctions;
    private boolean supportsTransactions;
    private boolean supportsTransactionIsolationLevel;
    private boolean supportsDataDefinitionAndDataManipulationTransactions;
    private boolean dataDefinitionCausesTransactionCommit;
    private boolean dataDefinitionIgnoredInTransactions;
    private boolean supportsResultSetType;
    private boolean supportsResultSetConcurrency;
    private boolean ownUpdatesAreVisible;
    private boolean ownDeletesAreVisible;
    private boolean ownInsertsAreVisible;
    private boolean othersUpdatesAreVisible;
    private boolean othersDeletesAreVisible;
    private boolean othersInsertsAreVisible;
    private boolean updatesAreDetected;
    private boolean deletesAreDetected;
    private boolean insertsAreDetected;
    private boolean isCatalogAtStart;
    private boolean isReadOnly;
    private boolean locatorsUpdateCopy;
    private boolean supportsBatchUpdates;
    // private Connection getConnection;
    private boolean supportsSavepoints;
    private boolean supportsNamedParameters;
    private boolean supportsMultipleOpenResults;
    private boolean supportsGetGeneratedKeys;
    private boolean supportsResultSetHoldability;
    private int sqlStateType;
    private boolean supportsStatementPooling;
    private boolean allProceduresAreCallable;
    private boolean allTablesAreSelectable;
    private String url;
    private String userName;
    private boolean nullPlusNonNullIsNull;
    private boolean nullsAreSortedHigh;
    private boolean nullsAreSortedLow;
    private boolean nullsAreSortedAtStart;
    private boolean nullsAreSortedAtEnd;
    private boolean autoCommitFailureClosesAllResultSets;
    private boolean generatedKeyAlwaysReturned;
    private boolean storesLowerCaseIdentifiers;
    private boolean storesLowerCaseQuotedIdentifiers;
    private boolean storesMixedCaseIdentifiers;
    private boolean storesMixedCaseQuotedIdentifiers;
    private boolean storesUpperCaseIdentifiers;
    private boolean storesUpperCaseQuotedIdentifiers;
    private boolean supportsAlterTableWithAddColumn;
    private boolean supportsAlterTableWithDropColumn;
    private boolean supportsANSI92EntryLevelSQL;
    private boolean supportsANSI92FullSQL;
    private boolean supportsANSI92IntermediateSQL;
    private boolean supportsCatalogsInDataManipulation;
    private boolean supportsCatalogsInIndexDefinitions;
    private boolean supportsCatalogsInPrivilegeDefinitions;
    private boolean supportsCatalogsInProcedureCalls;
    private boolean supportsCatalogsInTableDefinitions;
    private boolean supportsColumnAliasing;
    private boolean supportsConvert;
    private boolean supportsCoreSQLGrammar;
    private boolean supportsCorrelatedSubqueries;
    private boolean supportsDataManipulationTransactionsOnly;
    private boolean supportsDifferentTableCorrelationNames;
    private boolean supportsExpressionsInOrderBy;
    private boolean supportsExtendedSQLGrammar;
    private boolean supportsFullOuterJoins;
    private boolean supportsGroupBy;
    private boolean supportsGroupByBeyondSelect;
    private boolean supportsGroupByUnrelated;
    private boolean supportsIntegrityEnhancementFacility;
    private boolean supportsLikeEscapeClause;
    private boolean supportsLimitedOuterJoins;
    private boolean supportsMinimumSQLGrammar;
    private boolean supportsMixedCaseIdentifiers;
    private boolean supportsMixedCaseQuoteIdentifiers;
    private boolean supportsMultipleResultSets;
    private boolean supportsMultipleTransactions;
    private boolean supportsNonNullableColumns;
    private boolean supportsOpenCursorsAcrossCommit;
    private boolean supportsOpenCursorsAcrossRollback;
    private boolean supportsOrderByUnrelated;
    private boolean supportsOuterJoins;
    private boolean supportsPositionedDelete;
    private boolean supportsPositionedUpdate;
    private boolean supportsRefCursors;
    private boolean supportsSavePoint;
    private boolean supportsSchemaInDataManipulation;
    private boolean supportsSchemaInIndexDefinition;
    private boolean supportsSchemasInPrivilegeDefinitions;
    private boolean supportsSchemasInProcedureCalls;
    private boolean supportsSchemasInTableDefinitions;
    private boolean supportsSelectForUpdate;
    private boolean supportsSharding;
    private boolean supportsStoredFunctionsUsingCallSyntax;
    private boolean supportsStoredProcedures;
    private boolean supportsSubqueriesInComparisons;
    private boolean supportsSubqueriesInExists;
    private boolean supportsSubqueriesInIns;
    private boolean supportsSubqueriesInQuantifieds;
    private boolean supportsTableCorrelationNames;
    private boolean supportsUnion;
    private boolean supportsUnionAll;
    private boolean usersLocalFilePerTable;
    private boolean usersLocalFiles;


    /**
     * Used to get Escaped Name for tableName or columnName
     *
     * @param name
     * @return escapedName
     */
    public String escapedName(final String name)
    {
	Boolean shouldEscape = this.getSqlKeywords().stream()
		.filter(keyword -> keyword.equalsIgnoreCase(name))
		.findFirst().isPresent();
	return shouldEscape ? this.identifierQuoteString + name + this.identifierQuoteString : name;
    }

    public SortedSet<String> getDistinctColumnTypeNames()
    {
	SortedSet<String> distinctColumnTypeNames = new TreeSet<>();
	this.tables.stream().forEach(table -> {
	    distinctColumnTypeNames.addAll(table.getDistinctColumnTypeNames());
	});

	return distinctColumnTypeNames;
    }

    public SortedSet<String> getDistinctCustomColumnTypeNames()
    {
	SortedSet<String> distinctColumnTypeNames = new TreeSet<>();
	this.tables.stream().forEach(table -> {
	    distinctColumnTypeNames.addAll(table.getDistinctCustomColumnTypeNames());
	});

	return distinctColumnTypeNames;
    }
}
