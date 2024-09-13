package org.sqlcomponents.core.model;


import org.sqlcomponents.core.model.relational.Table;
import org.sqlcomponents.core.model.relational.enums.DBType;
import org.sqlcomponents.core.model.relational.enums.Flag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * The type Entity.
 */

public class Entity {

    /**
     * The Table.
     */
    private Table table;

    /**
     * The Name.
     */
    private String name;
    /**
     * The Plural name.
     */
    private String pluralName;
    /**
     * The Bean package.
     */
    private String beanPackage;
    /**
     * The Dao package.
     */
    private String daoPackage;

    /**
     * The Orm.
     */
    private ORM orm;

    /**
     * The Properties.
     */
    private List<Property> properties;

    /**
     * Instantiates a new Entity.
     *
     * @param paramOrm   the orm
     * @param paramTable the table
     */
    public Entity(final ORM paramOrm, final Table paramTable) {
        setOrm(paramOrm);
        setTable(paramTable);
    }

    /**
     * Has java class boolean.
     *
     * @param className the class name
     * @return the boolean
     */
    public boolean hasJavaClass(final String className) {
        return orm.hasJavaClass(className);
    }

    /**
     * Has java type boolean.
     *
     * @param type the class name
     * @return the boolean
     */
    public boolean hasJavaType(final String type) {
        return this.getProperties().stream()
                .filter(property -> property.getDataType().equals(type))
                .findFirst().isPresent();
    }

    /**
     * Contains encryption boolean.
     *
     * @param property the property
     * @return the boolean
     */
    public boolean containsEncryption(final Property property) {
        String combinedKey = property.getColumn().getTableName() + "#"
                + property.getColumn().getColumnName();
        return orm.getEncryption() != null && (orm.getEncryption()
                .contains(property.getColumn().getColumnName())
                || orm.getEncryption().contains(combinedKey));
    }

    /**
     * Contains encrypted property boolean.
     *
     * @return the boolean
     */
    public boolean containsEncryptedProperty() {
        return this.getProperties().stream().filter(this::containsEncryption)
                .findFirst().isPresent();
    }

    /**
     * Contains property boolean.
     *
     * @param property the property
     * @param map      the map
     * @return the boolean
     */
    public boolean containsProperty(final Property property,
                                    final Map<String, String> map) {
        String combinedKey = property.getColumn().getTableName() + "#"
                + property.getColumn().getColumnName();
        return !(map.containsKey(property.getColumn().getColumnName())
                || map.containsKey(combinedKey));
    }

    /**
     * Gets prepared value.
     *
     * @param property the property
     * @param map      the map
     * @return the prepared value
     */
    public String getPreparedValue(final Property property,
                                   final Map<String, String> map) {
        String preparedValue = null;
        String columnName = property.getColumn().getColumnName();
        String tableNameColumnName =
                property.getColumn().getTableName() + "#" + columnName;
        String typeName = property.getColumn().getTypeName();
        DBType dbType =
                property.getEntity().getTable().getDatabase().getDbType();

        preparedValue = map.get(columnName);
        if (Objects.nonNull(preparedValue)) {
            return preparedValue.replaceAll("\"",
                    Matcher.quoteReplacement("\\\""));
        }

        preparedValue = map.get(tableNameColumnName);
        if (Objects.nonNull(preparedValue)) {
            return preparedValue.replaceAll("\"",
                    Matcher.quoteReplacement("\\\""));
        }

        if (dbType == DBType.POSTGRES && typeName.equals("xml")) {
            preparedValue = "XMLPARSE(document ?)";
        }

        return preparedValue == null ? "?" : preparedValue;
    }

    /**
     * Gets returning properties.
     *
     * @return the returning properties
     */
    public List<Property> getReturningProperties() {
        return this.getProperties().stream().filter(Property::isReturning)
                .collect(Collectors.toList());
    }


