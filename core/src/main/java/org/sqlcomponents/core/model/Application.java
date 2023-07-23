package org.sqlcomponents.core.model;

import org.sqlcomponents.core.compiler.Compiler;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * The type Application.
 */
public final class Application {

    /**
     * The constant METHOD_SPECIFICATION.
     */
    public static final List<String> METHOD_SPECIFICATION =
            Arrays.asList("SelectStatement",
                    "InsertStatement", "DeleteStatement", "MViewRefresh",
                    "UpdateStatement");
    /**
     * The Name.
     */
    private String name;
    /**
     * The Location.
     */
    private String location;
    /**
     * The Src folder.
     */
    private String srcFolder;
    /**
     * The Driver name.
     */
    private String driverName;
    /**
     * The Table patterns.
     */
    private List<String> tablePatterns;
    /**
     * The Sequence patterns.
     */
    private List<String> sequencePatterns;
    /**
     * The Database word separator.
     */
    private String databaseWordSeparator = "_";
    /**
     * The Root package.
     */
    private String rootPackage;
    /**
     * The Words map.
     */
    private Map<String, String> wordsMap;
    /**
     * The Modules map.
     */
    private Map<String, String> modulesMap;
    /**
     * The Plural map.
     */
    private Map<String, String> pluralMap;

    /**
     * The Sequence table map.
     */
    private String sequenceTableMap;

    /**
     * The Modules first.
     */
    private boolean modulesFirst;

    /**
     * The Orm.
     */
    private ORM orm;

    /**
     * Instantiates a new Application.
     */
    public Application() {
        setOrm(new ORM(this));
    }

    /**
     * Gets driver name.
     *
     * @return the driver name
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * Sets driver name.
     *
     * @param paramDriverName the param driver name
     */
    public void setDriverName(final String paramDriverName) {
        this.driverName = paramDriverName;
    }

    /**
     * Gets sequence patterns.
     *
     * @return the sequence patterns
     */
    public List<String> getSequencePatterns() {
        return sequencePatterns;
    }

    /**
     * Sets sequence patterns.
     *
     * @param paramSequencePatterns the param sequence patterns
     */
    public void setSequencePatterns(final List<String> paramSequencePatterns) {
        this.sequencePatterns = paramSequencePatterns;
    }

    /**
     * Gets database word separator.
     *
     * @return the database word separator
     */
    public String getDatabaseWordSeparator() {
        return databaseWordSeparator;
    }

    /**
     * Sets database word separator.
     *
     * @param paramDatabaseWordSeparator the param database word separator
     */
    public void setDatabaseWordSeparator(
            final String paramDatabaseWordSeparator) {
        this.databaseWordSeparator = paramDatabaseWordSeparator;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param paramName the param name
     */
    public void setName(final String paramName) {
        this.name = paramName;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param paramLocation the param location
     */
    public void setLocation(final String paramLocation) {
        this.location = paramLocation;
    }

    /**
     * Gets table patterns.
     *
     * @return the table patterns
     */
    public List<String> getTablePatterns() {
        return tablePatterns;
    }

    /**
     * Sets table patterns.
     *
     * @param paramTablePatterns the param table patterns
     */
    public void setTablePatterns(final List<String> paramTablePatterns) {
        this.tablePatterns = paramTablePatterns;
    }

    /**
     * Gets root package.
     *
     * @return the root package
     */
    public String getRootPackage() {
        return rootPackage;
    }

    /**
     * Sets root package.
     *
     * @param paramRootPackage the param root package
     */
    public void setRootPackage(final String paramRootPackage) {
        this.rootPackage = paramRootPackage;
    }

    /**
     * Gets words map.
     *
     * @return the words map
     */
    public Map<String, String> getWordsMap() {
        return wordsMap;
    }

    /**
     * Sets words map.
     *
     * @param paramWordsMap the param words map
     */
    public void setWordsMap(final Map<String, String> paramWordsMap) {
        this.wordsMap = paramWordsMap;
    }

    /**
     * Gets modules map.
     *
     * @return the modules map
     */
    public Map<String, String> getModulesMap() {
        return modulesMap;
    }

    /**
     * Sets modules map.
     *
     * @param paramModulesMap the param modules map
     */
    public void setModulesMap(final Map<String, String> paramModulesMap) {
        this.modulesMap = paramModulesMap;
    }

    /**
     * Gets sequence table map.
     *
     * @return the sequence table map
     */
    public String getSequenceTableMap() {
        return sequenceTableMap;
    }

    /**
     * Sets sequence table map.
     *
     * @param paramSequenceTableMap the param sequence table map
     */
    public void setSequenceTableMap(final String paramSequenceTableMap) {
        this.sequenceTableMap = paramSequenceTableMap;
    }

    /**
     * Gets orm.
     *
     * @return the orm
     */
    public ORM getOrm() {
        return orm;
    }

    /**
     * Sets orm.
     *
     * @param paramOrm the param orm
     */
    public void setOrm(final ORM paramOrm) {
        this.orm = paramOrm;
    }

    /**
     * Gets entities.
     *
     * @return the entities
     */
    public List<Entity> getEntities() {
        return orm.getEntities();
    }

    /**
     * Sets entities.
     *
     * @param paramEntities the param entities
     */
    public void setEntities(final List<Entity> paramEntities) {
        orm.setEntities(paramEntities);
    }

    /**
     * Gets insert map.
     *
     * @return the insert map
     */
    public Map<String, String> getInsertMap() {
        return orm.getInsertMap();
    }

    /**
     * Sets insert map.
     *
     * @param paramInsertMap the param insert map
     */
    public void setInsertMap(final Map<String, String> paramInsertMap) {
        orm.setInsertMap(paramInsertMap);
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return orm.getPassword();
    }

    /**
     * Sets password.
     *
     * @param paramPassword the param password
     */
    public void setPassword(final String paramPassword) {
        orm.setPassword(paramPassword);
    }

    /**
     * Gets update map.
     *
     * @return the update map
     */
    public Map<String, String> getUpdateMap() {
        return orm.getUpdateMap();
    }

    /**
     * Sets update map.
     *
     * @param paramUpdateMap the param update map
     */
    public void setUpdateMap(final Map<String, String> paramUpdateMap) {
        orm.setUpdateMap(paramUpdateMap);
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return orm.getUrl();
    }

    /**
     * Sets url.
     *
     * @param paramUrl the param url
     */
    public void setUrl(final String paramUrl) {
        orm.setUrl(paramUrl);
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return orm.getUserName();
    }

    /**
     * Sets user name.
     *
     * @param paramUserName the param user name
     */
    public void setUserName(final String paramUserName) {
        orm.setUserName(paramUserName);
    }

    /**
     * Returns hash code.
     * @return hashCode
     */
    public int hashCode() {
        return orm.hashCode();
    }
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Application) {
            Application app = (Application) obj;
            return orm.equals(app.getOrm());
        } else {
            return false;
        }
    }

