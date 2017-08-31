package persionalCenter.user_action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import persionalCenter.entity.UserInfo;
import persionalCenter.service.UserService;
import zzu.util.Returndata;


@Transactional
@Controller(value="UserinfoAction")
@Component
@Scope(value="prototype")
public class UserInfoAction extends ActionSupport implements ModelDriven<UserInfo>{

	/**
	 * �޸ĺͱ����û���Ϣ
	 */
	private static final long serialVersionUID = 1L;

	//ע��service��ʵ��������
		@Resource(name="User_Service")
		private UserService userService;
		
		private UserInfo userinfo=new UserInfo();
		
	@Override
	public UserInfo getModel() {
		// TODO Auto-generated method stub
		return userinfo;
	}
	@Override
	public String execute() throws Exception {
		boolean isSuccessful=false;
		System.out.println("ģ������"+userinfo.toString());
		
		if(!userinfo.equals("") && !userinfo.getPhone().equals("")){
			
			isSuccessful=userService.updateUserInfo(userinfo);
		
		}else{System.out.println("�ͻ��˴�����userinfo����Ϊ��");}
		userinfo=null;
		
			//��������
			Returndata.returnboolean(isSuccessful);

		return NONE;
	}

	
}
