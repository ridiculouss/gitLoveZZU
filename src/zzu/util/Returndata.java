package zzu.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

public class Returndata {
	
public static void returndata(Object o) throws IOException{
	
	// ���ض�������

	//������ķ��س��֣����ַ��������û�м�response.setCharacterEncoding("UTF-8");��仰��
  //������ص������ǡ����С��������룬˵��������Ľ������⣬Ӧ�ü�����Ƿ�����response.setHeader("Content-type", "text/html;charset=UTF-8");��仰
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setHeader("Content-type", "text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				
				
				PrintWriter out = response.getWriter();
				out.print(o);
				out.flush();
				out.close();
				System.out.println("����app����");
}
//���ز�������
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
	System.out.println("����app���ݳɹ�"+isSuccessful);
}
}
