package life.taoyu.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;

import life.taoyu.dao.Dao_taoyu;
import life.taoyu.entity.Cart;
import life.taoyu.entity.Comments_L1;
import life.taoyu.entity.Comments_L2;
import life.taoyu.entity.Goods;
import life.taoyu.entity.Order;
import life.taoyu.entity.OrderItems;
import life.taoyu.modeldriver.CGoods;
import life.taoyu.modeldriver.L1_Comments_Modeldriver;
import life.taoyu.modeldriver.L2_Comments_Modeldriver;
import life.taoyu.modeldriver.OrderAndItems;
import life.taoyu.modeldriver.OrderGoods;
import life.taoyu.modeldriver.Order_Ugoods;
import life.taoyu.modeldriver.UComments_L2;
import life.taoyu.modeldriver.Ucomments;
import life.taoyu.modeldriver.Ugoods;
import persionalCenter.dao.Dao;
import persionalCenter.entity.User;
import persionalCenter.entity.UserInfo;
import zzu.util.GetDate;

@Transactional
@Component(value = "taoyuService")

public class TaoyuService {

	@Resource(name = "user_Dao")
	private Dao dao;
	@Resource(name = "taoyuDao")
	private Dao_taoyu TDao;
	
	private GetDate date=new GetDate();


	// 级联保存商品操作
	public String savegoods(Goods goods) {
		
	boolean	Successful = false;
		String goods_id = null;
		Serializable	id = null;// 初始化id
	String	sql = "from User where SessionID=?";
	String	values = goods.getSessionID();
		List<User> list = dao.query(sql, values);

		for (User user : list) {
			goods.setGdate(date.GetNowDate());
			goods.setGsearch(goods.getGtype() + goods.getGname() + goods.getGcampus() + goods.getGdescribe());

			goods.setGcomments(0);
			user.getSetgoods().add(goods);
			goods.setUser(user);
			id = this.dao.save(goods);
			goods_id = id.toString();
			Successful = true;
		}

		if (Successful) {// 重置搜索字段的内容
			sql = "from Goods where Goods_id=?";
			resetSearch(sql, Integer.parseInt(goods_id));
		}
		return goods_id;
		 }
	

	// 删除商品
	public boolean deletegoods(String goodsID) {
		 
		boolean	Successful =false;
		Integer id = Integer.valueOf(goodsID);
		System.out.println("转为Integer:" + id);

	String	sql = "from Goods where Goods_id=?";
		List<Goods> list = this.dao.query(sql, id);

		for (Goods goods : list) {

			dao.delete(goods);
			Successful = true;
		}

		return Successful;
		 }
	

	// 分类查询商品
	public List<Ugoods> query(String action, int num, String sql) {
		

		List<Ugoods> uglist = new ArrayList<Ugoods>();

		// List<Goods> goodslist=dao.query(sql, values);
		List<Goods> goodslist = TDao.hqlquery(sql, action, num);
		for (Goods goods : goodslist) {
			Ugoods ug = new Ugoods();
			sql = "from UserInfo where ul_id=?";
		String	values = goods.getUser().getUid().toString();

			List<UserInfo> userinfo = dao.query(sql, values);
			for (UserInfo userInfo2 : userinfo) {

				ug.setUserinfo(userInfo2);
				ug.setGoods(goods);

			}
			uglist.add(ug);

		}
		return uglist;
		 }
	

	// 管理员查询商品，模糊搜索search字段
	public List<Ugoods> AdminQueryGoods(String sql) {
		List<Ugoods> uglist = new ArrayList<Ugoods>();
		List<Goods> goodslist = TDao.adminhqlquery(sql);
		for (Goods goods : goodslist) {
			Ugoods ug = new Ugoods();
			sql = "from UserInfo where ul_id=?";
		String	values = goods.getUser().getUid().toString();

			List<UserInfo> userinfo = dao.query(sql, values);
			for (UserInfo userInfo2 : userinfo) {

				ug.setUserinfo(userInfo2);
				ug.setGoods(goods);

			}
			uglist.add(ug);

		}
		return uglist;

	}

	// 返回图片名
	public String[] getImageName(String SessionID, String goods_id) {
		 
		String[] str = new String[10];

		String Account = null;
		String oldimageurl = null;
		String sql = "from User where SessionID=?";
		String values = SessionID;
		List<User> list2 = dao.query(sql, values);

		if (!list2.isEmpty()) {
			for (User user : list2) {
				Account = user.getAccount();

			}
			sql = "from Goods where Goods_id=?";
			values = goods_id;
			Integer integer = Integer.valueOf(goods_id);
			List<Goods> list3 = dao.query(sql, integer);

			for (Goods goods : list3) {
				oldimageurl = goods.getGimage();

			}

			str[0] = Account;
			str[1] = oldimageurl;
		}
		return str;
		 }
	

