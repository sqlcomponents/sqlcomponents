package com.techatpark.scubebird.crawler.postgres;
import com.techatpark.scubebird.core.crawler.Crawler;
import com.techatpark.scubebird.core.exception.ScubeException;
import com.techatpark.scubebird.core.model.Column;
import com.techatpark.scubebird.core.model.DaoProject;
import com.techatpark.scubebird.core.model.Schema;
import com.techatpark.scubebird.core.model.Table;

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
        //Print TABLE_TYPE "TABLE"

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
        ResultSet resultset = databasemetadata.getColumns(null,null, String.valueOf(table), null);

        //TABLE_CAT
        //TABLE_SCHEM
        //TYPE_NAME
        //BUFFER_LENGTH
        //NUM_PREC_RADIX
        //NULLABLE
        //COLUMN_DEF
        //SQL_DATA_TYPE
        //SQL_DATETIME_SUB
        //CHAR_OCTET_LENGTH
        //ORDINAL_POSITION
        //SCOPE_CATALOG
        //SCOPE_SCHEMA
        //SCOPE_TABLE
        //SOURCE_DATA_TYPE
        //IS_GENERATEDCOLUMN

        while(resultset.next()) {
            Column column = new Column();
            column.setColumnName(resultset.getString("COLUMN_NAME"));
            column.setTableName(resultset.getString("TABLE_NAME"));
            column.setSqlDataType(resultset.getString("DATA_TYPE"));
            column.setSize(resultset.getInt("COLUMN_SIZE"));
            column.setDecimalDigits(resultset.getInt("DECIMAL_DIGITS"));
            column.setRemarks(resultset.getString("REMARKS"));
            column.setNullable(resultset.getBoolean("IS_NULLABLE"));
            column.setAutoIncrement(resultset.getBoolean("IS_AUTOINCREMENT"));
            columns.add(column);
        }
        return columns;
    }
}
