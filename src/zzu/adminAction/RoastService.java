package zzu.adminAction;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import persionalCenter.dao.Dao;
import persionalCenter.dao.Userdao;
@Transactional
@Component(value="RoastService")

public class RoastService {
	@Autowired
	private Dao dao;
	public boolean setRoast1(Roast roast){
		boolean isok=false;
		String sql="from Roast where moduleIdentifier=?";
		String values=roast.getModuleIdentifier();
		List<Roast> list=null;
		list=dao.query(sql,values);
		if(!list.isEmpty()){
			list.get(list.size()-1).setImageUrl(roast.getImageUrl());
			list.get(list.size()-1).setNewsUrl(roast.getNewsUrl());
			list.get(list.size()-1).setModuleIdentifier(roast.getModuleIdentifier());
			
			dao.update(list.get(list.size()-1));
			System.out.println(roast.getModuleIdentifier()+"轮播记录存在已更新");
			isok=true;
		}else{
				dao.save(roast);
				System.out.println(roast.getModuleIdentifier()+"轮播记录不存在已保存");
				isok=true;
		}
		return isok;
	}
	public Roast getRoast1(String values){
		String sql="from Roast where moduleIdentifier=?";
		List<Roast> roast=dao.query(sql, values);
		if(roast.isEmpty()){return null;}
		return roast.get(0);
		
	}
	public List<Roast> queryAll(){
		String sql="from Roast where moduleIdentifier=?";
		String value="首页";
		List<Roast> roastlist=dao.query(sql, value);
		String values="生活";
		List<Roast> roastlist2=dao.query(sql, values);
		roastlist.addAll(roastlist2);
		
		return roastlist;
	}
}
