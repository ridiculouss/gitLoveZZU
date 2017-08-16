package zzu.fileUploadAndDownload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import zzu.util.GetDate;

public class UploadThread implements Runnable {
	private List<File> images=new ArrayList<File>();
		// 上传文件类型
		private String[] imagesContentType = new String[10];
		// 封装上传文件名
		private String[] imagesFileName = new String[10];
	
		GetDate gd=new GetDate();
		String imgname ,realPath,id;
		BlockingQueue BQmadeimg;
		public  UploadThread(String id,BlockingQueue BQmadeimg,String imgname,
				List<File> images,String[] imagesContentType,String[] imagesFileName ,String realPath) {

	         this.id=id;
			this.BQmadeimg=BQmadeimg;
			this.imgname=imgname;
            this.images=images;
            this.imagesContentType=imagesContentType;
            this.imagesFileName=imagesFileName;
			this.realPath=realPath;
}
			
		
		public UploadThread() {
			// TODO Auto-generated constructor stub
		}

		@Override
		 public void run(){
			System.out.println("run中images:"+images);
			
			
			
			
			List<String> imgnamelist = new ArrayList<String>();// 建保存新图片名的集合，用于后面拼接成字符串更新到数据库
			
			// 图片存放路径
			
			System.out.println("文件存放目录: " + realPath);
			
			try {
      
				File file = new File(realPath);

				// 创建文件上传的位置
				if (!file.exists()) {
					file.mkdirs();
					System.out.println("文件夹不存在已创建");
				} else {
					System.out.println("文件夹已经存在");
				}

				for(int i=0;i<images.size();i++){
					String sss = getImagesContentType()[i];
					System.out.println("类型:" + sss);
					String suffix = getImagesFileName()[i].substring(getImagesFileName()[i].lastIndexOf("."));
					String name = imgname + gd.GetNowDate2() + UUID.randomUUID().toString() + suffix; // 设置图片名=用户账户+目前时间+UUID
					imgnamelist.add(name);
					
				}
				// 上传文件
				BufferedOutputStream fos = null;
				BufferedInputStream fis = null;
				System.out.println(imgnamelist);
				synchronized (this) {
					
					
					int i=0;
				for (File image: images) {
					fis=new BufferedInputStream(new FileInputStream(image));					
					System.out.println("i="+i);
					 fos = new BufferedOutputStream(new FileOutputStream(realPath + imgnamelist.get(i++)));
					
					//fis = new FileInputStream(images[i]);
					byte[] buffer = new byte[2024];
					int len = 0;
					while ((len = fis.read(buffer)) != -1) {
						fos.write(buffer, 0, len);
					     }
					fos.flush();
                     }
				}
				if(fis!=null){
					fis.close(); System.out.println("fis colse");}
				if(fos!=null){
					fos.close();System.out.println("fos colse");}
				
			}catch (FileNotFoundException e) {
				
				System.err.println("文件不存在" + e.getMessage());
			} catch (Exception e) {
				System.out.println("文件上传失败");
				e.printStackTrace();
			} 
			Module m=new Module();
			m.setId(id);
			m.setIdentity(imgname);
			m.setImgname(imgnamelist);
			
			BQmadeimg.add(m);
}
             
	
			
			
			
		
		public List<File> getImages() {
			return images;
		}
		public void setImages(List<File> images) {
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
			

}
