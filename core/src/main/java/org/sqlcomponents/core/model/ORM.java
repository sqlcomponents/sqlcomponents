package org.sqlcomponents.core.model;

import lombok.Getter;
import lombok.Setter;
import org.sqlcomponents.core.model.relational.Database;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class ORM
{
    public ORM(final Application application)
    {
	setApplication(application);
    }

    private Application application;

    private String userName;

    private String password;

    private String schemaName;

    private Database database;

    private String url;

    private String daoIdentifier = "store";

    private String beanIdentifier = "model";

    private String daoSuffix = "";

    private String beanSuffix;

    private HashMap<String, String> wordsMap;
    private HashMap<String, String> modulesMap;
    private HashMap<String, String> updateMap;
    private HashMap<String, String> insertMap;
    private HashMap<String, String> validationMap;

    private List<Entity> entities;
    private List<Service> services;
    private List<Method> methods;
    private List<Default> defaults;
    private boolean pagination;
    private List<String> methodSpecification;
}
