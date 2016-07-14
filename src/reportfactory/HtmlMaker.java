package reportfactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import util.JsonHelper;


public class HtmlMaker {

	public static void make(Map<String,Object> map){
		try {
			// 模板路径
			String filePath = "output/template.html";
			System.out.print(filePath);
			String templateContent = "";
			FileInputStream fileinputstream = new FileInputStream(filePath);// 读取模板文件
			int lenght = fileinputstream.available();
			byte bytes[] = new byte[lenght];
			fileinputstream.read(bytes);
			fileinputstream.close();
			templateContent = new String(bytes);
			System.out.print(templateContent);
			Set<Entry<String,Object>> set = map.entrySet();
			for(Entry<String,Object> entry : set){
				if(entry.getValue() instanceof String){
					templateContent = templateContent.replaceAll("###"+entry.getKey()+"###",(String) entry.getValue());
				}
				else {
					templateContent = templateContent.replaceAll("###"+entry.getKey()+"###",JsonHelper.jsonEncode(entry.getValue()));
				}
			}
			System.out.print(templateContent);

			// 根据时间得文件名
			String fileame = "output/output.html";
			FileOutputStream fileoutputstream = new FileOutputStream(fileame);// 建立文件输出流
			System.out.print("文件输出路径:");
			System.out.print(fileame);
			byte tag_bytes[] = templateContent.getBytes();
			fileoutputstream.write(tag_bytes);
			fileoutputstream.close();
		} 
		catch (Exception e) {
			System.out.print(e.toString());
		}

	}
	
	
	
	
	
	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<>();
		map.put("pipData",ChartData.getTestPipData());
		map.put("yearData", ChartData.getTestYearData());
		map.put("motionData",ChartData.getTestMotionData());
		Date date = new Date(System.currentTimeMillis());
		String time = String.format("%tF%n", date);
		//System.out.println(time);
		map.put("time", time);
		make(map);
		
		
		
	}

}
