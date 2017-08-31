package life.playTogether.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;

import life.playTogether.entity.GroupDynamicComment;
import life.playTogether.modeldriver.UserDynamicComment;
import life.playTogether.service.GroupService;
import zzu.themb.ThembModuel;
import zzu.themb.ThembRecord;
import zzu.util.Getjson;
import zzu.util.Returndata;

@Transactional
@Component(value = "DynamicCommentAction")
@Scope(value = "prototype")
public class DynamicCommentAction extends ActionSupport {
	private String dynamicId, action, SessionID,comment;
	@Autowired
	GroupService groupService;

	@Override
	public String execute() throws Exception {
		if (action == null) {System.err.println("Ⱥ�鶯̬����actionΪ��");return null;}
		System.out.println(action);
		if (action.equals("����̬����")) {
			if(dynamicId==null){System.err.println("dynamicIdΪ��");return null;}
			Integer id=Integer.valueOf(dynamicId);
			GroupDynamicComment GDC=new GroupDynamicComment();
			GDC.setComment(comment);
			boolean isSuccessful=groupService.PublishDynamicComment(GDC,SessionID,id);
			Returndata.returnboolean(isSuccessful);
		}else if(action.equals("��ѯ��̬����")){
			if(dynamicId==null){System.err.println("dynamicIdΪ��");return null;}
			Integer id=Integer.valueOf(dynamicId);
			List<UserDynamicComment> UDClist=groupService.QueryDynamicComment(id);
			Returndata.returndata(Getjson.Generaljsonarray(UDClist, action, new String[] {"user","group" ,"groupDynamic"}));
		}else if(action.equals("��̬����")){
			boolean isSuccessful=false;
			List<ThembModuel> list= ThembRecord.getvalue(SessionID);
			if(list!=null){
				System.out.println("�û���̬���޲�ѯhashmap���Ƿ��м�¼");
				for (ThembModuel thembModuel : list) {
					if(thembModuel.getAction().equals("��̬����") && thembModuel.getId().equals(dynamicId)){
						isSuccessful=true;
						System.out.println("hashmap�в鵽�м�¼��ֱ�ӷ���true");
					}
				}
			}else{
			Integer id=Integer.valueOf(dynamicId);
			 isSuccessful=groupService.ThembDynamic(id,SessionID);
			 ThembModuel tm=ThembModuel.getThembModuel(action, dynamicId);
			 //��¼��hashmap
			 list=new ArrayList<>();
			 list.add(tm);
			 ThembRecord.addkeyvalue(SessionID, list);
			 System.out.println("��̬���޲�ѯʱ���ݿ��Ƿ���ޣ�����¼��hashmap");
			}
			Returndata.returnboolean(isSuccessful);
		}

		return null;
	}

	
	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
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
