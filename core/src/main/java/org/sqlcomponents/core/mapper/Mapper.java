package org.sqlcomponents.core.mapper;

import org.sqlcomponents.core.crawler.Crawler;
import org.sqlcomponents.core.model.Application;
import org.sqlcomponents.core.model.Entity;
import org.sqlcomponents.core.model.Method;
import org.sqlcomponents.core.model.ORM;
import org.sqlcomponents.core.model.Property;
import org.sqlcomponents.core.model.Service;
import org.sqlcomponents.core.model.relational.Column;
import org.sqlcomponents.core.model.relational.Database;
import org.sqlcomponents.core.model.relational.Package;
import org.sqlcomponents.core.model.relational.Procedure;
import org.sqlcomponents.core.model.relational.Table;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Mapper.
 */
public abstract class Mapper {
    /**
     * The constant DOT.
     */
    private static final String DOT = ".";

    /**
     * The Application.
     */
    private final Application application;

    /**
     * Instantiates a new Mapper.
     *
     * @param aApplication the a application
     */
    public Mapper(final Application aApplication) {
        application = aApplication;
    }

    /**
     * Gets data type.
     *
     * @param aColumn the a column
     * @return the data type
     */
    public abstract String getDataType(Column aColumn);

    /**
     * Gets orm.
     *
     * @return ORM orm
     * @throws SQLException the sql exception
     */
    public ORM getOrm() throws SQLException {
        ORM orm = application.getOrm();

        Database database = new Crawler(application).getDatabase();
        application.getOrm().setDatabase(database);

        orm.setEntities(getEntities());
        orm.setMethods(getMethods());
        orm.setServices(getServices());

        return orm;
    }

    /**
     * Gets method.
     *
     * @param aProcedure the a procedure
     * @return the method
     */
    private Method getMethod(final Procedure aProcedure) {
        List<Property> lProperties =
                new ArrayList<>(aProcedure.getParameters().size());
        Method lMethod = new Method(aProcedure);

        for (Column column : aProcedure.getParameters()) {
            lProperties.add(getProperty(null, column));
        }
        return lMethod;
    }

    /**
     * Gets methods.
     *
     * @return the methods
     */
    private List<Method> getMethods() {
        Database database = application.getOrm().getDatabase();
        ArrayList<Method> methods = new ArrayList<>();
        if (database.getFunctions() != null) {
            for (Procedure function : database.getFunctions()) {
                methods.add(getMethod(function));
            }
        }
        return methods;
    }

    /**
     * Gets services.
     *
     * @return the services
     */
    private List<Service> getServices() {
        ArrayList<Service> services = new ArrayList<>();
        if (application.getOrm().getDatabase().getPackages() != null) {
            Service service;
            for (Package package1 : application.getOrm().getDatabase()
                    .getPackages()) {
                service = new Service();
                service.setPackage(package1);
                service.setServiceName(getServiceName(service.getName()));
                service.setDaoPackage(getDaoPackage(service.getName()));
                service.setMethods(new ArrayList<>());
                for (Procedure function : package1.getFunctions()) {
                    service.getMethods().add(getMethod(function));
                }
                services.add(service);
            }
        }

        return services;
    }

    /**
     * Gets property.
     *
     * @param aEntity the a entity
     * @param aColumn the a column
     * @return the property
     */
    private Property getProperty(final Entity aEntity, final Column aColumn) {
        if (aColumn != null) {
            Property property = new Property(aEntity, aColumn);
            if (aColumn.getColumnName() != null) {
                property.setName(getPropertyName(aColumn.getColumnName()));
            }
            property.setUniqueConstraintGroup(getEntityName(
                    property.getColumn().getUniqueConstraintName()));
            property.setDataType(getDataType(aColumn));
            return property;
        }
        return null;
    }

    /**
     * Gets entities.
     *
     * @return the entities
     */
    private List<Entity> getEntities() {
        Database lDatabase = application.getOrm().getDatabase();
        ArrayList<Entity> lEntities =
                new ArrayList<>(lDatabase.getTables().size());

        List<Property> lProperties;
        Entity lEntity;

        for (Table table : lDatabase.getTables()) {
            lEntity = new Entity(application.getOrm(), table);
            lEntity.setName(getEntityName(table.getTableName()));
            lEntity.setPluralName(getPluralName(lEntity.getName()));
            lEntity.setDaoPackage(getDaoPackage(table.getTableName()));
            lEntity.setBeanPackage(getBeanPackage(table.getTableName()));
            lProperties = new ArrayList<>(table.getColumns().size());

            for (Column column : table.getColumns()) {
                lProperties.add(getProperty(lEntity, column));
            }

            lEntity.setProperties(lProperties);
            lEntities.add(lEntity);
        }
        return lEntities;
    }

    /**
     * Gets service name.
     *
     * @param aPackageName the a package name
     * @return the service name
     */
    protected String getServiceName(final String aPackageName) {
        if (aPackageName != null) {
            StringBuilder lStringBuilder = new StringBuilder();
            String[] bRelationalWords =
                    aPackageName.split(application.getDatabaseWordSeparator());

            for (final String aRelationalWord : bRelationalWords) {
                lStringBuilder.append(
                        toTileCase(getObjectOrientedWord(aRelationalWord)));
            }

            lStringBuilder.append("Service");
            return lStringBuilder.toString();
        }
        return null;
    }

