package persionalCenter.confessionWall.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;

import persionalCenter.confessionWall.entity.LoveCard;
import persionalCenter.confessionWall.service.LoveCardService;
import zzu.util.Getjson;
import zzu.util.Returndata;
@Transactional
@Component(value = "LoveCardAction")
@Scope(value = "prototype")
public class LoveCardAction extends ActionSupport{
	@Autowired
	private LoveCardService LovecardService;
	
	private String senderName,lovedName,loveContent,action,search; 
@Override
public String execute() throws Exception {
	if(action==null){System.err.println("表白卡action为空");Returndata.returnboolean(false);}
	System.out.println("action="+action);
	if(action.equals("发布表白")){
		if(senderName.equals("")){System.err.println("表白人名字空串");}
		System.out.println("表白人名字="+senderName);
		LoveCard l=new LoveCard();
		l.setLoveContent(loveContent);
		l.setSenderName(senderName);
		l.setLovedName(lovedName);
		boolean isSuccessful=LovecardService.PublishLoveCard(l);
		Returndata.returnboolean(isSuccessful);
	}else if(action.equals("查询所有表白卡")){
		List<LoveCard> loveCard=LovecardService.QueryAllLoveCard();
		Returndata.returndata(Getjson.Generaljsonarray(loveCard, action, new String[]{"setloveCardComment","search"}));
	}else if(action.equals("搜索表白卡")){
		List<LoveCard> loveCard=LovecardService.SearchLoveCard(search);
		Returndata.returndata(Getjson.Generaljsonarray(loveCard, action, new String[]{"setloveCardComment","search"}));
	}
	
	
	return null; 
}
public String getSenderName() {
	return senderName;
}
public void setSenderName(String senderName) {
	this.senderName = senderName;
}
public String getLovedName() {
	return lovedName;
}
public void setLovedName(String lovedName) {
	this.lovedName = lovedName;
}
public String getLoveContent() {
	return loveContent;
}
public void setLoveContent(String loveContent) {
	this.loveContent = loveContent;
}
public String getAction() {
	return action;
}
public void setAction(String action) {
	this.action = action;
}

public String getSearch() {
	return search;
}
public void setSearch(String search) {
	this.search = search;
}
}
