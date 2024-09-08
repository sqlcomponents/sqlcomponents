public Procedure call() {
    return new Procedure();
}

public final class Procedure {
    <#list orm.methods as method>
    public void ${method.name}(){

    }
    </#list>
}