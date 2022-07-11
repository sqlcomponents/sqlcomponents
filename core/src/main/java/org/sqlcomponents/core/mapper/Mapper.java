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
    public abstract String getDataType(final Column aColumn);

    /**
     * @param aApplication
     * @return ORM
     * @throws SQLComponentsException
     */
    public ORM getOrm(final Application aApplication) throws Exception
    {
	ORM orm = aApplication.getOrm();

	if (aApplication.getOrm().getDatabase() == null || aApplication.isOnline())
	{
	    Database database = new Crawler(aApplication).getDatabase();
	    aApplication.getOrm().setDatabase(database);
	}

	orm.setEntities(getEntities(aApplication));
	orm.setMethods(getMethods(aApplication));
	orm.setServices(getServices(aApplication));
	return orm;
    }

    private Method getMethod(final Procedure aProcedure, final Application aApplication)
    {
	List<Property> properties = new ArrayList<Property>(aProcedure.getParameters().size());
	Method method = new Method(aProcedure);
	method.setName(getPropertyName(aApplication, aProcedure.getFunctionName()));

	for (Column column : aProcedure.getParameters())
	{
	    properties.add(getProperty(aApplication, null, column));
	}
	method.setInputParameters(properties);

	method.setOutputProperty(getProperty(aApplication, null,
					     aProcedure.getOutput()));
	return method;
    }

    private List<Method> getMethods(final Application aApplication)
    {
	Database database = aApplication.getOrm().getDatabase();
	ArrayList<Method> methods = new ArrayList<Method>();
	if (database.getFunctions() != null)
	{
	    for (Procedure function : database.getFunctions())
	    {
		methods.add(getMethod(function, aApplication));
	    }
	}
	return methods;
    }

    private List<Service> getServices(final Application aApplication)
    {
	ArrayList<Service> services = new ArrayList<Service>();
	if (aApplication
		    .getOrm().getDatabase().getPackages() != null)
	{
	    Service service = null;
	    for (Package package1 : aApplication.getOrm()
						.getDatabase().getPackages())
	    {
		service = new Service();
		service.setPackage(package1);
		service.setServiceName(getServiceName(aApplication,
						      service.getName()));
		service.setDaoPackage(getDaoPackage(aApplication,
						    service.getName()));
		service.setMethods(new ArrayList<Method>());
		for (Procedure function : package1.getFunctions())
		{
		    service.getMethods().add(getMethod(function,
						       aApplication));
		}
		services.add(service);
	    }
	}

	return services;
    }

    private Property getProperty(final Application aApplication,
				 final Entity aEntity, final Column aColumn)
    {
	if (aColumn != null)
	{
	    Property property = new Property(aEntity, aColumn);
	    if (aColumn.getColumnName() != null)
	    {
		property.setName(getPropertyName(aApplication, aColumn
			.getColumnName()));
	    }
	    property.setUniqueConstraintGroup(getEntityName(aApplication,
							    property.getColumn().getUniqueConstraintName()));
	    property.setDataType(getDataType(aColumn));
	    return property;
	}
	return null;

    }

    private List<Entity> getEntities(final Application aApplication)
    {
	Database database = aApplication.getOrm().getDatabase();
	ArrayList<Entity> entities = new ArrayList<Entity>(database.getTables().size());

	List<Property> properties;
	Entity entity;

	for (Table table : database.getTables())
	{
	    entity = new Entity(aApplication.getOrm(), table);
	    entity.setName(getEntityName(aApplication, table.getTableName()));
	    entity.setPluralName(getPluralName(aApplication, entity.getName()));
	    entity
		    .setDaoPackage(getDaoPackage(aApplication, table
			    .getTableName()));
	    entity.setBeanPackage(getBeanPackage(aApplication, table
		    .getTableName()));


	    properties = new ArrayList<Property>(table.getColumns().size());

	    for (Column column : table.getColumns())
	    {
		properties.add(getProperty(aApplication, entity, column));
	    }
	    entity.setProperties(properties);
	    entities.add(entity);
	}
	return entities;
    }

    protected String getServiceName(final Application aApplication, final String aPackageName)
    {
	if (aPackageName != null)
	{
	    StringBuffer buffer = new StringBuffer();
	    String[] relationalWords = aPackageName
		    .split(aApplication.getDatabaseWordSeparator());
	    int relationalWordsCount = relationalWords.length;
	    for (int index = 0; index < relationalWordsCount; index++)
	    {
		buffer.append(toTileCase(getObjectOrientedWord(aApplication,
							       relationalWords[index])));
	    }
	    buffer.append("Service");
	    return buffer.toString();
	}
	return null;
    }

    protected String getEntityName(final Application aApplication, final String tableName)
    {
	if (tableName != null)
	{
	    StringBuffer buffer = new StringBuffer();
	    String[] relationalWords = tableName
		    .split(aApplication.getDatabaseWordSeparator());
	    int relationalWordsCount = relationalWords.length;
	    for (int index = 0; index < relationalWordsCount; index++)
	    {
		buffer.append(toTileCase(getObjectOrientedWord(aApplication,
							       relationalWords[index])));
	    }
	    if (aApplication.getBeanSuffix() != null
		&& aApplication.getBeanSuffix().trim().length() != 0)
	    {
		buffer.append(aApplication.getBeanSuffix().trim());
	    }
	    return buffer.toString();
	}
	return null;
    }

    protected String getObjectOrientedWord(final Application aApplication, final String relationalWord)
    {
	String objectOrientedWord = null;
	if (aApplication.getWordsMap() != null)
	{
	    for (String relationalWordKey : aApplication
		    .getWordsMap().keySet())
	    {
		if (relationalWord.equalsIgnoreCase(relationalWordKey))
		{
		    objectOrientedWord = aApplication.getWordsMap().get(
			    relationalWordKey);
		}

	    }
	}
	return objectOrientedWord == null ? relationalWord : objectOrientedWord;

    }

    protected String getPluralName(final Application aApplication, final String entityName)
    {
	String pluralName = null;
	HashMap<String, String> pluralMap = aApplication.getPluralMap();
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


    private String getPackage(final Application aApplication, final String tableName, final String aIdentifier)
    {
	StringBuffer buffer = new StringBuffer();
	if (aApplication.getRootPackage() != null)
	{
	    buffer.append(aApplication.getRootPackage());
	}

	String moduleName = getModuleName(aApplication, tableName);

	if (aApplication.isModulesFirst())
	{
	    if (moduleName != null
		&& moduleName.trim().length() != 0)
	    {
		buffer.append(".");
		buffer.append(moduleName.trim());
	    }
	    if (aIdentifier != null
		&& aIdentifier.trim().length() != 0)
	    {
		buffer.append(".");
		buffer.append(aIdentifier.trim());
	    }
	}
	else
	{
	    if (aIdentifier != null
		&& aIdentifier.trim().length() != 0)
	    {
		buffer.append(".");
		buffer.append(aIdentifier.trim());
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

    protected String getDaoPackage(final Application aApplication, final String aTableName)
    {
	return getPackage(aApplication, aTableName, aApplication.getDaoIdentifier());
    }

    protected String getBeanPackage(final Application aApplication, final String aTableName)
    {
	return getPackage(aApplication, aTableName, aApplication.getBeanIdentifier());
    }

    protected String getModuleName(final Application aApplication, final String aTableName)
    {

	String[] dbWords = aTableName.split(aApplication.getDatabaseWordSeparator());
	HashMap<String, String> modulesMap = aApplication.getModulesMap();
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

    protected String getPropertyName(final Application aApplication, final String aColumnName)
    {
	StringBuffer buffer = new StringBuffer();
	String[] relationalWords = aColumnName
		.split(aApplication.getDatabaseWordSeparator());
	int relationalWordsCount = relationalWords.length;
	for (int index = 0; index < relationalWordsCount; index++)
	{
	    if (index == 0)
	    {
		buffer.append(getObjectOrientedWord(aApplication, relationalWords[index]).toLowerCase());
	    }
	    else
	    {
		buffer.append(toTileCase(getObjectOrientedWord(aApplication, relationalWords[index])));
	    }
	}
	return buffer.toString();
    }

    protected String toTileCase(final String aWord)
    {
	char[] wordTemp = aWord.toLowerCase().toCharArray();
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
