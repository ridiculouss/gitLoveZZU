package life.mytopiccircle.entity;

import persionalCenter.entity.User;

public class TopicComment {

	private Integer TopicCommentId;// ÆÀÂÛid
	private String TopicComment;// ÆÀÂÛÄÚÈÝ
	
	private String date;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	private User user;
	private Topic topic;
	
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Integer getTopicCommentId() {
		return TopicCommentId;
	}
	public void setTopicCommentId(Integer topicCommentId) {
		TopicCommentId = topicCommentId;
	}
	public String getTopicComment() {
		return TopicComment;
	}
	public void setTopicComment(String topicComment) {
		TopicComment = topicComment;
	}

	@Override
	public String toString() {
		return "TopicComment [TopicCommentId=" + TopicCommentId + ", TopicComment=" + TopicComment
				+"date="+date+ "]";
	}
}
