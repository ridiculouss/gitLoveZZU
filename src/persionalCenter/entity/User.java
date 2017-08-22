package persionalCenter.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import life.mytopiccircle.entity.Theme;
import life.mytopiccircle.entity.Topic;
import life.mytopiccircle.entity.TopicComment;
import life.parTime.entity.PartTime;
import life.playTogether.entity.Group;
import life.playTogether.entity.GroupDynamic;
import life.playTogether.entity.GroupDynamicComment;
import life.taoyu.entity.Cart;
import life.taoyu.entity.Goods;
import life.taoyu.entity.Order;
import life.treeHole.entity.TreeHole;
import life.treeHole.entity.TreeHoleComment;
import persionalCenter.confessionWall.entity.LoveCardComment;



@Service(value="User")
public class User {

	//用户属性
	private Integer uid;
	private String phone;
	private String password;
	private String account;
	private String verification_code; //验证码
	private String SessionID;//身份ID
	private String SessionIDDate;//身份ID日期

	

	private Set<UserInfo> setuserinfo=new HashSet<UserInfo>();//使用set集合表示UserInfo
	private Set<Goods> setgoods=new HashSet<Goods>();//使用set集合表示Goods类
	private Set<Order> setorder=new HashSet<Order>();//使用set集合表示Order类
	private Set<Cart> setcart=new HashSet<Cart>();//使用set集合表示Cart类
	private Set<Theme> settheme=new HashSet<Theme>();//使用set集合表示Themen类
	private Set<Topic> settopic=new HashSet<Topic>();//使用set集合表示Topic类
	private Set<TopicComment> settopiccomment=new HashSet<TopicComment>();//使用set集合表示Topic类
	private Set<TreeHole> settreehole=new HashSet<TreeHole>();//使用set集合表示TreeHole类
	private Set<TreeHoleComment> settreeholeComment=new HashSet<TreeHoleComment>();//使用set集合表示TreeHole类
	private Set<LoveCardComment> setloveCardComment=new HashSet<LoveCardComment>();//使用set集合表示LoveCardComment类
	private Set<PartTime> setpartTime=new HashSet<PartTime>();//使用set集合表示PartTime类
	private Set<Group> setgroup=new HashSet<Group>();//使用set集合表示Group类
	private Set<GroupDynamic> setgroupDynamic=new HashSet<GroupDynamic>();//使用set集合表示GroupDynamic类
	private Set<GroupDynamicComment> setgroupDynamicComment=new HashSet<GroupDynamicComment>();//使用set集合表示GroupDynamic类

	
	public Set<GroupDynamicComment> getSetgroupDynamicComment() {
		return setgroupDynamicComment;
	}
	public void setSetgroupDynamicComment(Set<GroupDynamicComment> setgroupDynamicComment) {
		this.setgroupDynamicComment = setgroupDynamicComment;
	}
	public Set<GroupDynamic> getSetgroupDynamic() {
		return setgroupDynamic;
	}
	public void setSetgroupDynamic(Set<GroupDynamic> setgroupDynamic) {
		this.setgroupDynamic = setgroupDynamic;
	}
	public Set<Group> getSetgroup() {
		return setgroup;
	}
	public void setSetgroup(Set<Group> setgroup) {
		this.setgroup = setgroup;
	}
	public Set<PartTime> getSetpartTime() {
		return setpartTime;
	}
	public void setSetpartTime(Set<PartTime> setpartTime) {
		this.setpartTime = setpartTime;
	}
	public Set<LoveCardComment> getSetloveCardComment() {
		return setloveCardComment;
	}
	public void setSetloveCardComment(Set<LoveCardComment> setloveCardComment) {
		this.setloveCardComment = setloveCardComment;
	}
	public Set<TreeHoleComment> getSettreeholeComment() {
		return settreeholeComment;
	}
	public void setSettreeholeComment(Set<TreeHoleComment> settreeholeComment) {
		this.settreeholeComment = settreeholeComment;
	}
	public Set<TreeHole> getSettreehole() {
		return settreehole;
	}
	public void setSettreehole(Set<TreeHole> settreehole) {
		this.settreehole = settreehole;
	}
	public Set<TopicComment> getSettopiccomment() {
		return settopiccomment;
	}
	public void setSettopiccomment(Set<TopicComment> settopiccomment) {
		this.settopiccomment = settopiccomment;
	}
	public Set<Topic> getSettopic() {
		return settopic;
	}
	public void setSettopic(Set<Topic> settopic) {
		this.settopic = settopic;
	}
	public Set<Theme> getSettheme() {
		return settheme;
	}
	public void setSettheme(Set<Theme> settheme) {
		this.settheme = settheme;
	}
	public Set<Order> getSetorder() {
		return setorder;
	}
	public void setSetorder(Set<Order> setorder) {
		this.setorder = setorder;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSessionIDDate() {
		return SessionIDDate;
	}
	public void setSessionIDDate(String sessionIDDate) {
		SessionIDDate = sessionIDDate;
	}
	
	


	public Set<Cart> getSetcart() {
		return setcart;
	}
	public void setSetcart(Set<Cart> setcart) {
		this.setcart = setcart;
	}
	public String getSessionID() {
		return SessionID;
	}
	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}



		public Set<Goods> getSetgoods() {
		return setgoods;
	}
	public void setSetgoods(Set<Goods> setgoods) {
		this.setgoods = setgoods;
	}
	public Set<UserInfo> getSetuserinfo() {
		return setuserinfo;
	}
	public void setSetuserinfo(Set<UserInfo> setuserinfo) {
		this.setuserinfo = setuserinfo;
	}
	public String getVerification_code() {
		return verification_code;
	}
	public void setVerification_code(String verification_code) {
		this.verification_code = verification_code;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String pssword) {
		this.password = pssword;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "User[phone:"+phone+",password:"+password+"]";
	}
	
}
