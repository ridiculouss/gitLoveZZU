package persionalCenter.confessionWall.entity;

import java.util.HashSet;
import java.util.Set;

import life.treeHole.entity.TreeHoleComment;

public class LoveCard {

	private Integer loveCardId;
	private String senderName;
	private String lovedName;
	private String loveContent;
	private int thembCount;
	private String date;
	private String search;
	private Set<LoveCardComment> setloveCardComment=new HashSet<LoveCardComment>();//使用set集合表示LoveCardComment
	
	
	public Set<LoveCardComment> getSetloveCardComment() {
		return setloveCardComment;
	}
	public void setSetloveCardComment(Set<LoveCardComment> setloveCardComment) {
		this.setloveCardComment = setloveCardComment;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public Integer getLoveCardId() {
		return loveCardId;
	}
	public void setLoveCardId(Integer loveCardId) {
		this.loveCardId = loveCardId;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getLovedName() {
		return lovedName;
	}
	public void setLovedName(String lovedName) {
		this.lovedName = lovedName;
	}
	public String getLoveContent() {
		return loveContent;
	}
	public void setLoveContent(String loveContent) {
		this.loveContent = loveContent;
	}
	public int getThembCount() {
		return thembCount;
	}
	public void setThembCount(int thembCount) {
		this.thembCount = thembCount;
	}
	@Override
	public String toString() {
		return "LoveCard [loveCardId=" + loveCardId + ", senderName=" + senderName + ", lovedName=" + lovedName
				+ ", loveContent=" + loveContent + ", thembCount=" + thembCount + ", date=" + date + "]";
	}
	
	
}
