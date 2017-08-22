package life.playTogether.modeldriver;

import life.playTogether.entity.GroupDynamic;
import persionalCenter.entity.UserInfo;

public class UserDynamic {

	private UserInfo userinfo;//动态发表人信息
	private GroupDynamic groupDynamic;//动态信息
	
	public UserInfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public GroupDynamic getGroupDynamic() {
		return groupDynamic;
	}
	public void setGroupDynamic(GroupDynamic groupDynamic) {
		this.groupDynamic = groupDynamic;
	}
	@Override
	public String toString() {
		return "UserDynamic [userinfo=" + userinfo + ", groupDynamic=" + groupDynamic + "]";
	}
	
}
