package org.sqlcomponents.core.model;

import org.sqlcomponents.core.model.relational.Column;

/**
 * The type Property.
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
     * Instantiates a new Property.
     *
     * @param entity the entity
     * @param column the column
     */
    public Property(Entity entity, Column column) {
        setEntity(entity);
        setColumn(column);
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
     * @param entity the entity
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
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
     * @param uniqueConstraintGroup the unique constraint group
     */
    public void setUniqueConstraintGroup(String uniqueConstraintGroup) {
        this.uniqueConstraintGroup = uniqueConstraintGroup;
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
     * @param column the column
     */
    public void setColumn(Column column) {
        this.column = column;
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
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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
     * @param dataType the data type
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
