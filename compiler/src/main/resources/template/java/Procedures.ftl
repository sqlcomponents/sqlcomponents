<#assign a=addImportStatement("java.sql.CallableStatement")>
<#assign a=addImportStatement("java.sql.Connection")>
<#assign a=addImportStatement("java.sql.SQLException")>

<#-- Binds one IN / INOUT input at the JDBC 1-based parameter index. -->
<#macro emitCallableInBind parameter ord>
               <#switch parameter.dataType>
                 <#case "java.time.LocalDate">
                 <#case "java.time.LocalTime">
                 <#case "java.time.LocalDateTime">
                 <#case "java.nio.ByteBuffer">
                 <#case "com.fasterxml.jackson.databind.JsonNode">
                 <#case "java.util.UUID">
                 <#case "java.time.Duration">
                 <#case "java.util.BitSet">
                      callableStatement.setObject(${ord}, ${parameter.name});
                      <#break>
                 <#default>
                      callableStatement.set${getClassName(parameter.dataType)}(${ord}, ${parameter.name});
               </#switch>
               	<#assign a=addImportStatement(parameter.dataType)>
</#macro>

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
    <#assign outNonVoidCount = 0>
    <#assign firstNonVoidOut = "">
    <#assign firstOutResolved = false>
    <#if method.outputParameters??>
    <#list method.outputParameters as op>
        <#if getClassName(op.dataType) != "Void">
            <#assign outNonVoidCount = outNonVoidCount + 1>
            <#if !firstOutResolved>
                <#assign firstNonVoidOut = op>
                <#assign firstOutResolved = true>
            </#if>
        </#if>
    </#list>
    </#if>
    <#assign maxOrd = 0>
    <#list method.inputParameters as parameter>
        <#if parameter.column?? && getClassName(parameter.dataType) != "Void" && parameter.column.ordinalPosition gte 1 && parameter.column.ordinalPosition gt maxOrd>
            <#assign maxOrd = parameter.column.ordinalPosition>
        </#if>
    </#list>
    <#list method.outputParameters as parameter>
        <#if parameter.column?? && getClassName(parameter.dataType) != "Void" && parameter.column.ordinalPosition gte 1 && parameter.column.ordinalPosition gt maxOrd>
            <#assign maxOrd = parameter.column.ordinalPosition>
        </#if>
    </#list>
    <#-- PostgreSQL SQL functions: JDBC {? = call fn(?,...)} — first ? is the scalar return (metadata ordinal 0). -->
    <#assign usePgFunctionReturnSyntax = (orm.database.dbType == 'POSTGRES') && (outNonVoidCount == 1)
        && firstNonVoidOut?has_content && firstNonVoidOut.column?? && (firstNonVoidOut.column.ordinalPosition == 0)>
    <#-- PostgreSQL CREATE PROCEDURE must use CALL, not JDBC {call ...} (driver treats that as a function call). -->
    <#assign pgUseCallKeyword = (orm.database.dbType == 'POSTGRES') && (method.function.catalogProcedure!false)>
    /**
    * ${method.name} Method.
    <#list method.inputParameters as parameter>
    * @param ${parameter.name}
    </#list>
    <#if outNonVoidCount == 1>
    * @return ${method.name} output value
    <#elseif outNonVoidCount gt 1>
    <#list method.outputParameters as parameter>
    <#if getClassName(parameter.dataType) != "Void">
    * @param ${parameter.name} single-element array; element 0 receives the output value
    </#if>
    </#list>
    </#if>
    <#if method.exceptions?? && (method.exceptions?size > 0)>
    <#list method.exceptions as exception>
    * @throws ${exception}
    </#list>
    </#if>
    */
    <#if outNonVoidCount == 1>
    public ${getClassName(firstNonVoidOut.dataType)} ${method.name}(
        final DataSource dbDataSource
    <#list method.inputParameters as parameter>
        <#if getClassName(parameter.dataType) != "Void">
        , final ${getClassName(parameter.dataType)} ${parameter.name}
        </#if>
    </#list>
    ) throws SQLException {
        <#if usePgFunctionReturnSyntax>
        try (Connection connection = dbDataSource.getConnection();
                CallableStatement callableStatement = connection
                .prepareCall("{? = call ${method.functionName}(<#assign sep=""><#list 1..inCount as i>${sep}?<#assign sep=","></#list>)}")) {
            callableStatement.registerOutParameter(1, ${getColumnType(firstNonVoidOut.column.columnType)} );
            <#assign inSlot = 2>
            <#list method.inputParameters as parameter>
               <#if getClassName(parameter.dataType) != "Void">
               <@emitCallableInBind parameter=parameter ord=inSlot/>
                <#assign inSlot = inSlot + 1>
               </#if>
            </#list>
            callableStatement.execute();
            return ${callableOutScalarExpression("1", firstNonVoidOut.dataType)};
        }
        <#else>
        try (Connection connection = dbDataSource.getConnection();
                CallableStatement callableStatement = connection
                .prepareCall(<#if pgUseCallKeyword>"CALL ${method.functionName}(<#assign sep2=""><#list 1..maxOrd as i>${sep2}?<#assign sep2=","></#list>)"<#else>"{call ${method.functionName}(<#assign sep2=""><#list 1..maxOrd as i>${sep2}?<#assign sep2=","></#list>)}"</#if>)) {
            <#list 1..maxOrd as ord>
            <#list method.inputParameters as parameter>
               <#if getClassName(parameter.dataType) != "Void" && parameter.column?? && parameter.column.ordinalPosition == ord>
               <@emitCallableInBind parameter=parameter ord=ord/>
               </#if>
            </#list>
            <#list method.outputParameters as oParameter>
                <#if getClassName(oParameter.dataType) != "Void" && oParameter.column?? && oParameter.column.ordinalPosition == ord>
                      callableStatement.registerOutParameter(${ord}, ${getColumnType(oParameter.column.columnType)} );
                </#if>
            </#list>
            </#list>
            callableStatement.execute();
            return ${callableOutScalarExpression(firstNonVoidOut.column.ordinalPosition?string, firstNonVoidOut.dataType)};
        }
        </#if>
    }
    <#elseif outNonVoidCount gt 1>
    public void ${method.name}(
        final DataSource dbDataSource
    <#list method.inputParameters as parameter>
        <#if getClassName(parameter.dataType) != "Void">
        , final ${getClassName(parameter.dataType)} ${parameter.name}
        </#if>
    </#list>
    <#list method.outputParameters as parameter>
        <#if getClassName(parameter.dataType) != "Void">
        , final ${getClassName(parameter.dataType)}[] ${parameter.name}
        </#if>
    </#list>
    ) throws SQLException {
        try (Connection connection = dbDataSource.getConnection();
                CallableStatement callableStatement = connection
                .prepareCall(<#if pgUseCallKeyword>"CALL ${method.functionName}(<#assign sep3=""><#list 1..maxOrd as i>${sep3}?<#assign sep3=","></#list>)"<#else>"{call ${method.functionName}(<#assign sep3=""><#list 1..maxOrd as i>${sep3}?<#assign sep3=","></#list>)}"</#if>)) {
            <#list 1..maxOrd as ord>
            <#list method.inputParameters as parameter>
               <#if getClassName(parameter.dataType) != "Void" && parameter.column?? && parameter.column.ordinalPosition == ord>
               <@emitCallableInBind parameter=parameter ord=ord/>
               </#if>
            </#list>
            <#list method.outputParameters as oParameter>
                <#if getClassName(oParameter.dataType) != "Void" && oParameter.column?? && oParameter.column.ordinalPosition == ord>
                      callableStatement.registerOutParameter(${ord}, ${getColumnType(oParameter.column.columnType)} );
                </#if>
            </#list>
            </#list>
            callableStatement.execute();
            <#list method.outputParameters as oParameter>
                <#if getClassName(oParameter.dataType) != "Void" && oParameter.column??>
                  ${oParameter.name}[0] = ${callableOutScalarExpression(oParameter.column.ordinalPosition?string, oParameter.dataType)};
                </#if>
            </#list>
        }
    }
    <#else>
    public void ${method.name}(
        final DataSource dbDataSource
    <#list method.inputParameters as parameter>
        <#if getClassName(parameter.dataType) != "Void">
        , final ${getClassName(parameter.dataType)} ${parameter.name}
        </#if>
    </#list>
    ) throws SQLException {
        SqlBuilder.prepareCall(<#if pgUseCallKeyword>"CALL ${method.functionName}(<#assign sep2=""><#list 1..inCount as i>${sep2}?<#assign sep2=","></#list>)"<#else>"call ${method.functionName}(<#assign sep2=""><#list 1..inCount as i>${sep2}?<#assign sep2=","></#list>)"</#if>)
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
    }
    </#if>
    </#list>
}
