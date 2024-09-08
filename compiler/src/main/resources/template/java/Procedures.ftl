public Procedure call() {
    return new Procedure();
}

final class Procedure {
    <#list orm.methods as method>
    // ${method.name}
    </#list>
}