    /**
     * Gets method specification.
     *
     * @return the method specification
     */
    public List<String> getMethodSpecification() {
        return orm.getMethodSpecification();
    }

    /**
     * Sets method specification.
     *
     * @param paramMethodSpecification the param method specification
     */
    public void setMethodSpecification(
            final List<String> paramMethodSpecification) {
        orm.setMethodSpecification(paramMethodSpecification);
    }

    /**
     * Returns String representation of this object.
     * @return name
     */
    public String toString() {
        return name;
    }

    /**
     * Gets schema name.
     *
     * @return the schema name
     */
    public String getSchemaName() {
        return orm.getSchemaName();
    }

    /**
     * Sets schema name.
     *
     * @param paramSchemaName the param schema name
     */
    public void setSchemaName(final String paramSchemaName) {
        orm.setSchemaName(paramSchemaName);
    }

    /**
     * Gets plural map.
     *
     * @return the plural map
     */
    public Map<String, String> getPluralMap() {
        return pluralMap;
    }

    /**
     * Sets plural map.
     *
     * @param paramPluralMap the param plural map
     */
    public void setPluralMap(final Map<String, String> paramPluralMap) {
        this.pluralMap = paramPluralMap;
    }

    /**
     * Is modules first boolean.
     *
     * @return the boolean
     */
    public boolean isModulesFirst() {
        return modulesFirst;
    }

    /**
     * Sets modules first.
     *
     * @param paramModulesFirst the param modules first
     */
    public void setModulesFirst(final boolean paramModulesFirst) {
        this.modulesFirst = paramModulesFirst;
    }

    /**
     * Gets src folder.
     *
     * @return the src folder
     */
    public String getSrcFolder() {
        return srcFolder;
    }

    /**
     * Sets src folder.
     *
     * @param paramSrcFolder the param src folder
     */
    public void setSrcFolder(final String paramSrcFolder) {
        this.srcFolder = paramSrcFolder;
    }

    /**
     * Gets encryption.
     *
     * @return the encryption
     */
    public List<String> getEncryption() {
        return orm.getEncryption();
    }

    /**
     * Sets encryption.
     *
     * @param paramEncryption the param encryption
     */
    public void setEncryption(final List<String> paramEncryption) {
        orm.setEncryption(paramEncryption);
    }

    /**
     * Compile.
     *
     * @param aCompiler the a compiler
     * @throws Exception the exception
     */
    public void compile(final Compiler aCompiler) throws Exception {

        File aSrcFolder = new File(this.getSrcFolder());
        if (aSrcFolder.exists()) {
            Files.walk(aSrcFolder.toPath()).sorted(Comparator.reverseOrder())
                    .map(Path::toFile).forEach(File::delete);
        }

        aCompiler.compile(this);
    }
}
