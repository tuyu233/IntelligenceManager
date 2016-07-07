package spider.helper;

import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.swing.*;

public class test extends JFrame implements ActionListener {
	JButton button1 = new JButton("stop");
	JButton button0 = new JButton("start");
	String key = "数控机床";
	boolean[] option = { true, false, false, false, false, false };
	Crawler crawler = new Crawler(key, option);

	public test() throws HeadlessException {
		setSize(225, 80);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FlowLayout flowLayout = new FlowLayout();
		setLayout(flowLayout);
		add(button0);
		add(button1);
		setVisible(true);
		button1.addActionListener(this);
		button0.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if (object == button1) {
			crawler.stop();
			System.out.println("stop now!");
		}
		if (object == button0) {
			crawler.start();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new test();
		 String urlStr=null;
			try {
				urlStr = URLEncoder.encode("信息系统", "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 System.out.println(urlStr);

	}

}
