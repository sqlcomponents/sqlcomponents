package org.sqlcomponents.core.model;


import lombok.Getter;
import lombok.Setter;
import org.sqlcomponents.core.model.relational.Table;
import org.sqlcomponents.core.model.relational.enumeration.Flag;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Entity {

    private Table table;

    private String name;
    private String pluralName;
    private String beanPackage;
    private String daoPackage;

    private ORM orm;

    private List<Property> properties;

    public Entity(ORM orm, Table table) {
        setOrm(orm);
        setTable(table);
    }

    public List<String> getDistinctColumnTypeNames() {
        return this.getProperties().stream()
                .map(property -> property.getColumn().getTypeName())
                .distinct().collect(Collectors.toList());
    }

    public List<Property> getInsertableProperties() {
        return this.getProperties().stream().filter(property -> {
            return property.getColumn().isInsertable();
        }).collect(Collectors.toList());
    }

    /**
     * Get Not Null Insertable Properties
     * @return properties
     */
    public List<Property> getMustInsertableProperties() {
        return this.getProperties().stream().filter(property -> {
            return property.getColumn().isInsertable()
                    && property.getColumn().getNullable() !=  Flag.YES;
        }).collect(Collectors.toList());
    }

    public List<Property> getPrimaryKeyProperties() {
        return this.getProperties().stream().filter(property -> {
            return property.getColumn().isPrimaryKey();
        }).collect(Collectors.toList());
    }

    public List<Property> getGeneratedPrimaryKeyProperties() {
        return this.getProperties().stream().filter(property -> {
            return property.getColumn().isPrimaryKey() && property.getColumn().getAutoIncrement() == Flag.YES;
        }).collect(Collectors.toList());
    }
}
