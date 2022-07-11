package org.sqlcomponents.core.mapper;

import org.sqlcomponents.core.crawler.Crawler;
import org.sqlcomponents.core.exception.SQLComponentsException;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Mapper
{

    public abstract String getDataType(Column column);

    /**
     * @param application
     * @param crawler
     * @return ORM
     * @throws SQLComponentsException
     */
    public ORM getOrm(final Application application, final Crawler crawler)
	    throws SQLComponentsException
    {
	ORM orm = application.getOrm();

	if (application.getOrm().getDatabase() == null || application.isOnline())
	{
	    Database database = crawler.getDatabase(application);
	    application.getOrm().setDatabase(database);
	}

	orm.setEntities(getEntities(application));
	orm.setMethods(getMethods(application));
	orm.setServices(getServices(application));
	return orm;
    }

    private Method getMethod(final Procedure function, final Application application)
    {
	List<Property> properties = new ArrayList<Property>(function.getParameters().size());
	Method method = new Method(function);
	method.setName(getPropertyName(application, function.getFunctionName()));

	for (Column column : function.getParameters())
	{
	    properties.add(getProperty(application, null, column));
	}
	method.setInputParameters(properties);

	method.setOutputProperty(getProperty(application, null,
					     function.getOutput()));
	return method;
    }

    private List<Method> getMethods(final Application application)
    {
	Database database = application.getOrm().getDatabase();
	ArrayList<Method> methods = new ArrayList<Method>();
	if (database.getFunctions() != null)
	{
	    for (Procedure function : database.getFunctions())
	    {
		methods.add(getMethod(function, application));
	    }
	}
	return methods;
    }

    private List<Service> getServices(final Application application)
    {
	ArrayList<Service> services = new ArrayList<Service>();
	if (application
		    .getOrm().getDatabase().getPackages() != null)
	{
	    Service service = null;
	    for (Package package1 : application.getOrm()
					       .getDatabase().getPackages())
	    {
		service = new Service();
		service.setPackage(package1);
		service.setServiceName(getServiceName(application,
						      service.getName()));
		service.setDaoPackage(getDaoPackage(application,
						    service.getName()));
		service.setMethods(new ArrayList<Method>());
		for (Procedure function : package1.getFunctions())
		{
		    service.getMethods().add(getMethod(function,
						       application));
		}
		services.add(service);
	    }
	}

	return services;
    }

    private Property getProperty(final Application application,
				 final Entity entity, final Column column)
    {
	if (column != null)
	{
	    Property property = new Property(entity, column);
	    if (column.getColumnName() != null)
	    {
		property.setName(getPropertyName(application, column
			.getColumnName()));
	    }
	    property.setUniqueConstraintGroup(getEntityName(application,
							    property.getColumn().getUniqueConstraintName()));
	    property.setDataType(getDataType(column));
	    return property;
	}
	return null;

    }

    private List<Entity> getEntities(final Application application)
    {

	Database database = application.getOrm().getDatabase();

	ArrayList<Entity> entities = new ArrayList<Entity>(database.getTables()
								   .size());

	List<Property> properties;
	Entity entity;

	for (Table table : database.getTables())
	{
	    entity = new Entity(application.getOrm(), table);
	    entity.setName(getEntityName(application, table.getTableName()));
	    entity.setPluralName(getPluralName(application, entity.getName()));
	    entity
		    .setDaoPackage(getDaoPackage(application, table
			    .getTableName()));
	    entity.setBeanPackage(getBeanPackage(application, table
		    .getTableName()));


	    properties = new ArrayList<Property>(table.getColumns().size());

	    for (Column column : table.getColumns())
	    {
		properties.add(getProperty(application, entity, column));
	    }
	    entity.setProperties(properties);
	    entities.add(entity);
	}
	return entities;
    }


    protected String getServiceName(final Application application,
				    final String packageName)
    {
	if (packageName != null)
	{
	    StringBuffer buffer = new StringBuffer();
	    String[] relationalWords = packageName
		    .split(application.getDatabaseWordSeparator());
	    int relationalWordsCount = relationalWords.length;
	    for (int index = 0; index < relationalWordsCount; index++)
	    {
		buffer.append(toTileCase(getObjectOrientedWord(application,
							       relationalWords[index])));
	    }
	    buffer.append("Service");
	    return buffer.toString();
	}
	return null;
    }

    protected String getEntityName(final Application application,
				   final String tableName)
    {
	if (tableName != null)
	{
	    StringBuffer buffer = new StringBuffer();
	    String[] relationalWords = tableName
		    .split(application.getDatabaseWordSeparator());
	    int relationalWordsCount = relationalWords.length;
	    for (int index = 0; index < relationalWordsCount; index++)
	    {
		buffer.append(toTileCase(getObjectOrientedWord(application,
							       relationalWords[index])));
	    }
	    if (application.getBeanSuffix() != null
		&& application.getBeanSuffix().trim().length() != 0)
	    {
		buffer.append(application.getBeanSuffix().trim());
	    }
	    return buffer.toString();
	}
	return null;
    }

    protected String getObjectOrientedWord(final Application application,
					   final String relationalWord)
    {
	String objectOrientedWord = null;
	if (application.getWordsMap() != null)
	{
	    for (String relationalWordKey : application
		    .getWordsMap().keySet())
	    {
		if (relationalWord.equalsIgnoreCase(relationalWordKey))
		{
		    objectOrientedWord = application.getWordsMap().get(
			    relationalWordKey);
		}

	    }
	}
	return objectOrientedWord == null ? relationalWord : objectOrientedWord;

    }

    protected String getPluralName(final Application application,
				   final String entityName)
    {
	String pluralName = null;
	HashMap<String, String> pluralMap = application.getPluralMap();
	String pluralValue;
	String capsEntityName = entityName.toUpperCase();
	if (pluralMap != null && pluralMap.size() != 0)
	{
	    for (String pluralKey : pluralMap.keySet())
	    {
		pluralValue = pluralMap.get(pluralKey).toUpperCase();
		if (capsEntityName.endsWith(pluralKey.toUpperCase()))
		{
		    int lastIndex = capsEntityName.lastIndexOf(pluralKey
								       .toUpperCase());
		    pluralName = entityName.substring(0, lastIndex)
				 + toTileCase(pluralValue);
		    break;
		}
	    }
	}
	if (pluralName == null)
	{
	    pluralName = entityName + "s";
	}
	return pluralName;
    }


    private String getPackage(final Application application,
			      final String tableName, final String identifier)
    {
	StringBuffer buffer = new StringBuffer();

	if (application.getRootPackage() != null)
	{
	    buffer.append(application.getRootPackage());
	}

	String moduleName = getModuleName(application, tableName);

	if (application.isModulesFirst())
	{
	    if (moduleName != null
		&& moduleName.trim().length() != 0)
	    {
		buffer.append(".");
		buffer.append(moduleName.trim());
	    }
	    if (identifier != null
		&& identifier.trim().length() != 0)
	    {
		buffer.append(".");
		buffer.append(identifier.trim());
	    }
	}
	else
	{
	    if (identifier != null
		&& identifier.trim().length() != 0)
	    {
		buffer.append(".");
		buffer.append(identifier.trim());
	    }
	    if (moduleName != null
		&& moduleName.trim().length() != 0)
	    {
		buffer.append(".");
		buffer.append(moduleName.trim());
	    }
	}
	return buffer.toString().toLowerCase();
    }

    protected String getDaoPackage(final Application application,
				   final String tableName)
    {
	return getPackage(application, tableName,
			  application.getDaoIdentifier());
    }

    protected String getBeanPackage(final Application application,
				    final String tableName)
    {
	return getPackage(application, tableName,
			  application.getBeanIdentifier());
    }

    protected String getModuleName(final Application application,
				   final String tableName)
    {

	String[] dbWords = tableName
		.split(application.getDatabaseWordSeparator());
	HashMap<String, String> modulesMap = application.getModulesMap();
	if (modulesMap != null)
	{
	    for (String moduleKey : modulesMap.keySet())
	    {
		for (int i = dbWords.length - 1; i >= 0; i--)
		{
		    if (dbWords[i].toUpperCase()
				  .equals(moduleKey.toUpperCase()))
		    {
			return modulesMap.get(moduleKey);

		    }
		}
	    }
	}
	return null;
    }

    protected String getPropertyName(final Application application,
				     final String columnName)
    {
	StringBuffer buffer = new StringBuffer();
	String[] relationalWords = columnName
		.split(application.getDatabaseWordSeparator());
	int relationalWordsCount = relationalWords.length;
	for (int index = 0; index < relationalWordsCount; index++)
	{
	    if (index == 0)
	    {
		buffer.append(getObjectOrientedWord(application,
						    relationalWords[index]).toLowerCase());
	    }
	    else
	    {
		buffer.append(toTileCase(getObjectOrientedWord(application,
							       relationalWords[index])));
	    }
	}
	return buffer.toString();
    }

    protected String toTileCase(final String word)
    {
	char[] wordTemp = word.toLowerCase().toCharArray();
	int letterCount = wordTemp.length;
	for (int index = 0; index < letterCount; index++)
	{
	    if (index == 0)
	    {
		wordTemp[index] = Character.toUpperCase(wordTemp[index]);
	    }
	    else
	    {
		wordTemp[index] = Character.toLowerCase(wordTemp[index]);
	    }
	}
	return new String(wordTemp);
    }
}
