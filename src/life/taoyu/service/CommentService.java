package life.taoyu.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
import zzu.util.GetDate;
@Transactional
@Component(value = "Commentervice")
public class CommentService {
	@Resource(name = "user_Dao")
	private Dao dao;
	@Resource(name = "taoyuDao")
	private Dao_taoyu TDao;
	@Resource(name = "taoyuService")
	private TaoyuService taoyuService;
	private GetDate date=new GetDate();
	

	// ��ѯһ������
	public <S> List<Ucomments> querycomments(String sql, S values, int num) {
		
		List<Ucomments> uclist = new ArrayList<Ucomments>();

		List<Comments_L1> commentslist = TDao.hqlquery(sql, values, num);
		for (Comments_L1 comments_L1 : commentslist) {
			Ucomments uc = new Ucomments();
			sql = "from User where account=?";
			values = (S) comments_L1.getAccount();
			List<User> user = dao.query(sql, values);
			System.out.println(user.toString());
			for (User user2 : user) {
				sql = "from UserInfo where ul_id=?";
				values = (S) user2.getUid().toString();

				List<UserInfo> userinfo = dao.query(sql, values);
				for (UserInfo userInfo2 : userinfo) {
					uc.setUserinfo(userInfo2);
					uc.setComments_L1(comments_L1);
					uclist.add(uc);
					System.out.println("uc����");
				}
			}
		}
		return uclist;

	}

	// �����û�һ��������Ϣ�����������ۺ��û���Ϣ��
	public Serializable postcomments(L1_Comments_Modeldriver CMD) {
		 
		Serializable	id=null;
		String sql = "from User where SessionID=?";
	String	values = CMD.getSessionID();
		List<User> user = dao.query(sql, values);
		if (user.size() == 0) {
			System.err.println("SessionID����");
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
			CL1.setCdate(date.GetNowDate());
			g.getSetcomments_L1().add(CL1);
			CL1.setCgoods(g);
			// ����������
			g.setGcomments(g.getGcomments() + 1);
			System.out.println("����������");

			id = this.dao.save(CL1);
			taoyuService.updateGoods(g);
			System.out.println("id:" + id);
		}

		// TaoyuService t = new TaoyuService();
		// sql = "from Comments_L1 where L1_Cid=?";
		// Integer v = new Integer((int) id);
		// System.out.println(v);
		// uclist = t.querycomments(sql, v, 0);

		return id;
		 }
	

	// ������������
	public boolean postcommentsL2(L2_Comments_Modeldriver CMD2) {
		
		boolean Successful = false;
	String	sql = "from User where SessionID=?";
	String	values = CMD2.getSessionID();
		List<User> user = dao.query(sql, values);
		if (user.size() == 1) {

			if (CMD2.getL1_Cid() != null && !CMD2.getL1_Cid().isEmpty()) {
				// ����������һ�����۲���
				List<Comments_L1> comments_l1 = dao.query(CMD2.getSql(), CMD2.getValues());
				for (Comments_L1 comments : comments_l1) {
					// �������������Ϣ
					if (CMD2.getComments() != null && !CMD2.getComments().isEmpty()) {
						comments.setNum_replies(comments.getNum_replies() + 1);
						Comments_L2 CL2 = new Comments_L2();
						CL2.setComments(CMD2.getComments());
						CL2.setCdate(date.GetNowDate());
						CL2.setCommented_id("L1" + "ZZU" + comments.getL1_Cid() + "");
						CL2.setAccount(user.get(0).getAccount());
						CL2.setComments_l1(comments);
						comments.getSetcomments_L2().add(CL2);
						this.dao.save(CL2);
						System.out.println("�������浽�������۱��ɹ�,һ�����۵Ļظ���+1");

					} else if (CMD2.getThumbNum() != null && !CMD2.getThumbNum().isEmpty()) {

						// ���޴������ݿ�
						if (CMD2.getThumbNum() != null && CMD2.getThumbNum().equals("1")) {
							comments.setNum_thumb(comments.getNum_thumb() + 1);
							System.out.println("һ�����۵�����+1");
						} else if (CMD2.getThumbNum().equals("0")) {
							comments.setNum_thumb(comments.getNum_thumb() - 1);
						}

					} else {
						System.out.println("����Ϊ�գ��ҵ��ޱ�ʶΪ��");
					}
					this.dao.update(comments);

					Successful = true;
				}
			} else if (CMD2.getL2_Cid() != null && !CMD2.getL2_Cid().isEmpty()) {
				// �����������������۲���
				List<Comments_L2> comments_l2 = dao.query(CMD2.getSql(), CMD2.getValues());
				for (Comments_L2 comments2 : comments_l2) {
					// �������������Ϣ
					if (CMD2.getComments() != null && !CMD2.getComments().isEmpty()) {
						// �������ݳ���15���ַ��ͽ�ȡǰ15���ַ���
						String strComments = null;
						if (comments2.getComments().length() > 15) {
							strComments = comments2.getComments().substring(0, 15);
						} else {
							strComments = comments2.getComments();
						}
						Comments_L2 CL2 = new Comments_L2();
						CL2.setComments(CMD2.getComments() + "@:" + strComments);
						CL2.setCdate(date.GetNowDate());
						CL2.setAccount(user.get(0).getAccount());
						CL2.setCommented_id("L2ZZU" + comments2.getL2_Cid() + "");
						CL2.setComments_l1(comments2.getComments_l1());

						this.dao.save(CL2);
						comments2.setNum_replies(comments2.getNum_replies() + 1);
						System.out.println("�������۶������۵�����,�����۵Ķ������ۻظ���+1");
					} else {

						// ���޴������ݿ�
						if (CMD2.getThumbNum() != null && CMD2.getThumbNum().equals("1")) {
							comments2.setNum_thumb(comments2.getNum_thumb() + 1);
							System.out.println("�������۵��޳ɹ�");
						} else if (CMD2.getThumbNum() != null && CMD2.getThumbNum().equals("0")) {
							comments2.setNum_thumb(comments2.getNum_thumb() - 1);
							System.out.println("��������ȡ�����޳ɹ�");
						}

					}
					this.dao.update(comments2);

					Successful = true;
				}
			}

		} else {
			System.out.println("������������ʧ�ܣ��û�������");
		}
		return Successful;
		 }
	

	// ��ѯ��������
	public List<UComments_L2> querycomments(L2_Comments_Modeldriver CMD2) {
		List<UComments_L2> uc2list = new ArrayList<UComments_L2>();
		// ����������۶���
		List<Comments_L2> CL2 = dao.query(CMD2.getSql(), CMD2.getValues());
		for (Comments_L2 comments_L2 : CL2) {
			UComments_L2 uc2 = new UComments_L2();
			// ����û�����
		String	sql = "from User where account=?";
			List<User> userlist = dao.query(sql, comments_L2.getAccount());
			sql = "from UserInfo where ul_id=?";
			List<UserInfo> userinfo = dao.query(sql, userlist.get(0).getUid());
			uc2.setComments_l2(comments_L2);
			uc2.setUserinfo(userinfo.get(0));
			uc2list.add(uc2);
		}
		return uc2list;

	}

}