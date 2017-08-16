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
import life.taoyu.entity.Goods;
import life.taoyu.service.TaoyuService;
import net.sf.json.JSONObject;
import persionalCenter.dao.Dao;
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

	private String goods_id;
	private String SessionID;
	private String action = null;

	private String[] DNames = null;// 指定要删除的图片数组
    private String ThemeId ,TopicId ;//主题，话题id
	@Resource(name = "getdata")
	private GetDate data;
	@Resource(name = "taoyuService")
	private TaoyuService taoyuService;
	@Autowired
	TopicCircleService  TCS;
	
	HttpServletRequest request = ServletActionContext.getRequest();
	boolean isSuccessful = false;
	String[] str = null;// str[0] 是放Account，str[1]是放获取数据库中的旧的图片名
	String[] imagenames = null;// 存放获取的旧的图片名
	Panduanstr p = new Panduanstr();
	Goods goods = new Goods();

	
	List<String> strlist=new ArrayList<String>();

	@Override
	public String execute() throws FileNotFoundException, IOException, InterruptedException {
		System.out.println(ThemeId+","+action+":"+images);
		String realPath = request.getRealPath("/").substring(0,
				request.getRealPath("/").lastIndexOf(request.getContextPath().replace("/", ""))) ;
		BlockingQueue BQmadeimg=new BlockingQueue();//实例化阻塞队列
		ExecutorService exec=Executors.newCachedThreadPool();
		
		
		if (action == null&&action.equals("")) {System.out.println("上传图片action空在或空串");}
		switch (action) {
			case "上传商品图片":realPath+= "goodsuploadImage"+ File.separator;
				
				exec.execute(new UploadThread(goods_id,BQmadeimg,"goods",images,imagesContentType,imagesFileName,realPath));
				exec.execute(new UpdateImgToDB(BQmadeimg,taoyuService));//传递队列和service对象

				break;
				
			case "上传主题图片":realPath+= "topicCircle"+ File.separator;
				exec.execute(new UploadThread(ThemeId,BQmadeimg, "theme",images,imagesContentType,imagesFileName,realPath));
				
				exec.execute(new UpdateImgToDB(BQmadeimg,TCS));
				break;
			case "上传话题图片":
				realPath+= "topicCircle"+ File.separator;
				for(int i=0;i<20;i++)
					exec.execute(new UploadThread(TopicId, BQmadeimg,"topic",images,imagesContentType,imagesFileName,realPath));
					exec.execute(new UpdateImgToDB(BQmadeimg,TCS));
				
				break;
				
			default:System.err.println("图片上传action未匹配");
				break;
			}
		
		 
			

			
					

		return null;
	}
	
public boolean getDBinfo(){
	// 获取数据库中记录信息
	boolean b=false;
	if(images==null){System.out.println("图片文件为空");return b;}
				if (goods_id != null && !goods_id.equals("") && SessionID != null && !SessionID.equals("")) {
					str = taoyuService.getImageName(SessionID, goods_id);
					if (str == null) {
						System.err.println("未检索到数据库中商品信息");
						return b;
					}
					goods.setGoods_id(Integer.parseInt(goods_id));
					if (str[1] != null)
						imagenames = p.fenli(str[1]);
                  b=true;
				} else {
					System.err.println("上传商品图片时SessionID或商品id为空");
					}	
				return b;
   }


	
	


	// 删除图片方法
	public void deletePicture() throws Exception {
		String realPath = request.getRealPath("/").substring(0,
				request.getRealPath("/").lastIndexOf(request.getContextPath().replace("/", ""))) + "goodsuploadImage"
				+ File.separator;

		if (action.equals("删除图片") && imagenames != null) {
			FileDelete d = new FileDelete();
			String newImgnames = d.delete(realPath, str[1], DNames);// 返回新的imageurl
			if (newImgnames != null) {
				goods.setGimage(newImgnames);
				taoyuService.updateGoods(goods);
				
			}
			isSuccessful = true;

		}
	}

	/**
	 * 属性注入
	 * 
	 * @return
	 */
	public String[] getDNames() {// 发来的要删除的图片名
		return DNames;
	}

	public void setDNames(String[] dNames) {
		DNames = dNames;
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

//保存图片名到数据库的内部类
	
}

