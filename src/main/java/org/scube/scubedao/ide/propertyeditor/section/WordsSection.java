package org.scube.scubedao.ide.propertyeditor.section;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.scube.scubedao.constants.ScreenConstants;
import org.scube.scubedao.ide.propertyeditor.interfaces.IcgProjectSection;
import org.scube.scubedao.model.DaoProject;

public class WordsSection extends JPanel implements IcgProjectSection,
		ScreenConstants, ActionListener {

	private DaoProject icgProject;

	private JPanel headerPane;
	protected JButton addBtn;
	protected JButton deleteBtn;

	protected DefaultTableModel mapTblMdl;
	protected JTable mapTbl;
	protected JScrollPane mapSPane;

	public WordsSection() {
		createComponents();
		addComponents();
		addActions();
	}

	private void addActions() {
		addBtn.addActionListener(this) ;
		deleteBtn.addActionListener(this) ;		
	}

	private void createComponents() {

		headerPane = new JPanel();
		addBtn = new JButton("+");
		deleteBtn = new JButton("-");

		mapTblMdl = new DefaultTableModel(new String[] { "Relational",
				"Object Oriented" }, 0);
		mapTbl = new JTable(mapTblMdl);
		mapSPane = new JScrollPane(mapTbl);

	}

	private void addComponents() {

		headerPane.setLayout(new FlowLayout(FlowLayout.RIGHT, V_GAP, H_GAP));
		headerPane.add(addBtn);
		headerPane.add(deleteBtn);

		setLayout(new BorderLayout());
		add(headerPane, BorderLayout.NORTH);
		add(mapSPane, BorderLayout.SOUTH);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DaoProject getIcgProject() {
		if (icgProject != null) {
			icgProject.setWordsMap(getTableValues());
		}
		return icgProject;
	}

	public void setIcgProject(DaoProject icgProject) {
		if( icgProject != null ) {
			setTableValues(icgProject.getWordsMap()) ;
		}
		this.icgProject = icgProject;
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == addBtn) {
			mapTblMdl.addRow(new Object[]{null,""}) ;
		}
		else if (arg0.getSource() == deleteBtn) {
			if(mapTbl.getSelectedRow() != -1) {
				mapTblMdl.removeRow(mapTbl.getSelectedRow()) ;
			}
		}
	}

	private HashMap<String, String> getTableValues() {
		HashMap<String, String> hashMap = new HashMap<String, String>(mapTblMdl.getRowCount()) ;
		for (int i = mapTblMdl.getRowCount() - 1; i >= 0; i--) {
			hashMap.put(mapTblMdl.getValueAt(i, 0).toString(), mapTblMdl.getValueAt(i, 1).toString() ) ;
		}
		return hashMap ;
	}
	private void setTableValues(HashMap<String, String> map) {
		for (int i = mapTblMdl.getRowCount() - 1; i >= 0; i--) {
			mapTblMdl.removeRow(0);
		}
		if( map != null ) {
			Vector<Object> rowVector;
			for (String relational : map.keySet()) {
				rowVector = new Vector<Object>(2);
				rowVector.add(relational);
				rowVector.add(map.get(relational));
				mapTblMdl.addRow(rowVector);
			}
		}


	}

}
