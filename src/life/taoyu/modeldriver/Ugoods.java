package life.taoyu.modeldriver;

import life.taoyu.entity.Goods;
import persionalCenter.entity.UserInfo;

//建存储用户对象和商品对象的模型 Ugoods
public class Ugoods {

	private UserInfo userinfo;
	private Goods goods;

	public UserInfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	@Override
	public String toString() {
		return "Ugoods [userinfo=" + userinfo + ", goods=" + goods + "]";
	}
	
}
