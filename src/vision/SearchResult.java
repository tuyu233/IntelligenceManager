//搜索结果
package vision;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.AbstractDocument.Content;

import properties.*;
import service.keyword.NLP;
import entity.*; 

public class SearchResult extends JPanel 
{
	private List<Record> resultList; 
	private JTable searchResultTable;
	
	public void setResult(List<Record> resultList)
	{
		this.resultList=resultList;
		
		int resultSize=resultList.size();//TODO resultSize
		
		this.setLayout(new BorderLayout());
		searchResultTable=new JTable();
		MyTableModel model = new MyTableModel(resultSize,2);
		MyTableCellRenderer renderer = new MyTableCellRenderer();
		myTableCellEditor editor = new myTableCellEditor(new JCheckBox());
		searchResultTable.setModel(model);
		searchResultTable.getColumnModel().getColumn(0).setPreferredWidth((int) (Attributes.MAIN_FRAME_WIDTH*0.89));
		searchResultTable.setEnabled(true);
		searchResultTable.getTableHeader().setVisible(false);
		searchResultTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
		searchResultTable.getColumnModel().getColumn(1).setCellRenderer(new MyButtonRenderer());
		searchResultTable.getColumnModel().getColumn(0).setCellEditor(editor);
		searchResultTable.getColumnModel().getColumn(1).setCellEditor(new myButtonEditor(new JCheckBox()));
		updateRowHeights();
		this.add(searchResultTable,BorderLayout.CENTER);
		
		this.validate();
		this.repaint();
	}
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
			tmp_button.setBounds(0,10,100,30);
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
			searchResult_Panel.setLayout(new BorderLayout());
			Box whole =  Box.createVerticalBox();
			Box line1 = Box.createHorizontalBox();
			Box line2 = Box.createHorizontalBox();
			Box line3 = Box.createHorizontalBox();
			
			style.setText(resultList.get(row).getType());//TODO
			style.setFont(Fonts.STYLE_TITLE);
			style.setForeground(Color.WHITE);
			style.setBackground(Color.BLACK);
			style.setOpaque(true);
			line1.add(style);
			line1.add(Box.createHorizontalStrut(20));
			title.setText(resultList.get(row).getTitle());
			title.setFont(Fonts.STYLE_TITLE);
			line1.add(title);
			line1.add(Box.createHorizontalGlue());
			
			JLabel time = new JLabel();
			time.setText(resultList.get(row).getSaveTime().toString().substring(0, 10));
			time.setFont(Fonts.TIME);
			time.setForeground(Color.GRAY);
			line2.add(time);
			JLabel url = new JLabel();
			String baseUrl = resultList.get(row).getBaseUrl();
			url.setText("<html>"+baseUrl+"<html>" + " 双击打开原网页");
			url.setFont(Fonts.normal);
			url.setForeground(Colors.URL_COLOR);
			
			line2.add(Box.createHorizontalStrut(20));
			line2.add(url);
			line2.add(Box.createHorizontalGlue());
			 
			String str =NLP.stringSummary(resultList.get(row).getContent());//摘要内容
			JTextArea content = new JTextArea(str,4,5);
			content.setSelectedTextColor(Color.GRAY);
			content.setLineWrap(true);        //激活自动换行功能 
			content.setWrapStyleWord(true);            // 激活断行不断字功能
			content.setFont(Fonts.normal);
			line3.add(content);
			
			whole.add(line1);
			whole.add(line2);
			whole.add(line3);
			searchResult_Panel.add(whole,BorderLayout.CENTER);
			
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
			searchResult_Panel.setLayout(new BorderLayout());
			Box whole =  Box.createVerticalBox();
			Box line1 = Box.createHorizontalBox();
			Box line2 = Box.createHorizontalBox();
			Box line3 = Box.createHorizontalBox();
			
			style.setText(resultList.get(row).getType());//TODO
			style.setFont(Fonts.STYLE_TITLE);
			style.setForeground(Color.WHITE);
			style.setBackground(Color.BLACK);
			style.setOpaque(true);
			line1.add(style);
			line1.add(Box.createHorizontalStrut(20));
			title.setText(resultList.get(row).getTitle());
			title.setFont(Fonts.STYLE_TITLE);
			line1.add(title);
			line1.add(Box.createHorizontalGlue());
			
