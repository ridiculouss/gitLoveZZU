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

	//ע������
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
		if( j.character(user.getPhone())){//�ֻ��ŵ�½
		info="�û��ֻ��������½"+user.toString();
		SessionID=userService.PhoneLogin(user);
		}else {//SessionID��½
		System.out.println("�û������ĵ�½SessionID="+user.getSessionID());
				SessionID=userService.SesionIDLogin(user);
				if(SessionID == null){
					SessionID="";
					info="SessionID���󣬻��û������ϴε�½����30��";				
				}
				
			}
	     System.out.println(info);
		//��������
	     JSONObject json = new JSONObject(); 
	     json.put("SessionID", SessionID);
		Returndata.returndata(json);

		return null;
	}
	
	
	 

 


}
