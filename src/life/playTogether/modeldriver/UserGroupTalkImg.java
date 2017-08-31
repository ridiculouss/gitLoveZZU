package life.playTogether.modeldriver;

import java.util.List;

import life.playTogether.entity.Group;
import life.playTogether.entity.GroupDynamic;
import persionalCenter.entity.UserInfo;

public class UserGroupTalkImg {

	private UserInfo userinfo;
	private Group group;
	private String talkImg;
	private  List<UserInfo> memberInfo;
	
	public List<UserInfo> getMemberInfo() {
		return memberInfo;
	}
	public void setMemberInfo(List<UserInfo> memberInfo) {
		this.memberInfo = memberInfo;
	}
	public UserInfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}

	public String getTalkImg() {
		return talkImg;
	}
	public void setTalkImg(String talkImg) {
		this.talkImg = talkImg;
	}
	@Override
	public String toString() {
		return "UserGroupTalkImg [userinfo=" + userinfo + ", group=" + group + ", talkImg=" + talkImg + ", memberInfo="
				+ memberInfo + "]";
	}
	
	}
