package com.techatpark.scubebird.crawler.mysql.enums;

import java.util.ArrayList;
import java.util.List;

public enum DataType {

	VARCHAR("varchar"),
	BIGINT("bigint"),
	LONGTEXT("longtext"),
	DATETIME("datetime"),
	INT("int"),
	DECIMAL("decimal"),
	TIMESTAMP("timestamp"),
	DATE("date"),
	TINYINT("tinyint"),
	BIT("bit"),
	CHAR("char"),
	SET("set"),
	ENUM("enum"),
	LONGBLOB("longblob"),
	MEDIUMTEXT("mediumtext"),
	SMALLINT("smallint"),
	TEXT("text"),
	BLOB("blob"),
	TIME("time");

	private final String dataType;
	
	DataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataType() {
		return dataType;
	}
	
	public static Boolean isNumericDataType(String sqlDataType){
		Boolean returnType = Boolean.FALSE;
		for (DataType dataType : getNumericDataTypes()) {
			if (dataType.getDataType().equals(sqlDataType)) {
				returnType = Boolean.TRUE;		
				break;
			}
		}
		return returnType;
	}
	
	public static List<DataType> getNumericDataTypes(){
		List<DataType> dataTypes = new ArrayList<DataType>();
		dataTypes.add(DataType.BIGINT);
		dataTypes.add(DataType.DECIMAL);
		dataTypes.add(DataType.INT);
		dataTypes.add(DataType.SMALLINT);
		dataTypes.add(DataType.TINYINT);
		return dataTypes;
		
	}
	
}
