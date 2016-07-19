package reportfactory;
//""+"\n"+
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

public class HtmlFile {

	final int ICON = 0;
	final int WORDS = 1;
	final int NEWS = 1;
	final int GOV = 0;

	//FileWriter out;
	OutputStreamWriter out;
	BufferedWriter writer;
	String keyword;

	/**
	 * 
	 * @param file_path
	 *            文件路径+文件名
	 * @param key
	 *            关键词
	 */
	public HtmlFile(String file_path, String key) {
		this.keyword = key;
		File file = new File(file_path);
		try {
			//out = new FileWriter(file);
			out=new OutputStreamWriter(new FileOutputStream(file),"UTF8");
			writer = new BufferedWriter(out);
			writer.write(
			"<!DOCTYPE html>"+"\n"+
			"<html lang=\"zh-CN\">"+"\n"+
			"<head>"+"\n"+
			"<meta charset=\"utf-8\">"+"\n"+
			" <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"+"\n"+
			" <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"+"\n"+
			"<title>公车评估报告</title>"+"\n"+
			"<link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">"+"\n"+
			"<script src=\"http://libs.baidu.com/jquery/1.9.1/jquery.min.js\"></script>"+"\n"+
			"<script data-main = \"js/me.js\" src=\"js/require.js\"></script>"+"\n"	+	
			////write_cs_CZN
			
			//write_cs_own
			"\n"
			
					);
			
			
		
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 写统计信息（图）
	 */
	public void writeStatistics(String info, int type) {
		try {
			if (type == ICON)
				writer.write("<img src=\"" + info
						+ "\" width=\"500\" height=\"400\" /><br/>");
			else if (type == WORDS)
				writer.write("<br/><h1 align=\"left\">" + info
						+ "</h1><br/>"+"");
		} catch (Exception e) {
			System.out.println();
		}
	}
	public void writemOfList(String info) {
		try {
			
				writer.write(
			"<li><font face=\"微软雅黑\"size=\"6\">"
						+info+"</font></li>"+"\n"
						);
		} catch (Exception e) {
			System.out.println();
		}
	}
	
	
	/**
	 * 写政府公文信息
	 * 
	 * @param piece
	 *            放入报告中的文摘篇数
	 */
//	public void writeGov(List<Map<String, String>> infor, int piece) {
//		try {
//			if (piece > infor.size())
//				piece = infor.size();
//			writer.write("</div><h1><font face=\"微软雅黑\"size=\"5\">二、政府公文摘要：</font></h1>");
//			// writeSummery(summary, GOV);
//			writer.write("<p><font face=\"微软雅黑\"size=\"3\">以下为近几年相关政策：</font></p>");
//			for (int i = 0; i < piece; i++) {
//				writer.write("<p>");
//				writer.write("<font face=\"楷体\" size=\"3\">" + (i + 1)
//						+ "、&nbsp&nbsp" + infor.get(i).get("time").toString()
//						+ "&nbsp&nbsp</font>" + "<font size=\"3\">"
//						+ infor.get(i).get("type").toString() + "发布“<B>"
//						+ infor.get(i).get("title").toString()
//						+ "</B>”。</font>"
//						+ "<font size=\"3\"><br/>&nbsp&nbsp内容摘要："
//						+ infor.get(i).get("abstract").toString()
//						+ "</font></p><br/>");
//
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}

	public void writeinfor(String infor) {
		try {

			writer.write("<p><font face=\"微软雅黑\"size=\"6\">"+infor+"</font></p>"+"\n");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
	public void writeList() {
		try {

			writer.write("<ol>"+"\n");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	public void writeDev(String id) {
		try {
			writer.write("<div id=\""+id+"\" >");
		} catch (Exception e) {
			System.out.println("write dev_start_faile");
			System.out.println(e);
		}
	}
	public void writeEndDev() {
		try {
			writer.write("</div>"+"\n");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void writeEndList() {
		try {
			writer.write("</ol>"+"\n");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
	/**
	 * 写新闻信息
	 * 
	 * @param infor
	 * @param piece放入报告中的文摘数量
	 */
//	public void writeNew(List<Map<String, String>> infor, int piece) {
//		try {
//			if (piece > infor.size())
//				piece = infor.size();
//			writer.write("<h1><font face=\"微软雅黑\"size=\"5\">三、相关新闻报道：</font></h1>");
//			writeSummery(summary, NEWS);
//			writer.write("<p><font face=\"微软雅黑\"size=\"3\">以下为近几年有关报道：</font></p>");
//			for (int i = 0; i < piece; i++) {
//				writer.write("<p>");
//				writer.write("<font face=\"楷体\" size=\"3\">" + (i + 1)
//						+ "、&nbsp&nbsp" + infor.get(i).get("time").toString()
//						+ "&nbsp&nbsp</font>" + "<font size=\"3\">"
//						+ infor.get(i).get("author").toString() + "发布“<B>"
//						+ infor.get(i).get("title").toString()
//						+ "</B>”。</font>"
//						+ "<font size=\"3\"><br/>&nbsp&nbsp内容摘要："
//						+ infor.get(i).get("abstract").toString()
//						+ "</font></p><br/>");
//
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}

	/**
	 * All the information summary an article
	 * 
	 * @param infor
	 * @param type
	 *            0:gov file,1 newS file
	 */
//	public void writeSummery(String infor, int type) {
//		try {
//			if (type == GOV)
//				writer.write("<p>" + "<font size=\"3\">&nbsp&nbsp<B>总述：</B>"
//						+ infor.substring(1, infor.indexOf("以下是来自新闻的参考：") - 2)
//						+ "。</font></p><br/>");
//			else
//				writer.write("<p>"
//						+ "<font size=\"3\">&nbsp&nbsp<B>总述：</B>"
//						+ infor.substring(infor.indexOf("以下是来自新闻的参考：") + 13,
//								infor.length() - 1) + "。</font></p><br/>");
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}

	/**
	 * 写入完毕
	 */
	public void finish() {
		try {
			writer.write("</body></html>");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void write_first() {//用于设计页边距 和 标题
		
		try {
			writer.write("<style type=\"text/css\">"+"\n"+
					"body{height:50px;  padding:70px;}"+"\n"+
					"</style>"+"\n"+
					//标题部分
					"<body>"+"\n"+
					"<div class=\"container\">"+"\n"+
					"<div class=\"page-header\">"+"\n"+
					"<h1>基于网络舆情的公共政策评估报告         <small>###time###</small></h1>"+"\n"+
					"</div>"+"\n"+
					"</div>"+"\n"
					+"\n"
					);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void write_mainone() {
		try {
			writer.write(
			"<h1 align=\"left\">一、主体分析</h1>"+"\n"+
			"<div class=\"container\">"+"\n"+
			"<div class=\"row\">"+"\n"+
			"<div class=\"col-md-6\">"+"\n"+
			"<div id=\"main1\" style=\"width: 100%;height:600px;\"></div>"+"\n"+
			"</div>"+"\n"+
			"<div class=\"col-md-6\">"+"\n"+
			"<div id=\"main2\" style=\"width: 100%;height:600px;\"></div>"+"\n"+
			"</div>"+"\n"+
			"<div class=\"col-md-6\">"+"\n"+
			"<div id=\"main3\" style=\"width: 100%;height:600px;\"></div>"+"\n"+
			"</div>"+"\n"+
			"</div>"+"\n"+
			"</div>"+"\n"
					);
		} catch (Exception e) {
			
			System.out.println(e);
		}
		
		
	}

	public void writerline() { //<hr>
		try {
			writer.write("<hr />"+"\n");
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

	public void writeLast() {
		try {
			writer.write(
			"</body>"+"\n"+
			"<script type=\"text/javascript\">"+"\n"+		
			"var pipData = ###pipData###;"+"\n"+
			" var yearData = ###yearData###;"+"\n"+
			"var motionData = ###motionData###;"+"\n"+
			"</script>"+"\n"+
			"</html>"+"\n"	
					);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
	}


}
