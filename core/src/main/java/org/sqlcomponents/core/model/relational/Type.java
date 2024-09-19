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
     * @param typeName type name
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
     * @param typeType type type
     */
    public void setTypeType(TypeType typeType) {
        this.typeType = typeType;
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
     * @param values type values
     */
    public void setValues(List<String> values) {
        this.values = values;
    }


}
