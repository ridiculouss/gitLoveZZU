package life.taoyu.action;

import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;

import life.taoyu.entity.Goods;
import life.taoyu.modeldriver.Ugoods;
import life.taoyu.service.TaoyuService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zzu.util.Getjson;
import zzu.util.Returndata;
@Transactional
@Component(value = "querygoodsAction")
@Scope(value = "prototype")

public class Querygoods extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource(name = "taoyuService")
	private TaoyuService taoyuService;
	
	private Getjson getJsonArray=new Getjson();
	private String action = null;// ����ǰ̨�����ķ����ѯ����
	private int num = 0;// ����ǰ̨���������֣����ڴӵ�num�����ݿ�ʼ��ѯ

	public void setNum(int num) {
		this.num = num;
	}

	public void setAction(String action) {
		this.action = action;
	}

	String SessionID,goods_id;



	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	@Override
	public String execute() throws Exception {
		String sql = null;

		
		List<Ugoods> list = new ArrayList<Ugoods>();
		
		System.out.println(action + "|||||" + num);
		if (action.equals("ѧϰ") || action.equals("����") || action.equals("����")) {
			sql = "from Goods where Gtype=? order by Goods_id desc";
			list = taoyuService.query(action, num, sql);
			
			System.out.println("�����ѯ����");
			// ��������
			  Returndata.returndata(getJsonArray.getjsonarray(list, action));
		} 

		else if(action!=null&& action.equals("��ѯ�ѷ�����Ʒ")){
			sql="1";//�����Ƿ����ģ����ѯ
		  List<Goods> GoodsList=taoyuService.QueryPublishedGoods(SessionID);
			System.out.println("��ѯ�ķ�������Ʒ:"+GoodsList);
			Returndata.returndata(getJsonArray.getjsonarray(GoodsList, action));
			
	  }else if(action !=null && action.equals("ɾ����Ʒ")){
		  sql="1";//�����Ƿ����ģ����ѯ
		  Boolean Successful=taoyuService.deletePublishedGoods(goods_id);
		  System.out.println("ɾ���ѷ�����Ʒ:"+Successful);
		  Returndata.returnboolean(Successful);
		  
	  }else if (action != null && !action.equals("") && sql==null) {

			sql = "from Goods where Gsearch like ?  order by Goods_id desc";
			action = action + "%";
			list = taoyuService.query(action, num, sql);
			action = "ģ��������Ʒ";
			
			System.out.println("������ѯ����");
			// ��������
			  Returndata.returndata(getJsonArray.getjsonarray(list, action));
		}
	

		return NONE;
	}
}
