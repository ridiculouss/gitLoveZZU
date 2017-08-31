package persionalCenter.service;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import persionalCenter.dao.Dao;
import persionalCenter.dao.Userdao;
import persionalCenter.entity.User;
import persionalCenter.entity.UserInfo;
import zzu.util.GetDate;
import zzu.util.GetUUID;

@Transactional
@Component(value = "User_Service")
@Scope(value = "prototype")
public class UserService {

	@Resource(name = "user_Dao")
	private Dao dao;

	// �����û�����
	public boolean save(User user) {

		int account = 0;
		boolean isSuccessful = true;
		Serializable id = null;// ��ʼ��id
		while (isSuccessful) {// �������һ���˻�������ѯ���ݿ�����û���Ѵ��ڵģ��еĻ�����������
			account = (int) ((Math.random() * 9 + 1) * 100000000);
			String sql = "from User where account=?";
			String values = account + "";
			List<User> list2 = dao.query(sql, values);
			if (list2.isEmpty()) {
				isSuccessful = false;
			}
		}
		String sql = "from User where phone=?";
		String values = user.getPhone();
		List<User> list = dao.query(sql, values);// �����ֻ��Ų�ѯ�Ƿ��û�һ����
		if (list.isEmpty()) {// �������򱣴��û������Ҽ��������û���Ϣ�����
			UserInfo userinfo = new UserInfo();// �����û���Ϣ����
			userinfo.setPhone(user.getPhone());
			userinfo.setNickname("zzu" + user.getPhone());
			user.setAccount(account + "");
			userinfo.setUser(user);
			id = this.dao.save(user);// �����û��ɹ��򷵻ر�id
			this.dao.save(userinfo);

		} else {
			System.out.println("�û�:" + user.getPhone() + "�Ѵ���");
			isSuccessful = false;
		}
		if (id != null) {
			isSuccessful = true;
			System.out.println("���û��ѱ��浽���ݿ�!");
		}

		return isSuccessful;
	}

	// �û��ֻ��������½����
	public String PhoneLogin(User user) {

		String sql = "from User where phone=? and password=?";
		String values1 = user.getPhone();
		String values2 = user.getPassword();
		List<User> list = this.dao.query(sql, new String[] { values1, values2 });
		if (list.isEmpty()) {
			System.err.println(user.getPhone() + "�û�������");
			return null;
		}
		for (User user2 : list) {
			// �û��洢��������SseeionID,����¼��SessionID����ʱ��
			String sessionid = GetUUID.getuuid();
			user2.setSessionID(sessionid);
			user2.setSessionIDDate(GetDate.GetNowDate());

			this.dao.update(user2);
			return sessionid;
		}
		return null;
	}

	// �û�SessionID��½
	public String SesionIDLogin(User user) throws ParseException {

		String sql = "from User where SessionID=?";
		String values = user.getSessionID();
		List<User> list = this.dao.query(sql, values);
		if (list.isEmpty()) {
			System.err.println("SessionID��½δ�������û�");
			return null;
		}
		for (User user2 : list) {
			String lastdate = user2.getSessionIDDate();
			String nowdate = GetDate.GetNowDate();
			int day = GetDate.getDataNum(lastdate, nowdate);
			if (day > 30) {// �����ϴε�½����30�죬���µ�½
				System.err.println("��½����30��");
				return null;
			} else {// δ���������SessionID����,SessionID����
				user2.setSessionIDDate(nowdate);
				dao.update(user2);
				System.out.println("��½δ����30�죬����sessionID����");
				return user.getSessionID();
			}
		}

		return null;
	}

