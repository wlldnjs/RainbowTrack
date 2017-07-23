package bean;

import java.io.Serializable;

public class UserBean implements Serializable{
	private String id;
	private String pw;
	private String name;
	private String phoneNum;
	private String email;
	private String birth;
	// 팔로워(나를 좋아하는 사람들)
	private String follower;
	// 팔로잉(내가 좋아요 누른 사람들)
	private String following;
	// 프로필 사진
	private String image;
	private String likeMusic;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getFollower() {
		return follower;
	}
	public void setFollower(String follower) {
		this.follower = follower;
	}
	public String getFollowing() {
		return following;
	}
	public void setFollowing(String following) {
		this.following = following;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLikeMusic() {
		return likeMusic;
	}
	public void setLikeMusic(String likeMusic) {
		this.likeMusic = likeMusic;
	}

}
