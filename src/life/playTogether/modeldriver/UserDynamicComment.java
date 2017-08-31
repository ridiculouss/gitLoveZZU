package life.playTogether.modeldriver;

import life.playTogether.entity.GroupDynamicComment;
import persionalCenter.entity.UserInfo;

public class UserDynamicComment {
	private UserInfo userinfo;//动态评论人信息
	private GroupDynamicComment dynamicComment;//评论信息
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