	// 更新商品信息
	public void updateGoods(Goods goods) {
		 
		boolean Successful=false;
		System.out.println("开始更新商品信息");
		String sql = "from Goods where Goods_id=?";
		Integer integer = Integer.valueOf(goods.getGoods_id());

		List<Goods> list = dao.query(sql, integer);
		if (list.size() == 1) {
			for (Goods querygoods2 : list) {
				if (goods.getGtype() != null && !goods.getGtype().equals("")) {
					querygoods2.setGtype(goods.getGtype());
				}
				if (goods.getGname() != null && !goods.getGname().equals("")) {
					querygoods2.setGname(goods.getGname());
				}
				if (goods.getGcampus() != null && !goods.getGcampus().equals("")) {
					querygoods2.setGcampus(goods.getGcampus());
				}
				if (goods.getGdescribe() != null && !goods.getGdescribe().equals("")) {
					querygoods2.setGdescribe(goods.getGdescribe());
				}
				if (goods.getGprice() != null && !goods.getGprice().equals("")) {
					querygoods2.setGprice(goods.getGprice());
				}
				if (goods.getGimage() != null && !goods.getGimage().equals("")) {
					querygoods2.setGimage(goods.getGimage());
				}
				if (goods.getGdate() != null && !goods.getGdate().equals("")) {
					querygoods2.setGdate(goods.getGdate());
				}

				if (goods.getGcomments() != null && !goods.getGcomments().equals("")) {
					querygoods2.setGcomments(goods.getGcomments());
				}

				this.dao.update(querygoods2);
				Successful = true;

				System.out.println("更新商品信息成功");

			}
		}
		if (Successful)
			resetSearch(sql, integer);
		 }
	

	// 重置商品搜索search字段内容
	public boolean resetSearch(String sql, Integer integer) {
		boolean Successful = false;
		List<Goods> list = dao.query(sql, integer);
		for (Goods goods : list) {
			goods.setGsearch(goods.getGoods_id() + goods.getGcampus() + goods.getGname() + goods.getGtype()
					+ goods.getGdescribe());
			this.dao.update(goods);
			Successful = true;
			System.out.println("重置商品搜索search字段内容成功!");
		}
		return Successful;
	}

	//// 添加商品到购物车
	public boolean AddGoodsToCart(String[] cartinfo) { // cart[0]=SessionID;cart[1]=goods_id;cart[2]=count+"";
		
		boolean Successful = false;
		String sql = "from User where SessionID=?";
		String values = cartinfo[0];
		List<User> list2 = dao.query(sql, values);
		for (User user : list2) {
			Cart cart = new Cart();
			cart.setGoods_id(Integer.valueOf(cartinfo[1]));
			cart.setCount(Integer.parseInt(cartinfo[2]));
			user.getSetcart().add(cart);
			cart.setUser(user);
			this.dao.save(cart);
			System.out.println("商品添加购物车成功");
			Successful = true;
		}

		return Successful;
		 }
	

	// 查询购物车中商品
	public List<CGoods> queryCart(String[] cartinfo) {
		
	String 	sql = "from User where SessionID=?";
		String values = cartinfo[0];
		List<User> list2 = dao.query(sql, values);
		Integer I = null;
		for (User user : list2) {
			I = user.getUid();

		}
		sql = "from Cart where UCart_id=?";
		List<Cart> list3 = dao.query(sql, I);
		List<CGoods> cglist = new ArrayList<CGoods>();

		for (int j = 0; j < list3.size(); j++) {
			CGoods cgoods = new CGoods();
			sql = "from Goods where Goods_id=?";
			List<Goods> list4 = dao.query(sql, list3.get(j).getGoods_id());
			System.out.println("第一步list4:" + j + "," + list4);
			cgoods.setCount(list3.get(j).getCount() + "");
			for (int i = 0; i < list4.size(); i++) {

				cgoods.setGoods(list4.get(i));

				cglist.add(cgoods);
				System.out.println("第二步");

			}
		}
		return cglist;
		 }
	
//卖家查询自己的发布商品
	public List<Goods> QueryPublishedGoods(String SessionID){
		
		List<Goods> glist = null;
		String sql="from User where SessionID=?";
		List<User> user=dao.query(sql, SessionID);
		for (User user2 : user) {
			sql="from Goods where UG_id=?";
			 glist=dao.query(sql, user2.getUid());
			
		}
		
		if(glist.size()==0){System.out.println("未查到已发布商品");}
		Collections.reverse(glist);
		
		return glist;
		 }
	
// 删除已发布的商品
	public boolean deletePublishedGoods(String goods_id){
		 
		boolean Successful=false;
		String sql="from Goods where Goods_id=?";
		Integer id=Integer.valueOf(goods_id);
		List<Goods> goods=dao.query(sql, id);
		for (Goods goods2 : goods) {
			dao.delete(goods2);
			Successful=true;	
			}
			return Successful;
		 }
	
	// 删除购物车中商品
	public boolean DeleteGoodsFromCart(String[] cartinfo) {
		 
		boolean Successful = false;
		String sql = "from Cart where goods_id=?";

		List<Cart> list2 = dao.query(sql, Integer.valueOf(cartinfo[1]));
		for (Cart cart : list2) {
			this.dao.delete(cart);

			Successful = true;
		}
		return Successful;
	}

	
	
}
