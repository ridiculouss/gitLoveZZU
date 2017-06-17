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
    
    private String imageFileName=null;

	public String getImageFileName() {
		return imageFileName;
	} 

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	@SuppressWarnings("deprecation")
	public InputStream getTargetFile() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String realPath = request.getRealPath("/").substring(0,
				request.getRealPath("/").lastIndexOf(request.getContextPath().replace("/", "")));
		String downloadimage=null;
		if(action.equals("头像")){
			downloadimage="uploadFiles";
		}else{downloadimage="goodsuploadImage";}
		if(imageFileName==null){
			imageFileName="111.jpg";}
		
		String FilerealPath = realPath + downloadimage + File.separator+ imageFileName;
		 System.out.println("文件下载路径" + FilerealPath);
		File image= new File(FilerealPath);
	
	  
	   
	    FileInputStream fis;
		boolean b=image.exists();
		if (b) {
			System.out.println("文件已找到并发送");
			fis = new FileInputStream(FilerealPath);
			System.out.println("文件size:" + String.valueOf(fis.available() / 1000) + "k");
           
			
		} else {
			FilerealPath = realPath + "uploadFiles" + File.separator + "1.jpg";
			System.out.println("文件不存在发送默认图片");
			fis = new FileInputStream(FilerealPath);
			
		}
		
		return fis;
	}
	


}
