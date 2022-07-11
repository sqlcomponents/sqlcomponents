package org.sqlcomponents.core.crawler;

import org.sqlcomponents.core.crawler.util.CrawlerConsts;
import org.sqlcomponents.core.crawler.util.DataSourceUtil;
import org.sqlcomponents.core.exception.SQLComponentsException;
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

public final class Crawler
{
    private final Database database = new Database();

    public Crawler()
    {
    }

    public Database getDatabase(final Application aApplication)
	    throws SQLComponentsException
    {
	DataSource lDataSource = DataSourceUtil.getDataSource(
		aApplication.getUrl(),
		aApplication.getUserName(),
		aApplication.getPassword(),
		aApplication.getSchemaName());
	try (Connection lConnection = lDataSource.getConnection())
	{
	    DatabaseMetaData lDatabaseMetaData = lConnection.getMetaData();
	    database.setCatalogTerm(lDatabaseMetaData.getCatalogTerm());
	    database.setCatalogSeperator(lDatabaseMetaData.getCatalogSeparator());

	    switch (lDatabaseMetaData.getDatabaseProductName().toLowerCase().trim())
	    {
		case CrawlerConsts.POSTGRES_DB:
		{
		    database.setDbType(DBType.POSTGRES);
		    break;
		}
		case CrawlerConsts.MYSQL_DB:
		{
		    database.setDbType(DBType.MYSQL);
		    break;
		}
		case CrawlerConsts.MARIA_DB:
		{
		    database.setDbType(DBType.MARIADB);
		}
	    }

	    database.setDatabaseMajorVersion(lDatabaseMetaData.getDatabaseMajorVersion());
	    database.setDatabaseMinorVersion(lDatabaseMetaData.getDatabaseMinorVersion());
	    database.setDatabaseProductVersion(lDatabaseMetaData.getDatabaseProductVersion());
	    database.setDefaultTransactionIsolation(lDatabaseMetaData.getDefaultTransactionIsolation());
	    database.setDatabaseMajorVersion(lDatabaseMetaData.getDatabaseMajorVersion());
	    database.setDatabaseMinorVersion(lDatabaseMetaData.getDatabaseMinorVersion());
	    database.setDriverName(lDatabaseMetaData.getDriverName());
	    database.setDriverVersion(lDatabaseMetaData.getDriverVersion());
	    database.setExtraNameCharacters(lDatabaseMetaData.getExtraNameCharacters());
	    database.setIdentifierQuoteString(lDatabaseMetaData.getIdentifierQuoteString());
	    database.setJdbcMajorVersion(lDatabaseMetaData.getJDBCMajorVersion());
	    database.setJdbcMinorVersion(lDatabaseMetaData.getJDBCMinorVersion());
	    database.setMaxBinaryLiteralLength(lDatabaseMetaData.getMaxBinaryLiteralLength());
	    database.setMaxCharLiteralLength(lDatabaseMetaData.getMaxCharLiteralLength());
	    database.setMaxCatalogNameLength(lDatabaseMetaData.getMaxCatalogNameLength());
	    database.setMaxColumnNameLength(lDatabaseMetaData.getMaxColumnNameLength());
	    database.setMaxColumnsInGroupBy(lDatabaseMetaData.getMaxColumnsInGroupBy());
	    database.setMaxColumnsInIndex(lDatabaseMetaData.getMaxColumnsInIndex());
	    database.setMaxColumnsInOrderBy(lDatabaseMetaData.getMaxColumnsInOrderBy());
	    database.setMaxColumnsInSelect(lDatabaseMetaData.getMaxColumnsInSelect());
	    database.setMaxColumnsInTable(lDatabaseMetaData.getMaxColumnsInTable());
	    database.setMaxConnections(lDatabaseMetaData.getMaxConnections());
	    database.setMaxCursorNameLength(lDatabaseMetaData.getMaxCursorNameLength());
	    database.setMaxIndexLength(lDatabaseMetaData.getMaxIndexLength());
	    database.setMaxSchemaNameLength(lDatabaseMetaData.getMaxSchemaNameLength());
	    database.setMaxProcedureNameLength(lDatabaseMetaData.getMaxProcedureNameLength());
	    database.setMaxRowSize(lDatabaseMetaData.getMaxRowSize());
	    database.setDoesMaxRowSizeIncludeBlobs(lDatabaseMetaData.doesMaxRowSizeIncludeBlobs());
	    database.setMaxStatementLength(lDatabaseMetaData.getMaxStatementLength());
	    database.setMaxStatements(lDatabaseMetaData.getMaxStatements());
	    database.setMaxTableNameLength(lDatabaseMetaData.getMaxTableNameLength());
	    database.setMaxTablesInSelect(lDatabaseMetaData.getMaxTablesInSelect());
	    database.setMaxUserNameLength(lDatabaseMetaData.getMaxUserNameLength());
	    database.setNumericFunctions(
		    new HashSet<>(
			    Arrays.asList(lDatabaseMetaData.getNumericFunctions().split(CrawlerConsts.CAMA_STR))));
	    database.setProcedureTerm(lDatabaseMetaData.getProcedureTerm());
	    database.setResultSetHoldability(lDatabaseMetaData.getResultSetHoldability());
	    database.setSchemaTerm(lDatabaseMetaData.getSchemaTerm());
	    database.setSearchStringEscape(lDatabaseMetaData.getSearchStringEscape());
	    database.setSqlKeywords(new TreeSet<>(Arrays.asList(lDatabaseMetaData.getSQLKeywords().split(
		    CrawlerConsts.CAMA_STR))));
	    database.setStringFunctions(
		    new TreeSet<>(Arrays.asList(lDatabaseMetaData.getStringFunctions().split(CrawlerConsts.CAMA_STR))));
	    database.setSystemFunctions(
		    new TreeSet<>(Arrays.asList(lDatabaseMetaData.getSystemFunctions().split(CrawlerConsts.CAMA_STR))));
	    database.setTimeDateFunctions(
		    new TreeSet<>(Arrays.asList(lDatabaseMetaData.getTimeDateFunctions().split(
			    CrawlerConsts.CAMA_STR))));
	    database.setSupportsTransactions(lDatabaseMetaData.supportsTransactions());
	    database.setSupportsDataDefinitionAndDataManipulationTransactions(
		    lDatabaseMetaData.supportsDataDefinitionAndDataManipulationTransactions());
	    database.setDataDefinitionCausesTransactionCommit(
		    lDatabaseMetaData.dataDefinitionCausesTransactionCommit());
	    database.setDataDefinitionIgnoredInTransactions(lDatabaseMetaData.dataDefinitionIgnoredInTransactions());
	    //			lDatabase.setSupportsResultSetType(lDatabaseMetaData.supportsResultSetType());
	    //			lDatabase.setSupportsResultSetConcurrency(lDatabaseMetaData.supportsResultSetConcurrency());
	    //			lDatabase.setOwnUpdatesAreVisible(lDatabaseMetaData.ownUpdatesAreVisible());
	    //			lDatabase.setOwnDeletesAreVisible(lDatabaseMetaData.ownDeletesAreVisible());
	    //			lDatabase.setOwnInsertsAreVisible(lDatabaseMetaData.ownInsertsAreVisible());
	    //			lDatabase.setOthersUpdatesAreVisible(lDatabaseMetaData.othersUpdatesAreVisible());
	    //			lDatabase.setOthersDeletesAreVisible(lDatabaseMetaData.othersDeletesAreVisible());
	    //			lDatabase.setOthersInsertsAreVisible(lDatabaseMetaData.othersInsertsAreVisible());
	    //			lDatabase.setUpdatesAreDetected(lDatabaseMetaData.updatesAreDetected());
	    //			lDatabase.setDeletesAreDetected(lDatabaseMetaData.deletesAreDetected());
	    //			lDatabase.setInsertsAreDetected(lDatabaseMetaData.insertsAreDetected());
	    // 			lDatabase.setSupportsResultSetHoldability(lDatabaseMetaData.supportsResultSetHoldability());
	    //			lDatabase.setSupportsTransactionIsolationLevel(lDatabaseMetaData.supportsTransactionIsolationLevel());
	    database.setCatalogAtStart(lDatabaseMetaData.isCatalogAtStart());
	    database.setReadOnly(lDatabaseMetaData.isReadOnly());
	    database.setLocatorsUpdateCopy(lDatabaseMetaData.locatorsUpdateCopy());
	    database.setSupportsBatchUpdates(lDatabaseMetaData.supportsBatchUpdates());
	    database.setSupportsSavePoint(lDatabaseMetaData.supportsAlterTableWithAddColumn());
	    database.setSupportsNamedParameters(lDatabaseMetaData.supportsNamedParameters());
	    database.setSupportsMultipleOpenResults(lDatabaseMetaData.supportsMultipleOpenResults());
	    database.setSupportsGetGeneratedKeys(lDatabaseMetaData.supportsGetGeneratedKeys());
	    database.setSqlStateType(lDatabaseMetaData.getSQLStateType());
	    database.setSupportsStatementPooling(lDatabaseMetaData.supportsStatementPooling());
	    database.setAllProceduresAreCallable(lDatabaseMetaData.allProceduresAreCallable());
	    database.setAllTablesAreSelectable(lDatabaseMetaData.allTablesAreSelectable());
	    database.setUrl(lDatabaseMetaData.getURL());
	    database.setUserName(lDatabaseMetaData.getUserName());
	    database.setNullPlusNonNullIsNull(lDatabaseMetaData.nullPlusNonNullIsNull());
	    database.setNullsAreSortedHigh(lDatabaseMetaData.nullsAreSortedHigh());
	    database.setNullsAreSortedLow(lDatabaseMetaData.nullsAreSortedLow());
	    database.setNullsAreSortedAtStart(lDatabaseMetaData.nullsAreSortedAtStart());
	    database.setNullsAreSortedAtEnd(lDatabaseMetaData.nullsAreSortedAtEnd());
	    database.setAutoCommitFailureClosesAllResultSets(lDatabaseMetaData.autoCommitFailureClosesAllResultSets());
	    database.setGeneratedKeyAlwaysReturned(lDatabaseMetaData.generatedKeyAlwaysReturned());
	    database.setStoresLowerCaseIdentifiers(lDatabaseMetaData.storesLowerCaseIdentifiers());
	    database.setStoresLowerCaseQuotedIdentifiers(lDatabaseMetaData.storesLowerCaseQuotedIdentifiers());
	    database.setStoresMixedCaseIdentifiers(lDatabaseMetaData.storesMixedCaseIdentifiers());
	    database.setStoresMixedCaseQuotedIdentifiers(lDatabaseMetaData.storesMixedCaseQuotedIdentifiers());
	    database.setStoresUpperCaseIdentifiers(lDatabaseMetaData.storesUpperCaseIdentifiers());
	    database.setStoresUpperCaseQuotedIdentifiers(lDatabaseMetaData.storesUpperCaseQuotedIdentifiers());
	    database.setSupportsAlterTableWithAddColumn(lDatabaseMetaData.supportsAlterTableWithAddColumn());
	    database.setSupportsAlterTableWithDropColumn(lDatabaseMetaData.supportsAlterTableWithDropColumn());
	    database.setSupportsANSI92EntryLevelSQL(lDatabaseMetaData.supportsANSI92EntryLevelSQL());
	    database.setSupportsANSI92FullSQL(lDatabaseMetaData.supportsANSI92FullSQL());
	    database.setSupportsANSI92IntermediateSQL(lDatabaseMetaData.supportsANSI92IntermediateSQL());
	    database.setSupportsCatalogsInDataManipulation(lDatabaseMetaData.supportsCatalogsInDataManipulation());
	    database.setSupportsCatalogsInIndexDefinitions(lDatabaseMetaData.supportsCatalogsInIndexDefinitions());
	    database.setSupportsCatalogsInPrivilegeDefinitions(
		    lDatabaseMetaData.supportsCatalogsInPrivilegeDefinitions());
	    database.setSupportsCatalogsInProcedureCalls(lDatabaseMetaData.supportsCatalogsInProcedureCalls());
	    database.setSupportsCatalogsInTableDefinitions(lDatabaseMetaData.supportsCatalogsInTableDefinitions());
	    database.setSupportsColumnAliasing(lDatabaseMetaData.supportsColumnAliasing());
	    database.setTableTypes(getTableTypes(lDatabaseMetaData));
	    database.setSequences(getSequences(lDatabaseMetaData));
	    database.setTables(getTables(lDatabaseMetaData, database, aApplication.getSchemaName(),
					 tableName -> matches(aApplication.getTablePatterns(), tableName)));
	    // lDatabase.setFunctions(getProcedures(lDatabaseMetaData));
	    repair(database, lDatabaseMetaData);
	}
	catch (final Exception aException)
	{
	    throw new SQLComponentsException(aException);
	}
	return database;
    }

