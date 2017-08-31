package life.mytopiccircle.moudledriver;

import life.mytopiccircle.entity.Topic;
import persionalCenter.entity.UserInfo;

public class UserTopic {

	private UserInfo userinfo;
	private Topic topic;
	public UserInfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	@Override
	public String toString() {
		return "UserTopic [userinfo=" + userinfo + ", topic=" + topic + "]";
	}
}
