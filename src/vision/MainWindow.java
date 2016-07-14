package vision;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.*;

import properties.Attributes;
import properties.Fonts;
import service.chart.tagcloud.Configuration;
import service.chart.tagcloud.TagCloud;
import service.chart.tagcloud.TagCloudGenerator;
import service.chart.tagcloud.TagCloudHelper;


public class MainWindow  
{
	static JFrame mainFrame;
    private static void setLookAndFeel() {
        try {
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        } catch (Exception e) {
            e.printStackTrace();}
            // handle exception
        } 
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
		
		JPanel searchBar_panel=new JPanel();
		search_panel.add(searchBar_panel,BorderLayout.EAST);
		searchBar_panel.setLayout(new BoxLayout(searchBar_panel,BoxLayout.X_AXIS));
		
		JTextField searchBar_textField=new JTextField();
		searchBar_panel.add(searchBar_textField);
		searchBar_textField.setFont(Fonts.searchBar);
		searchBar_textField.setToolTipText(Attributes.TOOLTIP);
		searchBar_textField.setColumns(30);
		
		JPanel button_panel=new JPanel();
		button_panel.setLayout(new GridLayout(1,2));
		searchBar_panel.add(button_panel);
		
		JButton search_button=new JButton(Attributes.SEARCHBUTTON);
		button_panel.add(search_button);
		JButton result_button=new JButton(Attributes.RESULTBUTTON);
		button_panel.add(result_button);
		search_button.setFont(Fonts.normal);
		result_button.setFont(Fonts.normal);
		
		//内容panel
		JPanel content_panel=new JPanel();
		content_panel.setLayout(new BorderLayout());
		frame_panel.add(content_panel,BorderLayout.CENTER);
		
		JTabbedPane tab_pane=new JTabbedPane(JTabbedPane.TOP);
		//tab_pane.setFont(Fonts.normal);
		content_panel.add(tab_pane,BorderLayout.CENTER);
		
		//三个tab(搜索结果)
		SearchResult tab_panel1=new SearchResult();
		JScrollPane tab1 = new JScrollPane(tab_panel1);
		tab_pane.addTab(Attributes.SEARCHRESULTTAB, null,tab1,null);
		
		//结果统计
		ResultStatistic tab_panel2 = new ResultStatistic();
		JScrollPane tab2 = new JScrollPane(tab_panel2);
		tab_pane.addTab(Attributes.RESULTSTATISTIC, null,tab2,null);
		
		//全体数据
		AllData tab_panel3 = new AllData();
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

}
