package zzu.adminAction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Component(value="Roast")
public class Roast {
	private Integer roastId;
	public Integer getRoastId() {
		return roastId;
	}
	public void setRoastId(Integer roastId) {
		this.roastId = roastId;
	}
	private String imageUrl;
	private String newsUrl;
	private String moduleIdentifier;//哪个模块的轮播标识
	public String getModuleIdentifier() {
		return moduleIdentifier;
	}
	public void setModuleIdentifier(String moduleIdentifier) {
		this.moduleIdentifier = moduleIdentifier;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getNewsUrl() {
		return newsUrl;
	}
	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}
	@Override
	public String toString() {
		return "Roast [ imageUrl=" + imageUrl + ", newsUrl=" + newsUrl + ", moduleIdentifier="
				+ moduleIdentifier + "]";
	}
}
