package org.sqlcomponents.compiler.util;

import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.compiler.OrmImplementation;
import org.sqlcomponents.compiler.specification.Specification;
import org.sqlcomponents.core.model.Application;

import java.sql.SQLException;

public class ScubeUtil {


	public static void writeCode(Application application) throws ScubeException, SQLException {

		if (application.isCleanSource()) {
			FileUtil.deleteDir(application.getSrcFolder());
		}
		Specification.getSpecification().writeSpecification(application);
		OrmImplementation.getImplementation().writeImplementation(application);
	}
}
