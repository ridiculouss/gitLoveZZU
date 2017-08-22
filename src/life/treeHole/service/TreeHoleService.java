package life.treeHole.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import life.treeHole.action.TreeHoleCommentAction;
import life.treeHole.entity.TreeHole;
import life.treeHole.entity.TreeHoleComment;
import persionalCenter.dao.Dao;
import persionalCenter.entity.User;
import zzu.util.GetDate;

@Transactional
@Component(value="TreeHoleService")
@Scope(value = "prototype")
public class TreeHoleService {
	
	@Resource(name = "user_Dao")
	private Dao dao;
	
	//发布树洞
	public boolean PublishTreeHole(TreeHole treeHole, String SessionID) {
		Serializable id=null;
		boolean isSuccessful=false;
		String sql="from User Where SessionID=?";
		List<User> user=dao.query(sql, SessionID);
		if(user.size()==0){System.err.println("发布树洞SessionID错误,未查到用户");}
		treeHole.setUser(user.get(0));
		//user.get(0).getSettreehole().add(treeHole);
		treeHole.setDate(GetDate.GetNowDate());
		id=dao.save(treeHole);
		if(id!=null){isSuccessful=true;System.out.println("发布树洞成功");} 
		return isSuccessful;
	}
//查询所有树洞
	public List<TreeHole> QueryAllTreeHole() {
		String sql="from TreeHole where TreeHoleId !=?";
		List<TreeHole> treehole=dao.query(sql, 0);
		Collections.reverse(treehole);
		return treehole;
		
	}
	//查询我发布的树洞
	public List<TreeHole> QueryMyTreeHole(String SessionID) {
		List<TreeHole> list=new ArrayList<TreeHole>();
		String sql="from User where SessionID =?";
		List<User> user=dao.query(sql, SessionID);
		if(user.size()==0){System.err.println("查询我发布的树洞时SessionID错误,未查到用户");return null;}
		Set<TreeHole> treehole=user.get(0).getSettreehole();
		if(treehole.size()!=0)
		list.addAll(treehole);
		return list;
		
	}
	
	
	//发布树洞评论
	public boolean PublishTreeHoleComment(TreeHoleComment tc, String SessionID, String treeHoleId) {
		boolean isSuccessful=false;
	      String sql="from User where SessionID=?";
	      List<User> user=dao.query(sql, SessionID);
	      sql="from TreeHole where TreeHoleID=?";
	      List<TreeHole> treeHole=dao.query(sql, treeHoleId);
	      if(user.size()==0 || treeHole.size()==0){System.err.println("发布树洞评论时用户或树洞没检索到"
	      		+ ",User:"+user.size()+",TreeHole:"+treeHole.size());}      
	      
	      tc.setDate(GetDate.GetNowDate());
	      tc.setUser(user.get(0));
	      tc.setTreehole(treeHole.get(0));
	      treeHole.get(0).setCommentCount(treeHole.get(0).getCommentCount()+1);//更新树洞的评论量
	      dao.update(treeHole.get(0));
	    Serializable id=  dao.save(tc);//保存树洞评论
	      if(id!=null){isSuccessful=true;System.out.println("发布树洞评论成功");}
	      return isSuccessful;
		
	}
	//查询树洞的评论
	public List<TreeHoleComment> QueryTreeHoleComment(Integer treeHoleId) {
		List<TreeHoleComment> THC=new ArrayList<TreeHoleComment>();
		String sql="from TreeHole where TreeHoleId=?";
		List<TreeHole> treeHole=dao.query(sql, treeHoleId);
		if(treeHole.size()==0){System.err.println("查询树洞评论时未检索到树洞");}
		Set<TreeHoleComment> treeHoleComment=treeHole.get(0).getSettreeholeComment();
		if(treeHoleComment.size()!=0){
			THC.addAll(treeHoleComment);
			}
		Collections.sort(THC,new Comparator<TreeHoleComment>(){

			@Override
			public int compare(TreeHoleComment o1, TreeHoleComment o2) {
				// TODO Auto-generated method stub
				return o2.getTreeHoleCommentId().compareTo(o1.getTreeHoleCommentId());//id从大到小排序
			}
			
		});
		return THC;
		
	}
	//查询物品我评论过的树洞
	public List<TreeHole> QueryMyCommentedTreeHole(String sessionID) {
		List<TreeHole> treehole=new ArrayList<TreeHole>();
		String sql="from User where SessionID=?";
		List<User> user=dao.query(sql, sessionID);
		if(user.size()==0){System.err.println("查询我评论过的树洞时未检索到用户");}
		Set<TreeHoleComment> treeHoleComment=user.get(0).getSettreeholeComment();
		for (TreeHoleComment tc : treeHoleComment) {
			treehole.add(tc.getTreehole());
		}
		Collections.sort(treehole,new Comparator<TreeHole>(){//比较器排序按id自然排序
			@Override
			public int compare(TreeHole arg0, TreeHole arg1) {
				
				return arg0.getTreeHoleId().compareTo(arg1.getTreeHoleId());
			}                  
        }); 
		
		return treehole;
		
	}
	//树洞点赞
	public boolean ThembTreeHole(Integer treeHoleId, String sessionID) {
		boolean isSuccessful=false;
		String sql="from User where SessionID =?";
		List<User> user=dao.query(sql, sessionID);
		if(user.size()==0){System.err.println("树洞点赞未检索到用户");}
		sql="from TreeHole where TreeHoleId=?";
		List<TreeHole> treeHole=dao.query(sql, treeHoleId);
		for (TreeHole treeHole2 : treeHole) {
			treeHole2.setThembCount(treeHole2.getThembCount()+1);
			dao.update(treeHole2);
			isSuccessful=true;
		}
		return isSuccessful;
		
	}
	
}
