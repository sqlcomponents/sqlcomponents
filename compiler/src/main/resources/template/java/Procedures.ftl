<#assign a=addImportStatement("java.sql.CallableStatement")>
<#assign a=addImportStatement("java.sql.SQLException")>

/**
* Calls a stored procedure.
* @return procedure
*/
public Procedure call() {
    return this.procedure;
}

public static final class Procedure {


    private Procedure() {
    }

    <#list orm.methods as method>
    <#assign inCount = 0>
    <#list method.inputParameters as parameter>
        <#if getClassName(parameter.dataType) != "Void">
            <#assign inCount = inCount + 1>
        </#if>
    </#list>
    /**
    * ${method.name} Method.
    <#list method.inputParameters as parameter>
    * @param ${parameter.name}
    </#list>
    <#if method.exceptions?? && (method.exceptions?size > 0)>
    <#list method.exceptions as exception>
    * @throws ${exception}
    </#list>
    </#if>
    */
    public void ${method.name}(
        final DataSource dbDataSource,
    <#list method.inputParameters as parameter>
    <#if getClassName(parameter.dataType) != "Void"> final ${getClassName(parameter.dataType)} ${parameter.name}<#if parameter?index < method.inputParameters?size-1>,</#if> </#if>
    </#list>
    <#if method.outputParameters?? && (method.outputParameters?size > 0) >,</#if>
    <#list method.outputParameters as parameter>
    <#if getClassName(parameter.dataType) != "Void">${getClassName(parameter.dataType)} ${parameter.name}<#if parameter?index <  method.outputParameters?size-1>,</#if></#if>
    </#list>
    ) throws SQLException {
    <#if method.outputParameters?? && (method.outputParameters?size > 0)>
        try (CallableStatement callableStatement = dbDataSource.getConnection()
        .prepareCall("call ${method.functionName}(<#assign sep=""><#list 1..inCount as i>${sep}?<#assign sep=","></#list>)")) {
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
                      callableStatement.setObject(${parameter?index+1}, ${parameter.name});
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
            <#list method.outputParameters as oParameter>
                  ${oParameter.name} = callableStatement.get${getClassName(oParameter.dataType)}(${method.inputParameters?size+1 + oParameter?index} );
            </#list>
        }
    <#else>
        SqlBuilder.prepareCall("call ${method.functionName}(<#assign sep2=""><#list 1..inCount as i>${sep2}?<#assign sep2=","></#list>)")
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
                    .param((Object) ${parameter.name})
                      <#break>
                 <#case "java.lang.Byte">
                    .param((Object) ${parameter.name})
                      <#break>
                 <#default>
                    .param(${parameter.name})
               </#switch>
               	<#assign a=addImportStatement(parameter.dataType)>
               </#if>
            </#list>
            .execute(dbDataSource);
    </#if>
    }
    </#list>
}
