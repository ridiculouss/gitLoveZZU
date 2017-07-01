package persionalCenter.user_action;

import java.io.PrintWriter;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import persionalCenter.entity.UserInfo;
import persionalCenter.service.UserService;


@Transactional
@Component(value="UserinfoAction")
@Scope(value="prototype")
public class UserInfoAction extends ActionSupport implements ModelDriven<UserInfo>{

	/**
	 * 修改和保存用户信息
	 */
	private static final long serialVersionUID = 1L;

	//注入service和实体类属性
		@Resource(name="userService")
		private UserService userService;
		@Resource(name="UserInfo")
		private UserInfo userinfo;
		
	@Override
	public UserInfo getModel() {
		// TODO Auto-generated method stub
		return userinfo;
	}
	@Override
	public String execute() throws Exception {
		boolean isSuccessful=false;
		System.out.println("模型驱动"+userinfo.toString());
		
		if(!userinfo.equals("") && !userinfo.getPhone().equals("")){
			
			isSuccessful=userService.saveOrupdate(userinfo);
		
		}else{System.out.println("客户端传来的userinfo数据为空");}
			//返回数据
			HttpServletResponse response= ServletActionContext.getResponse();
			response.setHeader("Content-type", "text/html;charset=UTF-8");   
			response.setCharacterEncoding("UTF-8");
			JSONObject json = new JSONObject();   
			
	    	 json.put("isSuccessful", isSuccessful);
	          System.out.println("UserInfoAction层json"+json);
	        
	     PrintWriter out=response.getWriter();    	
	    	out.println(json);
	    	out.flush();
	    	out.close();

		return NONE;
	}

	
}
