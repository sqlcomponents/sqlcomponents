package org.example.model;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import org.json.JSONObject;
 /**
  * Data Holder for the table - raja.
  */
public final class Raja {

    /**
     * holds value of the column id.
     */
    private Long id;
    /**
     * holds value of the column a_boolean.
     */
    private Boolean aBoolean;
    /**
     * holds value of the column reference_code.
     */
    private String referenceCode;
    /**
     * holds value of the column a_char.
     */
    private Character aChar;
    /**
     * holds value of the column a_text.
     */
    private String aText;
    /**
     * holds value of the column a_encrypted_text.
     */
    private String aEncryptedText;
    /**
     * holds value of the column a_smallint.
     */
    private Short aSmallint;
    /**
     * holds value of the column a_integer.
     */
    private Long aInteger;
    /**
     * holds value of the column a_bigint.
     */
    private Long aBigint;
    /**
     * holds value of the column a_decimal.
     */
    private Byte aDecimal;
    /**
     * holds value of the column a_numeric.
     */
    private Byte aNumeric;
    /**
     * holds value of the column a_real.
     */
    private Float aReal;
    /**
     * holds value of the column a_double.
     */
    private Double aDouble;
    /**
     * holds value of the column a_smallserial.
     */
    private Short aSmallserial;
    /**
     * holds value of the column a_serial.
     */
    private Long aSerial;
    /**
     * holds value of the column a_bigserial.
     */
    private Long aBigserial;
    /**
     * holds value of the column a_date.
     */
    private LocalDate aDate;
    /**
     * holds value of the column a_blob.
     */
    private ByteBuffer aBlob;
    /**
     * holds value of the column json.
     */
    private JSONObject json;
    /**
     * holds value of the column a_jsonb.
     */
    private JSONObject aJsonb;
    /**
     * holds value of the column a_uuid.
     */
    private UUID aUuid;
    /**
     * holds value of the column a_xml.
     */
    private String aXml;
    /**
     * holds value of the column a_time.
     */
    private LocalTime aTime;
    /**
     * holds value of the column a_interval.
     */
    private Duration aInterval;
    /**
     * gets value of column - id.
	 *
     * @return id
     */
    public Long getId() {
        return id;
	}

	/**
	 * sets value of column - id.
	 *
	 * @param theId
	 */
	public void setId(final Long theId) {
		this.id = theId;
	}
    /**
     * gets value of column - a_boolean.
	 *
     * @return aBoolean
     */
    public Boolean getABoolean() {
        return aBoolean;
	}

	/**
	 * sets value of column - a_boolean.
	 *
	 * @param theABoolean
	 */
	public void setABoolean(final Boolean theABoolean) {
		this.aBoolean = theABoolean;
	}
    /**
     * gets value of column - reference_code.
	 *
     * @return referenceCode
     */
    public String getReferenceCode() {
        return referenceCode;
	}

	/**
	 * sets value of column - reference_code.
	 *
	 * @param theReferenceCode
	 */
	public void setReferenceCode(final String theReferenceCode) {
		this.referenceCode = theReferenceCode;
	}
    /**
     * gets value of column - a_char.
	 *
     * @return aChar
     */
    public Character getAChar() {
        return aChar;
	}

	/**
	 * sets value of column - a_char.
	 *
	 * @param theAChar
	 */
	public void setAChar(final Character theAChar) {
		this.aChar = theAChar;
	}
    /**
     * gets value of column - a_text.
	 *
     * @return aText
     */
    public String getAText() {
        return aText;
	}

	/**
	 * sets value of column - a_text.
	 *
	 * @param theAText
	 */
	public void setAText(final String theAText) {
		this.aText = theAText;
	}
    /**
     * gets value of column - a_encrypted_text.
	 *
     * @return aEncryptedText
     */
    public String getAEncryptedText() {
        return aEncryptedText;
	}

	/**
	 * sets value of column - a_encrypted_text.
	 *
	 * @param theAEncryptedText
	 */
	public void setAEncryptedText(final String theAEncryptedText) {
		this.aEncryptedText = theAEncryptedText;
	}
    /**
     * gets value of column - a_smallint.
	 *
     * @return aSmallint
     */
    public Short getASmallint() {
        return aSmallint;
	}

	/**
	 * sets value of column - a_smallint.
	 *
	 * @param theASmallint
	 */
	public void setASmallint(final Short theASmallint) {
		this.aSmallint = theASmallint;
	}
    /**
     * gets value of column - a_integer.
	 *
     * @return aInteger
     */
    public Long getAInteger() {
        return aInteger;
	}

	/**
	 * sets value of column - a_integer.
	 *
	 * @param theAInteger
	 */
	public void setAInteger(final Long theAInteger) {
		this.aInteger = theAInteger;
	}
    /**
     * gets value of column - a_bigint.
	 *
     * @return aBigint
     */
    public Long getABigint() {
        return aBigint;
	}