    private boolean matches(final List<String> aPatterns, final String aValue)
    {
	boolean matches = aPatterns == null || aPatterns.isEmpty();
	if (!matches)
	{

	    for (String pattern :
		    aPatterns)
	    {
		matches = Pattern.matches(pattern, aValue);
		if (matches)
		{
		    break;
		}
	    }

	}
	return matches;
    }

    private Set<TableType> getTableTypes(final DatabaseMetaData aDatabaseMetaData) throws SQLException
    {
	Set<TableType> tableTypes = new TreeSet<>();
	ResultSet resultset = aDatabaseMetaData.getTableTypes();

	while (resultset.next())
	{
	    String s = resultset.getString("TABLE_TYPE");
	    TableType lTableType = TableType.value(s);
	    assert (lTableType != null) : "TableType can't be null for '" + s + "', Check if all tables are created in Database";
	    tableTypes.add(lTableType);
	}

	return tableTypes;
    }


    private List<String> getSequences(final DatabaseMetaData aDatabaseMetaData) throws SQLException
    {
	List<String> sequences = new ArrayList<>();
	ResultSet resultset = aDatabaseMetaData.getTables(null, null,
							  null, new String[]{"SEQUENCE"});

	while (resultset.next())
	{
	    sequences.add(resultset.getString("table_name"));
	}

	return sequences;
    }

