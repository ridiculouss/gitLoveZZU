package life.taoyu.entity;

public class Comments_L2 {

	private Integer L2_Cid;//  二级评论表ID
	private String account;//  评论者唯一账号
	private String comments;//  评论内容
	private int commented_id;//  被评论的内容的id（ L1ZZU1 ）
	private String num_replies;//回复数
	private String num_thumb;//点赞数
	private String Cdate;//评论时间
	public String getCdate() {
		return Cdate;
	}
	public void setCdate(String cdate) {
		Cdate = cdate;
	}
	//LL_id  一级评论表外键
	private Comments_L1 comments_l1;
	
	public Comments_L1 getComments_l1() {
		return comments_l1;
	}
	public void setComments_l1(Comments_L1 comments_l1) {
		this.comments_l1 = comments_l1;
	}
	public Integer getL2_Cid() {
		return L2_Cid;
	}
	public void setL2_Cid(Integer l2_Cid) {
		L2_Cid = l2_Cid;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getCommented_id() {
		return commented_id;
	}
	public void setCommented_id(int commented_id) {
		this.commented_id = commented_id;
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
	@Override
	public String toString() {
		return "Comments_L2 [L2_Cid=" + L2_Cid + ", account=" + account + ", comments=" + comments
				+ ", commented_id=" + commented_id + ", num_replies=" + num_replies + ", num_thumb=" + num_thumb + "]";
	}
	
}
