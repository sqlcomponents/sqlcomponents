package org.sqlcomponents.core.crawler;

import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.core.model.*;
import org.sqlcomponents.core.model.relational.enumeration.Flag;
import org.sqlcomponents.core.model.relational.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Crawler {


	public Database getDatabase(final Application application) throws ScubeException {
		Database database = new Database();
		try (Connection connection = DriverManager.getConnection(application.getUrl(), application
				.getUserName(), application.getPassword());) {
			DatabaseMetaData databasemetadata = connection.getMetaData();
			database.setSequences(getSequences(databasemetadata));
			database.setTables(getTables(databasemetadata, database));
			database.setFunctions(getProcedures(databasemetadata));
		} catch (SQLException e) {
			throw new ScubeException(e);
		}
		return database;
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

	private List<Table> getTables(DatabaseMetaData databasemetadata, Database database) throws SQLException {
		List<Table> tables = new ArrayList<>();

		ResultSet resultset = databasemetadata.getTables(null, null, null, new String[]{"TABLE"});

		while (resultset.next()) {
			final String tableName = resultset.getString("table_name");

				Table table = new Table(database);
				table.setTableName(tableName);
				table.setCategoryName(resultset.getString("table_cat"));
				table.setSchemaName(resultset.getString("table_schem"));
				table.setTableType(resultset.getString("table_type"));
				table.setRemarks(resultset.getString("remarks"));
				table.setCategoryType(resultset.getString("type_cat"));
				table.setSchemaType(resultset.getString("type_schem"));
				table.setNameType(resultset.getString("type_name"));
				table.setSelfReferencingColumnName(resultset.getString("self_referencing_col_name"));
				table.setReferenceGeneration(resultset.getString("ref_generation"));

				table.setColumns(getColumns(databasemetadata,table));

				// Set Sequence
				database.getSequences()
						.stream()
						.filter(sequenceName->sequenceName.contains(tableName))
						.findFirst()
						.ifPresent(sequenceName->table.setSequenceName(sequenceName));

				tables.add(table);


		}

		return tables;
	}

	private List<Column> getColumns(DatabaseMetaData databasemetadata,final Table table) throws SQLException {
		List<Column> columns = new ArrayList<>();

		ResultSet columnResultset = databasemetadata.getColumns(null, null, table.getTableName(), null);

		while (columnResultset.next()) {
			Column column = new Column(table);
			column.setColumnName(columnResultset.getString("COLUMN_NAME"));
			column.setTableName(columnResultset.getString("TABLE_NAME"));
			column.setTypeName(columnResultset.getString("TYPE_NAME"));
			column.setTypes(columnResultset.getInt("DATA_TYPE"));
			column.setSize(columnResultset.getInt("COLUMN_SIZE"));
			column.setDecimalDigits(columnResultset.getInt("DECIMAL_DIGITS"));
			column.setRemarks(columnResultset.getString("REMARKS"));
			column.setNullable(columnResultset.getBoolean("IS_NULLABLE"));
			column.setAutoIncrement(columnResultset.getBoolean("IS_AUTOINCREMENT"));
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
			}).findFirst().get().setPrimaryKeyIndex(primaryKeysResultSet.getInt("KEY_SEQ"));
		}

		//Extracting Foreign Keys.
		ResultSet foreignKeysResultSet = databasemetadata.getExportedKeys(null, null, table.getTableName());

		while (foreignKeysResultSet.next()) {
			Key key = new Key();
			key.setTableName(foreignKeysResultSet.getString("FKTABLE_NAME"));
			key.setColumnName(foreignKeysResultSet.getString("FKCOLUMN_NAME"));
			columns.stream().filter(column -> {
				try {
					return column.getColumnName().equals(foreignKeysResultSet.getString("PKCOLUMN_NAME"));
				} catch (SQLException throwables) {
					return false;
				}
			}).findFirst().get().getExportedKeys().add(key);
		}
		return columns;
	}

	private List<Procedure> getProcedures(DatabaseMetaData databasemetadata) throws SQLException {

		List<Procedure> functions = new ArrayList<>();

		ResultSet functionResultset = databasemetadata.getProcedures(null, null, null);
		System.out.println(functionResultset.getMetaData().getColumnCount());
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
		System.out.println("procedures");
		return functions;
	}
}
