package life.mytopiccircle.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;

import life.mytopiccircle.entity.Topic;
import life.mytopiccircle.moudledriver.UserTopic;
import life.mytopiccircle.service.TopicCircleService;
import zzu.util.Getjson;
import zzu.util.Returndata;

@Transactional
@Component(value = "TopicAction")
@Scope(value = "prototype")
public class TopicAction extends ActionSupport {
	@Autowired
	TopicCircleService  TCS;
	String ThemeId,TopicTitle,TopicText,SessionID,action;
	
	@Override
	public String execute() throws Exception {
           if(action!=null && ThemeId!=null && !ThemeId.isEmpty()){
        	   Integer i=Integer.valueOf(ThemeId);
        	   if(action.equals("发布话题")){
        		   Topic t=new Topic();
        		   t.setTopicTitle(TopicTitle);
        		   t.setTopicText(TopicText);
        		
        		   
        		  String topicId= TCS.PublishTopic(i, t, SessionID);
        		  Returndata.returndata(topicId);
        		  
        	   }else if(action.equals("查询话题")){
        		   Getjson g=new Getjson();
        		   List<UserTopic>  topiclist=  TCS.queryTopic(i);
        		   Returndata.returndata(g.Topicgetjsonarray3(topiclist, action));
        	   }
        	   
           }
		return null;
	}
	//注入属性
	public void setThemeId(String themeId) {
		ThemeId = themeId;
	}
	public void setTopicTitle(String topicTitle) {
		TopicTitle = topicTitle;
	}
	public void setTopicText(String topicText) {
		TopicText = topicText;
	}
	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}
	public void setAction(String action) {
		this.action = action;
	}
}
