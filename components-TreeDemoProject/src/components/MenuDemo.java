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

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JFrame;

/* MenuDemo.java requires images/middle.gif. */

/*
 * This class is just like MenuLookDemo, except the menu items
 * actually do something, thanks to event listeners.
 */
public class MenuDemo implements ActionListener, ItemListener {
	JTextArea output;
	JScrollPane scrollPane;
	String newline = "\n";

	private JSplitPane splitPane;
	private JScrollPane treeView;

	public MenuDemo() {


	}


	public JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem rbMenuItem;
		JCheckBoxMenuItem cbMenuItem;

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("Configuration");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");
		menuBar.add(menu);

		//a group of JMenuItems
		menuItem = new JMenuItem("Index Files Directory",
				KeyEvent.VK_T);
		//menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		//a group of JMenuItems
		menuItem = new JMenuItem("Data Files Directory",
				KeyEvent.VK_T);
		//menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		//a group of JMenuItems
		menuItem = new JMenuItem("File Formats To Be Indexed",
				KeyEvent.VK_T);
		//menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_3, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		//a group of JMenuItems
		menuItem = new JMenuItem("File With Included File Extension In The Search",
				KeyEvent.VK_T);
		//menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_4, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menuItem.addActionListener(this);
		menu.add(menuItem);







