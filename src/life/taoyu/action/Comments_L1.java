package life.taoyu.action;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import life.taoyu.modeldriver.L1_Comments_Modeldriver;
import life.taoyu.modeldriver.Ucomments;
import life.taoyu.service.CommentService;
import life.taoyu.service.TaoyuService;
import net.sf.json.JSONObject;
import zzu.util.Getjson;
import zzu.util.Returndata;

@Transactional
@Component(value = "comments_L1Action")
@Scope(value = "prototype")
public class Comments_L1 extends ActionSupport implements ModelDriven<L1_Comments_Modeldriver> {

	/**
	 * 一级评论（商品的评论），查，存，修
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private CommentService commentService;

	private Getjson jsonarray = new Getjson();

	private L1_Comments_Modeldriver CMD = new L1_Comments_Modeldriver();

	@Override
	public L1_Comments_Modeldriver getModel() {
		// TODO Auto-generated method stub
		return CMD;
	}

	@Override
	public String execute() throws Exception {
		System.out.println(CMD);
		JSONObject comments = null;
		// 查询一级评论
		if (CMD.getAction().equals("querycomments_L1")) {
			CMD.setSql("from Comments_L1 where GC_id=? order by L1_Cid desc");
			CMD.setValues(CMD.getGoods_id().toString());
			List<Ucomments> commentslist = commentService.querycomments(CMD);
			if (commentslist != null) {
				CMD.setAction("一级评论");
				comments = jsonarray.getjsonarray(commentslist, CMD.getAction());
			} else {
				System.out.println("没有查询到一级评论");
			}
		}
		// 发表一级评论
		else if (CMD.getAction().equals("postcomments_L1") && CMD.getGoods_id() != null && CMD.getSessionID() != null) {
			CMD.setSql("from Goods where Goods_id=?");
			CMD.setValues(CMD.getGoods_id());
			Serializable id = commentService.postcomments(CMD);

		comments=Getjson.getjsonobject(id);
			
		}

		// 返回数据
		Returndata.returndata(comments);

		return null;
	}

}
