package zzu.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

public class Returndata {
	
public static void returndata(Object o) throws IOException{
	
	// 返回对象数据
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setHeader("Content-type", "text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				
				
				PrintWriter out = response.getWriter();
				out.println(o);
				out.flush();
				out.close();
				System.out.println("返回前端数据成功");
}
//返回布尔数据
public static void returnboolean(boolean isSuccessful) throws IOException{
	
	HttpServletResponse response = ServletActionContext.getResponse();
	response.setHeader("Content-type", "text/html;charset=UTF-8");
	response.setCharacterEncoding("UTF-8");
	JSONObject json = new JSONObject();
	json.put("isSuccessful", isSuccessful);
	
	PrintWriter out = response.getWriter();
	out.println(json);
	out.flush();
	out.close();
	System.out.println("返回前端数据成功");
}
}
