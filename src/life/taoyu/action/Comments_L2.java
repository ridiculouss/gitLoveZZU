package life.taoyu.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import life.taoyu.modeldriver.L2_Comments_Modeldriver;

@Component(value = "comments_L2Action")
@Scope(value = "prototype")
public class Comments_L2 extends ActionSupport implements ModelDriven<L2_Comments_Modeldriver>{

	/**
	 * 二级评论(一级评论的评论)，查，存，修
	 */
	private static final long serialVersionUID = 1L;
	@Resource(name = "Comments_Modeldriven")
	private L2_Comments_Modeldriver CMD;

	@Override
	public L2_Comments_Modeldriver getModel() {
		// TODO Auto-generated method stub
		return CMD;
	}
	@Override
	public String execute() throws Exception {
          

		
		return null;
	}

	
}
