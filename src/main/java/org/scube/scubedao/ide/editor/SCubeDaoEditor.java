package org.scube.scubedao.ide.editor;

import org.scube.ide.ApplicationWindow;
import org.scube.ide.annotation.Screen;
import org.scube.ide.screen.interfaces.FileHandler;
import org.scube.ide.screen.model.SetupRequest;
import org.scube.scubedao.constants.RequestContants;
import org.scube.scubedao.ide.editor.navigator.DaoProjectNavigator;
import org.scube.scubedao.model.DaoProject;
import org.scube.scubedao.model.Entity;
import org.scube.util.FileUtil;
import org.scube.util.ScubeUtil;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Screen(width = 650, height = 600)
public class SCubeDaoEditor extends AbstractSCubeDaoEditor implements
		FileHandler, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private File icgProjectFile;
	private DaoProject icgProject;

	public SCubeDaoEditor() {
		addActions();
	}

	private void addActions() {
		listViewBtn.addActionListener(this);
		treeViewBtn.addActionListener(this);
	}

	public DaoProject getIcgProject() {
		return icgProject;
	}

	public void setIcgProject(File icgProjectFile, DaoProject icgProject) {
		this.icgProjectFile = icgProjectFile;

		((DaoProjectNavigator) navigatorSPane.getViewport().getView())
				.setIcgProject(icgProject);

		entitiesTreeNode.setUserObject(icgProject.getName());
		entitiesTreeNode.removeAllChildren();
		entityPane.setICGProject(icgProject);
		methodPane.setICGProject(icgProject);
		servicePane.setICGProject(icgProject);

		entityPane.clearValues();
		methodPane.clearValues();
		servicePane.clearValues();

		if (icgProject != null) {

			this.icgProject = icgProject;
			setScreenTitle(icgProject.getName());
			HashMap<String, DefaultMutableTreeNode> hashMap = new HashMap<String, DefaultMutableTreeNode>();
			DefaultMutableTreeNode packageNode = null;
			for (Entity entity : icgProject.getOrm().getEntities()) {
				if (entity.getBeanPackage() != null
						&& entity.getBeanPackage().trim().length() != 0) {
					packageNode = hashMap.get(entity.getBeanPackage());
					if (packageNode == null) {
						packageNode = createPackageNode(
								entity.getBeanPackage(), icgProject
										.getBeanIdentifier(), hashMap);
					}
					if (packageNode == null) {
						entitiesTreeNode
								.add(new DefaultMutableTreeNode(entity));
					} else {
						packageNode.add(new DefaultMutableTreeNode(entity));
					}

				}
			}

		}

	}

	private DefaultMutableTreeNode createPackageNode(String beanPackage,
			String beanIdentifier,
			HashMap<String, DefaultMutableTreeNode> hashMap) {
		if (beanIdentifier != null && beanIdentifier.trim().length() != 0) {
			beanPackage = beanPackage.replaceAll("." + beanIdentifier, "");
		}
		DefaultMutableTreeNode prevPackageNode = entitiesTreeNode;
		DefaultMutableTreeNode packageNode = null;
		String[] packageWords = beanPackage.split("\\.");
		boolean isFirst = true;
		StringBuffer packageWordBuffer = new StringBuffer();
		for (String packageWord : packageWords) {
			if (!isFirst) {
				packageWordBuffer.append(".");
			} else {
				isFirst = false;
			}
			packageWordBuffer.append(packageWord);
			packageNode = hashMap.get(packageWordBuffer.toString());
			if (packageNode == null) {
				packageNode = new DefaultMutableTreeNode(packageWord);
				prevPackageNode.add(packageNode);
				prevPackageNode = packageNode;
				hashMap.put(packageWordBuffer.toString(), packageNode);

			} else {
				prevPackageNode = packageNode;
			}

		}
		return packageNode;
	}

	public void cleanUp() {

	}

	public boolean saveScreen() {
		try {
			FileUtil.write(icgProject, icgProject.getLocation()
					+ File.separator + icgProject.getName() + ".icg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void showProperties() {
		SetupRequest request = new SetupRequest();
		request.setRequestParameter(RequestContants.PARAM_DAO_PROJECT_FILE,
				icgProjectFile);
		request.setRequestParameter(RequestContants.PARAM_DAO_PROJECT,
				icgProject);
		request.setRequestParameter(RequestContants.PARAM_DAO_PROJECT_EDITOR,
				this);
		openModelDialog(
				"org.scube.scubedao.ide.propertyeditor.IcgProjectScreen",
				request);

	}

	// public boolean openPropertyEditor() {
	// getRequest().setRequestParameter("ICG_Project", icgProject);
	// openScreen("com.sss.icg.orm.ide.ui.screen.IcgProjectScreen");
	// return true;
	// }

	// @Override
	// public void doExecute(IIOPRequest request) {
	// try {
	// setIcgProject((ICGProject) FileUtil.read(request
	// .getRequestParameter("ICG_Project_Location").toString()));
	// } catch (ClassNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	public void writeCode() {
		if (isValidBusinessModel(icgProject)) {
			ScubeUtil.writeCode(icgProject);
		}
	}

	private boolean isValidBusinessModel(DaoProject icgProject) {
		boolean isValid = false;
		if (icgProject.getOrm().getMethodSpecification() == null
				|| icgProject.getOrm().getMethodSpecification().size() == 0) {
			showErrorMessage("No methods selected");
		} else if (icgProject.getRootPackage() == null
				|| icgProject.getRootPackage().trim().length() == 0) {
			showErrorMessage("Root Package is empty");
		} else if (icgProject.getBeanIdentifier() == null
				|| icgProject.getBeanIdentifier().trim().length() == 0) {
			showErrorMessage("Bean Identifier is empty");
		}
		// else if (icgProject.getDaoIdentifier() == null
		// || icgProject.getDaoIdentifier().trim().length() == 0) {
		// showErrorMessage("Dao Identifier is empty");
		// }
		else {
			isValid = true;
		}
		return isValid;
	}

	public void actionPerformed(ActionEvent arg0) {
		navigatorSPane
				.setViewportView(listViewBtn.isSelected() ? rdbmsNavigator
						: entitiesTree);
	}

	@Override
	public boolean openFile(File file) {
		boolean success = false;
		if (file != null) {
			try {
				setIcgProject(file, (DaoProject) FileUtil.read(file
						.getAbsolutePath()));
				success = true;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;
	}

	@Override
	public void saveFile() {
		if (icgProjectFile != null) {
			try {
				FileUtil.write(icgProject, icgProjectFile.getAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			File file = ApplicationWindow.getApplicationWindow()
					.showSaveFileDialog("dao", "Dao Project");
			try {
				String filePath = file.getAbsolutePath();
				if (!filePath.endsWith(".dao")) {
					filePath = filePath + ".dao";
				}
				FileUtil.write(icgProject, filePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// openFile(file)
		}

	}

	@Override
	public void setUp(SetupRequest setupRequest) {
		setIcgProject((File) setupRequest
				.getRequestParameter(RequestContants.PARAM_DAO_PROJECT_FILE),
				(DaoProject) setupRequest
						.getRequestParameter(RequestContants.PARAM_DAO_PROJECT));
	}

}
