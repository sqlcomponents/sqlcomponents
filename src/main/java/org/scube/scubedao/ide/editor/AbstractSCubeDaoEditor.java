package org.scube.scubedao.ide.editor;

import org.scube.ide.screen.BaseScreen;
import org.scube.scubedao.ide.component.EntityPane;
import org.scube.scubedao.ide.component.MethodPane;
import org.scube.scubedao.ide.component.ServicePane;
import org.scube.scubedao.ide.editor.navigator.impl.RDBMSNavigator;
import org.scube.scubedao.model.Entity;
import org.scube.scubedao.model.Method;
import org.scube.scubedao.model.Service;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;


public abstract class AbstractSCubeDaoEditor extends BaseScreen {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String ENTITY_PANE = "ENTITY_PANE";

	private static final String METHOD_PANE = "METHOD_PANE";
	private static final String SERVICE_PANE = "SERVICE_PANE";


	private JSplitPane contentPane;

	private JPanel navigationPane;
	private JPanel footerPane;
	private ButtonGroup buttonGroup;
	protected JToggleButton listViewBtn;
	protected JToggleButton treeViewBtn;

	protected JScrollPane navigatorSPane;
	
	protected RDBMSNavigator rdbmsNavigator;

	protected DefaultMutableTreeNode entitiesTreeNode;
	protected JTree entitiesTree;

	private JPanel editorPane ;
	private CardLayout editorPaneCardLayout ;
	protected EntityPane entityPane;
	protected MethodPane methodPane;
	protected ServicePane servicePane ;

	protected void initComponents() {
		
		buttonGroup.add(listViewBtn);
		buttonGroup.add(treeViewBtn);

		listViewBtn.setSelected(true) ;
		
		//entitiesTree.setRootVisible(false);

		
	}

	protected void createComponents() {

	
		navigationPane  = new JPanel();
		footerPane     = new JPanel();
		buttonGroup    = new ButtonGroup();
		listViewBtn    = new JToggleButton("L");
		treeViewBtn    = new JToggleButton("T");

		rdbmsNavigator = new RDBMSNavigator(this);
		navigatorSPane     = new JScrollPane(rdbmsNavigator);
		

		entitiesTreeNode = new DefaultMutableTreeNode("");
		entitiesTree = new JTree(entitiesTreeNode);

		entityPane = new EntityPane();
		methodPane = new MethodPane() ;
		servicePane = new ServicePane() ;
		
		editorPaneCardLayout = new CardLayout() ;
		editorPane = new JPanel(editorPaneCardLayout) ;

		contentPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navigationPane,
				editorPane);

	}

	protected void sizeComponents() {
		// Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(150, 50);
		navigatorSPane.setMinimumSize(minimumSize);
		entityPane.setMinimumSize(minimumSize);
	}

	protected void addComponents() {
		

		navigationPane.setLayout(new BorderLayout());

		footerPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		footerPane.add(listViewBtn);
		footerPane.add(treeViewBtn);

		navigationPane.add(navigatorSPane, BorderLayout.CENTER);
		navigationPane.add(footerPane, BorderLayout.SOUTH);

		editorPane.add(entityPane,ENTITY_PANE) ;
		editorPane.add(methodPane,METHOD_PANE) ;
		editorPane.add(servicePane,SERVICE_PANE) ;
		
		setLayout(new BorderLayout());
		add(contentPane, BorderLayout.CENTER);

	}
	
	public void setService(Service service) {
		editorPaneCardLayout.show(editorPane, SERVICE_PANE) ;
		servicePane.getService() ;
		servicePane.setService(service) ;
	}
	
	public void setEntity(Entity entity) {
		editorPaneCardLayout.show(editorPane, ENTITY_PANE) ;
		entityPane.getEntity();
		entityPane.setEntity(entity);
	}
	
	public void setMethod(Method method) {
		editorPaneCardLayout.show(editorPane, METHOD_PANE) ;
		methodPane.getMethod() ;
		methodPane.setMethod(method);
	}

}
