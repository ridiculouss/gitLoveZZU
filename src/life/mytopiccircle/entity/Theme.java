package life.mytopiccircle.entity;

import java.util.HashSet;
import java.util.Set;

import persionalCenter.entity.User;

public class Theme {
	private Integer ThemeId; // 主题id
	private String ThemeTitle; // 标题
	private String ThemeImg; // 主题图片只有一张，但也用List集合发送	
	private int TopicCount; // 拥有的话题数量
	private User user;
	private Set<Topic> settopic=new HashSet<Topic>();//使用set集合表示topic类
	
	public Set<Topic> getSettopic() {
		return settopic;
	}

	public void setSettopic(Set<Topic> settopic) {
		this.settopic = settopic;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getThemeId() {
		return ThemeId;
	}

	public void setThemeId(Integer themeId) {
		ThemeId = themeId;
	}

	public String getThemeTitle() {
		return ThemeTitle;
	}

	public void setThemeTitle(String themeTitle) {
		ThemeTitle = themeTitle;
	}

	public String getThemeImg() {
		return ThemeImg;
	}

	public void setThemeImg(String themeImg) {
		ThemeImg = themeImg;
	}

	public int getTopicCount() {
		return TopicCount;
	}

	public void setTopicCount(int topicCount) {
		TopicCount = topicCount;
	}

	@Override
	public String toString() {
		return "Theme [ThemeId=" + ThemeId + ", ThemeTitle=" + ThemeTitle + ", ThemeImg=" + ThemeImg + ", TopicCount="
				+ TopicCount + "]";
	}

}
