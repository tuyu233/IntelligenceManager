package vision;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entity.Record;
import properties.Fonts;


public class AllData extends JPanel
{
	private List<Record> resultList;
	public void setResult(List<Record> resultList)
	{
		this.resultList = resultList;
		
		int resultsize = resultList.size();//TODO
		
		final MyTableModel model = new MyTableModel(resultsize, 7);
		table = new JTable(model);
		this.setLayout(new BorderLayout());
		this.add(table.getTableHeader(),BorderLayout.PAGE_START);
		this.add(table,BorderLayout.CENTER);
		table.getColumnModel().getColumn(0).setPreferredWidth(3);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setFont(Fonts.normal);

		//TODO
		
		for(int i=0;i<resultsize;i++)
		{
			model.setValueAt(resultList.get(i).getType(), i, 0);
			model.setValueAt(resultList.get(i).getTitle(), i, 1);
			model.setValueAt(resultList.get(i).getContent(), i, 2);
			model.setValueAt(resultList.get(i).getSaveTime().toString().substring(0, 10), i, 3);
			model.setValueAt(resultList.get(i).getBaseUrl(), i,4);
			model.setValueAt(resultList.get(i).getAuthor(), i, 5);
			model.setValueAt(resultList.get(i).getOther(), i, 6);
		}
		
		menu = new JPopupMenu();
		JMenuItem item = new JMenuItem("删除该行");
		menu.add(item);

		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(MainWindow.mainFrame,
						"确定删除所选记录吗", "删除", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					String deleteTitle = model.getValueAt(table.getSelectedRow(), 1).toString();
					model.removeRow(table.getSelectedRow());
				}
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (count == 0)
						count++;
				}

				if (e.getButton() == MouseEvent.BUTTON3) {// right key click
					if (count == 1) {
						menu.show(e.getComponent(), e.getX(), e.getY());
					}
					count = 0;
				}
			}
		});
		
		this.validate();
		this.repaint();
		
	}
	private JTable table ;
	private JPopupMenu menu;
	int count=0;
	private class MyTableModel extends DefaultTableModel
	{
		public MyTableModel(int row,int column)
		{
			super(row,column);
		}
		@Override
		public boolean isCellEditable(int arg0, int arg1) {
			return false;
		}

		public String getColumnName(int n) {
			String columnNames[] = { "类型", "标题", "正文", "时间", "源地址", "作者", "其他"};
			return columnNames[n];
		}
	};
	
	public AllData() 
	{
		
	}
}
