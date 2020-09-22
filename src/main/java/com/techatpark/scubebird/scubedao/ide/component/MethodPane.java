package com.techatpark.scubebird.scubedao.ide.component;

import com.techatpark.scubebird.scubedao.constants.ScreenConstants;
import com.techatpark.scubebird.scubedao.mapper.Mapper;
import com.techatpark.scubebird.scubedao.model.DaoProject;
import com.techatpark.scubebird.scubedao.model.Method;
import com.techatpark.scubebird.scubedao.model.Property;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class MethodPane extends JPanel implements ScreenConstants,
		ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int COLUMN_NAME_INDEX = 0;
	private static final int SQL_TYPE_INDEX = 1;
	private static final int NAME_INDEX = 2;
	private static final int DATA_TYPE_INDEX = 3;
	private static final int SIZE_INDEX = 4;
	private static final int PRECITION_INDEX = 5;
	private static final int REMARKS_INDEX = 6;

	private JPanel headerPane;

	private JLabel nameLbl;
	private JTextField nameTxt;

	private JLabel methodNameLbl;
	private JTextField methodNameTxt;

	private JLabel remarksLbl;
	private JTextArea remarksTxt;
	protected JScrollPane remarksSPane;

	protected DefaultTableModel propertiesTblMdl;
	protected JTable propertiesTbl;
	protected JScrollPane propertiesSPane;

	private JComboBox<String> dataTypesCmb;

	private Method method;

	private static final String EMPTY_STRING = "";

	private static final int MEDIUM_COLUMN_WIDTH = 40;

	public void clearValues() {
		nameTxt.setText(EMPTY_STRING);

		methodNameTxt.setText(EMPTY_STRING);
		remarksTxt.setText(EMPTY_STRING);
		for (int i = propertiesTblMdl.getRowCount() - 1; i >= 0; i--) {
			propertiesTblMdl.removeRow(0);
		}
	}

	private DaoProject project;

	public void setICGProject(DaoProject project) {
		if (this.project != project) {
			this.project = project;
		}
	}

	public Method getMethod() {
		if (method != null) {
			method.setName(nameTxt.getText());
			method.setFunctionName(methodNameTxt.getText()) ;
			method.setRemarks(remarksTxt.getText());
		}
		return method;
	}

	public void setMethod(Method method) {
		if (method != null) {
			this.method = method;
			clearValues();
			nameTxt.setText(method.getName());
			methodNameTxt.setText(method.getFunctionName()) ;
			remarksTxt.setText(method.getRemarks());
			for (int i = propertiesTblMdl.getRowCount() - 1; i >= 0; i--) {
				propertiesTblMdl.removeRow(0);
			}

			Vector<Object> rowVector;
			for (Property property : method.getInputParameters()) {
				rowVector = new Vector<Object>();
				rowVector.add(COLUMN_NAME_INDEX, property.getColumnName());
				rowVector.add(SQL_TYPE_INDEX, property.getSqlDataType());
				rowVector.add(NAME_INDEX, property.getName());
				rowVector.add(DATA_TYPE_INDEX, property.getDataType());
				rowVector.add(SIZE_INDEX, property.getSize());
				rowVector.add(PRECITION_INDEX, property.getDecimalDigits());
				rowVector.add(REMARKS_INDEX, property.getRemarks());
				propertiesTblMdl.addRow(rowVector);
			}

		}
	}

	public MethodPane() {
		createComponents();
		addComponents();
		initComponents();
		addActions();
	}

	private void addActions() {
		propertiesTbl.getSelectionModel().addListSelectionListener(this);
	}

	private void initComponents() {

		remarksTxt.setLineWrap(true);
		remarksTxt.setWrapStyleWord(true);

		propertiesTbl.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		propertiesTbl.getTableHeader().setReorderingAllowed(false);

		propertiesTbl.getColumnModel().getColumn(SIZE_INDEX).setPreferredWidth(
				MEDIUM_COLUMN_WIDTH);
		propertiesTbl.getColumnModel().getColumn(SIZE_INDEX).setMaxWidth(
				MEDIUM_COLUMN_WIDTH);
		propertiesTbl.getColumnModel().getColumn(SIZE_INDEX).setMinWidth(
				MEDIUM_COLUMN_WIDTH);

		propertiesTbl.getColumnModel().getColumn(PRECITION_INDEX)
				.setPreferredWidth(MEDIUM_COLUMN_WIDTH);
		propertiesTbl.getColumnModel().getColumn(PRECITION_INDEX).setMaxWidth(
				MEDIUM_COLUMN_WIDTH);
		propertiesTbl.getColumnModel().getColumn(PRECITION_INDEX).setMinWidth(
				MEDIUM_COLUMN_WIDTH);

		propertiesTbl.getColumnModel().getColumn(DATA_TYPE_INDEX)
				.setCellEditor(new SCubeCellEditor(dataTypesCmb));

	}

	private void createComponents() {

		headerPane = new JPanel();

		nameLbl = new JLabel("Name");
		nameTxt = new JTextField();

		methodNameLbl = new JLabel("Procedure Name");
		methodNameTxt = new JTextField();

		remarksLbl = new JLabel("Remarks");
		remarksTxt = new JTextArea();
		remarksSPane = new JScrollPane(remarksTxt);

		propertiesTblMdl = new DefaultTableModel(new String[] { "Column Name",
				"SQL Type", "Name", "Data Type", "Size", "Prec", "Remarks" }, 0);
		propertiesTbl = new JTable(propertiesTblMdl) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return (columnIndex == NAME_INDEX
						|| columnIndex == DATA_TYPE_INDEX || columnIndex == REMARKS_INDEX);
			}

			@Override
			public void setValueAt(Object value, int rowIndex, int columnIndex) {
				if(rowIndex < getRowCount()) {
					super.setValueAt(value, rowIndex, columnIndex);
					switch (columnIndex) {
					case NAME_INDEX:
						savePropertyName(rowIndex, value);
						break;
					case DATA_TYPE_INDEX:
						savePropertyDataType(rowIndex, value);
						break;
					case REMARKS_INDEX:
						savePropertyRemarks(rowIndex, value);
						break;
					}
				}
				
			}

		};
		propertiesSPane = new JScrollPane(propertiesTbl);

		dataTypesCmb = new JComboBox<>();

	}

	private void addComponents() {

		setLayout(new BorderLayout());

		headerPane.setLayout(null);

		int xPos = H_GAP;
		int yPos = V_GAP;

		nameLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH, COMPONENT_HEIGHT);
		xPos += (nameLbl.getWidth() + H_GAP);
		nameTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH, COMPONENT_HEIGHT);

		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		methodNameLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (methodNameLbl.getWidth() + H_GAP);
		methodNameTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		yPos = V_GAP;
		xPos += (methodNameTxt.getWidth() + H_GAP);
		remarksLbl.setBounds(xPos, yPos, SMALL_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (remarksLbl.getWidth() + H_GAP);
		remarksSPane.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH, LIST_HEIGHT);

		headerPane.add(nameLbl);
		headerPane.add(nameTxt);
		headerPane.add(methodNameLbl);
		headerPane.add(methodNameTxt);
		headerPane.add(remarksLbl);
		headerPane.add(remarksSPane);

		headerPane.setPreferredSize(new Dimension(800, 150));

		add(headerPane, BorderLayout.NORTH);
		add(propertiesSPane, BorderLayout.CENTER);

		headerPane.repaint();
		headerPane.invalidate();

	}

	private void savePropertyName(int rowIndex, Object value) {
		Property property = method.getInputParameters().get(rowIndex);
		if (property != null) {
			property.setName(value.toString());
		}
	}

	private void savePropertyDataType(int rowIndex, Object value) {
		Property property = method.getInputParameters().get(rowIndex);
		if (property != null && value != null) {
			property.setDataType(value.toString());
		}
	}

	private void savePropertyRemarks(int rowIndex, Object value) {
		Property property = method.getInputParameters().get(rowIndex);
		if (property != null) {
			property.setRemarks(value.toString());
		}

	}

	public void valueChanged(ListSelectionEvent arg0) {
		dataTypesCmb.removeAllItems();
		if (propertiesTbl.getSelectedRow() != -1) {
			Property property = method.getInputParameters().get(
					propertiesTbl.getSelectedRow());
			String[] dataTypes = Mapper.getMapper(project).getValidDataTypes(
					property.getSqlDataType(), property.getSize(),
					property.getDecimalDigits());
			for (String dataType : dataTypes) {
				dataTypesCmb.addItem(dataType);
			}
			if (dataTypesCmb.getItemCount() != 0) {
				dataTypesCmb.setSelectedIndex(0);
			}
		}

	}

}
