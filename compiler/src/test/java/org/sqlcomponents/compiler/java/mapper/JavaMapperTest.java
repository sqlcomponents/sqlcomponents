package org.sqlcomponents.compiler.java.mapper;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.sqlcomponents.compiler.java.util.CompilerTestUtil;
import org.sqlcomponents.core.crawler.Crawler;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.model.relational.Database;
import java.io.IOException;
import java.sql.SQLException;
import java.net.InetAddress;
import static org.junit.jupiter.api.Assertions.*;

class JavaMapperTest {

    private final Database database;
    private final JavaMapper javaMapper;

    JavaMapperTest() throws IOException, SQLException {
        Application application = CompilerTestUtil.getApplication();
        this.database = new Crawler(application).getDatabase();
        this.javaMapper = new JavaMapper(application);
    }

    @Test
    void getDataType() throws Exception {
//        assertEquals(UUID.class, Class.forName(getDataType("a_uuid")), "Type Mismatch");
        assertEquals(InetAddress.class, Class.forName(getDataType("a_cidr")), "Type Mismatch");
    }

    @NotNull
    private String getDataType(final String columnName) {
        return javaMapper.getDataType(database.getTables().stream()
                .filter(table -> table.getTableName().equals("raja")).findFirst()
                .get().getColumns().stream()
                .filter(column -> column.getColumnName().equals(columnName)).findFirst().get());
    }
}
