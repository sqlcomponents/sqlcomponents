<#include "/template/java/jdbcbase.ftl">
<#import "/template/java/columns.ftl" as columns>
package <#if daoPackage?? && daoPackage?length != 0 >${daoPackage}</#if>;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.stream.Collectors;

<#assign capturedOutput>
    /**
    * Datastore for the table - ${table.tableName}.
    */
    public final class ${name}Store  {



    public static ${name}Store get${name}Store(final javax.sql.DataSource theDataSource
    ,final DatabaseManager.Observer theObserver


    <#if containsEncryptedProperty() >
        <#assign a=addImportStatement("java.util.function.Function")>
        ,final Function<String,String> encryptionFunction
        ,final Function<String,String> decryptionFunction
    </#if>) {
    return new ${name}Store(theDataSource
    ,theObserver


    <#if containsEncryptedProperty() >
        <#assign a=addImportStatement("java.util.function.Function")>
        ,encryptionFunction
        ,decryptionFunction
    </#if>);

    }

    private final javax.sql.DataSource dbDataSource;

    private final DatabaseManager.Observer observer;
    <#assign a=addImportStatement(orm.application.rootPackage+ ".DatabaseManager")>
    <#assign a=addImportStatement(orm.application.rootPackage+ ".DatabaseManager.Value")>


    <#if containsEncryptedProperty() >
        private final Function<String,String> encryptionFunction;
        private final Function<String,String> decryptionFunction;
    </#if>

    /**
    * Datastore
    */
    private ${name}Store(final javax.sql.DataSource theDataSource
    ,final DatabaseManager.Observer theObserver


    <#if containsEncryptedProperty() >
        <#assign a=addImportStatement("java.util.function.Function")>
        ,final Function<String,String> encryptionFunction
        ,final Function<String,String> decryptionFunction
    </#if>
    ) {
    this.dbDataSource = theDataSource;
    this.observer = theObserver;

    <#if containsEncryptedProperty() >
        this.encryptionFunction = encryptionFunction;
        this.decryptionFunction = decryptionFunction;
    </#if>


    }

    <#list orm.methodSpecification as method>
        <#include "/template/java/method/${method}.ftl">
    </#list>

    <#if (returningProperties?size > 0) >

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
        inserting${name}.get${property.name?cap_first}()
        </#if>
        
        <#assign comma=1>
    </#list>
    );
    
    }

    </#if>

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

        public static Value<Column.${property.name?cap_first}Column,${getClassName(property.dataType)}> ${property.name}(final ${getClassName(property.dataType)} value) {
        return new Value<>(${property.name}(),value);
        }

        public static Column.${property.name?cap_first}Column ${property.name}() {
        return new WhereClause().${property.name}();
        }

    </#list>

    public static class WhereClause  extends PartialWhereClause  {
    private WhereClause(){
    super();
    }
    private String asSql() {
    return nodes.isEmpty() ? null : nodes.stream().map(node -> {
    String asSql;
    if (node instanceof Column) {
    asSql = ((Column) node).asSql();
    } else if (node instanceof WhereClause) {
    asSql = "(" + ((WhereClause) node).asSql() + ")";
    } else {
    asSql = (String) node;
    }
    return asSql;
    }).collect(Collectors.joining(" "));
    }

    public PartialWhereClause and() {
    this.nodes.add("AND");
    return this;
    }

    public PartialWhereClause  or() {
    this.nodes.add("OR");
    return this;
    }

    public WhereClause  and(final WhereClause  whereClause) {
    this.nodes.add("AND");
    this.nodes.add(whereClause);
    return (WhereClause) this;
    }

    public WhereClause  or(final WhereClause  whereClause) {
    this.nodes.add("OR");
    this.nodes.add(whereClause);
    return (WhereClause) this;
    }
    }

    public static class PartialWhereClause  {

    protected final List<Object> nodes;

    private PartialWhereClause() {
    this.nodes = new ArrayList<>();
    }


    <#list properties as property>

        public Column.${property.name?cap_first}Column ${property.name}() {
        Column.${property.name?cap_first}Column query = new Column.${property.name?cap_first}Column(this);
        this.nodes.add(query);
        return query;
        }

    </#list>





    }




    public static abstract class Column<T> implements DatabaseManager.Column<T> {


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

            

        </#switch>
    </#list>

    }


    }<#assign a=addImportStatement("java.util.ArrayList")><#assign a=addImportStatement("java.time.LocalDate")>
</#assign>
<@importStatements/>

${capturedOutput}