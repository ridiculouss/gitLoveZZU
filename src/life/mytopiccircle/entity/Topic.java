package life.mytopiccircle.entity;

import java.util.HashSet;
import java.util.Set;

import persionalCenter.entity.User;

public class Topic {

	private Integer TopicId  ;//  话题id
	private String TopicTitle;// 话题标题
	private String TopicImg;//   话题图片，多张
	private String TopicText;// 话题文字内容
	private int TopicCommentCount ;//话题评论量
	private int TopicThumbCount ;//话题点赞量
	private String date;
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	private Theme theme;
	private User user;
	private Set<TopicComment> settopiccomment=new HashSet<TopicComment>();//使用set集合表示topicComment类
	
	public Set<TopicComment> getSettopiccomment() {
		return settopiccomment;
	}

	public void setSettopiccomment(Set<TopicComment> settopiccomment) {
		this.settopiccomment = settopiccomment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getTopicId() {
		return TopicId;
	}

	public void setTopicId(Integer topicId) {
		TopicId = topicId;
	}

	public String getTopicTitle() {
		return TopicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		TopicTitle = topicTitle;
	}

	public String getTopicImg() {
		return TopicImg;
	}

	public void setTopicImg(String topicImg) {
		TopicImg = topicImg;
	}

	public String getTopicText() {
		return TopicText;
	}

	public void setTopicText(String topicText) {
		TopicText = topicText;
	}

	public int getTopicCommentCount() {
		return TopicCommentCount;
	}

	public void setTopicCommentCount(int topicCommentCount) {
		TopicCommentCount = topicCommentCount;
	}

	public int getTopicThumbCount() {
		return TopicThumbCount;
	}

	public void setTopicThumbCount(int topicThumbCount) {
		TopicThumbCount = topicThumbCount;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	@Override
	public String toString() {
		return "Topic [TopicId=" + TopicId + ", TopicTitle=" + TopicTitle + ", TopicImg=" + TopicImg + ", TopicText="
				+ TopicText + ", TopicCommentCount=" + TopicCommentCount + ", TopicThumbCount=" + TopicThumbCount +"date="+date+ "]";
	}
}
