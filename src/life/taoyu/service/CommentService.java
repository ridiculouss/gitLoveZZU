package life.taoyu.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import life.playTogether.modeldriver.UserDynamic;
import life.taoyu.dao.Dao_taoyu;
import life.taoyu.entity.Comments_L1;
import life.taoyu.entity.Comments_L2;
import life.taoyu.entity.Goods;
import life.taoyu.modeldriver.L1_Comments_Modeldriver;
import life.taoyu.modeldriver.L2_Comments_Modeldriver;
import life.taoyu.modeldriver.UComments_L2;
import life.taoyu.modeldriver.Ucomments;
import persionalCenter.dao.Dao;
import persionalCenter.entity.User;
import persionalCenter.entity.UserInfo;
import persionalCenter.service.UserService;
import zzu.util.GetDate;

@Transactional
@Component(value = "Commentervice")
@Scope(value = "prototype")
public class CommentService {
	@Resource(name = "user_Dao")
	private Dao dao;
	@Resource(name = "taoyuDao")
	private Dao_taoyu TDao;
	@Resource(name = "taoyuService")
	private TaoyuService taoyuService;
	@Resource(name = "User_Service")
	private UserService userService;

	// 查询一级评论
	public <S> List<Ucomments> querycomments(L1_Comments_Modeldriver CMD) {
		String sql=CMD.getSql(); 
		String values= CMD.getValues(); 
		int num=CMD.getNum();
		List<Ucomments> uclist = new ArrayList<Ucomments>();

		List<Comments_L1> commentslist = TDao.hqlquery(sql, values, num);
		for (Comments_L1 comments_L1 : commentslist) {
			Ucomments uc = new Ucomments();
			sql = "from User where account=?";
			values = (String) comments_L1.getAccount();
			List<User> user = dao.query(sql, values);
			System.out.println(user.toString());
			for (User user2 : user) {
				sql = "from UserInfo where ul_id=?";
				values = (String) user2.getUid().toString();

				List<UserInfo> userinfo = dao.query(sql, values);
				for (UserInfo userInfo2 : userinfo) {
					uc.setUserinfo(userInfo2);
					uc.setComments_L1(comments_L1);
					uclist.add(uc);
					System.out.println("uc添加");
				}
			}
		}
		 //查询用户是否点过赞
        User user=userService.queryUser(CMD.getSessionID());
        if(user!=null){
      	  for(Ucomments ud:uclist){
      		  String UserId=ud.getComments_L1().getThembUser();
      		  if(UserId ==null){break;}
      		  String[] uid=UserId.split("#");
      			  for (String d : uid) {
						  if(user.getUid().toString().equals(d)){ud.getComments_L1().setThembed(true);}
					} 
      	  }
      	  System.out.println("查询淘渔一级评论是否被该用户点赞");
        }else{System.out.println("用户未发来SesionID,对所查数据不检查是否点赞");}
		return uclist;

	}

	// 保存用户一级评论信息，并返回评论和用户信息。
	public Serializable postcomments(L1_Comments_Modeldriver CMD) {

		Serializable id = null;
		String sql = "from User where SessionID=?";
		String values = CMD.getSessionID();
		List<User> user = dao.query(sql, values);
		if (user.size() == 0) {
			System.err.println("SessionID错误");
			return null;
		}
		String account = user.get(0).getAccount();

		sql = CMD.getSql();
		values = CMD.getValues();
		Integer i = new Integer(values);

		List<Goods> goods = dao.query(sql, i);

		for (Goods g : goods) {

			Comments_L1 CL1 = new Comments_L1();
			CL1.setComments(CMD.getComments());
			CL1.setAccount(account);
			CL1.setCdate(GetDate.GetNowDate());
			CL1.setThembUser("0");
			g.getSetcomments_L1().add(CL1);
			CL1.setCgoods(g);
			// 增加评论数
			g.setGcomments(g.getGcomments() + 1);
			System.out.println("增加评论数");

			id = this.dao.save(CL1);
			taoyuService.updateGoods(g);
			System.out.println("id:" + id);
		}

		return id;
	}

	// 对一级评论点赞
	public boolean PublishThembToComment_L1(L2_Comments_Modeldriver CMD2) {
		User user = userService.queryUser(CMD2.getSessionID());
		List<Comments_L1> comments_l1 = dao.query(CMD2.getSql(), CMD2.getValues());
		for (Comments_L1 comments : comments_l1) {
			String[] thembuser=comments.getThembUser().split("#");//查询是否已经点过赞，点过直接返回false
			for (String string : thembuser) {
				if(user.getUid().toString().equals(string)){return false;}
			}
			// 点赞存入数据库
			comments.setThembUser(comments.getThembUser() + "#" + user.getUid());
			comments.setNum_thumb(comments.getNum_thumb() + 1);
			System.out.println("一级评论点赞数+1");
			dao.update(comments);
			return true;
		}
		return false;
	}

