package zzu.adminAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;

import life.parTime.entity.PartTime;
import life.parTime.service.PartTimeService;
import life.taoyu.modeldriver.Gimgs;
import life.taoyu.modeldriver.Ugoods;
import life.taoyu.service.TaoyuService;
import zzu.util.Panduanstr;

@Transactional
@Component(value = "admin")
@Scope(value = "prototype")
public class Admin extends ActionSupport {
	
	@Resource(name = "taoyuService")
	private TaoyuService taoyuService;
	@Autowired
	private PartTimeService partTimeService;
	HttpServletRequest request = ServletActionContext.getRequest();

	String value,SearchGoods,action,partTimeId;
	HttpSession session = request.getSession();
	List<Ugoods> list = new ArrayList<Ugoods>();
	List<PartTime> partTime=new ArrayList<PartTime>();
	String[] str;

	public String[] getStr() {
		return str;
	}

	public List<Ugoods> getList() {
		return list;
	}

	// 登陆
	@Override
	public String execute() throws Exception {
	String	account = request.getParameter("account");
	String	password = request.getParameter("password");

		String info = null;
		if (account != null && password != null && account.equals("admin") && password.equals("123")) {
			session.setAttribute("admin", "已登录");
			info = "success";
			try {
				Thread thread = Thread.currentThread();
				thread.sleep(4000);// 暂停4秒后程序继续执行
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			addActionError("账号或密码有误");
			info = "input";
		}
		System.out.println(account + ":" + password);
		System.out.println("管理员登陆:" + info);

		return info;
	}

	// 退出
	public String loginout() {
		session.setAttribute("admin", "未登录");
		session.invalidate();
		System.out.println("session已销毁");
		addActionError("成功退出");
		return "loginout";
	}
	
	//搜索商品
	public String searchGoods(){
		Panduanstr p=new Panduanstr();
		String SearchGoods=request.getParameter("SearchGoods");
		System.out.println("查询的关键字:"+SearchGoods);
		request.getSession().setAttribute("SearchGoods", SearchGoods);
		if(SearchGoods!=null){
			String sql="from Goods where Gsearch like '%"+SearchGoods+"%'";
			
			list=taoyuService.AdminQueryGoods(sql);
			
			for (Ugoods ugoods : list) {
				List<Gimgs> g=new ArrayList<Gimgs>();
				 str=p.fenli(ugoods.getGoods().getGimage());
				for(int i=0;i<str.length;i++){
					Gimgs Gimg=new Gimgs();
					Gimg.setImg(str[i]);
					g.add(Gimg);
				}
					ugoods.setGimgs(g);
				      
			}
			System.out.println("查出的商品和用户信息:"+list);
		}
			
		return "returnUgoods";
	}
	//再次查询商品
	public String research(String SearchGoods){
		Panduanstr p=new Panduanstr();
		System.out.println("再次查询的关键字:"+SearchGoods);
		if(SearchGoods!=null){
			String sql="from Goods where Gsearch like '%"+SearchGoods+"%'";
			
			list=taoyuService.AdminQueryGoods(sql);
			
			for (Ugoods ugoods : list) {
				List<Gimgs> g=new ArrayList<Gimgs>();
				str=p.fenli(ugoods.getGoods().getGimage());
				for(int i=0;i<str.length;i++){
					Gimgs Gimg=new Gimgs();
					Gimg.setImg(str[i]);
					g.add(Gimg);
				}
				ugoods.setGimgs(g);
				
			}
			System.out.println("查出的商品和用户信息:"+list);
		}
		
		return "returnUgoods";
	}
	//删除商品
	public String deletegoods() throws ServletException, IOException{
		
		String goodsid=request.getParameter("deletegoods")+"";
		
		System.out.println("要删除的商品ID："+goodsid);
		//删除商品
		if(goodsid !=null){
		boolean b=taoyuService.deletegoods(goodsid);
		if(b){
			System.out.println("ID为:"+goodsid+"的商品删除成功");
		}else{System.out.println("ID为:"+goodsid+"的商品删除失败");}
		}
		//查询
		 value=request.getSession().getAttribute("SearchGoods")+"";

		 research(value);

		return "returnUgoods";
	}
	//管理兼职的方法
	public String QueryPartTime(){
		String status=null;
		this.clearErrorsAndMessages();
		if(action==null){System.err.println("管理员查询兼职action为空");return null;}
		
		if(action.equals("查询待审核兼职")){
			action=(String) request.getAttribute("action");//使用action标签发送的值要用这个接收,其他的用setget方法
			status="正在审核";
			partTime=partTimeService.QueryPartTimeBystatus(status);
			System.out.println(partTime);
			addActionMessage("目前有"+partTime.size()+"条待审核兼职");
		}else if(action.equals("approved")){
			System.out.println("partTimeId="+partTimeId);
			status="审核通过";
			Integer id=Integer.valueOf(partTimeId);
			partTimeService.UpdatePartTimeStatus(status, id);
			this.addActionMessage("修改成功");
			return "partTime";//跳转到查询兼职的页面 action标签查询
			
		}else if(action.equals("notapproved")){
			System.out.println("partTimeId="+partTimeId);
			status="审核未通过";
			Integer id=Integer.valueOf(partTimeId);
			partTimeService.UpdatePartTimeStatus(status, id);
			this.addActionMessage("修改成功");
			return "partTime";
		}else if(action.equals("搜索兼职")){
			System.out.println("管理员搜索兼职");
			partTime=partTimeService.SearchPartTime(SearchGoods);
			
		}
		
		return "showPartTime";//跳转到显示兼职详情的页面
		
	}
	
	
	
	public String getPartTimeId() {
		return partTimeId;
	}

	public void setPartTimeId(String partTimeId) {
		this.partTimeId = partTimeId;
	}

	public List<PartTime> getPartTime() {
		return partTime;
	}

	public void setPartTime(List<PartTime> partTime) {
		this.partTime = partTime;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSearchGoods() {
		return SearchGoods;
	}

	public void setSearchGoods(String searchGoods) {
		SearchGoods = searchGoods;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
