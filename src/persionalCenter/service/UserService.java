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

	// 保存用户操作
	public boolean save(User user) {

		int account = 0;
		boolean isSuccessful = true;
		Serializable id = null;// 初始化id
		while (isSuccessful) {// 随机生成一个账户，并查询数据库里有没有已存在的，有的话就重新生成
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
		List<User> list = dao.query(sql, values);// 根据手机号查询是否用户一存在
		if (list.isEmpty()) {// 不存在则保存用户，并且级联保存用户信息详情表
			UserInfo userinfo = new UserInfo();// 设置用户信息对象
			userinfo.setPhone(user.getPhone());
			userinfo.setNickname("zzu" + user.getPhone());
			user.setAccount(account + "");
			userinfo.setUser(user);
			id = this.dao.save(user);// 保存用户成功则返回表id
			this.dao.save(userinfo);

		} else {
			System.out.println("用户:" + user.getPhone() + "已存在");
			isSuccessful = false;
		}
		if (id != null) {
			isSuccessful = true;
			System.out.println("新用户已保存到数据库!");
		}

		return isSuccessful;
	}

	// 用户手机号密码登陆操作
	public String PhoneLogin(User user) {

		String sql = "from User where phone=? and password=?";
		String values1 = user.getPhone();
		String values2 = user.getPassword();
		List<User> list = this.dao.query(sql, new String[] { values1, values2 });
		if (list.isEmpty()) {
			System.err.println(user.getPhone() + "用户不存在");
			return null;
		}
		for (User user2 : list) {
			// 用户存储在则生成SseeionID,并记录该SessionID生成时间
			String sessionid = GetUUID.getuuid();
			user2.setSessionID(sessionid);
			user2.setSessionIDDate(GetDate.GetNowDate());

			this.dao.update(user2);
			return sessionid;
		}
		return null;
	}

	// 用户SessionID登陆
	public String SesionIDLogin(User user) throws ParseException {

		String sql = "from User where SessionID=?";
		String values = user.getSessionID();
		List<User> list = this.dao.query(sql, values);
		if (list.isEmpty()) {
			System.err.println("SessionID登陆未检索到用户");
			return null;
		}
		for (User user2 : list) {
			String lastdate = user2.getSessionIDDate();
			String nowdate = GetDate.GetNowDate();
			int day = GetDate.getDataNum(lastdate, nowdate);
			if (day > 30) {// 距离上次登陆超过30天，重新登陆
				System.err.println("登陆超过30天");
				return null;
			} else {// 未超过则更新SessionID日期,SessionID不变
				user2.setSessionIDDate(nowdate);
				dao.update(user2);
				System.out.println("登陆未超过30天，更新sessionID日期");
				return user.getSessionID();
			}
		}

		return null;
	}

	// 保存或修改用户信息
	public boolean updateUserInfo(UserInfo userinfo) {

		boolean isSuccessful = false;

		// 得到User对象
		String sql = "from User where phone=?";
		String values = userinfo.getPhone();
		List<User> list = this.dao.query(sql, values);
		if (list.size() == 0) {
			System.err.println("修改用户信息用户未检索到");
			return false;
		}

		for (User user : list) {
			// 得到User对象的id
			// 根据id查询关联的userinfo表的外键为ul_id=id的对象是否存在
			sql = "from UserInfo where ul_id=?";
			values = user.getUid().toString();
			System.out.println("Uid:" + values + sql + "," + values);
			List<UserInfo> infolist = dao.query(sql, values);
			if (userinfo.getAcademy() != null && !userinfo.getAcademy().equals("")) {// 判断不是空的才写更新操作
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
			System.out.println("用户信息修改操作");
			isSuccessful = true;
			System.out.println("个人信息已保存到数据库!");

		}

		return isSuccessful;
	}

	// 查询并返回用户信息
	public UserInfo query(UserInfo userinfo) {

		// 根据phone查询User对象
		String sql = "from User where phone=?";
		String values = userinfo.getPhone();
		List<User> list = this.dao.query(sql, values);
		if (list.size() == 0) {
			System.err.println("查询用户信息未检索到用户");
			return null;
		}
		// 根据User的id查询关联的userinfo表的外键为ul_id=id的对象是否存在
		sql = "from UserInfo where ul_id=?";
		List<UserInfo> list2 = dao.query(sql, list.get(0).getUid());
		return list2.get(0);
	}

	// 获取account并返回图片名
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
			newimageurl = newimageurl + "ZZU" + GetDate.GetNowDate2();// 图片名：account+现在日期
			str[0] = newimageurl;
			str[1] = phone;
			str[2] = oldimageurl;
		}
		return str;

	}

	// 批量查询用户信息并返回list
	public List<UserInfo> BatchQueryUserInfo(String[] ulid) {
		List<UserInfo> userinfolist = new ArrayList<UserInfo>();
		if (ulid.length!=0) {
			for (String id : ulid) {
				if(id.equals("")){break;}
				Integer i = Integer.valueOf(id);
				String sql = "from UserInfo where ul_id=?";// 根据外键查询用户信息表
				List<UserInfo> userinfo = dao.query(sql, i);
				userinfolist.add(userinfo.get(0));
			}
		}
		return userinfolist;

	}

	// 通过SessionID查询用户
	public User queryUser(String SessionID) {
		String sql = "from User where SessionID=?";
		if(SessionID==null || SessionID.equals("")){System.out.println("SessionID为空");return null;}
		List<User> user = dao.query(sql, SessionID);
		if (user.size() == 0) {
			System.err.println("通过SessionID未检索到用户");
			return null;
		}
		return user.get(0);

	}

	// 通过id查询用户
	public User queryUser(Integer id) {
		String sql = "from User where uid=?";
		List<User> user = dao.query(sql, id);
		if (user.size() == 0) {
			System.err.println("通过id未检索到用户");
			return null;
		}
		return user.get(0);

	}
}
