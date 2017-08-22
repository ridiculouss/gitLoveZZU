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
//发布兼职
	public boolean  PublishPartTime(String sessionID, PartTime p) {
		boolean isSuccessful=false;
		String sql="from User where SessionID=?";
		List<User> user=dao.query(sql, sessionID);
		if(user.size()==0){System.err.println("发布兼职用户未检索到");return false;}
		p.setStatus("正在审核");
		p.setUser(user.get(0));
		p.setPublishDate(GetDate.GetNowDate());
		Serializable id=dao.save(p);
		if(id!=null)isSuccessful=true;
		return isSuccessful;
	}
	//查询我发布的兼职
	public List<PartTime>  QueryMyPartTime(String sessionID) {
		List<PartTime> partTimelist=new ArrayList<PartTime>();
		String sql="from User where SessionID=?";
		List<User> user=dao.query(sql, sessionID);
		if(user.size()==0){System.err.println("查询我发布的兼职未检索到用户");}
		Set<PartTime> partTime=user.get(0).getSetpartTime();
		partTimelist.addAll(partTime);
		return partTimelist;
		
		
	}
	//根据校内，校外分类查询
	public List<PartTime> QueryPartTimeBySearch(String search) {
		List<PartTime> p=new ArrayList<PartTime>();
		String sql="from PartTime where campus=?   order by partTimeId desc";
		List<PartTime> partTime=dao.query(sql, search);
		for (int i=0;i<partTime.size();i++) {
			if(partTime.get(i).getStatus().equals("审核通过")){p.add(partTime.get(i));}
		}
		//按最新返回
		return p;
		
	}
	//搜索兼职
	public List<PartTime> SearchPartTime(String search) {
		List<PartTime> p=new ArrayList<PartTime>();
		String sql="from PartTime where title like ? order by partTimeId desc";
		String values="%"+search+"%";
		List<PartTime> partTime=dao.query(sql, values);
		for (int i=0;i<partTime.size();i++) {
			if(partTime.get(i).getStatus().equals("审核通过")){p.add(partTime.get(i));}
		}
		//按最新返回
		return p;
		
	}
	//删除我的兼职
	public boolean DeleteMyPartTime(Integer partTimeId) {
		
		String sql="from PartTime where partTimeId =?";
		List<PartTime> partTime=dao.query(sql, partTimeId);
		if(partTime.size()==0){System.err.println("删除兼职时未检索到兼职");return false;}
		dao.delete(partTime.get(0));
		
		return true;
		
	}
	
	//根据状态查询兼职， 管理员的操作
	public List<PartTime> QueryPartTimeBystatus(String status){
		String sql="from PartTime where status = ?";
		List<PartTime> partTime =dao.query(sql, status);
		return partTime;
	}
	//管理员更新兼职状态
	public void UpdatePartTimeStatus(String status,Integer partTimeId){
		String sql="from PartTime where partTimeId=?";
		List<PartTime> partTime=dao.query(sql, partTimeId);
		for (PartTime p : partTime) {
			p.setStatus(status);
			dao.update(p);
			System.out.println("更新兼职状态为"+status+"成功!");
		}
	}
}
