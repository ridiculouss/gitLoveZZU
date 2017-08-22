package life.playTogether.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;

import life.playTogether.entity.Group;
import life.playTogether.modeldriver.UserGroupTalkImg;
import life.playTogether.service.GroupService;
import life.taoyu.dao.Dao_taoyu;
import persionalCenter.entity.User;
import persionalCenter.service.UserService;
import zzu.util.Getjson;
import zzu.util.Returndata;

@Transactional
@Component(value = "GroupAction")
@Scope(value = "prototype")
public class GroupAction extends ActionSupport{
	private String name,introduce,label,action,SessionID,groupId,campus,search;
	Integer num;
	@Autowired
	GroupService groupService;
	@Resource(name="User_Service")
	private UserService userService;
	
	@Override
	public String execute() throws Exception {
		if(action==null){System.err.println("群组action为空");return null;}
		System.out.println("action="+action+",num="+num);
		if(action.equals("创建群")){
			Group g=new Group();
			g.setName(name);
			g.setIntroduce(introduce);
			g.setLabel(label);
			g.setCampus(campus);
			String groupId=groupService.CreatGroup(g,SessionID);
			if(groupId!=null)System.out.println("创建群成功!");
			Returndata.returndata(groupId);
		}else if(action.equals("查询群组")){
			Integer n=Integer.valueOf(num);
			String sql="from Group order by groupId desc";
			String values=null; 
			List<UserGroupTalkImg>list=	groupService.QueryGroup(sql,values,n);
			if(list==null ||list.size()==0)System.out.println("查询群组失败或群组一个也没有");
			Returndata.returndata(Getjson.Generaljsonarray(list, action, new String[] {"user","setgroupDynamicComment" ,"setgroupDynamic"}));
		}else if(action.equals("加入群组")){
			Integer id=Integer.valueOf(groupId);
			boolean isSuccessful=groupService.JionGroup(id,SessionID);
			Returndata.returnboolean(isSuccessful);
		}else if(action.equals("查询我创建的群组")){
			User user=userService.queryUser(SessionID);
			Integer num=null;
			String sql="from Group where UserGroup_fkey=?";
			Integer values=user.getUid(); 
			List<UserGroupTalkImg>list=	groupService.QueryGroup(sql,values,num);
			if(list==null ||list.size()==0)System.out.println("查询我创建的群组失败或群组一个也没有");
			Returndata.returndata(Getjson.Generaljsonarray(list, action, new String[] {"user","setgroupDynamicComment" ,"setgroupDynamic"}));
		}else if(action.equals("查询我加入的群组")){
			List<UserGroupTalkImg> list=groupService.QueryMyjoinedGroup(SessionID);
			if(list==null ||list.size()==0)System.out.println("查询我加入的群组失败或群组一个也没有");
			Returndata.returndata(Getjson.Generaljsonarray(list, action, new String[] {"user","setgroupDynamicComment" ,"setgroupDynamic"}));
		}else if(action.equals("搜索群组")){
			List<UserGroupTalkImg> list=groupService.SearchGroup(search);
			if(list==null || list.size()==0)System.out.println("搜索群组失败或群组一个也没有");
			Returndata.returndata(Getjson.Generaljsonarray(list, action, new String[] {"user","setgroupDynamicComment" ,"setgroupDynamic"}));
		}else if(action.equals("退出群组")){
			Integer id=Integer.valueOf(groupId);
			boolean isSuccessful=groupService.QuitGroup(id,SessionID);
			Returndata.returnboolean(isSuccessful);
		}
		return null;
	}
	
	
	
	
	
	
	
	

	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getSessionID() {
		return SessionID;
	}
	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

}
