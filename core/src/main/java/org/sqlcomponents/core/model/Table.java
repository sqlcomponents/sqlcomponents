package org.sqlcomponents.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Table  {

    public static final String TYPE_VIEW = "VIEW";
    public static final String TYPE_MVIEW = "MVIEW";
    public static final String TYPE_TABLE = "TABLE";

    private String tableName;

    private String sequenceName;

    private String categoryName;

    private String schemaName;

    private String tableType;

    private String remarks;

    private String categoryType;
    private String schemaType;
    private String nameType;
    private String selfReferencingColumnName;
    private String referenceGeneration;
    private List<Column> columns;

    private final Database database;

    public Table(final Database database) {
        this.database = database;
    }


}
