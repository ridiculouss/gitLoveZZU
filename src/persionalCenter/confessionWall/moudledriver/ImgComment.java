package persionalCenter.confessionWall.moudledriver;

import persionalCenter.confessionWall.entity.LoveCardComment;

public class ImgComment {
   private String userImg;
   private LoveCardComment loveCardComment;


public LoveCardComment getLoveCardComment() {
	return loveCardComment;
}
public void setLoveCardComment(LoveCardComment loveCardComment) {
	this.loveCardComment = loveCardComment;
}
@Override
public String toString() {
	return "ImgComment [userImg=" + userImg + ", loveCardComment=" + loveCardComment + "]";
}
public String getUserImg() {
	return userImg;
}
public void setUserImg(String userImg) {
	this.userImg = userImg;
}
   
}
