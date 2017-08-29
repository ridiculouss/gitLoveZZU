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
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            // 设置通用的请求属性
	            conn.setRequestProperty("Content-Type", "application/json");
	            conn.setRequestProperty("Authorization", "Bearer${YWMtkYuozIu8EeeUDAms-qBUTgAAAAAAAAAAAAAAAAAAAAFioi7wZ5oR54Ihe5Cl3p0QAgMAAAFeJ5cXnQBPGgDPJOLog3hzRlc2UOQCDfIi7cMEaQXUvWhxLWWvDB1XMA}");
	            
	            
	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(conn.getOutputStream());
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            // 定义BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	        }
	        //使用finally块来关闭输出流、输入流
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
	 //注册环信账号
	 public static String regist(){
		 JsonObject json=new JsonObject();
		 json.addProperty("username", "Mr123");
		 json.addProperty("password", "han12345");
		
		 System.out.println(json);
		 //发送 POST 请求
	        String sr=Huanxin.sendPost("https://a1.easemob.com/1113170131115043/lovezzu/users",json);
	        		System.out.println(sr);

		 return sr;
	 }
	 //获取环信token
	 public static String getToken(){
		 JsonObject json=new JsonObject();
		 json.addProperty("grant_type", "client_credentials");
		 json.addProperty("client_id", "YXA6YqIu8GeaEeeCIXuQpd6dEA");
		 json.addProperty("client_secret", "YXA6SiB41MSM88qQboBPb3k0V3gy4PQ");
		 System.out.println(json);
		 //发送 POST 请求
	        String sr=Huanxin.sendPost("https://a1.easemob.com/1113170131115043/lovezzu/token",json);
	        		System.out.println(sr);

		 return sr;
	 }
	 public static void main(String[] args) {
		 regist();

	}
}