	// ������޸��û���Ϣ
	public boolean updateUserInfo(UserInfo userinfo) {

		boolean isSuccessful = false;

		// �õ�User����
		String sql = "from User where phone=?";
		String values = userinfo.getPhone();
		List<User> list = this.dao.query(sql, values);
		if (list.size() == 0) {
			System.err.println("�޸��û���Ϣ�û�δ������");
			return false;
		}

		for (User user : list) {
			// �õ�User�����id
			// ����id��ѯ������userinfo������Ϊul_id=id�Ķ����Ƿ����
			sql = "from UserInfo where ul_id=?";
			values = user.getUid().toString();
			System.out.println("Uid:" + values + sql + "," + values);
			List<UserInfo> infolist = dao.query(sql, values);
			if (userinfo.getAcademy() != null && !userinfo.getAcademy().equals("")) {// �жϲ��ǿյĲ�д���²���
				infolist.get(0).setAcademy(userinfo.getAcademy());
			}
			if (userinfo.getDepartments() != null && !userinfo.getDepartments().equals("")) {
				infolist.get(0).setDepartments(userinfo.getDepartments());
			}
			if (userinfo.getGender() != null && !userinfo.getGender().equals("")) {
				infolist.get(0).setGender(userinfo.getGender());
			}
			if (userinfo.getHometown() != null && !userinfo.getHometown().equals("")) {
				infolist.get(0).setHometown(userinfo.getHometown());
			}
			if (userinfo.getImageUrl() != null && !userinfo.getImageUrl().equals("")) {
				infolist.get(0).setImageUrl(userinfo.getImageUrl());
			}
			if (userinfo.getNickname() != null && !userinfo.getNickname().equals("")) {
				infolist.get(0).setNickname(userinfo.getNickname());
			}
			if (userinfo.getProfessional() != null && !userinfo.getProfessional().equals("")) {
				infolist.get(0).setProfessional(userinfo.getProfessional());
			}
			if (userinfo.getQr_codeUrl() != null && !userinfo.getQr_codeUrl().equals("")) {
				infolist.get(0).setQr_codeUrl(userinfo.getQr_codeUrl());
			}

			this.dao.update(infolist.get(0));
			System.out.println("�û���Ϣ�޸Ĳ���");
			isSuccessful = true;
			System.out.println("������Ϣ�ѱ��浽���ݿ�!");

		}

		return isSuccessful;
	}

	// ��ѯ�������û���Ϣ
	public UserInfo query(UserInfo userinfo) {

		// ����phone��ѯUser����
		String sql = "from User where phone=?";
		String values = userinfo.getPhone();
		List<User> list = this.dao.query(sql, values);
		if (list.size() == 0) {
			System.err.println("��ѯ�û���Ϣδ�������û�");
			return null;
		}
		// ����User��id��ѯ������userinfo������Ϊul_id=id�Ķ����Ƿ����
		sql = "from UserInfo where ul_id=?";
		List<UserInfo> list2 = dao.query(sql, list.get(0).getUid());
		return list2.get(0);
	}

	// ��ȡaccount������ͼƬ��
	public String[] getImageName(String SessionID) {

		String[] str = new String[10];
		String phone = null;
		String newimageurl = null;
		String oldimageurl = null;
		String sql = "from User where SessionID=?";
		String values = SessionID;
		List<User> list2 = dao.query(sql, values);

		if (!list2.isEmpty()) {
			for (User user : list2) {
				newimageurl = user.getAccount();
				phone = user.getPhone();
				sql = "from UserInfo where ul_id=?";
				values = user.getUid().toString();
			}
			List<UserInfo> list3 = dao.query(sql, values);
			for (UserInfo userInfo : list3) {
				oldimageurl = userInfo.getImageUrl();
			}
			newimageurl = newimageurl + "ZZU" + GetDate.GetNowDate2();// ͼƬ����account+��������
			str[0] = newimageurl;
			str[1] = phone;
			str[2] = oldimageurl;
		}
		return str;

	}

	// ������ѯ�û���Ϣ������list
	public List<UserInfo> BatchQueryUserInfo(String[] ulid) {
		List<UserInfo> userinfolist = new ArrayList<UserInfo>();
		if (ulid.length!=0) {
			for (String id : ulid) {
				if(id.equals("")){break;}
				Integer i = Integer.valueOf(id);
				String sql = "from UserInfo where ul_id=?";// ���������ѯ�û���Ϣ��
				List<UserInfo> userinfo = dao.query(sql, i);
				userinfolist.add(userinfo.get(0));
			}
		}
		return userinfolist;

	}

	// ͨ��SessionID��ѯ�û�
	public User queryUser(String SessionID) {
		String sql = "from User where SessionID=?";
		if(SessionID==null || SessionID.equals("")){System.out.println("SessionIDΪ��");return null;}
		List<User> user = dao.query(sql, SessionID);
		if (user.size() == 0) {
			System.err.println("ͨ��SessionIDδ�������û�");
			return null;
		}
		return user.get(0);

	}

	// ͨ��id��ѯ�û�
	public User queryUser(Integer id) {
		String sql = "from User where uid=?";
		List<User> user = dao.query(sql, id);
		if (user.size() == 0) {
			System.err.println("ͨ��idδ�������û�");
			return null;
		}
		return user.get(0);

	}
}
