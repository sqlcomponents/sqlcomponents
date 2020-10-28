package org.sqlcomponents.compiler.util;

import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.compiler.OrmImplementation;
import org.sqlcomponents.compiler.specification.Specification;
import org.sqlcomponents.core.model.DaoProject;

import java.sql.SQLException;

public class ScubeUtil {


	public static void writeCode(DaoProject daoProject) throws ScubeException, SQLException {

		if (daoProject.isCleanSource()) {
			FileUtil.deleteDir(daoProject.getSrcFolder());
		}
		Specification.getSpecification().writeSpecification(daoProject);
		OrmImplementation.getImplementation().writeImplementation(daoProject);
	}
}
