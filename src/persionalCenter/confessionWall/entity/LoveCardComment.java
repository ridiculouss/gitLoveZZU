package persionalCenter.confessionWall.entity;

import persionalCenter.entity.User;

public class LoveCardComment {

	private Integer LoveCardCommentId;
	private String commentContent;
	private String date;
	private LoveCard lovecard;
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LoveCard getLovecard() {
		return lovecard;
	}
	public void setLovecard(LoveCard lovecard) {
		this.lovecard = lovecard;
	}
	public Integer getLoveCardCommentId() {
		return LoveCardCommentId;
	}
	public void setLoveCardCommentId(Integer loveCardCommentId) {
		LoveCardCommentId = loveCardCommentId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "LoveCardComment [LoveCardCommentId=" + LoveCardCommentId + ", commentContent=" + commentContent
				+ ", date=" + date + "]";
	}
	
}
