package life.taoyu.action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import life.taoyu.entity.Goods;
import life.taoyu.entity.Order;
import life.taoyu.entity.OrderItems;
import life.taoyu.modeldriver.OrderAndItems;
import life.taoyu.modeldriver.OrderGoods;
import life.taoyu.modeldriver.Order_Ugoods;
import life.taoyu.service.OrderService;
import life.taoyu.service.TaoyuService;
import zzu.util.Getjson;
import zzu.util.Returndata;
import zzu.util.Sort;
@Transactional
@Component(value ="OrderAction")
@Scope(value = "prototype")
public class OrderAction extends ActionSupport implements ModelDriven<OrderAndItems> {

	/**
	 * ��������
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OrderAndItems OAI =new OrderAndItems();
	
	@Autowired
	private OrderService orderService;
	
	private Getjson getJsonArray=new Getjson();
	@Override
	public OrderAndItems getModel() {
		// TODO Auto-generated method stub
		return OAI;
	}
	

	

	boolean Successful = false;
    HttpServletRequest request=ServletActionContext.getRequest();

	@Override
	public String execute() throws Exception {
		//�Ѱ�׿�˴�����String����Gson�ַ���תΪ���󣬲���ӵ�ģ��������
		if(OAI.getStrOrderData()!=null && !OAI.getStrOrderData().isEmpty()){
		  Gson gson = new Gson();  
		    String json = OAI.getStrOrderData();  	  
		    // jsonתΪ��Bean  
		    Order order = gson.fromJson(json, Order.class);  
		    System.out.println("order:"+order);  
		    OAI.setOrderData(order);//���ö�������
		    System.out.println("������list:"+OAI.getStrOrderItemsData());
		    List<OrderItems> orlist=new ArrayList<OrderItems>();
		    for (String orderItems : OAI.getStrOrderItemsData()) {
		    	  Gson gson2 = new Gson();  
				    String json2 = orderItems;  				  
				    // jsonתΪ��Bean  
				    OrderItems orderitems = gson2.fromJson(json2, OrderItems.class); 
				    
				    orlist.add(orderitems);
			}
		    OAI.setOrderItemsData(orlist);
		}

		System.out.println(OAI);
		
		 if(OAI.getAction()==null){ System.err.println("��������actionΪ��"); return null;}
		 switch (OAI.getAction()) {
		case "���ɶ���":
                    Successful= orderService.savaorder(OAI);
			        Returndata.returnboolean(Successful);
			break;
		case "��ѯ��Ҷ���":
			List<Order_Ugoods> OUG = orderService.queryorder(OAI);
			List<Order_Ugoods> OUG2=	Sort.sortByStatus(OUG);//���մ�����/�Ѹ������ȵ�ԭ��
			Returndata.returndata(getJsonArray.getjsonarray(OUG2, "��ѯ��Ҷ���"));
			break;
		case "����"://�����ɾ��������û��ʹ��SessionID��֤һ���û���
			Integer id=Integer.valueOf(OAI.getOrderID());
			 System.out.println(id);
			 Order o=new Order();
			 o.setOrder_id(id);
			 o.setOrder_status("�Ѹ���");
			Successful= orderService.updateOrderStatus(o);
			 Returndata.returnboolean(Successful);
			break;
		case "ɾ������":
			Integer id2=Integer.valueOf(OAI.getOrderID());
			 System.out.println(id2);
			 Successful= orderService.deleteOrder(id2);
			 Returndata.returnboolean(Successful);
			break;
		case "��ѯ���Ҷ���":
			List<OrderGoods> OGlist=orderService.querySellerOrder(OAI.getSessionID());
			List<OrderGoods> OG2=	Sort.sortByStatus2(OGlist);//���մ�����/�Ѹ������ȵ�ԭ��
			Returndata.returndata(getJsonArray.getjsonarray(OG2, "��ѯ���Ҷ���"));
			break;
		
		default:
			System.err.println("��������actionΪ�մ�");
			 Returndata.returnboolean(false);
			break;
		}
		 
		return null;
	}
	 
	
	
	
}
