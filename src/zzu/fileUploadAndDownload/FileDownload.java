package zzu.fileUploadAndDownload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.Buffer;
import com.opensymphony.xwork2.ActionSupport;
@Transactional
@Component(value = "imagefiledownload")
@Scope(value="prototype")
public class FileDownload extends ActionSupport {
 
	/**
	 * 文件下载
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private String inputPath;
private String action=null;
public String getAction() {
	return action;
}

public void setAction(String action) {
	this.action = action;
}

public void setInputPath(String value) {
	inputPath = value;
}
    
    private String imageURL=null;


	
	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	HttpServletRequest request = ServletActionContext.getRequest();
	BufferedInputStream fis=null;
	@SuppressWarnings("deprecation")
	public InputStream getTargetFile() throws IOException {
		
	
		Download d=new Download();
		Thread io=new Thread(d);
	   io.start();
	   
	   
	  
	Thread main=new Thread();
	main.start();
	try {
		io.join();
		 fis=d.getFileInputStream();
		   
			
		    
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return fis;
	  
	  
	}
	
class Download implements Runnable{
	
	
	public Download() {
		
	}
	
	@Override
	public void run() {
		try {
			action=URLDecoder.decode(action,"UTF-8");
		
		System.out.println("action:"+action);
		
		
		String realPath = request.getRealPath("/").substring(0,
				request.getRealPath("/").lastIndexOf(request.getContextPath().replace("/", "")));
		String downloadimage=null;
		
		switch (action) {
		case "头像":
			downloadimage="uploadFiles";
			break;
		case "商品":
			downloadimage="goodsuploadImage";
			break;
		case "话题圈":
			downloadimage="topicCircle";
			break;
		

		default:System.err.println("没有发来要下载哪个文件夹的图片，默认是头像");
			break;
		}
		if(imageURL==null || imageURL.equals("")){
			imageURL="111.jpg";
			System.err.println("要下载的文件名为空，返回默认图片");}
		System.out.println("图片名:"+imageURL);
		String FilerealPath = realPath + downloadimage + File.separator+ imageURL;
		 System.out.println("文件下载路径" + FilerealPath);
		
		 File image= new File(FilerealPath);
			boolean b=image.exists();
			if (b) {
				System.out.println("文件已找到并发送");
				fis = new BufferedInputStream(new FileInputStream(image));
				System.out.println("文件size:" + String.valueOf(fis.available() / 1000) + "k");
	           
				
			} else {
				FilerealPath = realPath + "uploadFiles" + File.separator + "111.jpg";
				File file=new File(FilerealPath);
				
				
					fis = new BufferedInputStream(new FileInputStream(file));
				 
				System.out.println("文件不存在发送默认图片");
			}
	    
		} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	
	public BufferedInputStream getFileInputStream(){
		return fis;
	}
}

}