    /**
     * Gets insertable properties.
     *
     * @return the insertable properties
     */
    public List<Property> getInsertableProperties() {
        return this.getProperties().stream().filter(property -> {
            if (isFilteredIn(this.getOrm().getInsertMap(), property)) {
                return false;
            }
            return property.getColumn().isInsertable();
        }).collect(Collectors.toList());
    }

    /**
     * Gets updatable properties.
     *
     * @return the updatable properties
     */
    public List<Property> getUpdatableProperties() {
        return this.getProperties().stream().filter(property -> {
            if (isFilteredIn(this.getOrm().getUpdateMap(), property)) {
                return false;
            }
            return property.getColumn().isInsertable();
        }).collect(Collectors.toList());
    }

    /**
     * Is filtered in boolean.
     *
     * @param map      the map
     * @param property the property
     * @return the boolean
     */
    private boolean isFilteredIn(final Map<String, String> map,
                                 final Property property) {
        String combinedKey = property.getColumn().getTableName() + "#"
                + property.getColumn().getColumnName();
        return map != null
                && ((map.containsKey(property.getColumn().getColumnName())
                && map.get(property.getColumn().getColumnName()) == null)
                || (map.containsKey(combinedKey)
                && map.get(combinedKey) == null));
    }

    /**
     * Get Not Null Insertable Properties.
     *
     * @return properties must insertable properties
     */
    public List<Property> getMustInsertableProperties() {
        return this.getProperties().stream().filter(property -> {
            return property.getColumn().isInsertable()
                    && property.getColumn().getNullable() != Flag.YES;
        }).collect(Collectors.toList());
    }

    /**
     * Gets primary key properties.
     *
     * @return the primary key properties
     */
    public List<Property> getPrimaryKeyProperties() {
        return this.getProperties().stream().filter(property -> {
            return property.getColumn().isPrimaryKey();
        }).collect(Collectors.toList());
    }

    /**
     * Gets generated primary key properties.
     *
     * @return the generated primary key properties
     */
    public List<Property> getGeneratedPrimaryKeyProperties() {
        return this.getProperties().stream().filter(property -> {
            return property.getColumn().isPrimaryKey()
                    && property.getColumn().getAutoIncrement() == Flag.YES;
        }).collect(Collectors.toList());
    }

    /**
     * Gets sample distinct custom column type properties.
     *
     * @return the sample distinct custom column type properties
     */
    public List<Property> getSampleDistinctCustomColumnTypeProperties() {
        SortedSet<String> distinctColumnTypeNames =
                table.getDistinctCustomColumnTypeNames();

        List<Property> sampleDistinctCustomColumnTypeProperties =
                new ArrayList<>(distinctColumnTypeNames.size());

        distinctColumnTypeNames.stream().forEach(typeName -> {
            sampleDistinctCustomColumnTypeProperties.add(
                    this.getProperties().stream()
                            .filter(property -> property.getColumn()
                                    .getTypeName().equals(typeName)).findFirst()
                            .get());
        });

        return sampleDistinctCustomColumnTypeProperties;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(final Table theTable) {
        this.table = theTable;
    }

    public String getName() {
        return name;
    }

    public void setName(final String theName) {
        this.name = theName;
    }

    public String getPluralName() {
        return pluralName;
    }

    public void setPluralName(final String thePluralName) {
        this.pluralName = thePluralName;
    }

    public String getBeanPackage() {
        return beanPackage;
    }

    public void setBeanPackage(final String theBeanPackage) {
        this.beanPackage = theBeanPackage;
    }

    public String getDaoPackage() {
        return daoPackage;
    }

    public void setDaoPackage(final String theDaoPackage) {
        this.daoPackage = theDaoPackage;
    }

    public ORM getOrm() {
        return orm;
    }

    public void setOrm(final ORM theOrm) {
        this.orm = theOrm;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(final List<Property> theProperties) {
        this.properties = theProperties;
    }
}
