package life.playTogether.modeldriver;

import life.playTogether.entity.GroupDynamicComment;
import persionalCenter.entity.UserInfo;

public class UserDynamicComment {
	private UserInfo userinfo;//��̬��������Ϣ
	private GroupDynamicComment dynamicComment;//������Ϣ
	public UserInfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public GroupDynamicComment getDynamicComment() {
		return dynamicComment;
	}
	public void setDynamicComment(GroupDynamicComment dynamicComment) {
		this.dynamicComment = dynamicComment;
	}
	@Override
	public String toString() {
		return "UserDynamicComment [userinfo=" + userinfo + ", dynamicComment=" + dynamicComment + "]";
	}

}
