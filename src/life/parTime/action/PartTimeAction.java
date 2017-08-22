package life.parTime.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import life.parTime.entity.PartTime;
import life.parTime.service.PartTimeService;
import zzu.util.Getjson;
import zzu.util.Returndata;

@Transactional
@Component(value = "PartTimeAction")
@Scope(value = "prototype")
public class PartTimeAction extends ActionSupport implements ModelDriven<PartTime> {
	private PartTime p = new PartTime();

	@Override
	public PartTime getModel() {
		// TODO Auto-generated method stub
		return p;
	}

	@Autowired
	PartTimeService partTimeService;
	private String action, SessionID, search, Id;

	@Override
	public String execute() throws Exception {

		if (action == null) {
			System.err.println("兼职action为空");
			return null;
		}
		System.out.println(action);
		if (action.equals("发布兼职")) {
			boolean isSuccessful = partTimeService.PublishPartTime(SessionID, p);
			Returndata.returnboolean(isSuccessful);
		}else if (action.equals("查询我发布的兼职")) {
			List<PartTime> partTimelist = partTimeService.QueryMyPartTime(SessionID);
			Returndata.returndata(Getjson.Generaljsonarray(partTimelist, action, new String[] { "user" }));
		} else if (action.equals("查询所有兼职")) {
			System.out.println("search:" + search);
			List<PartTime> partTime = partTimeService.QueryPartTimeBySearch(search);//这个search是校内/校外
			Returndata.returndata(Getjson.Generaljsonarray(partTime, action, new String[] { "user" }));
		} else if (action.equals("搜索兼职")) {
			System.out.println("search:" + search);
			List<PartTime> partTime = partTimeService.SearchPartTime(search);
			Returndata.returndata(Getjson.Generaljsonarray(partTime, action, new String[] { "user" }));
		} else if (action.equals("删除我的兼职")) {
			Integer id = Integer.valueOf(Id);
			boolean isSuccessful = partTimeService.DeleteMyPartTime(id);
			Returndata.returnboolean(isSuccessful);
		} else {
			System.err.println("兼职action不匹配");
		}
		return null;
	}
	
	
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSessionID() {
		return SessionID;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}
}
