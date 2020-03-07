package org.scube.scubedao.ide.propertyeditor.section;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.scube.ide.model.Method;
import org.scube.ide.util.ConfigUtil;
import org.scube.scubedao.constants.ScreenConstants;
import org.scube.scubedao.ide.propertyeditor.interfaces.IcgProjectSection;
import org.scube.scubedao.model.DaoProject;

public class MSRSection extends JPanel implements IcgProjectSection,
		ScreenConstants {

	private DaoProject icgProject;

	private JScrollPane methodSpecPane;
	private DefaultListModel methodSpecLstMdl;
	private JList methodSpecLst;
	private JCheckBox paginationChkBox;

	public MSRSection() {
		createComponents();
		addComponents();
		initComponents();

	}

	private void initComponents() {
		List<Method> methods = ConfigUtil.getConfigUtil().getMethods();
		for (Method method : methods) {
			methodSpecLstMdl.addElement(method);
		}

	}

	private void createComponents() {

		methodSpecLstMdl = new DefaultListModel();
		methodSpecLst = new JList(methodSpecLstMdl);
		methodSpecPane = new JScrollPane(methodSpecLst);
		paginationChkBox = new JCheckBox("Pagination Support");
	}

	private void addComponents() {
		setLayout(new BorderLayout());

		add(methodSpecPane, BorderLayout.CENTER);
		add(paginationChkBox, BorderLayout.SOUTH);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DaoProject getIcgProject() {
		if (icgProject != null) {
			List<String> methodSpecificationsList = new ArrayList<String>();
			Object[] methodSpecifications = methodSpecLst.getSelectedValues();
			for (Object object : methodSpecifications) {
				methodSpecificationsList.add(((Method) object).getId());
			}
			icgProject.getOrm()
					.setMethodSpecification(methodSpecificationsList);
			icgProject.getOrm().setPagination(paginationChkBox.isSelected());
		}
		return icgProject;
	}

	public void setIcgProject(DaoProject icgProject) {
		if (icgProject != null) {
			List<String> msrs = icgProject.getOrm().getMethodSpecification();
			int size = methodSpecLstMdl.getSize();
			if (msrs != null) {
				int[] selectedIndices = new int[size];
				int si = 0;
				for (int i = 0; i < size; i++) {
					if (msrs.contains(((Method) methodSpecLstMdl
							.getElementAt(i)).getId())) {
						selectedIndices[si++] = i;
					}
				}
				methodSpecLst.setSelectedIndices(selectedIndices);
			}
			paginationChkBox.setSelected(icgProject.getOrm().isPagination());
		}
		this.icgProject = icgProject;
	}

}
