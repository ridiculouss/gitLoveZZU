package zzu.fileUploadAndDownload;

//上传多个文件
import java.util.ArrayList;


import java.util.List;
import java.util.UUID;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

import life.taoyu.entity.Goods;
import life.taoyu.service.TaoyuService;
import net.sf.json.JSONObject;
import zzu.util.FileDelete;
import zzu.util.GetDate;
import zzu.util.Panduanstr;

@Component(value = "imagesupload")
@Scope(value = "prototype")
public class UploadAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 上传文件域
	private File[] images = new File[10];
	// 上传文件类型
	private String[] imagesContentType = new String[10];
	// 封装上传文件名
	private String[] imagesFileName = new String[10];

	private String goods_id;
	private String SessionID;
    private String action=null;
    
    private String[] DNames=null;//指定要删除的图片数组


	@Resource(name = "getdata")
	private GetDate data;
	@Resource(name = "taoyuService")
	private TaoyuService taoyuService;

	@Override
	public String execute() throws FileNotFoundException, IOException {
		boolean isSuccessful=false;
		String[] str =null;// str[0] 是放Account，str[1]是放获取数据库中的旧的图片名
		String[] imagenames = null;// 存放获取的旧的图片名
		Panduanstr p = new Panduanstr();
		Goods goods = new Goods();
		
		//获取数据库中商品信息
		if (goods_id != null && goods_id != null) {
			str = taoyuService.getImageName(SessionID, goods_id);
			goods.setGoods_id(Integer.parseInt(goods_id));
			imagenames = p.fenli(str[1]);

		} else {
			System.out.println("上传图片时SessionID为空");
			
		}
       
		HttpServletRequest request = ServletActionContext.getRequest();
		String realPath = request.getRealPath("/").substring(0,
				request.getRealPath("/").lastIndexOf(request.getContextPath().replace("/", "")));
		System.out.println("文件存放目录: " + realPath + "goodsuploadImage" + File.separator);
		FileOutputStream fos = null;
		FileInputStream fis = null;
		
		try {
			 if(action.equals("删除图片") ){
		        	FileDelete d=new FileDelete();
		          String newnames= d.delete(realPath, str[1],DNames);//返回新的imageurl
		          if(newnames !=null){
		          goods.setGimage(newnames);
					taoyuService.updateGoods(goods);}
		          isSuccessful=true;
		          
			 }else if(action.equals("上传图片") && imagenames.length<10){
		        
		
			File file = new File(realPath + "goodsuploadImage" + File.separator);

	// 创建文件上传的位置
			if (!file.exists()) {
				file.mkdirs();
				System.out.println("文件夹不存在已创建");
			} else {
				System.out.println("文件夹已经存在");
			}

	//上传文件
			List<String> l = new ArrayList<>();//建保存新图片名的集合，用于后面拼接成字符串
			
			for (int i = 0; i < images.length; i++) {
                 String sss=getImagesContentType()[i];System.out.println("类型:"+sss);
				String suffix = getImagesFileName()[i].substring(getImagesFileName()[i].lastIndexOf("."));
				String name = str[0] + data.GetNowDate2() + UUID.randomUUID().toString() + suffix; // 设置图片名=用户账户+目前时间+UUID
				l.add(name);

				fos = new FileOutputStream(realPath  + "goodsuploadImage" + File.separator + name);
				fis = new FileInputStream(getImages()[i]);
				
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				}
				
				close(fos, fis);
				System.out.println("多文件上传操作成功");
			
			
			
			
			String imageurl = p.pinjie(l);//    拼接成字符串
			       imageurl +=str[1];//数据库中和新生成的图片名拼接
			       
			if (imageurl != null && !imageurl.equals("")) {
				goods.setGimage(imageurl);
				taoyuService.updateGoods(goods);
				isSuccessful=true;
			}
		
		
	    	
			 }else{System.out.println("该用户上传图片数量累计已超过"+imagenames.length +"个");}

		} catch (FileNotFoundException e) {
			System.out.println("文件不存在" + e.getMessage());
		} catch (Exception e) {
			System.out.println("文件上传失败");
			e.printStackTrace();
		}
//返回数据	
		HttpServletResponse response= ServletActionContext.getResponse();
		response.setHeader("Content-type", "text/html;charset=UTF-8");   
		response.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();   
		
    	 json.put("isSuccessful", isSuccessful);	    	 
          
        
     PrintWriter out=response.getWriter();    	
    	out.println(json);
    	out.flush();
    	out.close();
		return null;
	}

	/**
	 * 属性注入
	 * 
	 * @return
	 */
	public String[] getDNames() {
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

	

	public File[] getImages() {
		return images;
	}

	public void setImages(File[] images) {
		this.images = images;
	}

	
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

	private void close(FileOutputStream fos, FileInputStream fis) {
		if (fis != null) {
			try {
				fis.close();
				fis = null;
			} catch (IOException e) {
				System.out.println("FileInputStream关闭失败");
				e.printStackTrace();
			}
		}
		if (fos != null) {
			try {
				fos.close();
				fis = null;
			} catch (IOException e) {
				System.out.println("FileOutputStream关闭失败");
				e.printStackTrace();
			}
		}
	}


	
}
