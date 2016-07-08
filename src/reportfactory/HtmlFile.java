package reportfactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
			writer.write("<!DOCTYPE html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" > <title>有关“"
					+ keyword
					+ "”的调查统计</title></head><body>"
					+ "<h1 align=\"center\">有关“"
					+ keyword
					+ "”的统计结果报告</h1>");
					//+ "<h1><font face=\"微软雅黑\"size=\"5\">一、信息统计结果：</font></h1><div align=\"center\">");
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
						+ "\" width=\"500\" height=\"400\" />");
			else if (type == WORDS)
				writer.write("<br/><h3 align=\"left\"><font size=\"4\">" + info
						+ "</font><h3><br/>");
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

			writer.write("<p><font face=\"微软雅黑\"size=\"3\">"+infor+"</font></p>");
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
}
