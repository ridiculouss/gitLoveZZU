package zzu.util;

import java.util.List;

import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
@Component(value="GetjsonArray")
public class Getjson {

	//list תjsonarray  ���ҹ��˼�������
	public <T> JSONObject getjsonarray(List<T> list,String action){
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.setExcludes(new String[]{"user","setcomments_L1","setcomments_L2","cgoods","setorderitems","comments_l1","settopic","settopiccomment"});
		
	    JSONArray values = JSONArray.fromObject(list,config);  
	    JSONObject json=new JSONObject();
	    json.put("result", action);
	    json.put("values", values);
	      System.out.println(values.toString());
		
		
		return json;
		} 
	//json������˼�������
	public static <T> JSONObject getjsonobject(T j){
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.setExcludes(new String[]{"user","setcomments_L1","cgoods","setorderitems","order"});
		
		JSONObject json=JSONObject.fromObject(j, config);
		return json;
		};
	
		//����Ȧlist תjsonarray  ���ҹ��˼�������
		public <T> JSONObject Topicgetjsonarray(List<T> list,String action){
			JsonConfig config = new JsonConfig();
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			config.setExcludes(new String[]{"topic","user","theme","setcomments_L1","setcomments_L2","setorderitems","settopic","setgoods","setorder","settopiccomment"});
			
		    JSONArray values = JSONArray.fromObject(list,config);  
		    JSONObject json=new JSONObject();
		    json.put("result", action);
		    json.put("values", values);
		      System.out.println(values.toString());
			
			
			return json;
			} 
		//����Ȧlist תjsonarray  ���ҹ��˼�������
		public <T> JSONObject Topicgetjsonarray3(List<T> list,String action){
			JsonConfig config = new JsonConfig();
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			config.setExcludes(new String[]{"user","theme","setcomments_L1","setcomments_L2","setorderitems","settopic","setgoods","setorder","settopiccomment"});
			
			JSONArray values = JSONArray.fromObject(list,config);  
			JSONObject json=new JSONObject();
			json.put("result", action);
			json.put("values", values);
			System.out.println(values.toString());
			
			
			return json;
		} 
		//���ⲿָ����ͨ�ü������Թ��˷���
		public static <T> JSONObject Generaljsonarray(List<T> list,String action,String[] FilterProperties){
			JsonConfig config = new JsonConfig();
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			config.setExcludes(FilterProperties);
			
			JSONArray values = JSONArray.fromObject(list,config);  
			JSONObject json=new JSONObject();
			json.put("result", action);
			json.put("values", values);
			System.out.println(values.toString());
			
			
			return json;
		} 
		
	}
