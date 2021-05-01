/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package components;

/**
 * This application that requires the following additional files:
 *   TreeDemoHelp.html
 *    arnold.html
 *    bloch.html
 *    chan.html
 *    jls.html
 *    swingtutorial.html
 *    tutorial.html
 *    tutorialcont.html
 *    vm.html
 */
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.apache.commons.io.FilenameUtils;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TreeDemo extends JPanel implements TreeSelectionListener {
	private JEditorPane textPane;
	private JTree tree;
	private URL helpURL;
	private static boolean DEBUG = false;


	//Optionally play with line styles.  Possible values are
	//"Angled" (the default), "Horizontal", and "None".
	private static boolean playWithLineStyle = false;
	private static String lineStyle = "Horizontal";

	//Optionally set the look and feel.
	private static boolean useSystemLookAndFeel = false;
	
	public TreeDemo() {
		
	}

	public TreeDemo(MenuDemo demo) {
		super(new GridLayout(1,0));





		//Create the nodes.
		DefaultMutableTreeNode top =
				new DefaultMutableTreeNode(new FileNode("", "", ""));
		//createNodes(top, "test1");

		//Create a tree that allows one selection at a time.
		tree = new JTree(top);
		ToolTipManager.sharedInstance().registerComponent(tree);
	    tree.setCellRenderer(new MyTreeCellRenderer());
		tree.getSelectionModel().setSelectionMode
		(TreeSelectionModel.SINGLE_TREE_SELECTION);

		//Listen for when the selection changes.
		tree.addTreeSelectionListener(this);

		if (playWithLineStyle) {
			System.out.println("line style = " + lineStyle);
			tree.putClientProperty("JTree.lineStyle", lineStyle);
		}

		//Create the scroll pane and add the tree to it. 
		JScrollPane treeView = new JScrollPane(tree);


		//Add the scroll panes to a split pane.
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		TextDemo textDemo = new TextDemo(splitPane, treeView);
		
		demo.setSplitPane(splitPane);
		demo.setTreeView(treeView);


		splitPane.setTopComponent(textDemo.getTextField());
		splitPane.setBottomComponent(treeView);

		Dimension minimumSize = new Dimension(100, 50);
		treeView.setMinimumSize(minimumSize);
		splitPane.setDividerLocation(25); 
		splitPane.setPreferredSize(new Dimension(500, 300));

		//Add the split pane to this panel.
		add(splitPane);
	}


	/** Required by TreeSelectionListener interface. */
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				tree.getLastSelectedPathComponent();

		if (node == null) return;

		Object nodeInfo = node.getUserObject();
		FileNode fileNode = (FileNode)nodeInfo;
    	File file = new File(fileNode.getFilepath());
    	try {
			Desktop.getDesktop().open(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}


	


	public static void createNodes(DefaultMutableTreeNode top, String args) {

		File propertiesFile = new File("./program.properties");
		if (propertiesFile.exists() && MenuDemo.propertiesExist(propertiesFile, "indexpath")
				 && MenuDemo.propertiesExist(propertiesFile, "includeextension")) {


			Properties prop = new Properties();
			InputStream input = null;
			try {
				input = new FileInputStream(propertiesFile);
				prop.load(input);
				input.close();


				FileNode fileNodesearch = new FileNode(args, args, "");
				Searcher searcher = new Searcher();
				HashMap<FileNode, HashMap> hm = searcher.getNodes(fileNodesearch, prop.getProperty("indexpath"), prop.getProperty("includeextension").toLowerCase());
				if (hm.get(fileNodesearch) != null) {



					ArrayList<HashMap> al = new ArrayList<HashMap>();
					al.add(hm.get(fileNodesearch));



					ArrayList<HashMap> allevel = new ArrayList<HashMap>();
					HashMap<FileNode, DefaultMutableTreeNode> allevel0 = new HashMap<FileNode, DefaultMutableTreeNode>();
					Iterator hmIterator0 = hm.get(fileNodesearch).entrySet().iterator(); 
					while (hmIterator0.hasNext()) {
						Map.Entry mapElement0 = (Map.Entry)hmIterator0.next(); 

						DefaultMutableTreeNode level = new DefaultMutableTreeNode(mapElement0.getKey());
						top.add(level);
						allevel0.put((FileNode)mapElement0.getKey(), level);
					} 
					allevel.add(allevel0);



					int cnt2 = 0;
					while (al.size() > 0) {

						ArrayList<HashMap> al2 = new ArrayList<HashMap>();
						ArrayList<HashMap> al2level = new ArrayList<HashMap>();

						cnt2 = 0;
						while (cnt2 < al.size()) {

							HashMap<FileNode, HashMap> hmal = (HashMap<FileNode, HashMap>)al.get(cnt2);
							HashMap<FileNode, DefaultMutableTreeNode> hmallevel = (HashMap<FileNode, DefaultMutableTreeNode>)allevel.get(cnt2);

							Iterator hmIterator = hmal.entrySet().iterator(); 
							while (hmIterator.hasNext()) {
								Map.Entry mapElement = (Map.Entry)hmIterator.next(); 
								HashMap<FileNode, HashMap> hm2 = (HashMap<FileNode, HashMap>)mapElement.getValue();

								if (hm2 == null)
									continue;

								al2.add(hm2);

								DefaultMutableTreeNode dmtn = hmallevel.get(mapElement.getKey());
								Iterator hmIteratordmtn = hm2.entrySet().iterator(); 
								HashMap<FileNode, DefaultMutableTreeNode> alleveldmtn = new HashMap<FileNode, DefaultMutableTreeNode>();
								while (hmIteratordmtn.hasNext()) {
									Map.Entry mapElementdmtn = (Map.Entry)hmIteratordmtn.next(); 

									DefaultMutableTreeNode level = new DefaultMutableTreeNode(mapElementdmtn.getKey());
									dmtn.add(level);
									alleveldmtn.put((FileNode)mapElementdmtn.getKey(), level);
								} 
								al2level.add(alleveldmtn);

							} 


							cnt2 = cnt2 + 1;
						}

						al = new ArrayList<HashMap>();
						al = (ArrayList<HashMap>)al2.clone();

						allevel = new ArrayList<HashMap>();
						allevel = (ArrayList<HashMap>)al2level.clone();

					}
				}



			} catch (IOException e1) {

				try {
					input.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}

				e1.printStackTrace();
			}
		}











		//        
		//
		//        category = new DefaultMutableTreeNode("Books for Java Programmers");
		//        top.add(category);
		//
		//        //original Tutorial
		//        book = new DefaultMutableTreeNode(new BookInfo
		//            ("The Java Tutorial: A Short Course on the Basics",
		//            "tutorial.html"));
		//        category.add(book);
		//        
		//        book2 = new DefaultMutableTreeNode(new BookInfo
		//			("emmantest",
		//			"tutorial.html"));
		//        book.add(book2);
		//
		//        //Tutorial Continued
		//        book = new DefaultMutableTreeNode(new BookInfo
		//            ("The Java Tutorial Continued: The Rest of the JDK",
		//            "tutorialcont.html"));
		//        category.add(book);
		//
		//        //JFC Swing Tutorial
		//        book = new DefaultMutableTreeNode(new BookInfo
		//            ("The JFC Swing Tutorial: A Guide to Constructing GUIs",
		//            "swingtutorial.html"));
		//        category.add(book);
		//
		//        //Bloch
		//        book = new DefaultMutableTreeNode(new BookInfo
		//            ("Effective Java Programming Language Guide",
		//	     "bloch.html"));
		//        category.add(book);
		//
		//        //Arnold/Gosling
		//        book = new DefaultMutableTreeNode(new BookInfo
		//            ("The Java Programming Language", "arnold.html"));
		//        category.add(book);
		//
		//        //Chan
		//        book = new DefaultMutableTreeNode(new BookInfo
		//            ("The Java Developers Almanac",
		//             "chan.html"));
		//        category.add(book);
		//
		//        category = new DefaultMutableTreeNode("Books for Java Implementers");
		//        top.add(category);
		//
		//        //VM
		//        book = new DefaultMutableTreeNode(new BookInfo
		//            ("The Java Virtual Machine Specification",
		//             "vm.html"));
		//        category.add(book);
		//
		//        //Language Spec
		//        book = new DefaultMutableTreeNode(new BookInfo
		//            ("The Java Language Specification",
		//             "jls.html"));
		//        category.add(book);
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event dispatch thread.
	 */
	private static void createAndShowGUI() {
		if (useSystemLookAndFeel) {
			try {
				UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				System.err.println("Couldn't use system look and feel.");
			}
		}

		//Create and set up the window.
		JFrame frame = new JFrame("Bubble Search: A Text Files Search Tool");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		MenuDemo demo = new MenuDemo();
		frame.setJMenuBar(demo.createMenuBar());
		frame.setContentPane(demo.createContentPane());

		//Add content to the window.
		frame.add(new TreeDemo(demo));


		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		//Schedule a job for the event dispatch thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
