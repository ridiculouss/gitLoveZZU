package zzu.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import life.mytopiccircle.entity.Theme;
import life.mytopiccircle.moudledriver.UserTheme;
import life.taoyu.modeldriver.OrderGoods;
import life.taoyu.modeldriver.Order_Ugoods;

public class Sort {
	public static List<Order_Ugoods> sortByStatus(List<Order_Ugoods> OUG) {
		// 按照未付款，已付款和其他状态排序
		List<Order_Ugoods> frontlist = new LinkedList<>();
		List<Order_Ugoods> Behindlist = new LinkedList<>();
		for (Order_Ugoods oug : OUG) {
			if (oug.getOrderdata().getOrder_status().equals("待付款")
					|| oug.getOrderdata().getOrder_status().equals("已付款")) {
				frontlist.add(oug);
			} else {
				Behindlist.add(oug);
			}

		}

		frontlist.addAll(Behindlist);
		System.out.println("已排序的订单list:" + frontlist);
		return frontlist;
	}

	public static List<OrderGoods> sortByStatus2(List<OrderGoods> OGlist) {
		// 按照未付款，已付款和其他状态排序
		List<OrderGoods> frontlist = new LinkedList<>();
		List<OrderGoods> Behindlist = new LinkedList<>();
		for (OrderGoods orderGoods : OGlist) {
			if (orderGoods.getOrder().getOrder_status().equals("待付款")
					|| orderGoods.getOrder().getOrder_status().equals("已付款")) {
				frontlist.add(orderGoods);
			} else {
				Behindlist.add(orderGoods);
			}
		}
		frontlist.addAll(Behindlist);
		return frontlist;
	}

	// 按照主题的话题量大小排序
	public static List<UserTheme> sortTheme(List<UserTheme> list) {

		UserTheme[] arr=new UserTheme[list.size()];
		for(int i=0;i<list.size();i++){ arr[i]=list.get(i); }
		
		for(int i=1;i<arr.length;i++){
			UserTheme num=arr[i];
			for(int j=i-1;j>=0 && arr[j].getTheme().getTopicCount()<num.getTheme().getTopicCount();j--){
				arr[j+1]=arr[j];
				arr[j]=num;
				
			}
		}
	List<UserTheme> ut=new LinkedList<UserTheme>();
			 ut.addAll(Arrays.asList(arr));
		 System.out.println(ut);
		return ut;
	}
	public static void main(String[] args) {
		Theme t1,t2,t3,t4;
		List<UserTheme> arr=new ArrayList<UserTheme>();
		UserTheme u1=new UserTheme();
		UserTheme u2=new UserTheme();
		UserTheme u3=new UserTheme();
		UserTheme u4=new UserTheme();
				t1=new Theme();
				t2=new Theme();
				t3=new Theme();
				t4=new Theme();
				t1.setTopicCount(15);
				t2.setTopicCount(10);
				t3.setTopicCount(20);
				t4.setTopicCount(21);
				u1.setTheme(t1);u2.setTheme(t2);u3.setTheme(t3);u4.setTheme(t4);
				arr.add(u1);
				arr.add(u2);
				arr.add(u3);
				arr.add(u4);
				sortTheme(arr);	
	}
}
