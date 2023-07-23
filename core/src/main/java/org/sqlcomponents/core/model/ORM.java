package org.sqlcomponents.core.model;

import lombok.Getter;
import lombok.Setter;
import org.sqlcomponents.core.model.relational.Database;

import java.util.List;
import java.util.Map;

/**
 * The type Orm.
 */
@Getter
@Setter
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
}
