//搜索结果
package vision;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import properties.*;

public class SearchResult extends Panel 
{
	private JTable searchResult_table;
	private JTextPane searchResult_textPane;
	
	//tabelModel
	private class MyTableModel extends DefaultTableModel{
		public MyTableModel(int row, int column){
			super(row, column);
		}

		public boolean isCellEditable(int row, int column){
			if(column==1)
				return true;
			return false;
		}
	}
	
	//tableRenderer,TODO
	private class MyTableCellRenderer implements TableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable arg0,Object arg1, boolean arg2, boolean arg3, int row, int column) 
		{
			if(column==1)
			{
				JPanel tmp_panel=new JPanel();
				tmp_panel.setLayout(null);
				JButton tmp_button=new JButton(Attributes.WHOLEPASSAGE);
				tmp_button.setBounds(0,30,100,30);
				tmp_button.setFont(Fonts.searchButton);
				tmp_panel.add(tmp_button);
				return tmp_panel;
			}
			else
			{
				searchResult_textPane = new JTextPane();
				searchResult_textPane.setText("1\n2\n3\n4\n5\n");
				return searchResult_textPane;
			}
		}

	}
	
	//MyButtonEditor
	private class myButtonEditor extends DefaultCellEditor
	{
		JPanel tmp_panel=new JPanel();
		JButton tmp_button=new JButton(Attributes.WHOLEPASSAGE);
	      public myButtonEditor(JCheckBox checkBox)
	      {
	        super(checkBox);
	        tmp_button.setOpaque(true);
	    	tmp_button.setFont(Fonts.searchButton);
	      }
	      public JComponent getTableCellEditorComponent(JTable table, Object value,
	              boolean isSelected, final int row, int column) 
	      {
	    	  tmp_button.addActionListener
		        (new ActionListener() 
		        {
		          public void actionPerformed(ActionEvent e) {
		        	  JFrame f=new JFrame();
		        	  f.show();
		        	  f.setBounds(500, 120, 600, 500);
		        	  f.setTitle("第"+row+"行");
		          }
		        });
	    	  
				tmp_panel.setLayout(null);
		    	tmp_button.setBounds(0,30,100,30);
				tmp_panel.add(tmp_button);
				return tmp_panel;
	      }
		
	}
	private void updateRowHeights() 
	{
		for (int row = 0; row < searchResult_table.getRowCount(); row++) {
			int rowHeight =searchResult_table.getRowHeight();

			for (int column = 0; column < searchResult_table.getColumnCount(); column++) {
				Component comp = searchResult_table.prepareRenderer(
						searchResult_table.getCellRenderer(row, column), row,
						column);
				rowHeight = Math.max(rowHeight,
						comp.getPreferredSize().height);
			}

			searchResult_table.setRowHeight(row, rowHeight);
		}
	}
	
	public SearchResult() 
	{
		int resultSize=1;//TODO resultSize
		
		this.setLayout(new BorderLayout());
		searchResult_table=new JTable();
		MyTableModel model = new MyTableModel(resultSize,2);
		searchResult_table.setModel(model);
		searchResult_table.getColumnModel().getColumn(0).setPreferredWidth((int) (Attributes.MAIN_FRAME_WIDTH*0.905));
		searchResult_table.setEnabled(true);
		searchResult_table.getTableHeader().setVisible(false);
		searchResult_table.setDefaultRenderer(Object.class,new MyTableCellRenderer());
		searchResult_table.getColumnModel().getColumn(1).setCellEditor(new myButtonEditor(new JCheckBox()));
		this.add(searchResult_table);
		updateRowHeights();
		
	}

}
