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

/* TextDemo.java requires no other files. */

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class TextDemo implements ActionListener {
	private JTextField textField;
	private JSplitPane splitPane;
	private JScrollPane treeView;
	private JTree tree;
	

	public TextDemo() {


	}

	public TextDemo(JSplitPane splitPane, JScrollPane treeView) {

		textField = new JTextField(20);
		textField.addActionListener(this);

		this.splitPane = splitPane;
		this.treeView = treeView;


	}

	public void actionPerformed(ActionEvent evt) {


		File propertiesFile = new File("./program.properties");
		if (propertiesFile.exists() && MenuDemo.propertiesExist(propertiesFile, "indexpath")) {

			Properties prop = new Properties();
			InputStream input = null;
			FileOutputStream output = null;
			try {
				input = new FileInputStream(propertiesFile);
				prop.load(input);
				input.close();

				String m = prop.getProperty("indexpath");


				if (m != null && !"".equals(m.trim())) {
					if (m.trim().charAt(m.length()-1) != '\\') {
						m = m + "\\";
					}

					File f = new File(m);

					if (f.exists()) {



						String text = textField.getText();

						if (!"".equals(text.trim())) {

							JOptionPane.showMessageDialog(null, "This may take a while. Please wait for the result!");

							treeView.removeAll();

							//Create the nodes.
							FileNode fileNodesearch = new FileNode(text, text, "");
							DefaultMutableTreeNode top =
									new DefaultMutableTreeNode(fileNodesearch);
							TreeDemo.createNodes(top, text);

							//Create a tree that allows one selection at a time.
							JTree tree = new JTree(top);
							this.tree = tree;
							ToolTipManager.sharedInstance().registerComponent(tree);
							tree.setCellRenderer(new MyTreeCellRenderer());
							tree.getSelectionModel().setSelectionMode
							(TreeSelectionModel.SINGLE_TREE_SELECTION);


							tree.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {

									if (e.getClickCount() == 2 && !e.isConsumed()) {
										e.consume();



										if(SwingUtilities.isLeftMouseButton(e)){

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

										} else if (SwingUtilities.isRightMouseButton(e)) {
											int x = e.getX();
										    int y = e.getY();

											if (tree.getRowForLocation(x, y) == -1) return;

											int row = tree.getRowForLocation(x, y);
										    TreePath path = tree.getPathForRow(row);

											DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();

											if (node == null) return;

											Object nodeInfo = node.getUserObject();
											FileNode fileNode = (FileNode)nodeInfo;



											Toolkit.getDefaultToolkit()
											.getSystemClipboard()
											.setContents(
													new StringSelection(fileNode.getFilepath()),
													null
													);
										}
										
									} else if (SwingUtilities.isRightMouseButton(e)) {
										
										int x = e.getX();
									    int y = e.getY();

										if (tree.getRowForLocation(x, y) == -1) return;

										int row = tree.getRowForLocation(x, y);
									    TreePath path = tree.getPathForRow(row);

										DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();

										if (node == null) return;

										Object nodeInfo = node.getUserObject();
										FileNode fileNode = (FileNode)nodeInfo;

										Toolkit.getDefaultToolkit()
										.getSystemClipboard()
										.setContents(
												new StringSelection(fileNode.toString()),
												null
												);

									}




								}
							});


							//Listen for when the selection changes.
							tree.addTreeSelectionListener(new TreeSelectionListener()
							{
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
							});

							boolean playWithLineStyle = false;
							String lineStyle = "Horizontal";

							if (playWithLineStyle) {
								System.out.println("line style = " + lineStyle);
								tree.putClientProperty("JTree.lineStyle", lineStyle);
							}

							//Create the scroll pane and add the tree to it. 
							treeView = new JScrollPane(tree);

							splitPane.setBottomComponent(treeView);

							Dimension minimumSize = new Dimension(100, 50);
							treeView.setMinimumSize(minimumSize);
							splitPane.setDividerLocation(25); 
							splitPane.setPreferredSize(new Dimension(500, 300));

							treeView.revalidate();
							treeView.repaint();

							splitPane.revalidate();
							splitPane.repaint();
						}



					}
				}

			} catch (IOException e1) {

				try {
					input.close();
					output.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}

				e1.printStackTrace();
			}

		} else {
			JOptionPane.showMessageDialog(null, "Please input all required configurations first!");
		}

	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}




}