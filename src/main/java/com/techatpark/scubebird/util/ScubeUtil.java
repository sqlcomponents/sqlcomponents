package com.techatpark.scubebird.util;

import com.techatpark.scubebird.scubedao.implementation.OrmImplementation;
import com.techatpark.scubebird.scubedao.model.DaoProject;
import com.techatpark.scubebird.scubedao.specification.Specification;

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
