package life.taoyu.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import life.taoyu.dao.Dao_taoyu;
import life.taoyu.entity.Goods;
import life.taoyu.entity.Order;
import life.taoyu.entity.OrderItems;
import life.taoyu.modeldriver.OrderAndItems;
import life.taoyu.modeldriver.OrderGoods;
import life.taoyu.modeldriver.Order_Ugoods;
import life.taoyu.modeldriver.Ugoods;
import persionalCenter.dao.Dao;
import persionalCenter.entity.User;
import persionalCenter.entity.UserInfo;
import zzu.util.GetDate;

@Transactional
@Component(value = "OrderService")
@Scope(value = "prototype")
public class OrderService {
	@Resource(name = "user_Dao")
	private Dao dao;
	@Resource(name = "taoyuDao")
	private Dao_taoyu TDao;
	
	
	
	
	// 保存订单
		public boolean savaorder(OrderAndItems orderdata) {
			  
			boolean Successful = false;
			Serializable	id = null;
			Integer total=0;
			List<OrderItems> itemsprice = orderdata.getOrderItemsData();
			for (OrderItems orderItems : itemsprice) {
				int count=orderItems.getCount();
			String	sql="from Goods where Goods_id=?";
				List<Goods> goods=dao.query(sql, orderItems.getGoods_id());
				String Gprice=goods.get(0).getGprice();
				count=Integer.valueOf(count);
				int p=Integer.valueOf(Gprice);
				total+=(p*count);
				System.out.println(p+"*"+count);
				System.out.println("total:"+total);
			}
			System.out.println("total:"+total);
			String sql = "from User where SessionID=?";
			String values = orderdata.getSessionID();
			List<User> list = dao.query(sql, values);
			if (list.size() == 0) {
				System.out.println("保存订单，用户没找到");
				return false;
			}
			
			// 查出用户，级联保存订单信息
			for (User user : list) {
				Order order = orderdata.getOrderData();
				order.setOrder_date(GetDate.GetNowDate());
				order.setOrder_status("待付款");
				order.setTotal(total.toString());
				order.setUser(user);
				user.getSetorder().add(order);
					id = this.dao.save(order);
				System.out.println("保存订单成功");
			}
			sql = "from Order where order_id=?";

			Integer id1 = Integer.valueOf(id + "");
			List<Order> orderlist = dao.query(sql, id1);

			List<OrderItems> items = orderdata.getOrderItemsData();
			for (OrderItems orderItems : items) {
				
				orderItems.setOrder(orderlist.get(0));
				this.dao.save(orderItems);
				System.out.println("保存订单项成功");
				Successful = true;

			}
			return Successful;
			 }
		

	// 查询买家所有订单
		public List<Order_Ugoods> queryorder(OrderAndItems OAI) {
			 
			
			String sql = "from User where SessionID=?";
			String values = OAI.getSessionID();
			List<User> list = dao.query(sql, values);
			Integer uid = list.get(0).getUid();
			List<Order_Ugoods> listOUG = new ArrayList<Order_Ugoods>();
			// Order_Ugoods OUG=new Order_Ugoods();//存储 订单，商品，卖家信息的对象

			// Ugoods ug=new Ugoods();
			// 查出一堆订单
			sql = "from Order where UO_id=? order by id desc";
			List<Order> list2 = dao.query(sql, uid);
			for (int i = 0; i < list2.size(); i++) {
				List<Ugoods> listUG = new ArrayList<Ugoods>();// 存储商品和卖家信息对象的集合
				Order_Ugoods OUG = new Order_Ugoods();// 存储商品和卖家信息对象和订单对象的对象

				OUG.setOrderdata(list2.get(i));// 订单对象放入Order_Ugoods中
				// 查出一堆订单项
				sql = "from OrderItems where OOItems_id=?";
				List<OrderItems> list3 = dao.query(sql, list2.get(i).getOrder_id());
				// 查商品信息

				for (int j = 0; j < list3.size(); j++) {// 查出商品和用户信息对象，存入ug对象中
					Ugoods ug = new Ugoods();
					ug.setCount(list3.get(j).getCount());
					sql = "from Goods where goods_id=?";
					List<Goods> list4 = dao.query(sql, list3.get(j).getGoods_id());
					for (Goods goods : list4) {
						String gprice=goods.getGprice();
						if(gprice==null){gprice="0";}
						int   total=Integer.valueOf(gprice) * list3.get(j).getCount();
						ug.setTotal(total);
						ug.setGoods(goods);
					}
					
					// 查卖家信息
					for (Goods goods : list4) {
						sql = "from UserInfo where ul_id=?";
						List<UserInfo> list5 = dao.query(sql, goods.getUser().getUid());
						UserInfo userinfo = list5.get(0);

						ug.setUserinfo(userinfo);
						listUG.add(ug);// 把商品和卖家信息对象放入list集合
					}
				}
				OUG.setUgoods(listUG);// Ugoods对象放入Order_Ugoods中
				listOUG.add(OUG);// OUG对象放入listOUG集合里

			}
			return listOUG;
			 }
		

	// 修改订单状态
		public boolean updateOrderStatus(Order order) {
			 
			boolean Successful = false;
			String sql = "from Order where order_id=?";
			Integer id=order.getOrder_id();
			List<Order> orderlist=dao.query(sql, id);
			if(orderlist.size()==0){System.out.println("订单为检索到"); return false;}
			for (Order o : orderlist) {
				if(order.getTotal()!=null && !order.getTotal().isEmpty())
					o.setTotal(order.getTotal());
				if(order.getOrder_status()!=null && !order.getOrder_status().isEmpty())
				o.setOrder_status(order.getOrder_status());
				
				dao.update(o);
				Successful=true;
			}
			return Successful;
			 }
		
	//删除订单
		public boolean deleteOrder(Integer id){
			
		boolean	Successful=false;
	      String    sql = "from Order where order_id=?";		
			List<Order> order=dao.query(sql, id);
			if(order==null){System.out.println("订单未检索到"); return false;}
			
			dao.delete(order.get(0));
			Successful=true;
			return Successful;
			 }
		
	//查询卖家订单
		public List<OrderGoods> querySellerOrder(String SessionID){
			 
			List<OrderGoods> OGlist=new ArrayList<OrderGoods>();
		String	sql="from User where SessionID=?";
			List<User> user=dao.query(sql, SessionID);
			if(user.size()==0){System.out.println("查询买家商品操作,用户未检索到");return null;}
			Set<Goods> goods= user.get(0).getSetgoods();
			for (Goods g : goods) {
				
			sql="from OrderItems where goods_id=?";
			List<OrderItems> oi=dao.query(sql, g.getGoods_id());
			
					for (OrderItems orderItems : oi) {
						int count=orderItems.getCount();
						int total=orderItems.getCount()*(Integer.valueOf(g.getGprice()));
						OrderGoods og=new OrderGoods();
						Order order=orderItems.getOrder();
						order.setTotal(total+"");
								og.setOrder(order);
								og.setGoods(g);
								og.setCount(count);
								OGlist.add(og);
					}
			
			}System.out.println(OGlist);
			return OGlist;
			 }
}
