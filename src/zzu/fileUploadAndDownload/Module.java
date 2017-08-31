package zzu.fileUploadAndDownload;

import java.util.List;

public class Module {

	private List<String> imgname; //图片名
	private String id;//表id
	private String identity;//身份标识，例如theme就是主题表
	public List<String> getImgname() {
		return imgname;
	}
	public void setImgname(List<String> imgname) {
		this.imgname = imgname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	@Override
	public String toString() {
		return "Module [imgname=" + imgname + ", id=" + id + ", identity=" + identity + "]";
	}
}
