package zzu.util;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

public class Panduanstr {
	
	
	//ƴ���ַ����м��ZZU
	public String pinjie(List<String> imageurl){
		StringBuilder URL = new StringBuilder();
		 System.out.println("ͼƬ����:"+imageurl.size()+",��ʼƴ��");
		 for(int s=0;s<imageurl.size();s++){
			 if(imageurl.get(s) !=null && !imageurl.get(s).equals("")){
		 	         
		 	           URL.append(imageurl.get(s));
		 	           URL.append("ZZU");
			 }
		 }
		 System.out.println(URL);
		 
		return URL.toString();
		
	}
	//�����ַ���
	public String[] fenli(String URL){
		List<String> imageURL=new ArrayList<String>();
		
		  
		 if(URL ==null){ System.out.println("������Ҫ�����ַ���Ϊ��");}
	     String a[] = URL.split("ZZU");  
	    
	     System.out.println("ͼƬ����:"+a.length+",��ʼ����");
	for(int j=0;j<a.length;j++){
		  imageURL.add(a[j].trim());
	}
	System.out.println("�ַ�������ɹ�:"+imageURL);
	String[] arr = (String[])imageURL.toArray(new String[imageURL.size()]);//listת����
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
   System.out.println(string2HexString("��ΰ"));
	 
	 
}
}