	/**
	 * sets value of column - a_bigint.
	 *
	 * @param theABigint
	 */
	public void setABigint(final Long theABigint) {
		this.aBigint = theABigint;
	}
    /**
     * gets value of column - a_decimal.
	 *
     * @return aDecimal
     */
    public Byte getADecimal() {
        return aDecimal;
	}

	/**
	 * sets value of column - a_decimal.
	 *
	 * @param theADecimal
	 */
	public void setADecimal(final Byte theADecimal) {
		this.aDecimal = theADecimal;
	}
    /**
     * gets value of column - a_numeric.
	 *
     * @return aNumeric
     */
    public Byte getANumeric() {
        return aNumeric;
	}

	/**
	 * sets value of column - a_numeric.
	 *
	 * @param theANumeric
	 */
	public void setANumeric(final Byte theANumeric) {
		this.aNumeric = theANumeric;
	}
    /**
     * gets value of column - a_real.
	 *
     * @return aReal
     */
    public Float getAReal() {
        return aReal;
	}

	/**
	 * sets value of column - a_real.
	 *
	 * @param theAReal
	 */
	public void setAReal(final Float theAReal) {
		this.aReal = theAReal;
	}
    /**
     * gets value of column - a_double.
	 *
     * @return aDouble
     */
    public Double getADouble() {
        return aDouble;
	}

	/**
	 * sets value of column - a_double.
	 *
	 * @param theADouble
	 */
	public void setADouble(final Double theADouble) {
		this.aDouble = theADouble;
	}
    /**
     * gets value of column - a_smallserial.
	 *
     * @return aSmallserial
     */
    public Short getASmallserial() {
        return aSmallserial;
	}

	/**
	 * sets value of column - a_smallserial.
	 *
	 * @param theASmallserial
	 */
	public void setASmallserial(final Short theASmallserial) {
		this.aSmallserial = theASmallserial;
	}
    /**
     * gets value of column - a_serial.
	 *
     * @return aSerial
     */
    public Long getASerial() {
        return aSerial;
	}

	/**
	 * sets value of column - a_serial.
	 *
	 * @param theASerial
	 */
	public void setASerial(final Long theASerial) {
		this.aSerial = theASerial;
	}
    /**
     * gets value of column - a_bigserial.
	 *
     * @return aBigserial
     */
    public Long getABigserial() {
        return aBigserial;
	}

	/**
	 * sets value of column - a_bigserial.
	 *
	 * @param theABigserial
	 */
	public void setABigserial(final Long theABigserial) {
		this.aBigserial = theABigserial;
	}
    /**
     * gets value of column - a_date.
	 *
     * @return aDate
     */
    public LocalDate getADate() {
        return aDate;
	}

	/**
	 * sets value of column - a_date.
	 *
	 * @param theADate
	 */
	public void setADate(final LocalDate theADate) {
		this.aDate = theADate;
	}
    /**
     * gets value of column - a_blob.
	 *
     * @return aBlob
     */
    public ByteBuffer getABlob() {
        return aBlob;
	}

	/**
	 * sets value of column - a_blob.
	 *
	 * @param theABlob
	 */
	public void setABlob(final ByteBuffer theABlob) {
		this.aBlob = theABlob;
	}
    /**
     * gets value of column - json.
	 *
     * @return json
     */
    public JSONObject getJson() {
        return json;
	}

	/**
	 * sets value of column - json.
	 *
	 * @param theJson
	 */
	public void setJson(final JSONObject theJson) {
		this.json = theJson;
	}
    /**
     * gets value of column - a_jsonb.
	 *
     * @return aJsonb
     */
    public JSONObject getAJsonb() {
        return aJsonb;
	}

	/**
	 * sets value of column - a_jsonb.
	 *
	 * @param theAJsonb
	 */
	public void setAJsonb(final JSONObject theAJsonb) {
		this.aJsonb = theAJsonb;
	}
    /**
     * gets value of column - a_uuid.
	 *
     * @return aUuid
     */
    public UUID getAUuid() {
        return aUuid;
	}

	/**
	 * sets value of column - a_uuid.
	 *
	 * @param theAUuid
	 */
	public void setAUuid(final UUID theAUuid) {
		this.aUuid = theAUuid;
	}
    /**
     * gets value of column - a_xml.
	 *
     * @return aXml
     */
    public String getAXml() {
        return aXml;
	}

	/**
	 * sets value of column - a_xml.
	 *
	 * @param theAXml
	 */
	public void setAXml(final String theAXml) {
		this.aXml = theAXml;
	}
    /**
     * gets value of column - a_time.
	 *
     * @return aTime
     */
    public LocalTime getATime() {
        return aTime;
	}

	/**
	 * sets value of column - a_time.
	 *
	 * @param theATime
	 */
	public void setATime(final LocalTime theATime) {
		this.aTime = theATime;
	}
    /**
     * gets value of column - a_interval.
	 *
     * @return aInterval
     */
    public Duration getAInterval() {
        return aInterval;
	}

	/**
	 * sets value of column - a_interval.
	 *
	 * @param theAInterval
	 */
	public void setAInterval(final Duration theAInterval) {
		this.aInterval = theAInterval;
	}

}

