package org.scube.scubedao.ide.propertyeditor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.scube.ide.screen.BaseScreen;
import org.scube.scubedao.ide.propertyeditor.section.BusinessSection;
import org.scube.scubedao.ide.propertyeditor.section.DefaultsSection;
import org.scube.scubedao.ide.propertyeditor.section.MSRSection;
import org.scube.scubedao.ide.propertyeditor.section.ModulesSection;
import org.scube.scubedao.ide.propertyeditor.section.PluralSection;
import org.scube.scubedao.ide.propertyeditor.section.SchemaSection;
import org.scube.scubedao.ide.propertyeditor.section.AbbrevationsSection;
import org.scube.scubedao.ide.propertyeditor.section.ValidationsSection;

public abstract class AbstractIcgProjectScreen extends BaseScreen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JSplitPane contentPane;

	protected JTree sectionsTree;

	private JScrollPane sectionsPane;

	protected JScrollPane sectionContentPane;

	private JPanel buttonsPane;

	protected JButton okBtn;
	
	protected SchemaSection schemaSection ;
	protected BusinessSection businessSection ;
	protected AbbrevationsSection wordsSection  ;
	protected ModulesSection modulesSection ;
	protected PluralSection pluralSection ;
	protected MSRSection msrSection ;
	protected DefaultsSection defaultsSection ;
	protected ValidationsSection validationsSection ;
	

	public AbstractIcgProjectScreen() {
		createComponents();
		sizeComponents();
		addComponents();
		initComponents() ;
	}

	protected void initComponents() {
		sectionsTree.setRootVisible(false) ;
		sectionsTree.expandRow(1) ;
		contentPane.setDividerLocation(180) ;
	}

	protected void createComponents() {

		sectionsTree = new JTree(getSectionNode());
		sectionsPane = new JScrollPane(sectionsTree);

		schemaSection = new SchemaSection() ;
		businessSection = new BusinessSection() ;
		wordsSection = new AbbrevationsSection() ;
		modulesSection = new ModulesSection() ;
		pluralSection = new PluralSection() ;
		msrSection = new MSRSection() ;
		defaultsSection = new DefaultsSection() ;
		validationsSection = new ValidationsSection() ;
		
		sectionContentPane = new JScrollPane(schemaSection);

		contentPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sectionsPane,
				sectionContentPane);
				

		buttonsPane = new JPanel();
		okBtn = new JButton("Ok") ;

	}

	protected void sizeComponents() {
		// Provide minimum sizes for the two components in the split pane
//		Dimension minimumSize = new Dimension(180, 50);
//		sectionsPane.setMinimumSize(minimumSize);
//		sectionContentPane.setMinimumSize(minimumSize);
	}

	private DefaultMutableTreeNode getSectionNode() {

		DefaultMutableTreeNode top = new DefaultMutableTreeNode("LMS");

		DefaultMutableTreeNode section = null;
	
		section = new DefaultMutableTreeNode("General");		
		top.add(section);
		
		section = new DefaultMutableTreeNode("Business");		
		top.add(section);
		
		section.add( new DefaultMutableTreeNode("Abbreviations") );		
		
		
		section.add( new DefaultMutableTreeNode("Modules"));		
		
		
		section.add( new DefaultMutableTreeNode("Plural"));		
		
		
		section.add( new DefaultMutableTreeNode("Method Specification")) ;		
		
		
		section.add( new DefaultMutableTreeNode("Defaults"));		
		
		section.add( new DefaultMutableTreeNode("Validation"));	
		

		return top;

	}

	protected void addComponents() {

		buttonsPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		buttonsPane.add(okBtn);

		setLayout(new BorderLayout(10, 10));

		add(contentPane, BorderLayout.CENTER);
		add(buttonsPane, BorderLayout.SOUTH);

	}
}
