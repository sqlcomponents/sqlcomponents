package com.techatpark.scubebird.scubedao.specification;

import com.techatpark.scubebird.scubedao.model.DaoProject;
import com.techatpark.scubebird.scubedao.specification.java.JavaSpecification;

import java.io.File;

public abstract class Specification {
	
	
	public static Specification getSpecification() {
		return new JavaSpecification() ;
	}
	
	public abstract void writeSpecification(DaoProject project) ;

	
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
