package life.mytopiccircle.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;

import life.mytopiccircle.entity.Topic;
import life.mytopiccircle.moudledriver.UserTopicComment;
import life.mytopiccircle.service.TopicCircleService;
import zzu.themb.ThembModuel;
import zzu.themb.ThembRecord;
import zzu.util.Getjson;
import zzu.util.Returndata;

@Transactional
@Component(value = "TopicCommentAction")
@Scope(value = "prototype")
public class TopicCommentAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String TopicId, TopicComment, SessionID, action, ThumbNum;

	@Autowired
	TopicCircleService TCS;

	@Override
	public String execute() throws Exception {
             System.out.println("action="+action+"ThumbNum="+ThumbNum+"TopicId="+TopicId);
		if (action == null || TopicId == null || TopicId.isEmpty()) {
			System.out.println("话题评论action或TopicId为空" );
			Returndata.returnboolean(false);
		}
		System.out.println("TopicId=" + TopicId + ",TopicComment=" + TopicComment + ",SessionID=" + SessionID
				+ ",action=" + action + "ThumbNum=" + ThumbNum);
		Integer topicId = Integer.valueOf(TopicId);
		if (action.equals("发布话题评论")) {
			boolean isSuccessful = TCS.PublishTopicComment(topicId, TopicComment, SessionID);
			Returndata.returnboolean(isSuccessful);
		} else if (action.equals("话题点赞")) {
			boolean isSuccessful=false;
			List<ThembModuel> list= ThembRecord.getvalue(SessionID);
			if(list!=null){
				System.out.println("用户话题点赞查询hashmap中是否有记录");
				for (ThembModuel thembModuel : list) {
					if(thembModuel.getAction().equals("话题点赞") && thembModuel.getId().equals(topicId.toString())){
						isSuccessful=true;
						System.out.println("hashmap中查到有记录，直接返回true");
					}
				}
			}else{
			Topic topic = new Topic();
			topic.setTopicId(topicId);
			Integer thumbnum = Integer.valueOf(ThumbNum);
			topic.setTopicThumbCount(thumbnum);
			 isSuccessful = TCS.updateTopic(topic,SessionID);
			 //记录到hashmap
			 ThembModuel tm=ThembModuel.getThembModuel(action, topicId.toString());
			 list=new ArrayList<>();
			 list.add(tm);
			 ThembRecord.addkeyvalue(SessionID, list);
			 System.out.println("话题点赞查询时数据库是否点赞，并记录到hashmap");
			}
			Returndata.returnboolean(isSuccessful);

		} else if (action.equals("查询话题评论")) {
			List<UserTopicComment> UTClist = TCS.queryTopicComment(topicId);
			System.out.println(UTClist);
			Getjson g = new Getjson();
			Returndata.returndata(g.Topicgetjsonarray(UTClist, action));
		}

		return null;
	}

	public String getTopicId() {
		return TopicId;
	}

	public void setTopicId(String topicId) {
		TopicId = topicId;
	}

	public String getTopicComment() {
		return TopicComment;
	}

	public void setTopicComment(String topicComment) {
		TopicComment = topicComment;
	}

	public String getSessionID() {
		return SessionID;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getThumbNum() {
		return ThumbNum;
	}

	public void setThumbNum(String thumbNum) {
		ThumbNum = thumbNum;
	}
}
