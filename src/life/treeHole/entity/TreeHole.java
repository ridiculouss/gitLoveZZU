package life.treeHole.entity;

import java.util.HashSet;
import java.util.Set;

import persionalCenter.entity.User;

public class TreeHole{
	private Integer treeHoleId;//表id
	private String treeHoleContent;//树洞内容
	private int thembCount;//点赞量
	private int commentCount;//评论量
	private String date;
	private String campus;//校区
	private User user;
	private Set<TreeHoleComment> settreeholeComment=new HashSet<TreeHoleComment>();//使用set集合表示UserInfo
	
	public Set<TreeHoleComment> getSettreeholeComment() {
		return settreeholeComment;
	}
	public void setSettreeholeComment(Set<TreeHoleComment> settreeholeComment) {
		this.settreeholeComment = settreeholeComment;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getTreeHoleId() {
		return treeHoleId;
	}
	public void setTreeHoleId(Integer treeHoleId) {
		this.treeHoleId = treeHoleId;
	}
	public String getTreeHoleContent() {
		return treeHoleContent;
	}
	public void setTreeHoleContent(String treeHoleContent) {
		this.treeHoleContent = treeHoleContent;
	}
	public int getThembCount() {
		return thembCount;
	}
	public void setThembCount(int thembCount) {
		this.thembCount = thembCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	@Override
	public String toString() {
		return "TreeHole [treeHoleId=" + treeHoleId + ", treeHoleContent=" + treeHoleContent + ", thembCount="
				+ thembCount + ", commentCount=" + commentCount + ", date=" + date + ", campus=" + campus + "]";
	}

	
}
