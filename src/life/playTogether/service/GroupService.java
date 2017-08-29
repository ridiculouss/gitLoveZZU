package life.playTogether.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.secure.internal.StandardJaccServiceImpl;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import life.playTogether.entity.Group;
import life.playTogether.entity.GroupDynamic;
import life.playTogether.entity.GroupDynamicComment;
import life.playTogether.modeldriver.UserDynamic;
import life.playTogether.modeldriver.UserDynamicComment;
import life.playTogether.modeldriver.UserGroupTalkImg;
import life.taoyu.dao.Dao_taoyu;
import persionalCenter.dao.Dao;
import persionalCenter.entity.User;
import persionalCenter.entity.UserInfo;
import persionalCenter.service.UserService;
import zzu.util.GetDate;
import zzu.util.Judge_character;
import zzu.util.Panduanstr;

@Transactional
@Component(value="GroupService")
@Scope(value = "prototype")
public class GroupService {
	@Resource(name = "user_Dao")
	private Dao dao;
	@Resource(name = "taoyuDao")
	private Dao_taoyu TDao;
	@Resource(name="User_Service")
	private UserService userService;
	
	Panduanstr p=new Panduanstr();
//创建群组
	public String CreatGroup(Group g, String SessionID) {
		String sql="from User where SessionID=?";
		List<User> user=dao.query(sql, SessionID);
		if(user.size()==0){System.err.println("创建群用户未检索到");return null;}
		g.setDate(GetDate.GetNowDate());
		g.setMember(user.get(0).getUid().toString());
		g.setUser(user.get(0));
		
		Serializable id= dao.save(g);
		
		return id.toString();
		
	}
//更新群组
	public boolean UpdateGroup(Group g){
		String sql="from Group where groupId =?";
		List<Group> group=dao.query(sql, g.getGroupId()); 
		group.get(0).setPicture(g.getPicture());
		dao.update(group.get(0));
		return true;
		
	}
	//发表说说
	public String PublishTalk(GroupDynamic gd, String sessionID, Integer groupId) {
		String sql="from User where SessionID=?";
		List<User> user=dao.query(sql, sessionID);
		sql="from Group where groupId=?";
		List<Group> group=dao.query(sql, groupId);
		if(user.size()==0 || group.size()==0){System.err.println("发表说说用户或群组未检索到");return null;}
		gd.setDate(GetDate.GetNowDate());
		gd.setThembUser("0");
		gd.setGroup(group.get(0));
		gd.setUser(user.get(0));
		Serializable id=dao.save(gd);
		
		return id.toString();
	}
	//更新群组动态说说
		public boolean UpdateGroupDynamic(GroupDynamic gd){
			String sql="from GroupDynamic where dynamicId =?";
			List<GroupDynamic> groupDynamic=dao.query(sql, gd.getDynamicId()); 
			groupDynamic.get(0).setTalkImg(gd.getTalkImg());
			dao.update(groupDynamic.get(0));
			return true;
		}
//查询群组 values=null则没有条件，num=null则不分页查询。具体看底层	TDao.hqlquery实现	
		public <S> List<UserGroupTalkImg> QueryGroup(String sql,S values,Integer num) {
			List<UserGroupTalkImg> UGDlist=new ArrayList<UserGroupTalkImg>();
			List<Group> grouplist=TDao.hqlquery(sql, values, num);
			for (Group group : grouplist) {
				UserGroupTalkImg UGD=new UserGroupTalkImg();
				UGD.setGroup(group);//添加群组对象
			    Set<UserInfo> userinfo=group.getUser().getSetuserinfo();//获得群主信息
			    for (UserInfo userInfo2 : userinfo) {
					UGD.setUserinfo(userInfo2);//添加群主对象
				}
			    String Img="";
			    int count=0;
			    Set<GroupDynamic> GroupDynamic=group.getSetgroupDynamic();
			    for (GroupDynamic groupDynamic2 : GroupDynamic) {
			    	if(groupDynamic2.getTalkImg()!=null&& count<4){
			    	Img+=groupDynamic2.getTalkImg();
			    	count++;
			    	}
				}
			   String talkImg = null;
			   if(Img!=null){
			    String[] img=Img.split("ZZU");
			    if(img!=null&&img.length>4){Img=img[0]+"ZZU"+img[1]+"ZZU"+img[2]+"ZZU"+img[3];}
			    talkImg=Img;      //动态的图片
			   }
			    UGD.setTalkImg(talkImg);//添加说说图片
			    
			
			    UGDlist.add(UGD);//模型对象添加到模型集合
			}
			for(UserGroupTalkImg ugd:UGDlist){
				if(ugd.getGroup().getMember()!=null){
				String[] memberinfo=ugd.getGroup().getMember().split("ZZU");
				List<UserInfo> userinfolist=userService.BatchQueryUserInfo(memberinfo);
				ugd.setMemberInfo(userinfolist);
				}
			}
			return UGDlist;
		}
//不分页查询所有群组
		
//分页查询群动态
		public List<UserDynamic> QueryDynamic(Integer id, int num,String SessionID) {
			List<UserDynamic> UDlist=new ArrayList<UserDynamic>();
	          String sql="from GroupDynamic where GroupGroupDynamic_fkey=?";
	          List<GroupDynamic>  groupDynamiclist=TDao.hqlquery(sql, id, num);
              for (GroupDynamic groupDynamic : groupDynamiclist) {
						UserDynamic ud=new UserDynamic();//存放动态发布人和动态信息对象
						Set<UserInfo> userinfoSet=groupDynamic.getUser().getSetuserinfo();//获取动态发布人信息
						for (UserInfo userInfo : userinfoSet) {
							ud.setUserinfo(userInfo);
							ud.setGroupDynamic(groupDynamic);
						}
						UDlist.add(ud);
				}
              //查询用户
              User user=userService.queryUser(SessionID);
              if(user!=null){
            	  for(UserDynamic ud:UDlist){
            		  String UserId=ud.getGroupDynamic().getThembUser();
            		  if(UserId ==null){break;}
            		  String[] uid=UserId.split("#");
            			  for (String d : uid) {
							  if(user.getUid().toString().equals(d)){ud.getGroupDynamic().setThembed(true);}
						} 
            	  }
            	  System.out.println("查询动态是否被该用户点赞");
              }else{System.out.println("用户未发来SesionID,对所查数据不检查是否点赞");}
			return UDlist;
		}
//发表动态的评论
		public boolean PublishDynamicComment(GroupDynamicComment GDC, String SessionID, Integer dynamicId) {
		String sql="from User where SessionID=?";
		List<User> user=dao.query(sql, SessionID);
		sql="from GroupDynamic where dynamicId=?";
		List<GroupDynamic> groupDynamic=dao.query(sql, dynamicId);
		
		if(user.size()==0|| groupDynamic.size()==0){System.err.println("发表动态的评论用户或动态未检索到");return false;}
		GDC.setUser(user.get(0));
		GDC.setGroupDynamic(groupDynamic.get(0));
		GDC.setDate(GetDate.GetNowDate());
		Serializable id=dao.save(GDC);
		groupDynamic.get(0).setCommentCount(groupDynamic.get(0).getCommentCount()+1);
		dao.update(groupDynamic.get(0));//动态的评论量+1
			if(id!=null){return true;}
			return false;
		}
//查询动态评论
		public List<UserDynamicComment> QueryDynamicComment(Integer id) {
			List<UserDynamicComment> UDClist=new ArrayList<UserDynamicComment>();
			String sql="from GroupDynamicComment where GroupDynamicComment_fkey=? order by groupDynamicCommentId desc";
			List<GroupDynamicComment> GroupDynamicComment=dao.query(sql, id);
			for (GroupDynamicComment groupDynamicComment : GroupDynamicComment) {
				UserDynamicComment UDC=new UserDynamicComment();
				Set<UserInfo> userinfo= groupDynamicComment.getUser().getSetuserinfo();
				for (UserInfo userInfo2 : userinfo) {
					UDC.setUserinfo(userInfo2);
					UDC.setDynamicComment(groupDynamicComment);
					UDClist.add(UDC);
				}
			}
			return UDClist;
			
		}
//加入群组
		public boolean JionGroup(Integer id, String sessionID) {
			String	sql="from User where SessionID=?";
			List<User> user=dao.query(sql, sessionID);
			 sql="from Group where groupId=?";
				List<Group> grouplist=dao.query(sql, id);
			for (Group group: grouplist) {
				String userId=user.get(0).getUid().toString();
				String[] existeduid=group.getMember().split("ZZU");
				boolean b=Judge_character.JudgeMember(userId, existeduid);
				if(b){System.err.println("用户已是该群组成员");return false;}
				group.setMember(group.getMember()+"ZZU"+userId);
				dao.update(group);
				System.out.println("加入群组成功");
				return true;
			}
			return false;
		}
//查询我加入的群组
		public List<UserGroupTalkImg> QueryMyjoinedGroup(String sessionID) {
			List<Group> newGrouplist=new ArrayList<Group>();
			String sql="from User where SessionID=?";
			List<User> user=dao.query(sql, sessionID);
			if(user.size()==0){System.err.println("查询我加入的群组用户未检索到");return null;}
			sql="from Group where groupId!=?";
			List<Group> grouplist=dao.query(sql, 0);
			for (Group group : grouplist) {
				String[] id=group.getMember().split("ZZU");
				id[0]="0";
				for (String s : id) {
					if(user.get(0).getUid().toString().equals(s)){
						newGrouplist.add(group);
					}
				}
			}
			List<UserGroupTalkImg> UGDlist=	buildData(newGrouplist);
			return UGDlist;
		}
//组织需要返回的群组数据格式
		public List<UserGroupTalkImg> buildData(List<Group> grouplist){
			List<UserGroupTalkImg> UGDlist=new ArrayList<UserGroupTalkImg>();
			for (Group group : grouplist) {
				UserGroupTalkImg UGD=new UserGroupTalkImg();
				UGD.setGroup(group);//添加群组对象
			    Set<UserInfo> userinfo=group.getUser().getSetuserinfo();//获得群主信息
			    for (UserInfo userInfo2 : userinfo) {
					UGD.setUserinfo(userInfo2);//添加群主对象
				}
			    String Img="";
			    int count=0;
			    Set<GroupDynamic> GroupDynamic=group.getSetgroupDynamic();
			    for (GroupDynamic groupDynamic2 : GroupDynamic) {
			    	if(groupDynamic2.getTalkImg()!=null&& count<4){
			    	Img+=groupDynamic2.getTalkImg();
			    	count++;
			    	}
				}
			   String talkImg = null;
			   if(Img!=null){
			    String[] img=Img.split("ZZU");
			    if(img!=null&&img.length>4){Img=img[0]+"ZZU"+img[1]+"ZZU"+img[2]+"ZZU"+img[3];}
			    talkImg=Img;      //动态的图片
			   }
			    UGD.setTalkImg(talkImg);//添加说说图片
			    
			
			    UGDlist.add(UGD);//模型对象添加到模型集合
			}
			for(UserGroupTalkImg ugd:UGDlist){
				if(ugd.getGroup().getMember()!=null){
				String[] memberinfo=ugd.getGroup().getMember().split("ZZU");
				List<UserInfo> userinfolist=userService.BatchQueryUserInfo(memberinfo);
				ugd.setMemberInfo(userinfolist);//设置成员信息
				}
			}
			return UGDlist;
		}
//搜索群组
		public List<UserGroupTalkImg> SearchGroup(String search) {
			String sql="from Group where name like ? or label like ? ";
			String[] values={"%"+search+"%","%"+search+"%"};
			List<Group> group=dao.query(sql, values);
			List<UserGroupTalkImg> UGDlist=buildData(group);
			return UGDlist;
			
		}
//退出群组
		public boolean QuitGroup(Integer groupId, String sessionID) {
			String sql="from User where SessionID=?";
			List<User> user=dao.query(sql, sessionID);
			if(user.size()==0){System.err.println("退出群组用户未检索到");return false;}
			sql="from Group where groupId =?";
			List<Group> grouplist=dao.query(sql, groupId);
			for (Group group : grouplist) {
				String[] id=group.getMember().split("ZZU");
				List<String> idlist=new ArrayList<String>(Arrays.asList(id));
				if(id.length==1){dao.delete(group);//群里只有群主，并且群主要退群，则删除群
				System.out.println("群主退出群组群里没人了删除群组");
				}else if(user.get(0).getUid().toString().equals(idlist.get(0))){//如果是群主退群，把第二个人设为群主
						idlist.remove(0);//member字段里把群主id去掉
						User user2=userService.queryUser(Integer.valueOf(id[1]));
						group.setUser(user2);
						String member=p.pinjie(idlist);
						group.setMember(member);
						dao.update(group);
						System.out.println("群主退出群组");
				}else if(user.size()!=0){//其他成员退群,则删除member中他的id
					List<String> newmember=new ArrayList<String>();
					for (String s : idlist) {
						if(!user.get(0).getUid().toString().equals(s)){newmember.add(s);}
					}
					String member=p.pinjie(newmember);
					group.setMember(member);
					dao.update(group);
					System.out.println("其他成员退出群组");
				}else{System.err.println("用户未检索到");return false;}
			}
			return true;
			
		}
//动态点赞		
		public boolean ThembDynamic(Integer id, String sessionID) {
			User user=userService.queryUser(sessionID);
			String sql="from GroupDynamic where dynamicId=?";
			List<GroupDynamic> dynamic=dao.query(sql, id);
			for (GroupDynamic groupDynamic : dynamic) {
				String[] thembUser=groupDynamic.getThembUser().split("#");				
				for (String string : thembUser) {
					if(user.getUid().toString().equals(string)){ return false;}
				}
				//点赞量+1
				groupDynamic.setThembCount(groupDynamic.getThembCount()+1);
				//记录点赞人id				
				groupDynamic.setThembUser(groupDynamic.getThembUser()+"#"+user.getUid().toString());
				dao.update(groupDynamic);
				return true;
			}
			return false;
		}
//判断是否是群主
		public boolean isGroupOwner(Integer id, String sessionID) {
			String sql="from User where SessionID=?";
			List<User> user=dao.query(sql, sessionID);
			for (User user2 : user) {
				Set<Group> group=user2.getSetgroup();
				for (Group group2 : group) {
					if(group2.getGroupId()==id)return true;
				}
			}
			return false;
			
		}
}