    private List<Table> getTables(final DatabaseMetaData aDatabaseMetaData,
				  final Database aDatabase,
				  final String aSchemeName,
				  final Predicate<String> aTableFilter) throws SQLException
    {
	List<Table> lTables = new ArrayList<>();

	String lSchemaNamePattern = (aDatabase.getDbType() == DBType.MYSQL) ? aSchemeName : null;
	String lCatalog = aDatabase.getDbType() == DBType.MYSQL ? aSchemeName : null;

	ResultSet lResultSet = aDatabaseMetaData.getTables(lCatalog, lSchemaNamePattern, null,
							   new String[]{"TABLE"});
	while (lResultSet.next())
	{
	    final String tableName = lResultSet.getString("table_name");
	    System.out.println(tableName);
	    if (aTableFilter.test(tableName))
	    {
		Table bTable = new Table(aDatabase);
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

		bTable.setColumns(getColumns(aDatabaseMetaData, bTable));
		bTable.setIndices(getIndices(aDatabaseMetaData, bTable));
		bTable.setUniqueColumns(getUniqueConstraints(aDatabaseMetaData, bTable));
		// Set Sequence
		aDatabase.getSequences()
			 .stream()
			 .filter(sequenceName -> sequenceName.contains(tableName))
			 .findFirst()
			 .ifPresent(bTable::setSequenceName);
		lTables.add(bTable);
	    }
	}

	return lTables;
    }

