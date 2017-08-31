package life.mytopiccircle.moudledriver;

import life.mytopiccircle.entity.TopicComment;
import persionalCenter.entity.UserInfo;

public class UserTopicComment {

	private UserInfo userinfo;
	private TopicComment topiccomment;
	public UserInfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public TopicComment getTopiccomment() {
		return topiccomment;
	}
	public void setTopiccomment(TopicComment topiccomment) {
		this.topiccomment = topiccomment;
	}
	@Override
	public String toString() {
		return "UserTopicComment [userinfo=" + userinfo + ", topiccomment=" + topiccomment + "]";
	}
	
}
