<#assign a=addImportStatement("java.sql.CallableStatement")>
<#assign a=addImportStatement("java.sql.Connection")>
<#assign a=addImportStatement("java.sql.SQLException")>

<#-- PostgreSQL JDBC: TYPE_NAME for arrays is often "_int4"; createArrayOf expects the element type (e.g. int4). -->
<#function pgProcedureArrayElementType typeName>
    <#local tn = (typeName!"")?trim>
    <#if (tn?length gt 0) && tn?starts_with("_")>
        <#return tn?substring(1)>
    <#else>
        <#return tn>
    </#if>
</#function>

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
                 <#case "java.sql.Array">
                      <#if parameter.column??>
                      {
                          final Object[] __sqlElems${ord} = (Object[]) ${parameter.name}.getArray();
                          final java.sql.Array __sqlArr${ord} = connection.createArrayOf(
                                  "${pgProcedureArrayElementType(parameter.column.typeName)}",
                                  __sqlElems${ord});
                          callableStatement.setArray(${ord}, __sqlArr${ord});
                      }
                      <#else>
                      callableStatement.setArray(${ord}, ${parameter.name});
                      </#if>
                      <#break>
                 <#case "java.sql.Struct">
                      callableStatement.setObject(${ord}, ${parameter.name});
                      <#break>
                 <#case "java.lang.Object">
                      callableStatement.setObject(${ord}, ${parameter.name});
                      <#break>
                 <#case "java.lang.Integer[]">
                 <#case "java.lang.Long[]">
                 <#case "java.lang.Short[]">
                 <#case "java.lang.Float[]">
                 <#case "java.lang.Double[]">
                 <#case "java.lang.String[]">
                      callableStatement.setArray(${ord}, connection.createArrayOf(
                              "${pgProcedureArrayElementType(parameter.column.typeName)}",
                              ${parameter.name}));
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

    private static java.sql.ResultSet detachRefCursorResultSet(
            final java.sql.ResultSet rawRs) throws java.sql.SQLException {
        if (rawRs == null) {
            return null;
        }
        final javax.sql.rowset.CachedRowSet crs =
                javax.sql.rowset.RowSetProvider.newFactory().createCachedRowSet();
        crs.populate(rawRs);
        rawRs.close();
        return crs;
    }

    <#list orm.methods as procMethod>
    <#assign inCount = 0>
    <#list procMethod.inputParameters as parameter>
        <#if getClassName(parameter.dataType) != "Void">
            <#assign inCount = inCount + 1>
        </#if>
    </#list>
    <#assign outNonVoidCount = 0>
    <#assign firstNonVoidOut = "">
    <#assign firstOutResolved = false>
    <#if procMethod.outputParameters??>
    <#list procMethod.outputParameters as op>
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
    <#list procMethod.inputParameters as parameter>
        <#if parameter.column?? && getClassName(parameter.dataType) != "Void" && parameter.column.ordinalPosition gte 1 && parameter.column.ordinalPosition gt maxOrd>
            <#assign maxOrd = parameter.column.ordinalPosition>
        </#if>
    </#list>
    <#list procMethod.outputParameters as parameter>
        <#if parameter.column?? && getClassName(parameter.dataType) != "Void" && parameter.column.ordinalPosition gte 1 && parameter.column.ordinalPosition gt maxOrd>
            <#assign maxOrd = parameter.column.ordinalPosition>
        </#if>
    </#list>
    <#-- PostgreSQL SQL functions: JDBC {? = call fn(?,...)} — first ? is the scalar return (metadata ordinal 0). -->
    <#assign usePgFunctionReturnSyntax = (orm.database.dbType == 'POSTGRES') && (outNonVoidCount == 1)
        && firstNonVoidOut?has_content && firstNonVoidOut.column?? && (firstNonVoidOut.column.ordinalPosition == 0)>
    <#-- PostgreSQL CREATE PROCEDURE and MySQL/MariaDB procedures: SQL CALL keyword (not JDBC {call ...} only). -->
    <#assign pgRefCursorSingleOut = (orm.database.dbType == 'POSTGRES') && (outNonVoidCount == 1)
        && (!usePgFunctionReturnSyntax) && firstNonVoidOut?has_content
        && (getClassName(firstNonVoidOut.dataType) == "ResultSet")>
    <#assign useSqlCallKeyword = procMethod.function.catalogProcedure!false &&
        (orm.database.dbType == 'POSTGRES'
        || orm.database.dbType == 'MYSQL'
        || orm.database.dbType == 'MARIADB')>
    /**
    * ${procMethod.name} Method.
    <#list procMethod.inputParameters as parameter>
    * @param ${parameter.name}
    </#list>
    <#if outNonVoidCount == 1>
    * @return ${procMethod.name} output value
    <#elseif outNonVoidCount gt 1>
    <#list procMethod.outputParameters as parameter>
    <#if getClassName(parameter.dataType) != "Void">
    * @param ${parameter.name} single-element array; element 0 receives the output value
    </#if>
    </#list>
    </#if>
    <#if procMethod.exceptions?? && (procMethod.exceptions?size > 0)>
    <#list procMethod.exceptions as exception>
    * @throws ${exception}
    </#list>
    </#if>
    */
    <#if outNonVoidCount == 1>
    public ${getClassName(firstNonVoidOut.dataType)} ${procMethod.name}(
        final DataSource dbDataSource
    <#list procMethod.inputParameters as parameter>
        <#if getClassName(parameter.dataType) != "Void">
        , final ${getClassName(parameter.dataType)} ${parameter.name}
        </#if>
    </#list>
    ) throws SQLException {
        <#if usePgFunctionReturnSyntax>
        try (Connection connection = dbDataSource.getConnection();
                CallableStatement callableStatement = connection
                .prepareCall("{? = call ${procMethod.sqlInvocationName}(<#assign sep=""><#list 1..inCount as i>${sep}?<#assign sep=","></#list>)}")) {
            callableStatement.registerOutParameter(1, ${getColumnType(firstNonVoidOut.column.columnType)} );
            <#assign inSlot = 2>
            <#list procMethod.inputParameters as parameter>
               <#if getClassName(parameter.dataType) != "Void">
               <@emitCallableInBind parameter=parameter ord=inSlot/>
                <#assign inSlot = inSlot + 1>
               </#if>
            </#list>
            callableStatement.execute();
            return ${callableOutScalarExpression("1", firstNonVoidOut.dataType)};
        }
        <#else>
        <#if pgRefCursorSingleOut>
        try (Connection connection = dbDataSource.getConnection()) {
            final boolean __refCursorTxAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            try (CallableStatement callableStatement = connection
                .prepareCall("CALL ${procMethod.sqlInvocationName}(<#assign sep2=""><#list 1..maxOrd as i>${sep2}?<#assign sep2=","></#list>)")) {
            <#list 1..maxOrd as ord>
            <#list procMethod.inputParameters as parameter>
               <#if getClassName(parameter.dataType) != "Void" && parameter.column?? && parameter.column.ordinalPosition == ord>
               <@emitCallableInBind parameter=parameter ord=ord/>
               </#if>
            </#list>
            <#list procMethod.outputParameters as oParameter>
                <#if getClassName(oParameter.dataType) != "Void" && oParameter.column?? && oParameter.column.ordinalPosition == ord>
                      callableStatement.registerOutParameter(${ord}, ${getColumnType(oParameter.column.columnType)} );
                </#if>
            </#list>
            </#list>
                callableStatement.execute();
                final java.sql.ResultSet __refCursorRows = detachRefCursorResultSet((java.sql.ResultSet) callableStatement.getObject(${firstNonVoidOut.column.ordinalPosition?string}, java.sql.ResultSet.class));
                connection.commit();
                return __refCursorRows;
            } catch (java.sql.SQLException __refCursorEx) {
                connection.rollback();
                throw __refCursorEx;
            } finally {
                connection.setAutoCommit(__refCursorTxAutoCommit);
            }
        }
        <#else>
        try (Connection connection = dbDataSource.getConnection();
                CallableStatement callableStatement = connection
                .prepareCall(<#if useSqlCallKeyword>"CALL ${procMethod.sqlInvocationName}(<#assign sep2=""><#list 1..maxOrd as i>${sep2}?<#assign sep2=","></#list>)"<#else>"{call ${procMethod.sqlInvocationName}(<#assign sep2=""><#list 1..maxOrd as i>${sep2}?<#assign sep2=","></#list>)}"</#if>)) {
            <#list 1..maxOrd as ord>
            <#list procMethod.inputParameters as parameter>
               <#if getClassName(parameter.dataType) != "Void" && parameter.column?? && parameter.column.ordinalPosition == ord>
               <@emitCallableInBind parameter=parameter ord=ord/>
               </#if>
            </#list>
            <#list procMethod.outputParameters as oParameter>
                <#if getClassName(oParameter.dataType) != "Void" && oParameter.column?? && oParameter.column.ordinalPosition == ord>
                      callableStatement.registerOutParameter(${ord}, ${getColumnType(oParameter.column.columnType)} );
                </#if>
            </#list>
            </#list>
            callableStatement.execute();
            return ${callableOutScalarExpression(firstNonVoidOut.column.ordinalPosition?string, firstNonVoidOut.dataType)};
        }
        </#if>
        </#if>
    }
    <#elseif outNonVoidCount gt 1>
    public void ${procMethod.name}(
        final DataSource dbDataSource
    <#list procMethod.inputParameters as parameter>
        <#if getClassName(parameter.dataType) != "Void">
        , final ${getClassName(parameter.dataType)} ${parameter.name}
        </#if>
    </#list>
    <#list procMethod.outputParameters as parameter>
        <#if getClassName(parameter.dataType) != "Void">
        , final ${getClassName(parameter.dataType)}[] ${parameter.name}
        </#if>
    </#list>
    ) throws SQLException {
        try (Connection connection = dbDataSource.getConnection();
                CallableStatement callableStatement = connection
                .prepareCall(<#if useSqlCallKeyword>"CALL ${procMethod.sqlInvocationName}(<#assign sep3=""><#list 1..maxOrd as i>${sep3}?<#assign sep3=","></#list>)"<#else>"{call ${procMethod.sqlInvocationName}(<#assign sep3=""><#list 1..maxOrd as i>${sep3}?<#assign sep3=","></#list>)}"</#if>)) {
            <#list 1..maxOrd as ord>
            <#list procMethod.inputParameters as parameter>
               <#if getClassName(parameter.dataType) != "Void" && parameter.column?? && parameter.column.ordinalPosition == ord>
               <@emitCallableInBind parameter=parameter ord=ord/>
               </#if>
            </#list>
            <#list procMethod.outputParameters as oParameter>
                <#if getClassName(oParameter.dataType) != "Void" && oParameter.column?? && oParameter.column.ordinalPosition == ord>
                      callableStatement.registerOutParameter(${ord}, ${getColumnType(oParameter.column.columnType)} );
                </#if>
            </#list>
            </#list>
            callableStatement.execute();
            <#list procMethod.outputParameters as oParameter>
                <#if getClassName(oParameter.dataType) != "Void" && oParameter.column??>
                  ${oParameter.name}[0] = ${callableOutScalarExpression(oParameter.column.ordinalPosition?string, oParameter.dataType)};
                </#if>
            </#list>
        }
    }
    <#else>
    public void ${procMethod.name}(
        final DataSource dbDataSource
    <#list procMethod.inputParameters as parameter>
        <#if getClassName(parameter.dataType) != "Void">
        , final ${getClassName(parameter.dataType)} ${parameter.name}
        </#if>
    </#list>
    ) throws SQLException {
        SqlBuilder.prepareCall(<#if useSqlCallKeyword>"CALL ${procMethod.sqlInvocationName}(<#assign sep2=""><#list 1..inCount as i>${sep2}?<#assign sep2=","></#list>)"<#else>"call ${procMethod.sqlInvocationName}(<#assign sep2=""><#list 1..inCount as i>${sep2}?<#assign sep2=","></#list>)"</#if>)
            <#list procMethod.inputParameters as parameter>
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
                 <#case "java.sql.Array">
                 <#case "java.sql.Struct">
                 <#case "java.lang.Object">
                 <#case "java.lang.Integer[]">
                 <#case "java.lang.Long[]">
                 <#case "java.lang.Short[]">
                 <#case "java.lang.Float[]">
                 <#case "java.lang.Double[]">
                 <#case "java.lang.String[]">
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
