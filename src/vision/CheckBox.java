package vision;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import properties.*;

public class CheckBox extends JPanel
{
	private JCheckBox governmentCheckBox;
	private JCheckBox mediaCheckBox;
	private JCheckBox publicCheckBox;
	private JButton filter;
	CheckBox()
	{
		governmentCheckBox = new JCheckBox("政府");
		governmentCheckBox.setSelected(true);
		mediaCheckBox = new JCheckBox("媒体");
		mediaCheckBox.setSelected(true);
		publicCheckBox = new JCheckBox("公众");
		publicCheckBox.setSelected(true);
		filter = new JButton("筛选");
		this.setLayout(new BorderLayout());
		Box box = Box.createHorizontalBox();
		box.add(governmentCheckBox);
		box.add(mediaCheckBox);
		box.add(publicCheckBox);
		box.add(filter);
		box.add(Box.createHorizontalGlue());
		this.add(box,BorderLayout.CENTER);
		
	}
}
