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

	// ��½
	@Override
	public String execute() throws Exception {
	String	account = request.getParameter("account");
	String	password = request.getParameter("password");

		String info = null;
		if (account != null && password != null && account.equals("admin") && password.equals("123")) {
			session.setAttribute("admin", "�ѵ�¼");
			info = "success";
			try {
				Thread thread = Thread.currentThread();
				thread.sleep(4000);// ��ͣ4���������ִ��
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			addActionError("�˺Ż���������");
			info = "input";
		}
		System.out.println(account + ":" + password);
		System.out.println("����Ա��½:" + info);

		return info;
	}

	// �˳�
	public String loginout() {
		session.setAttribute("admin", "δ��¼");
		session.invalidate();
		System.out.println("session������");
		addActionError("�ɹ��˳�");
		return "loginout";
	}
	
	//������Ʒ
	public String searchGoods(){
		Panduanstr p=new Panduanstr();
		String SearchGoods=request.getParameter("SearchGoods");
		System.out.println("��ѯ�Ĺؼ���:"+SearchGoods);
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
			System.out.println("�������Ʒ���û���Ϣ:"+list);
		}
			
		return "returnUgoods";
	}
	//�ٴβ�ѯ��Ʒ
	public String research(String SearchGoods){
		Panduanstr p=new Panduanstr();
		System.out.println("�ٴβ�ѯ�Ĺؼ���:"+SearchGoods);
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
			System.out.println("�������Ʒ���û���Ϣ:"+list);
		}
		
		return "returnUgoods";
	}
	//ɾ����Ʒ
	public String deletegoods() throws ServletException, IOException{
		
		String goodsid=request.getParameter("deletegoods")+"";
		
		System.out.println("Ҫɾ������ƷID��"+goodsid);
		//ɾ����Ʒ
		if(goodsid !=null){
		boolean b=taoyuService.deletegoods(goodsid);
		if(b){
			System.out.println("IDΪ:"+goodsid+"����Ʒɾ���ɹ�");
		}else{System.out.println("IDΪ:"+goodsid+"����Ʒɾ��ʧ��");}
		}
		//��ѯ
		 value=request.getSession().getAttribute("SearchGoods")+"";

		 research(value);

		return "returnUgoods";
	}
	//�����ְ�ķ���
	public String QueryPartTime(){
		String status=null;
		this.clearErrorsAndMessages();
		if(action==null){System.err.println("����Ա��ѯ��ְactionΪ��");return null;}
		
		if(action.equals("��ѯ����˼�ְ")){
			action=(String) request.getAttribute("action");//ʹ��action��ǩ���͵�ֵҪ���������,��������setget����
			status="�������";
			partTime=partTimeService.QueryPartTimeBystatus(status);
			System.out.println(partTime);
			addActionMessage("Ŀǰ��"+partTime.size()+"������˼�ְ");
		}else if(action.equals("approved")){
			System.out.println("partTimeId="+partTimeId);
			status="���ͨ��";
			Integer id=Integer.valueOf(partTimeId);
			partTimeService.UpdatePartTimeStatus(status, id);
			this.addActionMessage("�޸ĳɹ�");
			return "partTime";//��ת����ѯ��ְ��ҳ�� action��ǩ��ѯ
			
		}else if(action.equals("notapproved")){
			System.out.println("partTimeId="+partTimeId);
			status="���δͨ��";
			Integer id=Integer.valueOf(partTimeId);
			partTimeService.UpdatePartTimeStatus(status, id);
			this.addActionMessage("�޸ĳɹ�");
			return "partTime";
		}else if(action.equals("������ְ")){
			System.out.println("����Ա������ְ");
			partTime=partTimeService.SearchPartTime(SearchGoods);
			
		}
		
		return "showPartTime";//��ת����ʾ��ְ�����ҳ��
		
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
