package vision;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import properties.Attributes;
import properties.Fonts;
import service.chart.tagcloud.Configuration;
import service.chart.tagcloud.TagCloudGenerator;
import service.chart.tagcloud.TagCloudHelper;

public class ResultStatistic extends JPanel
{
	public void setResult(float[] index, List<List<String>> keywords)
	{
		this.index=index;
		this.keywords=keywords;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		module1Setup();
		module2Setup();
		module3Setup();	
		this.invalidate();
		this.repaint();
	}
	private float[] index;
	private List<List<String>> keywords;
	private JLabel[] note = new JLabel[9];
	private JLabel[] label = new JLabel[9];
	private JPanel[] panel = new JPanel[9];
	private JLabel[] imagelabel = new JLabel[4];
	
	//模块1
	private JPanel module1 = new JPanel();
	private GridBagLayout gbl1 = new GridBagLayout();;
	private GridBagConstraints gbc1 = new GridBagConstraints();

	private void addComponent(Component a)
	{
		gbl1.setConstraints(a, gbc1);
		module1.add(a);
	}
	
	private void module1Setup()
	{
		this.add(module1);
		module1.setLayout(gbl1);
		module1.setBorder(new EtchedBorder());
		gbc1.fill = GridBagConstraints.BOTH;
		gbc1.weightx = 1;
		gbc1.weighty = 1;
		
		//第一行
		gbc1.gridwidth = GridBagConstraints.REMAINDER;
		addComponent(note[0]);
		note[0].setText(Attributes.INDEXNOTE);
		note[0].setFont(Fonts.NOTETILE);
		note[0].setBorder(new EtchedBorder());
		
		//第二行
		gbc1.gridwidth = GridBagConstraints.REMAINDER;
		addComponent(note[1]);
		note[1].setText(Attributes.SINDEXNOTE);
		note[1].setFont(Fonts.normal);
		
		//第三行
		gbc1.gridwidth=1;
		addComponent(panel[2]);
		addComponent(label[0]);
		label[0].setText(Attributes.WHOLEWEB);
		gbc1.gridwidth=10;
		addComponent(label[1]);
		label[1].setText(Float.toString(index[0]));//全网评分
		
		gbc1.gridwidth=1;
		addComponent(panel[3]);
		addComponent(label[2]);
		label[2].setText(Attributes.GOVERNMENT);
		gbc1.gridwidth=10;
		addComponent(label[3]);
		label[3].setText(Float.toString(index[1]));//政府评分
		
		gbc1.gridwidth=1;
		addComponent(panel[4]);
		addComponent(label[4]);
		label[4].setText(Attributes.MEDIA);
		gbc1.gridwidth=10;
		addComponent(label[5]);
		label[5].setText(Float.toString(index[2]));//媒体评分
		
		gbc1.gridwidth=1;
		addComponent(panel[5]);
		addComponent(label[6]);
		label[6].setText(Attributes.PUBLIC);
		gbc1.gridwidth=10;
		addComponent(label[7]);
		label[7].setText(Float.toString(index[3]));//公众评分
		
		gbc1.weightx=1;
		gbc1.gridwidth = GridBagConstraints.REMAINDER;
		addComponent(panel[6]);
		gbc1.gridheight=30;
		addComponent(panel[0]);
	}
	
	
	
//模块2
	private JPanel module2 = new JPanel();
	private GridBagLayout gbl2 = new GridBagLayout();;
	private GridBagConstraints gbc2 = new GridBagConstraints();

	private void addComponent2(Component a)
	{
		gbl2.setConstraints(a, gbc2);
		module2.add(a);
	}

	private void tagcloud()
	{
		//TODO
		String str=Util.tagCloudTrans(keywords.get(0));//全部的关键词
				//"神话中,25\n孩子,25\n柠檬水,25\n压缩,25\n美国,25\n柠檬,25\n就出,25\n黑暗中,25\n大脑,25\n小狗,24白色,18\n小部件,18\n企鹅,18\n薄荷,18\n低,18\n屋顶,17\n合资企业,17\n最爱,17\n鼻子,17\n太阳,17\n客户,17\n狗,17\n海洋,16\n处理,16\n粉色,16\n发现,15\n风险,15";
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
		tagCloud.frame=MainWindow.mainFrame;
		tagCloud.init();
		panel[7].add(tagCloud,BorderLayout.CENTER);
		tagCloud.stop();
	}
	
