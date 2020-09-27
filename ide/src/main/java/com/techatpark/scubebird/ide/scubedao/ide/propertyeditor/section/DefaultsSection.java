package com.techatpark.scubebird.ide.scubedao.ide.propertyeditor.section;

import com.techatpark.scubebird.core.model.DaoProject;
import com.techatpark.scubebird.core.model.Default;
import com.techatpark.scubebird.ide.scubedao.constants.ScreenConstants;
import com.techatpark.scubebird.ide.scubedao.ide.propertyeditor.interfaces.IcgProjectSection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DefaultsSection extends JPanel implements IcgProjectSection,
		ScreenConstants, ActionListener {

	private static final int ON_INSERT_INDEX = 0;
	private static final int ON_UPDATE_INDEX = 1;
	private static final int COLUMN_NAME_INDEX = 2;
	private static final int DEFAULT_VALUE_INDEX = 3;

	private static final int SMALL_COLUMN_WIDTH = 25;

	private DaoProject icgProject;

	private JPanel headerPane;
	protected JButton addBtn;
	protected JButton deleteBtn;

	protected DefaultTableModel mapTblMdl;
	protected JTable mapTbl;
	protected JScrollPane mapSPane;

	public DefaultsSection() {
		createComponents();
		addComponents();
		addActions();
		initComponents();
	}

	private void initComponents() {
		mapTbl.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);

		mapTbl.getColumnModel().getColumn(ON_INSERT_INDEX)
				.setPreferredWidth(15);
		mapTbl.getColumnModel().getColumn(ON_INSERT_INDEX).setMaxWidth(
				SMALL_COLUMN_WIDTH);
		mapTbl.getColumnModel().getColumn(ON_INSERT_INDEX).setMinWidth(
				SMALL_COLUMN_WIDTH);

		mapTbl.getColumnModel().getColumn(ON_UPDATE_INDEX)
				.setPreferredWidth(15);
		mapTbl.getColumnModel().getColumn(ON_UPDATE_INDEX).setMaxWidth(
				SMALL_COLUMN_WIDTH);
		mapTbl.getColumnModel().getColumn(ON_UPDATE_INDEX).setMinWidth(
				SMALL_COLUMN_WIDTH);

	}

	private void addActions() {
		addBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
	}

	private void createComponents() {

		headerPane = new JPanel();
		addBtn = new JButton("+");
		deleteBtn = new JButton("-");

		mapTblMdl = new DefaultTableModel(new String[] { "I", "U", "Column",
				"Default" }, 0);
		mapTbl = new JTable(mapTblMdl){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int arg0) {
				if(arg0 == ON_INSERT_INDEX 
						|| arg0 == ON_UPDATE_INDEX ) {
					return Boolean.class ;
				}
				return super.getColumnClass(arg0);
			}
		};
		mapSPane = new JScrollPane(mapTbl);

	}

	private void addComponents() {

		headerPane.setLayout(new FlowLayout(FlowLayout.RIGHT, V_GAP, H_GAP));
		headerPane.add(addBtn);
		headerPane.add(deleteBtn);

		setLayout(new BorderLayout());
		add(headerPane, BorderLayout.NORTH);
		add(mapSPane, BorderLayout.CENTER);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public DaoProject getIcgProject() {
		if (icgProject != null) {
			List<Default> defaults = new ArrayList<Default>(mapTblMdl
					.getRowCount());
			Vector<Vector> dataVector = mapTblMdl.getDataVector();
			Vector<Object> rowVector;
			Default default1;
			for (int i = mapTblMdl.getRowCount() - 1; i >= 0; i--) {
				rowVector = dataVector.get(i);
				default1 = new Default();
				default1.setOnInsert((Boolean) rowVector.get(ON_INSERT_INDEX));
				default1.setOnUpdate((Boolean) rowVector.get(ON_UPDATE_INDEX));
				default1.setColumnName((String) rowVector
						.get(COLUMN_NAME_INDEX));
				default1.setDefaultValue((String) rowVector
						.get(DEFAULT_VALUE_INDEX));
				defaults.add(default1);
			}
			icgProject.getOrm().setDefaults(defaults);
		}
		return icgProject;
	}

	public void setIcgProject(DaoProject icgProject) {
		clearValues() ;
		if (icgProject != null && icgProject.getOrm().getDefaults() != null ) {
			List<Default> defaults = icgProject.getOrm().getDefaults();
			Vector<Object> rowVector;
			for (Default default1 : defaults) {
				rowVector = new Vector<Object>(4);
				rowVector.add( default1.isOnInsert());
				rowVector.add( default1.isOnUpdate());
				rowVector.add( default1.getColumnName());
				rowVector.add( default1.getDefaultValue());
				mapTblMdl.addRow(rowVector) ;
			}
		}
		this.icgProject = icgProject;
	}

	public void clearValues() {
		for (int i = mapTblMdl.getRowCount() - 1; i >= 0; i--) {
			mapTblMdl.removeRow(0);
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == addBtn) {
			mapTblMdl.addRow(new Object[] { true,true, "", "" });
		} else if (arg0.getSource() == deleteBtn) {
			if (mapTbl.getSelectedRow() != -1) {
				mapTblMdl.removeRow(mapTbl.getSelectedRow());
			}
		}
	}

}
