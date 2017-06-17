package life.taoyu.entity;

public class Comments_L1 {

	private int L1_Cid;// 一级评论表ID
	
	private String account; //评论者唯一账户号	
	private String comments;//   评论内容
	private String num_replies; // 回复数量
	private Goods Cgoods;
	private String num_thumb;//    点赞数量
	private String Cdate;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCdate() {
		return Cdate;
	}
	public void setCdate(String cdate) {
		Cdate = cdate;
	}

	public Goods getCgoods() {
		return Cgoods;
	}
	public void setCgoods(Goods cgoods) {
		Cgoods = cgoods;
	}
	
	public int getL1_Cid() {
		return L1_Cid;
	}
	public void setL1_Cid(int l1_Cid) {
		L1_Cid = l1_Cid;
	}

	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getNum_replies() {
		return num_replies;
	}
	public void setNum_replies(String num_replies) {
		this.num_replies = num_replies;
	}
	public String getNum_thumb() {
		return num_thumb;
	}
	public void setNum_thumb(String num_thumb) {
		this.num_thumb = num_thumb;
	}

}
