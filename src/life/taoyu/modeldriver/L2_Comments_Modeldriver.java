package life.taoyu.modeldriver;

public class L2_Comments_Modeldriver {

	private String action;
	private String SessionID;
	private String comments; 
	private String L1_Cid; 
	private String L2_Cid;
	
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getL1_Cid() {
		return L1_Cid;
	}
	public void setL1_Cid(String l1_Cid) {
		L1_Cid = l1_Cid;
	}
	public String getL2_Cid() {
		return L2_Cid;
	}
	public void setL2_Cid(String l2_Cid) {
		L2_Cid = l2_Cid;
	}
	@Override
	public String toString() {
		return "L2_Comments_Modeldriver [action=" + action + ", SessionID=" + SessionID + ", comments=" + comments
				+ ", L1_Cid=" + L1_Cid + ", L2_Cid=" + L2_Cid + "]";
	}
	
}
