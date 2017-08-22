package life.parTime.entity;

import persionalCenter.entity.User;

public class PartTime {
	private Integer partTimeId;
	private String title;// 兼职标题
	private String content;// 兼职详情
	private String salary;// 薪资待遇
	private String startDate;// 开始日期
	private String endDate;// 结束日期
	private String startTime;// 上班时间
	private String endTime;// 下班时间
	private String workPlace;// 工作地点
	private String contactWay;// 联系方式
	private String campus;//校内校外标识
	private String publishDate;// 发布时期
	private String status;// 兼职信息审核状态(正在审核 / 审核通过)
	private User user;
	
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getPartTimeId() {
		return partTimeId;
	}
	public void setPartTimeId(Integer partTimeId) {
		this.partTimeId = partTimeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	public String getContactWay() {
		return contactWay;
	}
	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "PartTime [partTimeId=" + partTimeId + ", title=" + title + ", content=" + content + ", salary=" + salary
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", startTime=" + startTime + ", endTime="
				+ endTime + ", workPlace=" + workPlace + ", contactWay=" + contactWay + ", campus=" + campus
				+ ", publishDate=" + publishDate + ", status=" + status + "]";
	}
	
}
