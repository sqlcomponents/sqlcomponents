package org.scube.scubedao.ide.propertyeditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.scube.exception.ScubeException;
import org.scube.ide.ApplicationManager;
import org.scube.ide.annotation.Screen;
import org.scube.ide.screen.model.SetupRequest;
import org.scube.scubedao.constants.RequestContants;
import org.scube.scubedao.ide.editor.SCubeDaoEditor;
import org.scube.scubedao.ide.propertyeditor.interfaces.IcgProjectSection;
import org.scube.scubedao.mapper.Mapper;
import org.scube.scubedao.model.DaoProject;

@Screen(closeable = false, width = 650, height = 600, singleInstance = true)
public class IcgProjectScreen extends AbstractIcgProjectScreen implements
		TreeSelectionListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private File icgProjectFile;
	private DaoProject icgProject;

	private SCubeDaoEditor daoEditor;

	public void setDaoEditor(SCubeDaoEditor daoEditor) {
		this.daoEditor = daoEditor;
	}

	public IcgProjectScreen() {
		loadPreferences();
		addActions();
		sectionsTree.setSelectionRow(0);
		icgProject = new DaoProject();
		setCurrentSection();
	}

	private void loadPreferences() {

	}

	private void addActions() {
		sectionsTree.addTreeSelectionListener(this);
		okBtn.addActionListener(this);
	}

	public void cleanUp() {
	}

	private void saveCurrentSection() {
		((IcgProjectSection) sectionContentPane.getViewport().getView())
				.getIcgProject();
	}

	private void setCurrentSection() {
		((IcgProjectSection) sectionContentPane.getViewport().getView())
				.setIcgProject(icgProject);
	}

	public void valueChanged(TreeSelectionEvent e) {
		if (sectionsTree.getSelectionRows() != null) {

			saveCurrentSection();

			int selectedRow = sectionsTree.getSelectionRows()[0];

			switch (selectedRow) {
			case 0:
				sectionContentPane.setViewportView(schemaSection);
				break;
			case 1:
				sectionContentPane.setViewportView(businessSection);
				break;
			case 2:
				sectionContentPane.setViewportView(wordsSection);
				break;
			case 3:
				sectionContentPane.setViewportView(modulesSection);
				break;
			case 4:
				sectionContentPane.setViewportView(pluralSection);
				break;
			case 5:
				sectionContentPane.setViewportView(msrSection);
				break;
			case 6:
				sectionContentPane.setViewportView(defaultsSection);
				break;
			case 7:
				sectionContentPane.setViewportView(validationsSection);
				break;

			}

			setCurrentSection();

		}
	}

	private boolean isValidProject() {
		boolean isValied = false;
		if (icgProject.getUrl() == null
				|| icgProject.getUrl().trim().length() == 0) {
			showErrorMessage("Please provide valid Data base URL");
		} else if (icgProject.getTablePatterns() == null
				|| icgProject.getTablePatterns().size() == 0) {
			showErrorMessage("Please provide table patterns");
		} else {
			isValied = true;
		}
		return isValied;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okBtn) {

			saveCurrentSection();
			if (isValidProject()) {
				try {
					icgProject.setOrm(Mapper.getMapper(icgProject).getOrm(
							icgProject));
					if (daoEditor != null) {
						daoEditor.setIcgProject(icgProjectFile, icgProject);
					} else {
						SetupRequest request = new SetupRequest();
						request.setRequestParameter(
								RequestContants.PARAM_DAO_PROJECT, icgProject);
						ApplicationManager.getApplicationManager().openScreen(
								SCubeDaoEditor.class, request);

					}
					closeScreen();

				} catch (ScubeException e1) {
					e1.printStackTrace();
					showErrorMessage(e1.getCause().getMessage());
				}
			}

		}

	}

	public DaoProject getIcgProject() {
		return icgProject;
	}

	public void setIcgProject(File icgProjectFile, DaoProject icgProject) {
		this.icgProjectFile = icgProjectFile;
		this.icgProject = icgProject;
		if(icgProject != null) {
			setScreenTitle(icgProject.getName());
		}
		setCurrentSection();
	}

	@Override
	public void setUp(SetupRequest setupRequest) {

		setIcgProject((File) setupRequest
				.getRequestParameter(RequestContants.PARAM_DAO_PROJECT_FILE),
				(DaoProject) setupRequest
						.getRequestParameter(RequestContants.PARAM_DAO_PROJECT));
		setDaoEditor((SCubeDaoEditor) setupRequest
				.getRequestParameter(RequestContants.PARAM_DAO_PROJECT_EDITOR));

	}

	// @Override
	// public void doExecute(IIOPRequest request) {
	// ICGProject project = (ICGProject)
	// getRequest().getRequestParameter("ICG_Project") ;
	// if(project == null) {
	// project = new ICGProject() ;
	// }
	// setIcgProject(project);
	// }

}
