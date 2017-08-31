package zzu.themb;

import java.sql.Date;

import zzu.util.GetDate;

public class ThembModuel {

	String action;//Ҫ���ĸ������
	String Id ;//���޵ı�id
	String date;
	
	public static ThembModuel getThembModuel(String action,String Id){
		 ThembModuel tm=new ThembModuel();
		 tm.setAction(action);
		 tm.setId(Id);
		 tm.setDate(GetDate.GetNowDate());
		 return tm;
	}
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ThembModuel [action=" + action + ", Id=" + Id + ", date=" + date + "]";
	}
	
}
