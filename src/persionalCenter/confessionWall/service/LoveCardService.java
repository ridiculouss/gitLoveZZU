package persionalCenter.confessionWall.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import persionalCenter.confessionWall.entity.LoveCard;
import persionalCenter.confessionWall.entity.LoveCardComment;
import persionalCenter.confessionWall.moudledriver.ImgComment;
import persionalCenter.dao.Dao;
import persionalCenter.entity.User;
import persionalCenter.entity.UserInfo;
import zzu.util.GetDate;

@Transactional
@Component(value = "LoveCardService")
@Scope(value = "prototype")
public class LoveCardService {
	
	@Resource(name = "user_Dao")
	private Dao dao;
	
	//发布表白卡
	public boolean PublishLoveCard(LoveCard l) {
		boolean isSuccessful=false;
		l.setDate(GetDate.GetNowDate());
		l.setSearch(l.getSenderName()+l.getLovedName());
		Serializable id=dao.save(l);
		if(id!=null)
		isSuccessful=true;
		return isSuccessful;
	}
//查询所有表白卡
	public List<LoveCard> QueryAllLoveCard() {
		String sql="from LoveCard where loveCardId !=? order by loveCardId desc";
		List<LoveCard> loveCard=dao.query(sql, 0);
		
		return loveCard;
	}
//模糊搜索表白卡
	public List<LoveCard> SearchLoveCard(String search) {
		String sql="from LoveCard where search like  ? ";
		String values="%"+search+"%";
		List<LoveCard> loveCard=dao.query(sql, values);
		String[] s=search.split("");
		if(loveCard.size()==0 && s.length>1){//取字符串search的第二个字再次查询，
			//因为如果数据库search字段为张飞刘备，而用户输入查询刘备张飞，这样是查不到的。而取其中一个字便能查出。缺点是更不精确了
			sql="from LoveCard where search like  ? ";
			 values="%"+s[1]+"%";
			 loveCard=dao.query(sql, values);
			 System.out.println("第一次模糊查询未查到，取字符串第二个字再查得到:"+loveCard.size()+"个");
		}
		return loveCard;
	}
	//发布表白卡评论
	public boolean PublishLoveCardComment(String sessionID, Integer loveCardId, LoveCardComment lCC) {
		boolean isSuccessful=false;
		String sql="from User where SessionID =?";
		List<User> user=dao.query(sql, sessionID);
		sql="from LoveCard where loveCardId =?";
		List<LoveCard> loveCard=dao.query(sql, loveCardId);
		if(user.size()==0 || loveCard.size()==0){System.err.println("发布表白评论用户或表白卡记录未检索到");return false;}
		lCC.setDate(GetDate.GetNowDate());
		lCC.setUser(user.get(0));
		lCC.setLovecard(loveCard.get(0));
		Serializable id=dao.save(lCC);
		if(id!=null)isSuccessful=true;
		
		return isSuccessful;
		
		
	}
	//查询表白卡评论
	public List<ImgComment> QueryLoveCardComment(Integer id) {
		List<ImgComment>  IClist=new ArrayList<ImgComment>();
		String sql="from LoveCard where loveCardId=?";
		List<LoveCard> lovecard=dao.query(sql, id);
		Set<LoveCardComment> LCM=lovecard.get(0).getSetloveCardComment();
		for (LoveCardComment loveCardComment : LCM) {
			Set<UserInfo> userinfo=loveCardComment.getUser().getSetuserinfo();
			for (UserInfo userInfo2 : userinfo) {
				ImgComment ic=new ImgComment();
				ic.setUserImg(userInfo2.getImageUrl());
				ic.setLoveCardComment(loveCardComment);
				IClist.add(ic);
			}
		}
		return IClist;
		
	}
	//点赞表白卡
	public boolean ThembLoveCard(Integer id, String sessionID) {
		boolean isSuccessful=false;
		String sql="from User where SessionID=?";
		List<User> user=dao.query(sql, sessionID);
		sql="from LoveCard where loveCardId=?";
		List<LoveCard> loveCard=dao.query(sql, id);
		for (LoveCard loveCard2 : loveCard) {
			loveCard2.setThembCount(loveCard2.getThembCount()+1);
			isSuccessful=true;
		}
		return isSuccessful;
		
	}
}
