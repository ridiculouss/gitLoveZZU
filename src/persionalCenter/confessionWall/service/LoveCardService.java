package persionalCenter.confessionWall.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import life.taoyu.dao.Dao_taoyu;
import persionalCenter.confessionWall.entity.LoveCard;
import persionalCenter.confessionWall.entity.LoveCardComment;
import persionalCenter.confessionWall.moudledriver.ImgComment;
import persionalCenter.dao.Dao;
import persionalCenter.entity.User;
import persionalCenter.entity.UserInfo;
import zzu.util.GetDate;
import zzu.util.Panduanstr;

@Transactional
@Component(value = "LoveCardService")
@Scope(value = "prototype")
public class LoveCardService {
	
	@Resource(name = "user_Dao")
	private Dao dao;
	@Resource(name = "taoyuDao")
	private Dao_taoyu TDao;
	//������׿�
	public boolean PublishLoveCard(LoveCard l) {
		boolean isSuccessful=false;
		l.setDate(GetDate.GetNowDate());
		l.setSearch(l.getSenderName()+l.getLovedName());
		Serializable id=dao.save(l);
		if(id!=null)
		isSuccessful=true;
		return isSuccessful;
	}
//��ѯ���б�׿�
	public List<LoveCard> QueryAllLoveCard() {
		String sql="from LoveCard where loveCardId !=? order by loveCardId desc";
		List<LoveCard> loveCard=dao.query(sql, 0);
		
		return loveCard;
	}
//ģ��������׿�
	public List<LoveCard> SearchLoveCard(String search) {
	
		String sql="from LoveCard where senderName  like ? or lovedName like ?";
		String[] value={search+"%",search+"%"};
		List<LoveCard> loveCard=dao.query(sql, value);
		String[] s=search.split("");
		if(loveCard.size()==0 && s.length>1){//ȡ�ַ���search�ĵڶ������ٴβ�ѯ��
			//��Ϊ������ݿ�search�ֶ�Ϊ�ŷ����������û������ѯ�����ŷɣ������ǲ鲻���ġ���ȡ����һ���ֱ��ܲ����ȱ���Ǹ�����ȷ��
			sql="from LoveCard where search like  ? ";
			String values=s[0]+s[1]+"%";
			 loveCard=dao.query(sql, values);
			 System.out.println("��һ��ģ����ѯδ�鵽��ȡ�ַ����ڶ������ٲ�õ�:"+loveCard.size()+"��");
		}
		return loveCard;
	}
	//������׿�����
	public boolean PublishLoveCardComment(String sessionID, Integer loveCardId, LoveCardComment lCC) {
		boolean isSuccessful=false;
		String sql="from User where SessionID =?";
		List<User> user=dao.query(sql, sessionID);
		sql="from LoveCard where loveCardId =?";
		List<LoveCard> loveCard=dao.query(sql, loveCardId);
		if(user.size()==0 || loveCard.size()==0){System.err.println("������������û����׿���¼δ������");return false;}
		lCC.setDate(GetDate.GetNowDate());
		lCC.setUser(user.get(0));
		lCC.setLovecard(loveCard.get(0));
		Serializable id=dao.save(lCC);
		if(id!=null)isSuccessful=true;
		
		return isSuccessful;
		
		
	}
	//��ѯ��׿�����
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
	//���ޱ�׿�
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
