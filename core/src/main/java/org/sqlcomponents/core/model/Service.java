package org.sqlcomponents.core.model;

import org.sqlcomponents.core.model.relational.Package;
import org.sqlcomponents.core.model.relational.Procedure;

import java.util.List;

/**
 * The type Service.
 */
public class Service {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The Db package.
     */
    private Package dbPackage;
    /**
     * The Service name.
     */
    private String serviceName;
    /**
     * The Dao package.
     */
    private String daoPackage;
    /**
     * The Methods.
     */
    private List<Method> methods;

    /**
     * Gets methods.
     *
     * @return the methods
     */
    public List<Method> getMethods() {
        return methods;
    }

    /**
     * Sets methods.
     *
     * @param methods the methods
     */
    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    /**
     * Gets service name.
     *
     * @return the service name
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Sets service name.
     *
     * @param serviceName the service name
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Gets dao package.
     *
     * @return the dao package
     */
    public String getDaoPackage() {
        return daoPackage;
    }

    /**
     * Sets dao package.
     *
     * @param daoPackage the dao package
     */
    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    /**
     * Gets package.
     *
     * @return the package
     */
    public Package getPackage() {
        return dbPackage;
    }

    /**
     * Sets package.
     *
     * @param dbPackage the db package
     */
    public void setPackage(Package dbPackage) {
        this.dbPackage = dbPackage;
    }

    /**
     * Gets functions.
     *
     * @return the functions
     */
    public List<Procedure> getFunctions() {
        return dbPackage.getFunctions();
    }

    /**
     * Sets functions.
     *
     * @param functions the functions
     */
    public void setFunctions(List<Procedure> functions) {
        dbPackage.setFunctions(functions);
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return dbPackage.getName();
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        dbPackage.setName(name);
    }

    /**
     * Gets remarks.
     *
     * @return the remarks
     */
    public String getRemarks() {
        return dbPackage.getRemarks();
    }

    /**
     * Sets remarks.
     *
     * @param remarks the remarks
     */
    public void setRemarks(String remarks) {
        dbPackage.setRemarks(remarks);
    }

    @Override
    public String toString() {
        return getName();
    }
}
