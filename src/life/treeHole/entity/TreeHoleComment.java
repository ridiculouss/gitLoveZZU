package life.treeHole.entity;

import persionalCenter.entity.User;

public class TreeHoleComment implements Comparable<TreeHoleComment>{
	private Integer treeHoleCommentId;//±íid
	private String CommentContent;//ÆÀÂÛÄÚÈÝ
	private String date;
	private User user;
	private TreeHole treehole;
	public TreeHole getTreehole() {
		return treehole;
	}
	public void setTreehole(TreeHole treehole) {
		this.treehole = treehole;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getTreeHoleCommentId() {
		return treeHoleCommentId;
	}
	public void setTreeHoleCommentId(Integer treeHoleCommentId) {
		this.treeHoleCommentId = treeHoleCommentId;
	}
	public String getCommentContent() {
		return CommentContent;
	}
	public void setCommentContent(String commentContent) {
		CommentContent = commentContent;
	}

	@Override
	public String toString() {
		return "TreeHoleComment [treeHoleCommentId=" + treeHoleCommentId + ", CommentContent=" + CommentContent
				+ ", date=" + date + "]";
	}
	@Override
	public int compareTo(TreeHoleComment o) {
		// TODO Auto-generated method stub
		return this.getTreeHoleCommentId().compareTo(o.getTreeHoleCommentId());
	}
	
}
