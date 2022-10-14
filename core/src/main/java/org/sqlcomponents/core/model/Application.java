package org.sqlcomponents.core.model;

import org.sqlcomponents.core.compiler.Compiler;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Application {
    public static final List<String> METHOD_SPECIFICATION = Arrays.asList("SelectStatement", "InsertStatement",
            "DeleteStatement", "MViewRefresh", "UpdateStatement");
    private String name;
    private String location;
    private String srcFolder;
    private String driverName;
    private List<String> tablePatterns;
    private List<String> sequencePatterns;
    private String databaseWordSeparator = "_";
    private String rootPackage;
    private Map<String, String> wordsMap;
    private Map<String, String> modulesMap;
    private Map<String, String> pluralMap;

    private String sequenceTableMap;

    private boolean modulesFirst;

    private ORM orm;

    public Application() {
        setOrm(new ORM(this));
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(final String driverName) {
        this.driverName = driverName;
    }

    public List<String> getSequencePatterns() {
        return sequencePatterns;
    }

    public void setSequencePatterns(List<String> sequencePatterns) {
        this.sequencePatterns = sequencePatterns;
    }

    public String getDatabaseWordSeparator() {
        return databaseWordSeparator;
    }

    public void setDatabaseWordSeparator(String databaseWordSeparator) {
        this.databaseWordSeparator = databaseWordSeparator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getTablePatterns() {
        return tablePatterns;
    }

    public void setTablePatterns(List<String> tablePatterns) {
        this.tablePatterns = tablePatterns;
    }

    public String getRootPackage() {
        return rootPackage;
    }

    public void setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
    }

    public Map<String, String> getWordsMap() {
        return wordsMap;
    }

    public void setWordsMap(Map<String, String> wordsMap) {
        this.wordsMap = wordsMap;
    }

    public Map<String, String> getModulesMap() {
        return modulesMap;
    }

    public void setModulesMap(Map<String, String> modulesMap) {
        this.modulesMap = modulesMap;
    }

    public String getSequenceTableMap() {
        return sequenceTableMap;
    }

    public void setSequenceTableMap(String sequenceTableMap) {
        this.sequenceTableMap = sequenceTableMap;
    }

    public ORM getOrm() {
        return orm;
    }

    public void setOrm(ORM orm) {
        this.orm = orm;
    }

    public List<Entity> getEntities() {
        return orm.getEntities();
    }

    public void setEntities(List<Entity> entities) {
        orm.setEntities(entities);
    }

    public Map<String, String> getInsertMap() {
        return orm.getInsertMap();
    }

    public void setInsertMap(Map<String, String> insertMap) {
        orm.setInsertMap(insertMap);
    }

    public String getPassword() {
        return orm.getPassword();
    }

    public void setPassword(String password) {
        orm.setPassword(password);
    }

    public Map<String, String> getUpdateMap() {
        return orm.getUpdateMap();
    }

    public void setUpdateMap(Map<String, String> updateMap) {
        orm.setUpdateMap(updateMap);
    }

    public String getUrl() {
        return orm.getUrl();
    }

    public void setUrl(String url) {
        orm.setUrl(url);
    }

    public String getUserName() {
        return orm.getUserName();
    }

    public void setUserName(String userName) {
        orm.setUserName(userName);
    }

    public int hashCode() {
        return orm.hashCode();
    }

    public List<String> getMethodSpecification() {
        return orm.getMethodSpecification();
    }

    public void setMethodSpecification(List<String> methodSpecification) {
        orm.setMethodSpecification(methodSpecification);
    }

    public String toString() {
        return name;
    }

    public String getSchemaName() {
        return orm.getSchemaName();
    }

    public void setSchemaName(String schemaName) {
        orm.setSchemaName(schemaName);
    }

    public Map<String, String> getPluralMap() {
        return pluralMap;
    }

    public void setPluralMap(Map<String, String> pluralMap) {
        this.pluralMap = pluralMap;
    }

    public boolean isModulesFirst() {
        return modulesFirst;
    }

    public void setModulesFirst(boolean modulesFirst) {
        this.modulesFirst = modulesFirst;
    }

    public String getSrcFolder() {
        return srcFolder;
    }

    public void setSrcFolder(String srcFolder) {
        this.srcFolder = srcFolder;
    }

    public List<String> getEncryption() {
        return orm.getEncryption();
    }

    public void setEncryption(final List<String> encryption) {
        orm.setEncryption(encryption);
    }

    public void compile(final Compiler aCompiler) throws Exception {

        File srcFolder = new File(this.getSrcFolder());
        if (srcFolder.exists()) {
            Files.walk(srcFolder.toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        }

        aCompiler.compile(this);
    }
}
