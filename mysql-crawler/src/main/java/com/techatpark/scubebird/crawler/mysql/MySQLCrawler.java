package com.techatpark.scubebird.crawler.mysql;

import com.techatpark.scubebird.core.crawler.Crawler;
import com.techatpark.scubebird.core.exception.ScubeException;
import com.techatpark.scubebird.core.model.*;
import com.techatpark.scubebird.crawler.mysql.enums.DataType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MySQLCrawler extends Crawler {

    private static final Object PRIMARY_KEY = "PRI";

    public MySQLCrawler(final DaoProject ormProject) {
        super(ormProject);
    }

    @Override
    public Schema getSchema() throws ScubeException {
        Schema schema = new Schema();
        schema.setTables(getTables(ormProject));
        schema.setFunctions(new ArrayList<Function>());
        schema.setPackages(new ArrayList<com.techatpark.scubebird.core.model.Package>());
        return schema;
    }

    private List<Table> getTables(DaoProject project) throws ScubeException {
        List<Table> tables = new ArrayList<Table>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query = "SELECT \n" +
                "                c.TABLE_CATALOG, \n" +
                "                c.TABLE_SCHEMA, \n" +
                "                c.TABLE_NAME, \n" +
                "                c.COLUMN_NAME, \n" +
                "                c.ORDINAL_POSITION, \n" +
                "                c.COLUMN_DEFAULT, \n" +
                "                c.IS_NULLABLE, c.DATA_TYPE, c.CHARACTER_MAXIMUM_LENGTH, c.CHARACTER_OCTET_LENGTH, c.NUMERIC_PRECISION, c.NUMERIC_SCALE, c.CHARACTER_SET_NAME, c.COLLATION_NAME, c.COLUMN_TYPE, c.COLUMN_KEY, c.EXTRA, c.PRIVILEGES, c.COLUMN_COMMENT FROM  information_Schema.columns c, information_Schema.tables t where  t.table_schema like {0}  and t.table_name = c.table_name  order by c.table_name,c.ordinal_position";
        query = MessageFormat.format(query, "'" + project.getSchemaName() + "'");
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            String tableName = "";
            Table table = null;
            while (rs.next()) {
                String currentTable = rs.getString("TABLE_NAME");
                if (!currentTable.equals(tableName)) {
                    tableName = currentTable;
                    table = new Table();
                    table.setColumns(new ArrayList<Column>());
                    table.setTableName(currentTable);
                    table.setTableType(Table.TYPE_TABLE);
                    tables.add(table);
                }
                table.addColumn(mapColumn(rs));

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ScubeException(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ScubeException(e.getMessage());
            }

        }
        return tables;
    }

    private Column mapColumn(ResultSet rs) throws SQLException {
        Column column = new Column();
        column.setColumnName(rs.getString("COLUMN_NAME"));
        column.setDecimalDigits(rs.getInt("NUMERIC_SCALE"));
        column.setForeignKeys(null);
        column.setNullable("Y".equals(rs.getString("IS_NULLABLE")));
        if (rs.getString("COLUMN_KEY").equals(PRIMARY_KEY)) {
            column.setPrimaryKeyIndex(rs.getInt("ORDINAL_POSITION"));
        }
        column.setRemarks(rs.getString("COLUMN_COMMENT"));
        column.setSqlDataType(rs.getString("DATA_TYPE"));
        if (DataType.isNumericDataType(column.getSqlDataType())) {
            column.setSize(rs.getInt("NUMERIC_PRECISION"));
        } else {
            column.setSize(rs.getInt("CHARACTER_MAXIMUM_LENGTH"));
        }
        column.setTableName(rs.getString("COLLATION_NAME"));
        column.setUniqueConstraintName(null);
        return column;
    }

}