	private void module2Setup()
	{
		this.add(module2);
		module2.setLayout(gbl2);
		module2.setBorder(new EtchedBorder());
		module2.setOpaque(true);
		gbc2.fill = GridBagConstraints.BOTH;
		gbc2.weightx = 1;
		gbc2.weighty = 1;
		
		//第四行,关键词
		gbc2.gridwidth = GridBagConstraints.REMAINDER;
		addComponent2(note[2]);
		note[2].setText(Attributes.KEYWORD);
		note[2].setFont(Fonts.NOTETILE);
		note[2].setBorder(new EtchedBorder());
		
		//最左边的panel
		gbc2.weightx=0.13;
		gbc2.gridheight=4;
		gbc2.gridwidth=8;
		addComponent2(panel[1]);
		
		//第五行
		gbc2.gridheight=1;		
		gbc2.gridwidth=8;
		addComponent2(label[8]);
		label[8].setBackground(Color.WHITE);
		label[8].setOpaque(true);
		label[8].setText(Attributes.WHOLEWEB);
		gbc2.gridwidth = GridBagConstraints.REMAINDER;
		addComponent2(panel[0]);
		
		//第六行
		gbc2.gridheight=3;
		gbc2.gridwidth=8;
		addComponent2(panel[7]);
		panel[7].setLayout(new BorderLayout());	
		tagcloud();
		
		//右三行
		gbc2.gridheight=3;
		gbc2.gridwidth=1;
		addComponent2(panel[8]);
		
		//政府
		gbc2.weightx=0;
		gbc2.gridheight=1;
		gbc2.gridwidth=1;
		addComponent2(note[3]);
		note[3].setText(Attributes.GOVERNMENT+" ");
		note[3].setFont(Fonts.opinion_title);
		
		//政府关键词
		gbc2.weightx=1;
		gbc2.gridwidth = GridBagConstraints.REMAINDER;
		addComponent2(note[4]);
		note[4].setText(Util.transFormat(keywords.get(1)));//政府关键词
		note[4].setFont(Fonts.KEYWORD);
		
		//媒体
		gbc2.weightx=0;
		gbc2.gridwidth=1;
		addComponent2(note[5]);
		note[5].setText(Attributes.MEDIA+" ");
		note[5].setFont(Fonts.opinion_title);
		
		//媒体关键词
		gbc2.weightx=1;
		gbc2.gridwidth = GridBagConstraints.REMAINDER;
		addComponent2(note[6]);
		note[6].setText(Util.transFormat(keywords.get(2)));//媒体关键词
		note[6].setFont(Fonts.KEYWORD);
		
		//公众
		gbc2.weightx=0;
		gbc2.gridwidth=1;
		addComponent2(note[7]);
		note[7].setText(Attributes.PUBLIC+" ");
		note[7].setFont(Fonts.opinion_title);
		
		//公众关键词
		gbc2.weightx=1;
		gbc2.gridwidth = GridBagConstraints.REMAINDER;
		addComponent2(note[8]);
		note[8].setText(Util.transFormat(keywords.get(3)));//公众关键词
		note[8].setFont(Fonts.KEYWORD);
		
		//第六行
		gbc2.gridheight=1;
	}
	
	//模块三
	private JPanel module3 = new JPanel();
	private GridBagLayout gbl3 = new GridBagLayout();;
	private GridBagConstraints gbc3 = new GridBagConstraints();

	private void addComponent3(Component a)
	{
		gbl3.setConstraints(a, gbc3);
		module3.add(a);
	}
	
	private void module3Setup()
	{
		this.add(module3);
		module3.setLayout(gbl3);
		module3.setBorder(new EtchedBorder());
		gbc3.fill = GridBagConstraints.BOTH;
		gbc3.weightx = 1;
		gbc3.weighty = 1;
		
		//第一行
		gbc3.gridwidth = GridBagConstraints.REMAINDER;
		addComponent3(imagelabel[0]);
		imagelabel[0].setText(Attributes.CHART);
		imagelabel[0].setFont(Fonts.NOTETILE);
		imagelabel[0].setBorder(new EtchedBorder());
		
		//第二行
		gbc3.gridwidth=1;
		
		addComponent3(imagelabel[1]);
		ImageIcon image1 = new ImageIcon("./output/year_comments.jpg");
		image1.setImage(image1.getImage().getScaledInstance(445, 370, Image.SCALE_DEFAULT));
		imagelabel[1].setIcon(image1);
		
		addComponent3(imagelabel[2]);
		ImageIcon image2 = new ImageIcon("./output/source.jpg");
		image2.setImage(image2.getImage().getScaledInstance(445, 370, Image.SCALE_DEFAULT));
		imagelabel[2].setIcon(image2);
		
		addComponent3(imagelabel[3]);
		ImageIcon image3 = new ImageIcon("./output/motion.jpg");
		image3.setImage(image3.getImage().getScaledInstance(445, 370, Image.SCALE_DEFAULT));
		imagelabel[3].setIcon(image3);
		
	}
	public ResultStatistic() 
	{
		for(int i = 0;i < 9;i++)
		{
			if(i<4)
				imagelabel[i] = new JLabel();
			label[i] = new JLabel();
			panel[i] = new JPanel();
			note[i] = new JLabel();
			if(i==1||i==3||i==5||i==7)
				label[i].setFont(Fonts.opinion_index);
			else
				label[i].setFont(Fonts.opinion_title);
		}
			
		
	}

}
