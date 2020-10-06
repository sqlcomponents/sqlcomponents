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

        //Extracting Primary Key Index
        ResultSet pk = databasemetadata.getPrimaryKeys(null,null, String.valueOf(table));

        //Extracting Foreign Keys.
        ResultSet fk = databasemetadata.getImportedKeys(null, null, String.valueOf(table));
        List<ForeignKey> foreignKeys = new ArrayList<>();
        while(fk.next())
        {
            ForeignKey foreignKey = new ForeignKey();
            foreignKey.setTableName(fk.getString("FKTABLE_NAME"));
            foreignKey.setColumnName(fk.getString("FKCOLUMN_NAME"));
            foreignKeys.add(foreignKey);
            //System.out.println(fk.getString("PKTABLE_NAME") + "---" + fk.getString("PKCOLUMN_NAME") + "===" + fk.getString("FKTABLE_NAME") + "---" + fk.getString("FKCOLUMN_NAME"));
        }

        //SQL_DATETIME_SUB
        //CHAR_OCTET_LENGTH

        while(resultset.next()) {
            Column column = new Column();
            column.setColumnName(resultset.getString("COLUMN_NAME"));
            column.setTableName(resultset.getString("TABLE_NAME"));
            column.setSqlDataType(resultset.getString("SQL_DATA_TYPE"));
            column.setSize(resultset.getInt("COLUMN_SIZE"));
            column.setDecimalDigits(resultset.getInt("DECIMAL_DIGITS"));
            column.setRemarks(resultset.getString("REMARKS"));
            column.setNullable(resultset.getBoolean("IS_NULLABLE"));
            column.setAutoIncrement(resultset.getBoolean("IS_AUTOINCREMENT"));
            column.setTableCategory(resultset.getString("TABLE_CAT"));
            column.setTableSchema(resultset.getString("TABLE_SCHEM"));
            column.setTypeName(resultset.getString("TYPE_NAME"));
            column.setBufferLength(resultset.getInt("BUFFER_LENGTH"));
            column.setNumberPrecisionRadix(resultset.getInt("NUM_PREC_RADIX"));
            column.setColumnDefinition(resultset.getString("COLUMN_DEF"));
            column.setOrdinalPosition(resultset.getInt("ORDINAL_POSITION"));
            column.setScopeCatalog(resultset.getString("SCOPE_CATALOG"));
            column.setScopeSchema(resultset.getString("SCOPE_SCHEMA"));
            column.setScopeTable(resultset.getString("SCOPE_TABLE"));
            column.setSourceDataType(resultset.getString("SOURCE_DATA_TYPE"));
            column.setGeneratedColumn(resultset.getBoolean("IS_GENERATEDCOLUMN"));
            column.setPrimaryKeyIndex(resultset.getInt(pk.getString("KEY_SEQ")));
            column.setForeignKeys(foreignKeys);

            columns.add(column);
        }
        return columns;
    }
}
