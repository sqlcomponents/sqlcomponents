package org.sqlcomponents.core.model;

import lombok.Getter;
import lombok.Setter;
import org.sqlcomponents.core.model.relational.Table;
import org.sqlcomponents.core.model.relational.enums.Flag;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
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

    public boolean hasJavaClass(String className) {
        return orm.hasJavaClass(className);
    }

    public List<Property> getInsertableProperties() {
        return this.getProperties().stream().filter(property -> {
            if (this.getOrm().getInsertMap() != null
                    && this.getOrm().getInsertMap().get(property.getColumn().getColumnName()) != null
                    && this.getOrm().getInsertMap().get(property.getColumn().getColumnName()).trim().length() == 0) {
                return false;
            }
            return property.getColumn().isInsertable();
        }).collect(Collectors.toList());
    }

    public List<Property> getUpdatableProperties() {
        return this.getProperties().stream().filter(property -> {
            if (this.getOrm().getUpdateMap() != null
                    && this.getOrm().getUpdateMap().get(property.getColumn().getColumnName()) != null
                    && this.getOrm().getUpdateMap().get(property.getColumn().getColumnName()).trim().length() == 0) {
                return false;
            }
            return property.getColumn().isInsertable();
        }).collect(Collectors.toList());
    }

    /**
     * Get Not Null Insertable Properties
     *
     * @return properties
     */
    public List<Property> getMustInsertableProperties() {
        return this.getProperties().stream().filter(property -> {
            return property.getColumn().isInsertable() && property.getColumn().getNullable() != Flag.YES;
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

    public List<Property> getSampleDistinctCustomColumnTypeProperties() {
        SortedSet<String> distinctColumnTypeNames = table.getDistinctCustomColumnTypeNames();

        List<Property> sampleDistinctCustomColumnTypeProperties = new ArrayList<>(distinctColumnTypeNames.size());

        distinctColumnTypeNames.stream().forEach(typeName -> {
            sampleDistinctCustomColumnTypeProperties.add(this.getProperties().stream()
                    .filter(property -> property.getColumn().getTypeName().equals(typeName)).findFirst().get());
        });

        return sampleDistinctCustomColumnTypeProperties;
    }
}
