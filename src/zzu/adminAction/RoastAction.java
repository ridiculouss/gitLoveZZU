package zzu.adminAction;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import zzu.util.Getjson;
import zzu.util.Panduanstr;
import zzu.util.Returndata;
@Transactional
@Component(value="Roasting")
@Scope(value="prototype")
public class RoastAction extends ActionSupport implements ModelDriven<Roast>{
	/*
	 * 
	 * 处理轮播图片
	 * 轮播action目前分 首页,生活
	 */
	Getjson json=new Getjson();
	List<Roast> list=new ArrayList<Roast>();
	public List<Roast> getList() {
		return list;
	}
	Panduanstr p=new Panduanstr();
	@Resource(name="RoastService")
	RoastService rs;
	@Override
	public String execute() throws Exception {
		System.out.println(action+r);
		if(action!=null&&action.equals("设置轮播")){
			
			boolean isok=rs.setRoast1(r);
			if(isok)
			addActionError("添加成功");
			else
				addActionError("添加失败");
			System.out.println("轮播设置操作成功!");
			return "ok";
		}else if(action!=null && !action.equals("")&&!action.equals("查询")){
			List<Roast> rlist=new ArrayList<>();
			//获取轮播
			Roast r=rs.getRoast1(action);
			System.out.println("轮播获取操作成功!");
			String[] imgstr=p.fenli(r.getImageUrl());
			String[] newsstr=p.fenli(r.getNewsUrl());
			for(int i=0;i<imgstr.length;i++){
				Roast roast=new Roast();
				roast.setImageUrl(imgstr[i]);
				roast.setNewsUrl(newsstr[i]);
				roast.setModuleIdentifier(r.getModuleIdentifier());
				rlist.add(roast);
			}
			
			Returndata.returndata(json.getjsonarray(rlist, "轮播"));
		}
		if(action!=null&&action.equals("查询")){
			 list=rs.queryAll();
			System.out.println(list);
			return "ok";
		}
		return null;
	
		
	}
	
	Roast r=new Roast();
	@Override
	public Roast getModel() {
		// TODO Auto-generated method stub
		return r;
	}
	String action;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
}

