package life.parTime.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import life.parTime.entity.PartTime;
import persionalCenter.dao.Dao;
import persionalCenter.entity.User;
import zzu.util.GetDate;

@Transactional
@Component(value="PartTimeService")
@Scope(value = "prototype")
public class PartTimeService {
	@Resource(name = "user_Dao")
	private Dao dao;
//������ְ
	public boolean  PublishPartTime(String sessionID, PartTime p) {
		boolean isSuccessful=false;
		String sql="from User where SessionID=?";
		List<User> user=dao.query(sql, sessionID);
		if(user.size()==0){System.err.println("������ְ�û�δ������");return false;}
		p.setStatus("�������");
		p.setUser(user.get(0));
		p.setPublishDate(GetDate.GetNowDate());
		Serializable id=dao.save(p);
		if(id!=null)isSuccessful=true;
		return isSuccessful;
	}
	//��ѯ�ҷ����ļ�ְ
	public List<PartTime>  QueryMyPartTime(String sessionID) {
		List<PartTime> partTimelist=new ArrayList<PartTime>();
		String sql="from User where SessionID=?";
		List<User> user=dao.query(sql, sessionID);
		if(user.size()==0){System.err.println("��ѯ�ҷ����ļ�ְδ�������û�");}
		Set<PartTime> partTime=user.get(0).getSetpartTime();
		partTimelist.addAll(partTime);
		return partTimelist;
		
		
	}
	//����У�ڣ�У������ѯ
	public List<PartTime> QueryPartTimeBySearch(String search) {
		List<PartTime> p=new ArrayList<PartTime>();
		String sql="from PartTime where campus=?   order by partTimeId desc";
		List<PartTime> partTime=dao.query(sql, search);
		for (int i=0;i<partTime.size();i++) {
			if(partTime.get(i).getStatus().equals("���ͨ��")){p.add(partTime.get(i));}
		}
		//�����·���
		return p;
		
	}
	//������ְ
	public List<PartTime> SearchPartTime(String search) {
		List<PartTime> p=new ArrayList<PartTime>();
		String sql="from PartTime where title like ? order by partTimeId desc";
		String values=search+"%";
		List<PartTime> partTime=dao.query(sql, values);
		for (int i=0;i<partTime.size();i++) {
			if(partTime.get(i).getStatus().equals("���ͨ��")){p.add(partTime.get(i));}
		}
		//�����·���
		return p;
		
	}
	//ɾ���ҵļ�ְ
	public boolean DeleteMyPartTime(Integer partTimeId) {
		
		String sql="from PartTime where partTimeId =?";
		List<PartTime> partTime=dao.query(sql, partTimeId);
		if(partTime.size()==0){System.err.println("ɾ����ְʱδ��������ְ");return false;}
		dao.delete(partTime.get(0));
		
		return true;
		
	}
	
	//����״̬��ѯ��ְ�� ����Ա�Ĳ���
	public List<PartTime> QueryPartTimeBystatus(String status){
		String sql="from PartTime where status = ?";
		List<PartTime> partTime =dao.query(sql, status);
		return partTime;
	}
	//����Ա���¼�ְ״̬
	public void UpdatePartTimeStatus(String status,Integer partTimeId){
		String sql="from PartTime where partTimeId=?";
		List<PartTime> partTime=dao.query(sql, partTimeId);
		for (PartTime p : partTime) {
			p.setStatus(status);
			dao.update(p);
			System.out.println("���¼�ְ״̬Ϊ"+status+"�ɹ�!");
		}
	}
}