		//Build second menu in the menu bar.
		menu = new JMenu("Actions");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
				"This menu does nothing");
		menuBar.add(menu);

		//a group of JMenuItems
		menuItem = new JMenuItem("Index Files",
				KeyEvent.VK_T);
		//menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_5, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		//a group of JMenuItems
		menuItem = new JMenuItem("Associate File Format",
				KeyEvent.VK_T);
		//menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_6, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menuItem.addActionListener(this);
		menu.add(menuItem);






		//Build second menu in the menu bar.
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
				"This menu does nothing");
		menuBar.add(menu);

		//a group of JMenuItems
		menuItem = new JMenuItem("About Bubble Search",
				KeyEvent.VK_T);
		//menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_5, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"This doesn't really do anything");
		menuItem.addActionListener(this);
		menu.add(menuItem);



		return menuBar;
	}

	public Container createContentPane() {
		//Create the content-pane-to-be.
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setOpaque(true);

		//Create a scrolled text area.
		output = new JTextArea(5, 30);
		output.setEditable(false);
		scrollPane = new JScrollPane(output);

		//Add the text area to the content pane.
		contentPane.add(scrollPane, BorderLayout.CENTER);

		return contentPane;
	}

	public static boolean propertiesExist(File propertiesFile, String propname) {
		Properties prop = new Properties();
		InputStream input = null;
		boolean exists = false;

		try {
			input = new FileInputStream(propertiesFile);

			prop.load(input);

			exists = prop.getProperty(propname) != null;

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return exists;
	}


	public void actionPerformed(ActionEvent e) {

		//		File directory = new File("./");
		//		System.out.println(directory.getAbsolutePath());
		//		System.out.println(directory.getAbsolutePath()+"program.properties");

		JMenuItem source = (JMenuItem)(e.getSource());
		String name = "";
		String propname = "";

		if (source.getText().equals("Index Files Directory") || source.getText().equals("Data Files Directory")
				|| source.getText().equals("File Formats To Be Indexed") || source.getText().equals("File With Included File Extension In The Search")) {

			if (source.getText().equals("Index Files Directory")) {
				name = "Index Files Directory:";
				propname = "indexpath";
			} else if (source.getText().equals("Data Files Directory")) {
				name = "Data Files Directory:";
				propname = "datapath";
			} else if (source.getText().equals("File Formats To Be Indexed")) {
				name = "File Formats (separate by comma):";
				propname = "fileformats";
			} else if (source.getText().equals("File With Included File Extension In The Search")) {
				name = "File With Included File Extension In The Search (separate by comma):";
				propname = "includeextension";
			}

			File propertiesFile = new File("./program.properties");
			if (propertiesFile.exists()) {


				Properties prop = new Properties();
				InputStream input = null;
				FileOutputStream output = null;
				try {
					input = new FileInputStream(propertiesFile);
					prop.load(input);
					input.close();

					String m = (String)JOptionPane.showInputDialog(null, name,
							name, JOptionPane.QUESTION_MESSAGE,null,null,prop.getProperty(propname));

					if (m != null) {
						m = m.trim();

						if (source.getText().equals("Index Files Directory") || source.getText().equals("Data Files Directory")) {
							if (m != null) {
								if (!"".equals(m)) {
									if (m.charAt(m.length()-1) != '\\') {
										m = m + "\\";
									}

									File f = new File(m);

									if (f.exists()) {

										prop.setProperty(propname, m);

										output = new FileOutputStream("./program.properties");
										prop.store(output, name);
										output.close();

										JOptionPane.showMessageDialog(null, "Success!");
									} else {
										JOptionPane.showMessageDialog(null, "Not a valid directory!");
									}
								} else {
									JOptionPane.showMessageDialog(null, "Please input valid directory!");
								}
							}
						} else {
							if (!"".equals(m) || source.getText().equals("File With Included File Extension In The Search")) {

								if (source.getText().equals("File Formats To Be Indexed")) {
									String[] arrOfStr = m.split(","); 
									Pattern pattern = Pattern.compile("[a-zA-Z]*");
									for (String a : arrOfStr) {
										a = a.trim();

										Matcher matcher = pattern.matcher(a);

										if (!matcher.matches() || "".equals(a)) {
											JOptionPane.showMessageDialog(null, "File format has invalid characters!");
											return;
										}
									}
								}

								prop.setProperty(propname, m);

								output = new FileOutputStream("./program.properties");
								prop.store(output, name);
								output.close();

								JOptionPane.showMessageDialog(null, "Success!");
							} else {
								JOptionPane.showMessageDialog(null, "Please input valid file format!");
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
				String m = JOptionPane.showInputDialog(name);

				if (m != null) {
					m = m.trim();

					if (source.getText().equals("Index Files Directory") || source.getText().equals("Data Files Directory")) {
						if (m != null) {
							if (!"".equals(m)) {
								if (m.charAt(m.length()-1) != '\\') {
									m = m + "\\";
								}

								File f = new File(m);

								if (f.exists()) {
									Properties props = new Properties();
									props.setProperty(propname, m);
									try {
										props.store(new FileOutputStream(new File("./program.properties")), name);
										JOptionPane.showMessageDialog(null, "Success!");
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								} else {
									JOptionPane.showMessageDialog(null, "Not a valid directory!");
								}
							} else {
								JOptionPane.showMessageDialog(null, "Please input valid directory!");
							}
						}
					} else {
						if (!"".equals(m) || source.getText().equals("File With Included File Extension In The Search")) {
							if (source.getText().equals("File Formats To Be Indexed")) {
								String[] arrOfStr = m.split(","); 
								Pattern pattern = Pattern.compile("[a-zA-Z]*");
								for (String a : arrOfStr) {
									a = a.trim();
									Matcher matcher = pattern.matcher(a);

									if (!matcher.matches() || "".equals(a)) {
										JOptionPane.showMessageDialog(null, "File format has invalid characters!");
										return;
									}
								}
							}

							Properties props = new Properties();
							props.setProperty(propname, m);
							try {
								props.store(new FileOutputStream(new File("./program.properties")), name);
								JOptionPane.showMessageDialog(null, "Success!");
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		} else if (source.getText().equals("Index Files")) {

			File propertiesFile = new File("./program.properties");
			if (propertiesFile.exists() && propertiesExist(propertiesFile, "indexpath") && propertiesExist(propertiesFile, "datapath")
					&& propertiesExist(propertiesFile, "fileformats")) {

				JOptionPane.showMessageDialog(null, "This may take a while. Please wait for the \"Success\" prompt!");

				Properties prop = new Properties();
				InputStream input = null;
				try {
					input = new FileInputStream(propertiesFile);
					prop.load(input);
					input.close();


					JTextArea ta = new JTextArea();
					TextAreaOutputStream taos = new TextAreaOutputStream( ta, 60 );
					PrintStream ps = new PrintStream( taos );
					System.setOut( ps );
					System.setErr( ps );

					//Create the scroll pane and add the tree to it. 
					treeView = new JScrollPane(ta);

					splitPane.setBottomComponent(treeView);

					Dimension minimumSize = new Dimension(100, 50);
					treeView.setMinimumSize(minimumSize);
					splitPane.setDividerLocation(25); 
					splitPane.setPreferredSize(new Dimension(500, 300));

					treeView.revalidate();
					treeView.repaint();

					splitPane.revalidate();
					splitPane.repaint();


					String[] args = new String[4];
					args[0] = "-index";
					args[1] = prop.getProperty("indexpath");
					args[2] = "-docs";
					args[3] = prop.getProperty("datapath");

					IndexFiles indexFiles = new IndexFiles(prop.getProperty("fileformats").toLowerCase());
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							if (indexFiles.index(args)) {
								JOptionPane.showMessageDialog(null, "Success!");
							} else {
								JOptionPane.showMessageDialog(null, "Failed!");
							}
							try {
								Thread.sleep(1000);
							} catch (InterruptedException ex) {
								ex.printStackTrace();
							}
						}
					});
					thread.start();

					//					IndexFiles indexFiles = new IndexFiles(prop.getProperty("fileformats"));
					//					if (indexFiles.index(args)) {
					//						JOptionPane.showMessageDialog(null, "Success!");
					//					} else {
					//						JOptionPane.showMessageDialog(null, "Failed!");
					//					}

				} catch (IOException e1) {

					try {
						input.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}

					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Please input all required configurations first!");
			}
		} else if (source.getText().equals("Associate File Format")) {

			File propertiesFile = new File("./program.properties");
			if (propertiesExist(propertiesFile, "fileformats")) {

				Properties prop = new Properties();
				InputStream input = null;
				try {
					input = new FileInputStream(propertiesFile);
					prop.load(input);
					input.close();

					String m = (String)JOptionPane.showInputDialog(null, "File Format",
							"File Format", JOptionPane.QUESTION_MESSAGE,null,null,prop.getProperty("fileformats"));

					if (m != null) {
						m = m.trim();

						String[] arrOfStr = m.split(","); 
						Pattern pattern = Pattern.compile("[a-zA-Z]*");
						String fileformats = "";
						for (String a : arrOfStr) {
							a = a.trim();
							Matcher matcher = pattern.matcher(a);

							if (!matcher.matches() || "".equals(a)) {
								JOptionPane.showMessageDialog(null, "File format has invalid characters!");
								return;
							} else {
								fileformats = fileformats + "\""+a+"\",";
							}
						}
						fileformats = fileformats.substring(0, fileformats.length() - 1);


						String n = JOptionPane.showInputDialog("Default Text Editor .exe Path:");
						//						JOptionPane.showMessageDialog(null, "Are you sure you want to re-associate file formats!");

						int input1 = JOptionPane.showConfirmDialog(null, 
								"Do you want to proceed?", "Are you sure you want to re-associate file formats!",JOptionPane.YES_NO_CANCEL_OPTION);

						if (input1 == 0 && n != null) {
							n = n.trim();

							if (n != null && !"".equals(n)) {


								File f = new File(n);

								if (f.exists()) {
									File f1 = new File("./fileassoc.ps1");
									f1.delete();

									PrintWriter writer = new PrintWriter("./fileassoc.ps1");
									writer.println("$exts=@("+ fileformats +")");
									writer.println("foreach ($ext in $exts){");
									writer.println("$extfile=$ext+\"file\"");
									writer.println("$dotext=\".\" + $ext");
									writer.println("cmd /c assoc $dotext=$extfile");
									writer.println("cmd /c \"ftype $extfile=\"\"" + n + "\"\" %1\"");
									writer.println("}");
									writer.close();
								}


								String command1[] = {
										"C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe",
										".\\fileassoc.ps1"
								};
								Process p = Runtime.getRuntime().exec(command1);

								SyncPipe syncPipeErrorStream = new SyncPipe(p.getErrorStream());
								SyncPipe syncPipeInputStream = new SyncPipe(p.getInputStream());
								Thread threadError = new Thread(syncPipeErrorStream);
								Thread threadInput = new Thread(syncPipeInputStream);
								threadError.start();
								threadInput.start();

								int returnCode = 0;
								try
								{
									returnCode = p.waitFor();

									threadError.join();
									threadInput.join();

									p.destroy();
								}
								catch(InterruptedException e1)
								{
									e1.printStackTrace();
									p.destroy();
								}

								if (returnCode == 0) {
									JOptionPane.showMessageDialog(null, "Success!");
								}

							}
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
			} else {
				JOptionPane.showMessageDialog(null, "Please input all required configurations first!");
			}
		} else if (source.getText().equals("About Bubble Search")) {


			JTextArea ta = new JTextArea();

			ta.append("Creator: Emmanuel T. Corpuz\n");
			ta.append("Email: emmanuel.corpuz@fisglobal.com\n");
			ta.append("emmanuelcorpuz@yahoo.com\n");
			ta.append("Version: 2019-11-14 (ModTax)\n");
			ta.setCaretPosition(output.getDocument().getLength());

			//Create the scroll pane and add the tree to it. 
			treeView = new JScrollPane(ta);

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




		/*JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Action event detected."
                   + newline
                   + "    Event source: " + source.getText()
                   + " (an instance of " + getClassName(source) + ")";
        output.append(s + newline);
        output.setCaretPosition(output.getDocument().getLength());*/
	}

	public void itemStateChanged(ItemEvent e) {
		JMenuItem source = (JMenuItem)(e.getSource());
		String s = "Item event detected."
				+ newline
				+ "    Event source: " + source.getText()
				+ " (an instance of " + getClassName(source) + ")"
				+ newline
				+ "    New state: "
				+ ((e.getStateChange() == ItemEvent.SELECTED) ?
						"selected":"unselected");
		output.append(s + newline);
		output.setCaretPosition(output.getDocument().getLength());
	}

	// Returns just the class name -- no package info.
	protected String getClassName(Object o) {
		String classString = o.getClass().getName();
		int dotIndex = classString.lastIndexOf(".");
		return classString.substring(dotIndex+1);
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = MenuDemo.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		//Create and set up the window.
		JFrame frame = new JFrame("MenuDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		MenuDemo demo = new MenuDemo();
		frame.setJMenuBar(demo.createMenuBar());
		frame.setContentPane(demo.createContentPane());

		//Display the window.
		frame.setSize(450, 260);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}


	public JSplitPane getSplitPane() {
		return splitPane;
	}


	public void setSplitPane(JSplitPane splitPane) {
		this.splitPane = splitPane;
	}


	public JScrollPane getTreeView() {
		return treeView;
	}


	public void setTreeView(JScrollPane treeView) {
		this.treeView = treeView;
	}
}