package life.taoyu.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import life.taoyu.modeldriver.OrderAndItems;
import life.taoyu.modeldriver.Order_Ugoods;
import life.taoyu.service.TaoyuService;
import net.sf.json.JSONObject;
import zzu.util.Getjson;
import zzu.util.Returndata;
import zzu.util.TestOO;

@Component(value = "OrderAction")
@Scope(value = "prototype")
public class OrderAction extends ActionSupport implements ModelDriven<OrderAndItems> {

	/**
	 * 订单操作
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource(name = "OrderAndItems")
	private OrderAndItems OAI;
	@Resource(name = "taoyuService")
	private TaoyuService taoyuService;
	
	@Resource(name = "GetjsonArray")
	private Getjson getJsonArray;
	@Override
	public OrderAndItems getModel() {
		// TODO Auto-generated method stub
		return OAI;
	}

	JSONObject Json;

	public JSONObject getJson() {
		return Json;
	}

	public void setJson(JSONObject json) {
		Json = json;
	}

	boolean Successful = false;

	@Override
	public String execute() throws Exception {
		OrderAndItems orderdata = TestOO.GetJsonObject();
		OAI = orderdata;
		if (OAI == null) {
			return null;
		} else if (OAI.getAction().equals("生成订单")) {
			Successful = taoyuService.savaorder(OAI);
			Returndata.returnboolean(Successful);
		} else if (OAI.getAction().equals("查询订单")) {
			List<Order_Ugoods> OUG = taoyuService.queryorder(OAI);
			
			Returndata.returndata(getJsonArray.getjsonarray(OUG, "查询订单"));
		}

		return null;
	}
}
