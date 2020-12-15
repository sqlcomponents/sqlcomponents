<#if table.hasPrimaryKey>

    public List<${name}> get${pluralName}(${getPrimaryKeysAsParameterStringExceptHighest()}) throws SQLException  {
        return null;
	}
</#if>
