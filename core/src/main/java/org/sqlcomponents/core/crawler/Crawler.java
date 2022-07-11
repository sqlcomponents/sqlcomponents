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
    /**
     * @param aApplication
     * @return Database
     * @throws SQLComponentsException
     */
    public Database getDatabase(final Application aApplication)
	    throws SQLComponentsException
    {
	Database lDatabase = new Database();
	DataSource lDataSource = DataSourceUtil.getDataSource(
		aApplication.getUrl(),
		aApplication.getUserName(),
		aApplication.getPassword(),
		aApplication.getSchemaName());
	try (Connection lConnection = lDataSource.getConnection())
	{
	    DatabaseMetaData databasemMetadata = lConnection.getMetaData();
	    lDatabase.setCatalogTerm(databasemMetadata.getCatalogTerm());
	    lDatabase.setCatalogSeperator(databasemMetadata.getCatalogSeparator());

	    switch (databasemMetadata.getDatabaseProductName().toLowerCase().trim())
	    {
		case CrawlerConsts.POSTGRES_DB:
		{
		    lDatabase.setDbType(DBType.POSTGRES);
		    break;
		}
		case CrawlerConsts.MYSQL_DB:
		{
		    lDatabase.setDbType(DBType.MYSQL);
		    break;
		}
		case CrawlerConsts.MARIA_DB:
		{
		    lDatabase.setDbType(DBType.MARIADB);
		}
	    }

	    lDatabase.setDatabaseMajorVersion(databasemMetadata.getDatabaseMajorVersion());
	    lDatabase.setDatabaseMinorVersion(databasemMetadata.getDatabaseMinorVersion());
	    lDatabase.setDatabaseProductVersion(databasemMetadata.getDatabaseProductVersion());
	    lDatabase.setDefaultTransactionIsolation(databasemMetadata.getDefaultTransactionIsolation());
	    lDatabase.setDatabaseMajorVersion(databasemMetadata.getDatabaseMajorVersion());
	    lDatabase.setDatabaseMinorVersion(databasemMetadata.getDatabaseMinorVersion());
	    lDatabase.setDriverName(databasemMetadata.getDriverName());
	    lDatabase.setDriverVersion(databasemMetadata.getDriverVersion());
	    lDatabase.setExtraNameCharacters(databasemMetadata.getExtraNameCharacters());
	    lDatabase.setIdentifierQuoteString(databasemMetadata.getIdentifierQuoteString());
	    lDatabase.setJdbcMajorVersion(databasemMetadata.getJDBCMajorVersion());
	    lDatabase.setJdbcMinorVersion(databasemMetadata.getJDBCMinorVersion());
	    lDatabase.setMaxBinaryLiteralLength(databasemMetadata.getMaxBinaryLiteralLength());
	    lDatabase.setMaxCharLiteralLength(databasemMetadata.getMaxCharLiteralLength());
	    lDatabase.setMaxCatalogNameLength(databasemMetadata.getMaxCatalogNameLength());
	    lDatabase.setMaxColumnNameLength(databasemMetadata.getMaxColumnNameLength());
	    lDatabase.setMaxColumnsInGroupBy(databasemMetadata.getMaxColumnsInGroupBy());
	    lDatabase.setMaxColumnsInIndex(databasemMetadata.getMaxColumnsInIndex());
	    lDatabase.setMaxColumnsInOrderBy(databasemMetadata.getMaxColumnsInOrderBy());
	    lDatabase.setMaxColumnsInSelect(databasemMetadata.getMaxColumnsInSelect());
	    lDatabase.setMaxColumnsInTable(databasemMetadata.getMaxColumnsInTable());
	    lDatabase.setMaxConnections(databasemMetadata.getMaxConnections());
	    lDatabase.setMaxCursorNameLength(databasemMetadata.getMaxCursorNameLength());
	    lDatabase.setMaxIndexLength(databasemMetadata.getMaxIndexLength());
	    lDatabase.setMaxSchemaNameLength(databasemMetadata.getMaxSchemaNameLength());
	    lDatabase.setMaxProcedureNameLength(databasemMetadata.getMaxProcedureNameLength());
	    lDatabase.setMaxRowSize(databasemMetadata.getMaxRowSize());
	    lDatabase.setDoesMaxRowSizeIncludeBlobs(databasemMetadata.doesMaxRowSizeIncludeBlobs());
	    lDatabase.setMaxStatementLength(databasemMetadata.getMaxStatementLength());
	    lDatabase.setMaxStatements(databasemMetadata.getMaxStatements());
	    lDatabase.setMaxTableNameLength(databasemMetadata.getMaxTableNameLength());
	    lDatabase.setMaxTablesInSelect(databasemMetadata.getMaxTablesInSelect());
	    lDatabase.setMaxUserNameLength(databasemMetadata.getMaxUserNameLength());
	    lDatabase.setNumericFunctions(
		    new HashSet<>(Arrays.asList(databasemMetadata.getNumericFunctions().split(CrawlerConsts.CAMA_STR))));
	    lDatabase.setProcedureTerm(databasemMetadata.getProcedureTerm());
	    lDatabase.setResultSetHoldability(databasemMetadata.getResultSetHoldability());
	    lDatabase.setSchemaTerm(databasemMetadata.getSchemaTerm());
	    lDatabase.setSearchStringEscape(databasemMetadata.getSearchStringEscape());
	    lDatabase.setSqlKeywords(new TreeSet<>(Arrays.asList(databasemMetadata.getSQLKeywords().split(
		    CrawlerConsts.CAMA_STR))));
	    lDatabase.setStringFunctions(
		    new TreeSet<>(Arrays.asList(databasemMetadata.getStringFunctions().split(CrawlerConsts.CAMA_STR))));
	    lDatabase.setSystemFunctions(
		    new TreeSet<>(Arrays.asList(databasemMetadata.getSystemFunctions().split(CrawlerConsts.CAMA_STR))));
	    lDatabase.setTimeDateFunctions(
		    new TreeSet<>(Arrays.asList(databasemMetadata.getTimeDateFunctions().split(
			    CrawlerConsts.CAMA_STR))));
	    lDatabase.setSupportsTransactions(databasemMetadata.supportsTransactions());
	    lDatabase.setSupportsDataDefinitionAndDataManipulationTransactions(
		    databasemMetadata.supportsDataDefinitionAndDataManipulationTransactions());
	    lDatabase.setDataDefinitionCausesTransactionCommit(
		    databasemMetadata.dataDefinitionCausesTransactionCommit());
	    lDatabase.setDataDefinitionIgnoredInTransactions(databasemMetadata.dataDefinitionIgnoredInTransactions());
	    //			lDatabase.setSupportsResultSetType(databasemMetadata.supportsResultSetType());
	    //			lDatabase.setSupportsResultSetConcurrency(databasemMetadata.supportsResultSetConcurrency());
	    //			lDatabase.setOwnUpdatesAreVisible(databasemMetadata.ownUpdatesAreVisible());
	    //			lDatabase.setOwnDeletesAreVisible(databasemMetadata.ownDeletesAreVisible());
	    //			lDatabase.setOwnInsertsAreVisible(databasemMetadata.ownInsertsAreVisible());
	    //			lDatabase.setOthersUpdatesAreVisible(databasemMetadata.othersUpdatesAreVisible());
	    //			lDatabase.setOthersDeletesAreVisible(databasemMetadata.othersDeletesAreVisible());
	    //			lDatabase.setOthersInsertsAreVisible(databasemMetadata.othersInsertsAreVisible());
	    //			lDatabase.setUpdatesAreDetected(databasemMetadata.updatesAreDetected());
	    //			lDatabase.setDeletesAreDetected(databasemMetadata.deletesAreDetected());
	    //			lDatabase.setInsertsAreDetected(databasemMetadata.insertsAreDetected());
	    // 			lDatabase.setSupportsResultSetHoldability(databasemMetadata.supportsResultSetHoldability());
	    //			lDatabase.setSupportsTransactionIsolationLevel(databasemMetadata.supportsTransactionIsolationLevel());
	    lDatabase.setCatalogAtStart(databasemMetadata.isCatalogAtStart());
	    lDatabase.setReadOnly(databasemMetadata.isReadOnly());
	    lDatabase.setLocatorsUpdateCopy(databasemMetadata.locatorsUpdateCopy());
	    lDatabase.setSupportsBatchUpdates(databasemMetadata.supportsBatchUpdates());
	    lDatabase.setSupportsSavePoint(databasemMetadata.supportsAlterTableWithAddColumn());
	    lDatabase.setSupportsNamedParameters(databasemMetadata.supportsNamedParameters());
	    lDatabase.setSupportsMultipleOpenResults(databasemMetadata.supportsMultipleOpenResults());
	    lDatabase.setSupportsGetGeneratedKeys(databasemMetadata.supportsGetGeneratedKeys());
	    lDatabase.setSqlStateType(databasemMetadata.getSQLStateType());
	    lDatabase.setSupportsStatementPooling(databasemMetadata.supportsStatementPooling());
	    lDatabase.setAllProceduresAreCallable(databasemMetadata.allProceduresAreCallable());
	    lDatabase.setAllTablesAreSelectable(databasemMetadata.allTablesAreSelectable());
	    lDatabase.setUrl(databasemMetadata.getURL());
	    lDatabase.setUserName(databasemMetadata.getUserName());
	    lDatabase.setNullPlusNonNullIsNull(databasemMetadata.nullPlusNonNullIsNull());
	    lDatabase.setNullsAreSortedHigh(databasemMetadata.nullsAreSortedHigh());
	    lDatabase.setNullsAreSortedLow(databasemMetadata.nullsAreSortedLow());
	    lDatabase.setNullsAreSortedAtStart(databasemMetadata.nullsAreSortedAtStart());
	    lDatabase.setNullsAreSortedAtEnd(databasemMetadata.nullsAreSortedAtEnd());
	    lDatabase.setAutoCommitFailureClosesAllResultSets(databasemMetadata.autoCommitFailureClosesAllResultSets());
	    lDatabase.setGeneratedKeyAlwaysReturned(databasemMetadata.generatedKeyAlwaysReturned());
	    lDatabase.setStoresLowerCaseIdentifiers(databasemMetadata.storesLowerCaseIdentifiers());
	    lDatabase.setStoresLowerCaseQuotedIdentifiers(databasemMetadata.storesLowerCaseQuotedIdentifiers());
	    lDatabase.setStoresMixedCaseIdentifiers(databasemMetadata.storesMixedCaseIdentifiers());
	    lDatabase.setStoresMixedCaseQuotedIdentifiers(databasemMetadata.storesMixedCaseQuotedIdentifiers());
	    lDatabase.setStoresUpperCaseIdentifiers(databasemMetadata.storesUpperCaseIdentifiers());
	    lDatabase.setStoresUpperCaseQuotedIdentifiers(databasemMetadata.storesUpperCaseQuotedIdentifiers());
	    lDatabase.setSupportsAlterTableWithAddColumn(databasemMetadata.supportsAlterTableWithAddColumn());
	    lDatabase.setSupportsAlterTableWithDropColumn(databasemMetadata.supportsAlterTableWithDropColumn());
	    lDatabase.setSupportsANSI92EntryLevelSQL(databasemMetadata.supportsANSI92EntryLevelSQL());
	    lDatabase.setSupportsANSI92FullSQL(databasemMetadata.supportsANSI92FullSQL());
	    lDatabase.setSupportsANSI92IntermediateSQL(databasemMetadata.supportsANSI92IntermediateSQL());
	    lDatabase.setSupportsCatalogsInDataManipulation(databasemMetadata.supportsCatalogsInDataManipulation());
	    lDatabase.setSupportsCatalogsInIndexDefinitions(databasemMetadata.supportsCatalogsInIndexDefinitions());
	    lDatabase.setSupportsCatalogsInPrivilegeDefinitions(
		    databasemMetadata.supportsCatalogsInPrivilegeDefinitions());
	    lDatabase.setSupportsCatalogsInProcedureCalls(databasemMetadata.supportsCatalogsInProcedureCalls());
	    lDatabase.setSupportsCatalogsInTableDefinitions(databasemMetadata.supportsCatalogsInTableDefinitions());
	    lDatabase.setSupportsColumnAliasing(databasemMetadata.supportsColumnAliasing());
	    lDatabase.setTableTypes(getTableTypes(databasemMetadata));
	    lDatabase.setSequences(getSequences(databasemMetadata));
	    lDatabase.setTables(getTables(databasemMetadata, lDatabase, aApplication.getSchemaName(),
					  tableName -> matches(aApplication.getTablePatterns(), tableName)));
	    // lDatabase.setFunctions(getProcedures(databasemMetadata));
	    repair(lDatabase, databasemMetadata);
	}
	catch (final Exception e)
	{
	    throw new SQLComponentsException(e);
	}
	return lDatabase;
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
	List<Table> tables = new ArrayList<>();

	String schemaNamePattern = aDatabase.getDbType() == DBType.MYSQL
				   ? aSchemeName : null;
	String catalog = aDatabase.getDbType() == DBType.MYSQL
			 ? aSchemeName : null;

	ResultSet resultset = aDatabaseMetaData.getTables(catalog, schemaNamePattern, null,
							  new String[]{"TABLE"});

	while (resultset.next())
	{
	    final String tableName = resultset.getString("table_name");
	    System.out.println(tableName);
	    if (aTableFilter.test(tableName))
	    {
		Table table = new Table(aDatabase);
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

		table.setColumns(getColumns(aDatabaseMetaData, table));

		table.setIndices(getIndices(aDatabaseMetaData, table));

		table.setUniqueColumns(getUniqueConstraints(aDatabaseMetaData, table));
		// Set Sequence
		aDatabase.getSequences()
			 .stream()
			 .filter(sequenceName -> sequenceName.contains(tableName))
			 .findFirst()
			 .ifPresent(table::setSequenceName);

		tables.add(table);
	    }
	}

	return tables;
    }

    private List<Index> getIndices(final DatabaseMetaData aDatabaseMetaData, final Table aTable) throws SQLException
    {
	List<Index> indices = new ArrayList<>();

	ResultSet indexResultset = aDatabaseMetaData.getIndexInfo(null, null, aTable.getTableName(),
								  true, true);

	while (indexResultset.next())
	{
	    Index index = new Index(aTable);
	    index.setColumnName(indexResultset.getString("COLUMN_NAME"));
	    index.setOrdinalPosition(indexResultset.getShort("ORDINAL_POSITION"));

	    index.setIndexName(indexResultset.getString("INDEX_NAME"));
	    index.setIndexQualifier(indexResultset.getString("INDEX_QUALIFIER"));
	    index.setCardinality(indexResultset.getInt("CARDINALITY"));
	    String ascDesc = indexResultset.getString("ASC_OR_DESC");
	    if (ascDesc != null)
	    {
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

    private List<Column> getColumns(final DatabaseMetaData aDatabaseMetaData, final Table aTable) throws SQLException
    {
	List<Column> lColumns = new ArrayList<>();

	ResultSet lColumnResultSet = aDatabaseMetaData.getColumns(null, null, aTable.getTableName(),
								 null);
	ColumnType columnType;
	while (lColumnResultSet.next())
	{
	    Column column = new Column(aTable);
	    column.setColumnName(lColumnResultSet.getString("COLUMN_NAME"));
	    column.setTableName(lColumnResultSet.getString("TABLE_NAME"));
	    column.setTypeName(lColumnResultSet.getString("TYPE_NAME"));
	    columnType = ColumnType.value(JDBCType.valueOf(lColumnResultSet.getInt("DATA_TYPE")));
	    column.setColumnType(columnType == ColumnType.OTHER ? getColumnTypeForOthers(column) : columnType);
	    column.setSize(lColumnResultSet.getInt("COLUMN_SIZE"));
	    column.setDecimalDigits(lColumnResultSet.getInt("DECIMAL_DIGITS"));
	    column.setRemarks(lColumnResultSet.getString("REMARKS"));
	    column.setNullable(Flag.value(lColumnResultSet.getString("IS_NULLABLE")));
	    column.setAutoIncrement(Flag.value(lColumnResultSet.getString("IS_AUTOINCREMENT")));
	    column.setTableCategory(lColumnResultSet.getString("TABLE_CAT"));
	    column.setTableSchema(lColumnResultSet.getString("TABLE_SCHEM"));
	    column.setBufferLength(lColumnResultSet.getInt("BUFFER_LENGTH"));
	    column.setNumberPrecisionRadix(lColumnResultSet.getInt("NUM_PREC_RADIX"));
	    column.setColumnDefinition(lColumnResultSet.getString("COLUMN_DEF"));
	    column.setOrdinalPosition(lColumnResultSet.getInt("ORDINAL_POSITION"));
	    column.setScopeCatalog(lColumnResultSet.getString("SCOPE_CATALOG"));
	    column.setScopeSchema(lColumnResultSet.getString("SCOPE_SCHEMA"));
	    column.setScopeTable(lColumnResultSet.getString("SCOPE_TABLE"));
	    column.setSourceDataType(lColumnResultSet.getString("SOURCE_DATA_TYPE"));
	    column.setGeneratedColumn(Flag.value(lColumnResultSet.getString("IS_GENERATEDCOLUMN")));
	    column.setExportedKeys(new TreeSet<>());
	    lColumns.add(column);
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
	    Key key = new Key();
	    key.setTableName(foreignKeysResultSet.getString("FKTABLE_NAME"));
	    key.setColumnName(foreignKeysResultSet.getString("FKCOLUMN_NAME"));
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
		}).findFirst().ifPresent(column -> column.getExportedKeys().add(key));
	    }
	}
	return lColumns;
    }

    private ColumnType getColumnTypeForOthers(final Column aColumn)
    {
	switch (aColumn.getTable().getDatabase().getDbType())
	{
	    case POSTGRES:
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
		break;
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

		    Column lColumn;
		    String lColumnType;
		    while (lResultSet.next())
		    {
			lColumn = table.getColumns().stream()
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
			lColumnType = lResultSet.getString("COLUMN_TYPE");
			String[] s = lColumnType.split(CrawlerConsts.START_BR_REGX);
			lColumn.setTypeName(s[0].trim());

			ColumnType columnType1 = ColumnType.value(lColumn.getTypeName().toUpperCase());
			if (columnType1 != null)
			{
			    lColumn.setColumnType(columnType1);
			}
			if (s.length == 2)
			{
			    String grp = s[1].trim().replaceAll(CrawlerConsts.END_BR_REGX, "");

			    s = grp.split(CrawlerConsts.CAMA_STR);
			    lColumn.setSize(Integer.parseInt(s[0]));
			    if (s.length == 2)
			    {
				lColumn.setDecimalDigits(Integer.parseInt(s[1]));
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
