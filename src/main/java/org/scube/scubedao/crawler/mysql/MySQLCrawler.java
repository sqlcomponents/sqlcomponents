package org.scube.scubedao.crawler.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.scube.exception.ScubeException;
import org.scube.scubedao.crawler.Crawler;
import org.scube.scubedao.crawler.mysql.enums.DataType;
import org.scube.scubedao.crawler.mysql.enums.TableColumn;
import org.scube.scubedao.crawler.mysql.enums.YesNo;
import org.scube.scubedao.model.Column;
import org.scube.scubedao.model.DaoProject;
import org.scube.scubedao.model.ForeignKey;
import org.scube.scubedao.model.Function;
import org.scube.scubedao.model.Schema;
import org.scube.scubedao.model.Table;

public class MySQLCrawler extends Crawler{
	
	private static final Object PRIMARY_KEY = "PRI";

	@Override
	public Schema getSchema(DaoProject project) throws ScubeException {
		Schema schema = new Schema();
		schema.setTables(getTables(project));
		schema.setFunctions(new ArrayList<Function>());
		schema.setSequences(new ArrayList<String>());
		schema.setPackages(new ArrayList<org.scube.scubedao.model.Package>());
		return schema;
	}

	private List<Table> getTables(DaoProject project)  throws ScubeException {
		List<Table> tables = new ArrayList<Table>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String query = ProperyFileReader.getString("MySQLCrawler.columnsQuery");
		query = MessageFormat.format(query, new Object[]{"'"+project.getSchemaName()+"'"});
		try {
			conn = getConnection(project);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			String tableName = "";
			Table table = null;
			while (rs.next()) {
				String currentTable = rs.getString(TableColumn.TABLE_NAME.name());
				if(!currentTable.equals(tableName) ){
					tableName = currentTable;
					table = new Table();
					table.setColumns(new ArrayList<Column>());
					table.setTableName(currentTable);
					table.setTableType(Table.TYPE_TABLE);
					tables.add(table);
				}
				table.addColumn(getColumn(rs,project));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ScubeException(e.getMessage());
		} finally {
			try {
				if(conn != null){
					conn.close();
				}
				if(stmt != null){
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ScubeException(e.getMessage());
			}
			
		}
		return tables;
	}

	private Column getColumn(ResultSet rs, DaoProject project) throws SQLException {
		Column column = new Column();
		column.setColumnName(rs.getString(TableColumn.COLUMN_NAME.name()));	
		column.setDecimalDigits(rs.getInt(TableColumn.NUMERIC_PRECISION.name()));
		column.setForeignKeys(null);
		column.setNullable(YesNo.YES.name().equals(rs.getString(TableColumn.IS_NULLABLE.name())) ? Boolean.TRUE : Boolean.FALSE);
		if(rs.getString(TableColumn.COLUMN_KEY.name()).equals(PRIMARY_KEY)){
			column.setPrimaryKeyIndex(rs.getInt(TableColumn.ORDINAL_POSITION.name()));	
		}
		column.setRemarks(rs.getString(TableColumn.COLUMN_COMMENT.name()));
		column.setSqlDataType(rs.getString(TableColumn.DATA_TYPE.name()));
		if(DataType.isNumericDataType(column.getSqlDataType())){
			column.setSize(rs.getInt(TableColumn.NUMERIC_SCALE.name()));	
		} else {
			column.setSize(rs.getInt(TableColumn.CHARACTER_MAXIMUM_LENGTH.name()));
		}
		column.setTableName(rs.getString(TableColumn.COLLATION_NAME.name()));
		column.setUniqueConstraintName(null);
		return column;
	}

}
