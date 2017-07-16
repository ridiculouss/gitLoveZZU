package persionalCenter.user_action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;

@Transactional
@Component(value="adminlogin")
@Scope(value="prototype")
public class Admin extends ActionSupport{
String account,password;

public String getAccount() {
	return account;
}

public void setAccount(String account) {
	this.account = account;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

HttpServletRequest request=ServletActionContext.getRequest();

HttpSession session=request.getSession();

	@Override
	public String execute() throws Exception {
		account=request.getParameter("account");
		password=request.getParameter("password");
		
	String info=null;
		if(account !=null && password !=null && account.equals("admin") && password.equals("123")){
			session.setAttribute("admin", "已登录");
			info="success";
			try{
			    Thread thread = Thread.currentThread();
			    thread.sleep(4000);//暂停4秒后程序继续执行
			}catch (InterruptedException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			
		}else{
			addActionError("账号或密码有误");
			info="input";
		}
		System.out.println(account+":"+password);
		System.out.println("管理员登陆:"+info);
		return info;
	}
	
	public String loginout (){
	session.setAttribute("admin", "未登录");
	session.invalidate();
		System.out.println("session已销毁");
		addActionError("成功退出");
		return "input";
	}
}
