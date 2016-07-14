package vision;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import properties.Fonts;


public class AllData extends JPanel
{
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
			String columnNames[] = { "类型", "标题", "时间", "作者", "其他",
					"正文", "源地址" };
			return columnNames[n];
		}
	};
	
	public AllData() 
	{
		int resultsize = 50;//TODO
		
		final MyTableModel model = new MyTableModel(resultsize, 7);
		table = new JTable(model);
		this.setLayout(new BorderLayout());
		this.add(table.getTableHeader(),BorderLayout.PAGE_START);
		this.add(table,BorderLayout.CENTER);
		table.getColumnModel().getColumn(0).setPreferredWidth(3);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setFont(Fonts.normal);

		//TODO
		model.setValueAt("媒体", 0, 0);
		model.setValueAt("公车改革应该以维权为目标", 0, 1);
		model.setValueAt("2009-01-01", 0, 2);
		model.setValueAt("cyy", 0, 3);
		model.setValueAt("公车改革", 0, 4);
		model.setValueAt("厅级干部有可能会下马，可能性", 0, 5);
		model.setValueAt("http://www.baidu.com", 0, 6);
		
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
	}
}
