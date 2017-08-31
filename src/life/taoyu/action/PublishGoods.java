package life.taoyu.action;

import java.io.PrintWriter;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import life.taoyu.entity.Goods;
import life.taoyu.service.TaoyuService;
import net.sf.json.JSONObject;
@Transactional
@Component(value = "publishgoodsAction")
@Scope(value = "prototype")
public class PublishGoods extends ActionSupport implements ModelDriven<Goods> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Goods goods=new Goods();
	@Resource(name = "taoyuService")
	private TaoyuService taoyuService;

	@Override
	public Goods getModel() {
		// TODO Auto-generated method stub
		return goods;
	}

	@Override//�����·�������Ʒ
	public String execute() throws Exception {
		boolean isSuccessful = false;
		String goods_id=null;
		System.out.println("SessionID:" + goods.getSessionID());
		System.out.println("��Ʒ��Ϣ����:" + goods.toString());
		if (goods.getSessionID() != null) {
			goods_id = taoyuService.savegoods(goods);
            if(goods_id !=null){
            	//ִ��һ�θ�����Ʒ�·�����Ʒ��id��search�ֶ�
                Goods g=	new Goods();
                 g.setGoods_id(Integer.valueOf(goods_id));
            	taoyuService.updateGoods(g);
            	isSuccessful=true;}
			
			// ��������
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			JSONObject json = new JSONObject();
			json.put("isSuccessful", isSuccessful);
			json.put("goods_id", goods_id);

			System.out.println("QueryUserInfoAction��json" + json);

			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} else {
			System.out.println("SessionIDΪ�գ�");
		}
		return NONE;
	}

}
