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
import zzu.util.Returndata;
@Transactional
@Component(value="queryuserinfo_action")
@Scope(value="prototype")
public class QueryUserInfo_action extends ActionSupport implements ModelDriven<UserInfo> {

	/**
	 * ��ѯ�û���Ϣ
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
		
		
		if(!userinfo.getPhone().equals("") && userinfo !=null){
		UserInfo uinfo=userService.query(userinfo);
		
		
		//��������
		//��������
				HttpServletResponse response= ServletActionContext.getResponse();
				response.setHeader("Content-type", "text/html;charset=UTF-8");   
				response.setCharacterEncoding("UTF-8");
				JSONObject json = new JSONObject(); 
				  
		    	 json.put("imageUrl",uinfo.getImageUrl());
		    	 json.put("nickname", uinfo.getNickname());
		    	 json.put("phone", uinfo.getPhone());
		    	 json.put("Qr_codeUrl", uinfo.getQr_codeUrl());
		    	 json.put("gender", uinfo.getGender());
		    	 json.put("hometown", uinfo.getHometown());
		    	 json.put("academy", uinfo.getAcademy());
		    	 json.put("departments",uinfo.getDepartments() );
		    	 json.put("professional", uinfo.getProfessional());
		    	 PrintWriter out=response.getWriter();    	
			    	out.println(json);
			    	out.flush();
			    	out.close();

				}else{ 
				
					System.out.println("QueryuserinfoAction��,�����phoneΪ��");}
			
				return NONE;
			}

}
