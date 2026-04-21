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
    <#-- PostgreSQL scalar SQL functions use JDBC {? = call fn(?,...)}; first ? is the return value. -->
    <#assign usePgFunctionReturnSyntax = (orm.database.dbType == 'POSTGRES') && (outNonVoidCount == 1)>
    <#assign paramTotal = inCount + outNonVoidCount>
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
        try (CallableStatement callableStatement = dbDataSource.getConnection()
                .prepareCall("{? = call ${method.functionName}(<#assign sep=""><#list 1..inCount as i>${sep}?<#assign sep=","></#list>)}")) {
            callableStatement.registerOutParameter(1, ${getColumnType(firstNonVoidOut.column.columnType)} );
            <#assign inSlot = 2>
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
                      callableStatement.setObject(${inSlot}, ${parameter.name});
                      <#break>
                 <#default>
                      callableStatement.set${getClassName(parameter.dataType)}(${inSlot}, ${parameter.name});
               </#switch>
               	<#assign a=addImportStatement(parameter.dataType)>
                <#assign inSlot = inSlot + 1>
               </#if>
            </#list>
            callableStatement.execute();
            return ${callableOutScalarExpression("1", firstNonVoidOut.dataType)};
        }
        <#else>
        try (CallableStatement callableStatement = dbDataSource.getConnection()
                .prepareCall("{call ${method.functionName}(<#assign sep2=""><#list 1..paramTotal as i>${sep2}?<#assign sep2=","></#list>)}")) {
            <#assign inSlot = 1>
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
                      callableStatement.setObject(${inSlot}, ${parameter.name});
                      <#break>
                 <#default>
                      callableStatement.set${getClassName(parameter.dataType)}(${inSlot}, ${parameter.name});
               </#switch>
               	<#assign a=addImportStatement(parameter.dataType)>
                <#assign inSlot = inSlot + 1>
               </#if>
            </#list>
            <#assign outIdx = 0>
            <#list method.outputParameters as oParameter>
                <#if getClassName(oParameter.dataType) != "Void">
                      callableStatement.registerOutParameter(${inCount + 1 + outIdx}, ${getColumnType(oParameter.column.columnType)} );
                    <#assign outIdx = outIdx + 1>
                </#if>
            </#list>
            callableStatement.execute();
            return ${callableOutScalarExpression((inCount + 1)?string, firstNonVoidOut.dataType)};
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
        try (CallableStatement callableStatement = dbDataSource.getConnection()
                .prepareCall("{call ${method.functionName}(<#assign sep3=""><#list 1..paramTotal as i>${sep3}?<#assign sep3=","></#list>)}")) {
            <#assign inSlot = 1>
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
                      callableStatement.setObject(${inSlot}, ${parameter.name});
                      <#break>
                 <#default>
                      callableStatement.set${getClassName(parameter.dataType)}(${inSlot}, ${parameter.name});
               </#switch>
               	<#assign a=addImportStatement(parameter.dataType)>
                <#assign inSlot = inSlot + 1>
               </#if>
            </#list>
            <#assign outIdx = 0>
            <#list method.outputParameters as oParameter>
                <#if getClassName(oParameter.dataType) != "Void">
                      callableStatement.registerOutParameter(${inCount + 1 + outIdx}, ${getColumnType(oParameter.column.columnType)} );
                    <#assign outIdx = outIdx + 1>
                </#if>
            </#list>
            callableStatement.execute();
            <#assign outIdx = 0>
            <#list method.outputParameters as oParameter>
                <#if getClassName(oParameter.dataType) != "Void">
                  ${oParameter.name}[0] = ${callableOutScalarExpression((inCount + 1 + outIdx)?string, oParameter.dataType)};
                    <#assign outIdx = outIdx + 1>
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
    }
    </#if>
    </#list>
}
