package life.mytopiccircle.moudledriver;

import java.util.Set;

import life.mytopiccircle.entity.Theme;
import persionalCenter.entity.User;
import persionalCenter.entity.UserInfo;

public class UserTheme {

	private UserInfo userinfo;
	private Theme theme;
	public UserInfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	@Override
	public String toString() {
		return "UserTheme [userinfo=" + userinfo + ", theme=" + theme + "]";
	}

	
}
