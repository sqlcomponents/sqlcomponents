package org.sqlcomponents.core.mapper;

import org.sqlcomponents.core.crawler.Crawler;
import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.core.model.*;
import org.sqlcomponents.core.model.Package;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Mapper {

    String DB_WORD_SEPARATOR ="_";

    public static ORM getOrm(Application application) throws ScubeException {
        return null;
    }

    public abstract String[] getValidDataTypes(String sqlType, int size,
                                               int precision);

    public abstract String getValidDataType(String sqlType, int size,
                                            int precision);

    public ORM getOrm(Application application, Crawler crawler) throws ScubeException {

        ORM orm = application.getOrm();

        if (application.getOrm().getSchema() == null || application.isOnline()) {
            Database database = crawler.getDatabase();
            application.getOrm().setSchema(database);
        }

        orm.setEntities(getEntities(application));
        orm.setMethods(getMethods(application));
        orm.setServices(getServices(application));
        return orm;
    }

    private Method getMethod(Function function, Application application) {
        List<Property> properties = new ArrayList<Property>(function
                .getParameters().size());
        Method method = new Method(function);
        method.setName(getPropertyName(application, function.getFunctionName()));

        for (Column column : function.getParameters()) {
            properties.add(getProperty(application, column));
        }
        method.setInputParameters(properties);

        method.setOutputProperty(getProperty(application, function.getOutput()));
        return method;
    }

    private List<Method> getMethods(Application application) {
        Database database = application.getSchema();
        ArrayList<Method> methods = new ArrayList<Method>();
        if (database.getFunctions() != null) {
            for (Function function : database.getFunctions()) {
                methods.add(getMethod(function, application));
            }
        }
        return methods;
    }

    private List<Service> getServices(Application application) {
        ArrayList<Service> services = new ArrayList<Service>();
        if (application
                .getSchema().getPackages() != null) {
            Service service = null;
            for (Package package1 : application.getSchema().getPackages()) {
                service = new Service();
                service.setPackage(package1);
                service.setServiceName(getServiceName(application, service.getName()));
                service.setDaoPackage(getDaoPackage(application, service.getName()));
                service.setMethods(new ArrayList<Method>());
                for (Function function : package1.getFunctions()) {
                    service.getMethods().add(getMethod(function, application));
                }
                services.add(service);
            }
        }

        return services;
    }

    private Property getProperty(Application application, Column column) {
        if (column != null) {
            Property property = new Property(column);
            if (column.getColumnName() != null) {
                property.setName(getPropertyName(application, column
                        .getColumnName()));
            }
            property.setUniqueConstraintGroup(getEntityName(application,
                    property.getUniqueConstraintName()));
            property.setDataType(getValidDataType(property.getTypeName(),
                    property.getSize(), property.getDecimalDigits()));
            return property;
        }
        return null;

    }

    private List<Entity> getEntities(Application application) {

        Database database = application.getSchema();

        ArrayList<Entity> entities = new ArrayList<Entity>(database.getTables()
                .size());

        List<Property> properties;
        Entity entity;

        for (Table table : database.getTables()) {
            entity = new Entity(table);
            entity.setName(getEntityName(application, table.getTableName()));
            entity.setPluralName(getPluralName(application, entity.getName()));
            entity
                    .setDaoPackage(getDaoPackage(application, table
                            .getTableName()));
            entity.setBeanPackage(getBeanPackage(application, table
                    .getTableName()));


            properties = new ArrayList<Property>(table.getColumns().size());

            for (Column column : table.getColumns()) {
                properties.add(getProperty(application, column));
            }
            entity.setProperties(properties);
            entities.add(entity);
        }
        return entities;
    }

    private String getSequenceName(Application application, String entityName) {
        List<String> sequences = application.getSchema().getSequences();
        if (sequences != null) {
            for (String sequence : sequences) {
                if (entityName.equals(getEntityName(application, sequence))) {
                    return sequence;
                }
            }
        }

        return null;
    }

    protected String getServiceName(Application application, String packageName) {
        if (packageName != null) {
            StringBuffer buffer = new StringBuffer();
            String[] relationalWords = packageName
                    .split(DB_WORD_SEPARATOR);
            int relationalWordsCount = relationalWords.length;
            for (int index = 0; index < relationalWordsCount; index++) {
                buffer.append(toTileCase(getObjectOrientedWord(application,
                        relationalWords[index])));
            }
            buffer.append("Service");
            return buffer.toString();
        }
        return null;
    }

    protected String getEntityName(Application application, String tableName) {
        if (tableName != null) {
            StringBuffer buffer = new StringBuffer();
            String[] relationalWords = tableName
                    .split(DB_WORD_SEPARATOR);
            int relationalWordsCount = relationalWords.length;
            for (int index = 0; index < relationalWordsCount; index++) {
                buffer.append(toTileCase(getObjectOrientedWord(application,
                        relationalWords[index])));
            }
            if (application.getBeanSuffix() != null && application.getBeanSuffix().trim().length() != 0) {
                buffer.append(application.getBeanSuffix().trim());
            }
            return buffer.toString();
        }
        return null;
    }

    protected String getObjectOrientedWord(Application application,
                                           String relationalWord) {
        String objectOrientedWord = null;
        if (application.getWordsMap() != null) {
            for (String relationalWordKey : application.getWordsMap().keySet()) {
                if (relationalWord.equalsIgnoreCase(relationalWordKey)) {
                    objectOrientedWord = application.getWordsMap().get(
                            relationalWordKey);
                }

            }
        }
        return objectOrientedWord == null ? relationalWord : objectOrientedWord;

    }

    protected String getPluralName(Application application, String entityName) {
        String pluralName = null;
        HashMap<String, String> pluralMap = application.getPluralMap();
        String pluralValue;
        String capsEntityName = entityName.toUpperCase();
        if (pluralMap != null && pluralMap.size() != 0) {
            for (String pluralKey : pluralMap.keySet()) {
                pluralValue = pluralMap.get(pluralKey).toUpperCase();
                if (capsEntityName.endsWith(pluralKey.toUpperCase())) {
                    int lastIndex = capsEntityName.lastIndexOf(pluralKey
                            .toUpperCase());
                    pluralName = entityName.substring(0, lastIndex)
                            + toTileCase(pluralValue);
                    break;
                }
            }
        }
        if (pluralName == null) {
            pluralName = entityName + "s";
        }
        return pluralName;
    }


    private String getPackage(Application application, String tableName, String identifier) {
        StringBuffer buffer = new StringBuffer();

        if (application.getRootPackage() != null) {
            buffer.append(application.getRootPackage());
        }

        String moduleName = getModuleName(application, tableName);

        if (application.isModulesFirst()) {
            if (moduleName != null &&
                    moduleName.trim().length() != 0) {
                buffer.append(".");
                buffer.append(moduleName.trim());
            }
            if (identifier != null
                    && identifier.trim().length() != 0) {
                buffer.append(".");
                buffer.append(identifier.trim());
            }
        } else {
            if (identifier != null
                    && identifier.trim().length() != 0) {
                buffer.append(".");
                buffer.append(identifier.trim());
            }
            if (moduleName != null &&
                    moduleName.trim().length() != 0) {
                buffer.append(".");
                buffer.append(moduleName.trim());
            }
        }
        return buffer.toString().toLowerCase();
    }

    protected String getDaoPackage(Application application, String tableName) {
        return getPackage(application, tableName, application.getDaoIdentifier());
    }

    protected String getBeanPackage(Application application, String tableName) {
        return getPackage(application, tableName, application.getBeanIdentifier());
    }

    protected String getModuleName(Application application, String tableName) {

        String[] dbWords = tableName
                .split(DB_WORD_SEPARATOR);
        HashMap<String, String> modulesMap = application.getModulesMap();
        if (modulesMap != null) {
            for (String moduleKey : modulesMap.keySet()) {
                for (int i = dbWords.length - 1; i >= 0; i--) {
                    if (dbWords[i].toUpperCase()
                            .equals(moduleKey.toUpperCase())) {
                        return modulesMap.get(moduleKey);

                    }
                }
            }
        }
        return null;
    }

    protected String getPropertyName(Application application, String columnName) {
        StringBuffer buffer = new StringBuffer();
        String[] relationalWords = columnName
                .split(DB_WORD_SEPARATOR);
        int relationalWordsCount = relationalWords.length;
        for (int index = 0; index < relationalWordsCount; index++) {
            if (index == 0) {
                buffer.append(getObjectOrientedWord(application,
                        relationalWords[index]).toLowerCase());
            } else {
                buffer.append(toTileCase(getObjectOrientedWord(application,
                        relationalWords[index])));
            }
        }
        return buffer.toString();
    }

    protected String toTileCase(String word) {
        char[] wordTemp = word.toLowerCase().toCharArray();
        int letterCount = wordTemp.length;
        for (int index = 0; index < letterCount; index++) {
            if (index == 0) {
                wordTemp[index] = Character.toUpperCase(wordTemp[index]);
            } else {
                wordTemp[index] = Character.toLowerCase(wordTemp[index]);
            }
        }
        return new String(wordTemp);
    }
}