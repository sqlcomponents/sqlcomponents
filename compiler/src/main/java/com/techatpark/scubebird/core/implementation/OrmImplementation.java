package com.techatpark.scubebird.core.implementation;

import com.techatpark.scubebird.core.implementation.jdbc.JdbcImplementation;
import com.techatpark.scubebird.core.model.DaoProject;

import java.io.File;

public abstract class OrmImplementation {

	public static OrmImplementation getImplementation() {
		// return new IBatisImplementation();
		return new JdbcImplementation();
	}

	public abstract void writeImplementation(DaoProject project);

	protected String getPackageAsFolder(String rootDir, String packageStr) {
		char[] charArray = packageStr.toCharArray();
		StringBuffer filePath = new StringBuffer();
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] == '.') {
				filePath.append(File.separatorChar);
			} else {
				filePath.append(charArray[i]);
			}
		}
		return rootDir + File.separatorChar + filePath.toString();
	}
}
