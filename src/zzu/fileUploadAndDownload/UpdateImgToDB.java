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
	Object  S;//����ע��service������Ʒ������Ȧ��һ����
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
			Module m=BQuseimg.take();//����������ȡ������
			System.out.println("Ҫ�����ͼƬ��="+m);
			  switch (m.getIdentity()) {//�ж����ĸ�ģ���ͼƬ��Ҫ����
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
			System.out.println("�˳���whileѭ��");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//����Ⱥ�鶯̬˵˵��ͼƬ
	private void updateGroupDynamicImg(List<String> strlist, String id) {
		Integer dynamicId=Integer.valueOf(id);
		GroupDynamic GD=new GroupDynamic();
		GD.setDynamicId(dynamicId);
		if(strlist.size()!=0){
			Panduanstr p=new Panduanstr();
			String str=p.pinjie(strlist);
			GD.setTalkImg(str);
			boolean b=((GroupService) S).UpdateGroupDynamic(GD);
			System.out.println("�ϴ�Ⱥ�鶯̬˵˵ͼƬ="+b);
		}
	}

	//����һ����Ⱥ��ͼƬ
	private void updateGroupImg(List<String> strlist, String id) {
		Integer groupId=Integer.valueOf(id);
		Group g=new Group();
		g.setGroupId(groupId);
		if(strlist.size()!=0){
			Panduanstr p=new Panduanstr();
			String str=p.pinjie(strlist);
			g.setPicture(str);
			boolean b=((GroupService) S).UpdateGroup(g);
			System.out.println("�ϴ�Ⱥ��ͼƬ="+b);
		}
		
	}

	//���»���ͼƬ�����ݿ�
	public  boolean updateTopicImg(List<String> strlist,String TopicId){
		boolean isSuccessful=false;
		
		Integer i2=Integer.parseInt(TopicId);
		Topic topic=new Topic();
		topic.setTopicId(i2);
		if(strlist.size()!=0){
			Panduanstr p=new Panduanstr();
			String str=p.pinjie(strlist);
			topic.setTopicImg(str); 
		isSuccessful=((TopicCircleService) S).updateTopic(topic,null);
		
		}
		return isSuccessful;
	}
	//��������ͼƬ�����ݿ�
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
	//������ƷͼƬ�����ݿ�
	public boolean updateGoodsImg(List<String> strlist,String goods_id){
		boolean isSuccessful=false;
		
	        if(strlist==null) {return false;}  
	        Panduanstr p=new Panduanstr();
	        	String imageurl = p.pinjie(strlist);// ƴ�ӳ��ַ���
	        	Goods goods=new Goods();
	        	goods.setGoods_id(Integer.valueOf(goods_id));
	        	goods.setGimage(imageurl);
				((TaoyuService) S).updateGoods(goods);
	        
	 return  isSuccessful;      
	}

	
}
