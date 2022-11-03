package model;
import java.io.Serializable;


public class Account implements Serializable{
	private String userName;
	private String pass;
	private String musicTitle;
	private String music;
	private int userId;
	private int like;
	public Account(String userName,int id) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.userName = userName;
		this.userId = id;
	}
	public void setMusic() {
		this.music = music;
	}
	public void setMusicTitle() {
		this.musicTitle = musicTitle;
	}
	public void setLike() {
		this.like = like;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public String getMusic() {
		return this.music;
	}
	public String getMusicTitle() {
		return this.musicTitle;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

}
