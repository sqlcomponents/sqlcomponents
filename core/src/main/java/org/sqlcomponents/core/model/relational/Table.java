package org.sqlcomponents.core.model.relational;

import lombok.Getter;
import lombok.Setter;
import org.sqlcomponents.core.model.relational.enumeration.TableType;

import java.util.List;

@Setter
@Getter
public class Table  {

    private String tableName;
    private String sequenceName;
    private String categoryName;
    private String schemaName;
    private TableType tableType;
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

    @Override
    public String toString() {
        return tableName + "::" + tableType ;
    }
}
