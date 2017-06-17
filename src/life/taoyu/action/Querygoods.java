package life.taoyu.action;

import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionSupport;

import life.taoyu.modeldriver.Ugoods;
import life.taoyu.service.TaoyuService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zzu.util.Getjson;

@Component(value = "querygoodsAction")
@Scope(value = "prototype")

public class Querygoods extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource(name = "taoyuService")
	private TaoyuService taoyuService;
	private String action = null;// 接收前台发来的分类查询动作
	private int num = 0;// 接受前台发来的数字，用于从第num条数据开始查询

	public void setNum(int num) {
		this.num = num;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String execute() throws Exception {
		String sql = null;

		Getjson array = new Getjson();
		List<Ugoods> list = new ArrayList<Ugoods>();
		JSONObject goods = new JSONObject();
		System.out.println(action + "|||||" + num);
		if (action.equals("学习") || action.equals("娱乐") || action.equals("出行")) {
			sql = "from Goods where Gtype=? order by Goods_id desc";
			list = taoyuService.query(action, num, sql);
			goods = array.getjsonarray(list, action);// 传递集合和要去掉的级联属性，获取json数组
			System.out.println("分类查询操作");
		} else if (action != null && !action.equals("")) {

			sql = "from Goods where Gsearch like ? order by Goods_id desc";
			action = "%" + action + "%";
			list = taoyuService.query(action, num, sql);
			action = "搜索";
			goods = array.getjsonarray(list, action);
			System.out.println("搜索查询操作");
		}

		// 返回数据
		if (goods != null) {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");

			PrintWriter out = response.getWriter();
			out.println(goods);
			out.flush();
			out.close();
		} else {
			System.out.println("商品信息为空");
		}

		return NONE;
	}
}
