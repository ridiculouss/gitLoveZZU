package life.playTogether.action;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

import life.playTogether.entity.Activity;
import life.playTogether.service.ActivityPraiseService;

@Component("activityPraiseAction")
@Scope("prototype")
public class ActivityPraiseAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name="activityPraiseService")
	private ActivityPraiseService activityPraiseService;
	@Resource(name="activity")
	private Activity activity;
	private int a_id;
	
	@Override
	public String execute() throws Exception {
		HttpServletResponse response= ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		boolean addPraise = activityPraiseService.addPraise(a_id);
		boolean isSuccessful = false;
		
		if (addPraise) {
			isSuccessful = true;
			out.println(isSuccessful);
	    	out.flush();
	    	out.close();
	    	return NONE;
		}
		out.println(isSuccessful);
    	out.flush();
    	out.close();
    	return NONE;
	}

	public int getA_id() {
		return a_id;
	}

	public void setA_id(int a_id) {
		this.a_id = a_id;
	}

	
	
}