			JLabel time = new JLabel();
			time.setText(resultList.get(row).getSaveTime().toString().substring(0, 10));
			time.setFont(Fonts.TIME);
			time.setForeground(Color.GRAY);
			line2.add(time);
			JLabel url = new JLabel();
			String baseUrl = resultList.get(row).getBaseUrl();
			url.setText("<html>"+baseUrl+"<html>" + " 双击打开原网页");
			url.setFont(Fonts.normal);
			url.setForeground(Colors.URL_COLOR);		
			try {
				url.addMouseListener(new MouseAdapter()
				{
					String baseUrl = resultList.get(row).getBaseUrl();
					URL link = new URL(baseUrl);
					public void mouseClicked(MouseEvent e) 
					{
						try
						{
							Desktop.getDesktop().browse(link.toURI());	
						}catch(IOException err)
						{
							err.printStackTrace();
						}catch(URISyntaxException err)
						{
							err.printStackTrace();
						}
					}
					
				});
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			line2.add(Box.createHorizontalStrut(20));
			line2.add(url);
			line2.add(Box.createHorizontalGlue());
			 
			String str =NLP.stringSummary(resultList.get(row).getContent());//摘要内容
			JTextArea content = new JTextArea(str,4,5);
			content.setSelectedTextColor(Color.GRAY);
			content.setLineWrap(true);        //激活自动换行功能 
			content.setWrapStyleWord(true);            // 激活断行不断字功能
			content.setFont(Fonts.normal);
			line3.add(content);
			
			whole.add(line1);
			whole.add(line2);
			whole.add(line3);
			searchResult_Panel.add(whole,BorderLayout.CENTER);
			
			return searchResult_Panel;
		}
		
	}
	//MyButtonEditor
	private class myButtonEditor extends DefaultCellEditor
	{

	      public myButtonEditor(JCheckBox checkBox)
	      {
	        super(checkBox);
	      }
	      public JComponent getTableCellEditorComponent(JTable table, Object value,
	              boolean isSelected, final int row, int column) 
	      {
	  		JPanel tmp_panel=new JPanel();
			JButton tmp_button=new JButton(Attributes.WHOLEPASSAGE);
	        tmp_button.setOpaque(true);
	    	tmp_button.setFont(Fonts.searchButton);
	    	tmp_button.addActionListener
		        (new ActionListener() 
		        {
			          public void actionPerformed(ActionEvent e) 
			          {
			        	  JFrame f=new JFrame();
			        	  f.setVisible(true);
			        	  f.setBounds(500, 120, 600, 500);
			        	  f.setTitle(resultList.get(row).getType()+" - "+resultList.get(row).getTitle());
			        	  f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			        	  JTextArea wholePage = new JTextArea(resultList.get(row).getContent());
			        	  wholePage.setLineWrap(true);
			        	  wholePage.setWrapStyleWord(true);
			        	  wholePage.setFont(Fonts.normal);
			        	  JScrollPane scroll = new JScrollPane(wholePage);
			        	  f.add(scroll);
			        	 
			          }
		        });
	    	  
				tmp_panel.setLayout(null);
		    	tmp_button.setBounds(0,10,100,30);
				tmp_panel.add(tmp_button);
				return tmp_panel;
	      }
		
	}
	private void updateRowHeights() 
	{
		for (int row = 0; row < searchResultTable.getRowCount(); row++) {
			int rowHeight =searchResultTable.getRowHeight();

			for (int column = 0; column < searchResultTable.getColumnCount(); column++) {
				Component comp = searchResultTable.prepareRenderer(
						searchResultTable.getCellRenderer(row, column), row,
						column);
				rowHeight = Math.max(rowHeight,
						comp.getPreferredSize().height);
			}

			searchResultTable.setRowHeight(row, rowHeight);
		}
	}
	
	public SearchResult() 
	{	
	}

}
