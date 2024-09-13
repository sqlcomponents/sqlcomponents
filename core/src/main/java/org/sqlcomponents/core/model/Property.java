package org.sqlcomponents.core.model;

import org.sqlcomponents.core.model.relational.Column;
import org.sqlcomponents.core.model.relational.enums.Flag;

import java.util.Map;

/**
 * The type.
 */
public class Property {
    /**
     * The Entity.
     */
    private Entity entity;
    /**
     * The Column.
     */
    private Column column;
    /**
     * The Name.
     */
    private String name;
    /**
     * The Data type.
     */
    private String dataType;
    /**
     * The Unique constraint group.
     */
    private String uniqueConstraintGroup;

    /**
     * Instantiates a new.
     *
     * @param paramEntity the entity
     * @param paramColumn the column
     */
    public Property(final Entity paramEntity, final Column paramColumn) {
        setEntity(paramEntity);
        setColumn(paramColumn);
    }

    /**
     * Gets entity.
     *
     * @return the entity
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Sets entity.
     *
     * @param paramEntity the entity
     */
    public void setEntity(final Entity paramEntity) {
        this.entity = paramEntity;
    }

    /**
     * Gets unique constraint group.
     *
     * @return the unique constraint group
     */
    public String getUniqueConstraintGroup() {
        return uniqueConstraintGroup;
    }

    /**
     * Sets unique constraint group.
     *
     * @param paramUniqueConstraintGroup the unique constraint group
     */
    public void setUniqueConstraintGroup(
            final String paramUniqueConstraintGroup) {
        this.uniqueConstraintGroup = paramUniqueConstraintGroup;
    }

    /**
     * Gets column.
     *
     * @return the column
     */
    public Column getColumn() {
        return column;
    }

    /**
     * Sets column.
     *
     * @param paramColumn the column
     */
    public void setColumn(final Column paramColumn) {
        this.column = paramColumn;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param paramName the name
     */
    public void setName(final String paramName) {
        this.name = paramName;
    }

    /**
     * Gets data type.
     *
     * @return the data type
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Sets data type.
     *
     * @param paramDataType the data type
     */
    public void setDataType(final String paramDataType) {
        this.dataType = paramDataType;
    }

    /**
     * Is returning boolean.
     *
     * @return the boolean
     */
    public boolean isReturning() {
        boolean isReturning =
                getColumn().getAutoIncrement() == Flag.YES
                        || getColumn().getGeneratedColumn()
                        == Flag.YES;
        Map<String, String> insertMap =
                getEntity().getOrm().getApplication()
                        .getInsertMap();
        String mapped = insertMap
                .get(getColumn().getColumnName());
        String specificTableMapped =
                insertMap.get(String.format("%s#%s",
                        getEntity().getTable().getTableName(),
                        getColumn().getColumnName()));
        if (mapped != null || specificTableMapped != null) {
            isReturning = true;
        }
        return isReturning;
    }

}
