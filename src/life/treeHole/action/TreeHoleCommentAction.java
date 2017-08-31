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
		if(action==null || treeHoleId==null){System.err.println("发布树洞评论action或treeHoleId为空");return null;}
		System.out.println("action="+action+",treeHoleId="+treeHoleId);
		if(action.equals("发布树洞评论")){
			TreeHoleComment tc=new TreeHoleComment();
			tc.setCommentContent(commentContent);
			boolean isSuccessful=TreeHoleService.PublishTreeHoleComment(tc,SessionID,treeHoleId);
			Returndata.returnboolean(isSuccessful);
		}else if(action.equals("查询树洞评论")){
			Integer id=Integer.valueOf(treeHoleId);
			List<TreeHoleComment> treeHoleComment=	TreeHoleService.QueryTreeHoleComment(id);
			Returndata.returndata( Getjson.Generaljsonarray(treeHoleComment, action, new String[]{"user","treehole"}));
			
		}else if(action.equals("树洞点赞")){
			boolean isSuccessful=false;
			List<ThembModuel> list= ThembRecord.getvalue(SessionID);
			if(list!=null){
				System.out.println("用户树洞点赞查询hashmap中是否有记录");
				for (ThembModuel thembModuel : list) {
					if(thembModuel.getAction().equals("树洞点赞") && thembModuel.getId().equals(treeHoleId)){
						isSuccessful=true;
						System.out.println("hashmap中查到有记录，直接返回true");
					}
				}
			}else{
			Integer id=Integer.valueOf(treeHoleId);
			 isSuccessful=TreeHoleService.ThembTreeHole(id, SessionID);
			//记录到hashmap
			 ThembModuel tm=ThembModuel.getThembModuel(action, treeHoleId);
			 list=new ArrayList<>();
			 list.add(tm);
			 ThembRecord.addkeyvalue(SessionID, list);
			 System.out.println("树洞点赞查询时数据库是否点赞，并记录到hashmap");
			}
			Returndata.returnboolean(isSuccessful);
		}else{System.err.println("树洞评论action不匹配");}
		
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
