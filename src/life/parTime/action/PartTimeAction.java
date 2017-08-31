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
			System.err.println("��ְactionΪ��");
			return null;
		}
		System.out.println(action);
		if (action.equals("������ְ")) {
			boolean isSuccessful = partTimeService.PublishPartTime(SessionID, p);
			Returndata.returnboolean(isSuccessful);
		}else if (action.equals("��ѯ�ҷ����ļ�ְ")) {
			List<PartTime> partTimelist = partTimeService.QueryMyPartTime(SessionID);
			Returndata.returndata(Getjson.Generaljsonarray(partTimelist, action, new String[] { "user" }));
		} else if (action.equals("��ѯ���м�ְ")) {
			System.out.println("search:" + search);
			List<PartTime> partTime = partTimeService.QueryPartTimeBySearch(search);//���search��У��/У��
			Returndata.returndata(Getjson.Generaljsonarray(partTime, action, new String[] { "user" }));
		} else if (action.equals("������ְ")) {
			System.out.println("search:" + search);
			List<PartTime> partTime = partTimeService.SearchPartTime(search);
			Returndata.returndata(Getjson.Generaljsonarray(partTime, action, new String[] { "user" }));
		} else if (action.equals("ɾ���ҵļ�ְ")) {
			Integer id = Integer.valueOf(Id);
			boolean isSuccessful = partTimeService.DeleteMyPartTime(id);
			Returndata.returnboolean(isSuccessful);
		} else {
			System.err.println("��ְaction��ƥ��");
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
