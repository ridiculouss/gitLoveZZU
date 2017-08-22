package life.playTogether.entity;

import java.util.HashSet;
import java.util.Set;

import persionalCenter.entity.User;

public class Group {
	private Integer groupId;//表id
	private String name;//群名字
	private String	picture;//群图片
	private String	introduce;//群介绍
	private String	label;//群标签
	private String	member;//群成员
	private String	campus;//群成员
	private String	date;//创建时间
	
	private User user;
	private Set<GroupDynamic> setgroupDynamic=new HashSet<GroupDynamic>();//使用set集合表示
	
	
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public Set<GroupDynamic> getSetgroupDynamic() {
		return setgroupDynamic;
	}
	public void setSetgroupDynamic(Set<GroupDynamic> setgroupDynamic) {
		this.setgroupDynamic = setgroupDynamic;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Group [groupId=" + groupId + ", name=" + name + ", picture=" + picture + ", introduce=" + introduce
				+ ", label=" + label + ", member=" + member + ", date=" + date + "]";
	}
	
}
