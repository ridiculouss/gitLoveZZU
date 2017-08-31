package life.mytopiccircle.entity;

import java.util.HashSet;
import java.util.Set;

import persionalCenter.entity.User;

public class Topic {

	private Integer TopicId  ;//  ����id
	private String TopicTitle;// �������
	private String TopicImg;//   ����ͼƬ������
	private String TopicText;// ������������
	private int TopicCommentCount ;//����������
	private int TopicThumbCount ;//���������
	private String ThembUser;//��¼������
	private boolean Thembed;//�Ƿ��ѵ���
	private String date;
	

	private Theme theme;
	private User user;
	
	public String getThembUser() {
		return ThembUser;
	}

	public void setThembUser(String thembUser) {
		ThembUser = thembUser;
	}

	public boolean isThembed() {
		return Thembed;
	}

	public void setThembed(boolean thembed) {
		Thembed = thembed;
	}

	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	private Set<TopicComment> settopiccomment=new HashSet<TopicComment>();//ʹ��set���ϱ�ʾtopicComment��
	
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
				+ TopicText + ", TopicCommentCount=" + TopicCommentCount + ", TopicThumbCount=" + TopicThumbCount
				+ ", ThembUser=" + ThembUser + ", Thembed=" + Thembed + ", date=" + date + "]";
	}
}
