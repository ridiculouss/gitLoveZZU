package zzu.fileUploadAndDownload;

//�ϴ�����ļ�
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
	

	
	// �ϴ��ļ���

	//private File[] images = new File[10];
	 List<File> images=new ArrayList<File>();
	// �ϴ��ļ�����
	private String[] imagesContentType = new String[10];
	// ��װ�ϴ��ļ���
	private String[] imagesFileName = new String[10];

	
	
	private String action ,SessionID;

    private String goods_id,ThemeId ,TopicId ,groupId,dynamicId;//��Ʒ �����⣬����,Ⱥ��,Ⱥ�鶯̬ ��id
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
	public String execute() throws IOException {
		 Pattern pattern = Pattern.compile("[0-9]*"); 
		   boolean isSuccessful=true;
		System.out.println(action+":"+images);
		System.out.println("groupId="+groupId);
		String realPath = request.getRealPath("/").substring(0,
				request.getRealPath("/").lastIndexOf(request.getContextPath().replace("/", ""))) ;
		BlockingQueue BQmadeimg=new BlockingQueue();//ʵ������������
		
		
		
		if (action == null&&action.equals("")) {System.out.println("�ϴ�ͼƬaction���ڻ�մ�");}
		switch (action) {
			case "�ϴ���ƷͼƬ":if(goods_id==null||goods_id.equals("")||!pattern.matcher(goods_id).matches()){isSuccessful=false;break;}
				realPath+= "goodsuploadImage"+ File.separator;
				new UploadThread(goods_id,BQmadeimg,"goods",images,imagesContentType,imagesFileName,realPath).run();//��ʹ���߳��ˣ���bug��û�����ס�
				exec.execute(new UpdateImgToDB(BQmadeimg,taoyuService));//���ݶ��к�service����,�ȴ������浽���ݿ������;
				break;
				
			case "�ϴ�����ͼƬ":if(ThemeId==null||ThemeId.equals("")||!pattern.matcher(ThemeId).matches()){isSuccessful=false;break;}
				realPath+= "topicCircle"+ File.separator;
				new UploadThread(ThemeId,BQmadeimg, "theme",images,imagesContentType,imagesFileName,realPath).run();
				exec.execute(new UpdateImgToDB(BQmadeimg,TCS));
				break;
				
			case "�ϴ�����ͼƬ":if(TopicId==null||TopicId.equals("")||!pattern.matcher(TopicId).matches()){isSuccessful=false;break;}
				realPath+= "topicCircle"+ File.separator;
					new UploadThread(TopicId, BQmadeimg,"topic",images,imagesContentType,imagesFileName,realPath).run();
					exec.execute(new UpdateImgToDB(BQmadeimg,TCS));
				break;
				
			case "�ϴ�Ⱥ��ͼƬ":if(groupId==null||groupId.equals("")||!pattern.matcher(groupId).matches()){isSuccessful=false;break;}
				realPath+= "playTogether"+ File.separator;
				new UploadThread(groupId, BQmadeimg,"group",images,imagesContentType,imagesFileName,realPath).run();
				exec.execute(new UpdateImgToDB(BQmadeimg,groupService));
				break;
				
			case "�ϴ�Ⱥ�鶯̬ͼƬ":if(dynamicId==null||dynamicId.equals("")||!pattern.matcher(dynamicId).matches()){isSuccessful=false;break;}
				realPath+= "playTogether"+ File.separator;
				new UploadThread(dynamicId, BQmadeimg,"groupDynamic",images,imagesContentType,imagesFileName,realPath).run();
				exec.execute(new UpdateImgToDB(BQmadeimg,groupService));
				break;
			default:System.err.println("ͼƬ�ϴ�actionδƥ��");
			isSuccessful=false;
				break;
			}
		
		 
			int count=((ThreadPoolExecutor)exec).getActiveCount();
			int maxmum=((ThreadPoolExecutor)exec).getMaximumPoolSize();
			int core=((ThreadPoolExecutor)exec).getCorePoolSize();
			int largest=((ThreadPoolExecutor)exec).getLargestPoolSize();
			int poolsize=((ThreadPoolExecutor)exec).getPoolSize();
			System.out.println("��ǰ����߳���="+count);
			System.out.println("ͬʱ���ڵ�����߳���="+maxmum);
			System.out.println("�����߳���="+core);
			System.out.println("����߳���="+largest);
			System.out.println("�̳߳ش�С="+poolsize);
			
             
			
				Returndata.returnboolean(isSuccessful);
			
					

		return null;
	}
	



	
	


	
	

	/**
	 * ����ע��
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

