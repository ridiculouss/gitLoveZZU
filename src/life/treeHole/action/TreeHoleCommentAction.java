package life.treeHole.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;

import life.treeHole.entity.TreeHole;
import life.treeHole.entity.TreeHoleComment;
import life.treeHole.service.TreeHoleService;
import zzu.themb.ThembModuel;
import zzu.themb.ThembRecord;
import zzu.util.Getjson;
import zzu.util.Returndata;
@Transactional
@Component(value="TreeHoleCommentAction")
@Scope(value = "prototype")
public class TreeHoleCommentAction extends ActionSupport {
	@Autowired
	private TreeHoleService TreeHoleService;
	String action,SessionID,commentContent,treeHoleId;
	@Override
	public String execute() throws Exception {
		if(action==null || treeHoleId==null){System.err.println("������������action��treeHoleIdΪ��");return null;}
		System.out.println("action="+action+",treeHoleId="+treeHoleId);
		if(action.equals("������������")){
			TreeHoleComment tc=new TreeHoleComment();
			tc.setCommentContent(commentContent);
			boolean isSuccessful=TreeHoleService.PublishTreeHoleComment(tc,SessionID,treeHoleId);
			Returndata.returnboolean(isSuccessful);
		}else if(action.equals("��ѯ��������")){
			Integer id=Integer.valueOf(treeHoleId);
			List<TreeHoleComment> treeHoleComment=	TreeHoleService.QueryTreeHoleComment(id);
			Returndata.returndata( Getjson.Generaljsonarray(treeHoleComment, action, new String[]{"user","treehole"}));
			
		}else if(action.equals("��������")){
			boolean isSuccessful=false;
			List<ThembModuel> list= ThembRecord.getvalue(SessionID);
			if(list!=null){
				System.out.println("�û��������޲�ѯhashmap���Ƿ��м�¼");
				for (ThembModuel thembModuel : list) {
					if(thembModuel.getAction().equals("��������") && thembModuel.getId().equals(treeHoleId)){
						isSuccessful=true;
						System.out.println("hashmap�в鵽�м�¼��ֱ�ӷ���true");
					}
				}
			}else{
			Integer id=Integer.valueOf(treeHoleId);
			 isSuccessful=TreeHoleService.ThembTreeHole(id, SessionID);
			//��¼��hashmap
			 ThembModuel tm=ThembModuel.getThembModuel(action, treeHoleId);
			 list=new ArrayList<>();
			 list.add(tm);
			 ThembRecord.addkeyvalue(SessionID, list);
			 System.out.println("�������޲�ѯʱ���ݿ��Ƿ���ޣ�����¼��hashmap");
			}
			Returndata.returnboolean(isSuccessful);
		}else{System.err.println("��������action��ƥ��");}
		
		return null;
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
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getTreeHoleId() {
		return treeHoleId;
	}
	public void setTreeHoleId(String treeHoleId) {
		this.treeHoleId = treeHoleId;
	}

	
	
}
