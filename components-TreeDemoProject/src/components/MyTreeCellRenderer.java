package components;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

class MyTreeCellRenderer extends DefaultTreeCellRenderer {
	int rendering = 0;
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		Component cell = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		if (cell instanceof JComponent) {

			DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;

			if (node != null) {

				Object nodeInfo = node.getUserObject();
				FileNode fileNode = (FileNode)nodeInfo;




				((JComponent) cell).setToolTipText(fileNode.getFilepath());
			}
		}
		return cell;
	}
}
