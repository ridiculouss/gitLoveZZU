package life.mytopiccircle.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import life.mytopiccircle.entity.Theme;
import life.mytopiccircle.entity.Topic;
import life.mytopiccircle.entity.TopicComment;
import life.mytopiccircle.moudledriver.UserTheme;
import life.mytopiccircle.moudledriver.UserTopic;
import life.mytopiccircle.moudledriver.UserTopicComment;
import life.taoyu.dao.Dao_taoyu;
import persionalCenter.dao.Dao;
import persionalCenter.entity.User;
import persionalCenter.entity.UserInfo;
import zzu.util.GetDate;

@Transactional
@Component(value="TopicCircleService")
@Scope(value = "prototype")
public class TopicCircleService {
	@Resource(name = "user_Dao")
	private Dao dao;
	
	
	//发布主题
	public String PublishTheme( Theme theme, String SessionID){
		
		 Serializable id=null;
		String sql="from User where SessionID=?";
		List<User> user=dao.query(sql, SessionID);
		if(user.size()!=0){
			theme.setUser(user.get(0));
		    
		 id=  dao.save(theme);
		}else{System.err.println("发布主题未检索到用户");}
		if(id.toString()!=null){
			
		System.out.println("发布主题成功!");	
		}
		return id.toString();
	}
	//更新主题(图片,话题量)
	public boolean updateTheme(Theme t){
		boolean isSuccessful = false;
		String sql="from Theme where ThemeId=?";
		List<Theme> theme=dao.query(sql, t.getThemeId());
		for (Theme theme2 : theme) {
			if(t.getThemeImg()!=null&&!t.getThemeImg().isEmpty())theme2.setThemeImg(t.getThemeImg());
			if(t.getTopicCount()==1)theme2.setTopicCount(theme2.getTopicCount()+1);
			dao.update(theme2);
			isSuccessful=true;
		}
		return isSuccessful;
	}
	//查询所有主题
	public List<UserTheme> queryTheme(){
		List<UserTheme> UTlist=new ArrayList<UserTheme>();
		String sql="from Theme where ThemeId!=?";
		List<Theme> theme=dao.query(sql, 0);
		for (Theme theme2 : theme) {
			UserTheme UT=new UserTheme();
			UT.setTheme(theme2);
			Integer uid= theme2.getUser().getUid();
			sql="from UserInfo where ul_id=?";
			List<UserInfo> userinfo=dao.query(sql, uid);

			UT.setUserinfo(userinfo.get(0));
			UTlist.add(UT);
		}
		return UTlist;
	}
	//发布话题
	public String PublishTopic(Integer ThemeId ,Topic topic, String SessionID){
		 Serializable id=null;
			String sql="from User where SessionID=?";
			List<User> user=dao.query(sql, SessionID);
			if(user.size()!=0){
				topic.setUser(user.get(0));
				 sql="from Theme where ThemeId=?";
				List<Theme> theme=dao.query(sql, ThemeId);
				if(theme.size()!=0)topic.setTheme(theme.get(0));
				topic.setDate(GetDate.GetNowDate());
			 id=  dao.save(topic);
			}else{System.err.println("发布话题未检索到用户");}
			if(id.toString()!=null){
				Theme t=new Theme();
				t.setTopicCount(1);t.setThemeId(ThemeId);
				updateTheme(t);
			System.out.println("发布话题成功!");	
			}

		return id.toString();
		
	}
	//更新话题
	public boolean updateTopic(Topic topic) {
		boolean isSuccessful = false;
		String sql="from Topic where TopicId=?";
		List<Topic> topiclist=dao.query(sql, topic.getTopicId());
		for (Topic topic2 : topiclist) {
			if(topic.getTopicImg()!=null&&!topic.getTopicImg().isEmpty()){topic2.setTopicImg(topic.getTopicImg());}
			if(topic.getTopicCommentCount()==1){topic2.setTopicCommentCount(topic2.getTopicCommentCount()+1);}
			if(topic.getTopicThumbCount()==1){topic2.setTopicThumbCount(topic2.getTopicThumbCount()+1);}
			if(topic.getTopicThumbCount()==0 && topic2.getTopicThumbCount()>0){topic2.setTopicThumbCount(topic2.getTopicThumbCount()-1);}
			
			dao.update(topic2);
			isSuccessful=true;
		}
		return isSuccessful;
	}
	//查询主题中的所有话题
	public List<UserTopic>  queryTopic(Integer i) {
		List<UserTopic> UPlist=new ArrayList<UserTopic>();
		
		String sql="from Theme where ThemeId=?";
		List<Theme> theme=dao.query(sql, i);
		if(theme.size()==0){System.out.println("查询话题时未检索到主题");return null;}
		Set<Topic> topicset=theme.get(0).getSettopic();
		List<Topic> topiclist=new ArrayList<>(topicset);
		for (Topic topic : topiclist) {
			Integer uid= topic.getUser().getUid();
			sql="from UserInfo where ul_id=?";
			List<UserInfo> userinfo=dao.query(sql, uid);
			UserTopic UP=new UserTopic();
			UP.setTopic(topic);UP.setUserinfo(userinfo.get(0));
			UPlist.add(UP);
		}
		Collections.reverse(UPlist);
		System.out.println(UPlist);
		return UPlist;
	}
	//发布话题评论
	public boolean PublishTopicComment(Integer topicId, String topicComment, String SessionID) {
		boolean isSuccessful=false;
		String sql="from User where SessionID=?";
		List<User> user=dao.query(sql, SessionID);
		if(user.size()==0){System.err.println("话题评论未找到用户");return false;}
		 sql="from Topic where TopicId=?";
		List<Topic> topic=dao.query(sql, topicId);
		for (Topic topic2 : topic) {
			TopicComment TC=new TopicComment();
			TC.setTopicComment(topicComment);
			TC.setTopic(topic2);
			TC.setUser(user.get(0));
			TC.setDate(GetDate.GetNowDate());
			dao.save(TC);
			isSuccessful=true;
		topic2.setTopicCommentCount(1);
		updateTopic(topic2);
		}
		return isSuccessful;
	}
	//查询话题评论
	public List<UserTopicComment> queryTopicComment(Integer topicId) {
		List<UserTopicComment> UTClist=new ArrayList<UserTopicComment>();
		String sql="from Topic where TopicId=?";
		List<Topic> topiclist=dao.query(sql, topicId);
		for (Topic topic : topiclist) {
			
			Set<TopicComment> TopicCommentlist=topic.getSettopiccomment();
			for (TopicComment topicComment : TopicCommentlist) {
				Set<UserInfo> userinfo=topicComment.getUser().getSetuserinfo();
				Iterator<UserInfo> uit=userinfo.iterator();
				UserInfo ui=uit.next();
				UserTopicComment UTC=new UserTopicComment();
				UTC.setUserinfo(ui);
				UTC.setTopiccomment(topicComment);
				UTClist.add(UTC);
			}
		}
		Collections.reverse(UTClist);
		return UTClist;
	}
	
	
}
