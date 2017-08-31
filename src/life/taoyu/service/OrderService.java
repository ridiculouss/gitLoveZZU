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
	
	
	
	
	// ���涩��
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
				System.out.println("���涩�����û�û�ҵ�");
				return false;
			}
			
			// ����û����������涩����Ϣ
			for (User user : list) {
				Order order = orderdata.getOrderData();
				order.setOrder_date(GetDate.GetNowDate());
				order.setOrder_status("������");
				order.setTotal(total.toString());
				order.setUser(user);
				user.getSetorder().add(order);
					id = this.dao.save(order);
				System.out.println("���涩���ɹ�");
			}
			sql = "from Order where order_id=?";

			Integer id1 = Integer.valueOf(id + "");
			List<Order> orderlist = dao.query(sql, id1);

			List<OrderItems> items = orderdata.getOrderItemsData();
			for (OrderItems orderItems : items) {
				
				orderItems.setOrder(orderlist.get(0));
				this.dao.save(orderItems);
				System.out.println("���涩����ɹ�");
				Successful = true;

			}
			return Successful;
			 }
		

	// ��ѯ������ж���
		public List<Order_Ugoods> queryorder(OrderAndItems OAI) {
			 
			
			String sql = "from User where SessionID=?";
			String values = OAI.getSessionID();
			List<User> list = dao.query(sql, values);
			Integer uid = list.get(0).getUid();
			List<Order_Ugoods> listOUG = new ArrayList<Order_Ugoods>();
			// Order_Ugoods OUG=new Order_Ugoods();//�洢 ��������Ʒ��������Ϣ�Ķ���

			// Ugoods ug=new Ugoods();
			// ���һ�Ѷ���
			sql = "from Order where UO_id=? order by id desc";
			List<Order> list2 = dao.query(sql, uid);
			for (int i = 0; i < list2.size(); i++) {
				List<Ugoods> listUG = new ArrayList<Ugoods>();// �洢��Ʒ��������Ϣ����ļ���
				Order_Ugoods OUG = new Order_Ugoods();// �洢��Ʒ��������Ϣ����Ͷ�������Ķ���

				OUG.setOrderdata(list2.get(i));// �����������Order_Ugoods��
				// ���һ�Ѷ�����
				sql = "from OrderItems where OOItems_id=?";
				List<OrderItems> list3 = dao.query(sql, list2.get(i).getOrder_id());
				// ����Ʒ��Ϣ

				for (int j = 0; j < list3.size(); j++) {// �����Ʒ���û���Ϣ���󣬴���ug������
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
					
					// ��������Ϣ
					for (Goods goods : list4) {
						sql = "from UserInfo where ul_id=?";
						List<UserInfo> list5 = dao.query(sql, goods.getUser().getUid());
						UserInfo userinfo = list5.get(0);

						ug.setUserinfo(userinfo);
						listUG.add(ug);// ����Ʒ��������Ϣ�������list����
					}
				}
				OUG.setUgoods(listUG);// Ugoods�������Order_Ugoods��
				listOUG.add(OUG);// OUG�������listOUG������

			}
			return listOUG;
			 }
		

	// �޸Ķ���״̬
		public boolean updateOrderStatus(Order order) {
			 
			boolean Successful = false;
			String sql = "from Order where order_id=?";
			Integer id=order.getOrder_id();
			List<Order> orderlist=dao.query(sql, id);
			if(orderlist.size()==0){System.out.println("����Ϊ������"); return false;}
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
		
	//ɾ������
		public boolean deleteOrder(Integer id){
			
		boolean	Successful=false;
	      String    sql = "from Order where order_id=?";		
			List<Order> order=dao.query(sql, id);
			if(order==null){System.out.println("����δ������"); return false;}
			
			dao.delete(order.get(0));
			Successful=true;
			return Successful;
			 }
		
	//��ѯ���Ҷ���
		public List<OrderGoods> querySellerOrder(String SessionID){
			 
			List<OrderGoods> OGlist=new ArrayList<OrderGoods>();
		String	sql="from User where SessionID=?";
			List<User> user=dao.query(sql, SessionID);
			if(user.size()==0){System.out.println("��ѯ�����Ʒ����,�û�δ������");return null;}
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
