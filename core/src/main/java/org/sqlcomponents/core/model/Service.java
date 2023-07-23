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
     * @param paramMethods the methods
     */
    public void setMethods(final List<Method> paramMethods) {
        this.methods = paramMethods;
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
     * @param paramServiceName the service name
     */
    public void setServiceName(final String paramServiceName) {
        this.serviceName = paramServiceName;
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
     * @param paramDaoPackage the dao package
     */
    public void setDaoPackage(final String paramDaoPackage) {
        this.daoPackage = paramDaoPackage;
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
     * @param paramDbPackage the db package
     */
    public void setPackage(final Package paramDbPackage) {
        this.dbPackage = paramDbPackage;
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
     * @param paramFunctions the functions
     */
    public void setFunctions(final List<Procedure> paramFunctions) {
        dbPackage.setFunctions(paramFunctions);
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
     * @param paramName the name
     */
    public void setName(final String paramName) {
        dbPackage.setName(paramName);
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
     * @param paramRemarks the remarks
     */
    public void setRemarks(final String paramRemarks) {
        dbPackage.setRemarks(paramRemarks);
    }
    /**
     * Returns the name of the package.
     */
    @Override
    public String toString() {
        return getName();
    }
}
