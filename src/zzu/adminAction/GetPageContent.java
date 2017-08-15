package zzu.adminAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;
@Transactional
@Component(value = "crawler")
@Scope(value = "prototype")
public class GetPageContent extends ActionSupport{
	List<Mo> imglist=new ArrayList<Mo>();//存放图片的list
	List<Mo> titlelist=new ArrayList<Mo>();
	List<Mo> textlist=new ArrayList<Mo>();
        
   
  
   


	@Override
		public String execute() throws Exception {
		getfun();
			return "ok";
		}
    
	public   boolean getfun(){
		 Document doc;
			try {
				doc = Jsoup.connect("http://news.xinhuanet.com/2017-08/09/c_1121452514.htm")	
						 .data("query", "Java")  
						  .userAgent("Mozilla")  
						  .cookie("auth", "token") 
						  .timeout(5000)
						  .get();  

				Elements e = doc.select("img"); 
				Elements e2 = doc.select("p"); 
				
				System.err.println(e);
			//	System.out.println("所有图片元素:"+e);//获取图片元素
			//	System.err.println("图片URL："+e.select("img").attr("src"));//获取img 元素中属性src的值
		    	//System.out.println(e2);
				
//				String[] ss=e2.text().split(" ");
//				for (String string : ss) {
//					System.out.println("所有p元素:"+string);//输出所有元素内容
//				}
			//System.out.println(e2.removeAll(e2.select("p:has(strong)")));
				System.out.println(e2.removeAll(e2.select("p:contains(Copyright)"))+","+e2.removeAll(e2.select("p:contains(版权所有)")));
//				System.out.println("不包括<strong>的所有p元素:"+e2.html());
				//System.err.println("获取第二条p元素内容"+e2.get(1).html());//获取指定元素的内容
				//System.err.println(e2.get(0).select("a").attr("href"));//获取超链接元素中属性值
				
				for(int i=0;i<e.size();i++){
					Mo m1=new Mo();
				m1.setImg(	e.get(i).select("img").attr("src"));
				imglist.add(m1);
				}
				if(imglist.size()>1)
			for (int i=1;i<imglist.size()-1;i++) {
				if(imglist.get(i).getImg().equals(imglist.get(i-1).getImg()))
					imglist.remove(imglist.get(i));
			}
				System.out.println(imglist);
				
				String[] ssse=e2.select("p:has(strong)").text().split(" ");
				for (String s : ssse) {
					 Mo m2=new Mo();
					m2.setTitle(s);
					titlelist.add(m2);
				}			
				System.out.println(titlelist+"//"+titlelist.size());
				System.out.println(e2.removeAll(e2.select("p:has(strong)")));
				
				Mo m3=new Mo();
				m3.setText(e2.html().toString());
					textlist.add(m3);
				System.out.println(textlist);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		return true;
	}

	public List<Mo> getImglist() {
		return imglist;
	}

	public List<Mo> getTitlelist() {
		return titlelist;
	}

	public List<Mo> getTextlist() {
		return textlist;
	}

	

	
	
}
class Mo{
	String img,title,text;

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Mo [img=" + img + ", title=" + title + ", text=" + text + "]";
	}
}
