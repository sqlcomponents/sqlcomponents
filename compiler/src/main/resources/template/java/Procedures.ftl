<#assign a=addImportStatement("java.sql.CallableStatement")>
<#assign a=addImportStatement("java.sql.SQLException")>

public Procedure call() {
    return this.procedure;
}

public static final class Procedure {

    private final javax.sql.DataSource dbDataSource;

    private Procedure(final DataSource dbDataSource) {
        this.dbDataSource = dbDataSource;
    }

    <#list orm.methods as method>
    public void ${method.name}(
    <#list method.inputParameters as parameter>
        <#if getClassName(parameter.dataType) != "Void">
            final ${getClassName(parameter.dataType)} ${parameter.name} <#if parameter?index <  method.inputParameters?size-1> , </#if>
        </#if>
    </#list>
    ) throws SQLException {
        try (CallableStatement callableStatement = dbDataSource.getConnection().prepareCall("call ${method.functionName}(?,?)")) {
            <#list method.inputParameters as parameter>
               <#if getClassName(parameter.dataType) != "Void">
                   callableStatement.set${getClassName(parameter.dataType)}(${parameter?index}, ${parameter.name});
               </#if>
            </#list>
            callableStatement.execute();
        } catch (SQLException e) {
            throw e;
        }
    }
    </#list>
}