    private List<Index> getIndices(final DatabaseMetaData aDatabaseMetaData, final Table aTable) throws SQLException
    {
	List<Index> indices = new ArrayList<>();

	ResultSet indexResultset = aDatabaseMetaData.getIndexInfo(null, null, aTable.getTableName(),
								  true, true);

	while (indexResultset.next())
	{
	    Index bIndex = new Index(aTable);
	    bIndex.setColumnName(indexResultset.getString("COLUMN_NAME"));
	    bIndex.setOrdinalPosition(indexResultset.getShort("ORDINAL_POSITION"));

	    bIndex.setIndexName(indexResultset.getString("INDEX_NAME"));
	    bIndex.setIndexQualifier(indexResultset.getString("INDEX_QUALIFIER"));
	    bIndex.setCardinality(indexResultset.getInt("CARDINALITY"));
	    String ascDesc = indexResultset.getString("ASC_OR_DESC");
	    if (ascDesc != null)
	    {
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

    private List<Column> getColumns(final DatabaseMetaData aDatabaseMetaData, final Table aTable) throws SQLException
    {
	List<Column> lColumns = new ArrayList<>();
	ResultSet lColumnResultSet = aDatabaseMetaData.getColumns(null, null, aTable.getTableName(),
								  null);
	ColumnType lColumnType;
	while (lColumnResultSet.next())
	{
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
	ResultSet primaryKeysResultSet = aDatabaseMetaData.getPrimaryKeys(null, null, aTable.getTableName());
	while (primaryKeysResultSet.next())
	{
	    lColumns.stream().filter(column -> {
			try
			{
			    return column.getColumnName().equals(primaryKeysResultSet.getString("COLUMN_NAME"));
			}
			catch (SQLException throwables)
			{
			    return false;
			}
		    }).findFirst()
		    .ifPresent(column -> {
			try
			{
			    column.setPrimaryKeyIndex(primaryKeysResultSet.getInt("KEY_SEQ"));
			}
			catch (SQLException throwables)
			{
			    throwables.printStackTrace();
			}
		    });
	}

	//Extracting Foreign Keys.
	ResultSet foreignKeysResultSet = aDatabaseMetaData.getExportedKeys(null, null, aTable.getTableName());
	while (foreignKeysResultSet.next())
	{
	    Key bKey = new Key();
	    bKey.setTableName(foreignKeysResultSet.getString("FKTABLE_NAME"));
	    bKey.setColumnName(foreignKeysResultSet.getString("FKCOLUMN_NAME"));
	    if (!lColumns.isEmpty())
	    {
		lColumns.stream().filter(column -> {
		    try
		    {
			return column.getColumnName().equals(foreignKeysResultSet.getString("PKCOLUMN_NAME"));
		    }
		    catch (SQLException throwables)
		    {
			return false;
		    }
		}).findFirst().ifPresent(column -> column.getExportedKeys().add(bKey));
	    }
	}
	return lColumns;
    }

    private ColumnType getColumnTypeForOthers(final Column aColumn)
    {
	if (aColumn.getTable().getDatabase().getDbType() == DBType.POSTGRES)
	{
	    if (aColumn.getTypeName().equalsIgnoreCase("json"))
	    {
		return ColumnType.JSON;
	    }
	    else if (aColumn.getTypeName().equalsIgnoreCase("jsonb"))
	    {
		return ColumnType.JSONB;
	    }
	    else if (aColumn.getTypeName().equalsIgnoreCase("uuid"))
	    {
		return ColumnType.UUID;
	    }
	    else if (aColumn.getTypeName().equalsIgnoreCase("interval"))
	    {
		return ColumnType.INTERVAL;
	    }
	}

	return null;
    }

    private List<Procedure> getProcedures(final DatabaseMetaData aDatabaseMetaData) throws SQLException
    {
	List<Procedure> lProcedures = new ArrayList<>();
	ResultSet lResultSet = aDatabaseMetaData.getProcedures(null, null, null);
	while (lResultSet.next())
	{
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

    private void repair(final Database aDatabase, final DatabaseMetaData aDatabaseMetaData)
    {
	switch (aDatabase.getDbType())
	{
	    case MARIADB:
	    case MYSQL:
	    {
		repairMySQL(aDatabase, aDatabaseMetaData);
		break;
	    }
	}
    }

    public List<UniqueConstraint> getUniqueConstraints(final DatabaseMetaData aDatabaseMetaData,
						       final Table aTable) throws SQLException
    {
	List<UniqueConstraint> lUniqueConstraints = new ArrayList<>();
	ResultSet rs = aDatabaseMetaData.getIndexInfo(null, aTable.getSchemaName(),
						      aTable.getTableName(), true, true);
	while (rs.next())
	{
	    String indexName = rs.getString("index_name");
	    String columnName = rs.getString("column_name");
	    Optional<UniqueConstraint> lUniqueConstraint = lUniqueConstraints.stream()
									     .filter(uniqueConstraint -> uniqueConstraint.getName().equals(
										     indexName))
									     .findFirst();
	    if (lUniqueConstraint.isPresent())
	    {
		lUniqueConstraint.get().getColumns().add(aTable.getColumns().stream()
							       .filter(column -> column.getColumnName()
										       .equals(columnName))
							       .findFirst().get());

	    }
	    else
	    {
		UniqueConstraint bUniqueConstraint = new UniqueConstraint();
		bUniqueConstraint.setName(indexName);
		List<Column> bColumns = new ArrayList<>();
		bColumns.add(aTable.getColumns().stream()
				   .filter(column -> column.getColumnName().equals(columnName)).findFirst().get());
		bUniqueConstraint.setColumns(bColumns);
		lUniqueConstraints.add(bUniqueConstraint);
	    }
	}

	return lUniqueConstraints;
    }

    private void repairMySQL(final Database aDatabase, final DatabaseMetaData aDatabaseMetaData)
    {
	if (aDatabase != null)
	{
	    aDatabase.getTables().forEach(table -> {
		try (PreparedStatement preparedStatement = aDatabaseMetaData.getConnection().prepareStatement("SELECT "
													      + "COLUMN_NAME,COLUMN_TYPE  from INFORMATION_SCHEMA.COLUMNS where\n"
													      + " table_name = ?"))
		{
		    preparedStatement.setString(1, table.getTableName());
		    ResultSet lResultSet = preparedStatement.executeQuery();

		    Column bColumn;
		    String bColumnType;
		    while (lResultSet.next())
		    {
			bColumn = table.getColumns().stream()
				       .filter(column1 -> {
					   try
					   {
					       return column1.getColumnName().equals(
						       lResultSet.getString("COLUMN_NAME"));
					   }
					   catch (SQLException throwables)
					   {
					       throwables.printStackTrace();
					   }
					   return false;
				       })
				       .findFirst().get();
			bColumnType = lResultSet.getString("COLUMN_TYPE");
			String[] s = bColumnType.split(CrawlerConsts.START_BR_REGX);
			bColumn.setTypeName(s[0].trim());

			ColumnType columnType1 = ColumnType.value(bColumn.getTypeName().toUpperCase());
			if (columnType1 != null)
			{
			    bColumn.setColumnType(columnType1);
			}
			if (s.length == 2)
			{
			    String grp = s[1].trim().replaceAll(CrawlerConsts.END_BR_REGX, "");

			    s = grp.split(CrawlerConsts.CAMA_STR);
			    bColumn.setSize(Integer.parseInt(s[0]));
			    if (s.length == 2)
			    {
				bColumn.setDecimalDigits(Integer.parseInt(s[1]));
			    }
			}
		    }
		}
		catch (final SQLException aSQLException)
		{
		    aSQLException.printStackTrace();
		}
	    });
	}
    }
}
