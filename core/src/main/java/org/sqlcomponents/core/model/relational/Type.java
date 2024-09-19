package org.sqlcomponents.core.model.relational;

import org.sqlcomponents.core.model.relational.enums.TypeType;

import java.util.List;

public class Type {
    /**
     * Type name.
     */
    private String typeName;
    /**
     * Type type.
     */
    private TypeType typeType;
    /**
     * Type values.
     */
    private List<String> values;

    /**
     * get type name.
     * @return String.
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Set type name.
     * @param aTypeName type name
     */
    public void setTypeName(final String aTypeName) {
        this.typeName = aTypeName;
    }

    /**
     * Get Type type.
     * @return TypeType
     */
    public TypeType getTypeType() {
        return typeType;
    }

    /**
     * Set Type Type.
     * @param aTypeType type type
     */
    public void setTypeType(final TypeType aTypeType) {
        this.typeType = aTypeType;
    }

    /**
     * Get Values.
     * @return List<String>
     */
    public List<String> getValues() {
        return values;
    }

    /**
     * Set List of values.
     * @param aValues type values
     */
    public void setValues(final List<String> aValues) {
        this.values = aValues;
    }


}
