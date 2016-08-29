package vision;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.*;

import org.apache.commons.collections.functors.WhileClosure;

import properties.Attributes;
import properties.Fonts;
import service.Controller;
import service.DataManager;
import service.chart.tagcloud.Configuration;
import service.chart.tagcloud.TagCloud;
import service.chart.tagcloud.TagCloudGenerator;
import service.chart.tagcloud.TagCloudHelper;
import spider.monitor.MonitorManager;


public class MainWindow  
{
	static JFrame mainFrame;
	SearchResult tab_panel1;
	ResultStatistic tab_panel2;
	AllData tab_panel3;
	JLabel searchStatus;
	JTextField searchBar_textField;
	JButton search_button;
	JButton result_button;
	JButton makeReport_button;
	
	public MainWindow()
	{
		setLookAndFeel();
		//主frame的基础设置 	
		mainFrame=new JFrame();
		mainFrame.setTitle(Attributes.TITLENAME);
		mainFrame.setBackground(Color.WHITE);
		mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//标题部分panel
		JPanel frame_panel=(JPanel) mainFrame.getContentPane();
		frame_panel.setLayout(new BorderLayout());
		
		JPanel north_panel=new JPanel();
		frame_panel.add(north_panel,BorderLayout.NORTH);
		north_panel.setLayout(new BoxLayout(north_panel,BoxLayout.Y_AXIS));
		
		//标题panel
		JPanel title_panel=new JPanel();
		north_panel.add(title_panel);
		title_panel.setLayout(new BorderLayout());
		
		JLabel title_label=new JLabel(Attributes.TITLENAME,JLabel.CENTER);
		title_panel.add(title_label,BorderLayout.CENTER);
		title_label.setFont(Fonts.title);
		
		//搜索panel
		JPanel search_panel=new JPanel();
		north_panel.add(search_panel);
		search_panel.setLayout(new BorderLayout());
		
		makeReport_button = new JButton();
		makeReport_button.setText(Attributes.REPORTBUTTON);
		makeReport_button.setFont(Fonts.searchButton);
		makeReport_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){
				makeReport_button.setText(Attributes.REPORTBUTTON_PROCESSING);
				makeReport_button.invalidate();
				makeReport_button.repaint();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						Controller.makeReport();

						makeReport_button.setText(Attributes.REPORTBUTTON);
						makeReport_button.invalidate();
						makeReport_button.repaint();
					}
				}).start();
			}
		});
		search_panel.add(makeReport_button, BorderLayout.WEST);
		
		JPanel searchBar_panel=new JPanel();
		search_panel.add(searchBar_panel,BorderLayout.EAST);
		searchBar_panel.setLayout(new BoxLayout(searchBar_panel,BoxLayout.X_AXIS));
		
		searchStatus = new JLabel();
		searchStatus.setFont(Fonts.searchBar);
		searchBar_panel.add(searchStatus);
		searchStatus.setVisible(false);
		
		searchBar_textField=new JTextField("公车改革");
		searchBar_panel.add(searchBar_textField);
		searchBar_textField.setFont(Fonts.searchBar);
		searchBar_textField.setToolTipText(Attributes.TOOLTIP);
		searchBar_textField.setColumns(30);
		
		JPanel button_panel=new JPanel();
		button_panel.setLayout(new GridLayout(1,2));
		searchBar_panel.add(button_panel);
		
		search_button=new JButton();
		search_button.setText(Attributes.SEARCHBUTTON);
		search_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				if(Controller.isrunning){
					stopSearch();
				}
				else{
					if(getInput()!=null){
						search_button.setText(Attributes.STOPBUTTON);
						searchBar_textField.setVisible(false);
						searchStatus.setVisible(true);
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								Controller.isrunning = true;
								Controller.startCrawl(getInput());
							}
						}).start();
						
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								MonitorManager monitor = MonitorManager.getInstance();
								do{
									if(monitor.getStatus().equals(Attributes.SPIDER_DONE)){
										stopSearch();
										break;
									}
									searchStatus.setText(monitor.getStatus());
									try {
										Thread.sleep(500);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}while(Controller.isrunning);
								JOptionPane.showMessageDialog(
										null,
										"搜索结束！\n共扫描了"+String.valueOf(monitor.getTotal())+"个页面"
												+ "\n成功下载"+String.valueOf(DataManager.getCountPipeline())
												+ "页",
										"搜索结束",
										JOptionPane.WARNING_MESSAGE);

							}
						}).start();
					}
				}

				search_button.invalidate();
				search_button.repaint();
			}
		});
			
		button_panel.add(search_button);
		result_button=new JButton(Attributes.RESULTBUTTON);
		button_panel.add(result_button);
		search_button.setFont(Fonts.normal);
		result_button.setFont(Fonts.normal);
		
		result_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				if(getInput()!=null){
					result_button.setText(Attributes.RESULTBUTTON_PROCESSING);
					result_button.invalidate();
					result_button.repaint();
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Controller.showResult(getInput(), tab_panel1, tab_panel2, tab_panel3);

							result_button.setText(Attributes.RESULTBUTTON);
							result_button.invalidate();
							result_button.repaint();
						}
					}).start();
				}
			}
		});
		
		//内容panel
		JPanel content_panel=new JPanel();
		content_panel.setLayout(new BorderLayout());
		frame_panel.add(content_panel,BorderLayout.CENTER);
		
		JTabbedPane tab_pane=new JTabbedPane(JTabbedPane.TOP);
		//tab_pane.setFont(Fonts.normal);
		content_panel.add(tab_pane,BorderLayout.CENTER);
		
		//三个tab(搜索结果)
		tab_panel1=new SearchResult();
		tab_pane.addTab(Attributes.SEARCHRESULTTAB, null,tab_panel1,null);
		
		//结果统计
		tab_panel2 = new ResultStatistic();
		JScrollPane tab2 = new JScrollPane(tab_panel2);
		tab_pane.addTab(Attributes.RESULTSTATISTIC, null,tab2,null);
		
		//全体数据
		tab_panel3 = new AllData();
		JScrollPane tab3 = new JScrollPane(tab_panel3);
		tab_pane.addTab(Attributes.ALLDATA, null,tab3,null);
		
		
		//TODO
		/*String str="神话中,25\n孩子,25\n柠檬水,25\n压缩,25\n美国,25\n柠檬,25\n就出,25\n黑暗中,25\n大脑,25\n小狗,24";
		TagCloudHelper.getInstance().makeTagcloud(str,"./output/tagcloud.png");
		try
		{
            Configuration.getInstance().load(new FileInputStream("./resource/config.properties"));
        } catch (IOException e) 
        {
            e.printStackTrace();
            return;
        }
		TagCloudGenerator tagCloud;
		tagCloud = new TagCloudGenerator();
		tagCloud.frame=mainFrame;
		tagCloud.init();
		tab_panel2.add(tagCloud,BorderLayout.CENTER);
		tagCloud.stop();
		*/
	}
	
	//拿到输入框里的内容
	public String getInput(){
		return searchBar_textField.getText();
	}
	
	private void stopSearch(){
		search_button.setText(Attributes.SEARCHBUTTON);
		Controller.stopCrawl();
		searchBar_textField.setVisible(true);
		searchStatus.setVisible(false);
	}
	
    private static void setLookAndFeel() {
        try {
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        } catch (Exception e) {
            e.printStackTrace();}
            // handle exception
        } 

}
