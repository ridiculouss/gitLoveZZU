package zzu.fileUploadAndDownload;

import java.util.List;

import life.mytopiccircle.entity.Theme;
import life.mytopiccircle.entity.Topic;
import life.mytopiccircle.service.TopicCircleService;
import life.playTogether.entity.Group;
import life.playTogether.entity.GroupDynamic;
import life.playTogether.service.GroupService;
import life.taoyu.entity.Goods;
import life.taoyu.service.TaoyuService;
import zzu.util.Panduanstr;

public class UpdateImgToDB  implements Runnable{

	BlockingQueue BQuseimg;
	Object  S;//接收注解service对象。商品，话题圈，一起玩
	public UpdateImgToDB(BlockingQueue BQmadeimg,Object  TCS) {
	synchronized (this) {
		
		this.BQuseimg=BQmadeimg;
		this.S=TCS;
	}
	}
	
	@Override
	synchronized public void run() {
		// TODO Auto-generated method stub
		try {
			boolean ok=true;
			while(ok){
			Module m=BQuseimg.take();//阻塞队列中取出对象
			System.out.println("要保存的图片名="+m);
			  switch (m.getIdentity()) {//判断是哪个模块的图片需要更新
			case "goods":
				updateGoodsImg(m.getImgname(),m.getId());
				ok=false;
				break;

			case "theme":
				updateThemeImg(m.getImgname(),m.getId());
				ok=false;
				break;
				
			case "topic":
				updateTopicImg(m.getImgname(),m.getId());
				ok=false;
				break;
			case "group":
				updateGroupImg(m.getImgname(),m.getId());
				ok=false;
				break;
			case "groupDynamic":
				updateGroupDynamicImg(m.getImgname(),m.getId());
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
	//更新群组动态说说的图片
	private void updateGroupDynamicImg(List<String> strlist, String id) {
		Integer dynamicId=Integer.valueOf(id);
		GroupDynamic GD=new GroupDynamic();
		GD.setDynamicId(dynamicId);
		if(strlist.size()!=0){
			Panduanstr p=new Panduanstr();
			String str=p.pinjie(strlist);
			GD.setTalkImg(str);
			boolean b=((GroupService) S).UpdateGroupDynamic(GD);
			System.out.println("上传群组动态说说图片="+b);
		}
	}

	//更新一起玩群组图片
	private void updateGroupImg(List<String> strlist, String id) {
		Integer groupId=Integer.valueOf(id);
		Group g=new Group();
		g.setGroupId(groupId);
		if(strlist.size()!=0){
			Panduanstr p=new Panduanstr();
			String str=p.pinjie(strlist);
			g.setPicture(str);
			boolean b=((GroupService) S).UpdateGroup(g);
			System.out.println("上传群组图片="+b);
		}
		
	}

	//更新话题图片到数据库
	public  boolean updateTopicImg(List<String> strlist,String TopicId){
		boolean isSuccessful=false;
		
		Integer i2=Integer.parseInt(TopicId);
		Topic topic=new Topic();
		topic.setTopicId(i2);
		if(strlist.size()!=0){
			Panduanstr p=new Panduanstr();
			String str=p.pinjie(strlist);
			topic.setTopicImg(str); 
		isSuccessful=((TopicCircleService) S).updateTopic(topic);
		
		}
		return isSuccessful;
	}
	//更新主题图片到数据库
	public boolean updateThemeImg(List<String> strlist,String ThemeId){
		boolean isSuccessful=false;
		
		Integer i=Integer.parseInt(ThemeId);
		  Theme t=new Theme();t.setThemeId(i);
		if(strlist.size()!=0){ t.setThemeImg(strlist.get(0)); 
		System.out.println("theme:"+t);
		isSuccessful=((TopicCircleService) S).updateTheme(t);
		
		           }
		return isSuccessful;
	}
	//更新商品图片到数据库
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

	
}
