package com.techatpark.scubebird.scubedao.ide.propertyeditor.section;

import com.techatpark.scubebird.ide.model.Method;
import com.techatpark.scubebird.ide.util.ConfigUtil;
import com.techatpark.scubebird.scubedao.constants.ScreenConstants;
import com.techatpark.scubebird.scubedao.ide.propertyeditor.interfaces.IcgProjectSection;
import com.techatpark.scubebird.scubedao.model.DaoProject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MSRSection extends JPanel implements IcgProjectSection,
		ScreenConstants {

	private DaoProject icgProject;

	private JScrollPane methodSpecPane;
	private DefaultListModel<Method> methodSpecLstMdl;
	private JList<Method> methodSpecLst;
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

		methodSpecLstMdl = new DefaultListModel<Method>();
		methodSpecLst = new JList<Method>(methodSpecLstMdl);
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
					if (msrs.contains((methodSpecLstMdl
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
