package org.sqlcomponents.core.crawler;

import org.sqlcomponents.core.model.relational.Column;
import org.sqlcomponents.core.model.relational.Database;
import org.sqlcomponents.core.model.relational.enums.ColumnType;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Crawler for MySQL.
 */
public class MysqlCrawler extends Crawler {
    /**
     * Reairs the Database for MySQL.
     * @param database
     * @param databaseMetaData
     */
    @Override
    protected void repair(final Database database,
                          final DatabaseMetaData databaseMetaData) {
        database.getTables().forEach(table -> {
            try (Connection connection = databaseMetaData.getConnection();
                 PreparedStatement preparedStatement =
                         connection
                                 .prepareStatement("SELECT "
                                         + "COLUMN_NAME,COLUMN_TYPE  "
                                         + "from INFORMATION_SCHEMA"
                                         + ".COLUMNS where\n"
                                         + " table_name = ?")) {
                preparedStatement.setString(1, table.getTableName());

                try (ResultSet lResultSet = preparedStatement.executeQuery()) {
                    Column bColumn;
                    String bColumnType;
                    while (lResultSet.next()) {
                        bColumn = table.getColumns().stream()
                                .filter(column1 -> {
                                    try {
                                        return column1.getColumnName()
                                                .equals(lResultSet.getString(
                                                        "COLUMN_NAME"));
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                    return false;
                                }).findFirst().get();
                        bColumnType = lResultSet.getString("COLUMN_TYPE");
                        String[] s = bColumnType.split(START_BR_REGX);
                        bColumn.setTypeName(s[0].trim());

                        ColumnType columnType1 = ColumnType.value(
                                bColumn.getTypeName().toUpperCase());
                        if (columnType1 != null) {
                            bColumn.setColumnType(columnType1);
                        }
                        if (s.length == 2) {
                            String grp = s[1].trim()
                                    .replaceAll(END_BR_REGX, "");

                            s = grp.split(COMMA_STR);
                            bColumn.setSize(Integer.parseInt(s[0]));
                            if (s.length == 2) {
                                bColumn
                                    .setDecimalDigits(Integer.parseInt(s[1]));
                            }
                        }
                    }
                }



            } catch (final SQLException aSQLException) {
                aSQLException.printStackTrace();
            }
        });
    }
}
