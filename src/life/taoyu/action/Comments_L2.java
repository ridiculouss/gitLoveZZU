package life.taoyu.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import life.taoyu.modeldriver.L2_Comments_Modeldriver;
import life.taoyu.modeldriver.UComments_L2;
import life.taoyu.service.CommentService;
import life.taoyu.service.TaoyuService;
import zzu.themb.ThembModuel;
import zzu.themb.ThembRecord;
import zzu.util.Getjson;
import zzu.util.Returndata;

@Transactional
@Component(value = "comments_L2Action")
@Scope(value = "prototype")
public class Comments_L2 extends ActionSupport implements ModelDriven<L2_Comments_Modeldriver> {

	/**
	 * 二级评论(一级评论的评论)，查，存，修
	 */
	private static final long serialVersionUID = 1L;

	private L2_Comments_Modeldriver CMD2 = new L2_Comments_Modeldriver();

	@Autowired
	private CommentService commentService;

	private Getjson jsonarray = new Getjson();

	@Override
	public L2_Comments_Modeldriver getModel() {
		// TODO Auto-generated method stub
		return CMD2;
	}

	boolean Successful = false;

	@Override
	public String execute() throws Exception {
		System.out.println(CMD2.getAction());
		System.out.println(CMD2);
		if (CMD2.getAction().equals("对一级评论发表评论")) {
			// 设置sql语句和值，String转Integer
			CMD2.setSql("from Comments_L1 where L1_Cid = ?");
			Integer integerValue = Integer.valueOf(CMD2.getL1_Cid());
			CMD2.setValues(integerValue);
			Successful = commentService.postcommentsL2(CMD2);
			// 返回数据
			Returndata.returnboolean(Successful);
		} else if (CMD2.getAction().equals("对一级评论点赞")) {
			boolean isSuccessful=false;
			List<ThembModuel> list= ThembRecord.getvalue(CMD2.getSessionID());
			if(list!=null){
				System.out.println("用户淘渔一级评论点赞查询hashmap中是否有记录");
				for (ThembModuel thembModuel : list) {
					if(thembModuel.getAction().equals("对一级评论点赞") && thembModuel.getId().equals(CMD2.getL1_Cid())){
						isSuccessful=true;
						System.out.println("hashmap中查到有记录，直接返回true");
					}
				}
			}else{
			CMD2.setSql("from Comments_L1 where L1_Cid = ?");
			Integer integerValue = Integer.valueOf(CMD2.getL1_Cid());
			CMD2.setValues(integerValue);
			isSuccessful = commentService.PublishThembToComment_L1(CMD2);
			 //记录到hashmap
			 ThembModuel tm=ThembModuel.getThembModuel(CMD2.getAction(), CMD2.getL1_Cid());
			 list=new ArrayList<>();
			 list.add(tm);
			 ThembRecord.addkeyvalue(CMD2.getSessionID(), list);
			 System.out.println("淘渔一级评论点赞查询时数据库是否点赞，并记录到hashmap");
			}
			// 返回数据
			Returndata.returnboolean(isSuccessful);
		} else if (CMD2.getAction().equals("对二级评论发表评论")) {

			CMD2.setSql("from Comments_L2 where L2_Cid = ?");
			Integer integerValue = Integer.valueOf(CMD2.getL2_Cid());
			CMD2.setValues(integerValue);
			Successful = commentService.PublishCommentToComments_L2(CMD2);
			// 返回数据
			Returndata.returnboolean(Successful);
		} else if (CMD2.getAction().equals("对二级评论点赞")) {
			boolean isSuccessful=false;
			List<ThembModuel> list= ThembRecord.getvalue(CMD2.getSessionID());
			if(list!=null){
				System.out.println("用户淘渔二级评论点赞查询hashmap中是否有记录");
				for (ThembModuel thembModuel : list) {
					if(thembModuel.getAction().equals("对二级评论点赞") && thembModuel.getId().equals(CMD2.getL2_Cid())){
						isSuccessful=true;
						System.out.println("hashmap中查到有记录，直接返回true");
					}
				}
			}else{
			CMD2.setSql("from Comments_L2 where L2_Cid = ?");
			Integer integerValue = Integer.valueOf(CMD2.getL2_Cid());
			CMD2.setValues(integerValue);
			Successful = commentService.PublishThembToComment_L2(CMD2);
			 //记录到hashmap
			 ThembModuel tm=ThembModuel.getThembModuel(CMD2.getAction(), CMD2.getL2_Cid());
			 list=new ArrayList<>();
			 list.add(tm);
			 ThembRecord.addkeyvalue(CMD2.getSessionID(), list);
			 System.out.println("淘渔二级评论点赞查询时数据库是否点赞，并记录到hashmap");
			}
			// 返回数据
			Returndata.returnboolean(isSuccessful);
		} else if (CMD2.getAction().equals("querycomments_L2")) {
			// 查询二级评论
			System.out.println("查询二级评论操作!");
			CMD2.setSql("from Comments_L2 where LL_id=?");
			CMD2.setValues(CMD2.getL1_Cid());
			List<UComments_L2> uc2list = commentService.querycomments(CMD2);
			jsonarray.getjsonarray(uc2list, "返回二级评论");

			// 返回数据
			Returndata.returndata(jsonarray.getjsonarray(uc2list, "返回二级评论"));
		}

		return null;
	}

}
