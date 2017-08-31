package persionalCenter.confessionWall.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;

import persionalCenter.confessionWall.entity.LoveCardComment;
import persionalCenter.confessionWall.moudledriver.ImgComment;
import persionalCenter.confessionWall.service.LoveCardService;
import zzu.util.Getjson;
import zzu.util.Returndata;

@Transactional
@Component(value = "LoveCardCommentAction")
@Scope(value = "prototype")
public class LoveCardCommentAction extends ActionSupport{
	@Autowired
	private LoveCardService LovecardService;
	String action,SessionID,loveCardId,commentContent;
	@Override
	public String execute() throws Exception {
		System.out.println(action+"||"+loveCardId);
		if(action==null || loveCardId==null){System.err.println("表白卡评论action或loveCardId为空");
			Returndata.returnboolean(false);return null;}
		if(action.equals("发布表白评论")){
			LoveCardComment LCC=new LoveCardComment();
			LCC.setCommentContent(commentContent);
			Integer id=Integer.valueOf(loveCardId);
	        boolean isSuccessful=  LovecardService.PublishLoveCardComment(SessionID,id,LCC);
	        Returndata.returnboolean(isSuccessful);
		}else if(action.equals("查询表白评论")){
			Integer id=Integer.valueOf(loveCardId);
			List<ImgComment>  IClist=LovecardService.QueryLoveCardComment(id);
			Returndata.returndata(Getjson.Generaljsonarray(IClist, action, new String[]{"user","lovecard"}));
		}else if(action.equals("点赞表白卡")){
			Integer id=Integer.valueOf(loveCardId);
		boolean isSuccessful=	LovecardService.ThembLoveCard(id,SessionID);
		Returndata.returnboolean(isSuccessful);
		}
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
	public String getLoveCardId() {
		return loveCardId;
	}
	public void setLoveCardId(String loveCardId) {
		this.loveCardId = loveCardId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

}
