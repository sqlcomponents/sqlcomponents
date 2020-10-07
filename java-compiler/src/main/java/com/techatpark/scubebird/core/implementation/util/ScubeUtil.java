package com.techatpark.scubebird.core.implementation.util;

import com.techatpark.scubebird.core.implementation.OrmImplementation;
import com.techatpark.scubebird.core.implementation.specification.Specification;
import com.techatpark.scubebird.core.model.DaoProject;

import java.io.IOException;

public class ScubeUtil {

	public static void generateCode(String daoProject, String src)
			throws ClassNotFoundException, IOException {
		DaoProject project = (DaoProject) FileUtil.read(daoProject);
		project.setSrcFolder(src);
		ScubeUtil.writeCode(project);
	}

	public static void writeCode(DaoProject icgProject) {

		if (icgProject.isCleanSource()) {
			FileUtil.deleteDir(icgProject.getSrcFolder());
		}
		Specification.getSpecification().writeSpecification(icgProject);
		OrmImplementation.getImplementation().writeImplementation(icgProject);

	}
}
