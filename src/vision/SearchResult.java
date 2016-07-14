//搜索结果
package vision;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.AbstractDocument.Content;

import properties.*;

public class SearchResult extends JPanel 
{
	private JTable searchResult_table;
	//tabelModel
	private class MyTableModel extends DefaultTableModel{
		public MyTableModel(int row, int column){
			super(row, column);
		}

		public boolean isCellEditable(int row, int column){
				return true;
		}
	}
	
	//tableRenderer,TODO
	private class MyButtonRenderer implements TableCellRenderer
	{
		public Component getTableCellRendererComponent(JTable arg0,Object arg1, boolean arg2, boolean arg3, int row, int column) 
		{
			JPanel tmp_panel=new JPanel();
			tmp_panel.setLayout(null);
			JButton tmp_button=new JButton(Attributes.WHOLEPASSAGE);
			tmp_button.setBounds(0,30,100,30);
			tmp_button.setFont(Fonts.searchButton);
			tmp_panel.add(tmp_button);
			return tmp_panel;
		}
	}
	
	private class MyTableCellRenderer implements TableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable arg0,Object arg1, boolean arg2, boolean arg3, int row, int column) 
		{
			JPanel searchResult_Panel = new JPanel();
			JLabel style = new JLabel();
			JLabel title = new JLabel();
			searchResult_Panel.setLayout(new GridLayout(3,1));
			Box whole =  Box.createVerticalBox();
			Box line1 = Box.createHorizontalBox();
			Box line2 = Box.createHorizontalBox();
			Box line3 = Box.createHorizontalBox();
			
			style.setText("媒体");//TODO
			style.setFont(Fonts.STYLE_TITLE);
			style.setForeground(Color.WHITE);
			style.setBackground(Color.BLACK);
			style.setOpaque(true);
			line1.add(style);
			line1.add(Box.createHorizontalStrut(20));
			title.setText("宝宝眼屎多的原因有哪些？腾讯新闻网");
			title.setFont(Fonts.STYLE_TITLE);
			line1.add(title);
			line1.add(Box.createHorizontalGlue());
			
			JLabel time = new JLabel();
			time.setText("2016-01-01");
			time.setFont(Fonts.TIME);
			time.setForeground(Color.GRAY);
			line2.add(time);
			line2.add(Box.createHorizontalGlue());
			 
			String str = "因为SUN公司的目标客户群是企业客户，Java开发者也多是为企业开发定制软件的，用户体验在市场竞争中不太重要，没有动力去改善UI设计。比如你是做ERP或金融系统的公司，系统的可用和可靠，比好看的界面重要多了。况且客户用了你的系统后，在很长时间不会换另一家，提高用户体验不带来更高利润。如果像苹果、微软和互联网公司一样面向大众开发产品，用户体验就很重要了。用户可选的产品很多，不爽就可以用脚投票，随时换另一家，用户体验能带来更多的用户。这是激烈的市场竞争逼得，必须不断在UI上进行改善和创新，把顾客给“惯坏”了。但是好处是苹果和微软为开发者提供了用户体验很高的GUI组件，Swing相比之下就是渣啊。";
			JTextArea content = new JTextArea(str,4,5);
			content.setSelectedTextColor(Color.GRAY);
			content.setLineWrap(true);        //激活自动换行功能 
			content.setWrapStyleWord(true);            // 激活断行不断字功能
			content.setFont(Fonts.normal);
			content.setBackground(Color.blue);
			JLabel tmp = new JLabel();
			tmp.setText(str);
			tmp.setFont(Fonts.normal);
			int hei=tmp.getPreferredSize().height;
			tmp.setSize(Attributes.MAIN_FRAME_WIDTH, hei);
			line3.add(tmp);
			
			whole.add(line1);
			whole.add(line2);
			whole.add(line3);
			searchResult_Panel.add(whole);
			
			return searchResult_Panel;
		}
	}
	
	
	private class myTableCellEditor extends DefaultCellEditor
	{
		
		public myTableCellEditor(JCheckBox checkBox) 
		{
			super(checkBox);
			// TODO Auto-generated constructor stub
		}
		public JComponent getTableCellEditorComponent(JTable table, Object value,
	              boolean isSelected, final int row, int column)
		{
			JPanel searchResult_Panel = new JPanel();
			JLabel style = new JLabel();
			JLabel title = new JLabel();
			searchResult_Panel.setLayout(new GridLayout(3,1));
			Box whole =  Box.createVerticalBox();
			Box line1 = Box.createHorizontalBox();
			Box line2 = Box.createHorizontalBox();
			Box line3 = Box.createHorizontalBox();
			
			style.setText("媒体");//TODO
			style.setFont(Fonts.STYLE_TITLE);
			style.setForeground(Color.WHITE);
			style.setBackground(Color.BLACK);
			style.setOpaque(true);
			line1.add(style);
			line1.add(Box.createHorizontalStrut(20));
			title.setText("宝宝眼屎多的原因有哪些？腾讯新闻网");
			title.setFont(Fonts.STYLE_TITLE);
			line1.add(title);
			line1.add(Box.createHorizontalGlue());
			
			JLabel time = new JLabel();
			time.setText("2016-01-01");
			time.setFont(Fonts.TIME);
			time.setForeground(Color.GRAY);
			line2.add(time);
			line2.add(Box.createHorizontalGlue());
			 
			String str = "因为SUN公司的目标客户群是企业客户，Java开发者也多是为企业开发定制软件的，用户体验在市场竞争中不太重要，没有动力去改善UI设计。比如你是做ERP或金融系统的公司，系统的可用和可靠，比好看的界面重要多了。况且客户用了你的系统后，在很长时间不会换另一家，提高用户体验不带来更高利润。如果像苹果、微软和互联网公司一样面向大众开发产品，用户体验就很重要了。用户可选的产品很多，不爽就可以用脚投票，随时换另一家，用户体验能带来更多的用户。这是激烈的市场竞争逼得，必须不断在UI上进行改善和创新，把顾客给“惯坏”了。但是好处是苹果和微软为开发者提供了用户体验很高的GUI组件，Swing相比之下就是渣啊。";
			JTextArea content = new JTextArea(str,4,5);
			content.setSelectedTextColor(Color.GRAY);
			content.setLineWrap(true);        //激活自动换行功能 
			content.setWrapStyleWord(true);            // 激活断行不断字功能
			content.setFont(Fonts.normal);
			line3.add(content);
			
			whole.add(line1);
			whole.add(line2);
			whole.add(line3);
			searchResult_Panel.add(whole);
			
			return searchResult_Panel;
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
			          public void actionPerformed(ActionEvent e) 
			          {
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
		int resultSize=50;//TODO resultSize
		
		this.setLayout(new BorderLayout());
		searchResult_table=new JTable();
		MyTableModel model = new MyTableModel(resultSize,1);
		searchResult_table.setModel(model);
		searchResult_table.getColumnModel().getColumn(0).setPreferredWidth((int) (Attributes.MAIN_FRAME_WIDTH*0.905));
		searchResult_table.setEnabled(true);
		searchResult_table.getTableHeader().setVisible(false);
		searchResult_table.setDefaultRenderer(Object.class,new MyTableCellRenderer());
		//searchResult_table.setDefaultEditor(Object.class, new myTableCellEditor(new JCheckBox()));
		updateRowHeights();
		this.add(searchResult_table,BorderLayout.CENTER);
		
	}

}
