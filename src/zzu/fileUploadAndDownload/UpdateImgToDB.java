package zzu.fileUploadAndDownload;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;

import life.mytopiccircle.entity.Topic;
import life.mytopiccircle.service.TopicCircleService;
import zzu.util.Panduanstr;
@Transactional
@Component(value="UpdateImgToDB")
@Scope(value = "prototype")
public class UpdateImgToDB extends ActionSupport{
	@Autowired
	private TopicCircleService  TCS;
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
		isSuccessful=TCS.updateTopic(topic);
		
		}
		return isSuccessful;
	}
}
