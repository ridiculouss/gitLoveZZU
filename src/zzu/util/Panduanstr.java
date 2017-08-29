package zzu.util;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

public class Panduanstr {
	
	
	//拼接字符串中间隔ZZU
	public String pinjie(List<String> imageurl){
		StringBuilder URL = new StringBuilder();
		 System.out.println("图片数量:"+imageurl.size()+",开始拼接");
		 for(int s=0;s<imageurl.size();s++){
			 if(imageurl.get(s) !=null && !imageurl.get(s).equals("")){
		 	         
		 	           URL.append(imageurl.get(s));
		 	           URL.append("ZZU");
			 }
		 }
		 System.out.println(URL);
		 
		return URL.toString();
		
	}
	//分离字符串
	public String[] fenli(String URL){
		List<String> imageURL=new ArrayList<String>();
		
		  
		 if(URL ==null){ System.out.println("传来的要分离字符串为空");}
	     String a[] = URL.split("ZZU");  
	    
	     System.out.println("图片数量:"+a.length+",开始分离");
	for(int j=0;j<a.length;j++){
		  imageURL.add(a[j].trim());
	}
	System.out.println("字符串分离成功:"+imageURL);
	String[] arr = (String[])imageURL.toArray(new String[imageURL.size()]);//list转数组
		return arr;
		
	} 
	 public static String string2HexString(String strPart) {  
	        StringBuffer hexString = new StringBuffer();  
	        for (int i = 0; i < strPart.length(); i++) {  
	            int ch = (int) strPart.charAt(i);  
	            String strHex = Integer.toHexString(ch);  
	            hexString.append(strHex);  
	        }  
	        return hexString.toString();  
	    }  

public static void main(String[] args) {
   System.out.println(string2HexString("韩伟"));
	 
	 
}
}
