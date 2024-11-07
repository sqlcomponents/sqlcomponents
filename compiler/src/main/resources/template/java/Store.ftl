<#include "jdbcbase.ftl">
<#import "Column.ftl" as columns>
package <#if daoPackage?? && daoPackage?length != 0 >${daoPackage}</#if>;

import java.sql.ResultSet;
import javax.sql.DataSource;
import java.sql.SQLException;

import java.util.List;
import java.util.stream.Collectors;

<#assign capturedOutput>
    /**
    * Datastore for the table - ${table.tableName}.
    */
    public final class ${name}Store  {

    /**
    * Retrieves an instance of ${name}Store.
    *
    * @param theDataManager  The DataManager instance.
    * @param theObserver     The observer to notify for data changes.
    * @return an instance of ${name}Store
    */
    public static ${name}Store get${name}Store(
    final DataManager theDataManager
    ,final DataManager.Observer theObserver

    <#if containsEncryptedProperty() >
        <#assign a=addImportStatement("java.util.function.Function")>
        ,final Function<String,String> encryptionFunction
        ,final Function<String,String> decryptionFunction
    </#if>) {
    return new ${name}Store(
    theDataManager
    ,theObserver

    <#if containsEncryptedProperty() >
        <#assign a=addImportStatement("java.util.function.Function")>
        ,encryptionFunction
        ,decryptionFunction
    </#if>);

    }

    private final DataManager dataManager;

    private final DataManager.Observer observer;
    <#assign a=addImportStatement(orm.application.rootPackage+ ".DataManager")>
    <#assign a=addImportStatement(orm.application.rootPackage+ ".DataManager.Value")>
    <#assign a=addImportStatement(orm.application.rootPackage+ ".DataManager.PartialWhereClause")>
    <#assign a=addImportStatement(orm.application.rootPackage+ ".DataManager.WhereClause")>


    <#if containsEncryptedProperty() >
        private final Function<String,String> encryptionFunction;
        private final Function<String,String> decryptionFunction;
    </#if>

    /**
    * Constructor for ${name}Store.
    *
    * @param theDataManager   The DataManager instance.
    * @param theObserver      The observer for data changes.
    */
    private ${name}Store(
    final DataManager theDataManager
    ,final DataManager.Observer theObserver


    <#if containsEncryptedProperty() >
        <#assign a=addImportStatement("java.util.function.Function")>
        ,final Function<String,String> encryptionFunction
        ,final Function<String,String> decryptionFunction
    </#if>
    ) {

    this.dataManager = theDataManager;
    this.observer = theObserver;

    <#if containsEncryptedProperty() >
        this.encryptionFunction = encryptionFunction;
        this.decryptionFunction = decryptionFunction;
    </#if>


    }

    <#list orm.methodSpecification as method>
        <#include "method/${method}.ftl">
    </#list>

    <#if (returningProperties?size > 0) >
    /**
    * Maps a row from ResultSet for returning properties.
    *
    * @param rs The ResultSet.
    * @param inserting${name} The inserting ${name} instance.
    * @return A new ${name} object.
    * @throws SQLException if any SQL error occurs.
    */
    private ${name} rowMapperForReturning(final ResultSet rs,final ${name} inserting${name}) throws <@throwsblock/>{
    return new ${name}(

<#assign comma=0>

    <#assign index=1>
    <#list properties as property>
    <#if comma != 0>
    ,
</#if>

        <#if property.returning>
        ${property.name}().get(rs,${index})
        <#assign index = index + 1>
        <#else>
        inserting${name}.${property.name}()
        </#if>
        
        <#assign comma=1>
    </#list>
    );
    
    }

    </#if>
    /**
    * Maps a row from ResultSet.
    *
    * @param rs The ResultSet.
    * @return A new ${name} object.
    * @throws SQLException if any SQL error occurs.
    */
    private ${name} rowMapper(ResultSet rs) throws <@throwsblock/> {
    return new ${name}(
    <#assign index=1>
    <#list properties as property>
    <#if index != 1>
    ,
</#if>
        ${property.name}().get(rs,${index})
        <#assign index = index + 1>
    </#list>
    );
    
    }



    <#list properties as property>
        <#assign a=addImportStatement(property.dataType)>
        /**
        * Creates a Value for ${property.name}.
        *
        * @param value The value of type ${getClassName(property.dataType)}.
        * @return A Value object.
        */
        public static Value<Column.${property.name?cap_first}Column,${getClassName(property.dataType)}> ${property.name}(final ${getClassName(property.dataType)} value) {
        return new Value<>(${property.name}(),value);
        }

        /**
        * Retrieves the column for ${property.name}.
        *
        * @return The column for ${property.name}.
        */
        public static Column.${property.name?cap_first}Column ${property.name}() {
            final WhereClause<Column.${property.name?cap_first}Column> whereClause = new WhereClause<>();

            Column.${property.name?cap_first}Column column = new Column.${property.name?cap_first}Column(whereClause);

            whereClause.add(column);

            return column;
        }

    </#list>

    /**
    * Abstract class for defining columns in the DataManager.
    */
    public static abstract class Column<T> implements DataManager.Column<T> {

    private final PartialWhereClause  whereClause ;

    protected Column(final PartialWhereClause  whereClause) {
    this.whereClause  = whereClause ;
    }

    protected WhereClause  getWhereClause() {
    return (WhereClause) whereClause ;
    }


    <#list properties as property>
        <#switch property.dataType>
            <#case "java.lang.String">
                <#if property.column.typeName == "macaddr8" >
                    <#assign a=addImportStatement("org.postgresql.util.PGobject")>
                <#elseif property.column.typeName == "macaddr" >
                    <#assign a=addImportStatement("org.postgresql.util.PGobject")>
                <#elseif property.column.typeName == "path">
                    <#assign a=addImportStatement("org.postgresql.util.PGobject")>
                </#if>
                <@columns.StringColumn property=property/>
                <#break>           
            <#case "java.lang.Character">
                <@columns.CharacterColumn property=property/>
                <#break>
            <#case "java.lang.Integer">
                <@columns.IntegerColumn property=property/>
                <#break>
            <#case "java.lang.Short">
                <@columns.ShortColumn property=property/>
                <#break>
            <#case "java.lang.Byte">
                <@columns.ByteColumn property=property/>
                <#break>
            <#case "java.lang.Long">
                <@columns.LongColumn property=property/>
                <#break>
            <#case "java.lang.Float">
                <@columns.FloatColumn property=property/>
                <#break>
            <#case "java.lang.Double">
                <@columns.DoubleColumn property=property/>
                <#break>
            <#case "java.math.BigDecimal">
                <@columns.BigDecimalColumn property=property/>
                <#break>
            <#case "java.lang.Boolean">
                <@columns.BooleanColumn property=property/>
                <#break>
            <#case "java.util.BitSet">
                <@columns.BitSetColumn property=property/>
                <#break>
            <#case "java.time.LocalDate">
                <@columns.LocalDateColumn property=property/>
                <#break>
            <#case "java.time.LocalTime">
                <@columns.LocalTimeColumn property=property/>
                <#break>
            <#case "java.time.LocalDateTime">
                <@columns.LocalDateTimeColumn property=property/>
                <#break>
            <#case "java.util.UUID">
                <@columns.UUIDColumn property=property/>
                <#break>
            <#case "com.fasterxml.jackson.databind.JsonNode" >
                <#assign a=addImportStatement("com.fasterxml.jackson.databind.ObjectMapper")>
                <#assign a=addImportStatement("com.fasterxml.jackson.databind.JsonNode")>
                <#assign a=addImportStatement("com.fasterxml.jackson.core.JsonProcessingException")>
                <@columns.JsonNodeColumn property=property/>
                <#break>
            <#case "java.nio.ByteBuffer" >
                <@columns.ByteBuffer property=property/>
                <#break>
            <#case "java.time.Duration" >
                <#assign a=addImportStatement("org.postgresql.util.PGobject")>
                <#assign a=addImportStatement("org.postgresql.util.PGInterval")>
                <#assign a=addImportStatement("java.time.temporal.ChronoUnit")>
                <@columns.DurationColumn property=property/>
                <#break>
            <#case "org.locationtech.spatial4j.shape.Circle">
                <#assign a=addImportStatement("org.locationtech.spatial4j.shape.Circle")>
                <#assign a=addImportStatement("org.locationtech.spatial4j.context.SpatialContext")>
                <#assign a=addImportStatement("org.postgresql.geometric.PGpoint")>
                <#assign a=addImportStatement("org.postgresql.geometric.PGcircle")>
                <@columns.CircleColumn property=property/>
                <#break>
            <#case "org.locationtech.jts.geom.Point">
                <#assign a=addImportStatement("org.locationtech.jts.geom.GeometryFactory")>
                <#assign a=addImportStatement("org.locationtech.jts.geom.Coordinate")>
                <#assign a=addImportStatement("org.postgresql.geometric.PGpoint")>
                <@columns.PointColumn property=property/>
                <#break>
            

            <#case "org.locationtech.jts.geom.Envelope" >
                <#assign a=addImportStatement("org.locationtech.jts.geom.Envelope")>
    <#assign a=addImportStatement("org.postgresql.geometric.PGbox")>
                <@columns.BoxColumn property=property/>
                <#break>
            
            <#case "java.net.InetAddress" >
            <#assign a=addImportStatement("java.net.InetAddress")>
            <#assign a=addImportStatement("java.net.UnknownHostException")>
                <@columns.InetAddressColumn property=property/>
                <#break> 
                 
            <#case "org.apache.commons.net.util.SubnetUtils" >
                <@columns.CidrColumn property=property/>
                <#break>
           
            <#case "org.locationtech.jts.geom.Polygon" >
                    <#assign a=addImportStatement("org.postgresql.geometric.PGpolygon")>
                    <#assign a=addImportStatement("org.locationtech.jts.geom.Coordinate")>
                    <#assign a=addImportStatement("org.locationtech.jts.geom.GeometryFactory")>
                    <#assign a=addImportStatement("org.locationtech.jts.geom.LinearRing")>
                    <#assign a=addImportStatement("org.locationtech.jts.geom.Polygon")>
                    <#assign a=addImportStatement("org.locationtech.jts.io.ParseException")>
                    <@columns.PolygonColumn property=property/>
                    <#break>
              
             <#case "org.locationtech.jts.geom.LineSegment" >
                     <#assign a=addImportStatement("org.locationtech.jts.geom.GeometryFactory")>
                    <#assign a=addImportStatement("org.locationtech.jts.geom.Coordinate")>
                    <#assign a=addImportStatement("org.locationtech.jts.geom.LineSegment")>
                    <#assign a=addImportStatement("org.postgresql.geometric.PGpoint")>
                    <#assign a=addImportStatement("org.postgresql.geometric.PGlseg")>
                      <@columns.LineSegmentColumn property=property/>
                    <#break>

            

            <#case "org.locationtech.jts.geom.LineString" >
                    <#assign a=addImportStatement("org.locationtech.jts.geom.GeometryFactory")>
                    <#assign a=addImportStatement("org.locationtech.jts.geom.Coordinate")>
                    <#assign a=addImportStatement("org.locationtech.jts.geom.LineString")>
                    <#assign a=addImportStatement("org.postgresql.geometric.PGpoint")>
                    <#assign a=addImportStatement("org.postgresql.geometric.PGline")>
                    <@columns.LineColumn property=property/>
                    <#break>

              <#default>
                    <#if property.column.columnType == "ENUM">
                        <@columns.TypeColumn property=property/>
                    </#if>


        </#switch>
    </#list>

    }

    <#include "ConvenienceMethods.ftl">


    }<#assign a=addImportStatement("java.util.ArrayList")><#assign a=addImportStatement("java.time.LocalDate")>
</#assign>
<@importStatements/>

${capturedOutput}