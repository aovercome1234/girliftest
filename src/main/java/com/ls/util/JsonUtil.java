package com.ls.util;

import com.jayway.jsonpath.JsonPath;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JsonUtil {
	static String jsonData = "";
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
	public static String MapToJson(Map<String, Object> obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonData = mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonData;
	}

	public static String mapToJsonStr(Map<String, ?> obj1){
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonData = mapper.writeValueAsString(obj1);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonData;
	}

	public static JSONObject JsonToJsonObject(String json){
		JSONObject jObject = new JSONObject(json);
		return jObject;
	}
	
	public static String resultSetToJson(ResultSet rs){
        // 
        JSONArray array = new JSONArray();

        //
        ResultSetMetaData metaData = null;
		try {
			metaData = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        int columnCount = 0;
		try {
			columnCount = metaData.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        //
        try {
			while (rs.next()) {
			    JSONObject jsonObj = new JSONObject();

			    // 
			    for (int i = 1; i <= columnCount; i++) {
			        String columnName = metaData.getColumnLabel(i);
			        String value = rs.getString(columnName);
			        jsonObj.put(columnName, value);
			    }
			    array.put(jsonObj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return array.toString();
    }
	
	/**
	 * 利用jsonPath解析返回值
	 * @param jsonpath: 类似xpath，传的是结构
	 * @param responseContent，接口请求的返回值
	 * @return
	 */
	public static Object getValByJsonPath(String jsonpath, String responseContent) {
		Object value = null;
		try {
			value = (Object) JsonPath.read(responseContent, jsonpath);
//			logger.info("getValByJsonPath={}", value);
		} catch (Exception e) {
			value=null;
		}
		return value;
	}
	
	public static int getIntValByJsonPath(String jsonpath, String responseContent){
		return Integer.parseInt(getValByJsonPath(jsonpath, responseContent)+"");
	}
	
	public static boolean getBoolValByJsonPath(String jsonpath, String responseContent){
		return Boolean.parseBoolean(getValByJsonPath(jsonpath, responseContent)+"");
	}
	
	public static double getDoubleValByJsonPath(String jsonpath, String responseContent){
		return Double.parseDouble(getValByJsonPath(jsonpath, responseContent)+"");
	}
	
	public static String getStringValByJsonPath(String jsonpath, String responseContent){
		return getValByJsonPath(jsonpath, responseContent)+"";
	}

	public static String ListMapToJson(List<Map<String, Object>> mapList) {
		ObjectMapper mapper = new ObjectMapper();
		String[] strings = new String[mapList.size()];
		try {
			for(int i=0; i<mapList.size();i++){
				strings[i] = mapper.writeValueAsString(mapList.get(i));
			}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Arrays.toString(strings);
	}

}
