package org.sqlcomponents.core.model;

import lombok.Getter;
import lombok.Setter;
import org.sqlcomponents.core.model.relational.Table;
import org.sqlcomponents.core.model.relational.enums.DBType;
import org.sqlcomponents.core.model.relational.enums.Flag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.regex.Matcher;
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

    public boolean containsProperty(final Property property, final Map<String, String> map) {
        String combinedKey = property.getColumn().getTableName() + "#" + property.getColumn().getColumnName();
        return !(map.containsKey(property.getColumn().getColumnName()) || map.containsKey(combinedKey));
    }

    public String getPreparedValue(final Property property, final Map<String, String> map) {
        String preparedValue = null;
        if ((preparedValue = map.get(property.getColumn().getColumnName())) != null) {
            return preparedValue.replaceAll("\"", Matcher.quoteReplacement("\\\""));
        } else if ((preparedValue = map
                .get(property.getColumn().getTableName() + "#" + property.getColumn().getColumnName())) != null) {
            return preparedValue.replaceAll("\"", Matcher.quoteReplacement("\\\""));
        } else {
            if (property.getEntity().getTable().getDatabase().getDbType() == DBType.POSTGRES) {
                // property.column.typeName == 'xml'
                if (property.getColumn().getTypeName().equals("xml")) {
                    preparedValue = "XMLPARSE(document ?)";
                }
            }
        }
        return preparedValue == null ? "?" : preparedValue;
    }

    public List<Property> getInsertableProperties() {
        return this.getProperties().stream().filter(property -> {
            if (isFilteredIn(this.getOrm().getInsertMap(), property)) {
                return false;
            }
            return property.getColumn().isInsertable();
        }).collect(Collectors.toList());
    }

    public List<Property> getUpdatableProperties() {
        return this.getProperties().stream().filter(property -> {
            if (isFilteredIn(this.getOrm().getUpdateMap(), property)) {
                return false;
            }
            return property.getColumn().isInsertable();
        }).collect(Collectors.toList());
    }

    private boolean isFilteredIn(final Map<String, String> map, final Property property) {
        String combinedKey = property.getColumn().getTableName() + "#" + property.getColumn().getColumnName();
        return map != null && ((map.containsKey(property.getColumn().getColumnName())
                && map.get(property.getColumn().getColumnName()) == null)
                || (map.containsKey(combinedKey) && map.get(combinedKey) == null));
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
