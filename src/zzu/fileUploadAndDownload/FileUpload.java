package zzu.fileUploadAndDownload;

//上传多个文件
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;

import life.mytopiccircle.entity.Theme;
import life.mytopiccircle.entity.Topic;
import life.mytopiccircle.service.TopicCircleService;
import life.playTogether.service.GroupService;
import life.taoyu.entity.Goods;
import life.taoyu.service.TaoyuService;
import net.sf.json.JSONObject;
import persionalCenter.dao.Dao;
import zzu.util.Execfactory;
import zzu.util.GetDate;
import zzu.util.Panduanstr;
import zzu.util.Returndata;
@Transactional
@Component(value = "imagesupload")
@Scope(value = "prototype")
public class FileUpload extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
	// 上传文件域

	//private File[] images = new File[10];
	 List<File> images=new ArrayList<File>();
	// 上传文件类型
	private String[] imagesContentType = new String[10];
	// 封装上传文件名
	private String[] imagesFileName = new String[10];

	
	
	private String action ,SessionID;

    private String goods_id,ThemeId ,TopicId ,groupId,dynamicId;//商品 ，主题，话题,群组,群组动态 的id
	@Resource(name = "getdata")
	private GetDate data;
	@Resource(name = "taoyuService")
	private TaoyuService taoyuService;
	@Autowired
	TopicCircleService  TCS;
	@Autowired
	GroupService groupService;
	HttpServletRequest request = ServletActionContext.getRequest();
	ExecutorService exec=Execfactory.getexec();

	@Override
	public String execute() {
		 Pattern pattern = Pattern.compile("[0-9]*"); 
		   boolean isSuccessful=true;
		System.out.println(action+":"+images);
		String realPath = request.getRealPath("/").substring(0,
				request.getRealPath("/").lastIndexOf(request.getContextPath().replace("/", ""))) ;
		BlockingQueue BQmadeimg=new BlockingQueue();//实例化阻塞队列
		
		
		
		if (action == null&&action.equals("")) {System.out.println("上传图片action空在或空串");}
		switch (action) {
			case "上传商品图片":if(goods_id==null||goods_id.equals("")||!pattern.matcher(goods_id).matches()){isSuccessful=false;break;}
				realPath+= "goodsuploadImage"+ File.separator;
				new UploadThread(goods_id,BQmadeimg,"goods",images,imagesContentType,imagesFileName,realPath).run();//不使用线程了，有bug还没搞明白。
				exec.execute(new UpdateImgToDB(BQmadeimg,taoyuService));//传递队列和service对象,等待处理保存到数据库的任务;
				break;
				
			case "上传主题图片":if(ThemeId==null||ThemeId.equals("")||!pattern.matcher(ThemeId).matches()){isSuccessful=false;break;}
				realPath+= "topicCircle"+ File.separator;
				new UploadThread(ThemeId,BQmadeimg, "theme",images,imagesContentType,imagesFileName,realPath).run();
				exec.execute(new UpdateImgToDB(BQmadeimg,TCS));
				break;
				
			case "上传话题图片":if(TopicId==null||TopicId.equals("")||!pattern.matcher(TopicId).matches()){isSuccessful=false;break;}
				realPath+= "topicCircle"+ File.separator;
					new UploadThread(TopicId, BQmadeimg,"topic",images,imagesContentType,imagesFileName,realPath).run();
					exec.execute(new UpdateImgToDB(BQmadeimg,TCS));
				break;
				
			case "上传群组图片":if(groupId==null||groupId.equals("")||!pattern.matcher(groupId).matches()){isSuccessful=false;break;}
				realPath+= "playTogether"+ File.separator;
				new UploadThread(groupId, BQmadeimg,"group",images,imagesContentType,imagesFileName,realPath).run();
				exec.execute(new UpdateImgToDB(BQmadeimg,groupService));
				break;
				
			case "上传群组动态图片":if(dynamicId==null||dynamicId.equals("")||!pattern.matcher(dynamicId).matches()){isSuccessful=false;break;}
				realPath+= "playTogether"+ File.separator;
				new UploadThread(dynamicId, BQmadeimg,"groupDynamic",images,imagesContentType,imagesFileName,realPath).run();
				exec.execute(new UpdateImgToDB(BQmadeimg,groupService));
				break;
			default:System.err.println("图片上传action未匹配");
			isSuccessful=false;
				break;
			}
		
		 
			int count=((ThreadPoolExecutor)exec).getActiveCount();
			int maxmum=((ThreadPoolExecutor)exec).getMaximumPoolSize();
			int core=((ThreadPoolExecutor)exec).getCorePoolSize();
			int largest=((ThreadPoolExecutor)exec).getLargestPoolSize();
			int poolsize=((ThreadPoolExecutor)exec).getPoolSize();
			System.out.println("当前活动的线程数="+count);
			System.out.println("同时存在的最大线程数="+maxmum);
			System.out.println("核心线程数="+core);
			System.out.println("最大线程数="+largest);
			System.out.println("线程池大小="+poolsize);
			
             
			try {
				Returndata.returnboolean(isSuccessful);
			} catch (IOException e) {
				System.err.println("FileUpload中io异常");
				e.printStackTrace();
			}
					

		return null;
	}
	



	
	


	
	

	/**
	 * 属性注入
	 * 
	 * @return
	 */
	
	

	public String getGroupId() {
		return groupId;
	}

	public String getDynamicId() {
		return dynamicId;
	}
	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSessionID() {
		return SessionID;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public List<File> getImages() {
		return images;
	}
	public void setImages(List<File> images) {
		this.images = images;
	}
//	public File[] getImages() {
//		return images;
//	}
//
//	public void setImages(File[] images) {
//		this.images = images;
//	}

	public String[] getImagesContentType() {
		return imagesContentType;
	}

	public void setImagesContentType(String[] imagesContentType) {
		this.imagesContentType = imagesContentType;
	}

	public String[] getImagesFileName() {
		return imagesFileName;
	}

	public void setImagesFileName(String[] imagesFileName) {
		this.imagesFileName = imagesFileName;
	}
	public String getThemeId() {
		return ThemeId;
	}
	public void setThemeId(String themeId) {
		ThemeId = themeId;
	}
	public String getTopicId() {
		return TopicId;
	}
	public void setTopicId(String topicId) {
		TopicId = topicId;
	}


	
}

