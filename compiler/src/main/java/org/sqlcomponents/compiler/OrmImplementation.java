package org.sqlcomponents.compiler;

import org.sqlcomponents.compiler.jdbc.JdbcImplementation;
import org.sqlcomponents.core.model.Application;

import java.io.File;

public abstract class OrmImplementation {

	public static OrmImplementation getImplementation() {
		return new JdbcImplementation();
	}

	public abstract void writeImplementation(Application project);

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
