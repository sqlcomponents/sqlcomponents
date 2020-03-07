package org.scube.scubedao.ide.editor.navigator.impl;

import java.util.List;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.scube.scubedao.ide.editor.AbstractSCubeDaoEditor;
import org.scube.scubedao.ide.editor.navigator.DaoProjectNavigator;
import org.scube.scubedao.model.DaoProject;
import org.scube.scubedao.model.Entity;
import org.scube.scubedao.model.Method;
import org.scube.scubedao.model.Service;
import org.scube.scubedao.model.Table;

public class RDBMSNavigator extends JTree implements DaoProjectNavigator,
		TreeSelectionListener {

	private DefaultMutableTreeNode parentNode;
	private DefaultMutableTreeNode tablesNode;
	private DefaultMutableTreeNode viewsNode;
	private DefaultMutableTreeNode mviewsNode;
	private DefaultMutableTreeNode packagesNode;
	private DefaultMutableTreeNode proceduresNode;
	private DefaultMutableTreeNode functionsNode;

	private AbstractSCubeDaoEditor sCubeDaoEditor;

	public RDBMSNavigator(AbstractSCubeDaoEditor sCubeDaoEditor) {
		super(getSectionNode());

		this.sCubeDaoEditor = sCubeDaoEditor;
		parentNode = (DefaultMutableTreeNode) getModel().getRoot();

		tablesNode = (DefaultMutableTreeNode) parentNode.getChildAt(0);
		viewsNode = (DefaultMutableTreeNode) parentNode.getChildAt(1);
		mviewsNode = (DefaultMutableTreeNode) parentNode.getChildAt(2);
		packagesNode = (DefaultMutableTreeNode) parentNode.getChildAt(3);
		proceduresNode = (DefaultMutableTreeNode) parentNode.getChildAt(4);
		functionsNode = (DefaultMutableTreeNode) parentNode.getChildAt(5);

		this.getSelectionModel().addTreeSelectionListener(this);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void setIcgProject(DaoProject icgProject) {

		parentNode.setUserObject(icgProject);

		tablesNode.removeAllChildren();
		viewsNode.removeAllChildren();
		mviewsNode.removeAllChildren();
		packagesNode.removeAllChildren();
		functionsNode.removeAllChildren();
		proceduresNode.removeAllChildren();

		for (Entity entity : icgProject.getOrm().getEntities()) {
			if (entity.getTableType().equals(Table.TYPE_TABLE)) {
				tablesNode.add(new DefaultMutableTreeNode(entity));
			} else if (entity.getTableType().equals(Table.TYPE_VIEW)) {
				viewsNode.add(new DefaultMutableTreeNode(entity));
			} else if (entity.getTableType().equals(Table.TYPE_MVIEW)) {
				mviewsNode.add(new DefaultMutableTreeNode(entity));
			}
		}

		List<Service> services = icgProject.getOrm().getServices();

		DefaultMutableTreeNode defaultMutableTreeNode = null;
		if (services != null) {
			for (Service service1 : services) {
				defaultMutableTreeNode = new DefaultMutableTreeNode(service1);
				packagesNode.add(defaultMutableTreeNode);
				for (Method method : service1.getMethods()) {
					defaultMutableTreeNode.add(new DefaultMutableTreeNode(
							method));
				}
			}
		}

		List<Method> methods = icgProject.getOrm().getMethods();
		if (methods != null) {
			DefaultMutableTreeNode nodeToAdd = null;
			for (Method method : methods) {

				if (method.getOutputProperty() == null) {
					nodeToAdd = proceduresNode;
				} else {
					nodeToAdd = functionsNode;
				}

				nodeToAdd.add(new DefaultMutableTreeNode(method));
			}
		}
		((DefaultTreeModel) getModel()).setRoot(parentNode);
	}

	private static DefaultMutableTreeNode getSectionNode() {
		DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode("Dao");
		parentNode.add(new DefaultMutableTreeNode("Tables"));
		parentNode.add(new DefaultMutableTreeNode("Views"));
		parentNode.add(new DefaultMutableTreeNode("Materialized Views"));
		parentNode.add(new DefaultMutableTreeNode("Packages"));
		parentNode.add(new DefaultMutableTreeNode("Stored Procedures"));
		parentNode.add(new DefaultMutableTreeNode("Functions"));
		return parentNode;
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		TreePath selectionPath = getSelectionPath();
		if (selectionPath != null) {
			Object selectedObject = ((DefaultMutableTreeNode) selectionPath
					.getLastPathComponent()).getUserObject();
			if (selectedObject instanceof Entity) {
				sCubeDaoEditor.setEntity((Entity) selectedObject);
			} else if (selectedObject instanceof Method) {
				sCubeDaoEditor.setMethod((Method) selectedObject);
			} else if (selectedObject instanceof Service) {
				sCubeDaoEditor.setService((Service) selectedObject);
			}
		}
	}

}
