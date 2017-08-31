package persionalCenter.user_action;

import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


import net.sf.json.JSONObject;
import persionalCenter.entity.User;
import persionalCenter.service.UserService;
import zzu.util.Judge_character;
import zzu.util.Returndata;


@Transactional
@Component(value="loginAction")
@Scope(value="prototype")
public class LoginAction extends ActionSupport implements ModelDriven<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//注入属性
	@Resource(name="User_Service")
	private UserService userService;
	
	
	private User user=new User();
	
	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}
	
	
	
	@Override
	public String execute() throws Exception {
		
           
		Judge_character j=new Judge_character();
		String SessionID=null;
		String info="";
		if( j.character(user.getPhone())){//手机号登陆
		info="用户手机号密码登陆"+user.toString();
		SessionID=userService.PhoneLogin(user);
		}else {//SessionID登陆
		System.out.println("用户发来的登陆SessionID="+user.getSessionID());
				SessionID=userService.SesionIDLogin(user);
				if(SessionID == null){
					SessionID="";
					info="SessionID错误，或用户距离上次登陆超过30天";				
				}
				
			}
	     System.out.println(info);
		//返回数据
	     JSONObject json = new JSONObject(); 
	     json.put("SessionID", SessionID);
		Returndata.returndata(json);

		return null;
	}
	
	
	 

 


}
