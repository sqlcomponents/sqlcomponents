package com.techatpark.scubebird.crawler.oracle;

import com.techatpark.scubebird.core.crawler.Crawler;
import com.techatpark.scubebird.core.exception.ScubeException;
import com.techatpark.scubebird.core.model.Package;
import com.techatpark.scubebird.core.model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OracleCrawler extends Crawler {

    private static final String YES = "Y";

    private static final String ARGUMENT_TYPE_IN = "IN";
    private static final String ARGUMENT_TYPE_OUT = "OUT";

    // Table Related
    private static final int INDEX_TABLE_NAME = 1;
    private static final int INDEX_TABLE_COMMENTS = 2;
    private static final int INDEX_TABLE_TYPE = 3;
    private static final int INDEX_COLUMN_NAME = 4;
    private static final int INDEX_DATA_TYPE = 5;
    private static final int INDEX_DATA_SCALE = 6;
    private static final int INDEX_DATA_PRECISION = 7;
    private static final int INDEX_NULLABLE = 8;
    private static final int INDEX_COLUMN_COMMENTS = 9;
    private static final int INDEX_PK_INDEX = 10;
    private static final int INDEX_UNIQUE_CONSTRAINT = 11;
    private static final int INDEX_REFERENCE_TABLE = 12;
    private static final int INDEX_REFERENCE_COLUMN = 13;

    // Function Related

    private static final int INDEX_PACKAGE_NAME = 1;
    private static final int INDEX_FUNCTION_NAME = 2;
    private static final int INDEX_ARGUMENT_TYPE = 3;
    private static final int INDEX_ARGUMENT_NAME = 4;
    private static final int INDEX_ARGUMENT_DATA_TYPE = 5;
    private static final int INDEX_ARGUMENT_SIZE = 6;

    // Sequence Related
    private static final int INDEX_TABLE_SEQUENCE = 1;

    public OracleCrawler(final DaoProject ormProject) throws SQLException {
        super(ormProject);
    }

    @Override
    public Schema getSchema() throws ScubeException {
        Schema schema = new Schema();
        schema.setTables(getTables(this.ormProject));
        getFunctions(this.ormProject, schema);
        schema.setSequences(getSequences(this.ormProject));
        return schema;
    }

    private void getFunctions(DaoProject project, Schema schema)
            throws ScubeException {

        List<Package> packages = null;
        Package oraclePackage = null;

        List<Function> functions = null;
        Function function = null;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(getFunctionsQuery(project));

            packages = new ArrayList<Package>();
            functions = new ArrayList<Function>();

            String functionName = null;
            String packageName = null;
            String newPackageName = null;
            while (resultSet.next()) {
                newPackageName = resultSet.getString(INDEX_PACKAGE_NAME);
                if (newPackageName != null && !newPackageName.equals(packageName)) {

                    packageName = resultSet.getString(INDEX_PACKAGE_NAME);

                    if (packageName != null) {
                        oraclePackage = new Package();
                        oraclePackage.setFunctions(new ArrayList<Function>());
                        oraclePackage.setName(packageName);
                        packages.add(oraclePackage);
                    }
                }

                if (!resultSet.getString(INDEX_FUNCTION_NAME).equals(
                        functionName)) {
                    functionName = resultSet.getString(INDEX_FUNCTION_NAME);
                    function = new Function();
                    function.setParametes(new ArrayList<Column>());
                    function.setFunctionName(functionName);
                    if (packageName == null) {
                        functions.add(function);
                    } else {
                        oraclePackage.getFunctions().add(function);
                    }

                }

                Column column = getColumn(resultSet);

                if (column != null) {
                    if (ARGUMENT_TYPE_OUT.equals(resultSet
                            .getString(INDEX_ARGUMENT_TYPE))) {
                        function.setOutput(column);
                    } else if (ARGUMENT_TYPE_IN.equals(resultSet
                            .getString(INDEX_ARGUMENT_TYPE))) {
                        function.getParametes().add(column);
                    }
                }
            }

        } catch (SQLException e) {
            throw new ScubeException(e);
        }

        schema.setFunctions(functions);
        schema.setPackages(packages);
    }

    private String getFunctionsQuery(DaoProject project) {
        StringBuffer buffer = new StringBuffer("\n" +
                "                SELECT\n" +
                "                \t PACKAGE_NAME,\n" +
                "                \t OBJECT_NAME,\n" +
                "                \t IN_OUT,\n" +
                "                \t ARGUMENT_NAME,\n" +
                "                \t PLS_TYPE,\n" +
                "                \t DATA_LENGTH\t\n" +
                "                FROM\n" +
                "                \t USER_ARGUMENTS\n" +
                "                ORDER BY\n" +
                "                \t  PACKAGE_NAME,\n" +
                "                \t  OBJECT_NAME,\n" +
                "                \t  IN_OUT,\n" +
                "                \t  POSITION");

        return buffer.toString();
    }

    private Column getColumn(ResultSet resultSet) throws SQLException {
        Column column = null;
        if (resultSet.getString(INDEX_ARGUMENT_NAME) != null) {
            column = new Column();
            column.setColumnName(resultSet.getString(INDEX_ARGUMENT_NAME));
            column.setSize(resultSet.getInt(INDEX_ARGUMENT_SIZE));
            column
                    .setSqlDataType(resultSet
                            .getString(INDEX_ARGUMENT_DATA_TYPE));
        }
        return column;
    }

    private List<Table> getTables(DaoProject project) throws ScubeException {
        List<Table> tables = null;
        Table table = null;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(getTablesQuery(project));

            tables = new ArrayList<Table>();

            String tableName = null;

            Column column = null;

            while (resultSet.next()) {

                if (!resultSet.getString(INDEX_TABLE_NAME).equals(tableName)) {
                    tableName = resultSet.getString(INDEX_TABLE_NAME);
                    table = new Table();
                    table.setTableName(tableName);
                    table.setTableType(resultSet.getString(INDEX_TABLE_TYPE));
                    table.setRemarks(resultSet.getString(INDEX_TABLE_COMMENTS));
                    table.setColumns(new ArrayList<Column>());
                    tables.add(table);
                }

                column = getColumn(resultSet, table, column);

            }
        } catch (SQLException e) {
            throw new ScubeException(e);
        }
        return tables;
    }

    /**
     * If Data Base uses Post Sequence like Oracle then its crawler has to
     * provide list of Sequence Names here
     *
     * @return
     */
    private List<String> getSequences(DaoProject project) {
        List<String> sequences = new ArrayList<String>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        StringBuffer sequenceQuery = new StringBuffer(
                "SELECT SEQUENCE_NAME FROM USER_SEQUENCES");

        List<String> sequencePatterns = project.getSequencePatterns();
        if (sequencePatterns != null && sequencePatterns.size() != 0) {
            boolean isFirst = true;
            sequenceQuery.append(" WHERE ");
            for (String sequencePattern : sequencePatterns) {
                if (!isFirst) {
                    sequenceQuery.append("OR ");
                }
                sequenceQuery.append("SEQUENCE_NAME LIKE '");
                sequenceQuery.append(sequencePattern);
                sequenceQuery.append("' ");
                if (isFirst) {
                    isFirst = false;
                }
            }
        }
        try {
            connection = getConnection();
            statement = connection.createStatement();
            System.out.println(sequenceQuery.toString());
            resultSet = statement.executeQuery(sequenceQuery.toString());

            while (resultSet.next()) {
                sequences.add(resultSet.getString(INDEX_TABLE_SEQUENCE));
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sequences;
    }

    private Column getColumn(ResultSet resultSet, Table table,
                             Column previousColumn) throws SQLException {

        Column column = null;

        String columnName = resultSet.getString(INDEX_COLUMN_NAME);

        // if (previousColumn != null
        // && columnName.equals(previousColumn.getColumnName())) {
        // column = previousColumn;
        // } else {
        column = new Column();
        column.setColumnName(columnName);
        column.setSqlDataType(resultSet.getString(INDEX_DATA_TYPE));
        column.setSize(resultSet.getInt(INDEX_DATA_PRECISION));
        column.setDecimalDigits(resultSet.getInt(INDEX_DATA_SCALE));
        column.setNullable(YES.equals(resultSet.getString(INDEX_NULLABLE)));
        column.setPrimaryKeyIndex(resultSet.getInt(INDEX_PK_INDEX));
        column.setUniqueConstraintName(resultSet
                .getString(INDEX_UNIQUE_CONSTRAINT));
        column.setRemarks(resultSet.getString(INDEX_COLUMN_COMMENTS));
        table.getColumns().add(column);
        // }

        String referenceTableName = resultSet.getString(INDEX_REFERENCE_TABLE);

        if (referenceTableName != null) {
            List<ForeignKey> foreignKeys = column.getForeignKeys();
            ForeignKey foreignKey = new ForeignKey();
            foreignKey.setTableName(referenceTableName);
            foreignKey.setColumnName(resultSet
                    .getString(INDEX_REFERENCE_COLUMN));
            if (foreignKeys == null) {
                foreignKeys = new ArrayList<ForeignKey>();
            }
            foreignKeys.add(foreignKey);
            column.setForeignKeys(foreignKeys);

        }

        return column;
    }

    private String getTablesQuery(DaoProject project) {
        StringBuffer buffer = new StringBuffer("SELECT T1.TABLE_NAME,       \n" +
                "                        T1.COMMENTS TABLE_COMMENTS,\n" +
                "                        T1.TABLE_TYPE,\n" +
                "                        T1.COLUMN_NAME,\n" +
                "                        T1.DATA_TYPE,\n" +
                "                        T1.DATA_LENGTH,\n" +
                "                        T1.DATA_PRECISION,\n" +
                "                        T1.NULLABLE,\n" +
                "                        T1.COLUMN_COMMENTS COLUMN_COMMENTS,\n" +
                "                        T2.POSITION PK_INDEX,\n" +
                "                \t\tT3.CONSTRAINT_NAME UNIQUE_CONSTRAINT,\n" +
                "                        T4.REFERENCE_TABLE,\n" +
                "                        T4.REFERENCE_COLUMN\n" +
                "                FROM\n" +
                "                (\n" +
                "                    \n" +
                "                (\n" +
                "                SELECT C_TABLE.TABLE_NAME,\n" +
                "                        C_TABLE_COMMENTS.COMMENTS,\n" +
                "                        C_TABLE_COMMENTS.TABLE_TYPE,\n" +
                "                        C_TABLE_COLUMN.COLUMN_NAME,\n" +
                "                        C_TABLE_COLUMN.DATA_TYPE,\n" +
                "                        C_TABLE_COLUMN.DATA_LENGTH,\n" +
                "                        C_TABLE_COLUMN.DATA_PRECISION,\n" +
                "                        C_TABLE_COLUMN.NULLABLE,\t\t       \n" +
                "                        C_TABLE_COLUMN_COMMENTS.COMMENTS COLUMN_COMMENTS       \n" +
                "                    FROMs\n" +
                "                        USER_TABLES C_TABLE,\n" +
                "                        USER_TAB_COMMENTS C_TABLE_COMMENTS,\n" +
                "                        USER_TAB_COLS C_TABLE_COLUMN,\n" +
                "                        USER_COL_COMMENTS C_TABLE_COLUMN_COMMENTS\n" +
                "                       \n" +
                "                    WHERE\n" +
                "                        C_TABLE_COMMENTS.TABLE_NAME = C_TABLE.TABLE_NAME\n" +
                "                        AND C_TABLE_COLUMN.TABLE_NAME = C_TABLE.TABLE_NAME\n" +
                "                        AND C_TABLE_COLUMN_COMMENTS.TABLE_NAME = C_TABLE_COLUMN.TABLE_NAME\n" +
                "                        AND C_TABLE_COLUMN_COMMENTS.COLUMN_NAME = C_TABLE_COLUMN.COLUMN_NAME\n" +
                "                        AND @tableName1       \n" +
                "                )\n" +
                "                UNION\n" +
                "                (\n" +
                "                SELECT C_TABLE.VIEW_NAME TABLE_NAME,\n" +
                "                        C_TABLE_COMMENTS.COMMENTS,\n" +
                "                        C_TABLE_COMMENTS.TABLE_TYPE,\n" +
                "                        C_TABLE_COLUMN.COLUMN_NAME,\n" +
                "                        C_TABLE_COLUMN.DATA_TYPE,\n" +
                "                        C_TABLE_COLUMN.DATA_LENGTH,\n" +
                "                        C_TABLE_COLUMN.DATA_PRECISION,\n" +
                "                        C_TABLE_COLUMN.NULLABLE,\t\t       \n" +
                "                        C_TABLE_COLUMN_COMMENTS.COMMENTS COLUMN_COMMENTS       \n" +
                "                    FROMs\n" +
                "                        USER_VIEWS C_TABLE,\n" +
                "                        USER_TAB_COMMENTS C_TABLE_COMMENTS,\n" +
                "                        USER_TAB_COLS C_TABLE_COLUMN,\n" +
                "                        USER_COL_COMMENTS C_TABLE_COLUMN_COMMENTS\n" +
                "                       \n" +
                "                    WHERE\n" +
                "                        C_TABLE_COMMENTS.TABLE_NAME = C_TABLE.VIEW_NAME\n" +
                "                        AND C_TABLE_COLUMN.TABLE_NAME = C_TABLE.VIEW_NAME\n" +
                "                        AND C_TABLE_COLUMN_COMMENTS.TABLE_NAME = C_TABLE_COLUMN.TABLE_NAME\n" +
                "                        AND C_TABLE_COLUMN_COMMENTS.COLUMN_NAME = C_TABLE_COLUMN.COLUMN_NAME\n" +
                "                        AND @viewName1       \n" +
                "                )\n" +
                "                UNION\n" +
                "                (\n" +
                "                SELECT C_TABLE.MVIEW_NAME TABLE_NAME,\n" +
                "                        C_TABLE_COMMENTS.COMMENTS,\n" +
                "                        'MVIEW' TABLE_TYPE,\n" +
                "                        C_TABLE_COLUMN.COLUMN_NAME,\n" +
                "                        C_TABLE_COLUMN.DATA_TYPE,\n" +
                "                        C_TABLE_COLUMN.DATA_LENGTH,\n" +
                "                        C_TABLE_COLUMN.DATA_PRECISION,\n" +
                "                        C_TABLE_COLUMN.NULLABLE,\t\t       \n" +
                "                        C_TABLE_COLUMN_COMMENTS.COMMENTS COLUMN_COMMENTS       \n" +
                "                    FROMs\n" +
                "                        USER_MVIEWS C_TABLE,\n" +
                "                        USER_MVIEW_COMMENTS C_TABLE_COMMENTS,\n" +
                "                        USER_TAB_COLS C_TABLE_COLUMN,\n" +
                "                        USER_COL_COMMENTS C_TABLE_COLUMN_COMMENTS\n" +
                "                       \n" +
                "                    WHERE\n" +
                "                        C_TABLE_COMMENTS.MVIEW_NAME = C_TABLE.MVIEW_NAME\n" +
                "                        AND C_TABLE_COLUMN.TABLE_NAME = C_TABLE.MVIEW_NAME\n" +
                "                        AND C_TABLE_COLUMN_COMMENTS.TABLE_NAME = C_TABLE_COLUMN.TABLE_NAME\n" +
                "                        AND C_TABLE_COLUMN_COMMENTS.COLUMN_NAME = C_TABLE_COLUMN.COLUMN_NAME\n" +
                "                        AND @viewName2\n" +
                "                )\n" +
                "                       \n" +
                "                ) T1,\n" +
                "                (       \n" +
                "                SELECT C.TABLE_NAME, C.OWNER, C.CONSTRAINT_NAME,C.COLUMN_NAME,C.POSITION\n" +
                "                      FROM USER_CONS_COLUMNS C\n" +
                "                      WHERE CONSTRAINT_NAME = ( SELECT CONSTRAINT_NAME\n" +
                "                \t      FROM USER_CONSTRAINTS\n" +
                "                \t      WHERE TABLE_NAME = C.TABLE_NAME\n" +
                "                \t      AND CONSTRAINT_TYPE = 'P'     \n" +
                "                \t      )\n" +
                "                      AND @tableName2\n" +
                "                ) T2,\n" +
                "                (       \n" +
                "                SELECT C.TABLE_NAME, C.OWNER, C.CONSTRAINT_NAME,C.COLUMN_NAME,C.POSITION\n" +
                "                      FROM USER_CONS_COLUMNS C\n" +
                "                      WHERE CONSTRAINT_NAME = ( SELECT CONSTRAINT_NAME\n" +
                "                \t      FROM USER_CONSTRAINTS\n" +
                "                \t      WHERE TABLE_NAME = C.TABLE_NAME\n" +
                "                \t\t  AND CONSTRAINT_NAME = C.CONSTRAINT_NAME\n" +
                "                \t      AND CONSTRAINT_TYPE = 'U'     \n" +
                "                \t      )\n" +
                "                      AND @tableName2\n" +
                "                ) T3,\n" +
                "                (\n" +
                "                SELECT UNIQUE A.CONSTRAINT_NAME,\n" +
                "                      A.TABLE_NAME,\n" +
                "                      A.OWNER,\n" +
                "                      C.COLUMN_NAME COLUMN_NAME ,\n" +
                "                      B.TABLE_NAME REFERENCE_TABLE,\n" +
                "                      B.COLUMN_NAME REFERENCE_COLUMN,\n" +
                "                      A.R_CONSTRAINT_NAME ,\n" +
                "                      C.POSITION\n" +
                "                      FROM USER_CONSTRAINTS A, USER_CONS_COLUMNS B, USER_CONS_COLUMNS C\n" +
                "                      WHERE A.R_CONSTRAINT_NAME=B.CONSTRAINT_NAME     \n" +
                "                      AND A.CONSTRAINT_NAME=C.CONSTRAINT_NAME        \n" +
                "                      AND A.TABLE_NAME=C.TABLE_NAME\n" +
                "                      AND B.POSITION=C.POSITION     \n" +
                "                      AND @tableName3\n" +
                "                      ORDER BY A.CONSTRAINT_NAME, C.POSITION\n" +
                "                ) T4\n" +
                "                WHERE\n" +
                "                T1.TABLE_NAME = T2.TABLE_NAME(+)\n" +
                "                AND T1.COLUMN_NAME = T2.COLUMN_NAME(+)\n" +
                "                AND T1.TABLE_NAME = T3.TABLE_NAME(+)\n" +
                "                AND T1.COLUMN_NAME = T3.COLUMN_NAME(+)\n" +
                "                AND T1.TABLE_NAME = T4.TABLE_NAME(+)\n" +
                "                AND T1.COLUMN_NAME = T4.COLUMN_NAME(+)\n" +
                "                ORDER BY\n" +
                "                \tT1.TABLE_NAME,PK_INDEX,T1.COLUMN_NAME");
        String str = null;
        String SPACE = "\n";
        str = buffer.toString().replaceAll("@schemaName",
                "'" + project.getSchemaName() + "'");
        if (project.getTablePatterns() != null
                && project.getTablePatterns().size() != 0) {
            str = str.replaceAll("@tableName1",
                    getTableName1(project.getTablePatterns())).replaceAll(
                    "@tableName2", getTableName2(project.getTablePatterns()))
                    .replaceAll("@tableName3",
                            getTableName3(project.getTablePatterns()))
                    .replaceAll("@viewName1",
                            getViewName1(project.getTablePatterns()))
                    .replaceAll("@viewName2",
                            getViewName2(project.getTablePatterns()));
        }

        System.out.println(str);
        return str;
    }

    private String getTableName1(List<String> patterns) {
        StringBuffer buffer = new StringBuffer(
                "( UPPER(C_TABLE.TABLE_NAME) LIKE UPPER(");
        int index = 0;
        for (String pattern : patterns) {
            if (index == 0) {
                index = 1;
            } else {
                buffer.append(" OR UPPER(C_TABLE.TABLE_NAME) LIKE UPPER(");
            }

            buffer.append(" '");
            buffer.append(pattern);
            buffer.append("') ");

        }
        buffer.append(")");
        return buffer.toString();
    }

    private String getTableName2(List<String> patterns) {
        StringBuffer buffer = new StringBuffer(
                "(  UPPER(TABLE_NAME) LIKE UPPER(");
        int index = 0;
        for (String pattern : patterns) {
            if (index == 0) {
                index = 1;
            } else {
                buffer.append(" OR  UPPER(TABLE_NAME) LIKE UPPER(");
            }
            buffer.append(" '");
            buffer.append(pattern);
            buffer.append("') ");

        }
        buffer.append(")");
        return buffer.toString();
    }

    private String getTableName3(List<String> patterns) {
        StringBuffer buffer = new StringBuffer(
                "(  UPPER(A.TABLE_NAME) LIKE UPPER(");
        int index = 0;
        for (String pattern : patterns) {
            if (index == 0) {
                index = 1;
            } else {
                buffer.append(" OR  UPPER(A.TABLE_NAME) LIKE UPPER(");
            }

            buffer.append(" '");
            buffer.append(pattern);
            buffer.append("') ");

        }
        buffer.append(")");
        return buffer.toString();
    }

    private String getViewName1(List<String> patterns) {
        StringBuffer buffer = new StringBuffer(
                "(  UPPER(C_TABLE.VIEW_NAME) LIKE UPPER(");
        int index = 0;
        for (String pattern : patterns) {
            if (index == 0) {
                index = 1;
            } else {
                buffer.append(" OR  UPPER(C_TABLE.VIEW_NAME) LIKE UPPER(");
            }

            buffer.append(" '");
            buffer.append(pattern);
            buffer.append("') ");
        }
        buffer.append(")");
        return buffer.toString();
    }

    private String getViewName2(List<String> patterns) {
        StringBuffer buffer = new StringBuffer(
                "(  UPPER(C_TABLE.MVIEW_NAME) LIKE UPPER(");
        int index = 0;
        for (String pattern : patterns) {
            if (index == 0) {
                index = 1;
            } else {
                buffer.append(" OR  UPPER(C_TABLE.MVIEW_NAME) LIKE UPPER(");
            }

            buffer.append(" '");
            buffer.append(pattern);
            buffer.append("') ");
        }
        buffer.append(")");
        return buffer.toString();
    }

}