    /**
     * Gets entity name.
     *
     * @param aTableName the a table name
     * @return the entity name
     */
    protected String getEntityName(final String aTableName) {
        if (aTableName != null) {
            StringBuilder lStringBuilder = new StringBuilder();
            String[] bRelationalWords =
                    aTableName.split(application.getDatabaseWordSeparator());
            for (final String aRelationalWord : bRelationalWords) {
                lStringBuilder.append(
                        toTileCase(getObjectOrientedWord(aRelationalWord)));
            }

            return lStringBuilder.toString();
        }
        return null;
    }

    /**
     * Gets object oriented word.
     *
     * @param aRelationalWord the a relational word
     * @return the object oriented word
     */
    protected String getObjectOrientedWord(final String aRelationalWord) {
        String lObjectOrientedWord = null;
        if (application.getWordsMap() != null) {
            for (String bRelationalWordKey : application.getWordsMap()
                    .keySet()) {
                if (aRelationalWord.equalsIgnoreCase(bRelationalWordKey)) {
                    lObjectOrientedWord =
                            application.getWordsMap().get(bRelationalWordKey);
                }
            }
        }
        return lObjectOrientedWord == null ? aRelationalWord
                : lObjectOrientedWord;
    }

    /**
     * Gets plural name.
     *
     * @param aEntityName the a entity name
     * @return the plural name
     */
    protected String getPluralName(final String aEntityName) {
        String lPluralName = null;
        Map<String, String> lPluralMap = application.getPluralMap();
        String lPluralValue;
        String lToUpperCase = aEntityName.toUpperCase();
        if (lPluralMap != null && lPluralMap.size() != 0) {
            for (String pluralKey : lPluralMap.keySet()) {
                lPluralValue = lPluralMap.get(pluralKey).toUpperCase();
                if (lToUpperCase.endsWith(pluralKey.toUpperCase())) {
                    int lastIndex =
                            lToUpperCase.lastIndexOf(pluralKey.toUpperCase());
                    lPluralName = aEntityName.substring(0, lastIndex)
                            + toTileCase(lPluralValue);
                    break;
                }
            }
        }

        if (lPluralName == null) {
            lPluralName = aEntityName + "s";
        }

        return lPluralName;
    }

    /**
     * Gets package.
     *
     * @param aTableName  the a table name
     * @param aIdentifier the a identifier
     * @return the package
     */
    private String getPackage(final String aTableName,
                              final String aIdentifier) {
        StringBuilder lBuffer = new StringBuilder();
        if (application.getRootPackage() != null) {
            lBuffer.append(application.getRootPackage());
        }

        String moduleName = getModuleName(aTableName);

        if (application.isModulesFirst()) {
            if (moduleName != null && moduleName.trim().length() != 0) {
                lBuffer.append(DOT);
                lBuffer.append(moduleName.trim());
            }
            if (aIdentifier != null && aIdentifier.trim().length() != 0) {
                lBuffer.append(DOT);
                lBuffer.append(aIdentifier.trim());
            }
        } else {
            if (aIdentifier != null && aIdentifier.trim().length() != 0) {
                lBuffer.append(DOT);
                lBuffer.append(aIdentifier.trim());
            }

            if (moduleName != null && moduleName.trim().length() != 0) {
                lBuffer.append(DOT);
                lBuffer.append(moduleName.trim());
            }
        }
        return lBuffer.toString().toLowerCase();
    }

    /**
     * Gets dao package.
     *
     * @param aTableName the a table name
     * @return the dao package
     */
    protected String getDaoPackage(final String aTableName) {
        return getPackage(aTableName, "store");
    }

    /**
     * Gets bean package.
     *
     * @param aTableName the a table name
     * @return the bean package
     */
    protected String getBeanPackage(final String aTableName) {
        return getPackage(aTableName, "model");
    }

    /**
     * Gets module name.
     *
     * @param aTableName the a table name
     * @return the module name
     */
    protected String getModuleName(final String aTableName) {
        String[] lDbWords =
                aTableName.split(application.getDatabaseWordSeparator());
        Map<String, String> lModulesMap = application.getModulesMap();
        if (lModulesMap != null) {
            for (String moduleKey : lModulesMap.keySet()) {
                for (int i = lDbWords.length - 1; i >= 0; i--) {
                    if (lDbWords[i].equalsIgnoreCase(moduleKey)) {
                        return lModulesMap.get(moduleKey);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets property name.
     *
     * @param aColumnName the a column name
     * @return the property name
     */
    protected String getPropertyName(final String aColumnName) {
        StringBuilder lStringBuilder = new StringBuilder();
        String[] lRelationalWords =
                aColumnName.split(application.getDatabaseWordSeparator());
        int lRelationalWordsCount = lRelationalWords.length;
        for (int index = 0; index < lRelationalWordsCount; index++) {
            if (index == 0) {
                lStringBuilder.append(getObjectOrientedWord(
                        lRelationalWords[index]).toLowerCase());
            } else {
                lStringBuilder.append(toTileCase(
                        getObjectOrientedWord(lRelationalWords[index])));
            }
        }
        return lStringBuilder.toString();
    }

    /**
     * To tile case string.
     *
     * @param aWord the a word
     * @return the string
     */
    protected String toTileCase(final String aWord) {
        char[] lCharArray = aWord.toLowerCase().toCharArray();
        int lLetterCount = lCharArray.length;
        for (int index = 0; index < lLetterCount; index++) {
            if (index == 0) {
                lCharArray[index] = Character.toUpperCase(lCharArray[index]);
            } else {
                lCharArray[index] = Character.toLowerCase(lCharArray[index]);
            }
        }
        return new String(lCharArray);
    }
}
