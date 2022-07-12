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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Mapper
{
    public static final String DOT = ".";
    private final Application application;

    public Mapper(final Application bApplication)
    {
	application = bApplication;
    }

    public abstract String getDataType(final Column aColumn);

    /**
     * @return ORM
     */
    public ORM getOrm() throws Exception
    {
	ORM orm = application.getOrm();

	if ((application.getOrm().getDatabase() == null) || application.isOnline())
	{
	    Database database = new Crawler(application).getDatabase();
	    application.getOrm().setDatabase(database);
	}

	orm.setEntities(getEntities());
	orm.setMethods(getMethods());
	orm.setServices(getServices());
	return orm;
    }

    private Method getMethod(final Procedure aProcedure)
    {
	List<Property> properties = new ArrayList<>(aProcedure.getParameters().size());
	Method method = new Method(aProcedure);
	method.setName(getPropertyName(aProcedure.getFunctionName()));

	for (Column column : aProcedure.getParameters())
	{
	    properties.add(getProperty(null, column));
	}
	method.setInputParameters(properties);

	method.setOutputProperty(getProperty(null, aProcedure.getOutput()));
	return method;
    }

    private List<Method> getMethods()
    {
	Database database = application.getOrm().getDatabase();
	ArrayList<Method> methods = new ArrayList<>();
	if (database.getFunctions() != null)
	{
	    for (Procedure function : database.getFunctions())
	    {
		methods.add(getMethod(function));
	    }
	}
	return methods;
    }

    private List<Service> getServices()
    {
	ArrayList<Service> services = new ArrayList<>();
	if (application
		    .getOrm().getDatabase().getPackages() != null)
	{
	    Service service;
	    for (Package package1 : application.getOrm().getDatabase().getPackages())
	    {
		service = new Service();
		service.setPackage(package1);
		service.setServiceName(getServiceName(
			service.getName()));
		service.setDaoPackage(getDaoPackage(service.getName()));
		service.setMethods(new ArrayList<>());
		for (Procedure function : package1.getFunctions())
		{
		    service.getMethods().add(getMethod(function));
		}
		services.add(service);
	    }
	}

	return services;
    }

    private Property getProperty(final Entity aEntity, final Column aColumn)
    {
	if (aColumn != null)
	{
	    Property property = new Property(aEntity, aColumn);
	    if (aColumn.getColumnName() != null)
	    {
		property.setName(getPropertyName(aColumn.getColumnName()));
	    }
	    property.setUniqueConstraintGroup(getEntityName(property.getColumn().getUniqueConstraintName()));
	    property.setDataType(getDataType(aColumn));
	    return property;
	}
	return null;
    }

    private List<Entity> getEntities()
    {
	Database database = application.getOrm().getDatabase();
	ArrayList<Entity> entities = new ArrayList<>(database.getTables().size());

	List<Property> properties;
	Entity entity;

	for (Table table : database.getTables())
	{
	    entity = new Entity(application.getOrm(), table);
	    entity.setName(getEntityName(table.getTableName()));
	    entity.setPluralName(getPluralName(entity.getName()));
	    entity
		    .setDaoPackage(getDaoPackage(table
							 .getTableName()));
	    entity.setBeanPackage(getBeanPackage(table.getTableName()));
	    properties = new ArrayList<>(table.getColumns().size());

	    for (Column column : table.getColumns())
	    {
		properties.add(getProperty(entity, column));
	    }
	    entity.setProperties(properties);
	    entities.add(entity);
	}
	return entities;
    }

    protected String getServiceName(final String aPackageName)
    {
	if (aPackageName != null)
	{
	    StringBuilder lStringBuilder = new StringBuilder();
	    String[] bRelationalWords = aPackageName.split(application.getDatabaseWordSeparator());
	    for (final String aRelationalWord : bRelationalWords)
	    {
		lStringBuilder.append(toTileCase(getObjectOrientedWord(aRelationalWord)));
	    }
	    lStringBuilder.append("Service");
	    return lStringBuilder.toString();
	}
	return null;
    }

    protected String getEntityName(final String aTableName)
    {
	if (aTableName != null)
	{
	    StringBuilder lStringBuilder = new StringBuilder();
	    String[] bRelationalWords = aTableName
		    .split(application.getDatabaseWordSeparator());
	    for (final String aRelationalWord : bRelationalWords)
	    {
		lStringBuilder.append(toTileCase(getObjectOrientedWord(aRelationalWord)));
	    }
	    if (application.getBeanSuffix() != null
		&& application.getBeanSuffix().trim().length() != 0)
	    {
		lStringBuilder.append(application.getBeanSuffix().trim());
	    }
	    return lStringBuilder.toString();
	}
	return null;
    }

    protected String getObjectOrientedWord(final String aRelationalWord)
    {
	String lObjectOrientedWord = null;
	if (application.getWordsMap() != null)
	{
	    for (String relationalWordKey : application.getWordsMap().keySet())
	    {
		if (aRelationalWord.equalsIgnoreCase(relationalWordKey))
		{
		    lObjectOrientedWord = application.getWordsMap().get(
			    relationalWordKey);
		}
	    }
	}
	return lObjectOrientedWord == null ? aRelationalWord : lObjectOrientedWord;
    }

    protected String getPluralName(final String aEntityName)
    {
	String lPluralName = null;
	HashMap<String, String> lPluralMap = application.getPluralMap();
	String pluralValue;
	String capsEntityName = aEntityName.toUpperCase();
	if (lPluralMap != null && lPluralMap.size() != 0)
	{
	    for (String pluralKey : lPluralMap.keySet())
	    {
		pluralValue = lPluralMap.get(pluralKey).toUpperCase();
		if (capsEntityName.endsWith(pluralKey.toUpperCase()))
		{
		    int lastIndex = capsEntityName.lastIndexOf(pluralKey
								       .toUpperCase());
		    lPluralName = aEntityName.substring(0, lastIndex)
				  + toTileCase(pluralValue);
		    break;
		}
	    }
	}

	if (lPluralName == null)
	{
	    lPluralName = aEntityName + "s";
	}
	return lPluralName;
    }


    private String getPackage(final String aTableName, final String aIdentifier)
    {
	StringBuilder lBuffer = new StringBuilder();
	if (application.getRootPackage() != null)
	{
	    lBuffer.append(application.getRootPackage());
	}

	String moduleName = getModuleName(aTableName);

	if (application.isModulesFirst())
	{
	    if (moduleName != null && moduleName.trim().length() != 0)
	    {
		lBuffer.append(DOT);
		lBuffer.append(moduleName.trim());
	    }
	    if (aIdentifier != null && aIdentifier.trim().length() != 0)
	    {
		lBuffer.append(DOT);
		lBuffer.append(aIdentifier.trim());
	    }
	}
	else
	{
	    if (aIdentifier != null && aIdentifier.trim().length() != 0)
	    {
		lBuffer.append(DOT);
		lBuffer.append(aIdentifier.trim());
	    }

	    if (moduleName != null && moduleName.trim().length() != 0)
	    {
		lBuffer.append(DOT);
		lBuffer.append(moduleName.trim());
	    }
	}
	return lBuffer.toString().toLowerCase();
    }

    protected String getDaoPackage(final String aTableName)
    {
	return getPackage(aTableName, application.getDaoIdentifier());
    }

    protected String getBeanPackage(final String aTableName)
    {
	return getPackage(aTableName, application.getBeanIdentifier());
    }

    protected String getModuleName(final String aTableName)
    {
	String[] dbWords = aTableName.split(application.getDatabaseWordSeparator());
	HashMap<String, String> modulesMap = application.getModulesMap();
	if (modulesMap != null)
	{
	    for (String moduleKey : modulesMap.keySet())
	    {
		for (int i = dbWords.length - 1; i >= 0; i--)
		{
		    if (dbWords[i].equalsIgnoreCase(moduleKey))
		    {
			return modulesMap.get(moduleKey);
		    }
		}
	    }
	}
	return null;
    }

    protected String getPropertyName(final String aColumnName)
    {
	StringBuilder lStringBuilder = new StringBuilder();
	String[] lRelationalWords = aColumnName
		.split(application.getDatabaseWordSeparator());
	int relationalWordsCount = lRelationalWords.length;
	for (int index = 0; index < relationalWordsCount; index++)
	{
	    if (index == 0)
	    {
		lStringBuilder.append(getObjectOrientedWord(lRelationalWords[index]).toLowerCase());
	    }
	    else
	    {
		lStringBuilder.append(toTileCase(getObjectOrientedWord(lRelationalWords[index])));
	    }
	}
	return lStringBuilder.toString();
    }

    protected String toTileCase(final String aWord)
    {
	char[] lCharArray = aWord.toLowerCase().toCharArray();
	int lLetterCount = lCharArray.length;
	for (int index = 0; index < lLetterCount; index++)
	{
	    if (index == 0)
	    {
		lCharArray[index] = Character.toUpperCase(lCharArray[index]);
	    }
	    else
	    {
		lCharArray[index] = Character.toLowerCase(lCharArray[index]);
	    }
	}
	return new String(lCharArray);
    }
}
