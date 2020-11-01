package org.sqlcomponents.compiler.util;

import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.compiler.OrmImplementation;
import org.sqlcomponents.compiler.specification.Specification;
import org.sqlcomponents.core.model.Application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Comparator;

public class ScubeUtil {


	public static void writeCode(Application application) throws ScubeException, SQLException, IOException {

		if (application.isCleanSource()) {
			Files.walk(new File(application.getSrcFolder()).toPath())
					.sorted(Comparator.reverseOrder())
					.map(Path::toFile)
					.forEach(File::delete);
		}
		Specification.getSpecification().writeSpecification(application);
		OrmImplementation.getImplementation().writeImplementation(application);
	}
}
