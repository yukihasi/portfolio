package model;
import java.io.Serializable;


public class User implements Serializable{
	private String name;
	private String pass;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	private String title;
	public User(String name,String pass) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.name = name;
		this.pass = pass;
	}
	
	public String getName() {
		return this.name;
	}
	public String getPass() {
		return this.pass;
	}
}
