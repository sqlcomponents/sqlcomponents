package com.techatpark.scubebird.crawler.postgres;
import com.techatpark.scubebird.core.crawler.Crawler;
import com.techatpark.scubebird.core.exception.ScubeException;
import com.techatpark.scubebird.core.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresCrawler extends Crawler {


    @Override
    public Schema getSchema(final DaoProject ormProject) throws ScubeException {
        Schema schema = new Schema();
        try {
            schema.setTables(getTables(ormProject));
        } catch (SQLException | ClassNotFoundException e) {
            throw new ScubeException(e);
        }
        return schema;
    }

    private List<Table> getTables(final DaoProject ormProject) throws SQLException, ClassNotFoundException {
        List<Table> tables = new ArrayList<>();
        Connection connection = getConnection(ormProject);
        DatabaseMetaData databasemetadata = connection.getMetaData();

        ResultSet resultset = databasemetadata.getTables(null, null, null, new String[]{"TABLE"});
        while(resultset.next()) {
            Table table = new Table();
            table.setTableName(resultset.getString("table_name"));
            table.setCategoryName(resultset.getString("table_cat"));
            table.setSchemaName(resultset.getString("table_schem"));
            table.setTableType(resultset.getString("table_type"));
            table.setRemarks(resultset.getString("remarks"));
            table.setCategoryType(resultset.getString("type_cat"));
            table.setSchemaType(resultset.getString("type_schem"));
            table.setNameType(resultset.getString("type_name"));
            table.setSelfReferencingColumnName(resultset.getString("self_referencing_col_name"));
            table.setReferenceGeneration(resultset.getString("ref_generation"));

            table.setColumns(getColumns(ormProject, table));
            tables.add(table);
        }

         return tables;
    }

    private List<Column> getColumns(final DaoProject ormProject, final Table table) throws SQLException, ClassNotFoundException {
        List<Column> columns = new ArrayList<>();
        Connection connection = getConnection(ormProject);
        DatabaseMetaData databasemetadata = connection.getMetaData();
        ResultSet columnResultset = databasemetadata.getColumns(null,null, table.getTableName(), null);

        while(columnResultset.next()) {
            Column column = new Column();
            column.setColumnName(columnResultset.getString("COLUMN_NAME"));
            column.setTableName(columnResultset.getString("TABLE_NAME"));
            column.setSqlDataType(columnResultset.getString("SQL_DATA_TYPE"));
            column.setSize(columnResultset.getInt("COLUMN_SIZE"));
            column.setDecimalDigits(columnResultset.getInt("DECIMAL_DIGITS"));
            column.setRemarks(columnResultset.getString("REMARKS"));
            column.setNullable(columnResultset.getBoolean("IS_NULLABLE"));
            column.setAutoIncrement(columnResultset.getBoolean("IS_AUTOINCREMENT"));
            column.setTableCategory(columnResultset.getString("TABLE_CAT"));
            column.setTableSchema(columnResultset.getString("TABLE_SCHEM"));
            column.setTypeName(columnResultset.getString("TYPE_NAME"));
            column.setBufferLength(columnResultset.getInt("BUFFER_LENGTH"));
            column.setNumberPrecisionRadix(columnResultset.getInt("NUM_PREC_RADIX"));
            column.setColumnDefinition(columnResultset.getString("COLUMN_DEF"));
            column.setOrdinalPosition(columnResultset.getInt("ORDINAL_POSITION"));
            column.setScopeCatalog(columnResultset.getString("SCOPE_CATALOG"));
            column.setScopeSchema(columnResultset.getString("SCOPE_SCHEMA"));
            column.setScopeTable(columnResultset.getString("SCOPE_TABLE"));
            column.setSourceDataType(columnResultset.getString("SOURCE_DATA_TYPE"));
            column.setGeneratedColumn( columnResultset.getString("IS_GENERATEDCOLUMN") != null
                    && !columnResultset.getString("IS_GENERATEDCOLUMN").trim().equals("") );
            column.setForeignKeys(new ArrayList<>());

            columns.add(column);
        }

        // Fill Primary Keys
        ResultSet primaryKeysResultSet = databasemetadata.getPrimaryKeys(null,null, table.getTableName());
        while(primaryKeysResultSet.next()) {
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

        while(foreignKeysResultSet.next())
        {

            ForeignKey foreignKey = new ForeignKey();
            foreignKey.setTableName(foreignKeysResultSet.getString("FKTABLE_NAME"));
            foreignKey.setColumnName(foreignKeysResultSet.getString("FKCOLUMN_NAME"));
            columns.stream().filter(column -> {
                try {
                    return column.getColumnName().equals(foreignKeysResultSet.getString("PKCOLUMN_NAME"));
                } catch (SQLException throwables) {
                    return false;
                }
            }).findFirst().get().getForeignKeys().add(foreignKey);
            //System.out.println(fk.getString("PKTABLE_NAME") + "---" + fk.getString("PKCOLUMN_NAME") + "===" + fk.getString("FKTABLE_NAME") + "---" + fk.getString("FKCOLUMN_NAME"));
        }


        return columns;
    }
}