	// 对一级评论发表评论
	public boolean postcommentsL2(L2_Comments_Modeldriver CMD2) {

		boolean Successful = false;
		User user = userService.queryUser(CMD2.getSessionID());
		if (user == null) {
			return false;
		}

		if (CMD2.getL1_Cid() != null && !CMD2.getL1_Cid().isEmpty()) {
			// 二级评论评一级评论操作
			List<Comments_L1> comments_l1 = dao.query(CMD2.getSql(), CMD2.getValues());
			for (Comments_L1 comments : comments_l1) {
				// 保存二级评论信息
				if (CMD2.getComments() != null && !CMD2.getComments().isEmpty()) {
					comments.setNum_replies(comments.getNum_replies() + 1);
					Comments_L2 CL2 = new Comments_L2();
					CL2.setComments(CMD2.getComments());
					CL2.setCdate(GetDate.GetNowDate());
					CL2.setCommented_id("L1ZZU" + comments.getL1_Cid().toString());
					CL2.setAccount(user.getAccount());
					CL2.setComments_l1(comments);
					CL2.setThembUser("0");
					comments.getSetcomments_L2().add(CL2);
					this.dao.save(CL2);
					this.dao.update(comments);
					Successful = true;
					System.out.println("级联保存到二级评论表成功,一级评论的回复数+1");
				}
			}
		}
		return Successful;
	}

	// 对二级评论发表评论
	public boolean PublishCommentToComments_L2(L2_Comments_Modeldriver CMD2) {
		boolean Successful = false;
		User user = userService.queryUser(CMD2.getSessionID());
		if (user == null) {
			return false;
		}
		if (CMD2.getL2_Cid() != null && !CMD2.getL2_Cid().isEmpty()) {
			// 二级评论评二级评论操作
			List<Comments_L2> comments_l2 = dao.query(CMD2.getSql(), CMD2.getValues());
			for (Comments_L2 comments2 : comments_l2) {
				// 保存二级评论信息
				if (CMD2.getComments() != null && !CMD2.getComments().isEmpty()) {
					// 评论内容超过15个字符就截取前15个字符串
					String strComments = null;
					if (comments2.getComments().length() > 15) {
						strComments = comments2.getComments().substring(0, 15);
					} else {
						strComments = comments2.getComments();
					}
					Comments_L2 CL2 = new Comments_L2();
					CL2.setComments(CMD2.getComments() + "@:" + strComments);
					CL2.setCdate(GetDate.GetNowDate());
					CL2.setAccount(user.getAccount());
					CL2.setCommented_id("L2ZZU" + comments2.getL2_Cid().toString());
					CL2.setComments_l1(comments2.getComments_l1());

					this.dao.save(CL2);
					comments2.setNum_replies(comments2.getNum_replies() + 1);
					System.out.println("保存评论二级评论的评论,被评论的二级评论回复数+1");
				}
				this.dao.update(comments2);

				Successful = true;
			}
		}
		return Successful;
	}

	// 对二级评论点赞
	public boolean PublishThembToComment_L2(L2_Comments_Modeldriver CMD2) {
		User user = userService.queryUser(CMD2.getSessionID());
		if (user == null) {
			return false;
		}
		List<Comments_L2> comments_l2 = dao.query(CMD2.getSql(), CMD2.getValues());
		for (Comments_L2 comments2 : comments_l2) {
			String[] thembuser=comments2.getThembUser().split("#");
			for (String string : thembuser) {
				if(user.getUid().toString().equals(string)){return false;}
			}
			// 点赞存入数据库
			comments2.setThembUser(comments2.getThembUser() + "#" + user.getUid());
			comments2.setNum_thumb(comments2.getNum_thumb() + 1);
			System.out.println("二级评论点赞成功");
			this.dao.update(comments2);
			return true;
		}

		return false;
	}

	// 查询二级评论
	public List<UComments_L2> querycomments(L2_Comments_Modeldriver CMD2) {
		List<UComments_L2> uc2list = new ArrayList<UComments_L2>();
		// 查出二级评论对象
		List<Comments_L2> CL2 = dao.query(CMD2.getSql(), CMD2.getValues());
		for (Comments_L2 comments_L2 : CL2) {
			UComments_L2 uc2 = new UComments_L2();
			// 查出用户对象
			String sql = "from User where account=?";
			List<User> userlist = dao.query(sql, comments_L2.getAccount());
			sql = "from UserInfo where ul_id=?";
			List<UserInfo> userinfo = dao.query(sql, userlist.get(0).getUid());
			uc2.setComments_l2(comments_L2);
			uc2.setUserinfo(userinfo.get(0));
			uc2list.add(uc2);
		}
		 //查询用户是否点过赞
        User user=userService.queryUser(CMD2.getSessionID());
        if(user!=null){
      	  for(UComments_L2 ud:uc2list){
      		  String UserId=ud.getComments_l2().getThembUser();
      		  if(UserId ==null){break;}
      		  String[] uid=UserId.split("#");
      			  for (String d : uid) {
						  if(user.getUid().toString().equals(d)){ud.getComments_l2().setThembed(true);}
					} 
      	  }
      	  System.out.println("查询淘渔二级评论是否被该用户点赞");
        }else{System.out.println("用户未发来SesionID,对所查数据不检查是否点赞");}
		return uc2list;

	}

}
