package com.techatpark.scubebird.scubedao.ide.propertyeditor.section;

import com.techatpark.scubebird.ide.ApplicationManager;
import com.techatpark.scubebird.scubedao.constants.ScreenConstants;
import com.techatpark.scubebird.scubedao.ide.propertyeditor.interfaces.IcgProjectSection;
import com.techatpark.scubebird.scubedao.model.DaoProject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BusinessSection extends JPanel implements IcgProjectSection,
		ScreenConstants, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel businessLayerLbl;

	private JComboBox<String> businessLayerCmb;

	private JLabel rootPackageLbl;

	private JTextField rootPackageTxt;

	private JLabel beanIdentifierLbl;

	private JTextField beanIdentifierTxt;

	private JLabel beanSuffixLbl;

	private JTextField beanSuffixTxt;

	private JLabel daoIdentifierLbl;

	private JTextField daoIdentifierTxt;

	private JLabel daoSuffixLbl;

	private JTextField daoSuffixTxt;

	private JLabel srcFolderLbl;

	private JTextField srcFolderTxt;

	private JButton srcFolderBtn;

	private JCheckBox cleanSourceChkBox;

	public BusinessSection() {
		createComponents();
		addComponents();
		initComponents();
		addActions();
	}

	private void addActions() {
		srcFolderBtn.addActionListener(this);

	}

	private void initComponents() {
		businessLayerCmb.addItem("Java");
	}

	private void createComponents() {

		businessLayerLbl = new JLabel("Business Layer");
		businessLayerCmb = new JComboBox<>();

		rootPackageLbl = new JLabel("Root Package");
		rootPackageTxt = new JTextField();

		beanIdentifierLbl = new JLabel("Bean Marker");
		beanIdentifierTxt = new JTextField();

		daoIdentifierLbl = new JLabel("Dao Marker");
		daoIdentifierTxt = new JTextField();
		
		beanSuffixLbl = new JLabel("Bean Suffix");
		beanSuffixTxt = new JTextField();

		daoSuffixLbl = new JLabel("Dao Suffix");
		daoSuffixTxt = new JTextField();

		srcFolderLbl = new JLabel("Source Folder");
		srcFolderTxt = new JTextField();
		srcFolderBtn = new JButton(ApplicationManager.getApplicationManager()
				.getIcon("open"));

		cleanSourceChkBox = new JCheckBox("Clean Source");
	}

	private void addComponents() {

		int xPos = H_GAP;
		int yPos = V_GAP;
		xPos = H_GAP;
		yPos += (LIST_HEIGHT + V_GAP);
		srcFolderLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (srcFolderLbl.getWidth() + H_GAP);
		srcFolderTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += srcFolderTxt.getWidth();
		srcFolderBtn.setBounds(xPos, yPos, srcFolderBtn.getIcon()
				.getIconWidth() + 2, COMPONENT_HEIGHT);

		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		businessLayerLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (businessLayerLbl.getWidth() + H_GAP);
		businessLayerCmb.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		rootPackageLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (rootPackageLbl.getWidth() + H_GAP);
		rootPackageTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		beanIdentifierLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (beanIdentifierLbl.getWidth() + H_GAP);
		beanIdentifierTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		daoIdentifierLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (daoIdentifierLbl.getWidth() + H_GAP);
		daoIdentifierTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		
		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		beanSuffixLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (beanSuffixLbl.getWidth() + H_GAP);
		beanSuffixTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		daoSuffixLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (daoSuffixLbl.getWidth() + H_GAP);
		daoSuffixTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		xPos = srcFolderLbl.getWidth() + (2 * H_GAP);
		yPos += (COMPONENT_HEIGHT + V_GAP);
		cleanSourceChkBox.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		setLayout(null);
		add(srcFolderLbl);
		add(srcFolderTxt);
		add(srcFolderBtn);
		add(businessLayerLbl);
		add(businessLayerCmb);
		add(rootPackageLbl);
		add(rootPackageTxt);

		add(beanIdentifierLbl);
		add(beanIdentifierTxt);
		add(daoIdentifierLbl);
		add(daoIdentifierTxt);
		
		
		add(beanSuffixLbl);
		add(beanSuffixTxt);
		add(daoSuffixLbl);
		add(daoSuffixTxt);

		add(cleanSourceChkBox);

	}

	private DaoProject icgProject;

	public DaoProject getIcgProject() {
		if (icgProject != null) {
			icgProject.setRootPackage(rootPackageTxt.getText());
			icgProject.setBeanIdentifier(beanIdentifierTxt.getText());
			icgProject.setDaoIdentifier(daoIdentifierTxt.getText());
			icgProject.setSrcFolder(srcFolderTxt.getText());
			icgProject.setCleanSource(cleanSourceChkBox.isSelected());
			icgProject.setBeanSuffix(beanSuffixTxt.getText());
			icgProject.setDaoSuffix(daoSuffixTxt.getText());
		}
		return icgProject;
	}

	public void setIcgProject(DaoProject icgProject) {
		if (icgProject != null) {

			rootPackageTxt.setText(icgProject.getRootPackage());
			beanIdentifierTxt.setText(icgProject.getBeanIdentifier());
			daoIdentifierTxt.setText(icgProject.getDaoIdentifier());
			srcFolderTxt.setText(icgProject.getSrcFolder());
			cleanSourceChkBox.setSelected(icgProject.isCleanSource());
			beanSuffixTxt.setText(icgProject.getBeanSuffix());
			daoSuffixTxt.setText(icgProject.getDaoSuffix());
		}
		this.icgProject = icgProject;
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == srcFolderBtn) {
			File folder = ApplicationManager.getApplicationManager()
					.openFileFolder();
			if (folder != null) {
				srcFolderTxt.setText(folder.getAbsolutePath());
			}
		}
	}

}
