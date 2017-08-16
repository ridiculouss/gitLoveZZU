package zzu.fileUploadAndDownload;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import life.mytopiccircle.entity.Theme;
import life.mytopiccircle.entity.Topic;
import life.mytopiccircle.service.TopicCircleService;
import life.taoyu.entity.Goods;
import life.taoyu.service.TaoyuService;
import zzu.util.Panduanstr;

public class UpdateImgToDB  implements Runnable{

	BlockingQueue BQuseimg;
	Object  S;
	public UpdateImgToDB(BlockingQueue BQmadeimg,Object  TCS) {
		this.BQuseimg=BQmadeimg;
		this.S=TCS;
	}
	
	
	
	
	public  boolean updateTopicImg(List<String> strlist,String TopicId){
		boolean isSuccessful=false;
		List<String> Topicimgname=strlist;
		Integer i2=Integer.parseInt(TopicId);
		Topic topic=new Topic();
		topic.setTopicId(i2);
		if(Topicimgname.size()!=0){
			Panduanstr p=new Panduanstr();
			String str=p.pinjie(Topicimgname);
			topic.setTopicImg(str); 
		isSuccessful=((TopicCircleService) S).updateTopic(topic);
		
		}
		return isSuccessful;
	}
	public boolean updateThemeImg(List<String> strlist,String ThemeId){
		boolean isSuccessful=false;
		List<String> Themeimgname=strlist;
		Integer i=Integer.parseInt(ThemeId);
		  Theme t=new Theme();t.setThemeId(i);
		if(Themeimgname.size()!=0){ t.setThemeImg(Themeimgname.get(0)); 
		System.out.println("theme:"+t);
		isSuccessful=((TopicCircleService) S).updateTheme(t);
		
		           }
		return isSuccessful;
	}
	public boolean updateGoodsImg(List<String> strlist,String goods_id){
		boolean isSuccessful=false;
		
	        if(strlist==null) {return false;}  
	        Panduanstr p=new Panduanstr();
	        	String imageurl = p.pinjie(strlist);// 拼接成字符串
	        	Goods goods=new Goods();
	        	goods.setGoods_id(Integer.valueOf(goods_id));
	        	goods.setGimage(imageurl);
				((TaoyuService) S).updateGoods(goods);
	        
	 return  isSuccessful;      
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			boolean ok=true;
			while(ok){
			Module m=BQuseimg.take();
			System.out.println(m);
			  switch (m.getIdentity()) {
			case "goods":
				updateGoodsImg(m.getImgname(),m.getId());
				break;

			case "theme":
				updateThemeImg(m.getImgname(),m.getId());
				ok=false;
				break;
				
			case "topic":
				updateTopicImg(m.getImgname(),m.getId());
				ok=false;
				break;
			default:
				break;
			}
			}
			System.out.println("退出了while循环");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
