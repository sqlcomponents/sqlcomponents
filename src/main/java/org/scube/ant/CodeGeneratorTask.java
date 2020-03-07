package org.scube.ant;

import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.scube.util.ScubeUtil;

public class CodeGeneratorTask extends Task {

	private String daoProject;

	public String getDaoProject() {
		return daoProject;
	}

	public void setDaoProject(String daoProject) {
		this.daoProject = daoProject;
	}

	private String src;

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public void execute() throws BuildException {
		try {
			ScubeUtil.generateCode(daoProject, src);
		} catch (ClassNotFoundException e) {
			throw new BuildException(e);
		} catch (IOException e) {
			throw new BuildException(e);
		}
	}

}
