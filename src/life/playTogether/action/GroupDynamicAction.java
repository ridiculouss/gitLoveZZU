package life.playTogether.action;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;
import life.playTogether.entity.GroupDynamic;
import life.playTogether.modeldriver.UserDynamic;
import life.playTogether.service.GroupService;
import zzu.util.Getjson;
import zzu.util.Returndata;

@Transactional
@Component(value = "GroupDynamicAction")
@Scope(value = "prototype")
public class GroupDynamicAction extends ActionSupport{
	private String talk,SessionID,action,groupId;
	int num;
	@Autowired
	GroupService groupService;
	@Override
	public String execute() throws Exception {
		if(action==null){System.err.println("群组动态action为空");return null;}
		System.out.println(action);
		if(action.equals("发表说说")){
			Integer id=Integer.valueOf(groupId);
			GroupDynamic gd=new GroupDynamic();
			gd.setTalk(talk);
			String dynamicId=groupService.PublishTalk(gd,SessionID,id);
			if(dynamicId!=null)System.out.println("发表说说成功");
			Returndata.returndata(dynamicId);
		}else if(action.equals("查询群动态")){
			Integer id=Integer.valueOf(groupId);
			int n=Integer.valueOf(num);
			List<UserDynamic> list=groupService.QueryDynamic(id,n);
			Returndata.returndata(Getjson.Generaljsonarray(list, action, new String[] { "group","user","setgroupDynamicComment" ,"setgroupDynamic"}));
			
		}
		return null;
	}
	


	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTalk() {
		return talk;
	}
	public void setTalk(String talk) {
		this.talk = talk;
	}
	public String getSessionID() {
		return SessionID;
	}
	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	
	
	
	
	
	
	

	
	
}
