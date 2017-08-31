package life.mytopiccircle.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;

import life.mytopiccircle.entity.Theme;
import life.mytopiccircle.moudledriver.UserTheme;
import life.mytopiccircle.service.TopicCircleService;
import zzu.util.Getjson;
import zzu.util.Panduanstr;
import zzu.util.Returndata;
import zzu.util.Sort;
@Transactional
@Component(value="ThemeAction")
@Scope(value = "prototype")
public class ThemeAction extends ActionSupport{
	@Autowired
	TopicCircleService  TCS;
	@Override
	public String execute() throws Exception {
		Getjson g=new Getjson();
		
		if(action!=null){
			if(action.equals("发布主题")){
				Theme theme=new Theme();
				theme.setThemeTitle(ThemeTitle);
				String ThemeId=TCS.PublishTheme(theme, SessionID);
				
				Returndata.returndata(ThemeId);
			}else if(action.equals("查询所有主题")){
				List<UserTheme> UT=TCS.queryTheme();
				List<UserTheme> sortUT =Sort.sortTheme(UT);//按主题拥有的话题量大小排序
				System.out.println(UT);
				Returndata.returndata(g.getjsonarray(sortUT, action));
			}
				
		}else{System.out.println("主题action为空");}
		
		return null;
	}

	private String action;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	private String ThemeTitle;
	private String SessionID;
	private boolean isSuccessful;
	public String getThemeTitle() {
		return ThemeTitle;
	}
	public void setThemeTitle(String themeTitle) {
		ThemeTitle = themeTitle;
	}
	public String getSessionID() {
		return SessionID;
	}
	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}
}
