package life.playTogether.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import life.playTogether.entity.Activity;
import life.playTogether.service.ActivityService;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;


@Controller(value="addActivityAction")
@Scope(value="prototype")
public class AddActivityAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		//注入属性
		@Resource(name="activityService")
		private ActivityService activityService;
		@Resource(name="activity")
		private Activity activity;
		
		private String a_userName;
		private String a_userImg;
		private Date a_date;
		private String a_title;
		private String a_content;
		private int a_praiseNumber;
		private int a_commentNumber;
		private String a_shareUrl;	
		
		private File a_img;
		private String a_imgFileName;
		private String a_imgContentType;
		public String upload() throws Exception{
			System.out.println("");
			HttpServletRequest request= ServletActionContext.getRequest();
			String realPath = request.getRealPath("/").substring(0, request.getRealPath("/").lastIndexOf(request.getContextPath().replace("/", "")));
			System.out.println(realPath);
			File file = new File(realPath+File.separator+"uploadFiles"+File.separator);
			//创建文件上传的位置
	        if(!file.exists() ){
	            file.mkdirs();   
	            System.out.println("文件夹不存在已创建");
	        }else{
	            System.out.println("文件夹已经存在");
	        }
			String strNewFileName = UUID.randomUUID().toString();
			String suffix=a_imgFileName.substring(a_imgFileName.lastIndexOf("."));
			strNewFileName +=suffix;
			System.out.println(strNewFileName);
			String fullPath=realPath+"uploadFiles"+File.separator+strNewFileName;
			System.out.println("上传路径："+fullPath);
			FileOutputStream fos=new FileOutputStream(fullPath);
			a_imgFileName=strNewFileName;
			FileInputStream fis=new FileInputStream(getA_img());
			byte[] buffer=new byte[1024];
			int len=0;
			while((len=fis.read(buffer))>0){
				fos.write(buffer,0,len);
			}
			fos.close();
			fis.close();
			
			fullPath="http://192.168.1.100:8080/uploadFiles/"+strNewFileName;
			activity.setA_commentNumber(a_commentNumber);
			activity.setA_content(a_content);
//			activity.setA_date(a_date);
			activity.setA_img(fullPath);
			activity.setA_praiseNumber(a_praiseNumber);
			activity.setA_shareUrl(a_shareUrl);
			activity.setA_title(a_title);
			activity.setA_userImg(a_userImg);
			activity.setA_userName(a_userName);
			activityService.save(activity);
			return SUCCESS;	
		}
		public ActivityService getActivityService() {
			return activityService;
		}
		public void setActivityService(ActivityService activityService) {
			this.activityService = activityService;
		}
		public Activity getActivity() {
			return activity;
		}
		public void setActivity(Activity activity) {
			this.activity = activity;
		}
		public String getA_userName() {
			return a_userName;
		}
		public void setA_userName(String a_userName) {
			this.a_userName = a_userName;
		}
		public String getA_userImg() {
			return a_userImg;
		}
		public void setA_userImg(String a_userImg) {
			this.a_userImg = a_userImg;
		}
		public Date getA_date() {
			return a_date;
		}
		public void setA_date(Date a_date) {
			this.a_date = a_date;
		}
		public String getA_title() {
			return a_title;
		}
		public void setA_title(String a_title) {
			this.a_title = a_title;
		}
		public String getA_content() {
			return a_content;
		}
		public void setA_content(String a_content) {
			this.a_content = a_content;
		}
		public int getA_praiseNumber() {
			return a_praiseNumber;
		}
		public void setA_praiseNumber(int a_praiseNumber) {
			this.a_praiseNumber = a_praiseNumber;
		}
		public int getA_commentNumber() {
			return a_commentNumber;
		}
		public void setA_commentNumber(int a_commentNumber) {
			this.a_commentNumber = a_commentNumber;
		}
		public String getA_shareUrl() {
			return a_shareUrl;
		}
		public void setA_shareUrl(String a_shareUrl) {
			this.a_shareUrl = a_shareUrl;
		}
		public File getA_img() {
			return a_img;
		}
		public void setA_img(File a_img) {
			this.a_img = a_img;
		}
		public String getA_imgFileName() {
			return a_imgFileName;
		}
		public void setA_imgFileName(String a_imgFileName) {
			this.a_imgFileName = a_imgFileName;
		}
		public String getA_imgContentType() {
			return a_imgContentType;
		}
		public void setA_imgContentType(String a_imgContentType) {
			this.a_imgContentType = a_imgContentType;
		}
		
		
		
}
