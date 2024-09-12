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
        try (CallableStatement callableStatement = dbDataSource.getConnection().prepareCall("call ${method.functionName}(<#list 0..< method.inputParameters?size-1 as i>?,</#list>?)")) {
            <#list method.inputParameters as parameter>
               <#if getClassName(parameter.dataType) != "Void">
               <#switch parameter.dataType>
                 <#case "java.time.LocalDate">
                 <#case "java.time.LocalTime">
                 <#case "java.time.LocalDateTime">
                 <#case "java.nio.ByteBuffer">
                 <#case "com.fasterxml.jackson.databind.JsonNode">
                 <#case "java.util.UUID">
                 <#case "java.time.Duration">
                 <#case "java.util.BitSet">
                      callableStatement.setObject(${parameter?index}, ${parameter.name});
                      <#break>

                 <#default>
                      callableStatement.set${getClassName(parameter.dataType)}(${parameter?index+1}, ${parameter.name});
               </#switch>
               	<#assign a=addImportStatement(parameter.dataType)>
               </#if>
            </#list>
            <#list method.outputParameters as oParameter>
                <#if getClassName(oParameter.dataType) != "Void">
                      callableStatement.registerOutParameter(${method.inputParameters?size+1 + oParameter?index}, ${getColumnType(oParameter.column.columnType)} );


                </#if>
            </#list>
            callableStatement.execute();
        } catch (SQLException e) {
            throw e;
        }
    }
    </#list>
}