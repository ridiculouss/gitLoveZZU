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
import persionalCenter.service.UserService;
import zzu.util.GetDate;

@Transactional
@Component(value="TreeHoleService")
@Scope(value = "prototype")
public class TreeHoleService {
	
	@Resource(name = "user_Dao")
	private Dao dao;
	@Resource(name="User_Service")
	private UserService userService;
	//��������
	public boolean PublishTreeHole(TreeHole treeHole, String SessionID) {
		Serializable id=null;
		boolean isSuccessful=false;
		String sql="from User Where SessionID=?";
		List<User> user=dao.query(sql, SessionID);
		if(user.size()==0){System.err.println("��������SessionID����,δ�鵽�û�");}
		treeHole.setUser(user.get(0));
		//user.get(0).getSettreehole().add(treeHole);
		treeHole.setDate(GetDate.GetNowDate());
		treeHole.setThembUser("0");
		id=dao.save(treeHole);
		if(id!=null){isSuccessful=true;System.out.println("���������ɹ�");} 
		return isSuccessful;
	}
//��ѯ��������
	public List<TreeHole> QueryAllTreeHole(String SessionID) {
		String sql="from TreeHole where TreeHoleId !=?";
		List<TreeHole> treehole=dao.query(sql, 0);
		Collections.reverse(treehole);
		//����û��Ƿ���޹�������
		User user=userService.queryUser(SessionID);
		if(user!=null){
			for (TreeHole th : treehole) {
				String uid=th.getThembUser();
				if(uid==null){break;}
				String[] id=uid.split("#");
				for (String d : id) {
					if(user.getUid().toString().equals(d)){th.setThembed(true);};
				}
			}
		}
		return treehole;
		
	}
	//��ѯ�ҷ���������
	public List<TreeHole> QueryMyTreeHole(String SessionID) {
		List<TreeHole> list=new ArrayList<TreeHole>();
		String sql="from User where SessionID =?";
		List<User> user=dao.query(sql, SessionID);
		if(user.size()==0){System.err.println("��ѯ�ҷ���������ʱSessionID����,δ�鵽�û�");return null;}
		Set<TreeHole> treehole=user.get(0).getSettreehole();
		if(treehole.size()!=0)
		list.addAll(treehole);
		return list;
		
	}
	
	
	//������������
	public boolean PublishTreeHoleComment(TreeHoleComment tc, String SessionID, String treeHoleId) {
		boolean isSuccessful=false;
	      String sql="from User where SessionID=?";
	      List<User> user=dao.query(sql, SessionID);
	      sql="from TreeHole where TreeHoleID=?";
	      List<TreeHole> treeHole=dao.query(sql, treeHoleId);
	      if(user.size()==0 || treeHole.size()==0){System.err.println("������������ʱ�û�������û������"
	      		+ ",User:"+user.size()+",TreeHole:"+treeHole.size());}      
	      
	      tc.setDate(GetDate.GetNowDate());
	      tc.setUser(user.get(0));
	      tc.setTreehole(treeHole.get(0));
	      treeHole.get(0).setCommentCount(treeHole.get(0).getCommentCount()+1);//����������������
	      dao.update(treeHole.get(0));
	    Serializable id=  dao.save(tc);//������������
	      if(id!=null){isSuccessful=true;System.out.println("�����������۳ɹ�");}
	      return isSuccessful;
		
	}
	//��ѯ����������
	public List<TreeHoleComment> QueryTreeHoleComment(Integer treeHoleId) {
		List<TreeHoleComment> THC=new ArrayList<TreeHoleComment>();
		String sql="from TreeHole where TreeHoleId=?";
		List<TreeHole> treeHole=dao.query(sql, treeHoleId);
		if(treeHole.size()==0){System.err.println("��ѯ��������ʱδ����������");}
		Set<TreeHoleComment> treeHoleComment=treeHole.get(0).getSettreeholeComment();
		if(treeHoleComment.size()!=0){
			THC.addAll(treeHoleComment);
			}
		Collections.sort(THC,new Comparator<TreeHoleComment>(){

			@Override
			public int compare(TreeHoleComment o1, TreeHoleComment o2) {
				// TODO Auto-generated method stub
				return o2.getTreeHoleCommentId().compareTo(o1.getTreeHoleCommentId());//id�Ӵ�С����
			}
			
		});
		return THC;
		
	}
	//��ѯ��Ʒ�����۹�������
	public List<TreeHole> QueryMyCommentedTreeHole(String sessionID) {
		List<TreeHole> treehole=new ArrayList<TreeHole>();
		String sql="from User where SessionID=?";
		List<User> user=dao.query(sql, sessionID);
		if(user.size()==0){System.err.println("��ѯ�����۹�������ʱδ�������û�");}
		Set<TreeHoleComment> treeHoleComment=user.get(0).getSettreeholeComment();
		for (TreeHoleComment tc : treeHoleComment) {
			treehole.add(tc.getTreehole());
		}
		Collections.sort(treehole,new Comparator<TreeHole>(){//�Ƚ�������id��Ȼ����
			@Override
			public int compare(TreeHole arg0, TreeHole arg1) {
				
				return arg0.getTreeHoleId().compareTo(arg1.getTreeHoleId());
			}                  
        }); 
		
		return treehole;
		
	}
	//��������
	public boolean ThembTreeHole(Integer treeHoleId, String SessionID) {
		boolean isSuccessful=false;
		User user=userService.queryUser(SessionID);
		if(user==null){return false;}
		String sql="from TreeHole where TreeHoleId=?";
		List<TreeHole> treeHole=dao.query(sql, treeHoleId);
		for (TreeHole treeHole2 : treeHole) {
			String[] thembuser=treeHole2.getThembUser().split("#");
			         for (String string : thembuser) {
						if(user.getUid().toString().equals(string)){return false;}
					}
			treeHole2.setThembCount(treeHole2.getThembCount()+1);
			treeHole2.setThembUser(treeHole2.getThembUser()+"#"+user.getUid().toString());
			dao.update(treeHole2);
			isSuccessful=true;
		}
		return isSuccessful;
		
	}
	
}
