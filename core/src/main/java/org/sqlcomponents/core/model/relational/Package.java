package org.sqlcomponents.core.model.relational;


import java.util.List;

public class Package {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String name;
    private String remarks;
    private List<Procedure> functions;

    public List<Procedure> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Procedure> functions) {
        this.functions = functions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
