package com.techatpark.scubebird.scubedao.ide.propertyeditor.section;

import com.techatpark.scubebird.ide.model.CrawlerConfig;
import com.techatpark.scubebird.ide.util.ConfigUtil;
import com.techatpark.scubebird.scubedao.constants.ScreenConstants;
import com.techatpark.scubebird.scubedao.ide.propertyeditor.interfaces.IcgProjectSection;
import com.techatpark.scubebird.scubedao.model.DaoProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SchemaSection extends JPanel implements IcgProjectSection,
		ScreenConstants, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel nameLbl;

	private JTextField nameTxt;

	private JLabel driverLbl;

	private JComboBox<CrawlerConfig> driverCmb;

	private JLabel urlLbl;

	private JTextField urlTxt;

	private JLabel usernameLbl;

	private JTextField usernameTxt;

	private JLabel passwordLbl;

	private JPasswordField passwordTxt;

	private JLabel schemaIdLbl;

	private JTextField schemaIdTxt;

	private JLabel tablePatternMapLbl;
	protected DefaultTableModel tablePatternMapTblMdl;
	protected JTable tablePatternMapTbl;
	protected JScrollPane tablePatternMapSPane;
	protected JButton addTablePatternBtn;
	protected JButton deleteTablePatternBtn;

	private JLabel sequencePatternMapLbl;
	protected DefaultTableModel sequencePatternMapTblMdl;
	protected JTable sequencePatternMapTbl;
	protected JScrollPane sequencePatternMapSPane;
	protected JButton addSequencePatternBtn;
	protected JButton deleteSequencePatternBtn;

	private JLabel onLineLbl;

	private JCheckBox onLineChkBox;

	public SchemaSection() {
		createComponents();
		addComponents();
		initComponents();
		addActions();
	}

	private void addActions() {
		addTablePatternBtn.addActionListener(this);
		deleteTablePatternBtn.addActionListener(this);
		addSequencePatternBtn.addActionListener(this);
		deleteSequencePatternBtn.addActionListener(this);
	}

	private void initComponents() {
		urlTxt.setHorizontalAlignment(JTextField.LEFT);
		for (CrawlerConfig driver : ConfigUtil.getConfigUtil().getDrivers()) {
			driverCmb.addItem(driver);
		}
		if (driverCmb.getItemCount() > 0) {
			driverCmb.setSelectedIndex(0);
		}

	}

	private void createComponents() {

		nameLbl = new JLabel("Name");
		nameTxt = new JTextField();

		driverLbl = new JLabel("Driver");
		driverCmb = new JComboBox<>();

		urlLbl = new JLabel("URL");
		urlTxt = new JTextField();

		usernameLbl = new JLabel("User Name");
		usernameTxt = new JTextField();

		passwordLbl = new JLabel("Password");
		passwordTxt = new JPasswordField();

		schemaIdLbl = new JLabel("Schema");
		schemaIdTxt = new JTextField();

		tablePatternMapLbl = new JLabel("Table Pattern");
		tablePatternMapTblMdl = new DefaultTableModel(new String[] { "Key" }, 0);
		tablePatternMapTbl = new JTable(tablePatternMapTblMdl);
		tablePatternMapSPane = new JScrollPane(tablePatternMapTbl);
		addTablePatternBtn = new JButton("+");
		deleteTablePatternBtn = new JButton("-");

		sequencePatternMapLbl = new JLabel("Sequence Pattern");
		sequencePatternMapTblMdl = new DefaultTableModel(
				new String[] { "Key" }, 0);
		sequencePatternMapTbl = new JTable(sequencePatternMapTblMdl);
		sequencePatternMapSPane = new JScrollPane(sequencePatternMapTbl);
		addSequencePatternBtn = new JButton("+");
		deleteSequencePatternBtn = new JButton("-");

		onLineLbl = new JLabel("Online");
		onLineChkBox = new JCheckBox();

	}

	private void addComponents() {

		int xPos = H_GAP;
		int yPos = V_GAP;

		nameLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH, COMPONENT_HEIGHT);
		xPos += (nameLbl.getWidth() + H_GAP);
		nameTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH, COMPONENT_HEIGHT);

		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		driverLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (driverLbl.getWidth() + H_GAP);
		driverCmb
				.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH, COMPONENT_HEIGHT);

		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		urlLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH, COMPONENT_HEIGHT);
		xPos += (urlLbl.getWidth() + H_GAP);
		urlTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH, COMPONENT_HEIGHT);

		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		usernameLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (usernameLbl.getWidth() + H_GAP);
		usernameTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		passwordLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (passwordLbl.getWidth() + H_GAP);
		passwordTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		schemaIdLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (schemaIdLbl.getWidth() + H_GAP);
		schemaIdTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		tablePatternMapLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		addTablePatternBtn.setBounds(
				xPos + (tablePatternMapLbl.getWidth() / 2), yPos
						+ COMPONENT_HEIGHT, 2 * COMPONENT_HEIGHT,
				COMPONENT_HEIGHT);
		deleteTablePatternBtn.setBounds(xPos
				+ (tablePatternMapLbl.getWidth() / 2), addTablePatternBtn
				.getY()
				+ COMPONENT_HEIGHT + V_GAP, 2 * COMPONENT_HEIGHT,
				COMPONENT_HEIGHT);
		xPos += (tablePatternMapLbl.getWidth() + H_GAP);
		tablePatternMapSPane.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				LIST_HEIGHT);

		xPos = H_GAP;
		yPos += (LIST_HEIGHT + V_GAP);
		sequencePatternMapLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		addSequencePatternBtn.setBounds(xPos
				+ (sequencePatternMapLbl.getWidth() / 2), yPos
				+ COMPONENT_HEIGHT, 2 * COMPONENT_HEIGHT, COMPONENT_HEIGHT);
		deleteSequencePatternBtn.setBounds(xPos
				+ (sequencePatternMapLbl.getWidth() / 2), addSequencePatternBtn
				.getY()
				+ COMPONENT_HEIGHT + V_GAP, 2 * COMPONENT_HEIGHT,
				COMPONENT_HEIGHT);
		xPos += (sequencePatternMapLbl.getWidth() + H_GAP);
		sequencePatternMapSPane.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				LIST_HEIGHT);

		xPos = H_GAP;
		yPos += (LIST_HEIGHT + V_GAP);
		onLineLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (onLineLbl.getWidth() + H_GAP);
		onLineChkBox.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		setLayout(null);
		add(nameLbl);
		add(nameTxt);
		add(driverLbl);
		add(driverCmb);
		add(urlLbl);
		add(urlTxt);
		add(usernameLbl);
		add(usernameTxt);
		add(passwordLbl);
		add(passwordTxt);
		add(schemaIdLbl);
		add(schemaIdTxt);
		add(tablePatternMapLbl);
		add(tablePatternMapSPane);
		add(addTablePatternBtn);
		add(deleteTablePatternBtn);
		add(sequencePatternMapLbl);
		add(sequencePatternMapSPane);
		add(addSequencePatternBtn);
		add(deleteSequencePatternBtn);
		add(onLineLbl);
		add(onLineChkBox);

	}

	private DaoProject icgProject;

	public DaoProject getIcgProject() {
		if (icgProject != null) {
			icgProject.setName(nameTxt.getText());
			icgProject.setUrl(urlTxt.getText());
			icgProject.setSchemaName(schemaIdTxt.getText());
			icgProject.setUserName(usernameTxt.getText());
			icgProject.setPassword(new String(passwordTxt.getPassword()));
			icgProject.setCrawlerConfig((CrawlerConfig) driverCmb
					.getSelectedItem());

			List<String> tablePatterns = new ArrayList<String>(
					tablePatternMapTblMdl.getRowCount());
			for (int i = tablePatternMapTblMdl.getRowCount() - 1; i >= 0; i--) {
				if (tablePatternMapTblMdl.getValueAt(i, 0) != null) {
					tablePatterns.add(tablePatternMapTblMdl.getValueAt(i, 0)
							.toString());
				}

			}
			icgProject.setTablePatterns(tablePatterns);

			List<String> sequencePatterns = new ArrayList<String>(
					sequencePatternMapTblMdl.getRowCount());
			for (int i = sequencePatternMapTblMdl.getRowCount() - 1; i >= 0; i--) {
				if (sequencePatternMapTblMdl.getValueAt(i, 0) != null) {
					sequencePatterns.add(sequencePatternMapTblMdl.getValueAt(i,
							0).toString());
				}
			}
			icgProject.setSequencePatterns(sequencePatterns);

			icgProject.setOnline(onLineChkBox.isSelected());

		}
		return icgProject;
	}

	public void setIcgProject(DaoProject icgProject) {
		if (icgProject != null) {
			nameTxt.setText(icgProject.getName());
			urlTxt.setText(icgProject.getUrl());
			schemaIdTxt.setText(icgProject.getSchemaName());
			usernameTxt.setText(icgProject.getUserName());
			passwordTxt.setText(icgProject.getPassword());
			driverCmb.setSelectedItem(icgProject.getCrawlerConfig()) ;
			for (int i = tablePatternMapTblMdl.getRowCount() - 1; i >= 0; i--) {
				tablePatternMapTblMdl.removeRow(0);
			}
			List<String> tablePatterns = icgProject.getTablePatterns();
			if (tablePatterns != null) {
				for (String tablePattern : tablePatterns) {
					tablePatternMapTblMdl.addRow(new Object[] { tablePattern });
				}
			}

			for (int i = sequencePatternMapTblMdl.getRowCount() - 1; i >= 0; i--) {
				sequencePatternMapTblMdl.removeRow(0);
			}
			List<String> sequencePatterns = icgProject.getSequencePatterns();
			if (sequencePatterns != null) {
				for (String sequencePattern : sequencePatterns) {
					sequencePatternMapTblMdl
							.addRow(new Object[] { sequencePattern });
				}
			}
			onLineChkBox.setSelected(icgProject.isOnline());
		}
		this.icgProject = icgProject;
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == addTablePatternBtn) {
			tablePatternMapTblMdl.addRow(new Object[] { null });
		} else if (arg0.getSource() == deleteTablePatternBtn) {
			if (tablePatternMapTbl.getSelectedRow() != -1) {
				tablePatternMapTblMdl.removeRow(tablePatternMapTbl
						.getSelectedRow());
			}
		} else if (arg0.getSource() == addSequencePatternBtn) {
			sequencePatternMapTblMdl.addRow(new Object[] { null });
		} else if (arg0.getSource() == deleteSequencePatternBtn) {
			if (sequencePatternMapTbl.getSelectedRow() != -1) {
				sequencePatternMapTblMdl.removeRow(sequencePatternMapTbl
						.getSelectedRow());
			}
		}
	}

}
