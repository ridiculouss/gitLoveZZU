package life.playTogether.entity;

import persionalCenter.entity.User;

public class GroupDynamicComment {

	private Integer groupDynamicCommentId;// 动态评论id
	private String comment;// 评论
	private String date;// 评论时间
	private GroupDynamic groupDynamic;
	private User user;

	public GroupDynamic getGroupDynamic() {
		return groupDynamic;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setGroupDynamic(GroupDynamic groupDynamic) {
		this.groupDynamic = groupDynamic;
	}

	public Integer getGroupDynamicCommentId() {
		return groupDynamicCommentId;
	}

	public void setGroupDynamicCommentId(Integer groupDynamicCommentId) {
		this.groupDynamicCommentId = groupDynamicCommentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "GroupDynamicComment [groupDynamicCommentId=" + groupDynamicCommentId + ", comment=" + comment
				+ ", date=" + date + "]";
	}

}
