package zzu.fileUploadAndDownload;

//上传多个文件
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
	@Autowired
	UpdateImgToDB UPDB;
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
		Thread uploadThread;
		
		
		if (action == null&&action.equals("")) {System.out.println("上传图片action空在或空串");}
		switch (action) {
			case "上传商品图片":
				boolean b=getDBinfo();
				 uploadThread=new Thread(new Upload("goodsuploadImage",str[0]));
				uploadThread.start();
				uploadThread.join();
			if(b){ List<String> imgname=strlist;  
			        if(imgname!=null)     updategoods(imgname);//更新商品图片信息
			        
			}
				break;
				
			case "上传主题图片":
				 uploadThread=new Thread(new Upload("topicCircle", "theme"));
				 uploadThread.start();
					uploadThread.join();
				List<String> Themeimgname=strlist;
				Integer i=Integer.parseInt(ThemeId);
				  Theme t=new Theme();t.setThemeId(i);
				if(Themeimgname.size()!=0){ t.setThemeImg(Themeimgname.get(0)); 
				System.out.println("theme:"+t);
				isSuccessful=TCS.updateTheme(t);
				
				           }
				break;
			case "上传话题图片":
				 uploadThread=new Thread(new Upload("topicCircle", "topic"));
				 uploadThread.start();
				 uploadThread.join();
					List<String> Topicimgname=strlist;
					Integer i2=Integer.parseInt(TopicId);
					Topic topic=new Topic();
					topic.setTopicId(i2);
					if(Topicimgname.size()!=0){
						Panduanstr p=new Panduanstr();
						String str=p.pinjie(Topicimgname);
						topic.setTopicImg(str); 
					isSuccessful=TCS.updateTopic(topic);
					
					}
					
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

//只管输入输出的内部文件类
	class Upload implements Runnable {
		
		String foldername,imgname;
		public  Upload(String foldername,String imgname) {
			this.foldername=foldername;
			this.imgname=imgname;

		}
		public Upload() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			isSuccessful=false;
			List<String> imgnamelist = new ArrayList<String>();// 建保存新图片名的集合，用于后面拼接成字符串更新到数据库
			// 图片存放路径
			String realPath = request.getRealPath("/").substring(0,
					request.getRealPath("/").lastIndexOf(request.getContextPath().replace("/", "")))
					+ foldername + File.separator;
			System.out.println("文件存放目录: " + realPath);
			FileOutputStream fos = null;
			FileInputStream fis = null;
			System.out.println("action:" + action);
			try {

				File file = new File(realPath);

				// 创建文件上传的位置
				if (!file.exists()) {
					file.mkdirs();
					System.out.println("文件夹不存在已创建");
				} else {
					System.out.println("文件夹已经存在");
				}

				// 上传文件

				for (int i = 0; i < images.size(); i++) {
					String sss = getImagesContentType()[i];
					System.out.println("类型:" + sss);
					String suffix = getImagesFileName()[i].substring(getImagesFileName()[i].lastIndexOf("."));
					String name = imgname + data.GetNowDate2() + UUID.randomUUID().toString() + suffix; // 设置图片名=用户账户+目前时间+UUID
					imgnamelist.add(name);
                    System.out.println("存储路径:"+realPath+name);
					fos = new FileOutputStream(realPath + name);
					//fis = new FileInputStream(images[i]);
					 fis=new FileInputStream(images.get(i));
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) != -1) {
						fos.write(buffer, 0, len);
					}
				}
				isSuccessful=true;
				strlist.addAll(imgnamelist);
				
			} catch (FileNotFoundException e) {
				System.err.println("文件不存在" + e.getMessage());
			} catch (Exception e) {
				System.err.println("文件上传失败");
				e.printStackTrace();
			} finally {
				try {
					fis.close();
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
             
	
			
			
			
		}
			
}//内部类到此结束
	
//只更新商品的方法
	public void updategoods(List<String> l) {
		String imageurl = p.pinjie(l);// 拼接成字符串
		if(str[1]!=null)
		imageurl += str[1];// 数据库中和新生成的图片名拼接

		if (imageurl != null && !imageurl.equals("")) {
			goods.setGimage(imageurl);
			taoyuService.updateGoods(goods);
			
			isSuccessful = true;
		}
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


}

