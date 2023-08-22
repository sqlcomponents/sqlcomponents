package org.example.model;

import java.time.LocalDateTime;
 /**
  * Data Holder for the table - flyway_schema_history.
  */
public final class FlywaySchemaHistory {

    /**
     * holds value of the column installed_rank.
     */
    private Long installedRank;
    /**
     * holds value of the column version.
     */
    private String version;
    /**
     * holds value of the column description.
     */
    private String description;
    /**
     * holds value of the column type.
     */
    private String type;
    /**
     * holds value of the column script.
     */
    private String script;
    /**
     * holds value of the column checksum.
     */
    private Long checksum;
    /**
     * holds value of the column installed_by.
     */
    private String installedBy;
    /**
     * holds value of the column installed_on.
     */
    private LocalDateTime installedOn;
    /**
     * holds value of the column execution_time.
     */
    private Long executionTime;
    /**
     * holds value of the column success.
     */
    private Boolean success;
    /**
     * gets value of column - installed_rank.
	 *
     * @return installedRank
     */
    public Long getInstalledRank() {
        return installedRank;
	}

	/**
	 * sets value of column - installed_rank.
	 *
	 * @param theInstalledRank
	 */
	public void setInstalledRank(final Long theInstalledRank) {
		this.installedRank = theInstalledRank;
	}
    /**
     * gets value of column - version.
	 *
     * @return version
     */
    public String getVersion() {
        return version;
	}

	/**
	 * sets value of column - version.
	 *
	 * @param theVersion
	 */
	public void setVersion(final String theVersion) {
		this.version = theVersion;
	}
    /**
     * gets value of column - description.
	 *
     * @return description
     */
    public String getDescription() {
        return description;
	}

	/**
	 * sets value of column - description.
	 *
	 * @param theDescription
	 */
	public void setDescription(final String theDescription) {
		this.description = theDescription;
	}
    /**
     * gets value of column - type.
	 *
     * @return type
     */
    public String getType() {
        return type;
	}

	/**
	 * sets value of column - type.
	 *
	 * @param theType
	 */
	public void setType(final String theType) {
		this.type = theType;
	}
    /**
     * gets value of column - script.
	 *
     * @return script
     */
    public String getScript() {
        return script;
	}

	/**
	 * sets value of column - script.
	 *
	 * @param theScript
	 */
	public void setScript(final String theScript) {
		this.script = theScript;
	}
    /**
     * gets value of column - checksum.
	 *
     * @return checksum
     */
    public Long getChecksum() {
        return checksum;
	}

	/**
	 * sets value of column - checksum.
	 *
	 * @param theChecksum
	 */
	public void setChecksum(final Long theChecksum) {
		this.checksum = theChecksum;
	}
    /**
     * gets value of column - installed_by.
	 *
     * @return installedBy
     */
    public String getInstalledBy() {
        return installedBy;
	}

	/**
	 * sets value of column - installed_by.
	 *
	 * @param theInstalledBy
	 */
	public void setInstalledBy(final String theInstalledBy) {
		this.installedBy = theInstalledBy;
	}
    /**
     * gets value of column - installed_on.
	 *
     * @return installedOn
     */
    public LocalDateTime getInstalledOn() {
        return installedOn;
	}

	/**
	 * sets value of column - installed_on.
	 *
	 * @param theInstalledOn
	 */
	public void setInstalledOn(final LocalDateTime theInstalledOn) {
		this.installedOn = theInstalledOn;
	}
    /**
     * gets value of column - execution_time.
	 *
     * @return executionTime
     */
    public Long getExecutionTime() {
        return executionTime;
	}

	/**
	 * sets value of column - execution_time.
	 *
	 * @param theExecutionTime
	 */
	public void setExecutionTime(final Long theExecutionTime) {
		this.executionTime = theExecutionTime;
	}
    /**
     * gets value of column - success.
	 *
     * @return success
     */
    public Boolean getSuccess() {
        return success;
	}

	/**
	 * sets value of column - success.
	 *
	 * @param theSuccess
	 */
	public void setSuccess(final Boolean theSuccess) {
		this.success = theSuccess;
	}

}

