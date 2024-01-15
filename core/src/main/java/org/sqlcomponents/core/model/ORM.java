package org.sqlcomponents.core.model;


import org.sqlcomponents.core.model.relational.Database;

import java.util.List;
import java.util.Map;

/**
 * The type Orm.
 */

public class ORM {
    /**
     * Instantiates a new Orm.
     *
     * @param paramApplication the application
     */
    public ORM(final Application paramApplication) {
        setApplication(paramApplication);
    }

    /**
     * The Application.
     */
    private Application application;

    /**
     * The User name.
     */
    private String userName;

    /**
     * The Password.
     */
    private String password;

    /**
     * The Schema name.
     */
    private String schemaName;

    /**
     * The Database.
     */
    private Database database;

    /**
     * The Url.
     */
    private String url;

    /**
     * The Words map.
     */
    private Map<String, String> wordsMap;
    /**
     * The Modules map.
     */
    private Map<String, String> modulesMap;
    /**
     * The Update map.
     */
    private Map<String, String> updateMap;
    /**
     * The Insert map.
     */
    private Map<String, String> insertMap;
    /**
     * The Validation map.
     */
    private Map<String, String> validationMap;
    /**
     * The Encryption.
     */
    private List<String> encryption;

    /**
     * The Entities.
     */
    private List<Entity> entities;
    /**
     * The Services.
     */
    private List<Service> services;
    /**
     * The Methods.
     */
    private List<Method> methods;
    /**
     * The Defaults.
     */
    private List<Default> defaults;
    /**
     * The Pagination.
     */
    private boolean pagination;
    /**
     * The Method specification.
     */
    private List<String> methodSpecification;

    /**
     * The Application class loader.
     */
    private ClassLoader applicationClassLoader;

    /**
     * Sets application class loader.
     *
     * @param paramApplicationClassLoader the application class loader
     */
    public void setApplicationClassLoader(
            final ClassLoader paramApplicationClassLoader) {
        this.applicationClassLoader = paramApplicationClassLoader;
    }

    /**
     * Has java class boolean.
     *
     * @param className the class name
     * @return the boolean
     */
    public boolean hasJavaClass(final String className) {
        if (applicationClassLoader != null) {
            try {
                applicationClassLoader.loadClass(className);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(final Application theApplication) {
        this.application = theApplication;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String theUserName) {
        this.userName = theUserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String thePassword) {
        this.password = thePassword;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(final String theSchemaName) {
        this.schemaName = theSchemaName;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(final Database theDatabase) {
        this.database = theDatabase;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String theUrl) {
        this.url = theUrl;
    }

    public Map<String, String> getWordsMap() {
        return wordsMap;
    }

    public void setWordsMap(final Map<String, String> theWordsMap) {
        this.wordsMap = theWordsMap;
    }

    public Map<String, String> getModulesMap() {
        return modulesMap;
    }

    public void setModulesMap(final Map<String, String> theModulesMap) {
        this.modulesMap = theModulesMap;
    }

    public Map<String, String> getUpdateMap() {
        return updateMap;
    }

    public void setUpdateMap(final Map<String, String> theUpdateMap) {
        this.updateMap = theUpdateMap;
    }

    public Map<String, String> getInsertMap() {
        return insertMap;
    }

    public void setInsertMap(final Map<String, String> theInsertMap) {
        this.insertMap = theInsertMap;
    }

    public Map<String, String> getValidationMap() {
        return validationMap;
    }

    public void setValidationMap(final Map<String, String> theValidationMap) {
        this.validationMap = theValidationMap;
    }

    public List<String> getEncryption() {
        return encryption;
    }

    public void setEncryption(final List<String> theEncryption) {
        this.encryption = theEncryption;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(final List<Entity> theEntities) {
        this.entities = theEntities;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(final List<Service> theServices) {
        this.services = theServices;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(final List<Method> theMethods) {
        this.methods = theMethods;
    }

    public List<Default> getDefaults() {
        return defaults;
    }

    public void setDefaults(final List<Default> theDefaults) {
        this.defaults = theDefaults;
    }

    public boolean isPagination() {
        return pagination;
    }

    public void setPagination(final boolean thePagination) {
        this.pagination = thePagination;
    }

    public List<String> getMethodSpecification() {
        return methodSpecification;
    }

    public void setMethodSpecification(final List<String>
                                               theMethodSpecification) {
        this.methodSpecification = theMethodSpecification;
    }

    public ClassLoader getApplicationClassLoader() {
        return applicationClassLoader;
    }
}
