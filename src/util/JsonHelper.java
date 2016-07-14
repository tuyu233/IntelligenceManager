package util;



import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;



/**
 * 由Object生成json字符串
 */
public class JsonHelper {
    /**
     * 将obj进行json编码
     * @param obj
     * @return jsonStr
     */
    public static String jsonEncode(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true) ;
        //mapper.configure( SerializationConfig.Feature.AUTO_DETECT_GETTERS,false);

//        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
//        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
//        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
//        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
//        mapper.configure(JsonParser.Feature.INTERN_FIELD_NAMES, true);
//        mapper.configure(JsonParser.Feature.CANONICALIZE_FIELD_NAMES, true);
//        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String s = null;
        JsonGenerator jsonGenerator = null;
        try{
            s = mapper.writeValueAsString(obj);
//            s = s.replaceAll("\\\"","\"");
//            System.out.println(s);
        }
        catch (Exception e){
            s = "";
            String errMessage = "can not get json encode : "+e.getMessage();
            System.out.println(errMessage);
        }
        return s;
    }

    public static void main(String args[]) {
		HashMap<Integer,String> map = new HashMap<>();
		map.put(1,"bbb");
		System.out.println(JsonHelper.jsonEncode(map));
	}
}
