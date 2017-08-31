package zzu.huanxin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonObject;

public class Huanxin {

	 public static String sendPost(String url, JsonObject param) {
	        PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            // �򿪺�URL֮�������
	            URLConnection conn = realUrl.openConnection();
	            // ����ͨ�õ���������
	            conn.setRequestProperty("Content-Type", "application/json");
	            conn.setRequestProperty("Authorization", "Bearer${YWMtkYuozIu8EeeUDAms-qBUTgAAAAAAAAAAAAAAAAAAAAFioi7wZ5oR54Ihe5Cl3p0QAgMAAAFeJ5cXnQBPGgDPJOLog3hzRlc2UOQCDfIi7cMEaQXUvWhxLWWvDB1XMA}");
	            
	            
	            // ����POST�������������������
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // ��ȡURLConnection�����Ӧ�������
	            out = new PrintWriter(conn.getOutputStream());
	            // �����������
	            out.print(param);
	            // flush������Ļ���
	            out.flush();
	            // ����BufferedReader����������ȡURL����Ӧ
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("���� POST ��������쳣��"+e);
	            e.printStackTrace();
	        }
	        //ʹ��finally�����ر��������������
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result;
	    } 
	 //ע�ỷ���˺�
	 public static String regist(){
		 JsonObject json=new JsonObject();
		 json.addProperty("username", "Mr123");
		 json.addProperty("password", "han12345");
		
		 System.out.println(json);
		 //���� POST ����
	        String sr=Huanxin.sendPost("https://a1.easemob.com/1113170131115043/lovezzu/users",json);
	        		System.out.println(sr);

		 return sr;
	 }
	 //��ȡ����token
	 public static String getToken(){
		 JsonObject json=new JsonObject();
		 json.addProperty("grant_type", "client_credentials");
		 json.addProperty("client_id", "YXA6YqIu8GeaEeeCIXuQpd6dEA");
		 json.addProperty("client_secret", "YXA6SiB41MSM88qQboBPb3k0V3gy4PQ");
		 System.out.println(json);
		 //���� POST ����
	        String sr=Huanxin.sendPost("https://a1.easemob.com/1113170131115043/lovezzu/token",json);
	        		System.out.println(sr);

		 return sr;
	 }
	 public static void main(String[] args) {
		 regist();

	}
}
