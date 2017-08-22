package life.playTogether.entity;

import java.util.HashSet;
import java.util.Set;

import persionalCenter.entity.User;

public class GroupDynamic {
	private Integer dynamicId;//群动态id
	private String talk;//说说
	private String	talkImg;//说说的图片
	private String	thembCount;//点赞量
	private String	commentCount;//评论量
	private String	date;//发表时间
	private Group group;
	private User user;
	private Set<GroupDynamicComment> setgroupDynamicComment=new HashSet<GroupDynamicComment>();//使用set集合表示UserInfo
	
	public Set<GroupDynamicComment> getSetgroupDynamicComment() {
		return setgroupDynamicComment;
	}
	public void setSetgroupDynamicComment(Set<GroupDynamicComment> setgroupDynamicComment) {
		this.setgroupDynamicComment = setgroupDynamicComment;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public Integer getDynamicId() {
		return dynamicId;
	}
	public void setDynamicId(Integer dynamicId) {
		this.dynamicId = dynamicId;
	}
	public String getTalk() {
		return talk;
	}
	public void setTalk(String talk) {
		this.talk = talk;
	}
	public String getTalkImg() {
		return talkImg;
	}
	public void setTalkImg(String talkImg) {
		this.talkImg = talkImg;
	}
	public String getThembCount() {
		return thembCount;
	}
	public void setThembCount(String thembCount) {
		this.thembCount = thembCount;
	}
	public String getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "GroupDynamic [dynamicId=" + dynamicId + ", talk=" + talk + ", talkImg=" + talkImg + ", thembCount="
				+ thembCount + ", commentCount=" + commentCount + ", date=" + date + "]";
	}
	
}
