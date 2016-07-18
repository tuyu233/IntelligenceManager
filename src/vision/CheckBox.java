package vision;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import entity.Record;
import properties.*;

public class CheckBox extends JPanel
{
	private JCheckBox governmentCheckBox = new JCheckBox("政府");
	private JCheckBox mediaCheckBox = new JCheckBox("媒体");
	private JCheckBox publicCheckBox = new JCheckBox("公众");
	JButton filter;
	CheckBox()
	{
		
		governmentCheckBox.setSelected(true);	
		mediaCheckBox.setSelected(true);
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
	
	Component checkButton()
	{
		return this.filter;
	}
	List<Record> updateList(List<Record> resultList)
	{
		List<Record> filterList = new ArrayList<>();
		int i=0;
		for(Record record : resultList){
			if((record.getType().contains("政府")&&governmentCheckBox.isSelected()==true)
					||(record.getType().contains("媒体")&&mediaCheckBox.isSelected()==true)
					||(record.getType().contains("公众")&&publicCheckBox.isSelected()==true))
				
				{
					filterList.add(record);
					i++;
					System.out.print(i);
				}
			
		}
		return filterList;
		
	}
}
