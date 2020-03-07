package org.scube.scubedao.ide.component;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.scube.scubedao.constants.ScreenConstants;
import org.scube.scubedao.model.DaoProject;
import org.scube.scubedao.model.Service;

public class ServicePane extends JPanel implements ScreenConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Service getService() {
		if (service != null) {
			service.setDaoPackage(daoPackageTxt.getText());
			service.setServiceName(serviceNameTxt.getText());
			service.setRemarks(remarksTxt.getText());
		}
		return service;
	}

	public void setService(Service service) {
		clearValues();
		this.service = service;
		if (service != null) {
			nameTxt.setText(service.getName()) ;
			daoPackageTxt.setText(service.getDaoPackage());
			serviceNameTxt.setText(service.getServiceName());
			remarksTxt.setText(service.getRemarks());
		}

	}

	private JPanel headerPane;

	private JLabel nameLbl;
	private JTextField nameTxt;

	private JLabel serviceNameLbl;
	private JTextField serviceNameTxt;

	private JLabel daoPackageLbl;
	private JTextField daoPackageTxt;

	private JLabel remarksLbl;
	private JTextArea remarksTxt;
	protected JScrollPane remarksSPane;

	private Service service;

	private static final String EMPTY_STRING = "";

	public void clearValues() {
		nameTxt.setText(EMPTY_STRING);
		serviceNameTxt.setText(EMPTY_STRING);
		daoPackageTxt.setText(EMPTY_STRING);
		remarksTxt.setText(EMPTY_STRING);

	}

	private DaoProject project;

	public void setICGProject(DaoProject project) {
		if (this.project != project) {
			this.project = project;
		}
	}

	public ServicePane() {
		createComponents();
		addComponents();
		initComponents();
		addActions();
	}

	private void addActions() {

	}

	private void initComponents() {

		remarksTxt.setLineWrap(true);
		remarksTxt.setWrapStyleWord(true);

	}

	private void createComponents() {

		headerPane = new JPanel();

		nameLbl = new JLabel("Name");
		nameTxt = new JTextField();

		serviceNameLbl = new JLabel("Service Name");
		serviceNameTxt = new JTextField();

		daoPackageLbl = new JLabel("Dao Package");
		daoPackageTxt = new JTextField();

		remarksLbl = new JLabel("Remarks");
		remarksTxt = new JTextArea();
		remarksSPane = new JScrollPane(remarksTxt);

	}

	private void addComponents() {

		setLayout(new BorderLayout());

		headerPane.setLayout(null);

		int xPos = H_GAP;
		int yPos = V_GAP;

		nameLbl.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH, COMPONENT_HEIGHT);
		xPos += (nameLbl.getWidth() + H_GAP);
		nameTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH, COMPONENT_HEIGHT);

		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		daoPackageLbl.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (daoPackageLbl.getWidth() + H_GAP);
		daoPackageTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		serviceNameLbl.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (serviceNameLbl.getWidth() + H_GAP);
		serviceNameTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		xPos = nameLbl.getWidth() + nameTxt.getWidth() + (3 * H_GAP);
		yPos = V_GAP;
		remarksLbl.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (remarksLbl.getWidth() + H_GAP);
		remarksSPane.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH, LIST_HEIGHT);

		headerPane.add(nameLbl);
		headerPane.add(nameTxt);
		headerPane.add(daoPackageLbl);
		headerPane.add(daoPackageTxt);
		headerPane.add(serviceNameLbl);
		headerPane.add(serviceNameTxt);
		headerPane.add(remarksLbl);
		headerPane.add(remarksSPane);

		headerPane.setPreferredSize(new Dimension(800, 150));

		add(headerPane, BorderLayout.NORTH);

		headerPane.repaint();
		headerPane.invalidate();

	}

}
