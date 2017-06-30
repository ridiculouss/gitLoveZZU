package zzu.fileUploadAndDownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

@Component(value = "filedownload")
@Scope(value="prototype")
public class FileDownload extends ActionSupport {
 
	/**
	 * 
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

	@SuppressWarnings("deprecation")
	public InputStream getTargetFile() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String realPath = request.getRealPath("/").substring(0,
				request.getRealPath("/").lastIndexOf(request.getContextPath().replace("/", "")));
		String downloadimage=null;
		if(action !=null && action.equals("头像")){
			downloadimage="uploadFiles";
		}else if(action !=null && action.equals("商品")){downloadimage="goodsuploadImage";
		}else{System.out.println("不知道要下载啥图片");
		downloadimage="uploadFiles";
		}
		if(imageURL==null){
			imageURL="111.jpg";}
		
		String FilerealPath = realPath + downloadimage + File.separator+ imageURL;
		 System.out.println("文件下载路径" + FilerealPath);
		File image= new File(FilerealPath);
	
	  
	   
	    FileInputStream fis;
	    
		boolean b=image.exists();
		if (b) {
			System.out.println("文件已找到并发送");
			fis = new FileInputStream(image);
			System.out.println("文件size:" + String.valueOf(fis.available() / 1000) + "k");
           
			
		} else {
			FilerealPath = realPath + "uploadFiles" + File.separator + "111.jpg";
			File file=new File(FilerealPath);
			
			fis = new FileInputStream(file);
			System.out.println("文件不存在发送默认图片");
		}
		 if(fis == null){System.out.println("返回的IO文件为空");}
		return fis;
	}
	


}
