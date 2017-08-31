package life.treeHole.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;

import life.treeHole.entity.TreeHole;
import life.treeHole.service.TreeHoleService;
import zzu.util.Getjson;
import zzu.util.Returndata;

@Transactional
@Component(value = "TreeHoleAction")
@Scope(value = "prototype")
public class TreeHoleAction extends ActionSupport {
	String treeHoleContent,SessionID,campus,action;
	@Autowired
	private TreeHoleService TreeHoleService;
	@Override
	public String execute() throws Exception {
		 if(action==null){ System.err.println("����actionΪ��");return null;}
		 System.out.println("action="+action);
		 if(action.equals("��������")){
			 TreeHole treeHole=new TreeHole();
			 treeHole.setCampus(campus);
			 treeHole.setTreeHoleContent(treeHoleContent);
			 boolean isSuccessful= TreeHoleService.PublishTreeHole(treeHole,SessionID);
			 Returndata.returnboolean(isSuccessful);
		
		 }else if(action.equals("��ѯ����")){
			 List<TreeHole> treeHole= TreeHoleService.QueryAllTreeHole(SessionID);
			
			Returndata.returndata( Getjson.Generaljsonarray(treeHole, action, new String[]{"user","settreeholeComment"}));
		
		 }else if(action.equals("��ѯ�ҷ���������")){
			 List<TreeHole>treeHole= TreeHoleService.QueryMyTreeHole(SessionID);
			 
			 Returndata.returndata( Getjson.Generaljsonarray(treeHole, action, new String[]{"user","settreeholeComment"}));
		 }else if(action.equals("��ѯ�����۹�������")){
			 List<TreeHole> treehole= TreeHoleService.QueryMyCommentedTreeHole(SessionID);
			
			 Returndata.returndata( Getjson.Generaljsonarray(treehole, action, new String[]{"user","settreeholeComment"}));
		 }else{
			 System.out.println("����action��ƥ��");
		 }
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getTreeHoleContent() {
		return treeHoleContent;
	}
	public void setTreeHoleContent(String treeHoleContent) {
		this.treeHoleContent = treeHoleContent;
	}
	public String getSessionID() {
		return SessionID;
	}
	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}
