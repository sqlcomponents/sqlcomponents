package com.techatpark.scubebird.ide.scubedao.ide.component;

import com.techatpark.scubebird.core.implementation.mapper.Mapper;
import com.techatpark.scubebird.core.model.*;
import com.techatpark.scubebird.ide.ApplicationManager;
import com.techatpark.scubebird.ide.scubedao.constants.ScreenConstants;
import com.techatpark.scubebird.ide.scubedao.ide.icon.UniqueIcon;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class EntityPane extends JPanel implements ScreenConstants,
		ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Icon pkIcon = ApplicationManager
			.getApplicationManager().getIcon("pk");

	private static final Icon fkIcon = ApplicationManager
			.getApplicationManager().getIcon("fk");

	private static final UniqueIcon ukIcon = new UniqueIcon();

	private static final int KEY_INDEX = 0;
	private static final int FOREIGN_KEY_INDEX = 1;
	private static final int NULLABLE_INDEX = 2;
	private static final int UNIQUE_INDEX = 3;

	private static final int COLUMN_NAME_INDEX = 4;
	private static final int SQL_TYPE_INDEX = 5;
	private static final int NAME_INDEX = 6;
	private static final int DATA_TYPE_INDEX = 7;
	private static final int SIZE_INDEX = 8;
	private static final int PRECITION_INDEX = 9;
	private static final int REMARKS_INDEX = 10;

	private JPanel headerPane;

	private JLabel nameLbl;
	private JTextField nameTxt;

	private JLabel pluralNameLbl;
	private JTextField pluralNameTxt;

	private JLabel tableNameLbl;
	private JTextField tableNameTxt;

	private JLabel beanPackageLbl;
	private JTextField beanPackageTxt;

	private JLabel daoPackageLbl;
	private JTextField daoPackageTxt;

	private JLabel remarksLbl;
	private JTextArea remarksTxt;
	protected JScrollPane remarksSPane;

	protected DefaultTableModel propertiesTblMdl;
	protected JTable propertiesTbl;
	protected JScrollPane propertiesSPane;

	private JLabel sequenceLbl;
	private JComboBox<String> sequenceCmb;

	private JComboBox<String> dataTypesCmb;

	private Entity entity;

	private static final String EMPTY_STRING = "";

	private static final int MEDIUM_COLUMN_WIDTH = 40;
	private static final int SMALL_COLUMN_WIDTH = 25;

	private boolean isClearingTheTable;

	public void clearValues() {
		nameTxt.setText(EMPTY_STRING);
		tableNameTxt.setText(EMPTY_STRING);
		beanPackageTxt.setText(EMPTY_STRING);
		daoPackageTxt.setText(EMPTY_STRING);
		remarksTxt.setText(EMPTY_STRING);
		pluralNameTxt.setText(EMPTY_STRING);
		sequenceCmb.setSelectedIndex(0);
		isClearingTheTable = true;
		for (int i = propertiesTblMdl.getRowCount() - 1; i >= 0; i--) {
			propertiesTblMdl.removeRow(0);
		}
		isClearingTheTable = false;
	}

	private DaoProject project;

	public void setICGProject(DaoProject project) {
		this.project = project;
		sequenceCmb.removeAllItems();
		if (project != null && project.getSchema().getSequences() != null) {
			sequenceCmb.addItem(null);
			for (String sequence : project.getSchema().getSequences()) {
				sequenceCmb.addItem(sequence);
			}
			sequenceLbl.setVisible(true);
			sequenceCmb.setVisible(true);

		} else {
			sequenceLbl.setVisible(false);
			sequenceCmb.setVisible(false);
		}

	}

	public Entity getEntity() {
		if (entity != null) {
			entity.setName(nameTxt.getText());
			entity.setPluralName(pluralNameTxt.getText());
			entity.setBeanPackage(beanPackageTxt.getText());
			entity.setDaoPackage(daoPackageTxt.getText());
			entity.setRemarks(remarksTxt.getText());
			if (sequenceCmb.getSelectedItem() != null) {
				entity
						.setSequenceName(sequenceCmb.getSelectedItem()
								.toString());
			} else {
				entity.setSequenceName(null);
			}
		}
		return entity;
	}

	public void setEntity(Entity entity) {
		if (entity != null) {
			this.entity = entity;
			clearValues();
			if (Table.TYPE_TABLE.equals(entity.getTableType())) {
				tableNameLbl.setText("Table Name");
				sequenceCmb.setVisible(true) ;
				sequenceLbl.setVisible(true) ;
			} else {
				sequenceCmb.setVisible(false) ;
				sequenceLbl.setVisible(false) ;
				if (Table.TYPE_MVIEW.equals(entity.getTableType())) {
					tableNameLbl.setText("MView Name");
				} else {
					tableNameLbl.setText("View Name");
				}
			}

			nameTxt.setText(entity.getName());
			sequenceCmb.setSelectedItem(entity.getSequenceName());
			pluralNameTxt.setText(entity.getPluralName());
			tableNameTxt.setText(entity.getTableName());
			beanPackageTxt.setText(entity.getBeanPackage());
			daoPackageTxt.setText(entity.getDaoPackage());
			remarksTxt.setText(entity.getRemarks());
			for (int i = propertiesTblMdl.getRowCount() - 1; i >= 0; i--) {
				propertiesTblMdl.removeRow(0);
			}

			Vector<Object> rowVector;
			for (Property property : entity.getProperties()) {
				rowVector = new Vector<Object>();
				rowVector.add(KEY_INDEX, property);
				rowVector.add(FOREIGN_KEY_INDEX, property);
				rowVector.add(NULLABLE_INDEX, property.isNullable());
				rowVector.add(UNIQUE_INDEX, property);
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

	public EntityPane() {
		createComponents();
		addComponents();
		initComponents();
		addActions();
	}

	private void addActions() {
		propertiesTbl.getSelectionModel().addListSelectionListener(this);
	}

	private void initComponents() {
		tableNameTxt.setEnabled(false);
		remarksTxt.setLineWrap(true);
		remarksTxt.setWrapStyleWord(true);

		propertiesTbl.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		propertiesTbl.getTableHeader().setReorderingAllowed(false);

		propertiesTbl.getColumnModel().getColumn(KEY_INDEX).setPreferredWidth(
				15);
		propertiesTbl.getColumnModel().getColumn(KEY_INDEX).setMaxWidth(
				SMALL_COLUMN_WIDTH);
		propertiesTbl.getColumnModel().getColumn(KEY_INDEX).setMinWidth(
				SMALL_COLUMN_WIDTH);

		propertiesTbl.getColumnModel().getColumn(FOREIGN_KEY_INDEX)
				.setPreferredWidth(SMALL_COLUMN_WIDTH);
		propertiesTbl.getColumnModel().getColumn(FOREIGN_KEY_INDEX)
				.setMaxWidth(SMALL_COLUMN_WIDTH);
		propertiesTbl.getColumnModel().getColumn(FOREIGN_KEY_INDEX)
				.setMinWidth(SMALL_COLUMN_WIDTH);

		propertiesTbl.getColumnModel().getColumn(NULLABLE_INDEX)
				.setPreferredWidth(15);
		propertiesTbl.getColumnModel().getColumn(NULLABLE_INDEX).setMaxWidth(
				SMALL_COLUMN_WIDTH);
		propertiesTbl.getColumnModel().getColumn(NULLABLE_INDEX).setMinWidth(
				SMALL_COLUMN_WIDTH);
		propertiesTbl.getColumnModel().getColumn(UNIQUE_INDEX)
				.setPreferredWidth(MEDIUM_COLUMN_WIDTH);
		propertiesTbl.getColumnModel().getColumn(UNIQUE_INDEX).setMaxWidth(
				MEDIUM_COLUMN_WIDTH);
		propertiesTbl.getColumnModel().getColumn(UNIQUE_INDEX).setMinWidth(
				MEDIUM_COLUMN_WIDTH);

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

		propertiesTbl.getColumnModel().getColumn(KEY_INDEX).setCellRenderer(
				new KeyCellRenderer());

		propertiesTbl.getColumnModel().getColumn(UNIQUE_INDEX).setCellRenderer(
				new UniqueKeyCellRenderer());

		propertiesTbl.getColumnModel().getColumn(FOREIGN_KEY_INDEX)
				.setCellRenderer(new FKCellRenderer());

	}

	private void createComponents() {

		headerPane = new JPanel();

		nameLbl = new JLabel("Name");
		nameTxt = new JTextField();

		pluralNameLbl = new JLabel("Plural");
		pluralNameTxt = new JTextField();

		tableNameLbl = new JLabel("Table Name");
		tableNameTxt = new JTextField();

		beanPackageLbl = new JLabel("Bean Package");
		beanPackageTxt = new JTextField();

		daoPackageLbl = new JLabel("Dao Package");
		daoPackageTxt = new JTextField();

		remarksLbl = new JLabel("Remarks");
		remarksTxt = new JTextArea();
		remarksSPane = new JScrollPane(remarksTxt);

		propertiesTblMdl = new DefaultTableModel(new String[] { "Key", "FK",
				"Null", "Unique", "Column Name", "SQL Type", "Name",
				"Data Type", "Size", "Prec", "Remarks" }, 0);
		propertiesTbl = new JTable(propertiesTblMdl) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == NULLABLE_INDEX) {
					return Boolean.class;
				}
				return super.getColumnClass(columnIndex);
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return (columnIndex == NAME_INDEX
						|| columnIndex == DATA_TYPE_INDEX || columnIndex == REMARKS_INDEX);
			}

			@Override
			public void setValueAt(Object value, int rowIndex, int columnIndex) {

				// None of the Editable field is Nullable
				if (value != null) {
					super.setValueAt(value, rowIndex, columnIndex);
				}
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

		};
		propertiesSPane = new JScrollPane(propertiesTbl);

		dataTypesCmb = new JComboBox<>();

		sequenceLbl = new JLabel("Sequence Name");
		sequenceCmb = new JComboBox<String>();
	}

	private void addComponents() {

		setLayout(new BorderLayout());

		headerPane.setLayout(null);

		int xPos = H_GAP;
		int yPos = V_GAP;

		nameLbl.setBounds(xPos, yPos, SMALL_COMPONENT_WIDTH, COMPONENT_HEIGHT);
		xPos += (nameLbl.getWidth() + H_GAP);
		nameTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH, COMPONENT_HEIGHT);

		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		pluralNameLbl.setBounds(xPos, yPos, SMALL_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (pluralNameLbl.getWidth() + H_GAP);
		pluralNameTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		xPos = H_GAP;
		yPos += (COMPONENT_HEIGHT + V_GAP);
		tableNameLbl.setBounds(xPos, yPos, SMALL_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (tableNameLbl.getWidth() + H_GAP);
		tableNameTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		xPos = nameLbl.getWidth() + nameTxt.getWidth() + (3 * H_GAP);
		yPos = V_GAP;

		beanPackageLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (beanPackageLbl.getWidth() + H_GAP);
		beanPackageTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		xPos = nameLbl.getWidth() + nameTxt.getWidth() + (3 * H_GAP);
		yPos += (COMPONENT_HEIGHT + V_GAP);
		daoPackageLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (daoPackageLbl.getWidth() + H_GAP);
		daoPackageTxt.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		xPos = nameLbl.getWidth() + nameTxt.getWidth() + (3 * H_GAP);
		yPos += (COMPONENT_HEIGHT + V_GAP);
		sequenceLbl.setBounds(xPos, yPos, MEDIUM_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (sequenceLbl.getWidth() + H_GAP);
		sequenceCmb.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);

		xPos = nameLbl.getWidth() + nameTxt.getWidth()
				+ beanPackageLbl.getWidth() + beanPackageTxt.getWidth()
				+ (5 * H_GAP);
		yPos = V_GAP;

		remarksLbl.setBounds(xPos, yPos, SMALL_COMPONENT_WIDTH,
				COMPONENT_HEIGHT);
		xPos += (remarksLbl.getWidth() + H_GAP);
		remarksSPane.setBounds(xPos, yPos, LARGE_COMPONENT_WIDTH, LIST_HEIGHT);

		headerPane.add(nameLbl);
		headerPane.add(nameTxt);
		headerPane.add(pluralNameLbl);
		headerPane.add(pluralNameTxt);
		headerPane.add(tableNameLbl);
		headerPane.add(tableNameTxt);
		headerPane.add(sequenceLbl);
		headerPane.add(sequenceCmb);
		headerPane.add(beanPackageLbl);
		headerPane.add(beanPackageTxt);
		headerPane.add(daoPackageLbl);
		headerPane.add(daoPackageTxt);
		headerPane.add(remarksLbl);
		headerPane.add(remarksSPane);

		headerPane.setPreferredSize(new Dimension(800, 150));

		add(headerPane, BorderLayout.NORTH);
		add(propertiesSPane, BorderLayout.CENTER);

		headerPane.repaint();
		headerPane.invalidate();

	}

	private void savePropertyName(int rowIndex, Object value) {
		Property property = entity.getProperties().get(rowIndex);
		if (property != null) {
			property.setName(value.toString());
		}
	}

	private void savePropertyDataType(int rowIndex, Object value) {
		Property property = entity.getProperties().get(rowIndex);
		if (property != null && value != null) {
			property.setDataType(value.toString());
		}
	}

	private void savePropertyRemarks(int rowIndex, Object value) {
		Property property = entity.getProperties().get(rowIndex);
		if (property != null) {
			property.setRemarks(value.toString());
		}
	}

	public void valueChanged(ListSelectionEvent arg0) {

		if (!isClearingTheTable && propertiesTbl.getSelectedRow() != -1) {
			dataTypesCmb.removeAllItems();
			Property property = entity.getProperties().get(
					propertiesTbl.getSelectedRow());
			String[] dataTypes = Mapper.getMapper(project).getValidDataTypes(
					property.getSqlDataType(), property.getSize(),
					property.getDecimalDigits());
			for (String dataType : dataTypes) {
				dataTypesCmb.addItem(dataType);
			}
			dataTypesCmb.setSelectedItem(property.getDataType());

		}

	}

	static class KeyCellRenderer extends DefaultTableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public KeyCellRenderer() {
			setHorizontalAlignment(CENTER);
		}

		public Component getTableCellRendererComponent(JTable arg0,
				Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
			super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4,
					arg5);
			Property property = (Property) arg1;
			setText("");
			setIcon((property.getPrimaryKeyIndex() == 0) ? null : pkIcon);
			return this;
		}

	}

	static class FKCellRenderer extends DefaultTableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public FKCellRenderer() {
			setHorizontalAlignment(CENTER);
		}

		public Component getTableCellRendererComponent(JTable arg0,
				Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
			super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4,
					arg5);

			setText("");
			List<ForeignKey> foreignKeys = ((Property) arg1).getForeignKeys();
			if (foreignKeys != null) {
				setIcon(fkIcon);
				setToolTipText(getForeignKeysInfo(foreignKeys));
			} else {
				setIcon(null);
				setToolTipText(null);
			}
			return this;
		}

		private String getForeignKeysInfo(List<ForeignKey> foreignKeys) {
			StringBuffer buffer = new StringBuffer(
					"<html><table border = \"1\">");
			for (ForeignKey foreignKey : foreignKeys) {
				buffer.append("<tr><td>");
				buffer.append(foreignKey.getTableName());
				buffer.append("</td><td>");
				buffer.append(foreignKey.getColumnName());
				buffer.append("</td></tr>");
			}
			buffer.append("</table></html>");
			return buffer.toString();

		}

	}

	static class UniqueKeyCellRenderer extends DefaultTableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public UniqueKeyCellRenderer() {
			setHorizontalAlignment(CENTER);
		}

		private Color getColor(String name) {
			int number = (name.hashCode() * name.length()) % 3;
			switch (number) {
			case 0:
				return Color.RED;
			case 1:
				return Color.BLUE;
			case 2:
				return Color.GREEN;
			}
			return Color.BLACK;

		}

		public Component getTableCellRendererComponent(JTable arg0,
				Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
			super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4,
					arg5);

			setText("");
			String unString = ((Property) arg1).getUniqueConstraintName();

			if (unString != null) {
				ukIcon.setBgColor(getColor(unString));
				setIcon(ukIcon);
				setToolTipText(unString);
			} else {
				setIcon(null);
				setToolTipText(null);
			}
			return this;
		}

	}

}
