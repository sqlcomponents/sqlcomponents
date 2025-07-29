package org.sqlcomponents.core.crawler;

import org.sqlcomponents.core.model.relational.Database;
import org.sqlcomponents.core.model.relational.Type;
import org.sqlcomponents.core.model.relational.enums.TypeType;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostgresCrawler extends Crawler {
    /**
     * Repairs Database for Postgres.
     * @param database
     * @param databaseMetaData
     */
    @Override
    protected void repair(final Database database,
                          final DatabaseMetaData databaseMetaData) {
        try (Connection connection = databaseMetaData.getConnection();
             PreparedStatement preparedStatement
                     = connection
                     .prepareStatement(
                             """
                                     select n.nspname as enum_schema,
                                     t.typname as enum_name,
                                    string_agg(e.enumlabel, ', ')
                                     as enum_value
                                     from pg_type t
                                     join pg_enum e on t.oid = e.enumtypid
                                   join pg_catalog.pg_namespace n ON
                                     n.oid = t.typnamespace
                                  group by enum_schema, enum_name
                                  """)) {
            try (ResultSet lResultSet = preparedStatement.executeQuery()) {
                if (!lResultSet.wasNull()) {
                    List<Type> types = new ArrayList<>();
                    while (lResultSet.next()) {
                        Type type = new Type();
                        type.setTypeName(lResultSet.getString("ENUM_NAME"));
                        type.setTypeType(TypeType.e);
                        String value = lResultSet.getString("enum_value");
                        if (value != null) {
                            String[] values = value.split(",");
                            type.setValues(Arrays.asList(values));
                        }
                        types.add(type);
                    }
                    database.setTypes(types);
                }
            }



        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
